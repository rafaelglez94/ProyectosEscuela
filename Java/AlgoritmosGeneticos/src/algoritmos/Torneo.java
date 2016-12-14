package algoritmos;

import java.util.Random;

import javax.swing.JTextArea;

public class Torneo {
	
	public static final int BINARIA = 0;
	public static final int REAL    = 1;
	private int[][] individuos;
	private int[] numIndividuo;
	private int[][] ganadores;
	private int[] ganadoresIndices;
	private int[] ganadoresNumIndividuo;
	private int[][] nuevos;
	private int[] nuevosNumIndividuo;
	private int poblacion;
	private int numGenotipos;
	private int representacion;
	private int individuosTotal;
	private float probabilidadCruza = 0.5f;
	private float probabilidadMutacion = 0.01f;
	private int generacion = 0;
	private int generacionMax = 0;
	private JTextArea txa_log;
	private Random rnd;
	
	public Torneo(int poblacion, int numGenotipos, int generacionMax, int representacion, 
			float probabilidadCruza, float probabilidadMutacion, JTextArea txa_log) {
		if(poblacion%2 != 0)
			poblacion++;
		this.poblacion = individuosTotal = poblacion;
		this.numGenotipos = numGenotipos;
		this.generacionMax = generacionMax;
		this.representacion = representacion;
		this.probabilidadCruza = probabilidadCruza;
		this.probabilidadMutacion = probabilidadMutacion;
		this.txa_log = txa_log;
		generacion = 0;
		
		rnd = new Random();
		
		individuos = new int[poblacion][numGenotipos * 4];
		numIndividuo = new int[poblacion];
		ganadores = new int[poblacion/2][numGenotipos * 4];
		ganadoresIndices = new int[poblacion/2];
		ganadoresNumIndividuo = new int[poblacion/2];
		nuevos = new int[poblacion/2][numGenotipos * 4];
		nuevosNumIndividuo = new int[poblacion/2];
		
		for(int i=0; i<poblacion; i++)
			numIndividuo[i] = i+1;
		
		crearIndividuos();
		mostrarPoblacion();
		realizarTorneo();
	}
	
	public void realizarTorneo() {
		while(generacion < generacionMax) {
			barajearIndividuos();
			torneo();
			mostrarGanadores();
			asignarPareja();
			cruza();
			nuevaGeneracion();
			mutacion();
			mostrarPoblacion();
		}
	}
	
	public void crearIndividuos() {
		generacion++;
		for(int i=0; i<individuos.length; i++)
			for(int j=0; j<individuos[0].length; j++) {
				individuos[i][j] = rnd.nextInt(2);
			}
	}
	
	public void mostrarPoblacion() {
		String texto = "\nIndividuos: (Generación No."+generacion+")\n";
		for(int i=0; i<individuos.length; i++) {
			texto += "    "+numIndividuo[i]+".- [";
			for(int j=0; j<individuos[0].length; j++) {
				texto += individuos[i][j] + ((j < individuos[0].length-1) ? "," : "");
			}
			texto += "]    Valor: "+convertirBinarioADecimal(individuos[i])+
			"    Aptitud: "+funcion(convertirBinarioADecimal(individuos[i]))+"\n";
		}
		txa_log.setText(txa_log.getText()+texto);
	}
	
	public void barajearIndividuos() {
		for(int i=0; i<individuos.length; i++) {
			int indice = rnd.nextInt(individuos.length);
			if(indice == i)
				continue;
			for(int j=0; j<individuos[0].length; j++) {
				int genotipo = individuos[i][j];
				individuos[i][j] = individuos[indice][j];
				individuos[indice][j] = genotipo;
			}
			int pos = numIndividuo[i];
			numIndividuo[i] = numIndividuo[indice];
			numIndividuo[indice] = pos;
		}
	}
	
	public void torneo() {
		for(int i=0, j=0; j<ganadoresIndices.length; i+=2, j++) {
			double n1 = funcion(convertirBinarioADecimal(individuos[i]));
			double n2 = funcion(convertirBinarioADecimal(individuos[i+1]));
			if(n1 > n2) {
				ganadoresIndices[j] = i;
				ganadoresNumIndividuo[j] = numIndividuo[i];
			} else {
				ganadoresIndices[j] = i+1;
				ganadoresNumIndividuo[j] = numIndividuo[i+1];
			}
		}
		copiarGanadores();
	}
	
