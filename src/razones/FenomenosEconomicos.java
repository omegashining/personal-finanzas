/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FenomenosEconomicos.java
 *
 * Created on 29/11/2009, 07:05:56 PM
 */

package razones;

import cuentas.*;

/**
 *
 * @author Gabriel
 */
public class FenomenosEconomicos extends javax.swing.JInternalFrame {

    /** Creates new form FenomenosEconomicos */
    public FenomenosEconomicos() {
        initComponents();
    }

    protected double liquidez( Activo activo, Pasivo pasivo ) {
        double valor = 0.0;

        try {
            valor = (activo.getCirculante().getCuenta("Caja").saldo()
                  + activo.getCirculante().getCuenta("Bancos").saldo())
                  / pasivo.getCirculante().saldo();
        } catch (Exception e) {
            return 0.0;
        }

        return valor;
    }

    protected double solvencia( Activo activo, Pasivo pasivo ) {
        double valor = 0.0;

        try {
            valor = activo.getCirculante().saldo()
                  / pasivo.getCirculante().saldo();
        } catch (Exception e) {
            return 0.0;
        }

        return valor;
    }

    protected double estabilidad( Activo activo, Pasivo pasivo ) {
        double valor = 0.0;

        try {
            valor = pasivo.saldo()
                  / activo.saldo();
        } catch (Exception e) {
            return 0.0;
        }

        return valor;
    }

    protected double inmovilizacion( Activo activo, Capital capital ) {
        double valor = 0.0;

        try {
            valor = activo.getFijo().saldo()
                  / capital.saldo();
        } catch (Exception e) {
            return 0.0;
        }

        return valor;
    }

    protected double rentabilidadVentas( Otras otras ) {
        double valor = 0.0;

        try {
            valor = otras.utilidadNeta()
                  / otras.getCuenta("Ventas").saldo();
        } catch (Exception e) {
            return 0.0;
        }

        return valor;
    }

    protected double rentabilidadInversion( Capital capital, Otras otras ) {
        double valor = 0.0;

        try {
            valor = otras.utilidadNeta()
                  / capital.getCuenta("Capital Social").saldo();
        } catch (Exception e) {
            return 0.0;
        }

        return valor;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}