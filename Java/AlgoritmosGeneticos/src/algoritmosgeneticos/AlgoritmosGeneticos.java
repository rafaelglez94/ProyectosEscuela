package algoritmosgeneticos;
import algoritmos.Ayuda;
import algoritmos.Main;
import java.util.*;
import java.io.*;
import javax.script.*;

public class AlgoritmosGeneticos {
    public static void main(String[] args) {
            //Parametros Int
            //Seleccion 
            //1.-Por Torneo
            //2.-Por Ruleta
            //Cruza 
            // 1.- Un Punto
            // 2.- Dos Puntos
            // 3.- Uniforme
            // Mutacion
            //1.- MutacionInsercion
            //2.- Desplazamiento
           new Main();
           //A.hacerAlgoritmo(1,3,0.01,1,0.1,4);
    }
}

abstract class Cruza{
    public abstract Cromosomas obtenerCruza(Cromosomas padre,Cromosomas madre);
}
abstract class Seleccion{
    public abstract Cromosomas getSeleccionado(Poblaciones Poblacion);
}
abstract class Mutacion{
    public abstract Cromosomas getMutado(Cromosomas Cromosoma);
}
class CruzaDeUnPunto extends Cruza{
    int puntoDeCruza;
    public int getPuntoCruza(){
        return puntoDeCruza;
    }
    public  Cromosomas obtenerCruza(Cromosomas padre,Cromosomas madre){
        puntoDeCruza= Cromosomas.TamanioCromosoma/2;
        Cromosomas hijo= new Cromosomas(Cromosomas.TamanioCromosoma,Cromosomas.TamanioGen);
        for (int i = 0; i < Cromosomas.TamanioCromosoma; i++) {
            if(i<puntoDeCruza)
                hijo.addAlelo(padre.Alelos[i]);
            else
                hijo.addAlelo(madre.Alelos[i]);
        }
        return hijo;
    }
}
class CruzaDeDosPuntos extends Cruza{
    int puntosDeCruza[];
    public int[] getPuntosCruza(){
        return puntosDeCruza;
    }
    public  Cromosomas obtenerCruza(Cromosomas padre,Cromosomas madre){
        int puntoMedio = Cromosomas.TamanioCromosoma/2;
        puntosDeCruza = new int[2];
        puntosDeCruza[0] = puntoMedio/2;
        puntosDeCruza[1] = puntoMedio+(puntoMedio/2);  
        Cromosomas hijo = new Cromosomas(Cromosomas.TamanioCromosoma,Cromosomas.TamanioGen);
        boolean cruza=true;
        int punto=0;
        for (int i = 0; i < Cromosomas.TamanioCromosoma; i++) {
            if(cruza){
                hijo.addAlelo(padre.Alelos[i]);
            }else{
                hijo.addAlelo(madre.Alelos[i]);
            }
            if(punto < puntosDeCruza.length && puntosDeCruza[punto] == i+1) {
                cruza = !cruza;
                punto++;
            }
        }
        return hijo;
    }
}

class CruzaUniforme extends Cruza{
    boolean []Pc;
    public boolean[] getPuntosCruza(){
        return Pc;
    }
    public Cromosomas obtenerCruza(Cromosomas padre, Cromosomas madre) {
        Pc = new boolean[Cromosomas.TamanioCromosoma];
        Cromosomas hijo= new Cromosomas(Cromosomas.TamanioCromosoma,Cromosomas.TamanioGen);
        puntosDeCruce();
        for (int i = 0; i < Cromosomas.TamanioCromosoma; i++) {
          //  System.out.print(Pc[i]+" ");
            if (Pc[i]){ 
                hijo.addAlelo(padre.Alelos[i]);
            }else{
                hijo.addAlelo(madre.Alelos[i]);
            }    
        }
        return hijo;
    }
    public void puntosDeCruce(){
        int mitad=Cromosomas.TamanioCromosoma;
        if(mitad%2!=0)
           mitad++;
        for (int i = 0; i < mitad; i++) {
            Random r= new Random();
               Pc[i] = r.nextInt(2)==0?false:true;
        }
        if(mitad%2!=0)
           mitad--;
        int j=0;
        for (int i = Cromosomas.TamanioCromosoma-1; i >= mitad; i--) {
            Pc[i] = Pc[j++];
        }
    }
}
class SeleccionPorRuleta extends Seleccion{
    double T;
    int N;
    double r;
    double f=0; 
    double sumaAptitud=0;
    Poblaciones Poblacion;
    double []Ve;
    int Ganador=-1;
    Cromosomas GanadorTorneo;
    public Cromosomas getGanador(){
        return GanadorTorneo;
    }
    public double getSumaAptitud(){
        return sumaAptitud;
    }
    public double[] getVe(){
        return Ve;
    }
    public double f(){
        return f;
    }
    public Cromosomas getSeleccionado(Poblaciones Poblacion){
        Ganador=-1;
        this.Poblacion=Poblacion;
        Ve= new double[Poblacion.Poblacion.length];
        r= r();
        if (sumaAptitud==0) {
            for (int i = 0; i < Poblacion.TotalPoblacion; i++) {
                sumaAptitud+=Poblacion.Poblacion[i].getAptitud();
            }
        }
        f = sumaAptitud/Poblacion.TotalPoblacion;
        double sumaVe=0;
        for (int i = 0; i < Poblacion.TotalPoblacion; i++) {
           
            Ve[i]=Poblacion.Poblacion[i].getAptitud()/f;
            sumaVe+=Ve[i];
            
            if(sumaVe>=r && Ganador==-1){
                Ganador=i;
            } 
        }
        GanadorTorneo =Poblacion.Poblacion[Ganador];
        return GanadorTorneo;
    }
    public double ValorEsperado(int Aptitud){
        return Aptitud/f;
    }
    public double r(){
        Random r= new Random();
        return r.nextDouble()*Poblacion.TotalPoblacion;
    }
}

