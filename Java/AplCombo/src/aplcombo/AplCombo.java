

package aplcombo;


import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
public class AplCombo extends JFrame implements ActionListener{
    JComboAuto cmb2,cmb1; 
    public AplCombo(){
	    super("Aplicacion Combo");
	    setSize(1200,500);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(3);
	    setLayout(null);
	    
	    String []aux={"csd","asdsd","bsd","csd1","asdsd1","bsd1","csd2","asdsd2","bsd2","csd3","asdsd3","bsd3",};
	    JuegoGuerra jg= new JuegoGuerra();
	    jg.setTamaño(450,10,450,450);
	    add(jg);
	    JCombo JC1= new JCombo("colima","manzanillo");
	    JC1.setBounds(10,10,430,100);
	    add(JC1);
	    JComboAuto JC= new JComboAuto(aux);
	    JC.setBounds(10,140,250,20);
	    add(JC);    
		JLeeFormato JLF= new JLeeFormato("LLLLNNNNNNAAA");
		JLF.setBounds(10,180,100,20);
		add(JLF);
		JLabel JL= new JLabel(JLF.getFormato());
		JL.setBounds(120,180,120,20);
		add(JL);
		/*JProteccion JP= new JProteccion("131");
		JP.setBounds(10,200,250,20);
		add(JP);
		*/
		Thanoi TH= new Thanoi();
		TH.setBounds(10,200,380,200);
		add(TH);
		setVisible(true);
      }
      public void actionPerformed(ActionEvent e){
      	
      }
    public static void main(String[] args) {
    	new AplCombo();
    
   	}
}
class TablaCiudades{
	private RandomAccessFile Arch;
    private String Nom_Arch;
    int N_Datos=0;
    private int N_bytes=40;
   	public TablaCiudades(String Nom_Arch){
    	this.Nom_Arch=Nom_Arch;
    	try{
    		Arch= new RandomAccessFile(new File(Nom_Arch),"rw" );
    		this.N_Datos=(int)Arch.length()/N_bytes;
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}	
    }
    public boolean InsertaDatos(int IdCiudad,int IdEstado, String Nombre){
        try{
        	if(Buscar(IdCiudad,IdEstado))
        		return false;
	        Arch.seek(N_Datos*N_bytes);
	        Arch.writeInt(IdCiudad);
	        Arch.writeInt(IdEstado);    
	        Arch.writeUTF(Nombre);
	        N_Datos++;	
        }catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
			return false;
		}
		return true;     
    }
    public Vector Todos(){
    	Vector aux1= new Vector();
	    Vector aux2= new Vector();
    	try{
		    Arch.seek(0);
	        for(int j=0;j<N_Datos;j++){
	    		aux1= new Vector();
	            aux1.add(Arch.readInt());
	            aux1.add(Arch.readInt());
	            aux1.add(Arch.readUTF());
	            aux2.add(aux1);
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return aux2;
    }
    public boolean Buscar(int idCiudad,int idEstado){
		try{
			for(int i=0;i<N_Datos;i++){
				Arch.seek(i*N_bytes);
				if(Arch.readInt()==idCiudad)
					if(Arch.readInt()==idEstado)
	    					return true;
			}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
			return false;
		}
    	return false;
    }
    public Vector ComboCiu(int idEstado){
	    Vector aux1= new Vector();
	    try{
	    	for(int j=0;j<N_Datos;j++){
	    		Arch.seek((j*N_bytes)+4);
	    		if(Arch.readInt()==idEstado){
	    			aux1.add(Arch.readUTF());	
	    		}
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return aux1;
    }
    public int BuscaId(String Nombre,int IdEstado){
		try{
		    for(int j=0;j<N_Datos;j++){
				Arch.seek((j*N_bytes)+8);
				String aux=Arch.readUTF();
				if(aux.compareTo(Nombre)==0){
		    		Arch.seek((j*N_bytes)+4);
		    		if(Arch.readInt()==IdEstado){
		    			Arch.seek(j*N_bytes);
		    			return Arch.readInt();
		    		}	
				}
			}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return -1;
    }
	public void Cerrar_Arch()throws IOException{
		Arch.close();
	}
 	
}
 class TablaColonias{
	private RandomAccessFile Arch;
    private String Nom_Arch;
    int N_Datos=0;
    private int N_bytes=44;
   	public TablaColonias(String Nom_Arch){
	    try{
	    	this.Nom_Arch=Nom_Arch;
	    	Arch= new RandomAccessFile(new File(Nom_Arch),"rw" );
	    	this.N_Datos=(int)Arch.length()/N_bytes;
	    }catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    }
    public boolean InsertaDatos(int IdColonia,int IdCiudad,int IdEstado,String Nombre){
        try{
	        if(Buscar(IdColonia,IdCiudad,IdEstado))
	        	return false;
	        Arch.seek(N_Datos*N_bytes);
	        Arch.writeInt(IdColonia);    
	        Arch.writeInt(IdCiudad);
	        Arch.writeInt(IdEstado);    
	        Arch.writeUTF(Nombre);    
	        N_Datos++;
        }catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
			return false;
		}
        return true;	
    }
    public Vector Todos(){
	    Vector aux1= new Vector();
        Vector aux2= new Vector();
		try{
		   	Arch.seek(0);
	        for(int j=0;j<N_Datos;j++){
	    		aux1= new Vector();
	            aux1.add(Arch.readInt());
	            aux1.add(Arch.readInt());
	            aux1.add(Arch.readInt());
	            aux1.add(Arch.readUTF());
	            aux2.add(aux1);
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return aux2;
    }
    public boolean Buscar(int IdColonia,int IdCiudad,int IdEstado){
	    try{
	    	for(int i=0;i<N_Datos;i++){
	    		Arch.seek(i*N_bytes);
	    		if(Arch.readInt()==IdColonia)	
	    			if(Arch.readInt()==IdCiudad)	
		    			if(Arch.readInt()==IdEstado)	
		    				return true;
		    				
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return false;
    }
    public Vector ComboCol(int idCiudad,int idEstado){
	    Vector aux1= new Vector();
	    try{
	    	for(int j=0;j<N_Datos;j++){
	    		Arch.seek((j*N_bytes)+4);
	    		if(Arch.readInt()==idCiudad)
	    			if(Arch.readInt()==idEstado)
	    				aux1.add(Arch.readUTF());	
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return aux1;
    }
    public int BuscaId(String Nombre,int IdCiudad,int IdEstado){
		try{
		    for(int j=0;j<N_Datos;j++){
				Arch.seek((j*N_bytes)+12);
				String aux=Arch.readUTF();
				if(aux.compareTo(Nombre)==0){
		    		Arch.seek((j*N_bytes)+4);
		    		if(Arch.readInt()==IdCiudad){
		    			if(Arch.readInt()==IdEstado){
			    			Arch.seek(j*N_bytes);
			    			return Arch.readInt();
			    		}
		    		}	
				}
			}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return -1;
    }
	public void Cerrar_Arch()throws IOException{
		Arch.close();
	}
 
}
 class TablaEstados{
	private RandomAccessFile Arch;
    private String Nom_Arch;
    int N_Datos=0;
    private int N_bytes=36;
   	public TablaEstados(String Nom_Arch){
	    try{
	    	this.Nom_Arch=Nom_Arch;
	    	Arch= new RandomAccessFile(new File(Nom_Arch),"rw" );
	    	this.N_Datos=(int)Arch.length()/N_bytes;
	    }catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    }
    public boolean  InsertaDatos(int IdEstado, String Nombre){
	     try{
	        if(Buscar(IdEstado))
	        	return false;
	        Arch.seek(N_Datos*N_bytes);
	        Arch.writeInt(IdEstado);    
	        Arch.writeUTF(Nombre);    
	        N_Datos++;
        }catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		 return false;
		}
        return true;	
    }
    public Vector Todos(){
	    Vector aux1= new Vector();
        Vector aux2= new Vector();
		try{
		   	Arch.seek(0);
	        for(int j=0;j<N_Datos;j++){
	    		aux1= new Vector();
	            aux1.add(Arch.readInt());
	            aux1.add(Arch.readUTF());
	            aux2.add(aux1);
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return aux2;
    }
    public Vector ComboEst(){
	    Vector aux1= new Vector();
	    try{
	       	for(int j=0;j<N_Datos;j++){
	    		Arch.seek((j*N_bytes)+4);
	    		aux1.add(Arch.readUTF());
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return aux1;
    }
    public int BuscaId(String Nombre){
		try{
		    for(int j=0;j<N_Datos;j++){
	    		Arch.seek((j*N_bytes)+4);
	    		String aux=Arch.readUTF();
				if(aux.compareTo(Nombre)==0){
		    		Arch.seek(j*N_bytes);	
		    		return Arch.readInt();
	    		}
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return -1;
    }
    public boolean Buscar(int idEstado){
	    try{
	    	for(int i=0;i<N_Datos;i++){
	    		Arch.seek(i*N_bytes);
	    		if(Arch.readInt()==idEstado)
	    				return true;
	    	}
    	}catch(IOException e){
			JOptionPane.showMessageDialog(null,"Error en el Archivo","Error",JOptionPane.ERROR_MESSAGE);	
		}
    	return false;
    }
	public void Cerrar_Arch()throws IOException{
		Arch.close();
	}
 	
}
class JCombo extends JPanel implements ActionListener{
	private JComboBox []JCA= new JComboBox[3];
 	private int IdEstado=-1,IdCiudad=-1,IdColonia=-1;
    private TablaEstados TE= new TablaEstados("Estados.dat");
	private TablaCiudades TCiu= new TablaCiudades("Ciudades.dat");
	private TablaColonias TCol= new TablaColonias("Colonias.dat");	
    
    public JCombo() {//estados sin seleccionar
  		HazCombos();
    }
    public JCombo(String Estado){//estados seleccionado
    	HazCombos();
    	JCA[0].setSelectedItem(VCad(Estado));
    	JCA[0].setEnabled(false);
    }
    public JCombo(String Estado,String Ciudad) {//estados y ciudad seleccionado
    	HazCombos();
  		JCA[0].setSelectedItem(VCad(Estado));
    	JCA[0].setEnabled(false);
    	JCA[1].setSelectedItem(VCad(Ciudad));
    	JCA[1].setEnabled(false);
    }
    public void HazCombos(){
    	UIManager.put("Button.defaultButtonFollowsFocus",Boolean.TRUE);
    	setBorder(new TitledBorder("Direccion"));
    	int x=getWidth()/3,y=getHeight()/2;
    	for(int i=0;i<3;i++){
    		JCA[i]= new JComboAuto();
    		JCA[i].addActionListener(this);
    		add(JCA[i]);
    	}
    	ActComboEst();
    	setVisible(true);
    }
    
    public void actionPerformed(ActionEvent E){
    	if(E.getSource()==JCA[0]){
    		if(JCA[0].getItemCount()==0)
    			return;
    		if(JCA[0].getSelectedItem().equals("Seleccione"))
    			return;
    		IdEst();
    		ActComboCiu();
    		//System.out.println("LLego al 1");
    		return;
    	}
    	if(E.getSource()==JCA[1]){
    		if(JCA[1].getItemCount()==0)
    			return;
    		if(JCA[1].getSelectedItem().equals("Seleccione"))
    			return;
    		IdCiu();
    		ActComboCol();
    		return;
    	}
    	if(E.getSource()==JCA[2]){
    		if(JCA[2].getItemCount()==0)
    			return;
    		if(JCA[2].getSelectedItem().equals("Seleccione"))
    			return;
    		JOptionPane.showMessageDialog(null,"Direccion Seleccionada correctamente","Atencion",JOptionPane.INFORMATION_MESSAGE);
    		return;
    	}
    }
    public boolean ActComboEst(){
        if(TE.N_Datos==0){
            JOptionPane.showMessageDialog(null,"Archivo Estados Vacío,\n Favor de agregar Estado","Atención",JOptionPane.INFORMATION_MESSAGE);
            return false;       
        }
        JCA[0].removeAllItems();
        JCA[1].removeAllItems();
        JCA[2].removeAllItems();
        Vector aux=TE.ComboEst();
        JCA[0].addItem("Seleccione");
        for(int i=0;i<aux.size();i++)
        	JCA[0].addItem(aux.elementAt(i));
        return true;
    }
	public boolean ActComboCiu(){
        if(TCiu.N_Datos==0){
            JOptionPane.showMessageDialog(null,"Archivo Ciudades Vacío,\n Favor de agregar Estado"," Atención ",JOptionPane.INFORMATION_MESSAGE);
            return false;       
        }
        JCA[1].removeAllItems();
        JCA[2].removeAllItems();
        Vector aux=TCiu.ComboCiu(IdEstado);
        if(aux.size()==0){
    		JOptionPane.showMessageDialog(null,"El Estado no tiene Ciudades"," Atención ",JOptionPane.INFORMATION_MESSAGE);          	    	
        	JCA[0].grabFocus();
        	return false;
        }
        
        JCA[1].addItem("Seleccione");
         for(int i=0;i<aux.size();i++)
        	JCA[1].addItem(aux.elementAt(i));
  	    return true;
	}
	public boolean ActComboCol(){
        if(TCol.N_Datos==0){
            JOptionPane.showMessageDialog(null,"Archivo Ciudades Vacío,\n Favor de agregar Estado"," Atención ",JOptionPane.INFORMATION_MESSAGE);
            return false;       
        }
        JCA[2].removeAllItems();
        Vector aux=TCol.ComboCol(IdCiudad,IdEstado);
         if(aux.size()==0){
        	JOptionPane.showMessageDialog(null,"La Ciudad no tiene Colonias"," Atención ",JOptionPane.INFORMATION_MESSAGE);          	
	   		JCA[1].grabFocus();	
	   			return false;
        }
        JCA[2].addItem("Seleccione");
         for(int i=0;i<aux.size();i++)
        	JCA[2].addItem(aux.elementAt(i));
  			return true;
	}
	
  public int IdEst(){
    	String sub=(String)JCA[0].getSelectedItem();
    	if(sub==null)
    		return -1;
        IdEstado=TE.BuscaId(sub);
        return IdEstado;
    }
    public int IdCiu(){
    	String sub=(String)JCA[1].getSelectedItem();
    	if(sub==null)
    		return -1;
        IdCiudad=TCiu.BuscaId(sub,IdEstado);
    	return IdCiudad;
    }
    public int IdCol(){
    	String sub=" ";
    	sub=(String)JCA[2].getSelectedItem();
    	if(sub==null|| IdCiudad==-1 ||IdEstado==-1)
    		return -1;
        IdColonia=TCol.BuscaId(sub,IdCiudad,IdEstado);
    	return IdColonia;
    }
    public String VCad(String Cadena){
 		if(Cadena.length()<=30 && Cadena.length()>0)
 			for(int i=Cadena.length();i<30;i++)
 				Cadena+=" ";
 		return Cadena;
 	}
 	
}
class JComboAuto extends JComboBox implements KeyListener,ActionListener,FocusListener{
	private String obj[];
    private JTextField a;
    private String texto="";
    private String aux[]= new String[1];
    private boolean bCB,bTF;
    public JComboAuto(){}
    public JComboAuto(String obj[] ) {
    	setEditable(true);
    	a=(JTextField)(getEditor().getEditorComponent());
    	InsertarItems(obj);
   	}
    public void InsertarItems(String obj[]){
    	UIManager.put("Button.defaultButtonFollowsFocus",Boolean.TRUE);
    	this.obj=obj;
    	ordenar();
  		removeAllItems();
    	addItem("Seleccione");
    	for(int i=0;i<obj.length;i++){
    		addItem(obj[i]);
    	}
    	Editable();
    	getEditor().getEditorComponent().addKeyListener(this);
    	addActionListener(this);
    	a.addFocusListener(this);
    	a.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                Editable();
            }
        });
    }
	public void focusLost(FocusEvent E){
		if(!pierdeFoco())
			SinSeleccion();
	}
    public void focusGained(FocusEvent E){
    	
    	}
   	public void actionPerformed(ActionEvent E){
   		if(E.getSource() instanceof JTextField){
   			pierdeFoco();
   		}	
   	} 
   	public boolean pierdeFoco(){
   			String  texto=a.getText();
   			int aux1=buscar(texto);
   			if(a.getText().length()==0 || aux1==-1){
   				Editable();
   				return false;
   			}
   			setSelectedIndex(aux1+1);
   			a.setText(""+getSelectedItem());
   			a.setEnabled(false);
   			a.setDisabledTextColor(Color.black);
   			return true;
   	}
   	public void SinSeleccion(){
   		JOptionPane.showMessageDialog(null,"Seleccione una Opcion","Atención",JOptionPane.INFORMATION_MESSAGE);
   	}
   	public void Editable(){
   		a.setEnabled(true);
		a.grabFocus();
		a.selectAll();
   	}	
   	public void keyPressed(KeyEvent E){}
   	public void keyTyped(KeyEvent E){}
   	public void keyReleased(KeyEvent E){
   		if(E.getKeyCode()==KeyEvent.VK_DOWN || E.getKeyCode()==KeyEvent.VK_UP )
   			return;
   		if(a.getText().compareTo("Seleccione")==0)
   			return;			
  		String  texto=a.getText();
   		int aux1=buscar(texto);
   		setSelectedIndex(aux1+1);
   		a.setText(texto);
    	setVisible(true);
    	texto="";
   	}
   	public int buscar(String dato){
   		if(dato.length()==0)
   			return -1;
   		String aux1[]= new String[obj.length];
   		int incre=0;
   		for(int i=0;i<obj.length;i++){
			if (obj[i].indexOf(dato)!=-1 &&obj[i].charAt(0)==dato.charAt(0)){
				return i;
			} 
   		}
   		return -1;	
   	}
   	public void ordenar(){
   		String aux="";
   		for(int i=0;i<obj.length-1;i++){
   			for(int j=i+1;j<obj.length;j++){
   				if(obj[i].compareTo(obj[j])>0){
					aux=obj[i];
					obj[i]=obj[j];
					obj[j]=aux;
				}   			
   			}	
   		}
   	}
}
class Punto {
   private int x;
   private int y;
    public Punto() {
    	this(0,0);
    }
    public Punto(int x,int y) {
   		this.x=x;
   		this.y=y;
    } 
    public void setX(int x){
    	this.x=x;
    }
    public void setY(int y){
    	this.y=y;
    }
    public int getX(){
    	return this.x;
    }
    public int getY(){
    	return this.y;
    }
    public double Hipo(){
    	return (double)Math.sqrt((x*x)+(y*y));
    }
    public String toString(){
    	return this.x+"   "+this.y;
    } 
}
class JuegoGuerra extends JPanel implements ActionListener, KeyListener{
    Graphics2D g;
    Image backbuffer;
   	Rectangulo PT;
   	Punto Colisiones[];
   	Rectangulo RL[];
   	Rectangulo PE[];
   	Rectangulo PF;
   	Rectangulo aux;
   	JButton VB[];
   	int ancho=0, alto=0,inc=0;
   	boolean bandera=false;
   	int ladoT,ecen;
   	int vel=10;
   		Random R= new Random();
   	public JuegoGuerra(){
   		UIManager.put("Button.defaultButtonFollowsFocus",Boolean.TRUE);
   		setLayout(null);
   		inc=0;
    	RL= new Rectangulo[100];
	   	PE= new Rectangulo[4];
	   	VB= new JButton[4];		   
    	String txt[]={"Izquierda","Arriba","Abajo","Derecha"};
    	for(int i=0;i<4;i++){
   			VB[i]=new JButton(txt[i]);	
	   		VB[i].addActionListener(this);
	   		VB[i].addKeyListener(this);
	   		add(VB[i]);
   		}
    	nuevoJuego();   			
   	}
   	public void nuevoJuego(){
   		Colisiones= new Punto[3000];
	   	PT= new Rectangulo(3,33,20,20);
   		PF= new Rectangulo(430,430,10,10);
   		inc=0;
   		ecen=R.nextInt(3);
   		repaint();
   	}
   	public void setTamaño(int x,int y, int ancho, int alto){
   		this.ancho=ancho;
   		this.alto=alto;
   		setBounds(x,y,ancho,alto);
   		for(int i=0;i<4;i++){
   			VB[i].setBounds(((ancho/4)*i),10,(ancho/4)-5,15);
	   	}
   	}
    public void paintComponent( Graphics g )
	{		
		g.clearRect(0,0,getWidth(), getHeight());				
   		g.setColor(Color.YELLOW);
   		g.fillRect(0,0,getWidth(),30);
   		tanque(g);
   		esenario(g);
   		g.setColor(Color.blue);
   		Rect(PF,g);    
	}
	public void tanque(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(PT.getX(),PT.getY(),PT.getAncho(),PT.getAlto());
		g.setColor(Color.GREEN);
		if(ladoT==0 || ladoT==1){
			 g.fillRect(PT.getX()+3,PT.getY(),PT.getAncho()-6,PT.getAlto());
		     g.setColor(Color.gray);
		     g.fillRect(PT.getX()+4,PT.getY()+4,PT.getAncho()-8,PT.getAlto()-8);
		     g.setColor(Color.BLACK);
			
			if(ladoT==0)
			    g.fillRect(PT.getX()+8,PT.getY()+6,PT.getAncho()-16,PT.getAlto()-5);
			if(ladoT==1)
			    g.fillRect(PT.getX()+8,PT.getY(),PT.getAncho()-16,PT.getAlto()-8);	   
		}
		if(ladoT==2 || ladoT==3){
		    g.fillRect(PT.getX(),PT.getY()+3,PT.getAncho(),PT.getAlto()-6);
		    g.setColor(Color.gray);
		    g.fillRect(PT.getX()+4,PT.getY()+4,PT.getAncho()-8,PT.getAlto()-8);
		    g.setColor(Color.BLACK);
		    
			if(ladoT==2)
		    	g.fillRect(PT.getX()+8,PT.getY()+8,PT.getAncho()-8,PT.getAlto()-16);
			if(ladoT==3)
			    g.fillRect(PT.getX(),PT.getY()+8,PT.getAncho()-8,PT.getAlto()-16);
		}
	}
    public void esenario(Graphics g){
    	g.setColor(Color.GREEN);
	    int x=0,y=30;
	    	PE[0]= new Rectangulo(0,30,1,(alto-31));
			PE[1]= new Rectangulo(0,(alto-2),(ancho-2),1);
			PE[2]= new Rectangulo((ancho-2),30,1,(alto-32));
			PE[3]= new Rectangulo(0,30,(ancho-2),1);
			
			Rect(PE[0],g);
			Rect(PE[1],g);
			Rect(PE[2],g);
			Rect(PE[3],g);
		  if(ecen==0){
        	for(int i=0;i<9;i++){
	    		if(i%2==0){
		    		RL[i]= new Rectangulo(x,y,ancho-50,1);
		    		Rect(RL[i],g);
		    	}else{
	    			x=50;
	    			RL[i]= new Rectangulo(x,y,ancho-50,1);
		    		Rect(RL[i],g);
	    		}
	    		x=0;
	    		y+=50;
	    	}
    	}
    	if(ecen==1){
    		for(int i=0;i<9;i++){
	    		if(i%2!=0){
		    		RL[i]= new Rectangulo(x,y,1,(alto-80));
		    		Rect(RL[i],g);
		    	}else{
	    			y=80;
	    			RL[i]= new Rectangulo(x,y,1,(alto-80));
		    		Rect(RL[i],g);
	    		}
		    	y=30;
		    	x+=50;
	    	}
    	}
    	if(ecen==2){
    		RL[0]= new Rectangulo(0,80,150,1);
			Rect(RL[0],g);
			RL[1]= new Rectangulo(200,30,1,110);
			Rect(RL[1],g);
			RL[2]= new Rectangulo(150,140,150,200);
			Rect(RL[2],g);
			RL[3]= new Rectangulo(100,200,1,100);
			Rect(RL[3],g);
			RL[4]= new Rectangulo(0,300,100,1);
			Rect(RL[4],g);
			RL[5]= new Rectangulo(200,340,1,50);
			Rect(RL[5],g);
			RL[6]= new Rectangulo(100,390,250,1);
			Rect(RL[6],g);
			RL[7]= new Rectangulo(250,80,200,1);
			Rect(RL[7],g);
			RL[8]= new Rectangulo(350,80,1,270);
			Rect(RL[8],g);
			PF= new Rectangulo(430,40,10,10);
		}
    	boolean bandc=false;
    	if(bandera){
    		for(int i=0;i<inc;i++){
    			if(Colisiones[i].getX()==(PT.getX()+(PT.getAncho()/2)-2) && Colisiones[i].getY()==(PT.getY()+(PT.getAlto()/2)+5)){
    				bandc=true;
    				break;
    			}
    		}
    		if(!bandc)
    			Colisiones[inc++]= new Punto(PT.getX()+(PT.getAncho()/2)-2,PT.getY()+(PT.getAlto()/2)+5);
    		bandera=false;
    	}
    	g.setColor(Color.RED);
    	for(int i=0;i<inc;i++){
    		g.drawString("X",Colisiones[i].getX(),Colisiones[i].getY());		
    	}
    }/*
    public void Linea(Punto p1,Punto p2, Graphics g){
	  	g.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
    }
  */
    public void Rect(Rectangulo R, Graphics g){
		g.drawRect(R.getX(),R.getY(),R.getAncho(),R.getAlto());
	}
    public boolean Colision(Rectangulo C,Rectangulo P){
    	int Ancho_obj1=C.getX()+C.getAncho();
    	int Ancho_obj2=P.getX()+P.getAncho();
    	int Alto_obj1=C.getY()+C.getAlto();
    	int Alto_obj2=P.getY()+P.getAlto();
    	
    	if(	Ancho_obj1	>=	P.getX() && C.getX()	<	Ancho_obj2){
    		if(	Alto_obj1	>=	P.getY() && C.getY()	<	Alto_obj2){
    			return true;
    		}
    	}
    	return false;
    }
    public void actionPerformed(ActionEvent E){
    	aux=new Rectangulo(PT.getX(),PT.getY(),PT.getAncho(),PT.getAlto());
    	if(E.getSource()==VB[0]){
    		PT.setX(PT.getX()-vel);
    		ladoT=3;//izquierda
    	}
    	if(E.getSource()==VB[1]){
    		PT.setY(PT.getY()-vel);
    		ladoT=1;//arriba
    	}
    	if(E.getSource()==VB[2]){
    		PT.setY(PT.getY()+vel);
    		ladoT=0;//abajo
    	}
    	if(E.getSource()==VB[3]){
    		PT.setX(PT.getX()+vel);
    		ladoT=2;//derecha
    	}
    	Validacion();
    }
    public void keyPressed(KeyEvent E){
    	aux=new Rectangulo(PT.getX(),PT.getY(),PT.getAncho(),PT.getAlto());
    	if(E.getKeyChar()=='a'||E.getKeyChar()=='A'){
    		PT.setX(PT.getX()-vel);
    		ladoT=3;//izquierda
    	}
    	if(E.getKeyChar()=='w'||E.getKeyChar()=='W'){
    		PT.setY(PT.getY()-vel);
    		ladoT=1;//arriba
    	}
    	if(E.getKeyChar()=='s'||E.getKeyChar()=='S'){
    		PT.setY(PT.getY()+vel);
    		ladoT=0;//abajo
    	}
    	if(E.getKeyChar()=='d'||E.getKeyChar()=='D'){
    		PT.setX(PT.getX()+vel);
    		ladoT=2;//derecha
    	}
    	Validacion();
    }
    public void keyTyped(KeyEvent E){
    	
    }
    public void Validacion(){
    	for(int i=0;i<4;i++){
    		if(Colision(PE[i],PT)){
    			PT=aux;
    			bandera=true;
    			break;
       		}
    	}
    	for(int i=0;i<9;i++){
    		if(Colision(RL[i],PT)){
    			PT=aux;
    			bandera=true;
    			break;
       		}
    	}
    	if(Colision(PT,PF)){
    		JOptionPane.showMessageDialog(null,"Juego Terminado con: "+inc+" Errores","Atencion",JOptionPane.INFORMATION_MESSAGE);		
    		nuevoJuego();
    		return;
    	}
    	repaint();
    }
    public void keyReleased(KeyEvent E){
    	if(E.getSource()==VB[0]){
			if(E.getKeyCode()==KeyEvent.VK_LEFT){
				VB[3].grabFocus();
				return;
			}
    	}
    	if(E.getSource()==VB[3]){
			if(E.getKeyCode()==KeyEvent.VK_RIGHT){
				VB[0].grabFocus();
				return;
			}
    	}
    	for(int i=0;i<4;i++){
    		if(E.getSource()==VB[i]){
    			if(E.getKeyCode()==KeyEvent.VK_RIGHT){
    				VB[i+1].grabFocus();
    				return;
    			}
    			if(E.getKeyCode()==KeyEvent.VK_LEFT){
    				VB[i-1].grabFocus();
    				return;
    			}	
    		}
    	}
    }
}
class Rectangulo extends Punto{
	int ancho=0,alto=0;
    public Rectangulo(int x,int y,int ancho,int alto){
    	setX(x);
    	setY(y);
    	this.ancho=ancho;
    	this.alto=alto;
    }
    public void setAncho(int ancho){
    	this.ancho=ancho;
    }
    public void setAlto(int alto){
    	this.alto=alto;
    }
    public int getAncho(){
    	return this.ancho;
    }
    public int getAlto(){
    	return this.alto;
    }
}
class JLeeFormato extends JTextField implements KeyListener{
	String Formato="";			
    public JLeeFormato() {}
    public JLeeFormato(String Formato) {
    	this.Formato = Formato;
    	addKeyListener(this);	
   
    }
    public String getFormato(){
    	return this.Formato;
    }
    public void setFormato(String Formato){
    	this.Formato = Formato;
    }
    public void keyReleased(KeyEvent E){
    	if(E.getKeyCode()==KeyEvent.VK_BACK_SPACE)
   			return;
    
    }
    public void keyTyped(KeyEvent E){
    	String texto=getText();
    	int i=texto.length();
    	char e=E.getKeyChar();
    	
    	if(i>=Formato.length()){
    		E.consume();
    		Toolkit.getDefaultToolkit().beep();
    		return;		
    	}
    	switch(Formato.charAt(i)){
    			case 'A':
    					if(!((e>=65 && e<=90)||(e>=97 && e<=122)||e==32) && !Character.isDigit(e)) 
    						E.consume();			
    				break;
    			case 'L':
    					if(!((e>=65 && e<=90)||(e>=97 && e<=122)||e==32)) 
    						E.consume();			
    				break;
    			case 'N':
    					if(!Character.isDigit(e) )
							E.consume();
    				break;
    			case 'S': 
    					
    					if(Character.isDigit(e))
							E.consume();
    					if((e>=65 && e<=90)||(e>=97 && e<=122)||e==32) 
    						E.consume();			
    				break;
    		}
    }
    public void keyPressed(KeyEvent E){
    	
    }
   
    	
}

class Thanoi extends JPanel implements ActionListener{      
    static MyTimer T= new MyTimer();
    static Random R=new Random();
    static int Cantidad=0;
    int Espera=10;
    pila P[]= new pila[3];
    Rectangulo rec;
   	JComboBox CB;
   	JButton B;	
    public Thanoi(){
     	UIManager.put("Button.defaultButtonFollowsFocus",Boolean.TRUE);
   		setLayout(null);
   		HazInterfaz();
   	}
   	public void HazInterfaz(){
   		String[]txt= new String[13];
   		for(int i=0;i<8;i++)
   			txt[i]=(i+3)+"";
   		CB= new JComboBox(txt);
   		CB.setBounds(10,10,40,20);
   		add(CB);
   		CB.addActionListener(this);
   		Cantidad=0;
   		for(int i=0;i<3;i++)
			P[i]= new pila(Cantidad);
   		for(int i=0;i<Cantidad;i++){
   			rec=new Rectangulo(20+(3*i),170-(10*i),100-(6*i),10);
   			P[0].insertar(rec);
   		}
    }
  	public void paintComponent( Graphics G){		
		Image imagen = createImage(getWidth(), getHeight());
		Graphics2D g = (Graphics2D)(imagen.getGraphics());
     	g.clearRect(0,0,getWidth(),getHeight());	
		g.setColor(Color.BLUE);
		g.fillRect(0,0,getWidth(), getHeight());
   		
   		g.setColor(Color.BLACK);
		g.fillRect(68,30,4,155);
		g.fillRect(20,180,100,5);
   		g.fillRect(178,30,4,155);
		g.fillRect(130,180,100,5);
   		g.fillRect(288,30,4,155);
		g.fillRect(240,180,100,5);
   		g.setColor(Color.RED);
   		
   		pila aux=new pila();
   		for(int i=0;i<3;i++){
   			while(!P[i].vacia()){
   			rec=(Rectangulo)P[i].retirar();
   			aux.insertar(rec);
   			Rect(rec,g);
   			}	
   			while(!aux.vacia())
   				P[i].insertar(aux.retirar());
   		}
		G.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
		G.dispose();
		CB.grabFocus();	
	}
    public void Rect(Rectangulo R, Graphics g){
		g.setColor(Color.red);
		g.fillRect(R.getX(),R.getY(),R.getAncho(),R.getAlto());
		g.setColor(Color.BLACK);
		g.drawRect(R.getX(),R.getY(),R.getAncho(),R.getAlto());
	}
    public void actionPerformed(ActionEvent e){
    	Cantidad=Integer.parseInt(CB.getSelectedItem().toString());
    	for(int i=0;i<3;i++)
			P[i]= new pila(Cantidad);
   		for(int i=0;i<Cantidad;i++){
   			rec=new Rectangulo(20+(3*i),170-(10*i),100-(6*i),10);
   			P[0].insertar(rec);
   		}
   		this.update(this.getGraphics());
 		T.Retardo(500);
    	HazTorres(Cantidad);
    }
    public void HazTorres(int Cantidad){
        Hanoi(Cantidad,1,2,3);  //1:origen  2:auxiliar 3:destino
    }
    public  void Hanoi(int n, int origen, int auxiliar, int destino){
	 	if(n==1){
	 		Hacer(origen,destino);
	 	}
	  	else{
	    	Hanoi(n-1, origen, destino, auxiliar);	
			Hacer(origen,destino);
	   		Hanoi(n-1, auxiliar, origen, destino);
	   }
    }
    public void Hacer(int origen,int destino){
     	Rectangulo rec;
	 	Rectangulo rec1;
	 	int alto=0;
	 	int ancho=0;
	 	int[]lim={110,220};
	 	for(int i=0;i<3;i++){
	 		if(origen==(i+1)){
	 			if(!P[i].vacia()){
 					rec=(Rectangulo)P[i].retirar();
 					ancho=rec.getX();
 					while(rec.getY()>10){
 						rec.setY(rec.getY()-10);
 						P[i].insertar(rec);
 						this.update(this.getGraphics());
						T.Retardo(Espera);		
						rec=(Rectangulo)P[i].retirar();
 					}
 					for(int j=0;j<3;j++){
	 					if(destino==(1+j)){
	 						int valor=Math.abs(origen-destino);
	 						System.out.println(valor);
	 						for(int k=0;k<2;k++){
	 							if(valor==(k+1)){
	 								if(origen<destino){
	 									while(rec.getX()<lim[valor-1]+ancho){
				 							rec.setX(rec.getX()+10);
				 							P[i].insertar(rec);
				 							this.update(this.getGraphics());
			 								T.Retardo(Espera);		
			 								rec=(Rectangulo)P[i].retirar();
			 							}
			 						}else{
	 									while(rec.getX()>ancho-lim[valor-1]){
				 							rec.setX(rec.getX()-10);
				 							P[i].insertar(rec);
				 							this.update(this.getGraphics());
			 								T.Retardo(Espera);		
			 								rec=(Rectangulo)P[i].retirar();
			 							}
	 								}
	 							}
	 						}
							if(P[j].vacia()){
 								alto=170;
 							}else{
 								rec1=(Rectangulo)P[j].retirar();
 								P[j].insertar(rec1);
 								alto=rec1.getY()-rec1.getAlto();
 							}
 							rec.setY(alto);
 							P[j].insertar(rec);	
	 					}	
 					}
	 			}
	 		}
	 	}
 		//System.out.println("mover disco de " + origen + " a " + destino);			
		this.update(this.getGraphics());
		T.Retardo(Espera);	
    }
    public static void main(String []args){
    	new Thanoi();
    }
}
/*
class Thanoi extends JPanel implements ActionListener{      
    static MyTimer T= new MyTimer();
    static Random R=new Random();
    static int Cantidad=0;
    int Espera=10;
    Rectangulo torre[][];
    Rectangulo rec;
   	JComboBox CB;
   	JButton B;	
    int ultimo[]={0,0,0};
    public Thanoi(){
     	UIManager.put("Button.defaultButtonFollowsFocus",Boolean.TRUE);
   		setLayout(null);
   		HazInterfaz();
   	}
   	public void HazInterfaz(){
   		String[]txt= new String[13];
   		for(int i=0;i<8;i++)
   			txt[i]=(i+3)+"";
   		CB= new JComboBox(txt);
   		CB.setBounds(10,10,40,20);
   		add(CB);
   		CB.addActionListener(this);
   		Cantidad=0;
   	}
  	public void paintComponent( Graphics G){		
		Image imagen = createImage(getWidth(), getHeight());
		Graphics2D g = (Graphics2D)(imagen.getGraphics());
     	g.clearRect(0,0,getWidth(),getHeight());	
		g.setColor(Color.BLUE);
		g.fillRect(0,0,getWidth(), getHeight());
   		
   		g.setColor(Color.BLACK);
		g.fillRect(68,30,4,155);
		g.fillRect(20,180,100,5);
   		g.fillRect(178,30,4,155);
		g.fillRect(130,180,100,5);
   		g.fillRect(288,30,4,155);
		g.fillRect(240,180,100,5);
   		g.setColor(Color.RED);
   		for(int i=0;i<3;i++){
   			for(int j=0;j<ultimo[i];i++){
   				Rect(torre[i][j],g);
   			}	
   		}
		G.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
		G.dispose();
		CB.grabFocus();	
	}
    public void Rect(Rectangulo R, Graphics g){
		g.setColor(Color.red);
		g.fillRect(R.getX(),R.getY(),R.getAncho(),R.getAlto());
		g.setColor(Color.BLACK);
		g.drawRect(R.getX(),R.getY(),R.getAncho(),R.getAlto());
	}
    public void actionPerformed(ActionEvent e){
    	Cantidad=Integer.parseInt(CB.getSelectedItem().toString());
    	torre=new Rectangulo[3][Cantidad];
   		int ultimo[]={0,0,0};
   		for(int i=0;i<Cantidad;i++){
   			torre[0][i]=new Rectangulo(20+(3*i),170-(10*i),100-(6*i),10);
   			ultimo[0]++;
   		}
   		this.update(this.getGraphics());
 		T.Retardo(500);
    	HazTorres(Cantidad);
    }
    public void HazTorres(int Cantidad){
        Hanoi(Cantidad,1,2,3);  //1:origen  2:auxiliar 3:destino
    }
    public  void Hanoi(int n, int origen, int auxiliar, int destino){
	 	if(n==1){
	 		Hacer(origen,destino);
	 	}
	  	else{
	    	Hanoi(n-1, origen, destino, auxiliar);	
			Hacer(origen,destino);
	   		Hanoi(n-1, auxiliar, origen, destino);
	   }
    }
    public void Hacer(int origen,int destino){
     	Rectangulo rec;
	 	Rectangulo rec1;
	 	int alto=0;
	 	int ancho=0;
	 	int[]lim={110,220};
	 	for(int i=0;i<3;i++){
	 		if(origen==(i+1)){
 				rec=torre[i][ultimo[i]];
 				System.out.println(ultimo[i]);
				ancho=rec.getX();
				while(rec.getY()>10){
					rec.setY(rec.getY()-10);
					T.Retardo(Espera);		
					torre[i][ultimo[i]]=rec;
				}
				for(int j=0;j<3;j++){
 					if(destino==(1+j)){
 						int valor=Math.abs(origen-destino);
 						for(int k=0;k<2;k++){
 							if(valor==(k+1)){
 								if(origen<destino){
 									while(rec.getX()<lim[valor-1]+ancho){
			 							rec.setX(rec.getX()+10);
										T.Retardo(Espera);		
										torre[i][ultimo[i]]=rec;
									}
				 				}else{
									while(rec.getX()>ancho-lim[valor-1]){
			 							rec.setX(rec.getX()-10);
										T.Retardo(Espera);		
										torre[i][ultimo[i]]=rec;
									}
 								}
 							}
 						}
						if(ultimo[j]==0){
							alto=170;
						}else{
							rec1=torre[j][ultimo[j]];
							alto=rec1.getY()-rec1.getAlto();
						}
						rec.setY(alto);
						torre[j][ultimo[j]]=rec;	
 					}
 					ultimo[i]--;
					ultimo[j]++;
					
				}
	 		}
	 	}
 		//System.out.println("mover disco de " + origen + " a " + destino);			
		T.Retardo(Espera);	
    }
    public static void main(String []args){
    	new Thanoi();
    }
}
*/
class pila{
	private int tope=-1;
	private int total=-1;
	private Object datos[];
	pila(){
		this(10);
	}
	pila(int total){
		this.total=total;
		datos = new Object[total];
	}
	public boolean insertar(Object dato){
		if(llena())
			return false;
		tope++;
		datos[tope]=dato;
		return true;
	}
	public boolean llena(){
		return tope==total-1;
	}
	public Object retirar(){
		if(vacia())
			return null;
		Object aux=datos[tope];
		tope--;
		return aux;
	}
	public boolean vacia(){
		return tope==-1;
	}
	public Object ultima(){
		if(vacia())
			return null;
		return datos[tope];		
	}
}
class MyTimer implements ActionListener {
	public void Retardo(int TiempoRetrazo) {
		try {
      		Thread.sleep(TiempoRetrazo);
   		} catch (InterruptedException e) { }
	}
    public void actionPerformed(ActionEvent e) {
    	System.out.println("Finalizo");
    }
}
