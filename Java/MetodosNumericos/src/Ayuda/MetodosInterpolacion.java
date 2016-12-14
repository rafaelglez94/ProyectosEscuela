
package Ayuda;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MetodosInterpolacion {
    public static Double interpolacionNewton(Double x,Double[][] puntos,TableModel model){
        generarTablaDiferencias(puntos, model);
        double k=(x-puntos[0][0])/(puntos[1][0]-puntos[0][0]);
        double y=0;
        for(int i=0;i<model.getRowCount()-1;i++){
            double num=1;
            for(int j=0;j<i;j++){
                num*=(k-j);
            }
            y+=num/factorial(i)*(Double)model.getValueAt(0,1+i);
        }
        return y;
    } 
    private static double factorial(int x){
        return (x==1 || x==0) ? 1:factorial(x-1)*x;
    }
    private static void generarTablaDiferencias(Double[][] puntos,TableModel model){
        for(int i=0;i<puntos.length;i++)
            for(int j=0;j<2;j++)
                model.setValueAt(puntos[i][j],i,j);
        for(int j=2;j<model.getColumnCount();j++)
            for(int i=0;i<model.getRowCount()-(j-1);i++)
                model.setValueAt((Double)model.getValueAt(i+1,j-1)-(Double)model.getValueAt(i,j-1),i,j);
    } 
     public static Double lagrange(Double x, Double[][] puntos){
        double y=0;
        for(int i=0;i<puntos.length;i++){
            double numerador=1;
            double denominador=1;
            for(int j=0;j<puntos.length;j++){
                if(i!=j){
                    numerador*=x-puntos[j][0];
                    denominador*=puntos[i][0]-puntos[j][0];
                }
            }
            y+=numerador/denominador*puntos[i][1];
        }
        return y;
     }
    public static Double minimosCuadrados(int m,Double x, Double[][] puntos,JTable tabla){
        
          int n=puntos.length;
        Double a[]= new Double[m*2+1];
        Double b[]= new Double[m+1];
        Double matriz[][]= new Double[m+1][m+2]; 
        a[0]=Double.parseDouble(""+n);//n
        b[0]=0.0d;
        for(int j=1;j<m*2+1;j++){
            a[j]=0d;
            if(j<m+1)
                b[j]=0d;                 
        }
        for(int i=0;i<n;i++){
            b[0]+=puntos[i][1];//suma de y
            for(int j=1;j<m*2+1;j++) 
                a[j]+=pot(puntos[i][0],j);
            for(int j=1;j<m+1;j++)
                    b[j]+=pot(puntos[i][0],j)*puntos[i][1];                 
        }     
        for(int i=0;i<m+1;i++){
            for(int j=0;j<m+1;j++)   
                matriz[i][j]=a[i+j]; 
            matriz[i][matriz[i].length-1]=b[i];
        }
        String columnas[]= new String[matriz.length];
        for(int i=0;i<columnas.length;i++)
            columnas[i]="";
        tabla.setModel(new DefaultTableModel(matriz,columnas));
                
        Double[][] ResultadosM=MetodosSisEcu.gaussJordan(matriz);
        Double total=0d;
        for(int i=0;i<ResultadosM.length;i++)
            total+=(ResultadosM[i][ResultadosM.length]/ResultadosM[i][i])*pot(x,i);
        return total;
     }
     public static Double pot(Double valor, int P){
         return Math.pow(valor, P);
     }
}
