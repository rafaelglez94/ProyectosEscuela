
package Ayuda;


import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

public class Grafica extends ChartPanel
{
	/**
         * crea la grafica en base a la raiz dada, con el titulo dado.
         * @param raiz raiz.
         * @param t titulo de la grafica.
         */
	public Grafica(double raiz,String t) 
	{
		super(null);
                XYSeries serie1 = new XYSeries("Funcion");
		for(double i=raiz-10;i<raiz+10;i+=.1){
			serie1.add(i,Funciones.f(i));
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serie1);
		setChart(ChartFactory.createXYLineChart(t,"x","y",dataset,PlotOrientation.VERTICAL,true,true,false));
	}
}