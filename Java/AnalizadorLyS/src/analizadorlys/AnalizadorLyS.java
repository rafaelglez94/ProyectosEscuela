package analizadorlys;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.*;
import java.util.*;

public class AnalizadorLyS extends JFrame  implements ActionListener{
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null; 
    String Direccion="";
    JTextArea AreaDeTrabajo= new JTextArea();
    JButton B= new JButton("Abrir Archivo...");
    JTable JTS= new JTable();
    JTable JTL= new JTable();
    JScrollPane JSPDTS;
    JScrollPane JSPDTL;
    JDialog DTS= new JDialog();
    JDialog DTL= new JDialog();
   
    JMenuBar barra=new JMenuBar();
    JMenu archivos=new JMenu("Archivo");
    JMenuItem compilar=new JMenuItem("Compilar");
    JMenuItem salir=new JMenuItem("Salir");
    JMenuItem abrir=new JMenuItem("Abrir");
    JMenuItem guardar=new JMenuItem("Guardar");
        
    AnalizadorLyS(){
        super("Analizador Lexico Sintatico");
        setSize(900,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        HazInterfaz();
        HazEscuchas();
        setVisible(true);
    }
    public void AbrirArchivo(String Direccion){
        try {
            String linea="";
            archivo = new File (Direccion);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            AreaDeTrabajo.setText("");
            while((linea=br.readLine())!=null){
                  AreaDeTrabajo.setText(AreaDeTrabajo.getText()+linea+"\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     public void actionPerformed(ActionEvent e){
        if(e.getSource()==salir){
            System.exit(0);
        }
        if(e.getSource()==compilar){
            if(AreaDeTrabajo.getText().length()==0)
                return;
            if(Direccion.equals("")){
                 JOptionPane.showMessageDialog(null, "Guardar el Archivo");
                 Direccion=seleccionarArchivo();
                 if(Direccion.equals(""))return;
            }
            GuardarArchivo(Direccion);
            Archivo a = new Archivo(Direccion);
            a.LeerArchivo();
            a.Salir_Archivo();
            
            DTS.remove(JSPDTS);
            DTL.remove(JSPDTL);
            
            JTS=a.TablaSimbolos();
            JTL=a.TablaLexica();
            
            JSPDTS= new JScrollPane(JTS);
            JSPDTL= new JScrollPane(JTL);
            DTS.add(JSPDTS);
            DTL.add(JSPDTL);
            DTS.show();
            DTL.show();
            return;
        }
        if(e.getSource()==abrir){
            Direccion=seleccionarArchivo();
            if(Direccion.equals(""))return;
            AbrirArchivo(Direccion);
            return;
        }
        if(e.getSource()==guardar){
            String Direccion=seleccionarArchivo();
            GuardarArchivo(Direccion);
        }
    }
    public void GuardarArchivo(String Direccion){
        FileWriter fw;
            try{
                  fw= new FileWriter(Direccion);
                  fw.write(AreaDeTrabajo.getText());
                  fw.close();
            }catch(IOException io){
                 JOptionPane.showMessageDialog(null, "Error al Guardar Archivo");            
            }
    }
    private void HazInterfaz() {
        barra.add(archivos);
        barra.add(compilar);
        archivos.add(salir);
        archivos.add(abrir);
        archivos.add(guardar);
        setJMenuBar(barra);
        DTS= new JDialog();
        DTS.setTitle("Tabla de Simbolos");
        DTL= new JDialog();
        DTL.setTitle("Tabla Lexica");
        DTS.setBounds(0, 50, 200, 250);
        DTS.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        DTL.setBounds(0, 300, 200, 400);
        DTL.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JSPDTS= new JScrollPane(JTS);
        JSPDTL= new JScrollPane(JTL);
        DTS.add(JSPDTS);
        DTL.add(JSPDTL);
        add(AreaDeTrabajo);
    }
    private void HazEscuchas() {
        compilar.addActionListener(this);
        salir.addActionListener(this);
        abrir.addActionListener(this);
        guardar.addActionListener(this);
    }
    public static void main(String[] args) {
        new AnalizadorLyS(); 
    }
     private String seleccionarArchivo() {
        try
        {
            String nombre="";
            JFileChooser file=new JFileChooser();
            file.showSaveDialog(this);
            File guarda =file.getSelectedFile();
            if(guarda !=null)
            {
                return guarda.toString();
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No se a especificado Ruta..","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        return "";
    }
}