	private void copiarGanadores() {
		for(int i=0; i<ganadores.length; i++) {
			for(int j=0; j<ganadores[0].length; j++) {
				ganadores[i][j] = individuos[ganadoresIndices[i]][j];
			}
		}
	}
	
	public void mostrarGanadores() {
		String texto = "\nGanadores: (Generación No."+generacion+")\n";
		for(int i=0; i<ganadores.length; i++) {
			texto += "    "+ganadoresNumIndividuo[i]+".- [";
			for(int j=0; j<ganadores[0].length; j++) {
				texto += ganadores[i][j] + ((j < ganadores[0].length-1) ? "," : "");
			}
			texto += "]    Valor: "+convertirBinarioADecimal(ganadores[i])+
					"    Aptitud: "+funcion(convertirBinarioADecimal(ganadores[i]))+"\n";
		}
		txa_log.setText(txa_log.getText()+texto);
	}
	
	public void asignarPareja() {
		individuos = new int[poblacion][numGenotipos * 4];
		for(int i=0, j=0; i<ganadoresIndices.length; i++, j+=2) {
			int pareja = i;
			while(pareja == i)
				pareja = rnd.nextInt(ganadores.length);
			copiarParejas(j, i, pareja);
		}
	}
	
	private void copiarParejas(int j, int indice1, int indice2) {
		for(int i=0; i<individuos[0].length; i++) {
			individuos[j][i] = ganadores[indice1][i];
			individuos[j+1][i] = ganadores[indice2][i];
		}
		numIndividuo[j] = ganadoresNumIndividuo[indice1];
		numIndividuo[j+1] = ganadoresNumIndividuo[indice2];
	}
	
	public void cruza() {
		for(int i=0, j=0; j<ganadoresIndices.length; i+=2, j++) {
			individuosTotal++;
			nuevosNumIndividuo[j] = individuosTotal;
			
			if(rnd.nextFloat() > probabilidadCruza) {
				for(int k=0; k<nuevos[0].length; k++)
					nuevos[j][k] = rnd.nextInt(2);
				continue;
			}
			
			int punto = rnd.nextInt(individuos[0].length-2)+1;
			
			int[] descendiente1 = Cruza.cruzaUnPunto(individuos[i], individuos[i+1], punto);
			int[] descendiente2 = Cruza.cruzaUnPunto(individuos[i+1], individuos[i], punto);
			
			double d1 = funcion(convertirBinarioADecimal(descendiente1));
			double d2 = funcion(convertirBinarioADecimal(descendiente2));
			
			if(d1 > d2) {
				copiarNuevo(descendiente1, j);
			} else {
				copiarNuevo(descendiente2, j);
			}
		}
	}
	
	private void copiarNuevo(int[] a, int indice) {
		for(int i=0; i<a.length; i++)
			nuevos[indice][i] = a[i];
	}
	
	public void nuevaGeneracion() {
		generacion++;
		for(int i=0, j=0; j<ganadoresIndices.length; i+=2, j++) {
			copiarAPoblacion(ganadores[j], i);
			copiarAPoblacion(nuevos[j], i+1);
			numIndividuo[i] = ganadoresNumIndividuo[j];
			numIndividuo[i+1] = nuevosNumIndividuo[j];
		}
	}
	
	private void copiarAPoblacion(int[] a, int indice) {
		for(int i=0; i<a.length; i++)
			individuos[indice][i] = a[i];
	}
	
	public void mutacion() {
		for(int i=0; i<individuos.length; i++) {
			if(rnd.nextFloat() > probabilidadMutacion)
				continue;
			int indice = rnd.nextInt(individuos[0].length);
			if(individuos[i][indice] == 0)
				individuos[i][indice] = 1;
			else
				individuos[i][indice] = 0;
		}
	}
	
	public int convertirBinarioADecimal(int[] a) {
		int n = 0;
		for(int j=a.length-1; j>=0; j--)
			n += a[j] * Math.pow(2, (a.length-1) - j);
		return n;
	}
	
	public double funcion(double x) {
		return Math.pow(x, 2) + 2*x - 3;
	}

}
