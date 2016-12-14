
package Ayuda;

public class MetodosIntegracion {
     public static Double metodoTrapecio(double a,double b,int n){
        double area;
        double h=(b-a)/(n-1);
        double x=a;
        double[] resultados=new double[n];
        for(int i=0;x<=b;i++){
            resultados[i]=Funciones.f(x);
            x=x+h;
        }
        area=resultados[0]+resultados[n-1];
        for(int i=1;i<n-1;i++)
            area+=2*resultados[i];
        return area*(h/2);     
     }
      public static Double metodoSimpson(double a,double b, int n){
            double h=(b-a)/n;
            double suma=0;
            double xi;
            double f=1;
            for(int i=0;i<n+1;i++){
                    xi=a+i*h;
                    if(suma!=0){
                            if(i!=n){
                                    if(i%2==0){
                                            f=2*Funciones.f(xi);
                                    }else{
                                            f=4*Funciones.f(xi);
                                    }
                            }
                    }
                    suma+=f;
            }
            return suma*h/3;
        }
}
