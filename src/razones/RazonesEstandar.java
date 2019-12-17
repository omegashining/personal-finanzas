/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RazonesEstandar.java
 *
 * Created on 29/11/2009, 07:10:56 PM
 */

package razones;

import cuentas.*;

/**
 *
 * @author Gabriel
 */
public class RazonesEstandar extends FenomenosEconomicos {

    /** Creates new form RazonesEstandar */
    public RazonesEstandar(Activo activo, Pasivo pasivo, Capital capital, Otras otras, int index) {
        initComponents();
        this.setLocation(320,150);
        if( activo.saldo() != 0 && pasivo.saldo() != 0 &&
            capital.saldo() != 0 && otras.saldo() != 0 ){
            campo.setValueAt(liquidez( activo, pasivo ), 0, 1);
            campo.setValueAt(solvencia( activo, pasivo ), 1, 1);
            campo.setValueAt(estabilidad( activo, pasivo ), 2, 1);
            campo.setValueAt(inmovilizacion( activo, capital ), 3, 1);
            campo.setValueAt(rentabilidadVentas( otras ), 4, 1);
            campo.setValueAt(rentabilidadInversion( capital, otras ), 5, 1);

            this.toFront();
            this.setVisible(true);
        }

        switch( index ){
            case 0:
                campo.setValueAt(0.72, 0, 2);
                campo.setValueAt(1.60, 1, 2);
                campo.setValueAt(0.59, 2, 2);
                campo.setValueAt(0.78, 3, 2);
                campo.setValueAt(0.25, 4, 2);
                campo.setValueAt(0.46, 5, 2);
                break;
            case 1:
                campo.setValueAt(0.92, 0, 2);
                campo.setValueAt(1.65, 1, 2);
                campo.setValueAt(0.28, 2, 2);
                campo.setValueAt(0.83, 3, 2);
                campo.setValueAt(0.31, 4, 2);
                campo.setValueAt(0.24, 5, 2);
                break;
        }
        
        double a, b;
        for( int i = 0; i < campo.getRowCount(); i++ ) {
            a = obtenerA(i);
            b = obtenerB(i);

            if( i == 2 || i == 3 )
                diferencias(b,a,i);
            else
                diferencias(a,b,i);
        }
    }

    private double obtenerA( int fila ) {
        double a = 0.0;

        a = Double.parseDouble(campo.getValueAt(fila, 1).toString());

        return a;
    }

    private double obtenerB( int fila ) {
        double b = 0.0;

        try {
            if( campo.getValueAt(fila, 2) != null )
                b = Double.parseDouble(campo.getValueAt(fila, 2).toString());
        } catch (NumberFormatException numberFormatException) {
            return 0.0;
        }

        return b;
    }

    private void diferencias ( double a, double b, int fila ) {
        campo.setValueAt(null, fila, 3);
        campo.setValueAt(null, fila, 4);
        if( a > b )
            campo.setValueAt(a-b,fila,3);
        else
            campo.setValueAt(b-a,fila,4);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        campo = new javax.swing.JTable();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(RazonesEstandar.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        campo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Liquidez", null, null, null, null},
                {"Solvencia", null, null, null, null},
                {"Estabilidad", null, null, null, null},
                {"Inmovilización", null, null, null, null},
                {"Rentabilidad en Ventas", null, null, null, null},
                {"Rentabilidad en Inversión", null, null, null, null}
            },
            new String [] {
                "Fenómeno Económico", "Razones Financieras", "Razones Estándar", "Diferencias (+)", "Diferencias ( - )"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        campo.setName("campo"); // NOI18N
        jScrollPane1.setViewportView(campo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable campo;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
