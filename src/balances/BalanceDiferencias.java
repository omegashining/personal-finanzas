/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BalanceDiferencias.java
 *
 * Created on 29/11/2009, 04:28:30 PM
 */

package balances;

import cuentas.*;

/**
 *
 * @author Gabriel
 */
public class BalanceDiferencias extends Balance {
    private String ai;
    private String af;
    private static int k;

    /** Creates new form BalanceGeneralComparativo */
    public BalanceDiferencias(Activo aa, Pasivo pa, Capital ca, Activo ar, Pasivo pr, Capital cr, String e, String ai, String af) {
        initComponents();
        this.ai = ai;
        this.af = af;
        this.setLocation(10,50);
        k = 0;

        if( (aa.saldo() != 0.0 && pa.saldo() != 0.0 && ca.saldo() != 0.0) || (ar.saldo() != 0.0 && pr.saldo() != 0.0 && cr.saldo() != 0.0)) {
            double sa, sr;
            double sumaa = 0.0, sumar = 0.0;
            String txt = "", aux = "";
            DatosCuenta cuentas1[] = null, cuentas2[] = null;

            for( int i = 0; i < 75-(e.length()/2); i++ ) txt += " ";
            txt += e + "\n";

            String titulo = "Balance General Comparativo";
            for( int i = 0; i < 75-(titulo.length()/2); i++ )
                txt += " ";
            txt += titulo + "\n\n";

            aux += cadenaS(" Activo") + "      " + ai + "     " + "      " + af + "     " + "  Diferencias  ";
            txt += aux + agregarTexto(pa,pr,ca,cr,false) + "\n";
            aux = "";

            for( int i = 0; i < 3; i++ ){
                switch( i ){
                    case 0:
                        if( (aa.getCirculante().saldo() != 0.0) || (ar.getCirculante().saldo() != 0.0) )
                            aux += cadenaS("  Activo Circulante") + " " + espacios(1) + " " + espacios(0);
                        cuentas1 = aa.getCirculante().getCuentas();
                        cuentas2 = ar.getCirculante().getCuentas();
                    break;
                    case 1:
                        if( (aa.getFijo().saldo() != 0.0) || (ar.getFijo().saldo() != 0.0) )
                            aux += cadenaS("  Activo Fijo") + " " + espacios(1) + " " + espacios(0);
                        cuentas1 = aa.getFijo().getCuentas();
                        cuentas2 = ar.getFijo().getCuentas();
                        break;
                    case 2:
                        if( (aa.getDiferido().saldo() != 0.0) || (ar.getDiferido().saldo() != 0.0) )
                            aux += cadenaS("  Activo Diferido") + " " + espacios(1) + " " + espacios(0);
                        cuentas1 = aa.getDiferido().getCuentas();
                        cuentas2 = ar.getDiferido().getCuentas();
                        break;
                }

                if(!aux.equals(""))
                    txt += aux + agregarTexto(pa,pr,ca,cr,false) + "\n";
                aux = "";

                for( int j = 0; j < cuentas1.length; j++ ){
                    sa = cuentas1[j].saldo();
                    sr = cuentas2[j].saldo();
                    if( sa != 0 || sr != 0 ){
                        sumaa += cuentas1[j].saldo();
                        sumar += cuentas2[j].saldo();

                        if( (sumaa - cuentas1[j].saldo()) == 0.0 )
                            aux += cadenaS("    "+cuentas1[j].getNombre()) + "$" + valor(cuentas1[j].saldo());
                        else
                            aux += cadenaS("    "+cuentas1[j].getNombre()) + " " + valor(cuentas1[j].saldo());

                        if( (sumar - cuentas2[j].saldo()) == 0.0 )
                            aux += "$" + valor(cuentas2[j].saldo());
                        else
                            aux += " " + valor(cuentas2[j].saldo());

                        aux += diferencias(sa,sr);
                    }

                    if(!aux.equals(""))
                        txt += aux + agregarTexto(pa,pr,ca,cr,false) + "\n";
                    aux = "";
                }
            }

            txt += cadenaS("") + " " + espacios(3) + espacios(0) + agregarTexto(pa,pr,ca,cr,false) + "\n";

            while( k <= 13 )
                txt += cadenaS("") + " " + espacios(1) + " " + espacios(0) + agregarTexto(pa,pr,ca,cr,false) + "\n";

            if( (aa.saldo() != 0.0) || (ar.saldo() != 0.0) ){
                txt += cadenaS("  Suma de Activo") + "$" + valor(aa.saldo()) + "$" + valor(ar.saldo())
                     + diferencias(aa.saldo(),ar.saldo()) + agregarTexto(pa,pr,ca,cr,true) + "\n";
            }

            balance.setText(txt);
            this.toFront();
            this.setVisible(true);
        }
    }

