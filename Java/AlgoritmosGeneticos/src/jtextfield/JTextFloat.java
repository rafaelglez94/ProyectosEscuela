package jtextfield;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;


public class JTextFloat extends JTextField implements KeyListener, FocusListener {
	
	private boolean negativos = true;
	private int tamano;
	private Color colorFondoNormal;
	private Color colorFondoFoco;
	private Color colorBordeNormal = Color.GRAY;
	private Color colorBordeFoco = Color.CYAN;
	private int alineadoNormal = JTextField.RIGHT;
	private int alineadoFoco = JTextField.LEFT;
	private Font fontTextoNormal = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private Font fontTextoFoco = new Font(Font.SANS_SERIF, Font.BOLD, 12);

	public JTextFloat(int tamano) {
		super(tamano);
		this.tamano = tamano;
		addKeyListener(this);
		addFocusListener(this);
		colorFondoNormal = getBackground();
		colorFondoFoco = getBackground();
	}
	
	public JTextFloat(int tamano, boolean negativos) {
		super(tamano);
		this.tamano = tamano;
		this.negativos = negativos;
		addKeyListener(this);
		addFocusListener(this);
		colorFondoNormal = getBackground();
		colorFondoFoco = getBackground();
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == 36 || ke.getKeyCode() == 37
				|| ke.getKeyCode() == 39) {
			ke.consume();
			return;
		}

		if (ke.isControlDown()) {

			if (ke.getKeyCode() == KeyEvent.VK_X
					|| ke.getKeyCode() == KeyEvent.VK_C
					|| ke.getKeyCode() == KeyEvent.VK_V) {
				ke.consume();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE
				&& getText().indexOf(".") < 0) {
			String Res = "", Cad = "";
			int Car = 0;
			Cad = quitaComas(getText());
			for(int i = Cad.length() - 1; i >= 0; i--) {
				if (i == 0 && Cad.charAt(i) == '-') {
					Res = "-" + Res;
					continue;
				}

				Car++;

				if (Car % 4 == 0) {
					Res = "," + Res;
					Car = 1;
				}
				Res = Cad.charAt(i) + Res;
			}
			setText(Res);
		}

	}

	@Override
	public void keyTyped(KeyEvent ke) {
		if(ke.getKeyChar() == '-' && !negativos) {
			ke.consume();
			return;
		}
		if (getText().length() == tamano) {
			ke.consume();
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		if (!(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'
				|| ke.getKeyChar() == '-' || ke.getKeyChar() == '.')) {
			ke.consume();
			return;
		}
		if (ke.getKeyChar() == '-' && getText().length() != 0) {
			ke.consume();
			return;
		}
		// Checar si ya existen puntos
		if (ke.getKeyChar() == '.' && getText().indexOf(".") >= 0) {
			ke.consume();
			return;
		}
		if (ke.getKeyChar() == '.' && getText().length() == 0) {
			setText("0");
			return;
		}
		if (ke.getKeyChar() == '.')
			return;
		if (getText().indexOf(".") < 0) {

			String Cad = getText();
			String Res = "";
			int Cont = 1;

			for (int i = Cad.length() - 1; i >= 0; i--) {
				if (i == 0 && Cad.charAt(i) == '-') {
					Res = "-" + Res;
					continue;
				}
				Cont++;
				if (Cad.charAt(i) == ',')
					continue;
				if (Cont % 4 == 0)
					Res = "," + Res;
				Res = Cad.charAt(i) + Res;
			}
			setText(Res);
		}

	}

	@Override
	public void focusGained(FocusEvent fe) {
		if(!isEditable())
			return;
		JTextField txt = (JTextField) fe.getSource();
		txt.setBackground(colorFondoFoco);
		txt.setFocusTraversalKeysEnabled(false);
		txt.selectAll();
		txt.setBorder(new LineBorder(colorBordeFoco));
		txt.setHorizontalAlignment(alineadoFoco);
		txt.setFont(fontTextoFoco);

	}

	@Override
	public void focusLost(FocusEvent fe) {
		if(!isEditable())
			return;
		JTextField txt = (JTextField) fe.getSource();
		txt.setBackground(colorFondoNormal);
		txt.setBorder(new LineBorder(colorBordeNormal));
		txt.setFont(fontTextoNormal);
		txt.setHorizontalAlignment(alineadoNormal);

	}

	public float getValor() {
		if (getText().length() == 0 || (getText().length() == 1 && getText().charAt(0) == '-')) {
			setText("0.0");
			return 0;
		}
		if(getText().charAt(getText().length()-1) == 'E')
			setText(getText().substring(0, getText().length()-1));
		if(getText().charAt(getText().length()-1) == '.')
			setText(getText()+"0");

		return Float.parseFloat(quitaComas(getText()));
	}
	
	public String getCadena() {
		if (getText().length() == 0 || (getText().length() == 1 && getText().charAt(0) == '-')) {
			setText("0.0");
			return "0.0";
		}
		if(getText().charAt(getText().length()-1) == 'E')
			setText(getText().substring(0, getText().length()-1));
		if(getText().charAt(getText().length()-1) == '.')
			setText(getText()+"0");

		return quitaComas(getText());
	}

	public String quitaComas(String conComas) {
		String sinComas = "";
		for (int i = 0; i < conComas.length(); i++)
			if (conComas.charAt(i) != ',')
				sinComas = sinComas + conComas.charAt(i);
		return sinComas;
	}

	public boolean isNegativos() {
		return negativos;
	}

	public void setNegativos(boolean negativos) {
		this.negativos = negativos;
	}

	public Color getColorFondoNormal() {
		return colorFondoNormal;
	}

	public void setColorFondoNormal(Color colorFondoNormal) {
		this.colorFondoNormal = colorFondoNormal;
	}

	public Color getColorFondoFoco() {
		return colorFondoFoco;
	}

	public void setColorFondoFoco(Color colorFondoFoco) {
		this.colorFondoFoco = colorFondoFoco;
	}

	public Color getColorBordeNormal() {
		return colorBordeNormal;
	}

	public void setColorBordeNormal(Color colorBordeNormal) {
		this.colorBordeNormal = colorBordeNormal;
	}

	public Color getColorBordeFoco() {
		return colorBordeFoco;
	}

	public void setColorBordeFoco(Color colorBordeFoco) {
		this.colorBordeFoco = colorBordeFoco;
	}

	public Font getFontTextoNormal() {
		return fontTextoNormal;
	}

	public void setFontTextoNormal(Font fontTextoNormal) {
		setFont(fontTextoNormal);
		this.fontTextoNormal = fontTextoNormal;
	}

	public Font getFontTextoFoco() {
		return fontTextoFoco;
	}

	public void setFontTextoFoco(Font fontTextoFoco) {
		this.fontTextoFoco = fontTextoFoco;
	}

	public int getAlineadoNormal() {
		return alineadoNormal;
	}

	public void setAlineadoNormal(int alineadoNormal) {
		setHorizontalAlignment(alineadoNormal);
		this.alineadoNormal = alineadoNormal;
	}

	public int getAlineadoFoco() {
		return alineadoFoco;
	}

	public void setAlineadoFoco(int alineadoFoco) {
		this.alineadoFoco = alineadoFoco;
	}

	public int getTamano() {
		return tamano;
	}

}