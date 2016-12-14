package Ayuda;

import static Ayuda.MetodosDerivacion.derivar;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Funciones {
     static List<String> Funcion;
 public static void convertirPosfija(String f)
    {
            Character aux=' ';
            Stack<Character> pila = new Stack<Character>();
            Funcion=new ArrayList<String>();
            String numero="";
            try{
                for(int i=0;i<f.length();i++)
                {
                        char c=f.charAt(i);
                        if(c=='(')
                        {
                                pila.add(c);
                        }else if(c==')')
                        {
                                while(true)
                                {
                                        aux = pila.lastElement();
                                        if(aux!='(')
                                        {
                                                Funcion.add(aux+"");
                                                pila.remove(pila.size()-1);
                                        }else
                                        {
                                                pila.remove(pila.size()-1);
                                                break;
                                        }
                                }
                        }else if(isOperador(c+""))
                        {
                                while(true)
                                {
                                        if(!pila.isEmpty())
                                        {
                                                aux=pila.lastElement();
                                        }
                                        if(pila.isEmpty() || ((c=='*' || c=='/') && (aux!='*' || aux!='/'))){
                                                pila.add(c);
                                                break;
                                        }else{
                                                Funcion.add(aux+"");
                                                pila.remove(pila.size()-1);
                                        }
                                }
                        }else{
                                numero+=c;
                                while((i+1)<f.length() && !isOperador(f.charAt(i+1)+"") && f.charAt(i+1)!='(' && f.charAt(i+1)!=')'){
                                        numero+=f.charAt(i+1);
                                        i++;
                                }
                                Funcion.add(numero+"");
                                numero="";
                        }
                }
                while(!pila.isEmpty())
                {
                        Funcion.add(pila.lastElement()+"");
                        pila.remove(pila.size()-1);
                }
            }catch(Exception E){
                JOptionPane.showMessageDialog(null,"Función Escrita Incorrectamente","Error",1);
            }
    }
    public static Double f(double x)
    {
            Stack<Double> pila = new Stack<Double>();
            double n1,n2;
            for(int i=0;i<Funcion.size();i++)
            {
                    String c = Funcion.get(i);
                    if(isOperador(c))
                    {
                            char operador=c.charAt(0);
                            n2=pila.lastElement();
                            pila.remove(pila.size()-1);
                            n1=pila.lastElement();
                            pila.remove(pila.size()-1);
                            switch(operador)
                            {
                                    case '+':
                                            pila.add(n1+n2);
                                            break;
                                    case '-':
                                            pila.add(n1-n2);
                                            break;
                                    case '*':
                                            pila.add(n1*n2);
                                            break;
                                    case '/':
                                            pila.add(n1/n2);
                                            break;
                            }
                    }else{
                            if(c.equalsIgnoreCase("x")){
                                    pila.add(x);
                            }else{
                                    pila.add(Double.parseDouble(c));
                            }
                    }
            }
            return pila.lastElement();
    }
    private static boolean isOperador(String c)
    {
        return c.equalsIgnoreCase("+") || c.equalsIgnoreCase("-") || c.equalsIgnoreCase("*") || c.equalsIgnoreCase("/");
    }
     public static Double g(double x){
      return  f(x)+x;
    }
    public static Double df(double x){
      return  derivar(x,0.00001);
    }
   
   
  
}
