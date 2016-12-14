
package Ayuda;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MetodosRaices {
   private static final String[] encabezadoSecante={"i", "Xi", "Xi+1", "f(Xi)", "f(xi+1)", "e"};
   public static Double biseccion(double a,double b,double errorAceptado,int iteracionesMaximas,JTable tblresultado){
                tblresultado.setModel(new DefaultTableModel(new Object[0][0],encabezadoBiseccion));
                DefaultTableModel model = (DefaultTableModel)tblresultado.getModel();
                model.getDataVector().removeAllElements();
                
		double resultado=0,resultadoAnterior=0,error;
                for(int i=1;i<=iteracionesMaximas;i++){
			resultado=(a+b)/2;
			error=Math.abs((resultado-resultadoAnterior))/Math.abs(resultado*100);
			model.addRow(new Object[]{i,a,b,(b-a),resultado,Funciones.f(a),Funciones.f(resultado),error});
                        if(Funciones.f(resultado)==0)
                            return resultado;
			if(error<=errorAceptado)
                            return resultado;
			resultadoAnterior=resultado;
			if(Funciones.f(a)*Funciones.f(resultado)<0)
                            b=resultado;
			else
                            a=resultado;
		}	
		return resultado;
	} 
   private static final String[] encabezadoBiseccion={"i", "X1", "X2", "X2-X1", "Xm", "f(X1)", "F(Xm)", "error"};
   public static Double secante(double a,double b,double errorAceptado,int iteracionesMaximas,JTable tblresultado){
            tblresultado.setModel(new DefaultTableModel(new Object[0][0],encabezadoSecante));
            DefaultTableModel model = (DefaultTableModel)tblresultado.getModel();
            model.getDataVector().removeAllElements();
             double c,d,resultado=0,error;

             c=Funciones.f(a);
             d=Funciones.f(b);

             for(int i=1;i<=iteracionesMaximas;i++){
                     resultado=b-((d*(b-a))/(d-c));
                     error=Math.abs(resultado-b);

                     model.addRow(new Object[]{i,b,resultado,Funciones.f(b),Funciones.f(resultado),error});

                     if(error<errorAceptado){
                             return resultado;
                     }

                     a=b;
                     b=resultado;
                     c=d;
                     d=Funciones.f(resultado);
             }	
             return resultado;
     }
    private static final String[] encabezadoFalsaPosicion={"i", "Xi", "f(Xi)", "Xi+1", "f(xi+1)","e"};
    public static Double falsaPosicion(double a,double b,double errorAceptado,int iteracionesMaximas,JTable tblresultado){
                tblresultado.setModel(new DefaultTableModel(new Object[0][0],encabezadoFalsaPosicion));
                DefaultTableModel model = (DefaultTableModel)tblresultado.getModel();
                model.getDataVector().removeAllElements();
		double c,d,resultado=0,resultadoAnterior,error;
              
                resultadoAnterior=0;
		c=Funciones.f(a);
		d=Funciones.f(b);
		
		for(int i=1;i<=iteracionesMaximas;i++){
			resultado=b-((d*(b-a))/(d-c));
                        error=Math.abs(resultado-resultadoAnterior);
			
			model.addRow(new Object[]{i,b,Funciones.f(b),resultado,Funciones.f(resultado),error});
			
			if(c*Funciones.f(resultado)<0){
                            b = resultado;
			}else if(c*Funciones.f(resultado)>0){
                            a = resultado;
                        }
                        if(c*Funciones.f(resultado)==0 || error<=errorAceptado){
                            return resultado;
                        }
                        c=Funciones.f(a);
                        d=Funciones.f(b);
                        resultadoAnterior=resultado;
		}	
		return resultado;
	}
    private static final String[] encabezadoNewtonRaphson={"i", "Xi", "Xi+1", "f(Xi)", "f(xi+1)", "e"};
    public static Double newtonRaphson(double a,double errorAceptado,int iteraciones,JTable tblresultado){
        tblresultado.setModel(new DefaultTableModel(new Object[0][0],encabezadoNewtonRaphson));
        DefaultTableModel model = (DefaultTableModel)tblresultado.getModel();
        model.getDataVector().removeAllElements();
        double error,resultado=0;

        for(int i=1;i<=iteraciones;i++){
            resultado = a-(Funciones.f(a)/Funciones.df(a));
            error=Math.abs(a-resultado);

            model.addRow(new Object[]{i,a,resultado,Funciones.f(a),Funciones.f(resultado),error});

            if(error<=errorAceptado){
                return resultado;
            }

            a=resultado;
        }
        return resultado;
    }
    private static final String[] encabezadoAproximaciones={"i", "Xi", "Xi+1", "f(Xi)", "f(xi+1)", "e"};
     public static Double aproximacionesSucesivas(double a,double errorAceptado,int iteraciones,JTable tblresultado){
        tblresultado.setModel(new DefaultTableModel(new Object[0][0],encabezadoAproximaciones));
        DefaultTableModel model = (DefaultTableModel)tblresultado.getModel();
        model.getDataVector().removeAllElements();

            double error,resultado=0;
            for(int i=1;i<=iteraciones;i++){
                resultado = Funciones.g(a);
                error=Math.abs(a-resultado);

                model.addRow(new Object[]{i,a,resultado,Funciones.f(a),Funciones.f(resultado),error});

                if(error<=errorAceptado){
                    return resultado;
                }

                a=resultado;
            }
            return resultado;
        }
}
