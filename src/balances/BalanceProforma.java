/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BalanceProforma.java
 *
 * Created on 6/12/2009, 10:59:01 AM
 */

package balances;

import cuentas.*;

/**
 *
 * @author Gabriel
 */
public class BalanceProforma extends Balance {
    private static int k;

    /** Creates new form BalanceProforma */
    public BalanceProforma( Activo a, Pasivo p, Capital c, String e ) {
        initComponents();
        this.setLocation(120,50);
        k = 0;

        if( a.saldo() != 0.0 && p.saldo() != 0.0 && c.saldo() != 0.0 ){
            double suma = 0.0;
            String txt = "";
            String aux = "";
            String titulo = "Balance General Proforma";
            DatosCuenta cuentas[] = null;

            for( int i = 0; i < 60-(e.length()/2); i++ )
                txt += " ";
            txt += e + "\n";

            for( int i = 0; i < 60-(titulo.length()/2); i++ )
                txt += " ";
            txt += titulo + "\n\n";

            aux += cadenaL(" Activo") + " " + espacios(1);
            txt += aux + masTexto(p,c,false) + "\n";
            aux = "";

            for( int i = 0; i < 3; i++ ){
                switch( i ){
                    case 0:
                        if( a.getCirculante().saldo() != 0.0 )
                            aux += cadenaL("  Activo Circulante") + " " + espacios(1);
                        cuentas = a.getCirculante().getCuentas();
                        break;
                    case 1:
                        if( a.getFijo().saldo() != 0.0 )
                            aux += cadenaL("  Activo Fijo") + " " + espacios(1);
                        cuentas = a.getFijo().getCuentas();
                        break;
                    case 2:
                        if( a.getDiferido().saldo() != 0.0 )
                            aux += cadenaL("  Activo Diferido") + " " + espacios(1);
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
                            aux += cadenaL("    "+cuentas[j].getNombre()) + " " + espacios(0) + "$" + valor(cuentas[j].saldo());
                        else{
                            String n;
                            n = cuentas[j].getNombre();
                            if( n.equals("Edificios") || n.equals("Mobiliario y Equipo") || n.equals("Equipo de Computo") || n.equals("Equipo de Reparto") || n.equals("Equipo de Transporte") ){
                                n = "Depreciacion de " + n;
                                aux = cadenaL("    "+cuentas[j].getNombre()) + " " + valor(cuentas[j].saldo()) + " " + espacios(0);
                                txt += aux + masTexto(p,c,false) + "\n";

                                aux = cadenaL("       menos") + " " + espacios(1);
                                txt += aux + masTexto(p,c,false) + "\n";

                                aux = cadenaL("    " + n ) + " " + valor(a.getFijo().getCuenta(n).saldo()) + " " + valor(cuentas[j].saldo()-a.getFijo().getSaldo(n));
                                txt += aux + masTexto(p,c,false) + "\n";

                                aux = "";
                            }else if( n.equals("Gastos de Instalacion") || n.equals("Gastos de Organizacion") ){
                                n = "Amortizacion de " + n;
                                aux = cadenaL("    "+cuentas[j].getNombre()) + " " + valor(cuentas[j].saldo()) + " " + espacios(0);
                                txt += aux + masTexto(p,c,false) + "\n";

                                aux = cadenaL("       menos") + " " + espacios(1);
                                txt += aux + masTexto(p,c,false) + "\n";

                                aux = cadenaL("    " + n ) + " " + valor(a.getDiferido().getCuenta(n).saldo()) + " " + valor(cuentas[j].saldo()-a.getDiferido().getSaldo(n));
                                txt += aux + masTexto(p,c,false) + "\n";

                                aux = "";
                            }else if( n.equals("Depreciacion de Edificios") || n.equals("Depreciacion de Mobiliario y Equipo") || n.equals("Depreciacion de Equipo de Computo") || n.equals("Depreciacion de Equipo de Reparto") || n.equals("Depreciacion de Equipo de Transporte") || n.equals("Amortizacion de Gastos de Instalacion") || n.equals("Amortizacion de Gastos de Organizacion") ){
                                aux = "";
                            }else
                                aux += cadenaL("    "+cuentas[j].getNombre()) + " " + espacios(0) + " " + valor(cuentas[j].saldo());
                        }

                        if(!aux.equals(""))
                            txt += aux + masTexto(p,c,false) + "\n";
                        aux = "";
                    }
                }
            }

            txt += cadenaL("") + " " + espacios(0) + " " + espacios(2) + masTexto(p,c,false) + "\n";

            while( k <= 13 )
                txt += cadenaL("") + " " + espacios(1) + masTexto(p,c,false) + "\n";

            double da = a.getFijo().getSaldo("Depreciacion de Edificios")
                      + a.getFijo().getSaldo("Depreciacion de Mobiliario y Equipo")
                      + a.getFijo().getSaldo("Depreciacion de Equipo de Computo")
                      + a.getFijo().getSaldo("Depreciacion de Equipo de Reparto")
                      + a.getFijo().getSaldo("Depreciacion de Equipo de Transporte")
                      + a.getDiferido().getSaldo("Amortizacion de Gastos de Instalacion")
                      + a.getDiferido().getSaldo("Amortizacion de Gastos de Organizacion");

            double m = a.saldo()-(da*2);

            if( a.saldo() != 0.0 )
                txt += cadenaL("  Suma de Activo") + " " + espacios(0) + "$" + valor(m) + masTexto(p,c,true) + "\n";

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
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(BalanceProforma.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1000, 600));

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea balance;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
