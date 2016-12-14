package Ayuda;

import static Ayuda.Funciones.f;

public class MetodosDerivacion {
   public static Double derivar(double x,double errorAceptado){
        double h=1.0;
        double resultado=0;
        double resultadoAnterior=Double.MAX_VALUE;
        for(int i=0;i<30;i++){
            h/=(i+1);
            resultado=(f(x+h)-f(x))/h;
            if(Math.abs(resultado-resultadoAnterior)<errorAceptado){
                return resultado;
            }
            resultadoAnterior=resultado;
        }
        return resultado;
    }  
}
