/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosgeneticos;
import algoritmosgeneticos.Algoritmos;
/**
 *
 * @author Rafael
 */
public class Cromosomas {
    int Alelos[];
    int Genes[][];
    int Fenotipos[];
    int Aptitud=-1;
    static int TotalGenes;
    static int TamanioGen;
    static int  TamanioCromosoma;
    int i=0;
    Cromosomas(){}
    Cromosomas(int TamanioCromosoma,int TamanioGen){
        Cromosomas.TamanioCromosoma=TamanioCromosoma;
        Alelos= new int[Cromosomas.TamanioCromosoma];
        Cromosomas.TotalGenes = Cromosomas.TamanioCromosoma/Cromosomas.TamanioGen;
        Genes = new int[Cromosomas.TotalGenes][TamanioGen];
        Fenotipos = new int[Cromosomas.TotalGenes];  
    }
    public void addAlelo(int Alelo){
        Alelos[i++] = Alelo;
    }
    public int getAptitud(){
        if(this.Aptitud!=-1)
            return this.Aptitud;
        for (int i = 0; i < TotalGenes; i++) {
            for (int j = 0; j < TamanioGen; j++) {
                Genes[i][j] = Alelos[(i*TotalGenes)+j];
            }
            if(Algoritmos.Base==2) 
                Fenotipos[i]=(int)Algoritmos.binarioDecimal(Algoritmos.ArrayToInt(Genes[i]));      
            else    
                Fenotipos[i]=(int)(Algoritmos.ArrayToInt(Genes[i]));
         }    
        return Algoritmos.f(Fenotipos);
    }
    
    public String imprimirCromosoma(){
        String Cadena = "";
        for (int j = 0; j <Alelos.length ; j++) {
            Cadena+= Alelos[j]+"  ";
        }
        return Cadena;
    }
}
