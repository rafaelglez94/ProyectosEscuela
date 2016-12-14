/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadordearbol;


import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class Nodo{
        String Nombre="";
        String Valor="";
        ArrayList<Nodo> Hijos = new ArrayList<Nodo>();
        Nodo(String Nombre,String Valor){
            this.Nombre=Nombre;
            this.Valor=Valor;
        }
        Nodo(){}
        public void AgregarHijo(Nodo Hijo){
           Hijos.add(Hijo);
        }
        public Document ToString(Element raiz,Document document){
            Element itemNode=document.createElement(this.Nombre);
            itemNode.setAttribute("Valor", this.Valor);
            raiz.appendChild(itemNode);
            if(Hijos.isEmpty()){
                return document;
            }
            for(int i=0;i<Hijos.size();i++){
               document=Hijos.get(i).ToString(itemNode,document);  
            }
            return document;
        }
    }
