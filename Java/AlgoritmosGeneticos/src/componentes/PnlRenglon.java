package componentes;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PnlRenglon extends JPanel {
	
	public PnlRenglon(int columnas) {
		super();
		setLayout(new GridLayout(0, columnas, 10, 10));
		setBorder(new EmptyBorder(0, 0, 0, 0));
	}

}
