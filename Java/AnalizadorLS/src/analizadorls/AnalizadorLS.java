
package analizadorls;

import java.awt.BorderLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorLS extends JFrame  implements ActionListener{
    BufferedReader br = null; 
    String Direccion="C:\\Users\\RAFAEL\\Documents\\ejemplo.txt";
    JTextArea AreaDeTrabajo= new JTextArea();
    JScrollPane scrollPane = new JScrollPane(AreaDeTrabajo);
    JButton B= new JButton("Abrir Archivo...");
 
    JMenuBar barra=new JMenuBar();
    JMenu archivos=new JMenu("Archivo");
    JMenuItem compilar=new JMenuItem("Compilar");
    JMenuItem salir=new JMenuItem("Salir");
    JMenuItem abrir=new JMenuItem("Abrir");
    JMenuItem guardar=new JMenuItem("Guardar");
    JLabel resultado = new JLabel("");    
    AnalizadorLS(){
        super("Analizador Lexico Sintatico");
        setSize(900,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        HazInterfaz();
        HazEscuchas();
        setVisible(true);
    }
     private void HazInterfaz() {
        barra.add(archivos);
        barra.add(compilar);
        archivos.add(salir);
        archivos.add(abrir);
        archivos.add(guardar);
        setJMenuBar(barra);
        add(scrollPane);
        add(resultado,BorderLayout.SOUTH); 
        AbrirArchivo(Direccion);   
    }
    private void HazEscuchas() {
        compilar.addActionListener(this);
        salir.addActionListener(this);
        abrir.addActionListener(this);
        guardar.addActionListener(this);
    }
    public void AbrirArchivo(String Direccion){
        try {
            String linea="",cadena="";
            br = new BufferedReader(new FileReader (new File (Direccion)));
            while((linea=br.readLine())!=null)
                cadena+=linea+"\n";
            AreaDeTrabajo.setText(cadena);
        }catch(Exception e){}
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==salir)
            System.exit(0);
        if(e.getSource()==compilar){
            if(Direccion.equals("")){
                 JOptionPane.showMessageDialog(null, "Guardar el Archivo");
                 Direccion=seleccionarArchivo();
                 if(Direccion.equals(""))return;
            }
            GuardarArchivo(Direccion,AreaDeTrabajo.getText());
            Sintactico c= new Sintactico(Direccion);
            remove(resultado);
            resultado = new JLabel(c.Resultado());
            add(resultado,BorderLayout.SOUTH);
            this.setVisible(true);
            if(c.byteCode().length()!=0){
                GuardarArchivo(Direccion.substring(0, Direccion.length()-4)+"JBC.txt",c.byteCode());
                try {
                    Runtime obj = Runtime.getRuntime();
                    obj.exec("C:/Program Files/Sublime Text 3/sublime_text.exe "+Direccion.substring(0, Direccion.length()-4)+"JBC.txt");
                } catch (IOException ex) {
                    Logger.getLogger(AnalizadorLS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return;
        }
        if(e.getSource()==abrir){
            Direccion=seleccionarArchivo();
            if(Direccion.equals(""))return;
            AbrirArchivo(Direccion);
            return;
        }
        if(e.getSource()==guardar){
            if(Direccion.equals(""))
                Direccion=seleccionarArchivo();
            GuardarArchivo(Direccion,AreaDeTrabajo.getText());
        }
    }
    public void GuardarArchivo(String Direccion,String Texto){
        FileWriter fw;
            try{
                  fw= new FileWriter(Direccion);
                  fw.write(Texto);
                  fw.close();
            }catch(IOException io){
                 JOptionPane.showMessageDialog(null, "Error al Guardar Archivo");            
            }
    }
   
    public static void main(String[] args) {
        new AnalizadorLS(); 
    }
    private String seleccionarArchivo() {
        try
        {
            String nombre="";
            JFileChooser file=new JFileChooser();
            file.showSaveDialog(this);
            File guarda =file.getSelectedFile();
            System.out.println(guarda.toString());
            if(guarda !=null)
                return guarda.toString();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"No se a especificado Ruta..","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        return "";
    }
}

class Sintactico{
    String Tipos[]={
        "Error Léxico","class","void","main","extends","return",
        "if","System.out.println","else","=","==","+",";",",","(",
        ")","{","}","int","boolean","'true'","IDENTIFIER","INTEGER"}; 
    /*
        0 - Error Sintactico
        1 - class 
        2 - void
        3 - main
        4 - extends   
        5 - return
        6 - if
        7 - System.out.println
        8 - else
        9 - =
       10 - ==
       11 - +
       12 - ;
       13 - ,   
       14 - (   
       15 - )
       16 - {
       17 - }
       18 - int
       19 - boolean
       20 - 'true'
       21 - IDENTIFIER
       22 - INTEGER
    */
    BufferedReader br = null;
    FileReader fr=null;
    Vector tks= new Vector();
    String Linea="",Direccion="",token,Error="";
    int NumLinea=0, tok,T;
    boolean Errores=false;
    boolean igualigual=false;
    NodoProgram P;
    StringTokenizer st= new StringTokenizer("");
    StringTokenizer st1=new StringTokenizer("");
    String tokenMas,AuxToken;
    
    Sintactico(String Direccion){
        this.Direccion=Direccion;
        try {
            fr=new FileReader (new File (Direccion));
            br = new BufferedReader(fr);
        }catch(Exception ec){}
        //while(tok!=-1)    
            advance();
        Program();
        /*if(!Errores){
            if(P.check().toString().length()==0)
              System.out.println(P.byteCode());
        }*/
    }
    
    public String byteCode(){
        if(!Errores)
            if(Error.length()==0)
                return P.byteCode();
        return "";    
    }
    public String Resultado(){
        if(!Errores){
            Error=P.check();
            if(Error.length()==0)
                return "Programa Compilado Con Exito";
            return P.check();
        }
        return Error;              
    }
    public void advance(){
        this.tok = getToken();
    }
    public void eat(int T){
        this.T=T;
        if(T == this.tok)
            advance();
        else
            error();
    }
    public boolean check(int T){
        this.T=T;
        if(T == tok)
            return true;
        return false;
    }
    public void error(){
        if(!Errores){
            if(tok!=0)
                Error+="Error en la linea: #"+NumLinea+" cerca de "+ token +" Se esperaba "+ Tipos[T] +"\n";
            else
                Error+="Error Léxico la linea: #"+NumLinea+" cerca de "+ token +" Se esperaba "+ Tipos[T]+"\n" ;
            Errores=true;
        }
    }
    public void Program(){
     P= new NodoProgram();
     ArrayList clases= new ArrayList();
     NodoMainClass  MC = mainClass();
        while(tok!=-1 && tok!=0)
            if(check(1))
                clases.add(classDeclaration());
            else{
                error();
                break;
            }
        P.MC=MC;
        P.Clases=clases;
    } 
    public NodoMainClass mainClass(){
        eat(1);
        String IdClase=token;
        eat(21);
        eat(16);
        eat(2);
        eat(3);
        eat(14);
        String IdParametro=token;
        eat(21);
        eat(15);
        NodoStatement s = statement();
        eat(17);
        return new NodoMainClass(IdClase,IdParametro,s);
    }
    public NodoClass classDeclaration(){
        String IdExtends="";
        ArrayList var= new ArrayList();
        ArrayList method= new ArrayList();
        NodoDeclarationVar vOm;
        NodoDeclarationMethod me;
        eat(1);
        String IdClass= token;
        eat(21);
        if(check(4)){
            eat(4);
             IdExtends= token;
            eat(21);    
        }
        eat(16);
        while((vOm=DeclarationVarOrMethod()).VoM==1){
            var.add(vOm);
            varDeclaration();
        }     
        if(check(14)){
            me= methodDeclaration(vOm);
            method.add(me);
            while((vOm=DeclarationVarOrMethod()).VoM==2){
                    me = methodDeclaration(vOm);
                    method.add(me);
                 }
        }
        eat(17);
        return new NodoClass(IdClass,IdExtends,var,method,P);
    }
    public NodoDeclarationVar DeclarationVarOrMethod(){
        String tipo,id;
        if((tipo=Type())!=null){
            id=token;
            eat(21);
            if(check(12))
                return new NodoDeclarationVar(1,id,tipo);
            if(check(14))
                return new NodoDeclarationVar(2,id,tipo);
        }
        return new NodoDeclarationVar(0,"","");
    }
    public void varDeclaration(){
        eat(12);
    }
    public NodoDeclarationMethod methodDeclaration(NodoDeclarationVar vOm){
        String tipo,id;
        ArrayList parametros= new ArrayList();
        ArrayList varD= new ArrayList();
        ArrayList stamts= new ArrayList();
        NodoDeclarationVar vOm1;
        eat(14);
        if((tipo=Type())!=null){
            id=token;
            eat(21);
            parametros.add(new NodoDeclarationVar(3,id,tipo));
            while(check(13)){
                eat(13);
                if((tipo=Type())!=null){
                    id=token;
                    eat(21);
                    parametros.add(new NodoDeclarationVar(3,id,tipo));
                }
            }
        }
        eat(15);
        eat(16);
        while((vOm1=DeclarationVarOrMethod()).VoM==1){
            varD.add(vOm1);
            varDeclaration();
        }   
        while(isStatement())
            stamts.add(statement());
        eat(5);
        NodoExpression e = expression();
        eat(12);
        eat(17);
        return new NodoDeclarationMethod(vOm,parametros,varD,new NodoStatements(stamts),e);
    }
    public boolean isStatement(){
        if(tok== 16 || tok== 6 || tok== 7 ||tok== 21)
            return true;
        return false;
    }
    public NodoStatement statement(){
        switch(tok){
            case 16:
                eat(16);
                ArrayList stats= new ArrayList();
                while(isStatement()){
                    stats.add(statement());
                }
                eat(17);
                return new NodoStatements(stats);
            case 6:
                eat(6);
                eat(14);
                NodoExpression Ex = expression();
                eat(15);
                NodoStatement sT=statement();
                eat(8);
                NodoStatement sF=statement();
                return new NodoIfStatement(Ex,sT,sF);
            case 7:
                eat(7);
                eat(14);
                NodoExpression Ex1 = expression();
                eat(15);
                eat(12);
                return new NodoSOPStatement(Ex1);
            case 21:
                String Id=token;
                eat(21);
                eat(9);
                NodoExpression Ex2 = expression();
                eat(12);
                 return new NodoIgualStatement(Id,Ex2);
            default: 
                error();
        }   
        return null;
    }
    public String SegExpression(){
        String Seg=token;
         switch(tok){
            case 20:
                eat(20);
                return Seg;
            case 21:
                eat(21);
                return Seg;
            case 22:
                eat(22);
                return Seg;
            default:
                error();
         }
         return null;
    }
    public NodoExtExpression AsigExpression(){
        String Operador;
        if(!check(10))    
           if(!check(11))
               return null;
           else{
               eat(11);
              Operador=Tipos[11];
           }
        else{
           eat(10); 
           Operador=Tipos[10];
        }
        return new NodoExtExpression(Operador,SegExpression(),AsigExpression());
    }
    public NodoExpression expression(){
        String exp1=SegExpression();
        return new NodoExpression(exp1,AsigExpression());
    }
    public String Type(){
        if(!check(18))
            if(!check(19))
                return null;
            else{
                eat(19);
                return Tipos[19];
            }
        else
            eat(18);
            return Tipos[18];
    }
    public void removeToken(String toke){
        String cadena="";
        for(int i=0;i<tokenMas.length();i++)
            if(i>=toke.length())
                cadena+=tokenMas.charAt(i);
        tokenMas=cadena;
    }
    public int getToken(){
        try{
            while(!st1.hasMoreTokens()){
                while(!st.hasMoreTokens()){
                    if((Linea=br.readLine())==null)
                        return -1;
                    st=new StringTokenizer(Linea);
                    NumLinea++;   
                }
                tokenMas = st.nextToken();
                st1=new StringTokenizer(tokenMas,"{}();,+=",true);
            }
            token = st1.nextToken();
            removeToken(token);
            if(token.equals("=") && tokenMas.length()!=0 && tokenMas.charAt(0)=='='){
                token += st1.nextToken();            
            }
           // System.out.println(token);
       }catch(IOException e){
            return -1;
        }
        for(int i=1;i<Tipos.length-2;i++){
            if(token.compareTo(Tipos[i])==0){
                tks.add(new Simbolo(i,token) {});
                return i; 
            }
        }
        if(isIdentifier(token)){
            tks.add(new Simbolo(21,token) );
            return 21;
        }
        if(isInteger(token)){
            tks.add(new Simbolo(22,token) );
            return 22;
        }
        tks.add(new Simbolo(0,token) );
        return 0;
    }
    public boolean isInteger(String Token){
       Pattern pat = Pattern.compile("[0-9][0-9]*");
       Matcher mat = pat.matcher(Token);
       if(!mat.matches())
          return false;
       return true;
    }    
    public boolean isIdentifier(String Token){
       Pattern pat = Pattern.compile("[a-z][a-z0-9]*");
       Matcher mat = pat.matcher(Token);
       if(!mat.matches())
          return false;
       return true;
    }
}
 class Simbolo{
    int Num_Tipo;
    String Valor;
    Simbolo(int Num_Tipo,String Valor){
        this.Num_Tipo=Num_Tipo;
        this.Valor=Valor;
    }
    public String toString(){
        return this.Valor+" - "+this.Num_Tipo;
    }
}