
package Ayuda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Matriz extends JDialog implements KeyListener{
    public int T;
    JTable tabla;
    Double[][]M;
    String columnas[];
    JTextField[][]txtM;
    boolean bandera;
    public Matriz(final int T,final JTable tabla){
            this.tabla=(JTable)tabla;
            this.T=T;
            M= new Double[T][T+1];
            columnas= new String[T+1];
            txtM= new JTextField[T][T+1];
            columnas[0]="";
            for(int i=0;i<T;i++){
                columnas[i+1]="";
                for(int j=0;j<T+1;j++){
                        txtM[i][j]= new JTextField();
                        txtM[i][j].addKeyListener(this);
                }	
            }
            setTitle("Matriz de "+T+" x "+T);
            setSize(T*100,T*45);
            setModal(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            this.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                       for(int i=0;i<T;i++)
                            for(int j=0;j<T+1;j++){
                                try{
                                    Double x=Double.parseDouble(txtM[i][j].getText());
                                    }catch(Exception ex){
                                    if(JOptionPane.showConfirmDialog(null, "¿Desea realmente salir de Captura de Matriz? Tiene Campos Vaciós",
                                    "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                        cerrar();
                                        return;
                                    }else
                                        return;
                                }
                            }
                        for(int i=0;i<T;i++)
                            for(int j=0;j<T+1;j++){
                                    M[i][j]=Double.parseDouble(txtM[i][j].getText());
                                }
                        if(MetodosSisEcu.Determinante(M)==0){
                                  if(JOptionPane.showConfirmDialog(null, "¿Desea realmente salir de Captura de Matriz? Los Valores de la matriz no tiene Solución",
                                    "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                    }else{
                                      return;
                                  }
                       }
                       tabla.setModel(new javax.swing.table.DefaultTableModel(M,columnas));
                       cerrar();
                    }
                }
            );
            
            HazInterfaz();
    }
    public void cerrar(){
            this.dispose();
    }
    public void HazInterfaz(){
            setLayout(new GridLayout(0,T+1,10,10));
            for(int i=0;i<T;i++)
                    for(int j=0;j<T+1;j++)
                            add(txtM[i][j]);

    }
    public void keyReleased(KeyEvent e){
    }
    public void  keyTyped(KeyEvent e){
        if(bandera){
           Toolkit.getDefaultToolkit().beep();
           e.consume();
        }   
    }
    public void  keyPressed(KeyEvent e){
        bandera=false;
         if(!((e.getKeyChar()>='0' && e.getKeyChar()<='9')||e.getKeyChar()=='.'||e.getKeyChar()=='-'||e.getKeyCode()==KeyEvent.VK_BACK_SPACE || e.getKeyCode()==KeyEvent.VK_ENTER)){
                  bandera=true;  
         }
         
    }
    public void verMatriz(){
            this.show();
    }
    public Double[][] dameMatriz(){
            return this.M;
    }
}