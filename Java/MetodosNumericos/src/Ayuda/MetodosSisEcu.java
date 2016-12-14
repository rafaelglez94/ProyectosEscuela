
package Ayuda;

public class MetodosSisEcu {
    public static Double[][] b;
    public static void copiarMatriz(Double a[][]){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                b[i][j]=a[i][j];
            }
        }
    }
    
    public static void copiarMatriz(Double a[][],Double copia[][]){
        for(int i=0;i<a.length;i++)
            for(int j=0;j<a[0].length;j++)
                copia[i][j]=a[i][j];
    }
    public static Double  Determinante(Double a[][]){
        b = new Double[a.length][a.length+1];
        double tem;
        double det=1;
        copiarMatriz(a);
        for(int i=0;i<b.length-1;i++){
            if(b[i][i]!=1){
                tem=b[i][i];
                det*=tem;
                for(int k=0;k<b[0].length;k++){
                    if(tem==0){
                        return 0.0;
                    }
                    b[i][k]/=tem;
                }
            }
            for(int j=i+1;j<b.length;j++){
                if(b[j][i]!=0){
                    tem=b[j][i];
                    for(int k=0;k<b[0].length;k++){
                        b[j][k]-=b[i][k]*tem;
                    }
                }
            }
        }
        for(int i=0;i<b.length;i++){
                det*=b[i][i];
        }
        return det;
    }
    public static Double[][] gauss(Double[][] matriz){
        b = new Double[matriz.length][matriz.length+1];
        copiarMatriz(matriz);
        double pivote;
        double temp;
        for(int i=0;i<b.length;i++){
            pivote=b[i][i];
            for(int j=i+1;j<b.length;j++){
                temp=b[j][i]/pivote;
                for(int k=i;k<b[i].length;k++){
                    b[j][k]-=b[i][k]*temp;
                }
            }
        }
        return b; 
    }
    public static Double[][] gaussJordan(Double[][] a){
        b = new Double[a.length][a.length+1];
        copiarMatriz(a);
        double pivote;
        double temp;
        for(int i=0;i<b.length;i++){
            pivote=b[i][i];
            for(int j=i;j<b[i].length;j++){
                b[i][j]/=pivote;
            }
            for(int j=0;j<b.length;j++){
                if(i==j){
                    continue;
                }
                temp=b[j][i];
                for(int k=i;k<b[i].length;k++){
                    b[j][k]-=b[i][k]*temp;
                }
            }
        }
        return b; 
    }
    public static Double[][] montante(Double[][] a){
        b = new Double[a.length][a.length+1];
        copiarMatriz(a);
        double pivote;
        Double[][] copia = new Double[b.length][b.length+1];
        double pivoteAnterior=1;
        for(int i=0;i<b.length;i++){
            copiarMatriz(b,copia);
            pivote=b[i][i];
            for(int j=0;j<b.length;j++){
                if(j==i){
                    continue;
                }
               for(int k=0;k<=b.length;k++){
                       b[j][k]=((pivote*copia[j][k])-(copia[j][i]*copia[i][k]))/pivoteAnterior;
               }
            }
            pivoteAnterior=pivote;
        }
        return b;
    }
    public static Double[] cramer(Double[][] a){    
        b = new Double[a.length][a[0].length];
        copiarMatriz(a);
        Double[][] copia = new Double[b.length][b[0].length];
        Double[] determinantes = new Double[b[0].length];
        Double resultado[] = new Double[b.length];
        determinantes[0]=Determinante(b);
        copiarMatriz(a); 
        for(int i=1;i<=b.length;i++){
            copiarMatriz(a); 
            copiarMatriz(b,copia);
            for(int j=0;j<b.length;j++){
                copia[j][i-1]=b[j][b.length];
            }
            determinantes[i]=Determinante(copia);
        }
        for(int i=0;i<b.length;i++){
            resultado[i]=determinantes[i+1]/determinantes[0];
        }
        return resultado;
    }
    public static void imprimir(Double[][] a){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                System.out.print(a[i][j]+"\t");
            }
            System.out.println();
        }
    }
    public static Double[] jacobi(Double[][] a,Double errorAceptado){
            b = new Double[a.length][a[0].length];
            copiarMatriz(a);
            int c=0;
            boolean flag=true;
            Double[] resultados = new Double[b.length];
            Double[] resultadosAnteriores = new Double[b.length];
            for(int i=0;i<resultados.length;i++){
                 resultadosAnteriores[i]=0d;
                 resultados[i]=0d;//valores de igualacion
            }
            while(flag && c<100){
                c++;
                flag=false;
                for(int i=0;i<b.length;i++){
                    resultados[i]=b[i][b[0].length-1];
                    for(int j=0;j<b.length;j++)
                        if(i!=j)
                            resultados[i]-=b[i][j]*resultadosAnteriores[j]; 
                    resultados[i]/=b[i][i];
                }
                for(int j=0;j<resultados.length;j++)
                    if(Math.abs(resultados[j]-resultadosAnteriores[j])>=errorAceptado)
                        flag=true;
                for(int i=0;i<resultados.length;i++)
                    resultadosAnteriores[i]=resultados[i];
            }
            return resultados;
        }
    public static Double[] gaussSeidel(Double[][] a,double errorAceptado){
            b = new Double[a.length][a[0].length];
            copiarMatriz(a);
            int c=0;
            boolean flag=true;
            Double[] resultados = new Double[b.length];
            Double[] resultadosAnteriores = new Double[b.length];
            for(int i=0;i<resultados.length;i++){
                 resultadosAnteriores[i]=0d;
                 resultados[i]=0d;//valores de igualacion
            }
            while(flag && c<100){
                c++;
                flag=false;
                for(int i=0;i<b.length;i++){
                    resultados[i]=b[i][b[0].length-1];
                    for(int j=0;j<b.length;j++)
                        if(i!=j)
                            resultados[i]-=b[i][j]*resultadosAnteriores[j]; 
                    resultados[i]/=b[i][i];
                }
                for(int j=0;j<resultados.length;j++)
                    if(Math.abs(resultados[j]-resultadosAnteriores[j])>=errorAceptado)
                        flag=true;
                for(int i=0;i<resultados.length;i++)
                    resultadosAnteriores[i]=resultados[i];
            }
            return resultados;
        }
}
