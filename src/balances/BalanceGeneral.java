/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BalanceGeneral.java
 *
 * Created on 29/11/2009, 01:22:30 PM
 */

package balances;

import cuentas.*;

/**
 *
 * @author Gabriel
 */
public class BalanceGeneral extends Balance {
    private static int k;

    /** Creates new form BalanceGeneral */
    public BalanceGeneral( Activo a, Pasivo p, Capital c, String e, String f ) {
        initComponents();
        this.setLocation(250,50);
        k = 0;

        if( a.saldo() != 0.0 && p.saldo() != 0.0 && c.saldo() != 0.0 ){
            double suma = 0.0;
            String txt = "";
            String aux = "";
            DatosCuenta cuentas[] = null;

            for( int i = 0; i < 45-(e.length()/2); i++ )
                txt += " ";
            txt += e + "\n";

            String titulo = "Balance General al " + f;
            for( int i = 0; i < 45-(titulo.length()/2); i++ )
                txt += " ";
            txt += titulo + "\n\n";

            aux += cadenaS(" Activo") + " " + espacios(0);
            txt += aux + masTexto(p,c,false) + "\n";
            aux = "";

            for( int i = 0; i < 3; i++ ){
                switch( i ){
                    case 0:
                        if( a.getCirculante().saldo() != 0.0 )
                            aux += cadenaS("  Activo Circulante") + " " + espacios(0);
                        cuentas = a.getCirculante().getCuentas();
                        break;
                    case 1:
                        if( a.getFijo().saldo() != 0.0 )
                            aux += cadenaS("  Activo Fijo") + " " + espacios(0);
                        cuentas = a.getFijo().getCuentas();
                        break;
                    case 2:
                        if( a.getDiferido().saldo() != 0.0 )
                            aux += cadenaS("  Activo Diferido") + " " + espacios(0);
                        cuentas = a.getDiferido().getCuentas();
                        break;
                }

                if(!aux.equals(""))
                    txt += aux + masTexto(p,c,false) + "\n";
                aux = "";

                for( int j = 0; j < cuentas.length; j++ ){
                    if( cuentas[j].saldo() != 0.0 ){
                        suma += cuentas[j].saldo();

                        if( (suma - cuentas[j].saldo()) == 0.0 )
                            aux += cadenaS("    "+cuentas[j].getNombre()) + "$" + valor(cuentas[j].saldo());
                        else
                            aux += cadenaS("    "+cuentas[j].getNombre()) + " " + valor(cuentas[j].saldo());

                        if(!aux.equals(""))
                            txt += aux + masTexto(p,c,false) + "\n";
                        aux = "";
                    }
                }
            }

            txt += cadenaS("") + " " + espacios(2) + masTexto(p,c,false) + "\n";

            while( k <= 13 )
                txt += cadenaS("") + " " + espacios(0) + masTexto(p,c,false) + "\n";

            if( a.saldo() != 0.0 ){
                txt += cadenaS("  Suma de Activo") + "$" + valor(a.saldo()) + masTexto(p,c,true) + "\n";
            }

            balance.setText(txt);
            this.toFront();
            this.setVisible(true);
        }
    }

    private String masTexto( Pasivo p, Capital c, boolean permiso ) {
        String txt = "";
        DatosCuenta cuentas[] = null;

        while( txt.equals("") ){

            if( k == 2 || k == 3 || k == 4 ){
                cuentas = p.getCirculante().getCuentas();
            }else if( k == 7 || k <= 8 ){
                cuentas = p.getFijo().getCuentas();
            }else if( k == 11 || k == 12 ){
                cuentas = c.getCuentas();
            }

            if( k == 14 && !permiso )
                return "";

            switch( k ){
                case 0:
                    if( p.saldo() != 0.0 )
                        txt += cadenaS(" Pasivo") + " " + espacios(0);
                    break;
                case 1:
                    if( p.getCirculante().saldo() != 0.0 )
                        txt += cadenaS("  Pasivo Circulante") + " " + espacios(0);
                    break;
                case 2:
                    if( p.getCirculante().getCuenta("Proveedores").saldo() != 0.0 )
                        txt += cadenaS("    Proveedores") + "$" + valor(cuentas[0].saldo());
                    break;
                case 3:
                    if( p.getCirculante().getCuenta("Acreedores").saldo() != 0.0 )
                        txt += cadenaS("    Acreedores") + " " + valor(cuentas[1].saldo());
                    break;
                case 4:
                    if( p.getCirculante().getCuenta("Documentos por Pagar (C)").saldo() != 0.0 )
                        txt += cadenaS("    Documentos por Pagar") + " " + valor(cuentas[2].saldo());
                    break;
                case 5:
                    if( p.getFijo().saldo() != 0.0 )
                        txt += cadenaS("  Pasivo Fijo") + " " + espacios(0);
                    break;
                case 6:
                    if( p.getFijo().getCuenta("Acreedores Hipotecarios").saldo() != 0.0 )
                        txt += cadenaS("    Acreedores Hipotecarios") + " " + valor(cuentas[0].saldo());
                    break;
                case 7:
                    if( p.getFijo().getCuenta("Documentos por Pagar (F)").saldo() != 0.0 )
                        txt += cadenaS("    Documentos por Pagar") + " " + valor(cuentas[1].saldo());
                    break;
                case 8:
                    if( p.saldo() != 0.0 )
                        txt += cadenaS("") + " " + espacios(2);
                    break;
                case 9:
                    if( p.saldo() != 0.0 )
                        txt += cadenaS("  Suma de Pasivo") + "$" + valor(p.saldo());
                    break;
                case 10:
                    if( c.saldo() != 0.0 )
                        txt += cadenaS(" Capital") + " " + espacios(0);
                    break;
                case 11:
                    if( c.getCuenta("Capital Social").saldo() != 0.0 )
                        txt += cadenaS("    Capital Social") + " " + valor(cuentas[0].saldo());
                    break;
                case 12:
                    if( c.getCuenta("Utilidad Neta").saldo() != 0.0 )
                        txt += cadenaS("    Utilidad Neta") + " " + valor(cuentas[1].saldo());
                    break;
                case 13:
                    if( c.saldo() != 0.0 )
                        txt += cadenaS("") + " " + espacios(2);
                    break;
                case 14:
                    if( (p.saldo() + c.saldo()) != 0.0 )
                        txt += cadenaS("  Suma de Pasivo y Capital") + "$" + valor((p.saldo() + c.saldo()));
                    break;
                case 15:
                    return "";
            }

            k++;
        }

        return txt;
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
        balance = new javax.swing.JTextArea();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(BalanceGeneral.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setPreferredSize(new java.awt.Dimension(750, 500));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        balance.setColumns(20);
        balance.setEditable(false);
        balance.setRows(5);
        balance.setName("balance"); // NOI18N
        jScrollPane1.setViewportView(balance);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea balance;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
