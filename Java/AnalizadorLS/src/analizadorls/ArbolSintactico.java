
package analizadorls;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArbolSintactico {
    
}
class NodoProgram{
    NodoMainClass MC=null;
    ArrayList Clases= new ArrayList();
    NodoProgram(){};
    NodoProgram(NodoMainClass MC,ArrayList Clases){
        this.MC=MC;
        this.Clases=Clases;
    }
    public String toString(){
        String cadena;
        cadena="P \n"+MC.toString();
        for(int i=0;i<Clases.size();i++)
            cadena+=Clases.get(i).toString();
        return cadena+"\n";
    }
    public String check(){
        Vector TC= new Vector();
        TC.add(MC.IdClase);
        for(int i=0;i<Clases.size();i++)
            TC.add(((NodoClass)Clases.get(i)).IdClase);
        String cadena=MC.check();
        for(int i=0;i<Clases.size();i++){
                cadena+=((NodoClass)Clases.get(i)).check(TC);
        }
        return cadena;
    }
    public String byteCode(){
        String cadena=MC.byteCode();
        for(int i=0;i<Clases.size();i++)
          cadena+=((NodoClass)Clases.get(i)).byteCode();
        return cadena;
    }
}
class NodoMainClass{
    String IdClase;
    String IdParametro;
    NodoStatement statament;
    NodoMainClass(String IdClase, String IdParametro,NodoStatement statament){
        this.IdClase=IdClase;
        this.IdParametro=IdParametro;
        this.statament=statament;
    }
    public String toString(){
       return IdClase+"\n"+IdParametro+"\n"+statament.toString()+"\n";
    }
    Vector<Vector> TS= new Vector<Vector>();
    public String check(){
        return statament.check(TS);
    }
    public String byteCode(){
        int i=0;
        String cadena="class "+this.IdClase+" { \n\t "+this.IdClase+"();\n\t\t "
                + "Code:\n\t\t\t"
                + "0: aload_0\n\t\t\t"
                + "1: invokespecial #1\n\t\t\t"
                + "4: return\n\t"
                +"void main( "+this.IdParametro+" );\n\t\t" 
                + "Code:\n\t\t\t" 
                + this.statament.byteCode(i,TS);
                i=this.statament.getI();
                cadena+= (i++)+": return\n";
        return cadena+"}";
    }
}
class NodoClass{
    String IdClase;
    String IdExtends;
    ArrayList Var= new ArrayList();
    ArrayList Method= new ArrayList();
    NodoProgram P;
    NodoClass(String IdClase,String IdExtends, ArrayList Var, ArrayList Method,NodoProgram P){
        this.IdClase=IdClase;
        this.IdExtends=IdExtends;
        this.Var=Var;
        this.Method=Method;
        this.P=P;
    }
    public String toString(){
        String cadena=this.IdClase+"\n"+this.IdExtends+"\n";
        for(int i=0;i< Var.size();i++)
            cadena+= Var.get(i).toString();
        for(int i=0;i< Method.size();i++)
            cadena+= Method.get(i).toString();
        return cadena;
    }
    Vector<Vector> TS;
    public String check(Vector TC){
        TS= new Vector<Vector>();
        String cadena="";
        if(this.IdExtends.length()>0){
            boolean estaExtends=false;
            for(int i=0;i<TC.size();i++)
                if(TC.get(i).toString().equals(this.IdExtends))
                    estaExtends =true;
            if(!estaExtends)
                return "La clase: "+this.IdExtends+" no fue encontrada ";
            if(this.IdExtends.length()>0){
                
                for(int i=0;i<this.P.Clases.size();i++){
                    if(((NodoClass)this.P.Clases.get(i)).IdClase.equals(this.IdExtends)){
                        for(int j=0;j<((NodoClass)this.P.Clases.get(i)).Var.size() ;j++){
                            String aux=((NodoDeclarationVar)((NodoClass)P.Clases.get(i)).Var.get(i)).IdVar;
                            for(int k=0;k<this.Var.size();k++)
                                if(((NodoDeclarationVar)(this.Var.get(k))).IdVar.equals(aux)){
                                    return "La objeto heredado: "+aux+" esta duplicada\n";
                                }
                            this.Var.add(((NodoClass)P.Clases.get(i)).Var.get(j)); 
                        }
                        break;
                    }
                }    
            }
        }
        for(int i=0;i< Var.size();i++)
             cadena+=((NodoDeclarationVar)Var.get(i)).check(TS);
        for(int i=0;i< Method.size();i++){
             cadena+=((NodoDeclarationMethod)Method.get(i)).check(TS,this.Method);
        }
        return cadena;
    }
    public String byteCode(){
        TS= new Vector<Vector>();
        int i=0;
        String cadena="class "+this.IdClase+" { \n\t "+this.IdClase+"();\n\t\t "
                + "Code:\n\t\t\t"
                + "0: aload_0\n\t\t\t"
                + "1: invokespecial #1\n\t\t\t"
                + "4: return\n";
        for(int j=0;j < Method.size();j++){
             cadena+=((NodoDeclarationMethod)Method.get(j)).byteCode(i);
        }
        return cadena+"}";
    }
}
class NodoDeclarationVar{
    int VoM;
    String IdVar;
    String Tipo;
    NodoDeclarationVar(int VoM,String IdVar,String Tipo){
        this.VoM=VoM;
        this.IdVar=IdVar;
        this.Tipo=Tipo;
    }
    public String toString(){
        return this.IdVar+" "+this.Tipo+"\n";
    }
    Vector col= new Vector();
    public String check(Vector<Vector> TS){
        for(int i=0;i<TS.size();i++){
            if(this.IdVar.compareTo((String)TS.elementAt(i).elementAt(0))== 0){
                return "La variable :"+this.IdVar+" ya esta definida";
            }
        }   
        col= new Vector();
        col.add(this.IdVar);
        col.add(this.Tipo);
        TS.add(col);
        return "";
    }
}
class NodoDeclarationMethod{
    NodoDeclarationVar declaration;
    ArrayList Parametros;
    ArrayList decVar;
    NodoStatements stataments;
    NodoExpression exp;
    NodoDeclarationMethod(NodoDeclarationVar declaration, ArrayList Parametros, ArrayList decVar, NodoStatements stataments, NodoExpression exp){
        this.declaration=declaration;
        this.Parametros=Parametros;
        this.decVar=decVar;
        this.stataments=stataments;
        this.exp=exp;
    }
     public String toString(){
        String cadena=this.declaration.toString();
        for(int i=0;i< Parametros.size();i++)
            cadena+= Parametros.get(i).toString();
         for(int i=0;i< decVar.size();i++)
            cadena+= decVar.get(i).toString();
        
        cadena+= this.stataments.toString();
        cadena+= this.exp.toString();
        return cadena;
    }
    Vector<Vector> TSimbolos;
    public String check(Vector<Vector> TS,ArrayList Methods){
        TSimbolos= new Vector<Vector>();
         String cadena="";
        for(int i=0;i< TS.size();i++)
             TSimbolos.add(TS.elementAt(i));
        int x=0;
        for(int i=0;i< Methods.size();i++)
            if(((NodoDeclarationMethod)Methods.get(i)).declaration.IdVar.equals(this.declaration.IdVar))
                x++;
        if(x>1)
            return "Error: El metodo "+this.declaration.IdVar+" tiene sobrecarga ";
        for(int i=0;i< Parametros.size();i++)
             cadena+=((NodoDeclarationVar)Parametros.get(i)).check(TSimbolos);
         for(int i=0;i< decVar.size();i++)
             cadena+=((NodoDeclarationVar)decVar.get(i)).check(TSimbolos);
        cadena+= this.stataments.check(TSimbolos);
        int error=this.exp.check(TSimbolos,2);
        if(error==-1)
            return cadena+"Error de tipos en la expreción de retorno : "+this.exp.toString()+"\n";
        if(error==-2)
            return cadena+"Variable no definida en la expreción de retorno : "+this.exp.toString()+"\n";
        if(error!=(this.declaration.Tipo.equals("int")?0:1)){
            return cadena+"Error de tipo de retorno : Requiere "+this.declaration.Tipo+" y obtiene "+(error==0?"int":"boolean")+"\n";    
        }
        return cadena;
    }
    public String byteCode(int i){
        for(int j=0;j<TSimbolos.size();j++)
            TSimbolos.get(j).add(false);
        
        String cadena ="\t"+ this.declaration.Tipo+" "+this.declaration.IdVar+" ( );\n\t\t"+ "Code:\n\t\t\t";
        cadena += this.stataments.byteCode(i,TSimbolos);
        i=this.stataments.getI();
        cadena += this.exp.byteCode(i,TSimbolos);
        i=this.exp.getI();
        cadena+= (i++)+": return\n";
        return cadena;
    }
}
abstract class NodoStatement{
    abstract public int getI();
    abstract public String toString();
    abstract public String check(Vector<Vector> TS);
    abstract public String byteCode(int indice,Vector<Vector> TS);
}
class NodoStatements extends NodoStatement{
    ArrayList statements= new ArrayList();
    NodoStatements(ArrayList statements){
        this.statements=statements;
    }
    public void addStatements(NodoStatement statement){
        statements.add(statement);
    }
 
