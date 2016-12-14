/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosgeneticos;

public class Poblaciones{
    public Cromosomas []Poblacion;
    int TotalPoblacion;
    int i=0;
    int SumaAptitud=0;
    Poblaciones(int TotalPoblacion){
        this.TotalPoblacion=TotalPoblacion;
        Poblacion = new Cromosomas[TotalPoblacion];
    }
    public void setCromosomas(Cromosomas[] Poblacion){
        this.Poblacion = Poblacion;
    }
    public void addCromosoma(Cromosomas cromosoma){
        Poblacion[i++] = cromosoma;
    }
}
