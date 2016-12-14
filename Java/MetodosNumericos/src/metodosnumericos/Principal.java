package metodosnumericos;

import Ayuda.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Principal extends javax.swing.JFrame {

    private double a;
    private double b;
    private double error;
    private double dx;
    private double xD;
    private int iteraciones;
    private Double raiz;
    Matriz m;
    private Double[][]MatrizFinal;
    private Double[]Resultado;
    public Principal() {
        initComponents();
        setLocationRelativeTo(null);
        jdgRaicez.setLocationRelativeTo(null);
        jdgRaicez.setModal(true);
        jdgMatriz.pack();
        jdgMatriz.setLocationRelativeTo(null);
        jdgMatriz.setModal(true);
        jdgInterpolacion.pack();
        jdgInterpolacion.setLocationRelativeTo(null);
        jdgInterpolacion.setModal(true);
        jdgIntegrantes.pack();
        jdgIntegrantes.setLocationRelativeTo(null);
        jdgIntegrantes.setModal(true);
        jdgIyD.pack();
        jdgIyD.setLocationRelativeTo(null);
        jdgIyD.setModal(true);
    }
    public static void main(String args[]) {
       //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
       /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
        */
       try {
           for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
               if ("Nimbus".equals(info.getName())) {
                   javax.swing.UIManager.setLookAndFeel(info.getClassName());
                   break;
               }
           }
       } catch (ClassNotFoundException ex) {
           java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       } catch (InstantiationException ex) {
           java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       } catch (IllegalAccessException ex) {
           java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       } catch (javax.swing.UnsupportedLookAndFeelException ex) {
           java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       }
       //</editor-fold>

       java.awt.EventQueue.invokeLater(new Runnable() {
           public void run() {
               new Principal().setVisible(true);
           }
       });
    }
    public void graficar(String titulo)
    {
        Grafica grafica = new Grafica(raiz,titulo);
        panelRaices.remove(1);
        panelRaices.addTab("Grafica",grafica);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdgRaicez = new javax.swing.JDialog();
        jdgRaicez.setSize(500,400);
        panelRaices = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtb = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtfuncion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txta = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txterror = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtinteraciones = new javax.swing.JTextField();
        CBMetodo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        lblInformacion = new javax.swing.JLabel();
        jdgMatriz = new javax.swing.JDialog();
        jLabel9 = new javax.swing.JLabel();
        txtTamano = new javax.swing.JTextField();
        btngenerar = new javax.swing.JButton();
        CBMetodoSE = new javax.swing.JComboBox();
        btncalcularSE = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbmatrizFinal = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtbinicial = new javax.swing.JTable();
        jpResultado = new javax.swing.JPanel();
        lblresultadoM = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jdgInterpolacion = new javax.swing.JDialog();
        jLabel11 = new javax.swing.JLabel();
        txtnpuntos = new javax.swing.JTextField();
        btngenerarIL = new javax.swing.JButton();
        CBmetodoIL = new javax.swing.JComboBox();
        btninterpolar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtXIL = new javax.swing.JTextField();
        lblResultadoIL = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtordenIL = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtbInterpolacion = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtblResultadoIL = new javax.swing.JTable();
        jdgIyD = new javax.swing.JDialog();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtfunciond = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtxder = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txterrord = new javax.swing.JTextField();
        CBmetodosD = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        lblResultadod = new javax.swing.JLabel();
        btnCalcularD = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        CBmetodosI = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        txtfI = new javax.swing.JTextField();
        txtaI = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtbI = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtnI = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        lblResultadoI = new javax.swing.JLabel();
        btnintegrar = new javax.swing.JButton();
        jdgIntegrantes = new javax.swing.JDialog();
        jLabel18 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnraices = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        btnMetodoSE = new javax.swing.JButton();
        btnMetodoIL = new javax.swing.JButton();
        btnMetodoIyD = new javax.swing.JButton();
        btnintegrantes = new javax.swing.JButton();

        jdgRaicez.setTitle("Metodos de Raices");

        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblResultado);

        panelRaices.addTab("Tabla", jScrollPane1);
        panelRaices.addTab("Grafica", jTabbedPane4);

        jLabel2.setText("Valor Final");
        jLabel2.setToolTipText("");

        jButton2.setText("Calcular");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Calcular(evt);
            }
        });

        txtb.setText(" ");

        jLabel3.setText("Función");

        jLabel4.setText("Valor Inicial");

        jLabel5.setText("Error");

        jLabel6.setText("Interaciones");

        CBMetodo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Biseccion", "Secante", "Falsa Posición", "Newton Raphson", "Aproximaciones Sucesivas" }));

        jLabel7.setText("Metodo");

        lblInformacion.setText("..");

        javax.swing.GroupLayout jdgRaicezLayout = new javax.swing.GroupLayout(jdgRaicez.getContentPane());
        jdgRaicez.getContentPane().setLayout(jdgRaicezLayout);
        jdgRaicezLayout.setHorizontalGroup(
            jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdgRaicezLayout.createSequentialGroup()
                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdgRaicezLayout.createSequentialGroup()
                        .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jdgRaicezLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtfuncion)
                                    .addComponent(txterror)))
                            .addGroup(jdgRaicezLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtinteraciones, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBMetodo, 0, 184, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdgRaicezLayout.createSequentialGroup()
                                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                    .addComponent(txta, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(jdgRaicezLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelRaices, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdgRaicezLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(lblInformacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jdgRaicezLayout.setVerticalGroup(
            jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdgRaicezLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtfuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txterror, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtinteraciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(16, 16, 16)
                .addGroup(jdgRaicezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(lblInformacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRaices, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jdgMatriz.setTitle("Sistemas de Ecuaciones");

        jLabel9.setText("Tamaño");

        btngenerar.setText("Generar");
        btngenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngenerarActionPerformed(evt);
            }
        });

        CBMetodoSE.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gauss", "Gauss-Jordan", "Motante", "Cramer", "Jacobi", "Gauss-Seidel" }));

        btncalcularSE.setText("Calcular");
        btncalcularSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularSEActionPerformed(evt);
            }
        });

        jtbmatrizFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jtbmatrizFinal);

        jtbinicial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jtbinicial);

        lblresultadoM.setText("Resultados");

        javax.swing.GroupLayout jpResultadoLayout = new javax.swing.GroupLayout(jpResultado);
        jpResultado.setLayout(jpResultadoLayout);
        jpResultadoLayout.setHorizontalGroup(
            jpResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpResultadoLayout.createSequentialGroup()
                .addComponent(lblresultadoM)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpResultadoLayout.setVerticalGroup(
            jpResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpResultadoLayout.createSequentialGroup()
                .addComponent(lblresultadoM)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jLabel8.setText("Matriz Inicial");

        jLabel10.setText("Matriz Final");

        javax.swing.GroupLayout jdgMatrizLayout = new javax.swing.GroupLayout(jdgMatriz.getContentPane());
        jdgMatriz.getContentPane().setLayout(jdgMatrizLayout);
        jdgMatrizLayout.setHorizontalGroup(
            jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdgMatrizLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jdgMatrizLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTamano, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btngenerar)
                        .addGap(34, 34, 34)
                        .addComponent(CBMetodoSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btncalcularSE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jdgMatrizLayout.createSequentialGroup()
                        .addGroup(jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jpResultado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jdgMatrizLayout.createSequentialGroup()
                                .addGroup(jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jdgMatrizLayout.createSequentialGroup()
                                        .addGap(0, 9, Short.MAX_VALUE)
                                        .addComponent(jLabel8)
                                        .addGap(163, 163, 163))
                                    .addGroup(jdgMatrizLayout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGap(6, 6, 6)))
                                .addGroup(jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(27, 27, 27))))
        );
        jdgMatrizLayout.setVerticalGroup(
            jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdgMatrizLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTamano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btngenerar)
                    .addComponent(CBMetodoSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncalcularSE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdgMatrizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpResultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jdgInterpolacion.setTitle("Interpolacíon Lineal");

        jLabel11.setText("N° de Puntos");

        btngenerarIL.setText("Generar");
        btngenerarIL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngenerarILActionPerformed(evt);
            }
        });

        CBmetodoIL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Newton", "Lagrange", "Minimos Cuadrados" }));
        CBmetodoIL.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CBmetodoILItemStateChanged(evt);
            }
        });
        CBmetodoIL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBmetodoILActionPerformed(evt);
            }
        });

        btninterpolar.setText("Interpolar");
        btninterpolar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninterpolarActionPerformed(evt);
            }
        });

        jLabel12.setText("X:");

        lblResultadoIL.setText("Resultado      Y=");

        jLabel13.setText("Orden");

        txtordenIL.setEnabled(false);

        jtbInterpolacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "X", "Y"
            }
        ));
        jScrollPane4.setViewportView(jtbInterpolacion);

        jTabbedPane1.addTab("Tabla ", jScrollPane4);

        jtblResultadoIL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtblResultadoIL.setEnabled(false);
        jScrollPane5.setViewportView(jtblResultadoIL);

        jTabbedPane1.addTab("Matriz Generada", jScrollPane5);

        javax.swing.GroupLayout jdgInterpolacionLayout = new javax.swing.GroupLayout(jdgInterpolacion.getContentPane());
        jdgInterpolacion.getContentPane().setLayout(jdgInterpolacionLayout);
        jdgInterpolacionLayout.setHorizontalGroup(
            jdgInterpolacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdgInterpolacionLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jdgInterpolacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdgInterpolacionLayout.createSequentialGroup()
                        .addComponent(jTabbedPane1)
                        .addContainerGap())
                    .addGroup(jdgInterpolacionLayout.createSequentialGroup()
                        .addGroup(jdgInterpolacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jdgInterpolacionLayout.createSequentialGroup()
                                .addGroup(jdgInterpolacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jdgInterpolacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtnpuntos, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                    .addComponent(txtordenIL))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btngenerarIL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtXIL, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CBmetodoIL, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btninterpolar))
                            .addGroup(jdgInterpolacionLayout.createSequentialGroup()
                                .addComponent(lblResultadoIL)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(116, 116, 116))))
        );
        jdgInterpolacionLayout.setVerticalGroup(
            jdgInterpolacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdgInterpolacionLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jdgInterpolacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnpuntos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(btngenerarIL)
                    .addComponent(CBmetodoIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btninterpolar)
                    .addComponent(jLabel12)
                    .addComponent(txtXIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jdgInterpolacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtordenIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblResultadoIL)
                .addContainerGap())
        );

        jdgIyD.setTitle("Integración y Derivación");

        jLabel14.setText("Función");

        jLabel15.setText("X");

        jLabel16.setText("Error");

        CBmetodosD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Limites" }));

        jLabel17.setText("Metodo");

        lblResultadod.setText("dx= ");

        btnCalcularD.setText("Evaluar");
        btnCalcularD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txterrord))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfunciond, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel15))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblResultadod)
                        .addGap(130, 130, 130)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtxder, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBmetodosD, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCalcularD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtfunciond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtxder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txterrord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBmetodosD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblResultadod))
                    .addComponent(btnCalcularD, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Derivación", jPanel1);

        jLabel21.setText("Metodo");

        CBmetodosI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Trapecio", "Simpson" }));

        jLabel22.setText("Función");

        jLabel23.setText("a");

        jLabel24.setText("b");

        jLabel25.setText("n");

        lblResultadoI.setText("Integral= ");

        btnintegrar.setText("Integrar");
        btnintegrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnintegrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtaI, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(txtfI))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnI, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbI, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBmetodosI, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)))
                .addGap(35, 35, 35))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblResultadoI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnintegrar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtfI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txtnI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(txtbI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CBmetodosI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResultadoI)
                    .addComponent(btnintegrar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Integración", jPanel2);

        javax.swing.GroupLayout jdgIyDLayout = new javax.swing.GroupLayout(jdgIyD.getContentPane());
        jdgIyD.getContentPane().setLayout(jdgIyDLayout);
        jdgIyDLayout.setHorizontalGroup(
            jdgIyDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jdgIyDLayout.setVerticalGroup(
            jdgIyDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/metodosnumericos/IntegrantesMetodos.png"))); // NOI18N

        javax.swing.GroupLayout jdgIntegrantesLayout = new javax.swing.GroupLayout(jdgIntegrantes.getContentPane());
        jdgIntegrantes.getContentPane().setLayout(jdgIntegrantesLayout);
        jdgIntegrantesLayout.setHorizontalGroup(
            jdgIntegrantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdgIntegrantesLayout.setVerticalGroup(
            jdgIntegrantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("Metodos Numericos");

        btnraices.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnraices.setText("Métodos de Raíces");
        btnraices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MetodoRaices(evt);
            }
        });

        btnsalir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Salir(evt);
            }
        });

        btnMetodoSE.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnMetodoSE.setText("Métodos Matriciales");
        btnMetodoSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetodoSEMetodoRaices(evt);
            }
        });

        btnMetodoIL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnMetodoIL.setText("Métodos Interpolación Lineal");
        btnMetodoIL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetodoILMetodoRaices(evt);
            }
        });

        btnMetodoIyD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnMetodoIyD.setText("Métodos de Integración y Derivación");
        btnMetodoIyD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetodoIyDMetodoRaices(evt);
            }
        });

        btnintegrantes.setText("Integrantes");
        btnintegrantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnintegrantesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel1)
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnraices, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnMetodoIL, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnMetodoSE, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnMetodoIyD)
                        .addGap(57, 57, 57))
                    .addComponent(btnintegrantes, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addComponent(btnraices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMetodoSE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMetodoIL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(btnMetodoIyD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnsalir)
                .addGap(18, 18, 18)
                .addComponent(btnintegrantes))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MetodoRaices(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MetodoRaices
        jdgRaicez.show();
    }//GEN-LAST:event_MetodoRaices

    private void Salir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Salir
        System.exit(3);

    }//GEN-LAST:event_Salir

    private void Calcular(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Calcular
        if(!ValidarRaiz())
            return;
        String titulo="";
         int Index=CBMetodo.getSelectedIndex();
        if(Index==0){//biseccion
            raiz=MetodosRaices.biseccion(a,b,error,iteraciones,tblResultado);
            titulo="Biseccion";
        }
        else if(Index==1){//secante
             raiz=MetodosRaices.secante(a,b,error,iteraciones,tblResultado);
            titulo="Secante";
        }
        else if(Index==2){//Falsa posicion
            raiz=MetodosRaices.falsaPosicion(a,b,error,iteraciones,tblResultado);
            titulo="Falsa Posicion";
        }
        else if(Index==3){//newton raphson
             raiz=MetodosRaices.newtonRaphson(a,error,iteraciones,tblResultado);
            titulo="Newton Raphson";
        }
        else if(Index==4){//aproximaciones Sucesicas
            raiz=MetodosRaices.aproximacionesSucesivas(a,error,iteraciones,tblResultado);
            titulo="Aproximaciones Sucesivas";
        }
        lblInformacion.setText("Raiz: "+raiz);
        try{
            if(raiz!=null)
                graficar(titulo);
        }catch(Exception E){}
    }//GEN-LAST:event_Calcular

    private void btnMetodoSEMetodoRaices(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodoSEMetodoRaices
        jdgMatriz.show();
    }//GEN-LAST:event_btnMetodoSEMetodoRaices

    private void btncalcularSEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularSEActionPerformed
        try{
            String []columnas= new String[m.T+1];
            for(int i=0;i<m.T+1;i++){
                columnas[i]="";
            }
            int Index=CBMetodoSE.getSelectedIndex();
            if(Index==0){//Gauss
                MatrizFinal=MetodosSisEcu.gauss(m.dameMatriz());
            }
            else if(Index==1){//Gauss-Jordan
                MatrizFinal=MetodosSisEcu.gaussJordan(m.dameMatriz());
            }
            else if(Index==2){//Motante
                MatrizFinal=MetodosSisEcu.montante(m.dameMatriz());
            }
            else if(Index==3 || Index==4 || Index==5){//Cramer o jacobi :( no sale
                 Resultado= new Double[m.T];
                if(Index==3)//Cramer 
                    Resultado = MetodosSisEcu.cramer(m.dameMatriz());
                if(Index==4)//jacobi
                    Resultado  = MetodosSisEcu.jacobi(m.dameMatriz(),0.00001d);
                if(Index==5)//Gauss-Seidel 
                    Resultado  = MetodosSisEcu.gaussSeidel(m.dameMatriz(),0.00001d);
                String Resultados="Resultado:  ";
                for(int i=0;i<Resultado.length;i++){
                    Resultados+="   X"+(i+1)+" = "+Resultado[i];
                }
                lblresultadoM.setText(Resultados);
                MatrizFinal=MetodosSisEcu.b;
            }
            jtbmatrizFinal.setModel(new javax.swing.table.DefaultTableModel(MatrizFinal,columnas));
            if(Index!=3 && Index!=4 && Index!=5)
                ResultadosSE();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Matriz Erronea o No Cargada","Error",1);
        }
    }//GEN-LAST:event_btncalcularSEActionPerformed

    private void btngenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngenerarActionPerformed
        try{
            int T= Integer.parseInt(txtTamano.getText());
            m= new Matriz(T,jtbinicial);
            m.verMatriz();

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"El Campo Tamaño Esta Vacio o NO es Numerico","Error",1);
        }
    }//GEN-LAST:event_btngenerarActionPerformed

    private void btnMetodoILMetodoRaices(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodoILMetodoRaices
        jtbInterpolacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {1, 1},
                {2, 1.5},
                {3, 2.5},
                {4, 4},
                {5, 6},
                {6, 9},
                {7, 15},
            },
            new String [] {
                "X", "Y"
            }
        ));
        txtXIL.setText("1.5");
        txtordenIL.setText("2");
        CBmetodoIL.setSelectedIndex(2);        
        jdgInterpolacion.show();
    }//GEN-LAST:event_btnMetodoILMetodoRaices

    private void btngenerarILActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngenerarILActionPerformed
        try{
        String[] columnas = {"X","Y"};
        jtbInterpolacion.setModel(new DefaultTableModel(new Double[Integer.parseInt(txtnpuntos.getText())][2],columnas));
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,"El Campo Tamaño Esta Vacio o NO es Numerico","Error",1);
        }
    }//GEN-LAST:event_btngenerarILActionPerformed

    private void btninterpolarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninterpolarActionPerformed
        Double[][] puntos = new Double[jtbInterpolacion.getRowCount()][2];
        try{
            for(int i=0;i<puntos.length;i++)
                for(int j=0;j<puntos[i].length;j++){
                    puntos[i][j]=Double.parseDouble(""+jtbInterpolacion.getModel().getValueAt(i,j));
                }           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Un Campo esta Vacio o Precione ENTER \n al terminar de Insertar todos los campos","Error",1);
            return;
        }
        int Index=CBmetodoIL.getSelectedIndex();
        try{
            double y=0;
            Double x=Double.parseDouble(txtXIL.getText());
            if(Index==0){//Newton
                String[] columnas = new String[puntos.length+1];
                columnas[0]="X";
                columnas[1]="Y";
                for(int i=2;i<columnas.length;i++){
                    columnas[i]="d"+(i-1)+"y";
                }
                jtbInterpolacion.setModel(new DefaultTableModel(new Double[puntos.length][columnas.length],columnas));
                 y=MetodosInterpolacion.interpolacionNewton(x,puntos,jtbInterpolacion.getModel());
            }
            else if(Index==1){//Lagrange
                y=MetodosInterpolacion.lagrange(x,puntos);
            }
            else if(Index==2){//Minimos Cuadrados
                y=MetodosInterpolacion.minimosCuadrados(Integer.parseInt(txtordenIL.getText()),x,puntos,jtblResultadoIL);
            }
            lblResultadoIL.setText("Resultado Y="+y);  
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"EL Campo de X esta Vacio o No es numerico","Error",1);
        }
    }//GEN-LAST:event_btninterpolarActionPerformed

    private void CBmetodoILItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CBmetodoILItemStateChanged
       if (evt.getStateChange() == ItemEvent.SELECTED) {
          if(evt.getItem().toString().equals("Minimos Cuadrados")){
            System.out.println(evt.getItem().toString());
            txtordenIL.setEnabled(true);
          }
          else{
            txtordenIL.setEnabled(false);
          }
       }
    }//GEN-LAST:event_CBmetodoILItemStateChanged

    private void CBmetodoILActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBmetodoILActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBmetodoILActionPerformed

    private void btnCalcularDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularDActionPerformed
        if(!ValidarDerivada())
            return;
        int Index= CBmetodosD.getSelectedIndex();
        Double dx=0d;
        try{
            if(Index==0){//Limites
                dx=MetodosDerivacion.derivar(xD, error);
           }
           else if(Index==1){//Diferencias Finitas

           }   
            lblResultadod.setText("dx= "+dx);
            jdgIyD.pack();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Uno de los Campos esta Vacio o No es numerico","Error",1);
        }
    }//GEN-LAST:event_btnCalcularDActionPerformed

    private void btnintegrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnintegrarActionPerformed
        if(!ValidarIntegral())
            return;
        int Index= CBmetodosI.getSelectedIndex();
        Double integral=0d;
        try{
            if(Index==0){//Limites
                integral=MetodosIntegracion.metodoTrapecio(a,b,n);
           }
           else if(Index==1){//Diferencias Finitas
                integral=MetodosIntegracion.metodoSimpson(a,b,n);
           }   
            lblResultadoI.setText("Integral= "+integral);
            jdgIyD.pack();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Uno de los Campos esta Vacio o No es numerico","Error",1);
        }
    }//GEN-LAST:event_btnintegrarActionPerformed

    private void btnintegrantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnintegrantesActionPerformed
        jdgIntegrantes.show();
    }//GEN-LAST:event_btnintegrantesActionPerformed
    public void ResultadosSE(){
        Resultado= new Double[m.T];
        String []columnas= new String[m.T];
        for(int i=0;i<m.T;i++){
            Resultado[i]=MatrizFinal[i][m.T];
        }
        for(int i=m.T-1;i>=0;i--){
            for(int j=0;j<m.T;j++){
                if(i==j){
                    continue;
                }
                Resultado[i]-=MatrizFinal[i][j]*Resultado[j];
            }
            Resultado[i]/=MatrizFinal[i][i];
        }
        String Resultados="Resultado:  ";
        for(int i=0;i<m.T;i++){
           Resultados+="   X"+(i+1)+" = "+Resultado[i];
        }
        lblresultadoM.setText(Resultados);
    }
    
    private void btnMetodoIyDMetodoRaices(java.awt.event.ActionEvent evt) {                                         
        jdgIyD.show();
    }                                        
    
    public boolean ValidarRaiz(){
        try{
            Funciones.convertirPosfija(txtfuncion.getText());
            a=Double.parseDouble(txta.getText());
            error=Double.parseDouble(txterror.getText());
            iteraciones=Integer.parseInt(txtinteraciones.getText());
            if(!(CBMetodo.getSelectedIndex()==3 || CBMetodo.getSelectedIndex()==4))
                b=Double.parseDouble(txtb.getText());
        }catch(Exception e){
               lblInformacion.setText("Campos Incompletos");
               JOptionPane.showMessageDialog(null,"Campos Incompletos","Error",1);
               return false;
        }
        if(!tieneRaiz()){
            lblInformacion.setText("No Tiene Raiz o Rango Incorrecto");
            return false;
        }
	return true;
    }
    public boolean ValidarDerivada(){
        try{
            Funciones.convertirPosfija(txtfunciond.getText());
            xD=Double.parseDouble(txtxder.getText());
            error=Double.parseDouble(txterrord.getText());
           }catch(Exception e){
               JOptionPane.showMessageDialog(null,"Campos Incompletos","Error",1);
               return false;
        }
	return true;
    }
    private int n;
      public boolean ValidarIntegral(){
        try{
            Funciones.convertirPosfija(txtfI.getText());
            a=Double.parseDouble(txtaI.getText());
            b=Double.parseDouble(txtbI.getText());
            n=Integer.parseInt(txtnI.getText());
           }catch(Exception e){
               JOptionPane.showMessageDialog(null,"Campos Incompletos","Error",1);
               return false;
        }
	return true;
    }
    public boolean tieneRaiz(){
        return Funciones.f(a)*Funciones.f(b)<0;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CBMetodo;
    private javax.swing.JComboBox CBMetodoSE;
    private javax.swing.JComboBox CBmetodoIL;
    private javax.swing.JComboBox CBmetodosD;
    private javax.swing.JComboBox CBmetodosI;
    private javax.swing.JButton btnCalcularD;
    private javax.swing.JButton btnMetodoIL;
    private javax.swing.JButton btnMetodoIyD;
    private javax.swing.JButton btnMetodoSE;
    private javax.swing.JButton btncalcularSE;
    private javax.swing.JButton btngenerar;
    private javax.swing.JButton btngenerarIL;
    private javax.swing.JButton btnintegrantes;
    private javax.swing.JButton btnintegrar;
    private javax.swing.JButton btninterpolar;
    private javax.swing.JButton btnraices;
    private javax.swing.JButton btnsalir;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JDialog jdgIntegrantes;
    private javax.swing.JDialog jdgInterpolacion;
    private javax.swing.JDialog jdgIyD;
    private javax.swing.JDialog jdgMatriz;
    private javax.swing.JDialog jdgRaicez;
    private javax.swing.JPanel jpResultado;
    private javax.swing.JTable jtbInterpolacion;
    private javax.swing.JTable jtbinicial;
    private javax.swing.JTable jtblResultadoIL;
    private javax.swing.JTable jtbmatrizFinal;
    private javax.swing.JLabel lblInformacion;
    private javax.swing.JLabel lblResultadoI;
    private javax.swing.JLabel lblResultadoIL;
    private javax.swing.JLabel lblResultadod;
    private javax.swing.JLabel lblresultadoM;
    private javax.swing.JTabbedPane panelRaices;
    private javax.swing.JTable tblResultado;
    private javax.swing.JTextField txtTamano;
    private javax.swing.JTextField txtXIL;
    private javax.swing.JTextField txta;
    private javax.swing.JTextField txtaI;
    private javax.swing.JTextField txtb;
    private javax.swing.JTextField txtbI;
    private javax.swing.JTextField txterror;
    private javax.swing.JTextField txterrord;
    private javax.swing.JTextField txtfI;
    private javax.swing.JTextField txtfuncion;
    private javax.swing.JTextField txtfunciond;
    private javax.swing.JTextField txtinteraciones;
    private javax.swing.JTextField txtnI;
    private javax.swing.JTextField txtnpuntos;
    private javax.swing.JTextField txtordenIL;
    private javax.swing.JTextField txtxder;
    // End of variables declaration//GEN-END:variables
}
