//Programa que nos ejecuta un tiempo que se mandara a dormir el programa

package HILOS;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Temporizador implements ActionListener 
{
	public void TiempodeRetardo(int TiempodeRetrazo) 
	{
		try 
		{
      		Thread.sleep(TiempodeRetrazo);
   		} 
   		catch (InterruptedException e) 
   		{ 
   			
   		}
	}
    public void actionPerformed(ActionEvent e) 
    {
    	
    }
}