/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DatosRecientes.java
 *
 * Created on 30/11/2009, 04:57:42 PM
 */

package datosempresa;

import cuentas.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class DatosRecientes extends javax.swing.JInternalFrame {

    /** Creates new form DatosRecientes */
    public DatosRecientes() {
        initComponents();
        this.setLocation(170, 25);
        this.setVisible(false);
    }
    
    public Activo getActivo(){
        String AC[] = {"Caja","Bancos","Almacen","Clientes","Deudores",
        "Documentos por Cobrar"};
        String AF[] = {"Terrenos","Edificios","Mobiliario y Equipo",
        "Equipo de Computo","Equipo de Reparto","Equipo de Transporte"};
        String AD[] = {"Depositos en Garantia","Gastos de Instalacion",
        "Gastos de Organizacion"};
        List lista;

        ActivoCirculante ac = new ActivoCirculante();
        for(int k =0; k<activoCirculante.getRowCount();k++){
            lista = new ArrayList();
            try {
                if(activoCirculante.getValueAt(k, 1)!=null){
                    lista.add(activoCirculante.getValueAt(k,1).toString());
                    ac.setAbonos(AC[k], lista);
                }
            } catch (Exception e) {
            }
        }

        ActivoFijo af = new ActivoFijo();
        for(int k =0; k<activoFijo.getRowCount();k++){
            lista = new ArrayList();
            try {
                if(activoFijo.getValueAt(k, 1)!=null){
                    lista.add(activoFijo.getValueAt(k,1).toString());
                    af.setAbonos(AF[k], lista);
                }
            } catch (Exception e) {
            }
        }

        ActivoDiferido ad = new ActivoDiferido();
        for(int k =0; k<activoDiferido.getRowCount();k++){
            lista = new ArrayList();
            try {
                if(activoDiferido.getValueAt(k, 1)!=null){
                    lista.add(activoDiferido.getValueAt(k,1).toString());
                    ad.setAbonos(AD[k], lista);
                }
            } catch (Exception e) {
            }
        }

        return new Activo(ac,af,ad);
    }

    public Pasivo getPasivo() {
        String PC[] = {"Proveedores","Acreedores","Documentos por Pagar (C)",};
        String PF[] = {"Acreedores Hipotecarios", "Documentos por Pagar (F)"};
        List lista;

        PasivoCirculante pc = new PasivoCirculante();
        for(int k =0; k<pasivoCirculante.getRowCount();k++){
            lista = new ArrayList();
            try {
                if(pasivoCirculante.getValueAt(k, 1)!=null){
                    lista.add(pasivoCirculante.getValueAt(k,1).toString());
                    pc.setAbonos(PC[k], lista);
                }
            } catch (Exception e) {
            }
        }

        PasivoFijo pf = new PasivoFijo();
        for(int k =0; k<pasivoFijo.getRowCount();k++){
            lista = new ArrayList();
            try {
                if(pasivoFijo.getValueAt(k, 1)!=null){
                    lista.add(pasivoFijo.getValueAt(k,1).toString());
                    pf.setAbonos(PF[k], lista);
                }
            } catch (Exception e) {
            }
        }

        return new Pasivo(pc,pf);
    }

    public Capital getCapital(){
        String C[] = {"Capital Social","Utilidad Neta"};
        List lista;

        Capital c = new Capital();
        for(int k =0; k<capital.getRowCount();k++){
            lista = new ArrayList();
            try {
                if(capital.getValueAt(k, 1)!=null){
                    lista.add(capital.getValueAt(k,1).toString());
                    c.setAbonos(C[k], lista);
                }
            } catch (Exception e) {
            }
        }

        Otras o = getOtras();
        lista = new ArrayList();
        lista.add(o.utilidadNeta());
        c.setAbonos(C[1], lista);

        return c;
    }

    public Otras getOtras(){
        String O[] = { "Ventas", "Costo de Ventas", "Gastos de Operacion",
        "Otros Gastos", "Otros Productos"};
        List lista;

        Otras o = new Otras();
        for(int k =0; k<otras.getRowCount();k++){
            lista = new ArrayList();
            try {
                if(otras.getValueAt(k, 1)!=null){
                    lista.add(otras.getValueAt(k,1).toString());
                    o.setAbonos(O[k], lista);
                }
            } catch (Exception e) {
            }
        }

        return o;
    }

    public void cargarDatos1 () {
        activoCirculante.setValueAt(46000, 0, 1);
        activoCirculante.setValueAt(86000, 1, 1);
        activoCirculante.setValueAt(23000, 2, 1);
        activoCirculante.setValueAt(56000, 3, 1);
        activoCirculante.setValueAt(35000, 4, 1);
        activoCirculante.setValueAt(86000, 5, 1);

        activoFijo.setValueAt(null, 0, 1);
        activoFijo.setValueAt(null, 1, 1);
        activoFijo.setValueAt(164000, 2, 1);
        activoFijo.setValueAt(98000, 3, 1);
        activoFijo.setValueAt(null, 4, 1);
        activoFijo.setValueAt(250000, 5, 1);

        activoDiferido.setValueAt(null, 0, 1);
        activoDiferido.setValueAt(58000, 1, 1);
        activoDiferido.setValueAt(90000, 2, 1);

        pasivoCirculante.setValueAt(45000, 0, 1);
        pasivoCirculante.setValueAt(72000, 1, 1);
        pasivoCirculante.setValueAt(90000, 2, 1);

        pasivoFijo.setValueAt(null, 0, 1);
        pasivoFijo.setValueAt(110000, 1, 1);

        capital.setValueAt(425000, 0, 1);

        otras.setValueAt(1140000, 0, 1);
        otras.setValueAt(94000, 1, 1);
        otras.setValueAt(796000, 2, 1);
        otras.setValueAt(null, 3, 1);
        otras.setValueAt(null, 4, 1);
    }

    public void cargarDatos2 () {
        activoCirculante.setValueAt(724000, 0, 1);
        activoCirculante.setValueAt(972000, 1, 1);
        activoCirculante.setValueAt(638000, 2, 1);
        activoCirculante.setValueAt(193000, 3, 1);
        activoCirculante.setValueAt(264000, 4, 1);
        activoCirculante.setValueAt(700000, 5, 1);

        activoFijo.setValueAt(1200000, 0, 1);
        activoFijo.setValueAt(3000000, 1, 1);
        activoFijo.setValueAt(200000, 2, 1);
        activoFijo.setValueAt(149500, 3, 1);
        activoFijo.setValueAt(215000, 4, 1);
        activoFijo.setValueAt(174000, 5, 1);

        activoDiferido.setValueAt(null, 0, 1);
        activoDiferido.setValueAt(58000, 1, 1);
        activoDiferido.setValueAt(12000, 2, 1);

        pasivoCirculante.setValueAt(1340000, 0, 1);
        pasivoCirculante.setValueAt(446000, 1, 1);
        pasivoCirculante.setValueAt(600000, 2, 1);

        pasivoFijo.setValueAt(1500000, 0, 1);
        pasivoFijo.setValueAt(914000, 1, 1);

        capital.setValueAt(3200000, 0, 1);

        otras.setValueAt(4200000, 0, 1);
        otras.setValueAt(630000, 1, 1);
        otras.setValueAt(3070500, 2, 1);
        otras.setValueAt(null, 3, 1);
        otras.setValueAt(null, 4, 1);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelActivoCirculante = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        pasivoFijo = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        capital = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        activoDiferido = new javax.swing.JTable();
        labelOtras = new javax.swing.JLabel();
        labelPasivoCirculante = new javax.swing.JLabel();
        labelActivoFijo = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        pasivoCirculante = new javax.swing.JTable();
        labelCapital = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        activoFijo = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        otras = new javax.swing.JTable();
        labelActivoDiferido = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        activoCirculante = new javax.swing.JTable();
        labelPasivoFijo = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(DatosRecientes.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(944, 562));

        labelActivoCirculante.setText(resourceMap.getString("labelActivoCirculante.text")); // NOI18N
        labelActivoCirculante.setName("labelActivoCirculante"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        pasivoFijo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Acreedores Hipotecarios", null},
                {"Documentos por Pagar", null}
            },
            new String [] {
                "Cuenta", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pasivoFijo.setName("pasivoFijo"); // NOI18N
        jScrollPane5.setViewportView(pasivoFijo);

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        capital.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Capital Social", null}
            },
            new String [] {
                "Cuenta", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        capital.setName("capital"); // NOI18N
        jScrollPane6.setViewportView(capital);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        activoDiferido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Depósitos en Garantía", null},
                {"Gastos de Instalación", null},
                {"Gastos de Organización", null}
            },
            new String [] {
                "Cuenta", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        activoDiferido.setName("activoDiferido"); // NOI18N
        jScrollPane3.setViewportView(activoDiferido);

        labelOtras.setText(resourceMap.getString("labelOtras.text")); // NOI18N
        labelOtras.setName("labelOtras"); // NOI18N

        labelPasivoCirculante.setText(resourceMap.getString("labelPasivoCirculante.text")); // NOI18N
        labelPasivoCirculante.setName("labelPasivoCirculante"); // NOI18N

        labelActivoFijo.setText(resourceMap.getString("labelActivoFijo.text")); // NOI18N
        labelActivoFijo.setName("labelActivoFijo"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        pasivoCirculante.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Proveedores", null},
                {"Acreedores", null},
                {"Documentos por Pagar", null}
            },
            new String [] {
                "Cuenta", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pasivoCirculante.setName("pasivoCirculante"); // NOI18N
        jScrollPane4.setViewportView(pasivoCirculante);

        labelCapital.setText(resourceMap.getString("labelCapital.text")); // NOI18N
        labelCapital.setName("labelCapital"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        activoFijo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Terrenos", null},
                {"Edificios", null},
                {"Mobiliario y Equipo", null},
                {"Equipo de Cómputo", null},
                {"Equipo de Reparto", null},
                {"Equipo de Transporte", null}
            },
            new String [] {
                "Cuenta", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        activoFijo.setName("activoFijo"); // NOI18N
        jScrollPane2.setViewportView(activoFijo);

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        otras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Ventas", null},
                {"Costo de Ventas", null},
                {"Gastos de Operación", null},
                {"Otros Gastos", null},
                {"Otros Productos", null}
            },
            new String [] {
                "Cuenta", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        otras.setName("otras"); // NOI18N
        jScrollPane7.setViewportView(otras);

        labelActivoDiferido.setText(resourceMap.getString("labelActivoDiferido.text")); // NOI18N
        labelActivoDiferido.setName("labelActivoDiferido"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        activoCirculante.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Caja", null},
                {"Bancos", null},
                {"Almacén", null},
                {"Clientes", null},
                {"Deudores", null},
                {"Documentos por Cobrar", null}
            },
            new String [] {
                "Cuenta", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        activoCirculante.setName("activoCirculante"); // NOI18N
        jScrollPane1.setViewportView(activoCirculante);

        labelPasivoFijo.setText(resourceMap.getString("labelPasivoFijo.text")); // NOI18N
        labelPasivoFijo.setName("labelPasivoFijo"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(391, 391, 391)
                                .addComponent(labelPasivoCirculante))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(99, 99, 99)
                                            .addComponent(labelActivoCirculante))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(105, 105, 105)
                                            .addComponent(labelActivoFijo)
                                            .addGap(105, 105, 105)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(99, 99, 99)
                                                .addComponent(labelActivoDiferido)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(152, 152, 152)
                                        .addComponent(labelPasivoFijo))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane5, 0, 0, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(155, 155, 155)
                                    .addComponent(labelCapital)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(36, 36, 36)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane7, 0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(labelOtras)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 619, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelActivoCirculante)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelPasivoCirculante)
                        .addComponent(labelCapital)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelActivoFijo)
                                    .addComponent(labelPasivoFijo)
                                    .addComponent(labelOtras))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(labelActivoDiferido))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JTable activoCirculante;
    protected javax.swing.JTable activoDiferido;
    protected javax.swing.JTable activoFijo;
    protected javax.swing.JTable capital;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel labelActivoCirculante;
    private javax.swing.JLabel labelActivoDiferido;
    private javax.swing.JLabel labelActivoFijo;
    private javax.swing.JLabel labelCapital;
    private javax.swing.JLabel labelOtras;
    private javax.swing.JLabel labelPasivoCirculante;
    private javax.swing.JLabel labelPasivoFijo;
    protected javax.swing.JTable otras;
    protected javax.swing.JTable pasivoCirculante;
    protected javax.swing.JTable pasivoFijo;
    // End of variables declaration//GEN-END:variables

}