    public String toString(){
        String cadena="";
        for(int i=0;i<statements.size();i++)
            cadena+=statements.get(i).toString();
        return cadena;
    }
    public String check(Vector<Vector> TS){
        String error="";
        for(int i=0;i<statements.size();i++)
            error+=((NodoStatement)statements.get(i)).check(TS);
        return error;
    }
    int i;
    public String byteCode(int indice,Vector<Vector> TS){
        this.i=indice;
        String cadena="";
        for(int i=0;i<statements.size();i++){
            cadena+=((NodoStatement)statements.get(i)).byteCode(this.i,TS);
            this.i=((NodoStatement)statements.get(i)).getI();
        }
        return cadena;
    }
    public int getI(){
        return this.i;
    }
}
class NodoIfStatement extends NodoStatement{
    NodoExpression Exp;
    NodoStatement statementTrue;
    NodoStatement statementFalse;
    NodoIfStatement(NodoExpression Exp,NodoStatement statementTrue,NodoStatement statementFalse){
        this.Exp=Exp;
        this.statementTrue=statementTrue;
        this.statementFalse=statementFalse;
    }
    public String toString(){
        return this.Exp.toString()+ this.statementTrue.toString()+ this.statementFalse.toString();
    }
    public String check(Vector<Vector> TS){
        int error; 
        if((error=this.Exp.check(TS,2))==-1)
            return "Error de tipos en la expreción : "+this.Exp.toString()+"\n";
        if((error=this.Exp.check(TS,2))==-2)
            return "Variable no definida en la exprecion : "+this.Exp.toString()+"\n";
        return this.statementTrue.check(TS)+ this.statementFalse.check(TS);
    }
    int i;
    public String byteCode(int i,Vector<Vector> TS){
        this.i=i;
        String cadena="";
        cadena  += this.Exp.byteCode(i,TS);
        this.i=this.Exp.getI();
        this.statementTrue.byteCode(this.i,TS);
        int aux1=this.statementTrue.getI();
        this.statementFalse.byteCode(aux1, TS);
        int aux2=this.statementFalse.getI();
        cadena  +=(this.i++) + ": if_icmpne "+ (aux1+2) + " \n\t\t\t"; 
        cadena  +=this.statementTrue.byteCode(this.i,TS);
        this.i=this.statementTrue.getI();
        cadena  +=(this.i++) + ": goto  "+ (aux2+2) + "  \n\t\t\t";
        cadena  +=this.statementFalse.byteCode(this.i,TS);
        this.i=this.statementFalse.getI();
        return cadena;
    }
    public int getI(){
        return this.i;
    }
}
class NodoSOPStatement extends NodoStatement{
    NodoExpression Exp;
    NodoSOPStatement(NodoExpression Exp){
        this.Exp=Exp;
    }
    public String toString(){
        return this.Exp.toString();
    }
    public String check(Vector<Vector> TS){
        int error=this.Exp.check(TS,2);
        if(error==-1)
            return "Error de tipos en la expreción  : "+this.Exp.toString()+"\n";
        if(error==-2)
            return "Variable no definida en la expreción : "+this.Exp.toString()+"\n";
        return "";
    }
    int i;
    public String byteCode(int i,Vector<Vector> TS){
        this.i=i;
        String cadena=this.Exp.byteCode(this.i,TS);
        this.i = this.Exp.getI();
        cadena+=""+(this.i++)+ ": invokevirtual	#3 \n\t\t\t";
        this.i+=2;
        return cadena; 
    }
    public int getI(){
        return this.i;
    }
}
class NodoIgualStatement extends NodoStatement{
    String Id;
    NodoExpression Exp;
    NodoIgualStatement(String Id,NodoExpression Exp){
        this.Id=Id;
        this.Exp=Exp;
    }
    public String toString(){
        return this.Id+"\n"+this.Exp.toString()+"\n";
    }
    public String check(Vector<Vector> TS){
        int tipo=-1;
        for(int i=0;i<TS.size();i++){
            if(this.Id.compareTo((String)TS.elementAt(i).elementAt(0))== 0){
                tipo = "int".equals(TS.elementAt(i).elementAt(1))?0:1;
            }
        }
        if(tipo!=-1){
            int error=this.Exp.check(TS,2);
            if(error==-1)
                return "Error de tipos en la expreción: "+this.Exp.toString()+"\n";
            if(error==-2)
                return "Variable no definida en la expreción : "+this.Exp.toString()+"\n";
            return "";
        }
        else
            return "La variable: "+this.Id+", no fue definida\n";
    }
    int i;
    public String byteCode(int i,Vector<Vector> TS){
        this.i= i ;
        String cadena=this.Exp.byteCode(this.i,TS);
        this.i = this.Exp.getI();
        int aux=-1;
        for(int j=0;j< TS.size();j++)
             if(TS.get(j).get(0).equals(this.Id)){
                 TS.get(j).set(TS.get(j).size()-1, true);
                 aux=j;
             }
        return cadena+(this.i++) + ": istore_"+aux+"\n\t\t\t";
    }
    public int getI(){
        return this.i;
    }
}


