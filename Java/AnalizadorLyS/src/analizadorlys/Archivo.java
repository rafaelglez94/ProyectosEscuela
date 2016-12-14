package analizadorlys;

import java.util.Scanner;
import java.util.regex.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class Archivo {
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null; 
    boolean PruebaLexica;
    String LineaActual;
    String TokenActual;
    String TokenAnterior;
    StringTokenizer tokens;  
    Vector col= new Vector();
    Vector fil= new Vector();
    Vector fls= new Vector();
    Cola Tks_Grds= new Cola();
    
    Vector colT= new Vector();
    Vector filT= new Vector();
    Vector flsT= new Vector();
    //Palabras Reservadas
    String P_R []={ "class",    "Palabra Reservada"//0
                    ,"public",  "Modificador"
                    ,"private", "Modificador"
                    ,"while",   "Palabra Reservada"
                    ,"if",      "Palabra Reservada"
                    ,"int",     "Tipo de dato"//10
                    ,"boolean", "Tipo de dato"
                    ,"true",    "Palabra Reservada"
                    ,"false",   "Palabra Reservada"
                    ,"{",       "Llave Abre"
                    ,"}",       "Llave Cierra"//20
                    ,"(",       "Parentesis Abre"
                    ,")",       "Parentesis Cierra"
                    ,";",       "Punto y Coma"
                    ,"=",       "Asignacion"
                    ,"+",       "Operador Aritmetico"//30
                    ,"-",       "Operador Aritmetico"
                    ,"<",       "Operador Logico"
                    ,">",       "Operador Logico"
                    ,"<=",      "Operador Logico"
                    ,">=",      "Operador Logico"//40
                    ,"==",      "Operador Logico"
                    ,"!=",      "Operador Logico"
            };
    Archivo(String Direccion){
        try {
            archivo = new File (Direccion);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    int N_Linea=1;
    public  void LeerArchivo(){
        col= new Vector();
        col.add("Tipo");
        col.add("Identificador");
        fls= new Vector();
        fil= new Vector();
        colT= new Vector();
        colT.add("Token");
        colT.add("Tipo");
        fls= new Vector();
        filT= new Vector();
        PruebaLexica=true;
        try {
            String linea;
            while((linea=br.readLine())!=null){
               LeerToken(linea);  
               N_Linea++;
            }
            N_Linea=1;
            if(PruebaLexica){
                System.out.println("\nPaso Prueba Lexica\n" );
                fr = new FileReader (archivo);
                br = new BufferedReader(fr);
                LineaActual=br.readLine();
                if(LineaActual!=null){
                    tokens=new StringTokenizer(LineaActual);
                    getToken();
                    if(!declaracion_clase()){
                        JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
                        //System.out.println("Error en la Linea: "+N_Linea+" cerca de el Token: "+TokenActual);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Programa Corrido con Exito");
                       // System.out.println("Programa Corrido con Exito");
                    }
                }   
            }
      }catch(Exception e){
         e.printStackTrace();
      }
    }
    public void Salir_Archivo(){
        try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
    }
    boolean RecorridoNormal = true;
    public boolean getToken(){
        if(RecorridoNormal)
            Tks_Grds.Lipiar();
        try{
            if(Tks_Grds.Vacia()){
                RecorridoNormal=true;
                if(tokens.hasMoreTokens()){
                    TokenActual=tokens.nextToken();
                    Tks_Grds.Mete(TokenActual);
                    //System.out.println("Token: "+TokenActual);
                    return true;
                }
                LineaActual=br.readLine();
                if(LineaActual!=null){
                    N_Linea++;
                    tokens=new StringTokenizer(LineaActual);
                    getToken();
                    return true;
                }    
            }
            TokenActual = Tks_Grds.Saca();
           // System.out.println("Token Actual "+TokenActual);
            return true;
        }
        catch(Exception e){
        }
        return false;
    }
    boolean Correcto;
    public boolean declaracion_clase(){
        modificador();
        if(TokenActual.compareTo(P_R[0])!=0)
            return false;
        getToken();
        if(!identificador())
            return false;
        getToken();
        if(TokenActual.compareTo(P_R[18])!=0)
            return false;  
        getToken();
        if(!declaracion_variable()){
            RecorridoNormal=false;
        }else{
          RecorridoNormal=true;
        }
        //getToken();
        if(!statament()){
            RecorridoNormal=false;
        }else{
            RecorridoNormal=true;
        }
        getToken();   
        if(TokenActual.compareTo(P_R[20])!=0)
            return false;  
        return true;
    }
    public boolean declaracion_variable(){
        modificador();
        if(!type())
            return false;
        getToken();
        if(!identificador())
            return false;
        getToken();
        if(TokenActual.compareTo(P_R[28])!=0)
            return false;
        getToken();
        if(!(integer_literal() ||  boolean_literal()))
            return false;
        getToken();
        if(TokenActual.compareTo(P_R[26])!=0)
            return false;
        getToken();
        return true;
    }
    public boolean type(){
        if(TokenActual.compareTo(P_R[10])==0 || TokenActual.compareTo(P_R[12])==0)
            return true;
        return false;
    } 
    public boolean statament(){
        if(!declaracion_variable()){
            RecorridoNormal=false;
            if(!while_statament()){
               if(!if_statament()){
                   return false;
               }   
            }
        }
        return true;
    }
    public boolean if_statament(){
        if(TokenActual.compareTo(P_R[8])!=0)
            return false;
        getToken();getToken();
        if(TokenActual.compareTo(P_R[22])!=0){
            JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            return false;
        }
        getToken();
        if(!expression()){
            JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);            
            //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            return false;
        }
        if(TokenActual.compareTo(P_R[24])!=0){
            JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);            
            //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            return false;
        }
        getToken();
        if(!statament_block())
            return false;
        return true;
    }
    public boolean while_statament(){
        if(TokenActual.compareTo(P_R[6])!=0)
            return false;
        getToken();getToken();
        if(TokenActual.compareTo(P_R[22])!=0){
            JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            return false;
        }
        getToken();
        if(!expression()){
            JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);            
            //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            return false;
        }
        if(TokenActual.compareTo(P_R[24])!=0)
            return false;
        getToken();
        if(!statament_block()){
            return false;
        }
            
        return true;
    }
    public boolean statament_block(){
        if(TokenActual.compareTo(P_R[18])!=0){
            JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            return false; 
        }
        getToken();
        if(!arismetica_expression()){
            RecorridoNormal=false;
        }else{
            RecorridoNormal=true;
        }
        if(!statament()){
            RecorridoNormal=false;
        }else{
            RecorridoNormal=true;
        }
        if(TokenActual.compareTo(P_R[20])!=0){
            JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);            
            //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
            return false; 
        }
        getToken();
        return true;
     }
    public boolean expression(){
         if(!identificador()){
             if(!integer_literal()){
                 return false;
             }
         }
         getToken();
         if(!(TokenActual.compareTo(P_R[34])==0 
                 || TokenActual.compareTo(P_R[36])==0
                 || TokenActual.compareTo(P_R[38])==0
                 || TokenActual.compareTo(P_R[40])==0
                 || TokenActual.compareTo(P_R[42])==0
                 || TokenActual.compareTo(P_R[44])==0))
             return false;
          getToken();
          if(!identificador()){
             if(!integer_literal()){
                 return false;
             }
         }
         getToken();
         return true;
    }
    public boolean arismetica_expression(){
         if(!identificador()){
             return false;
         }
         getToken();
         if(TokenActual.compareTo(P_R[28])!=0){
             //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
             JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
             return false;
         }
         getToken();
         if(!integer_literal()){
              //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
             JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
              return false;
         }
         getToken();
         if(!(TokenActual.compareTo(P_R[30])==0 || TokenActual.compareTo(P_R[32])==0)){
             JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
             //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
             return false;
         }  
         getToken();
         if(!integer_literal()){
              //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
             JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
              return false;
         }
         getToken();
         if(TokenActual.compareTo(P_R[26])!=0){
              //System.out.println("Error en la linea: "+N_Linea+" En el Token "+TokenActual);
             JOptionPane.showMessageDialog(null, "Error en la linea: "+N_Linea+" En el Token "+TokenActual);
              return false;
         }
         getToken();
         return true;
    }
    public void modificador(){
        if(TokenActual.compareTo(P_R[2])==0 ||TokenActual.compareTo(P_R[4])==0)
            getToken();
    }
    public boolean identificador(){
       Pattern pat = Pattern.compile("[a-z,_]+");
       Matcher mat = pat.matcher(TokenActual);
       if(!mat.matches())
          return false;
       return true;
    }
    public boolean integer_literal(){
       Pattern pat = Pattern.compile("[1-9][0-9]?");
       Matcher mat = pat.matcher(TokenActual);
       if(!mat.matches())
          return false;
       return true;
    }
    public boolean boolean_literal(){
        if(TokenActual.compareTo(P_R[14])==0 ||TokenActual.compareTo(P_R[15])==0)
          return false;
       return true;
    }    
    
    
    
    
    public void LeerToken(String Linea){
	StringTokenizer tokens=new StringTokenizer(Linea);
        while(tokens.hasMoreTokens()){
            String T=tokens.nextToken();
            if(!AnalizadorLexico(T))
                PruebaLexica=false;
            TokenAnterior=T;
        }
    }
    public boolean AnalizadorLexico(String Token){
        for(int i=0;i<P_R.length;i+=2){
           if(Token.compareTo(P_R[i])==0){
               flsT= new Vector();
               flsT.add(Token);
               flsT.add(P_R[i+1]);
               filT.add(flsT);
               return true;
           }
        }
        if(isDigit(Token)){
            flsT= new Vector();
            flsT.add(Token);
            flsT.add("Numero");
            filT.add(flsT);
            return true;
        }
        if(isIdentifier(Token)){
            flsT= new Vector();
            flsT.add(Token);
            flsT.add("Identificador");
            filT.add(flsT);
            fil= new Vector();
            if(TokenAnterior.compareTo(P_R[0])==0 ||TokenAnterior.compareTo(P_R[10])==0 ||TokenAnterior.compareTo(P_R[12])==0)
                fil.add(TokenAnterior);
            else
                fil.add("");
            fil.add(Token);
            fls.add(fil);

            return true;
        }
        JOptionPane.showMessageDialog(null, "Error Lexico en la linea: "+N_Linea +" cadena no valida: "+Token);
       // System.out.println("Error Lexico en la linea: "+N_Linea +" cadena no valida: "+Token );
        return false;
    }
    public boolean isDigit(String Token){
       Pattern pat = Pattern.compile("[1-9][0-9]?");
       Matcher mat = pat.matcher(Token);
       if(!mat.matches())
          return false;
       return true;
    }    
    public boolean isIdentifier(String Token){
       Pattern pat = Pattern.compile("[a-z,_]+");
       Matcher mat = pat.matcher(Token);
       if(!mat.matches())
          return false;
       return true;
    }
    public JTable TablaSimbolos(){
        return new JTable(fls,col);
    }
    public JTable TablaLexica(){
        return new JTable(filT,colT);
    }
}

