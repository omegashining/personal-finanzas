/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Cuenta.java
 *
 * Created on 29/11/2009, 09:05:40 AM
 */

package cuentas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabriel
 */

public class Cuenta extends javax.swing.JInternalFrame {
    private String nombre;
    private String cuenta;
    private String tipo;

    /** Creates new form Cuenta */
    public Cuenta(String t, String c, String n) {
        initComponents();
        this.tipo = t;
        this.cuenta = c;
        this.nombre = n;
        this.setTitle(this.nombre);
        this.setLocation(450,150);
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List getCargos() {
        List lista = new ArrayList();
        for(int k =0; k<transacciones.getRowCount();k++){
            try {
                if(transacciones.getValueAt(k, 0)!=null){
                    lista.add(transacciones.getValueAt(k, 0));
                }
            } catch (Exception e) {
            }
        }
        return lista;
    }
    
    private void setCargos() {
        List abonos = getAbonos();
        List cargos = getCargos();
        
        try {
            double m = Double.parseDouble(monto.getText().toString());
            DefaultTableModel modelo = (DefaultTableModel) transacciones.getModel();
            if( cargos.size() >= abonos.size() && m > 0 ){
                modelo.addRow(new Object[2]);
                modelo.setValueAt(m, cargos.size(), 0);
            }
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog( null, "El monto introducido no es un número" );
        }
        monto.setText("");
    }

    public List getAbonos() {
        List lista = new ArrayList();
        for(int k =0; k<transacciones.getRowCount();k++){
            try {
                if(transacciones.getValueAt(k, 1)!=null){
                    lista.add(transacciones.getValueAt(k, 1));
                }
            } catch (Exception e) {
            }
        }
        return lista;
    }

    private void setAbonos() {
        List abonos = getAbonos();
        List cargos = getCargos();

        try {
            double m = Double.parseDouble(monto.getText().toString());
            DefaultTableModel modelo = (DefaultTableModel) transacciones.getModel();
            if( abonos.size() >= cargos.size() && m > 0 ){
                modelo.addRow(new Object[2]);
                modelo.setValueAt(m, abonos.size(), 1);
            }
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog( null, "El monto introducido no es un número" );
        }
        monto.setText("");
    }

    public double getSaldo(){
        return Double.parseDouble(saldo.getValueAt(0, 0).toString());
    }

    public void cargar( double valor ) {
        List abonos = getAbonos();
        List cargos = getCargos();
        double m = 0.0;
        double sumaCargos = 0.0;

        try {
            m = valor;
            DefaultTableModel modelo = (DefaultTableModel) transacciones.getModel();
            if( cargos.size() >= abonos.size() )
                modelo.addRow(new Object[2]);
            modelo.setValueAt(m, cargos.size(), 0);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog( null, "El monto introducido no es un número" );
        }

        cargos = getCargos();
        for( Object cargo : cargos )
            sumaCargos += Double.valueOf(cargo.toString());

        saldos.setValueAt(sumaCargos,0,0);

        double deudor = Double.parseDouble(saldos.getValueAt(0,0).toString());
        double acreedor = Double.parseDouble(saldos.getValueAt(0,1).toString());
        double s;
        s = deudor > acreedor ? deudor-acreedor : acreedor-deudor;
        saldo.setValueAt(s,0,0);
    }

    public void abonar( double valor ) {
        List abonos = getAbonos();
        List cargos = getCargos();
        double m = 0.0;
        double sumaAbonos = 0.0;

        try {
            m = valor;
            DefaultTableModel modelo = (DefaultTableModel) transacciones.getModel();
            if( abonos.size() >= cargos.size() )
                modelo.addRow(new Object[2]);
            modelo.setValueAt(m, abonos.size(), 1);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog( null, "El monto introducido no es un número" );
        }

        abonos = getAbonos();
        for( Object abono : abonos )
            sumaAbonos += Double.valueOf(abono.toString());

        saldos.setValueAt(sumaAbonos, 0, 1);

        double deudor = Double.parseDouble(saldos.getValueAt(0,0).toString());
        double acreedor = Double.parseDouble(saldos.getValueAt(0,1).toString());
        double s;
        s = deudor > acreedor ? deudor-acreedor : acreedor-deudor;
        saldo.setValueAt(s,0,0);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        transacciones = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        saldos = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        saldo = new javax.swing.JTable();
        cargar = new javax.swing.JButton();
        abonar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        monto = new javax.swing.JTextField();

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane3.setViewportView(jTable1);

        setIconifiable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(Cuenta.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        transacciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cargos", "Abonos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        transacciones.setName("transacciones"); // NOI18N
        jScrollPane1.setViewportView(transacciones);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        saldos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {new Double(0.0), new Double(0.0)}
            },
            new String [] {
                "Saldo Deudor", "Saldo Acreedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        saldos.setName("saldos"); // NOI18N
        jScrollPane2.setViewportView(saldos);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        saldo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {new Double(0.0)}
            },
            new String [] {
                "Saldo de la Cuenta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        saldo.setName("saldo"); // NOI18N
        jScrollPane4.setViewportView(saldo);

        cargar.setText(resourceMap.getString("cargar.text")); // NOI18N
        cargar.setName("cargar"); // NOI18N
        cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarActionPerformed(evt);
            }
        });

        abonar.setText(resourceMap.getString("abonar.text")); // NOI18N
        abonar.setName("abonar"); // NOI18N
        abonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abonarActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        monto.setText(resourceMap.getString("monto.text")); // NOI18N
        monto.setName("monto"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(monto, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cargar)
                        .addGap(18, 18, 18)
                        .addComponent(abonar)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cargar)
                            .addComponent(abonar))
                        .addGap(45, 45, 45)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarActionPerformed
        setCargos();
        double sumaCargos = 0.0;

        List cargos = getCargos();
        for( Object cargo : cargos )
            sumaCargos += Double.valueOf(cargo.toString());

        saldos.setValueAt(sumaCargos,0,0);

        double deudor = Double.parseDouble(saldos.getValueAt(0,0).toString());
        double acreedor = Double.parseDouble(saldos.getValueAt(0,1).toString());
        double s;
        s = deudor > acreedor ? deudor-acreedor : acreedor-deudor;
        saldo.setValueAt(s,0,0);
    }//GEN-LAST:event_cargarActionPerformed

    private void abonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abonarActionPerformed
        setAbonos();
        double sumaAbonos = 0.0;

        List abonos = getAbonos();
        for( Object abono : abonos )
            sumaAbonos += Double.valueOf(abono.toString());

        saldos.setValueAt(sumaAbonos, 0, 1);

        double deudor = Double.parseDouble(saldos.getValueAt(0,0).toString());
        double acreedor = Double.parseDouble(saldos.getValueAt(0,1).toString());
        double s;
        s = deudor > acreedor ? deudor-acreedor : acreedor-deudor;
        saldo.setValueAt(s,0,0);
    }//GEN-LAST:event_abonarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abonar;
    private javax.swing.JButton cargar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField monto;
    private javax.swing.JTable saldo;
    private javax.swing.JTable saldos;
    private javax.swing.JTable transacciones;
    // End of variables declaration//GEN-END:variables

}
