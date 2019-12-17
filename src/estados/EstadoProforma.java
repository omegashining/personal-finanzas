/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EstadoProforma.java
 *
 * Created on 6/12/2009, 10:53:17 AM
 */

package estados;

import cuentas.Otras;

/**
 *
 * @author Gabriel
 */
public class EstadoProforma extends Estado {

    /** Creates new form EstadoProforma */
    public EstadoProforma( Otras otras, String empresa ) {
        initComponents();
        this.setLocation(280, 50);

        if( otras.utilidadNeta() != 0.0 ) {
            String txt = "";

            for( int i = 0; i < 41-(empresa.length()/2); i++ ) txt += " ";
            txt += empresa + "\n";

            String fechas = "Estado de Resultados Proforma";
            for( int i = 0; i < 41-(fechas.length()/2); i++ ) txt += " ";
            txt += fechas + "\n\n";

            double v = otras.getCuenta("Ventas").saldo();
            double c = otras.getCuenta("Costo de Ventas").saldo();
            double b = otras.utilidadBruta();
            double o = otras.getCuenta("Gastos de Operacion").saldo();
            double u = otras.utilidadDeOperacion();

            txt += "\t\t    " + titulo(3) + "$" + valor(v) + "\n";
            txt += "\t\t    " + titulo(1) + "\n";
            txt += "\t\t    " + titulo(4) + " " + valor(c) + "\n";
            txt += "\t\t    " + titulo(0) + " " + espacios(2) + "\n";
            txt += "\t\t    " + titulo(5) + " " + valor(b) + "\n";
            txt += "\t\t    " + titulo(1) + "\n";
            txt += "\t\t    " + titulo(6) + " " + valor(o) + "\n";

            double g = otras.getCuenta("Otros Gastos").saldo();
            double p = otras.getCuenta("Otros Productos").saldo();

            if( g != 0 && p != 0 ){
                txt += "\t\t    " + titulo(0) + " " + espacios(2) + "\n";
                txt += "\t\t    " + titulo(7) + " " + valor(u) + "\n";
                txt += "\t\t    " + titulo(1) + "\n";
                txt += "\t\t    " + titulo(8) + " " + valor(g) + "\n";
                txt += "\t\t    " + titulo(2) + "\n";
                txt += "\t\t    " + titulo(9) + " " + valor(p) + "\n";
            }else if( g != 0 ){
                txt += "\t\t    " + titulo(0) + " " + espacios(2) + "\n";
                txt += "\t\t    " + titulo(7) + " " + valor(u) + "\n";
                txt += "\t\t    " + titulo(1) + "\n";
                txt += "\t\t    " + titulo(8) + " " + valor(g) + "\n";
            }else if( p != 0 ){
                txt += "\t\t    " + titulo(0) + " " + espacios(2) + "\n";
                txt += "\t\t    " + titulo(7) + " " + valor(u) + "\n";
                txt += "\t\t    " + titulo(2) + "\n";
                txt += "\t\t    " + titulo(9) + " " + valor(p) + "\n";
            }

            double n = otras.utilidadNeta();

            txt += "\t\t    " + titulo(0)  + " " + espacios(2) + "\n";
            txt += "\t\t    " + titulo(10) + "$" + valor(n)  + "\n";

            estado.setText(txt);
            this.toFront();
            this.setVisible(true);
        }
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
        estado = new javax.swing.JTextArea();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(EstadoProforma.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setPreferredSize(new java.awt.Dimension(700, 350));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        estado.setColumns(20);
        estado.setRows(5);
        estado.setName("estado"); // NOI18N
        jScrollPane1.setViewportView(estado);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea estado;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
