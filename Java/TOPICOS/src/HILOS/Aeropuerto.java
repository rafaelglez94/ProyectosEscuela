//Programa que simula el descenso de aviones en una pista segun su turno indicado

package HILOS;

import java.util.Random;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class Aeropuerto extends JFrame
{
	private Image [] VImagenes;
	private Posicion [] PosicionAviones;
	private Image Dibuja=null;
	private JPanel PanelFinaliza;
		
	public Aeropuerto() throws Exception
	{
		super("Aeropuerto de México");
		setIconImage(new ImageIcon("Icono.PNG").getImage());
		setSize(600,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(3);
		Dibuja=createImage(getWidth(),getHeight());
		Graphics2D g=(Graphics2D) Dibuja.getGraphics();
		PanelFinaliza=new JPanel();
    	PanelFinaliza.setLayout(new GridLayout(0,2));
		Random R=new Random();
		Semaforo Pista=new Semaforo(1);
		int [] NoBajadas;
		Avion [] Aviones;
	//	int NumeroVuelos=R.nextInt(7)+3;
		int NumeroVuelos=9;
		Aviones=new Avion[NumeroVuelos];
		PosicionAviones=new Posicion[NumeroVuelos];
		NoBajadas=new int[NumeroVuelos];
		VImagenes=new Image [4];
		Temporizador Tiempo=new Temporizador();
		
		for(int i=0;i<4;i++)
        	VImagenes[i]=new ImageIcon((i)+".PNG").getImage();
        	
		for(int i=0;i<NumeroVuelos;i++)
			PosicionAviones[i]=new Posicion(10,(i+1)*45);
		
		System.out.println("Total de vuelos "+NumeroVuelos);
			
		for(int i=0;i<NumeroVuelos;i++)
		{
			Aviones[i]=new Avion(Pista,NoBajadas,PosicionAviones);
			Aviones[i].start();
			Aviones[i].setName((i+1)+"");
		}
		
		while(true)
		{
			for(int i=Avion.Finaliza()-1;i<NumeroVuelos;i++)
			{
				if(PosicionAviones[i].getX()>=650)
					PosicionAviones[i].setX(-100);
				PosicionAviones[i].setX(PosicionAviones[i].getX()+10);
			}
			Dibujando(g);
			repaint();
			if(PosicionAviones[NumeroVuelos-1].getX()==(600-(Avion.Finaliza()*60)) && PosicionAviones[NumeroVuelos-1].getY()>=520)
				break;
			Tiempo.TiempodeRetardo(50);
		}
		for(int i=0;i<NumeroVuelos;i++)
		{
			Aviones[i].join();
		}
		
		PanelFinaliza.add(new JLabel("No. avión:",SwingConstants.CENTER));
		PanelFinaliza.add(new JLabel("Intentos fallidos:",SwingConstants.CENTER));
			
		for(int i=0;i<NumeroVuelos;i++)
		{
			PanelFinaliza.add(new JLabel(Aviones[i].getName(),SwingConstants.CENTER));
			PanelFinaliza.add(new JLabel(NoBajadas[i]+"",SwingConstants.CENTER));
			System.out.println("El avion "+Aviones[i].getName()+" intento aterrizar "+NoBajadas[i]+" sin ser su turno...");
		}
		JOptionPane.showMessageDialog(null,PanelFinaliza,"Intentos fallidos de aterrizar:", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void Dibujando(Graphics2D g)
	{
		g.drawImage(VImagenes[3],0,20,this);//Cielo
		g.drawImage(VImagenes[0],0,540,this);//Pista
		g.drawImage(VImagenes[1],380,370,this);//Torre
		
		g.setColor(Color.red);
		if(Avion.Finaliza()<=PosicionAviones.length)
			g.drawString("Turno: "+Avion.Finaliza(),500,50);
		
		for(int i=0;i<PosicionAviones.length;i++)
		{	
			g.drawImage(VImagenes[2],PosicionAviones[i].getX()+10,PosicionAviones[i].getY(),this);//Avi�n
			g.drawString(""+(i+1),PosicionAviones[i].getX()+50,PosicionAviones[i].getY()+27);//Identificador en n�mero	
		}
	}
	
	public void paint(Graphics G)
	{
		G.drawImage(Dibuja,0,0,getWidth(),getHeight(),this);
		G.dispose();
	}
	
	public static void main(String [] a) throws  Exception
	{
		new Aeropuerto();
	}
}

class Avion extends Thread
{
	private static Semaforo Pista;
	private static int Turno=1;
	private static int [] NoBajadas;
	private int NumerodeAvion,Contador=0;
	private Temporizador Tarda=new Temporizador();
	private Random R=new Random();
	private boolean Entra=true;
	private static Posicion [] PosicionAvion; 
	
	public Avion(Semaforo Pista,int [] NoBajadas,Posicion [] PosicionAvion)
	{
		this.Pista=Pista;
		this.NoBajadas=NoBajadas;
		this.PosicionAvion=PosicionAvion;
	}
	
	public void run()
	{
		while(Entra)
		{
			Pista.Espera();
			NumerodeAvion=Integer.parseInt(getName());
			while(PosicionAvion[NumerodeAvion-1].getY()<=450)
			{
				Contador++;
				PosicionAvion[NumerodeAvion-1].setY(PosicionAvion[NumerodeAvion-1].getY()+5);
				Tarda.TiempodeRetardo(50);
			}
			
			if(NumerodeAvion==Turno)
			{
				System.out.println("Aterrizo el avion "+getName()+" en el turno "+Turno);
				Turno++;
				Entra=false;
				while(PosicionAvion[NumerodeAvion-1].getX()!=(600-(Turno*60)))//Checar
				{
					PosicionAvion[NumerodeAvion-1].setX(PosicionAvion[NumerodeAvion-1].getX()+5);
					if(PosicionAvion[NumerodeAvion-1].getY()!=520)
						PosicionAvion[NumerodeAvion-1].setY(PosicionAvion[NumerodeAvion-1].getY()+5);
					if(PosicionAvion[NumerodeAvion-1].getX()>=650)
						PosicionAvion[NumerodeAvion-1].setX(-100);
					if(PosicionAvion[NumerodeAvion-1].getY()!=520 && PosicionAvion[NumerodeAvion-1].getX()==(600-(Turno*60)))
						PosicionAvion[NumerodeAvion-1].setX(PosicionAvion[NumerodeAvion-1].getX()+5);
					Tarda.TiempodeRetardo(50);
				}					
			}
			else
			{
				NoBajadas[NumerodeAvion-1]++;
				System.out.println("Avion no aterrizado "+getName());
		
				for(int i=0;i<5;i++)
				{
					PosicionAvion[NumerodeAvion-1].setX(PosicionAvion[NumerodeAvion-1].getX()+5);
					Tarda.TiempodeRetardo(30);
				}
					
				while(Contador!=0)
				{
					Contador--;
					PosicionAvion[NumerodeAvion-1].setX(PosicionAvion[NumerodeAvion-1].getX()+5);
					PosicionAvion[NumerodeAvion-1].setY(PosicionAvion[NumerodeAvion-1].getY()-5);
					Tarda.TiempodeRetardo(50);
				}
			}
			System.out.println("Pista Libre...");
			Pista.Libera();
			Tarda.TiempodeRetardo(R.nextInt(50)+50);
		}
	}
	
	public static int Finaliza()
	{
		return Turno;
	}
}

