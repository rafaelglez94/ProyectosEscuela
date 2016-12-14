package algoritmos;

public class Cruza {
	
	public static int[] cruzaUnPunto(int[] arreglo1, int[] arreglo2, int punto) {
		int[] descendiente = new int[arreglo1.length];
		
		for(int i=0; i<descendiente.length; i++) {
			if(i < punto)
				descendiente[i] = arreglo1[i];
			else
				descendiente[i] = arreglo2[i];
		}
		
		return descendiente;
	}

}
