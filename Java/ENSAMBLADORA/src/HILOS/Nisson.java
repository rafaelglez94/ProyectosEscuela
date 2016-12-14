//Programa que simula mediante hilos el trabajo de una planta ensambladora

package HILOS;

import java.util.Random;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JOptionPane;

public class Nisson extends JFrame
{
	private int NumeroLineas;
	private Random R;
	private Semaforo [] Robots;
	private int [] Recursos;
	private Linea [] Lineas;
	private Image [] VImagenes;
	private Posicion [] PosicionCarro;
	private Image Dibuja=null;
	private String [] Estaciones={"Chasis-Cableado","Motor","Tranmisión","Carrocería","Interiores","Llantas","Pruebas"};
	private Graphics2D g;
	private int TotaldeCarros;
	
	public Nisson() throws Exception
	{
		super("Nisson");
		setIconImage(new ImageIcon("Icono.PNG").getImage());
		setSize(1200,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(3);
		
		Temporizador Tiempo=new Temporizador();
		R=new Random();
		NumeroLineas=R.nextInt(3)+7;
	//	NumeroLineas=2;
		TotaldeCarros=100;
		Semaforo [] Robots=new Semaforo[6];
		int [] Recursos={5,4,2,3,NumeroLineas/2,1};
		Lineas=new Linea[NumeroLineas];
		PosicionCarro=new Posicion[NumeroLineas];
		VImagenes=new Image[8];
		
		for(int i=0;i<8;i++)
        	VImagenes[i]=new ImageIcon(i+".PNG").getImage();
        	
        for(int i=0;i<NumeroLineas;i++)
			PosicionCarro[i]=new Posicion(-100,70+(i*(100/2)));
		
		for(int i=0;i<Recursos.length;i++)
			Robots[i]=new Semaforo(Recursos[i]);
		
		for(int i=0;i<Lineas.length;i++)
		{
			Lineas[i]=new Linea(Robots,PosicionCarro,TotaldeCarros);
			Lineas[i].setName((i+1)+"");
			Lineas[i].start();	
		}
		
		while(true)
		{
			Dibujando(g);
			repaint();
			Tiempo.TiempodeRetardo(50);
			if(Linea.getCarroTerminado()==TotaldeCarros)
				break;
		}
	
		for(int i=0;i<Lineas.length;i++)
		{
			Lineas[i].join();
		}
		JOptionPane.showMessageDialog(null,"Iniciaron "+Lineas[0].getCarroEnProceso()+" y terminaron "+Lineas[0].getCarroTerminado(),"Carros: ", JOptionPane.PLAIN_MESSAGE);
		System.out.println("Iniciaron "+Lineas[0].getCarroEnProceso()+" y terminaron "+Lineas[0].getCarroTerminado());
	}
	
	public void Dibujando(Graphics2D g)
	{
		Dibuja=createImage(getWidth(),getHeight());
		g=(Graphics2D) Dibuja.getGraphics();
		
		for(int j=0;j<NumeroLineas;j++)
		{	
			g.drawString("Linea "+(j+1),10,80+(j*(100/2)));//Nombre de Lineas
			g.drawString("Carro terminado: "+Linea.getCarroTerminado(),getWidth()-150,getHeight()-20);//Terminados
			
			for(int k=0;k<7;k++)	
				g.drawString(Estaciones[k],(k+(k+1))*80,50);//Nombre de estaciones
			
				g.drawImage(VImagenes[0],PosicionCarro[j].getX(),PosicionCarro[j].getY(),this);//Inicio sin nada solo rines
				
			if(PosicionCarro[j].getX()<90 || PosicionCarro[j].getX()<220)
				g.drawImage(VImagenes[1],PosicionCarro[j].getX(),PosicionCarro[j].getY(),this);//Chasis y cableado
			else if(PosicionCarro[j].getX()<230 || PosicionCarro[j].getX()<380)
				g.drawImage(VImagenes[2],PosicionCarro[j].getX(),PosicionCarro[j].getY(),this);//Motor
			else if(PosicionCarro[j].getX()<390 || PosicionCarro[j].getX()<540)
				g.drawImage(VImagenes[3],PosicionCarro[j].getX(),PosicionCarro[j].getY(),this);//Transmisi�n
			else if(PosicionCarro[j].getX()<550 || PosicionCarro[j].getX()<710)
				g.drawImage(VImagenes[4],PosicionCarro[j].getX(),PosicionCarro[j].getY(),this);//Carrocer�a
			else if(PosicionCarro[j].getX()<710 || PosicionCarro[j].getX()<860)
				g.drawImage(VImagenes[5],PosicionCarro[j].getX(),PosicionCarro[j].getY(),this);//Interiores
			else if(PosicionCarro[j].getX()<870 || PosicionCarro[j].getX()<1020)
				g.drawImage(VImagenes[6],PosicionCarro[j].getX(),PosicionCarro[j].getY(),this);//Llantas
			else if(PosicionCarro[j].getX()>1030 || PosicionCarro[j].getX()<1100)
				g.drawImage(VImagenes[6],PosicionCarro[j].getX(),PosicionCarro[j].getY(),this);//Pruebas
			
			g.drawString("Lineas: "+NumeroLineas,getWidth()-150,getHeight()-10);//Terminados
						
			for(int i=0;i<8;i++)		
				g.drawImage(VImagenes[7],(i+(i+1))*80,70+(j*(100/2)),this);//Estaciones X=(i+(i+1)))*80 y Y=70+(j*(100/2)
		}		
	}
	
	public void paint(Graphics G)
	{
		G.drawImage(Dibuja,0,0,getWidth(),getHeight(),this);
		G.dispose();
	}
	
	public static void main(String [] a) throws Exception
	{
		new Nisson();
	}
}

class Linea extends Thread
{
	private static Semaforo [] Robots;
	private static int CarroIniciado=0,CarroTerminado=0;
	private Temporizador Tarda=new Temporizador();
	private static Posicion [] PosicionCarro;
	private int TotaldeCarros;  
	
