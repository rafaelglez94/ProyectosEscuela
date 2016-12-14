package proyectodba;

import javax.swing.*;
import java.awt.event.*;
import java.awt .*;
import java.io.*;
import java.sql.*;
import java.util.*;


public class ProyectoDBA extends JFrame implements ActionListener{
    //conexion
    private Connection connection = null;
    private ResultSet rs = null;
    private Statement s = null;
    String host,usuario, password, puerto,DB;
    int activa=0;
    
    //Login
    JPanel DL= new JPanel();
    JPanel BL= new JPanel();
    String []SLVL={"Host", "Usuario", "Password", "Puerto", "Base de Datos"};
    String []STVL={"localhost","postgres", "1234", "5435", "postgres"};
    JLabel []LVL = new JLabel[5];
    JTextField []TVL= new JTextField[5];
    JPasswordField PVL= new JPasswordField(STVL[2]);
    JButton []BVL = new JButton[2];

    //Menu
    JDialog DMenu= new JDialog(this,"Menu",Dialog.ModalityType.DOCUMENT_MODAL);
    JButton []BVM= new JButton[4];
    String []SBM={"Datos de Sesión","Tablas Por BD y Tamaño BD","Tablas Mas Grandes de BD","Respaldos"};
    
    //Datos de Sesion
    JDialog DSesion= new JDialog(DMenu,"Datos de Sesión",Dialog.ModalityType.DOCUMENT_MODAL);
    String []SLVS={"Servidor", "Usuario", "Puerto", "Base de Datos","Fecha de Arranque","Fecha Actual","Uptime","Listado de BD","Espacio de disco Ocupado"};
    String []STVS={"", "", "", "", "", "", "", "", ""};
    JLabel []LVS = new JLabel[9];
    JTextField []TVS= new JTextField[9];
 
    //Respaldo
    //Panel uno Globales
    String SLVR1[]={"Archivos","Servidor","Usuario","Password","Puerto","Solo de User,Password y Roles"};
    JPanel P1R=new JPanel(); 
    JLabel LVR1[] = new JLabel[6];
    JTextField TVR1[]= new JTextField[5];
    JPasswordField PVR1= new JPasswordField();
    JPasswordField PVR2= new JPasswordField();
    JCheckBox CBR1 = new JCheckBox(SLVR1[5]);
    JButton BAR1= new JButton("Aceptar");
    JButton BArchivoR1= new JButton("...");
    
    //Panel uno Especificos
    JPanel P2R=new JPanel(); 
    String SLVR2[]={"Archivos","Servidor","Usuario","Password","Puerto","Base de Datos","Tablas","Formato"};
    String SRBRP2[]={"Esquemas","Datos","Todo"};
    JLabel LVR2[] = new JLabel[8];
    JTextField TVR2[]= new JTextField[5];
    JComboBox  CBR2[]= new JComboBox[3];
    JRadioButton RBER2= new JRadioButton("Estructura");
    JRadioButton RBDR2= new JRadioButton("Datos");
    JRadioButton RBTR2= new JRadioButton("Todo");
    ButtonGroup BGR2 = new ButtonGroup(); 
    JDialog DRespaldos= new JDialog(DMenu,"Respaldos",Dialog.ModalityType.DOCUMENT_MODAL);
    JTabbedPane PRespaldos = new JTabbedPane();
    JButton BAR2= new JButton("Aceptar");
    JButton BArchivoR2= new JButton("...");

    //Consultas
    JDialog DConsulta= new JDialog(DMenu,"Consulta por Tabla",Dialog.ModalityType.DOCUMENT_MODAL);
    JComboBox  CBC[]= new JComboBox[2];
    JTable TC;
    JScrollPane JSPC;
    JLabel LVC= new JLabel("Tamaño BD:");
    JTextField TVC= new JTextField();
    Vector cols= new Vector();
    
    //Tabla mas Grande
    JDialog DTablaG= new JDialog(DMenu,"Tabla mas Grande",Dialog.ModalityType.DOCUMENT_MODAL);
    JComboBox  CBTG= new JComboBox();
    JLabel LVTGN= new JLabel("Nombre Tabla:");
    JTextField TVTGN= new JTextField();
    JLabel LVTGT= new JLabel("Tamaño:");
    JTextField TVTGT= new JTextField();
    
