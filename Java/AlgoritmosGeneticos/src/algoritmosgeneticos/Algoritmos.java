/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosgeneticos;

import algoritmosgeneticos.Cromosomas;
import java.util.Random;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JTextArea;

public class Algoritmos {
    Poblaciones poblacionInicial;
    Cromosomas cromosoma;
    Cruza cruza;
    Seleccion seleccion;
    Mutacion mutacion;
    static String funcion;
    static int Base=1;
    int TamanioCromosoma;
    int TamanioGen;
    Poblaciones []Generaciones;
    public Algoritmos(int TotalPoblacionInicial,int TamanioCromosoma,int TamanioGen){
        setValoresCromosomas(TamanioCromosoma,TamanioGen);
        poblacionInicial = new Poblaciones(TotalPoblacionInicial);
        getPoblacionInicial();
    }
    public void getPoblacionInicial(){
        for (int i = 0; i < poblacionInicial.TotalPoblacion; i++) {
            Cromosomas cromosoma = new Cromosomas(TamanioCromosoma,TamanioGen);
            for (int j = 0; j < Cromosomas.TamanioCromosoma; j++){
                cromosoma.addAlelo(Random(0,Algoritmos.Base));
            }
            poblacionInicial.addCromosoma(cromosoma);
        }
    }
    public void setFuncion(String funcion){
        Algoritmos.funcion=funcion;
    }
    public void setBase(int base){
        this.Base=base;
    }
    public void setValoresCromosomas(int TamanioCromosoma,int TamanioGen){
        Cromosomas.TamanioCromosoma=TamanioCromosoma;
        Cromosomas.TamanioGen=TamanioGen;
        this.TamanioCromosoma=TamanioCromosoma;
        this.TamanioGen=TamanioGen;
    }
    public Poblaciones hacerAlgoritmo( int selectSeleccion,
                                int selectCruza,
                                double porcentajeCruza,
                                int selectMutacion,
                                double porcentajeMutacion,
                                int generaciones,
                                JTextArea Imprimir){
        String Cadena="";
        Generaciones= new Poblaciones[generaciones+1];
        Generaciones[0]=poblacionInicial;
        Cadena=imprimirPoblacion(poblacionInicial);
        Poblaciones Generacion = new Poblaciones(poblacionInicial.TotalPoblacion);
        for (int i = 0; i < generaciones ; i++) {
            Generacion= new Poblaciones(poblacionInicial.TotalPoblacion);
            for (int j = 0; j < poblacionInicial.TotalPoblacion; j++) {
                if(selectSeleccion==1)
                    seleccion = new SeleccionPorTorneo();
                else if(selectSeleccion==2){
                    seleccion = new SeleccionPorRuleta();
                }
                Cromosomas padre =seleccion.getSeleccionado(poblacionInicial);
                Cromosomas madre =seleccion.getSeleccionado(poblacionInicial);
                Random r= new Random();
                while(porcentajeCruza>r.nextDouble()){
                    padre =seleccion.getSeleccionado(poblacionInicial);
                    madre =seleccion.getSeleccionado(poblacionInicial);
                    while(madre==padre){
                        madre =seleccion.getSeleccionado(poblacionInicial);
                    }    
                }
                Cadena+= "Padre ="+padre.imprimirCromosoma()+"\n";
                Cadena+= "Madre ="+madre.imprimirCromosoma()+"\n";
                //System.out.println("Padre ="+padre.imprimirCromosoma());
                //System.out.println("Madre ="+madre.imprimirCromosoma());
                if(selectCruza==1)
                    cruza= new CruzaDeUnPunto();
                else if(selectCruza==2){
                    cruza= new CruzaDeDosPuntos();
                }else if(selectCruza==3){
                    cruza= new CruzaUniforme();
                }        
                Cromosomas hijo =cruza.obtenerCruza(padre, madre);
                Cadena+= "Hijo ="+hijo.imprimirCromosoma()+"\n";              
                //  System.out.println("Hijo = "+hijo.imprimirCromosoma());
                if (porcentajeMutacion>r.nextDouble()) {
                    if (selectMutacion==1) {
                        mutacion = new MutacionPorInsercion();
                    }else{
                        mutacion = new MutacionPorDesplazamiento();
                    }
                    Cromosomas hijoMutado = mutacion.getMutado(hijo);    
                    Cadena+= "Hijo Mutado ="+hijoMutado.imprimirCromosoma()+"\n";              
                    Generacion.addCromosoma(hijoMutado);
                }else{
                    Generacion.addCromosoma(hijo);
                }
                //System.out.println("Muta = "+hijoMutado.imprimirCromosoma());
            }
            Generaciones[i+1]=Generacion;
            //System.out.println("Generacion = "+(i+1));
            Cadena+= imprimirPoblacion(Generacion);
            Imprimir.setText(Cadena); 
        }
        return Generacion;
    }
    public static String imprimirPoblacion(Poblaciones poblacion){
        String Cadena = ""; 
        for (int i = 0; i < poblacion.TotalPoblacion; i++) {
            Cadena+="[  ";
            Cadena+=poblacion.Poblacion[i].imprimirCromosoma();
            Cadena+="  ]     ";
            Cadena+="Aptitud: "+poblacion.Poblacion[i].getAptitud()+" \t\n";
        }
        return Cadena+" \t\n";
    }
    public static long ArrayToInt(int []gen){
        StringBuffer cadena = new StringBuffer();
        for (int x=0;x<gen.length;x++){
           cadena =cadena.append(gen[x]);
        }
        return Long.parseLong(cadena.toString());
    }
    public static long binarioDecimal(long numero){
      long exponente = 0;
      long digito = 0; 
      long decimal = 0; 
      while (numero != 0) {
                digito = numero % 10;
                decimal = decimal + digito * (int) Math.pow(2, exponente);
                exponente++;
                numero = numero / 10;
      }
        return decimal;
    }
    public static int Random(int del,int hasta){
        return del+(new Random().nextInt((hasta-del)+1)); 
    }
    public static int f(int []var) {
        String variables[] = new String[4];
        variables[0] = "x";
        variables[1] = "y";
        variables[2] = "z";
        variables[3] = "w";
        try{
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("JavaScript");
            for (int i = 0; i < var.length && i <variables.length; i++) {
                engine.put(variables[i], var[i]);
            }
            engine.eval("var aptitud = "+Algoritmos.funcion);
            Object obj = engine.get("aptitud");
            return (int)Double.parseDouble(obj.toString()+"");
        }catch(Exception e){
            System.err.println(e);
        }
        return 0;
    }    
}


