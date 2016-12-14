
package generadordearbol;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeneradorDeArbol extends JFrame implements ActionListener {
    Vector cls;
    Vector fil;
    Vector fls;
    
    JTable DataSet;
    JScrollPane JSP;
    
    JMenuBar barra=new JMenuBar();
    JMenu archivos=new JMenu("Archivo");
    JMenuItem compilar=new JMenuItem("Generar Arbol");
    JMenuItem salir=new JMenuItem("Salir");
    JMenuItem abrir=new JMenuItem("Abrir");
    
    
    GeneradorDeArbol(){
        super("Generador Arbol");
        setSize(300,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        HazInterfaz();
        HazEscuchas();
        setVisible(true);
    }
    public static void main(String[] args) {
        new GeneradorDeArbol();
    }
    public void HazInterfaz(){
        //String x="C:/Users/RAFAEL/Downloads/training_data (1).xml";
        //x=seleccionarArchivo(); 
        //JTable Tabla=LeerXML(x);
        //JScrollPane JSP= new JScrollPane(Tabla);
        //add(JSP);
        barra.add(archivos);
        barra.add(compilar);
        archivos.add(salir);
        archivos.add(abrir);
        setJMenuBar(barra);
        DataSet= new JTable();
        JSP= new JScrollPane(DataSet);
        add(JSP);
        //padre.ToString();
    }
    public void HazEscuchas(){
        compilar.addActionListener(this);
        salir.addActionListener(this);
        abrir.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==salir){
            System.exit(0);
        }
        if(e.getSource()==compilar){
            GenerarArbol a= new GenerarArbol(toVector(DataSet));
            Nodo padre=new Nodo();
            padre.Nombre="Arbol";
            a.crearArbol(padre,a.Valores[0].length-1,a.Columnas,a.Valores);
            try{
                a.GenerarArbolXML(padre,Direccion,"Arbol.xml" );
                JOptionPane.showMessageDialog(null,"Árbol Guardado con exito","Guardar...",JOptionPane.INFORMATION_MESSAGE);
        
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error al generar árbol: "+e,"Advertencia",JOptionPane.INFORMATION_MESSAGE);
            }
            return;
        }
        if(e.getSource()==abrir){
            Direccion=seleccionarArchivo();
            if(Direccion.equals(""))
                Direccion="C:/Users/RAFAEL/Downloads/training_data (1).xml";
            DataSet=LeerXML(Direccion);
            remove(JSP);
            JSP= new JScrollPane(DataSet);
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
        return "C:\\Users\\RAFAEL\\Desktop\\XMLS\\3 Servidores.xml";
    }
    public Vector toVector(JTable Tabla){
        Vector fil= new Vector();
        Vector fls= new Vector();
        for(int i=0; i<Tabla.getColumnCount(); i++)
            fil.add(Tabla.getModel().getColumnName(i));
        fls.add(fil);
        for(int i=0; i<Tabla.getRowCount(); i++){
            fil= new Vector();
            for(int a=0;a<Tabla.getColumnCount(); a++){
                fil.add(Tabla.getModel().getValueAt(i ,a).toString());
            } 
            fls.add(fil);
        }
        return fls;
    }
    public JTable LeerXML(String Direccion){
          try{
                    File fXmlFile = new File(Direccion);
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(fXmlFile);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("INSTANCIA");
                    cls= new Vector();
                    fil= new Vector();
                    fls= new Vector();
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                            Node nNode = nList.item(temp);
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                    NodeList nodeList = nNode.getChildNodes();                                    
                                    for(int c = 0; c < nodeList.getLength(); c++){
                                        Node tempNode = nodeList.item(c);
                                        if(tempNode.getNodeType() == Node.ELEMENT_NODE){
                                           if(temp==0)
                                                cls.add(tempNode.getNodeName());
                                           fil.add(tempNode.getTextContent());
                                        }
                                    }
                                    fls.add(fil);
                                    fil= new Vector();     
                            }
                    }
                    return new JTable(fls,cls);
                }catch (Exception e) {
                    e.printStackTrace();
                }
          return null;
    }
}
