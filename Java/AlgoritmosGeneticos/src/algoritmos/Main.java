package algoritmos;
import org.jfree.*;
import algoritmosgeneticos.Algoritmos;
import algoritmosgeneticos.Poblaciones;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import componentes.*;
import jtextfield.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main extends JFrame implements ActionListener {
	
	private JTextInteger txi_poblacion;
	private JTextField txt_funcion;
	private JTextInteger txi_noGeneraciones;
	private JRadioButton rbn_x;
	private JRadioButton rbn_y;
	private JTextFloat txf_X;
	private JTextFloat txf_Y;
	private JTextFloat txf_cruza;
	private JTextFloat txf_mutacion;
	private JComboBox<String> cmb_seleccion;
	private JComboBox<String> cmb_cruza;
        private JComboBox<String> cmb_mutacion;
        private JComboBox<String> cmb_representacion;
	private JTextInteger txi_TamanioGenotipo;	
        private JTextInteger txi_TamanioCromosoma;

	private JButton btn_calcular;
	private JButton btn_borrar;
        private JButton btn_ayuda;
	private JTextArea txa_resultado;
	private Torneo torneo;
	private Font fontTxt = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	
	public Main() {     
            super("IA Algoritmos Genoticos");
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
            }catch(Exception e){}
		iniciarComponentes();
		crearInterfaz();
		agregarListeners();
		setVisible(true);
	}
	
	private void iniciarComponentes() {
		txi_poblacion = new JTextInteger(6, false);
		txi_poblacion.setText("8");
                txt_funcion = new JTextField(12);
		txt_funcion.setText("(x*x) + (2*x) - 3");
		txt_funcion.setEditable(true);
		txi_noGeneraciones = new JTextInteger(6, false);
                txi_noGeneraciones.setText("10");

		rbn_x = new JRadioButton("Variable X");
		rbn_x.setSelected(true);
		rbn_y = new JRadioButton("Variable Y");
		rbn_y.setSelected(false);
		txf_X = new JTextFloat(6);
		txf_Y = new JTextFloat(6);
		txf_cruza = new JTextFloat(8, false);
		txf_cruza.setText("0.01");
                txf_mutacion = new JTextFloat(8, false);
		txf_mutacion.setText("0.1");
                cmb_seleccion = new JComboBox<String>(new String[] {
				"Torneo",
                                "Ruleta"
		});
                cmb_cruza = new JComboBox<String>(new String[] {
				"Un Punto",
                                "Dos Puntos",
                                "Uniforme"
		});
                cmb_mutacion = new JComboBox<String>(new String[] {
				"Inserción",
                                "Desplazamiento"
		});
		cmb_representacion = new JComboBox<String>(new String[] {
				"Binaria",
				"Decimal"
		});
		txi_TamanioCromosoma = new JTextInteger(6, false);
                txi_TamanioCromosoma.setText("8");
                txi_TamanioGenotipo = new JTextInteger(6,false);
                txi_TamanioGenotipo.setText("4");
		btn_calcular = new JButton("Calcular");
		btn_borrar = new JButton("Borrar");
                btn_ayuda=new JButton("Ayuda");
		txa_resultado = new JTextArea(33, 70);
		txa_resultado.setEditable(false);
		txa_resultado.setTabSize(4);
		txa_resultado.setFont(fontTxt);
	}
	
	private void crearInterfaz() {
		JPanel	pnl_arriba=new JPanel();
		pnl_arriba.setLayout(new BorderLayout());
		pnl_arriba.add(new JLabel("<html><h1> Algoritmos Genéticos</h1></html>"),BorderLayout.CENTER);
		pnl_arriba.setBorder(new EmptyBorder(10,10,10,10));
		
		
		JPanel pnl_centro = new JPanel();
		pnl_centro.setLayout(new GridLayout(0, 1, 10, 10));
		pnl_centro.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		PnlRenglon pnl_poblacion = new PnlRenglon(2);
		pnl_poblacion.add(new JLabel("Tamañoo de la Población:"));
		pnl_poblacion.add(txi_poblacion);
		
		PnlRenglon pnl_funcion = new PnlRenglon(2);
		pnl_funcion.add(new JLabel("Función de Aptitud:"));
		pnl_funcion.add(txt_funcion);
		
		PnlRenglon pnl_noGeneraciones = new PnlRenglon(2);
		pnl_noGeneraciones.add(new JLabel("Número de generaciones:"));
		pnl_noGeneraciones.add(txi_noGeneraciones);
		
		PnlRenglon pnl_cruza = new PnlRenglon(2);
		pnl_cruza.add(new JLabel("Tipo de Cruza:"));
		pnl_cruza.add(cmb_cruza);
		PnlRenglon pnl_Pcruza = new PnlRenglon(2);
                pnl_Pcruza.add(new JLabel("Porcentaje de Cruza %:"));
		pnl_Pcruza.add(txf_cruza);
		
		PnlRenglon pnl_mutacion = new PnlRenglon(2);
                pnl_mutacion.add(new JLabel("Tipo de Mutación:"));
		pnl_mutacion.add(cmb_mutacion);
		PnlRenglon pnl_Pmutacion = new PnlRenglon(2);
                pnl_Pmutacion.add(new JLabel("Porcentaje de Mutación %:"));
		pnl_Pmutacion.add(txf_mutacion);
		
		PnlRenglon pnl_seleccion = new PnlRenglon(2);
		pnl_seleccion.add(new JLabel("Tipo de Selección:"));
		pnl_seleccion.add(cmb_seleccion);
		
		PnlRenglon pnl_representacion = new PnlRenglon(2);
		pnl_representacion.add(new JLabel("Tipo de Representación:"));
		pnl_representacion.add(cmb_representacion);
		
		PnlRenglon pnl_Genotipos = new PnlRenglon(2);
		pnl_Genotipos.add(new JLabel(" Tamaño de Genotipos:"));
		pnl_Genotipos.add(txi_TamanioGenotipo);
		PnlRenglon pnl_Cromosoma = new PnlRenglon(2);
		pnl_Cromosoma.add(new JLabel("Tamaño de Cromosoma:"));
		pnl_Cromosoma.add(txi_TamanioCromosoma);
		
		JPanel pnl_x = new JPanel();
		pnl_x.add(rbn_x);
		PnlRenglon pnl_limX = new PnlRenglon(4);
		pnl_limX.add(new JLabel("Valor de X:"));
		pnl_limX.add(txf_X);
		
		JPanel pnl_y = new JPanel();
		pnl_y.add(rbn_y);
		PnlRenglon pnl_limY = new PnlRenglon(4);
		pnl_limY.add(new JLabel("Valor de Y:"));
		pnl_limY.add(txf_Y);
		
		PnlRenglon pnl_botones = new PnlRenglon(3);
		pnl_botones.add(btn_ayuda);
                pnl_botones.add(btn_borrar);
		pnl_botones.add(btn_calcular);
		
		pnl_centro.add(pnl_poblacion);
		pnl_centro.add(pnl_funcion);
		pnl_centro.add(pnl_noGeneraciones);
		pnl_centro.add(pnl_seleccion);
		pnl_centro.add(pnl_cruza);
		pnl_centro.add(pnl_mutacion);
		pnl_centro.add(pnl_Pcruza);
		pnl_centro.add(pnl_Pmutacion);
		pnl_centro.add(pnl_representacion);
		pnl_centro.add(pnl_Genotipos);		
                pnl_centro.add(pnl_Cromosoma);		
                //pnl_centro.add(new JLabel("                         (Cada genotipo est� formado por 4 alelos)"));
		//pnl_centro.add(new JSeparator());
		//pnl_centro.add(pnl_x);
		//pnl_centro.add(pnl_limX);
		//pnl_centro.add(pnl_y);
		//pnl_centro.add(pnl_limY);
		pnl_centro.add(new JSeparator());
		pnl_centro.add(pnl_botones);
		add(pnl_centro, BorderLayout.CENTER);
		
		JPanel pnl_este = new JPanel();
		pnl_este.setBorder(new EmptyBorder(10, 10, 10, 10));
		JScrollPane scrollPane = new JScrollPane(txa_resultado);
		pnl_este.add(scrollPane);
		add(pnl_este, BorderLayout.EAST);
		
		add(pnl_arriba,BorderLayout.NORTH);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void agregarListeners() {
		rbn_x.addActionListener(this);
		rbn_y.addActionListener(this);
		btn_calcular.addActionListener(this);
		btn_borrar.addActionListener(this);
		btn_ayuda.addActionListener(this);

        }
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btn_calcular) {
			calcular();
		}
		if(ae.getSource() == btn_borrar) {
			txa_resultado.setText("");
		}
		if(ae.getSource() == rbn_x) {
			if(rbn_x.isSelected())
				habilitarVariableX(true);
			else
				habilitarVariableX(false);
		}
		if(ae.getSource() == rbn_y) {
			if(rbn_y.isSelected())
				habilitarVariableY(true);
			else
				habilitarVariableY(false);
		}
                if (ae.getSource() == btn_ayuda) {
                    new Ayuda();
                }
	}
	
	private void habilitarVariableX(boolean habilitar) {
		txf_X.setEditable(habilitar);
	}
	
	private void habilitarVariableY(boolean habilitar) {
		txf_Y.setEditable(habilitar);
		//txf_limMinY.setEditable(habilitar);
	}
	
	private void calcular() {
            Algoritmos A= new Algoritmos(txi_poblacion.getValor(),txi_TamanioCromosoma.getValor(), txi_TamanioGenotipo.getValor());
            A.setBase(cmb_representacion.getSelectedIndex()==0?1:10);
            A.setFuncion(txt_funcion.getText());
            int sS = cmb_seleccion.getSelectedIndex()+1;
            int sC = cmb_cruza.getSelectedIndex()+1;
            double pC = txf_cruza.getValor();
            int sM =cmb_mutacion.getSelectedIndex()+1;
            double pM = txf_mutacion.getValor();
            //System.out.print(sS);
            
            A.hacerAlgoritmo(sS,sC,pC,sM,pM,txi_noGeneraciones.getValor(),txa_resultado);
            Poblaciones UltimaGeneracion = A.hacerAlgoritmo(sS,sC,pC,sM,pM,txi_noGeneraciones.getValor(),txa_resultado);
            DefaultCategoryDataset Datos = new DefaultCategoryDataset();
            JFreeChart Grafica;
            Grafica = ChartFactory.createBarChart("Relación Aptitud con la Poblacion",
            "Cromosomas", "Aptitud", Datos,
            PlotOrientation.VERTICAL, true, true, false);
            ChartPanel Panel = new ChartPanel(Grafica);
            JFrame Ventana = new JFrame("Aptitud de los Cromosomas");
            Ventana.getContentPane().add(Panel);
            Ventana.pack();
            Ventana.setVisible(true);
            Ventana.setDefaultCloseOperation(1);
            for (int i = 0; i < UltimaGeneracion.Poblacion.length; i++) {
                    Datos.addValue(UltimaGeneracion.Poblacion[i].getAptitud(), "Aptitud", "Cromosoma #"+(i+1));    
            }
            

           // torneo = new Torneo(txi_poblacion.getValor(), txi_numGenotipos.getValor(), 
		//		txi_noGeneraciones.getValor(), cmb_representacion.getSelectedIndex(),
		//		txf_cruza.getValor(), txf_mutacion.getValor(), txa_resultado);
		//torneo.realizarTorneo();
	}

	

}