class NodoExpression{
    String Exp; 
    NodoExtExpression Exprecion;
    NodoExpression(String Exp,NodoExtExpression Exprecion){
        this.Exp=Exp;
        this.Exprecion=Exprecion;
    }
    public String toString(){
        if(this.Exprecion==null)
            return this.Exp+"\n";
        else
            return this.Exp+" "+this.Exprecion.toString()+" ";
    }
    public int check(Vector<Vector> TS, int tipo){
        int tipos=Tipo(TS);
        if(tipo!=2)
             tipos = tipo == tipos?tipo:tipos==-2 || tipo==-2 ?-2:-1;
        if(this.Exprecion!=null)
             tipos = (tipos == this.Exprecion.check(TS,tipos))?tipos:this.Exprecion.check(TS,tipos);
        return tipos;
    }
    public int Tipo(Vector<Vector> TS){
        if(isInteger(this.Exp))
            return 0;
        if("'true'".equals(this.Exp)){
            return 1;
        }
        for(int i=0;i<TS.size();i++)
            if(this.Exp.compareTo((String)TS.elementAt(i).elementAt(0))== 0)
                return "int".equals(TS.elementAt(i).elementAt(1))?0:1;
        return -2;
    }
    public boolean isInteger(String Token){
       Pattern pat = Pattern.compile("[0-9][0-9]*");
       Matcher mat = pat.matcher(Token);
       if(!mat.matches())
          return false;
       return true;
    }
    int i;
    public String byteCode(int i,Vector<Vector> TS){
        this.i=i;
        String cadena;
        if(this.Exprecion!=null){
            if(isInteger(Exp)){
                cadena=(this.i++) + ": bipush "+Exp+" \n\t\t\t";
            }
            else if("'true'".equals(Exp)){
                cadena=(this.i++) + ": bipush "+Exp+" \n\t\t\t";
            }
            else{
                int aux=-1;
                for(int j=0;j< TS.size();j++){
                    if(TS.elementAt(j).get(0).equals(this.Exp)){
                        if((boolean)TS.elementAt(j).get(2) == false){
                            cadena=(this.i++) + ": istore_"+j+" \n\t\t\t";
                            TS.get(j).set(2, true);
                        }
                        aux=j;
                    } 
                }
                cadena=+(this.i++) + ": iload_"+aux+" \n\t\t\t";
            }
            cadena+= this.Exprecion.byteCode(this.i,TS);
            this.i=this.Exprecion.getI();
        }else{
            if(isInteger(Exp)){
                cadena=(this.i++) + ": iconst_"+Exp+" \n\t\t\t";
            }
            else if("'true'".equals(Exp)){
                cadena=(this.i++) + ": zconst_1 \n\t\t\t";
            }
            else{
                int aux=-1;
                for(int j=0;j< TS.size();j++){
                    if(TS.elementAt(j).get(0).equals(this.Exp)){
                        if((boolean)TS.elementAt(j).get(2) == false){
                            cadena=(this.i++) + ": istore_"+j+" \n\t\t\t";
                            TS.get(j).set(2, true);
                        }
                        aux=j;
                    }  
                }
                cadena=+(this.i++) + ": iload_"+aux+" \n\t\t\t";
            }
        }
        return cadena; 
    }
    public int getI(){
        return this.i;
    }
}
class NodoExtExpression{
    String Operador; 
    String Exp; 
    NodoExtExpression Exprecion;
    NodoExtExpression(String Operador,String Exp,NodoExtExpression Exprecion){
        this.Operador=Operador;
        this.Exp=Exp;
        this.Exprecion=Exprecion;
    }
    public String toString(){
        if(this.Exprecion==null)
            return this.Operador+" "+this.Exp+"\n";
        else
            return this.Operador+" "+this.Exp+" "+this.Exprecion.toString()+" ";
    }
    public int check(Vector<Vector> TS, int tipo){
        int tipos=Tipo(TS);
        if(Operador.equals("=="))
            tipos = (tipo == tipos &&  tipos != -2 &&  tipos != -1)?1: tipos==-2 || tipo==-2?-2:-1;
        else
            tipos = (tipo == tipos &&  tipos != -2 )?tipo:tipos==-2|| tipo==-2?-2:-1;
        if(this.Exprecion!=null)
            tipos = (tipos == this.Exprecion.check(TS,tipos))?tipos:this.Exprecion.check(TS,tipos);
        return tipos;
    }
    public int Tipo(Vector<Vector> TS){
        if(isInteger(this.Exp)){
            return 0;
        }
        if("'true'".equals(this.Exp)){
            return 1;
        }
        for(int i=0;i<TS.size();i++){
            if(this.Exp.compareTo((String)TS.elementAt(i).elementAt(0))== 0){
                return "int".equals(TS.elementAt(i).elementAt(1))?0:1;
            }
        }
        return -2;
    }
    int i;
    public String byteCode(int i,Vector<Vector> TS){
        this.i=i;
        String cadena;
        if(this.Exprecion!=null){
            if(isInteger(Exp)){
                cadena=(this.i++) + ": bipush "+Exp+" \n\t\t\t";
                if(this.Operador.equals("+"))
                    cadena += (this.i++) + ": add  \n\t\t\t";
            }
            else if("'true'".equals(Exp)){
                cadena=(this.i++) + ": bipush "+Exp+" \n\t\t\t";
                if(this.Operador.equals("+"))
                    cadena += (this.i++) + ": add  \n\t\t\t";
            }
            else{
                int aux=-1;
                for(int j=0;j< TS.size();j++){
                    if(TS.elementAt(j).get(0).equals(this.Exp)){
                        if((boolean)TS.elementAt(j).get(2) == false){
                            cadena=(this.i++) + ": istore "+j+" \n\t\t\t";
                            TS.get(j).set(2, true);
                        }
                        aux=j;
                    } 
                  
                }
                cadena=+(this.i++) + ": iload_"+aux+" \n\t\t\t";
                if(this.Operador.equals("+"))
                    cadena += (this.i++) + ": add  \n\t\t\t";
        
            }
            cadena+= this.Exprecion.byteCode(this.i,TS);
            this.i=this.Exprecion.getI();
        }else{
            if(isInteger(Exp)){
                cadena=(this.i++) + ": iconst_"+Exp+" \n\t\t\t";
                if(this.Operador.equals("+"))
                    cadena += (this.i++) + ": add  \n\t\t\t";
            }
            else if("'true'".equals(Exp)){
                cadena=(this.i++) + ": zconst_1 \n\t\t\t";
                if(this.Operador.equals("+"))
                        cadena += (this.i++) + ": add  \n\t\t\t";
            }
            else{
                int aux=-1;
                for(int j=0;j< TS.size();j++){
                    if(TS.elementAt(j).get(0).equals(this.Exp)){
                        if((boolean)TS.elementAt(j).get(2) == false){
                            cadena=(this.i++) + ": istore "+j+" \n\t\t\t";
                            TS.get(j).set(2, true);
                        }
                        aux=j;
                    } 
                  
                }
                cadena=+(this.i++) + ": iload_"+aux+" \n\t\t\t";
                if(this.Operador.equals("+"))
                    cadena += (this.i++) + ": add  \n\t\t\t";
            }
        }
        return cadena; 
    }
    public int getI(){
        return this.i;
    }
    public boolean isInteger(String Token){
       Pattern pat = Pattern.compile("[0-9][0-9]*");
       Matcher mat = pat.matcher(Token);
       if(!mat.matches())
          return false;
       return true;
    }    
}