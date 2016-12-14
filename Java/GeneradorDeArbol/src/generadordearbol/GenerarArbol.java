
package generadordearbol;


import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import java.io.StringWriter;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class GenerarArbol {
     String []Columnas;
     String [][]Valores;
    GenerarArbol(Vector<Vector> Valores){
        this.Valores=toArray(Valores);
    }
    public String[] Valores(int Columna,String DataSet[][]){
        int Tot=DataSet.length;
        int CD=0;
        boolean Esta=false;
        String Valor[]= new String[Tot];
        for(int i=0;i<Tot;i++){
            Esta=false;
            for(int j=0;j<CD;j++){
                if(Valor[j].equalsIgnoreCase(DataSet[i][Columna])){
                   Esta=true;
                   break;
                }
            }
            if(!Esta)
                Valor[CD++]=DataSet[i][Columna]; 
        }
        String V[]= new String[CD];
        for(int j=0;j<CD;j++)
            V[j]=Valor[j];
        return V;
    }
    public String[][] toArray(Vector<Vector> Valores){
        Vector<String> Aux= Valores.elementAt(0);
        this.Columnas = Aux.toArray(new String[Aux.size()]);
        String Val[][]=new String[Valores.size()-1][Aux.size()];
        for(int i=0;i<Valores.size()-1;i++){
            Vector aux = Valores.elementAt(i+1);
            for(int j=0;j<aux.size();j++){
                Val[i][j]=aux.elementAt(j).toString().replaceAll("\\s","");
               // System.out.print(Val[i][j]+"\t");
            }        
         //   System.out.println();  
        }//System.out.println();  
        return Val;
    }
    public String[][] toArray2(Vector<Vector> Valores){
        Vector<String> Aux= Valores.elementAt(0);
        String Val[][]=new String[Valores.size()][Aux.size()];
        for(int i=0;i<Valores.size();i++){
            Vector aux = Valores.elementAt(i);
            for(int j=0;j<aux.size();j++){
                Val[i][j]=aux.elementAt(j).toString().replaceAll("\\s","");
            }         
        } 
        return Val;
    }
    public String[] toArray3(Vector<String> Valores){
        String Val[]=new String[Valores.size()];
        for(int j=0;j<Valores.size();j++){
                Val[j]=Valores.elementAt(j).replaceAll("\\s","");
            } 
        return Val;
    }
    public void Mostrar(String [][]Valores){
        for(int i=0;i<Valores.length;i++){
            for(int j=0;j<Valores[0].length;j++)
                System.out.print(Valores[i][j]+"\t");
            System.out.println();  
        }
    }
    public void crearArbol(Nodo Padre,int Columna,String[] Cols,String DataSet[][]){
        int Clase=DataSet[0].length-1;
        String Valores[]= Valores(Clase,DataSet);
        if(Valores.length<=1){
           // System.out.println("Clase:"+Valores[0]);
            Nodo raiz= new Nodo(Cols[Clase],Valores[0]);
            Padre.AgregarHijo(raiz);
            return;
        }
        else if(DataSet.length<=1){
             return;
        }
        //System.out.println("\n");
        //Mostrar(DataSet);
        //System.out.println("\n");
        double entropiaG=entropy(DataSet);
        //ystem.out.println("entropiaG= "+entropiaG);
        double mayor=-1.0;
        int idM=-1;
        double aux=0.0;
        for(int i=0;i<Clase;i++){
            aux= (entropiaG - entropia(i,DataSet));
            //System.out.println("entropiaDif= "+aux);
            if(mayor < aux){
                mayor=aux;
                idM=i;
            }
        }      
        String Valor[]=Valores(idM,DataSet); 
        //System.out.println(Cols[idM]);
        String Cl=Cols[idM];
        Vector col= new Vector();
        for(int i=0;i<Cols.length;i++)
            if(i!=idM)
                col.add(Cols[i]);
        Cols=toArray3(col);
        Vector fil;
        Vector fls= new Vector();
        for(int i=0;i<Valor.length;i++){
            fls= new Vector();
            for(int j=0;j<DataSet.length;j++){
                fil= new Vector();
                if(Valor[i].equalsIgnoreCase(DataSet[j][idM])){
                    for(int k=0;k<DataSet[0].length;k++)
                        if(k!=idM)
                            fil.add(DataSet[j][k]);
                fls.add(fil);
                }
            }       
            String [][]DS=toArray2(fls);
            if(DS.length!=0){
                Nodo raiz= new Nodo(Cl,Valor[i]);
                    Padre.AgregarHijo(raiz);
                crearArbol(raiz,idM,Cols,DS);
            } 
        } 
    }
    public double entropy(String[][] DataSet){
        int Tot=DataSet.length;
        int Col=DataSet[0].length-1;
        String Valor[]=Valores(Col,DataSet);
        int []Cont= new int[Valor.length];
        for(int i=0;i<Tot;i++){
            for(int j= 0;j<Valor.length;j++)
                if(Valor[j].equalsIgnoreCase(DataSet[i][Col])){
                   Cont[j]++;
                   break;
                }
        }
        double entropia=0.0;
        for(int i=0;i<Cont.length;i++)
            entropia-=((double)Cont[i]/Tot)*log2((double)Cont[i]/Tot);
        return entropia;
    }
    public static double log2(double num){
        return (Math.log(num)/Math.log(2));
    }
    public double entropia(int Columna,String [][]DataSet){
        int Clase=DataSet[0].length-1;
        String D[]=Valores(Clase,DataSet);
        String dj[]=Valores(Columna,DataSet);    
        double cont[][]=new double[dj.length][D.length+1];
        
        for(int i=0;i<DataSet.length;i++)
            for(int j=0;j<dj.length;j++)
                if(dj[j].equalsIgnoreCase(DataSet[i][Columna])){
                    for(int l=0;l<D.length;l++)
                        if(D[l].equalsIgnoreCase(DataSet[i][Clase])){
                            cont[j][l]++;
                            cont[j][D.length]++;
                            break;
                        }
                }
        double []entropy= new double [cont.length];
        double []sub= new double [cont.length];
        double entropia=0.0;
        for(int i=0;i<cont.length;i++){
            for(int j=0;j<cont[0].length-1;j++){
                if(cont[i][j]!=0)
                    entropy[i]-=((double)cont[i][j]/cont[i][cont[0].length-1])*log2((double)cont[i][j]/cont[i][cont[0].length-1]);
            }
            sub[i]=entropy[i]*(cont[i][cont[0].length-1]/DataSet.length);
            entropia+=sub[i];
        }
        return entropia;
    }
    public static void GenerarArbolXML(Nodo Raiz, String Direccion,String Nombre) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, Nombre, null);
        document.setXmlVersion("1.0");    
        Element raiz = document.getDocumentElement();
        Source source = new DOMSource(Raiz.ToString(raiz,document));
        Result result = new StreamResult(new java.io.File(Direccion+Nombre)); //nombre del archivo
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(source, result);
        }
}


