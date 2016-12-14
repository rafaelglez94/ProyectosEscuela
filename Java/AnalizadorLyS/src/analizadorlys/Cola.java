
package analizadorlys;
import java.util.*;

public class Cola{
	LinkedList cola= new LinkedList();
	public void Mete(String Token){
		cola.addFirst(Token);
	}
	public String Saca(){
		return cola.removeLast().toString();
	}
	public void Mostrar(){
		System.out.println(cola);
	}
        public void Lipiar(){
            cola.clear();
        }
        public boolean Vacia(){
            return cola.isEmpty();
        }
}