class SeleccionPorTorneo extends Seleccion{
    int e[];
    Poblaciones Poblacion;
    Cromosomas Competidor1;
    Cromosomas Competidor2;
    public Cromosomas getCompetidor1(){
        return Competidor1;
    }
    public Cromosomas getCompetidor2(){
        return Competidor2;
    }
    public Cromosomas getSeleccionado(Poblaciones Poblacion) {
        List<Cromosomas> Individuos = new ArrayList<Cromosomas>();
        this.Poblacion = new Poblaciones(Poblacion.Poblacion.length); 
        Cromosomas []Ind= new Cromosomas[Cromosomas.TamanioCromosoma];
        Individuos = Arrays.asList(Poblacion.Poblacion);
        Collections.shuffle(Individuos);
        //System.out.println("Revuelta");
        Ind = Individuos.toArray(Ind);
        this.Poblacion.setCromosomas(Ind);
        //Algoritmos.imprimirPoblacion(this.Poblacion);
        Competidor1 = getRandomCromosoma();
        Competidor2 = getRandomCromosoma();
        while(Competidor1==Competidor2){
            Competidor2 = getRandomCromosoma();
        }    
     //   System.out.println("Aptitud Competidor 1 = "+Competidor1.getAptitud());
       // System.out.println("Aptitud Competidor 2 = "+Competidor2.getAptitud());
        return Competidor1.Aptitud > Competidor2.Aptitud ? Competidor1 : Competidor2;
    }
    public Cromosomas getRandomCromosoma(){
        Random r= new Random();
        return this.Poblacion.Poblacion[r.nextInt(Poblacion.TotalPoblacion)];
    }
}

class MutacionPorInsercion extends Mutacion{
    Cromosomas Cromosoma;
    int de;
    int a;
    public int getDe(){
        return de;
    }
    public int getA(){
        return a;
    }
    public Cromosomas getMutado(Cromosomas Cromosoma) {
        this.Cromosoma=Cromosoma;
        de = getRandomPosicion();
        a = getRandomPosicion();
        while(a==de){
            a = getRandomPosicion();
        }
        Cromosomas CromosomaMutado = new Cromosomas(Cromosomas.TamanioCromosoma,Cromosomas.TamanioGen);
        for (int i = 0; i < Cromosomas.TamanioCromosoma; i++) {
            if(i==de){
                CromosomaMutado.addAlelo(Cromosoma.Alelos[a]);
            }else if(i==a){
                CromosomaMutado.addAlelo(Cromosoma.Alelos[de]);
            }else{
                CromosomaMutado.addAlelo(Cromosoma.Alelos[i]);
            }
        }
        return CromosomaMutado;
    }
    public int getRandomPosicion(){
        Random r= new Random();
        return r.nextInt(Cromosomas.TamanioCromosoma);
    }
}
class MutacionPorDesplazamiento extends Mutacion{
    Cromosomas Cromosoma;
    int [][]DeA;
    public int[][] getDeA(){
        return DeA;
    }
    public Cromosomas getMutado(Cromosomas Cromosoma) {
        DeA = getDesplazamientos(3);
        Cromosomas hijoMutado= new Cromosomas(Cromosomas.TamanioCromosoma,Cromosomas.TamanioGen);
        int []ordenCromosoma= new int[Cromosomas.TamanioCromosoma];
        for (int j = 0; j <DeA.length; j++) {
            ordenCromosoma[DeA[j][1]]=DeA[j][0];
        }
        int pendientes[]= new int[ordenCromosoma.length];
        int inc=-1;
        for (int i = 0; i < ordenCromosoma.length; i++) {
            boolean esta=false;
            for (int j = 0; j < DeA.length; j++) {
               if (i==DeA[j][1]){
                   esta=true;
               }
            }
            if (!esta) {
               if (inc!=-1) {
                   ordenCromosoma[i]=pendientes[inc--];
               }else
                   ordenCromosoma[i]=i;
            }else{
               inc++;
               pendientes[inc]=i;
            }
        }
        for (int i = 0; i < ordenCromosoma.length; i++) 
            hijoMutado.addAlelo(Cromosoma.Alelos[ordenCromosoma[i]]);
        return hijoMutado;
    }
    public int[][] getDesplazamientos(int Desplazamientos){
        int [][]DeA= new int[Desplazamientos][2]; 
        Random r= new Random();
        for (int i = 0; i < Desplazamientos; i++) {
            for (int j = 0; j < 2; j++) {
                while(true){
                    boolean repetida=false;
                    DeA[i][j]=Algoritmos.Random(0,Cromosomas.TamanioCromosoma-1);
                    for (int k = 0; k <= i; k++) {
                       for (int l = 0; l < 2; l++) {
                           if(!(i==k && j==l) && DeA[i][j]==DeA[k][l]){
                               repetida=true;
                           }
                       }
                   }
                   if (!repetida) {
                        break;
                   }
               }   
            }
        }
        return DeA;
    }
    
}