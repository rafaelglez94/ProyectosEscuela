package algoritmos;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Ayuda extends JFrame{
	String codigo = new String(), path = "src/algoritmos/ayuda.txt";
	public Ayuda() {		
		super("AYUDA");
		leeArchivo();
		hazInterfaz();
	}
	private void hazInterfaz() {
		JPanel panel=new JPanel();
		JTextArea txt= new JTextArea();
		txt.setText(codigo);
		txt.setEditable(false);
		panel.setLayout(new BorderLayout());
		panel.add(txt,BorderLayout.CENTER);
		this.add(panel);
		this.setResizable(false);
		this.setBounds(500,0,500, 700);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
	}
	 private void leeArchivo() {
			File archivo = new File(path);

			FileReader fr = null;
			BufferedReader entrada = null;
			try {
				fr = new FileReader(path);
				entrada = new BufferedReader(fr);

				while(entrada.ready()){
					codigo += entrada.readLine()+"\n";
				}

			}catch(IOException e) {
				e.printStackTrace();
			}finally{
				try{                    
					if(null != fr){   
						fr.close();     
					}                  
				}catch (Exception e2){ 
					e2.printStackTrace();
				}
			}
	}
}
