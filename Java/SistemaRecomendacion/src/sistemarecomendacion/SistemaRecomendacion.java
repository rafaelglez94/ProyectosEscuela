
package sistemarecomendacion;

import java.awt.BorderLayout;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import jxl.*;
import javax.swing.*;
import java.io.*;
public class SistemaRecomendacion extends JFrame implements ActionListener {
    JDialog DTR;
    JTable TR;
    JScrollPane JSPR;
    JLabel R;
    JTable TT;
    JScrollPane JSP;
    JMenuBar barra=new JMenuBar();
    JMenu archivos=new JMenu("Archivo");
    JMenuItem recomendar=new JMenuItem("Recomendar");
    JMenuItem salir=new JMenuItem("Salir");
    JMenuItem abrir=new JMenuItem("Abrir");
    JTextField persona= new JTextField();
    SistemaRecomendacion(){
        super("Sistema de Recomendacion");
        setSize(200,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        hazInterfaz();
        hazEscuchas();
        setVisible(true);
    }
    public static void main(String[] args) {
        new SistemaRecomendacion();
    }
    public void hazInterfaz(){    
        barra.add(archivos);
        barra.add(recomendar);
        archivos.add(salir);
        archivos.add(abrir);
        setJMenuBar(barra);
        TT=LeerExcel("C:\\Users\\RAFAEL\\Desktop\\Recomendacion.xls");

       // TT = new JTable();
        DTR= new JDialog();
        DTR.setTitle("Recomendacion");
        DTR.setSize(500,300);
        DTR.setLocationRelativeTo(null);
        DTR.setDefaultCloseOperation(2);
        JSP = new JScrollPane(TT);
        add(JSP);
        add(persona,BorderLayout.SOUTH);
        TR= new JTable();
        JSPR = new JScrollPane(TR);
        DTR.add(JSPR);
        R= new JLabel("Recomendacion: ");
        DTR.add(R,BorderLayout.SOUTH);
        
    }
    public void hazEscuchas(){
        recomendar.addActionListener(this);
        salir.addActionListener(this);
        abrir.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==salir){
            System.exit(0);
        }
        if(e.getSource()==recomendar){
            String aux[][]=Datos(TT);
            Vector cols= new Vector();
            Vector fil= new Vector();
            Vector<Vector> fls= new Vector<Vector>();
            Vector fil1= new Vector();
            Vector<Vector> fls1= new Vector<Vector>();
            int par=Integer.parseInt(persona.getText());
            int []PNV= pelisNoVistas(aux,par);
            int []PMS = masSimilar(aux,par);
            double [][]TC= new double[PMS.length][PNV.length*2];
            double[][] sub= new double[3][PNV.length];
            double auxS=0.0;
            cols.add("Persona");
            cols.add("Similitud");
            for(int i=0;i<TC[0].length;i+=2){
                cols.add(TT.getColumnName(PNV[(i/2)]));
                cols.add(TT.getColumnName(PNV[(i/2)])+".xSim");
                fil= new Vector();
                for(int j=0;j<TC.length;j++){
                    auxS=similitud(aux,par,PMS[j]);
                    fil.add(auxS);
                    if(aux[PMS[j]][PNV[i/2]].length()!=0){
                        TC[j][i]=Double.parseDouble(aux[PMS[j]][PNV[i/2]]);
                        TC[j][i+1]=TC[j][i]*auxS;
                        sub[0][i/2]+=TC[j][i+1];
                        sub[1][i/2]+=auxS;
                    }
                    else{
                        TC[j][i]=0;
                        TC[j][i+1]=0;
                    }
                }
                fls.add(fil);
                sub[2][i/2]=sub[0][i/2] / sub[1][i/2];     
            }
            for(int i=0;i<TC.length;i++){
                fil1= new Vector();
                fil1.add(TT.getModel().getValueAt(PMS[i], 0));
                for(int j=0;j<TC[0].length;j+=2){
                    fil1.add(fls.elementAt(j/2).elementAt(i).toString());
                    fil1.add(TC[i][j]);
                    fil1.add(TC[i][j+1]);
                }
                fls1.add(fil1);
            }
            for(int i=0;i<sub.length;i++){
                fil1= new Vector();
                fil1.add("");
                fil1.add("");
                for(int j=0;j<sub[0].length;j++){
                    fil1.add("");
                    fil1.add(sub[i][j]);
                }
                fls1.add(fil1);
            }
            TR=new JTable(fls1,cols);
            JSPR.remove(TR);
            DTR.remove(JSPR);
            JSPR= new JScrollPane(TR);
            DTR.add(JSPR);
            DTR.show();
            double may=0.0;
            int indice=-1;
            for(int i=0;i<sub[0].length;i++){
                if(indice<sub[2][i]){
                    may=sub[2][i];
                    indice=i;
                }
            }
            System.out.println("Se le recomienda: "+TT.getColumnName(PNV[indice]));
            R.setText("Recomendacion: "+TT.getColumnName(PNV[indice]));

        }
        if(e.getSource()==abrir){
            Direccion=seleccionarArchivo();
            TT=LeerExcel(Direccion);
            //remove(JSP);
            JSP= new JScrollPane(TT);
            add(JSP);
            revalidate();
            setVisible(true);
            return;
        }
    }
    String Direccion;
      private String seleccionarArchivo() {
        try{
            String nombre="";
            JFileChooser file=new JFileChooser();
            file.showOpenDialog(this);
            File guarda =file.getSelectedFile();
            System.out.println(guarda.toString());
            if(guarda !=null){
                return guarda.toString();
            } 
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No se a especificado Ruta..","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        return "C:\\Users\\RAFAEL\\Desktop\\Recomendacion.xls";
    }
    public JTable LeerExcel(String Direccion) {
        Vector cols= new Vector();
        Vector fil= new Vector();
        Vector fls= new Vector();
        File excelSheet = null;
        Workbook workbook = null;    

        try {
            Workbook wb = Workbook.getWorkbook(new File(Direccion));
            int sheetNo=0;
            Sheet sheet = wb.getSheet(sheetNo);
            int columns = sheet.getColumns();
            int rows = sheet.getRows();
            for(int j = 0;j < columns;j++) 
                  cols.add(sheet.getCell(j, 0).getContents());
            for(int i = 1;i < rows;i++) {
                    fil= new Vector();
                    for(int j = 0;j < columns;j++)
                     fil.add(sheet.getCell(j, i).getContents()+"");     
                fls.add(fil);
            }
             setSize(90*columns,30*rows);
             setLocationRelativeTo(null);
        } catch(Exception ioe) {
             ioe.printStackTrace();
        }
        return new JTable(fls,cols);
    }
    public String[][] Datos(JTable Tabla){
        int rows=Tabla.getRowCount();
        int cols=Tabla.getColumnCount();
        String x[][]= new String[rows][cols];
        for(int i=0;i<rows;i++)
            for(int j=1;j<cols;j++)
                x[i][j]=Tabla.getModel().getValueAt(i, j)+"";
        return x;
    }
    public double similitud(String[][] Datos,int r1,int r2){
        int contador=0;
        boolean si[]= new boolean[Datos[0].length];
        double suma=0.0;
        for(int i=1;i<Datos[0].length;i++){
            if(Datos[r1][i].length()==0){
                si[i]=false;
                continue;
            }  
            if(Datos[r2][i].length()==0){
                si[i]=false;
                continue;
            }
            contador++;
            si[i]=true;
        }
        if(contador==0)
            return 0.0;
        for(int i=1;i<Datos[0].length;i++){
               if(si[i]){
                suma+=(Math.pow((Double.parseDouble(Datos[r1][i])-Double.parseDouble(Datos[r2][i])), 2));
            }
        }
        suma=Math.sqrt(suma);
        return 1/(1+suma);
    }
    public int Recomendacion(String[][]Datos,int r){
        
        return 2;
    }
    public int[] pelisNoVistas(String[][] Datos,int r){
        Vector ind= new Vector();
        for(int i=1;i<Datos[0].length;i++){
            if(Datos[r][i].length()==0){
                ind.add(i);
            }
        }
      
        int []indeces= new int[ind.size()];
        for(int i=0;i<ind.size();i++){
            indeces[i]=Integer.parseInt(ind.elementAt(i).toString());
        }
        return indeces;
    }
    public int[] masSimilar(String[][] Datos,int r){
            double may=0;
            double sim[]= new double[Datos.length-1];
            int ind[]=new int[Datos.length-1];
            int inc=0;
            for(int i=0;i<Datos.length;i++){
                if(r!=i){
                    sim[inc]=similitud(Datos,r,i);
                    ind[inc]=i;
                    System.out.println("Similitud entre "+TT.getModel().getValueAt(r, 0)+" y "+ TT.getModel().getValueAt(i, 0)+"  igual a :"+sim[inc++]);   
                }
            }
            double aux=0.0;
            int auxi=0;
            for(int i=0;i<sim.length;i++){
                for(int j=0;j<sim.length;j++){
                    if(sim[i]>sim[j]){
                        aux=sim[i];
                        auxi=ind[i];
                        sim[i]=sim[j];
                        ind[i]=ind[j];
                        sim[j]=aux;
                        ind[j]=auxi;
                    }
                }
            }
            System.out.println("La persona mas similar es:"+TT.getModel().getValueAt(ind[0], 0));
        return ind;
    }
}
