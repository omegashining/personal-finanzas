/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EstadoOrigenAplicacion.java
 *
 * Created on 6/12/2009, 03:17:34 PM
 */

package estados;

import cuentas.*;

/**
 *
 * @author Gabriel
 */
public class EstadoOrigenAplicacion extends Estado {
    private static int s;
    private static int r;

    /** Creates new form EstadoOrigenAplicacion */
    public EstadoOrigenAplicacion(Activo a1, Pasivo p1, Capital c1, Otras o1, Activo a2, Pasivo p2, Capital c2, Otras o2, String e, String n) {
        initComponents();
        this.setLocation(250, 50);
        String pasivos1[] = new String[5], pasivos2[] = new String[5];
        String capital1[] = new String[2], capital2[] = new String[2];
        String activos1[] = new String[22], activos2[] = new String[22];
        DatosCuenta cuentas1[] = new DatosCuenta[10], cuentas2[] = new DatosCuenta[10];
        String aux1[],aux2[];

            String txt = "", na = "";
            double origen = 0;
            double aplicacion = 0;

            for( int i = 0; i < 48-(e.length()/2); i++ ) txt += " ";
            txt += e + "\n";

            String fechas = "Estado de Origen y Aplicación de recursos " + n + " - Proforma";
            for( int i = 0; i < 48-(fechas.length()/2); i++ ) txt += " ";
            txt += fechas + "\n\n";

            txt += space("Origen") + " " + espacios(0) + " " + space("Aplicación") + "\n";

            for( int j = 0; j < 6; j++ ){
                switch( j ){
                    case 0:
                        cuentas1 = p1.getCirculante().getCuentas();
                        cuentas2 = p2.getCirculante().getCuentas();
                        break;
                    case 1:
                        cuentas1 = p1.getFijo().getCuentas();
                        cuentas2 = p2.getFijo().getCuentas();
                        break;
                    case 2:
                        cuentas1 = c1.getCuentas();
                        cuentas2 = c2.getCuentas();
                        break;
                    case 3:
                        cuentas1 = a2.getCirculante().getCuentas();
                        cuentas2 = a1.getCirculante().getCuentas();
                        break;
                    case 4:
                        cuentas1 = a1.getFijo().getCuentas();
                        cuentas2 = a2.getFijo().getCuentas();
                        break;
                    case 5:
                        cuentas1 = a1.getDiferido().getCuentas();
                        cuentas2 = a2.getDiferido().getCuentas();
                        break;
                }

                aux1 = new String[cuentas1.length];
                aux2 = new String[cuentas2.length];

                for( int i = 0; i < cuentas1.length; i++ ){
                    na = cuentas1[i].getNombre();

                    if( na.equals("Depreciacion de Edificios") ){
                        na = "Edificios";
                    }else if( na.equals("Depreciacion de Mobiliario y Equipo")){
                        na = "Mobiliario y Equipo";
                    }else if( na.equals("Depreciacion de Equipo de Computo")){
                        na = "Equipo de Computo";
                    }else if( na.equals("Depreciacion de Equipo de Reparto")){
                        na = "Equipo de Reparto";
                    }else if( na.equals("Depreciacion de Equipo de Transporte")){
                        na = "Equipo de Transporte";
                    }else if( na.equals("Amortizacion de Gastos de Instalacion")){
                        na = "Gastos de Instalacion";
                    }else if( na.equals("Amortizacion de Gastos de Organizacion")){
                        na = "Gastos de Organizacion";
                    }

                    na = "   " + na;
                    double diferencia = OA( cuentas1[i].saldo(), cuentas2[i].saldo()  );
                    if( diferencia > 0 ){
                        origen += diferencia;
                        aux1[i] = space(na) + " " + valor(diferencia);
                        aux2[i] = space("") + " " + espacios(0) + " ";
                    }else if( diferencia < 0 ){
                        diferencia *= -1;
                        aplicacion += diferencia;
                        aux1[i] = space("") + " " + espacios(0) + " ";
                        aux2[i] = space(na) + " " + valor(diferencia);
                    }else{
                        aux1[i] = space("") + " " + espacios(0) + " ";
                        aux2[i] = space("") + " " + espacios(0) + " ";
                    }

                    switch( j ){
                        case 0:
                            for( int k = 0; k < cuentas1.length; k++ ){
                                pasivos1[k] = aux1[k];
                                pasivos2[k] = aux2[k];
                            }
                            break;
                        case 1:
                            for( int k = 3; k < 3 + cuentas1.length; k++ ){
                                pasivos1[k] = aux1[k-3];
                                pasivos2[k] = aux2[k-3];
                            }
                            break;
                        case 2:
                            for( int k = 0; k < cuentas1.length; k++ ){
                                capital1[k] = aux1[k];
                                capital2[k] = aux2[k];
                            }
                            break;
                        case 3:
                            for( int k = 0; k < cuentas1.length; k++ ){
                                activos1[k] = aux1[k];
                                activos2[k] = aux2[k];
                            }
                            break;
                        case 4:
                            for( int k = 6; k < 6 + cuentas1.length; k++ ){
                                activos1[k] = aux1[k-6];
                                activos2[k] = aux2[k-6];
                            }
                            break;
                        case 5:
                            for( int k = 17; k < 17 + cuentas1.length; k++ ){
                                activos1[k] = aux1[k-17];
                                activos2[k] = aux2[k-17];
                            }
                            break;
                    }

                }                
            }
            String aux = space("") + " " + espacios(0) + " ", auz = aux, auy = aux;
            
            txt += space("\na) Aumento de Pasivos") + " " + espacios(0) + " " + space("a) Disminución de Pasivos") + "\n";
            s = 0; r = 0;
            for( int i = 0; i < pasivos1.length; i++ ){
                while( auz.equals(aux) ){
                    if( s == pasivos1.length ){
                        auz = aux;
                        break;
                    }
                    auz = pasivos2[s++];
                }

                while( auy.equals(aux) ){
                    if( r == pasivos1.length ){
                        auy = aux;
                        break;
                    }
                    auy = pasivos1[r++];
                }

                if( (!auz.equals(aux) && !auy.equals(aux)) ||
                    (!auz.equals(aux) && auy.equals(aux)) ||
                    (auz.equals(aux) && !auy.equals(aux)) )
                    txt += auy + auz + "\n";
                else break;
                auz = aux;
                auy = aux;
            }

            txt += space("\nb) Aumento de Capital") + " " + espacios(0) + " " + space("c) Disminución de Capital") + "\n";
            s = 0; r = 0;
            for( int i = 0; i < capital1.length; i++ ){
                while( auz.equals(aux) ){
                    if( s == capital1.length ){
                        auz = aux;
                        break;
                    }
                    auz = capital2[s++];
                }

                while( auy.equals(aux) ){
                    if( r == capital1.length ){
                        auy = aux;
                        break;
                    }
                    auy = capital1[r++];
                }

                if( (!auz.equals(aux) && !auy.equals(aux)) ||
                    (!auz.equals(aux) && auy.equals(aux)) ||
                    (auz.equals(aux) && !auy.equals(aux)) )
                    txt += auy + auz + "\n";
                else break;
                auz = aux;
                auy = aux;
            }

            txt += space("\nc) Disminución de Activos") + " " + espacios(0) + " " + space("c) Aumento de Activos") + "\n";
            s = 0; r = 0;
            for( int i = 0; i < activos1.length; i++ ){
                while( auz.equals(aux) ){
                    if( s == activos1.length ){
                        auz = aux;
                        break;
                    }
                    auz = activos2[s++];
                }

                while( auy.equals(aux) ){
                    if( r == activos1.length ){
                        auy = aux;
                        break;
                    }
                    auy = activos1[r++];
                }

                if( (!auz.equals(aux) && !auy.equals(aux)) ||
                    (!auz.equals(aux) && auy.equals(aux)) ||
                    (auz.equals(aux) && !auy.equals(aux)) )
                    txt += auy + auz + "\n";
                else break;
                auz = aux;
                auy = aux;
            }

            txt += space("\nOrigen") + " " + valor(origen) + " " + space("Aplicación") + " " + valor(aplicacion) + "\n";

            estado.setText(txt);
            this.toFront();
            this.setVisible(true);
    }

    public String space( String cadena ){
        String text = "";
        text += cadena;

        for( int i = 0; i < 30 - cadena.length(); i++ )
            text += " ";

        return text;
    }

    public double OA(double valor1,double valor2){
        double valor = 0;

        valor = valor1 - valor2;

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

        jScrollPane1 = new javax.swing.JScrollPane();
        estado = new javax.swing.JTextArea();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(EstadoOrigenAplicacion.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 500));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        estado.setColumns(20);
        estado.setEditable(false);
        estado.setRows(5);
        estado.setName("estado"); // NOI18N
        jScrollPane1.setViewportView(estado);

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
    private javax.swing.JTextArea estado;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