	public Linea(Semaforo [] Robots,Posicion [] PosicionCarro,int TotaldeCarros)
	{
		this.Robots=Robots;
		this.PosicionCarro=PosicionCarro;
		this.TotaldeCarros=TotaldeCarros;
	}
	
	public void run()
	{
		while(true)
		{
			Robots[5].Espera();
                        if(CarroIniciado>=TotaldeCarros)
                        {
                            Robots[5].Libera();
                            break;
                        }
			CarroIniciado++;
			System.out.println("Carro inicio "+CarroIniciado+" en linea "+getName());
			Robots[5].Libera();
			
			int NumerodeCarro=Integer.parseInt(getName());
			PosicionCarro[NumerodeCarro-1].setX(-100);
			//Chasis y cableado con 5 robots
			Robots[0].Espera();
			System.out.println("En linea "+getName()+" instalando chasis y cableado");
			while(PosicionCarro[NumerodeCarro-1].getX()<80)
			{
				PosicionCarro[NumerodeCarro-1].setX(PosicionCarro[NumerodeCarro-1].getX()+5);
				Tarda.TiempodeRetardo(50);
			}
			Tarda.TiempodeRetardo(20000);
		//	Tarda.TiempodeRetardo(200);
			Robots[0].Libera();
			
			//Estaci�n 2 motor y transmisi�n con 4 robots para motor y 2 para transmisi�n
			
			//Motor
			Robots[1].Espera();
			System.out.println("En linea "+getName()+" instalando motor");
			while(PosicionCarro[NumerodeCarro-1].getX()<230)
			{
				PosicionCarro[NumerodeCarro-1].setX(PosicionCarro[NumerodeCarro-1].getX()+5);
				Tarda.TiempodeRetardo(50);
			}
			Tarda.TiempodeRetardo(6000);
		//	Tarda.TiempodeRetardo(60);
			
			
			//Transmisi�n
			Robots[2].Espera();
			Robots[1].Libera();//Libera Motor
			System.out.println("En linea "+getName()+" instalando transmisi�n");
			while(PosicionCarro[NumerodeCarro-1].getX()<390)
			{
				PosicionCarro[NumerodeCarro-1].setX(PosicionCarro[NumerodeCarro-1].getX()+5);
				Tarda.TiempodeRetardo(50);
			}
			Tarda.TiempodeRetardo(4000);
		//	Tarda.TiempodeRetardo(40);
			Robots[2].Libera();
			
			//Carroceria
			Robots[3].Espera();
			System.out.println("En linea "+getName()+" instalando carroceria");
			while(PosicionCarro[NumerodeCarro-1].getX()<550)
			{
				PosicionCarro[NumerodeCarro-1].setX(PosicionCarro[NumerodeCarro-1].getX()+5);
				Tarda.TiempodeRetardo(50);
			}
			Tarda.TiempodeRetardo(10000);
		//	Tarda.TiempodeRetardo(100);
			Robots[3].Libera();
			
			//Interiores
			Robots[4].Espera();
			System.out.println("En linea "+getName()+" instalando interiores");
			while(PosicionCarro[NumerodeCarro-1].getX()<710)
			{
				PosicionCarro[NumerodeCarro-1].setX(PosicionCarro[NumerodeCarro-1].getX()+5);
				Tarda.TiempodeRetardo(50);
			}
			Tarda.TiempodeRetardo(5000);
		//	Tarda.TiempodeRetardo(50);
			Robots[4].Libera();
			
			//Llantas
			while(PosicionCarro[NumerodeCarro-1].getX()<870)
			{
				PosicionCarro[NumerodeCarro-1].setX(PosicionCarro[NumerodeCarro-1].getX()+5);
				Tarda.TiempodeRetardo(50);
			}
			Tarda.TiempodeRetardo(5000);
			System.out.println("En linea "+getName()+" instalando llantas");
		//	Tarda.TiempodeRetardo(50);
			
			//Pruebas
			while(PosicionCarro[NumerodeCarro-1].getX()<1030)
			{
				PosicionCarro[NumerodeCarro-1].setX(PosicionCarro[NumerodeCarro-1].getX()+5);
				Tarda.TiempodeRetardo(50);
			}
			Tarda.TiempodeRetardo(10000);
			System.out.println("En linea "+getName()+" haciendo pruebas");
		//	Tarda.TiempodeRetardo(100);
			
			while(PosicionCarro[NumerodeCarro-1].getX()<1210)
			{
				PosicionCarro[NumerodeCarro-1].setX(PosicionCarro[NumerodeCarro-1].getX()+5);
				Tarda.TiempodeRetardo(50);
			}
			Robots[5].Espera();
			CarroTerminado++;
			System.out.println("Carro terminado "+CarroTerminado+" en linea "+getName());
			Robots[5].Libera();
		}
	}
	
	public int getCarroEnProceso()
	{
		return CarroIniciado;
	}
	
	public static int getCarroTerminado()
	{
		return CarroTerminado;
	}
}