    private String agregarTexto( Pasivo pa, Pasivo pr, Capital ca, Capital cr, boolean permiso ) {
        String txt = "";
        DatosCuenta cuentasa[] = null;
        DatosCuenta cuentasr[] = null;

        while( txt.equals("") ){

            if( k == 2 || k == 3 || k == 4 ){
                cuentasa = pa.getCirculante().getCuentas();
                cuentasr = pr.getCirculante().getCuentas();
            }else if( k == 7 || k <= 8 ){
                cuentasa = pa.getFijo().getCuentas();
                cuentasr = pr.getFijo().getCuentas();
            }else if( k == 11 || k == 12 ){
                cuentasa = ca.getCuentas();
                cuentasr = cr.getCuentas();
            }

            if( k == 14 && !permiso )
                return "";

            switch( k ){
                case 0:
                    if( pa.saldo() != 0.0 )
                        txt += cadenaS(" Pasivo") + "      " + ai + "     " + "      " + af + "     " + "  Diferencias  ";
                    break;
                case 1:
                    if( pa.getCirculante().saldo() != 0.0 || pr.getCirculante().saldo() != 0.0 )
                        txt += cadenaS("  Pasivo Circulante") + " " + espacios(1);
                    break;
                case 2:
                    if( pa.getCirculante().getCuenta("Proveedores").saldo() != 0.0 || pr.getCirculante().getCuenta("Proveedores").saldo() != 0.0 )
                        txt += cadenaS("    Proveedores") + "$" + valor(cuentasa[0].saldo())
                             + "$" + valor(cuentasr[0].saldo()) + diferencias(cuentasa[0].saldo(),cuentasr[0].saldo());
                    break;
                case 3:
                    if( pa.getCirculante().getCuenta("Acreedores").saldo() != 0.0 || pr.getCirculante().getCuenta("Acreedores").saldo() != 0.0 )
                        txt += cadenaS("    Acreedores") + " " + valor(cuentasa[1].saldo())
                             + " " + valor(cuentasr[1].saldo()) + diferencias(cuentasa[1].saldo(),cuentasr[1].saldo());
                    break;
                case 4:
                    if( pa.getCirculante().getCuenta("Documentos por Pagar (C)").saldo() != 0.0 || pr.getCirculante().getCuenta("Documentos por Pagar (C)").saldo() != 0.0 )
                        txt += cadenaS("    Documentos por Pagar") + " " + valor(cuentasa[2].saldo())
                             + " " + valor(cuentasr[2].saldo()) + diferencias(cuentasa[2].saldo(),cuentasr[2].saldo());
                    break;
                case 5:
                    if( pa.getFijo().saldo() != 0.0 )
                        txt += cadenaS("  Pasivo Fijo") + " " + espacios(1);
                    break;
                case 6:
                    if( pa.getFijo().getCuenta("Acreedores Hipotecarios").saldo() != 0.0 || pr.getFijo().getCuenta("Acreedores Hipotecarios").saldo() != 0.0 )
                        txt += cadenaS("    Acreedores Hipotecarios") + " " + valor(cuentasa[0].saldo())
                             + " " + valor(cuentasr[0].saldo()) + diferencias(cuentasa[0].saldo(),cuentasr[0].saldo());
                    break;
                case 7:
                    if( pa.getFijo().getCuenta("Documentos por Pagar (F)").saldo() != 0.0 || pr.getFijo().getCuenta("Documentos por Pagar (F)").saldo() != 0.0 )
                        txt += cadenaS("    Documentos por Pagar") + " " + valor(cuentasa[1].saldo())
                             + " " + valor(cuentasr[1].saldo()) + diferencias(cuentasa[1].saldo(),cuentasr[1].saldo());
                    break;
                case 8:
                    if( pa.saldo() != 0.0 || pr.saldo() != 0.0 )
                        txt += cadenaS("") + " " + espacios(2) + " " + espacios(2);
                    break;
                case 9:
                    if( pa.saldo() != 0.0 || pr.saldo() != 0.0 )
                        txt += cadenaS("  Suma de Pasivo") + "$" + valor(pa.saldo())
                             + "$" + valor(pr.saldo()) + diferencias(pa.saldo(),pr.saldo());
                    break;
                case 10:
                    if( ca.saldo() != 0.0 || cr.saldo() != 0.0 )
                        txt += cadenaS(" Capital") + " " + espacios(1);
                    break;
                case 11:
                    if( ca.getCuenta("Capital Social").saldo() != 0.0 || cr.getCuenta("Capital Social").saldo() != 0.0 )
                        txt += cadenaS("    Capital Social") + " " + valor(cuentasa[0].saldo())
                             + " " + valor(cuentasr[0].saldo()) + diferencias(cuentasa[0].saldo(),cuentasr[0].saldo());
                    break;
                case 12:
                    if( ca.getCuenta("Utilidad Neta").saldo() != 0.0 || cr.getCuenta("Utilidad Neta").saldo() != 0.0 )
                        txt += cadenaS("    Utilidad Neta") + " " + valor(cuentasa[1].saldo())
                             + " " + valor(cuentasr[1].saldo()) + diferencias(cuentasa[1].saldo(),cuentasr[1].saldo());
                    break;
                case 13:
                    if( ca.saldo() != 0.0 || cr.saldo() != 0.0 )
                        txt += cadenaS("") + " " + espacios(3);
                    break;
                case 14:
                    if( (pa.saldo() + ca.saldo()) != 0.0 || (pr.saldo() + cr.saldo()) != 0.0 )
                        txt += cadenaS("  Suma de Pasivo y Capital") + "$" + valor((pa.saldo() + ca.saldo()))
                             + "$" + valor((pr.saldo() + cr.saldo())) + diferencias((pa.saldo() + ca.saldo()),(pr.saldo() + cr.saldo()));
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
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(BalanceDiferencias.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setPreferredSize(new java.awt.Dimension(1250, 500));

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1234, Short.MAX_VALUE)
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