    public boolean Conexion(String host,String usuario,String password,String puerto,String bd){
        this.host=host;
        this.usuario=usuario;
        this.password=password;
        this.puerto=puerto;
        this.DB=bd;
        String url = "jdbc:postgresql://"+this.host+":"+this.puerto+"/"+bd;
        try {
            if (connection != null)
                connection.close();
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, this.usuario, password);
            if (connection != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Problemas de Conexión");
        }
        return false;
    }
    ProyectoDBA(){
        super("Login");
        setSize(200,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        HazInterfaz();
        HazEscuchas();
        setVisible(true);
    }
    public void HazInterfaz(){
        //Vista Login
        DL.setLayout(new GridLayout(0,1,2,1));
        BL.setLayout(new GridLayout(0,2));
        for(int i=0;i<5;i++){
            LVL[i] = new JLabel(SLVL[i]);
            DL.add(LVL[i]);
            if(i!=2){
                TVL[i]= new JTextField(STVL[i]);
                DL.add(TVL[i]);
            }
            else
                DL.add(PVL);
        }
        add(DL,BorderLayout.NORTH);
        BVL[0] = new JButton("Conectar");
        BVL[1] = new JButton("Salir");
        BL.add(BVL[0]);
        BL.add(BVL[1]);
        add(BL,BorderLayout.SOUTH);
        //Vista Menu
        DMenu.setLayout(new GridLayout(0,1));
        DMenu.setSize(300,300);
        DMenu.setDefaultCloseOperation(1);
        DMenu.setLocationRelativeTo(null);      
        for(int i=0;i<BVM.length;i++){
            BVM[i]= new JButton(SBM[i]);
            DMenu.add(BVM[i]);
        }
        //Vista Sesión
        DSesion.setLayout(new GridLayout(0,2));
        DSesion.setSize(300,300);
        DSesion.setDefaultCloseOperation(1);
        DSesion.setLocationRelativeTo(null);      
        for(int i=0;i<9;i++){
            LVS[i] = new JLabel(SLVS[i]);
            DSesion.add(LVS[i]);
            TVS[i]= new JTextField(STVS[i]);
            TVS[i].setEditable(false);
            DSesion.add(TVS[i]);
        }
        //Vista Respaldo
        DRespaldos.setLayout(new GridLayout(0,1));
        DRespaldos.setSize(300,300);
        DRespaldos.setDefaultCloseOperation(1);
        DRespaldos.setLocationRelativeTo(null);  
        P1R.setLayout(new GridLayout(0,2));
        P2R.setLayout(new GridLayout(0,2));
        for(int i=0;i<5;i++){
            LVR1[i] = new JLabel(SLVR1[i]);
            P1R.add(LVR1[i]); 
            if(i!=3){
                TVR1[i]= new JTextField();
                if(i!=0)
                    TVR1[i].setEditable(false);
                P1R.add(TVR1[i]);
            }else{
                P1R.add(PVR1);
                PVR1.setEditable(false);
            }
            if(i==0){
                P1R.add(BArchivoR1);
                P1R.add(new JLabel(""));
            }
        }  
        P1R.add(CBR1);
        P1R.add(new JLabel(""));
        P1R.add(BAR1);
        
        PRespaldos.addTab("Globales", null, P1R, "Globales");
        P2R.setLayout(new GridLayout(0,2));
        for(int i=0;i<5;i++){
            LVR2[i] = new JLabel(SLVR2[i]);
            P2R.add(LVR2[i]); 
            if(i!=3){
                TVR2[i]= new JTextField();
                if(i!=0)
                    TVR2[i].setEditable(false);
                P2R.add(TVR2[i]);
            }else{
                P2R.add(PVR2);
                PVR2.setEditable(false);
            }
            if(i==0){
                P2R.add(BArchivoR2);
                P2R.add(new JLabel(""));
            }
        }  
        for(int i=0;i<3;i++){
            CBR2[i]= new JComboBox();
        }
        LVR2[5] = new JLabel(SLVR2[5]);
        P2R.add(LVR2[5]);
        P2R.add(CBR2[0]);
        LVR2[6] = new JLabel(SLVR2[6]);
        P2R.add(LVR2[6]);
        CBR2[1].setEditable(false);
        P2R.add(CBR2[1]);
        LVR2[7] = new JLabel(SLVR2[7]);
        P2R.add(LVR2[7]);
        CBR2[2].addItem("Plain");
        CBR2[2].addItem("Custom");
        CBR2[2].addItem("TAR");
        P2R.add(CBR2[2]);
        
        BGR2.add(RBER2);
        BGR2.add(RBDR2);
        BGR2.add(RBTR2);
        P2R.add(RBER2);
        P2R.add(RBDR2);
        P2R.add(RBTR2);
        RBTR2.setSelected(true);
        P2R.add(new JLabel(""));
        
        P2R.add(BAR2);
        PRespaldos.addTab("Especificos", null, P2R, "Especificos");
        DRespaldos.add(PRespaldos);
        //Consultas
        DConsulta.setLayout(null);
        DConsulta.setSize(450,150);
        DConsulta.setDefaultCloseOperation(1);
        DConsulta.setLocationRelativeTo(null);
        CBC[0]= new JComboBox();
        CBC[0].setBounds(10,10,150,30);
        DConsulta.add(CBC[0]);
        CBC[1]= new JComboBox();
        CBC[1].setBounds(170,10,100,30);
        DConsulta.add(CBC[1]);
        LVC.setBounds(280,10,70,30);
        TVC.setBounds(350,10,60,30);
        TVC.setEditable(false);
        DConsulta.add(LVC);
        DConsulta.add(TVC);
        TC= new JTable();
        JSPC= new JScrollPane(TC, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JSPC.setBounds(10,60,400,400);
        DConsulta.add(TC);
        //Tabla mas Grande
        DTablaG.setLayout(null);
        DTablaG.setSize(500,100);
        DTablaG.setDefaultCloseOperation(1);
        DTablaG.setLocationRelativeTo(null);
        CBTG.setBounds(10,10,150,30);
        DTablaG.add(CBTG);
        
        LVTGN.setBounds(180,10,100,30);
        TVTGN.setBounds(280,10,70,30);
        TVTGN.setEditable(false);
        DTablaG.add(LVTGN);
        DTablaG.add(TVTGN);
        
        LVTGT.setBounds(360,10,50,30);
        TVTGT.setBounds(410,10,60,30);
        TVTGT.setEditable(false);
        DTablaG.add(LVTGT);
        DTablaG.add(TVTGT);
    }
    public void HazEscuchas(){
        BVL[0].addActionListener(this);
        BVL[1].addActionListener(this);
        for(int i=0;i<BVM.length;i++)
            BVM[i].addActionListener(this);
        BArchivoR1.addActionListener(this);
        BArchivoR2.addActionListener(this);
        BAR1.addActionListener(this);
        BAR2.addActionListener(this);
        
        DMenu.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                activa=0;    
            }
        }); 
        DSesion.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                activa=1;    
            }
        }); 
        DRespaldos.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                activa=1;    
            }
        });
        DConsulta.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                activa=1;    
            }
        });
        
        DTablaG.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                activa=1;    
            }
        });
        CBR2[0].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                
                if(arg0.getStateChange()==ItemEvent.SELECTED && CBR2[0].getSelectedIndex()!=0){
                    Object aux[]=TablasBD(CBR2[0].getSelectedItem().toString());
                    CBR2[1].removeAllItems();
                    CBR2[1].addItem("-Todas-");
                    for(int i=0;i<aux.length;i++){
                        CBR2[1].addItem(aux[i]);
                    }
                }
            }
        });
        CBC[0].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {   
                if(arg0.getStateChange()==ItemEvent.SELECTED && CBC[0].getSelectedIndex()!=0){
                    Object aux[]=TablasBD(CBC[0].getSelectedItem().toString());
                    CBC[1].removeAllItems();
                    CBC[1].addItem("-Seleccione-");
                    for(int i=0;i<aux.length;i++){
                        CBC[1].addItem(aux[i]);
                    }
                    CBC[1].setSelectedIndex(0);
                    TVC.setText(TamañoBD(CBC[0].getSelectedItem().toString()));
                }
            }
        });
        CBC[1].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                if(arg0.getStateChange()==ItemEvent.SELECTED && CBC[1].getSelectedIndex()!=0){
                    TC= new JTable(ConsultaTabla(CBC[0].getSelectedItem().toString(),CBC[1].getSelectedItem().toString()),cols);
                    JSPC= new JScrollPane(TC, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    JSPC.setBounds(10,60,400,400);
                    DConsulta.add(JSPC);
                    DConsulta.setSize(450,550);
                    DConsulta.setLocationRelativeTo(null);
                    DConsulta.setVisible(true);
                }
            }
        });
        //Tabla mas Grande
        CBTG.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {   
                if(arg0.getStateChange()==ItemEvent.SELECTED && CBTG.getSelectedIndex()!=0){
                    TablaMG(CBTG.getSelectedItem().toString());
                }
            }
        });
    }
    public void actionPerformed(ActionEvent e){
        //Login
      //  System.out.println(activa);
        if(activa==0){
            if(e.getSource()==BVL[0]){
                if(Conexion(TVL[0].getText(),TVL[1].getText(),PVL.getText(),TVL[3].getText(),TVL[4].getText())){
                    activa=1;
                    DMenu.show();
                }
                return;
            }
            if(e.getSource()==BVL[1]){  
                System.exit(0);
                return;
            }
        }
        // Menu
        if(activa==1){
            activa=2;
            if(e.getSource()==BVM[0]){ 
                DatosSesion();
                DSesion.show();
                return;
            }
            if(e.getSource()==BVM[1]){ 
                Object aux[]=BaseDatos();
                CBC[0].removeAllItems();
                CBC[0].addItem("-Todas-");
                for(int i=0;i<aux.length;i++){
                    CBC[0].addItem(aux[i]);
                }
                DConsulta.show();
                return;
            }
            if(e.getSource()==BVM[2]){ 
               Object aux[]=BaseDatos();
                CBTG.removeAllItems();
                CBTG.addItem("-Todas-");
                for(int i=0;i<aux.length;i++){
                    CBTG.addItem(aux[i]);
                }
              DTablaG.show();
                return;
            }
            if(e.getSource()==BVM[3]){
                for(int i=1;i<4;i++){
                    if(i!=3){
                        TVR1[i].setText(TVL[i-1].getText());
                        TVR2[i].setText(TVL[i-1].getText());
                    }else{
                        TVR1[i+1].setText(TVL[i].getText());
                        TVR2[i+1].setText(TVL[i].getText());
                    }
                }
                PVR1.setText(PVL.getText());
                PVR2.setText(PVL.getText());
                Object aux[]=BaseDatos();
                CBR2[0].removeAllItems();
                CBR2[0].addItem("-Todas-");
                for(int i=0;i<aux.length;i++){
                    CBR2[0].addItem(aux[i]);
                }
                DRespaldos.show();
                return;
            }
        }
         if(activa==2){
             String Direccion="";
            if(e.getSource()==BArchivoR1){ 
                Direccion =guardarArchivo();
                TVR1[0].setText(Direccion);
                return;
            }
            if(e.getSource()==BArchivoR2){ 
                Direccion =guardarArchivo();
                TVR2[0].setText(Direccion);
                return;
            }
            if(e.getSource()==BAR1){
                Restaurar(!CBR1.isSelected(),TVR1[1].getText(),TVR1[2].getText(),PVR1.getText(),TVR1[4].getText(),null,TVR1[0].getText(),null,0,0);
                return;
            }
            if(e.getSource()==BAR2){ 
                int tipo=RBER2.isSelected()==true?1:RBDR2.isSelected()==true?2:3;
                Restaurar(true,TVR2[1].getText(),TVR2[2].getText(),PVR2.getText(),TVR2[4].getText(),CBR2[0].getSelectedIndex()==0?null:CBR2[0].getSelectedItem().toString(),TVR2[0].getText(),CBR2[1].getSelectedIndex()==0?null:CBR2[1].getSelectedItem().toString(),tipo,CBR2[2].getSelectedIndex());
                return;
            }
         }
    }
    public boolean DatosSesion(){
        String C[]={"inet_server_addr()","current_user","inet_server_port()","current_database()","pg_postmaster_start_time()","current_date","now() - pg_postmaster_start_time()"," datname FROM pg_database WHERE datistemplate = false"," pg_size_pretty(pg_database_size('"+DB+"'))"};
        for(int i=0;i<9;i++){
            try {
                s = connection.createStatement();
                rs = s.executeQuery("SELECT "+C[i]);
                String string = "";
                while (rs.next()) {
                    if(i!=7)
                        string += rs.getString(1);
                    else
                        string += rs.getString(1)+", ";    
                }
                TVS[i].setText(string);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Error al Obtener Datos de Sesiòn "+e,"Advertencia",JOptionPane.WARNING_MESSAGE);
            }
        }
        return true;
    }
    public Object[] BaseDatos(){
        ArrayList<String> aux = new ArrayList<String>();
        try {
            s = connection.createStatement();
            rs = s.executeQuery("SELECT datname FROM pg_database WHERE datistemplate = false");
            String string = "";
            while (rs.next())
                aux.add(rs.getString(1));
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null,"Problema al mostrar Base de Datos "+e,"Advertencia",JOptionPane.WARNING_MESSAGE);
       }
        return aux.toArray();
    }
    public Vector ConsultaTabla(String BD,String Tabla){
        Conexion(this.host,this.usuario,this.password,this.puerto,BD);
        cols= new Vector();
        Vector fil= new Vector();
        Vector fils= new Vector();
         try {
            s = connection.createStatement();
            rs = s.executeQuery("SELECT * FROM "+Tabla);
            ResultSetMetaData metaDatos = rs.getMetaData();
            String string = "";
            int j=1;
            while (rs.next()) {
                for(int i=0;i<metaDatos.getColumnCount();i++){
                        if(j==1)
                            cols.add("Col-"+i);
                        fil.add(rs.getString(i+1));
                       
                }
                 fils.add(fil);
                 fil= new Vector();
                 j++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Tabla Sin datos para mostrar en Consulta: "+e,"Advertencia",JOptionPane.WARNING_MESSAGE);
         }
        return fils;
    }
    public void TablaMG(String BD){
        Conexion(this.host,this.usuario,this.password,this.puerto,BD);
         try {
            TVTGN.setText("No Tablas");
            TVTGT.setText("No Tamaño");
            s = connection.createStatement();
            rs = s.executeQuery("SELECT relname,pg_size_pretty(pg_total_relation_size(c.oid)) "
                              + "FROM  pg_class c LEFT JOIN pg_namespace n ON (n.oid = c.relnamespace) " 
                              + "WHERE  n.nspname NOT IN ('pg_catalog', 'information_schema') AND c.relkind <> 'i' AND  n.nspname !~ '^pg_toast' " 
                              + "ORDER BY  pg_total_relation_size(c.oid) DESC " 
                              + "LIMIT 1;");
            String string = "";
            int j=1;
            while (rs.next()) {
                TVTGN.setText(rs.getString(1));
                TVTGT.setText(rs.getString(2));
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Base datos sin Tablas Publicas: "+e,"Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }
    public Object[] TablasBD(String BD){
        Conexion(this.host,this.usuario,this.password,this.puerto,BD);
        ArrayList<String> aux = new ArrayList<String>();
        try {
            s = connection.createStatement();
            rs = s.executeQuery("SELECT tablename FROM pg_tables WHERE schemaname = 'public' ");
            String string = "";
            while (rs.next())
                aux.add(rs.getString(1));
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Base datos sin Tablas Publicas: "+e,"Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        return aux.toArray();
    }
    public String TamañoBD(String BD){
        Conexion(this.host,this.usuario,this.password,this.puerto,BD);
        try {
            s = connection.createStatement();
            rs = s.executeQuery("SELECT pg_size_pretty(pg_database_size('"+DB+"'))");
            String string = "";
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Error al Consultar: "+e,"Advertencia",JOptionPane.WARNING_MESSAGE);
       }
        return "";
    }
    public static void main(String[] args) {
        new ProyectoDBA();
    }
    private String guardarArchivo() {
        try
        {
            String nombre="";
            JFileChooser file=new JFileChooser();
            file.showSaveDialog(this);
            File guarda =file.getSelectedFile();
            if(guarda !=null)
            {
                return guarda.toString();
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No se a especificado Ruta..","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        return "";
    }
    public boolean Restaurar(boolean todo,String host,String usuario,String password,String puerto,String BD,String direccion,String tabla,int tipo,int formato){
         try {
            Runtime r = null;
            Process p;
            ProcessBuilder pb=null;
            ArrayList<String> cmds = new ArrayList<String>();
            cmds.add("-h");
            cmds.add(host);
            cmds.add("-p");
            cmds.add(puerto);
            cmds.add("-U");
            cmds.add(usuario);
            cmds.add("-f");
            cmds.add(direccion);
                
            if(BD==null){
                cmds.add(0,"C:/postgres/9.2/bin/pg_dumpall.exe");
                if(!todo)    
                    cmds.add(1,"-g");
            }else{
                cmds.add(0,"-i");
                cmds.add(0,"C:/postgres/9.2/bin/pg_dump.exe");  
                cmds.add(2,"-v"); 
                cmds.add(2,"-b"); 
                cmds.add(BD);
                cmds.add(2,"-F");
                if(formato==0)
                    cmds.add(3,"p");
                if(formato==1)
                    cmds.add(3,"c");
                if(formato==2)
                    cmds.add(3,"t");
                if(tabla!=null){
                    cmds.add(2,"-t");
                    cmds.add(3,tabla);
                }
                if(tipo!=3){
                    if(tipo==1){
                        cmds.add(1,"-s");//Solo esquemas
                    }else{
                        cmds.add(1,"-a");//Solo datos
                    }
                }
            }
            System.out.println(cmds.toString());
            r = Runtime.getRuntime();
            pb = new ProcessBuilder(cmds);
            pb.environment().put("PGPASSWORD", password);
            pb.redirectErrorStream(true);
            p = pb.start();    
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al Respaldar.."+e,"Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        return true;
    }
}