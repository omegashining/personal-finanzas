/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AreaDeTrabajo.java
 *
 * Created on 29/11/2009, 08:59:36 AM
 */

package trabajo;

import balances.*;
import calculos.*;
import cuentas.*;
import estados.*;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import razones.*;
import datosempresa.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabriel
 */
public class AreaDeTrabajo extends javax.swing.JInternalFrame {
    private static int numeroTrabajo = 1;
    private List<String> cuentasAbiertas = new ArrayList<String>();
    private static int indice = 0;
    private Cuenta arreglo[] = new Cuenta[36];
    private ActivoCirculante ac;
    private ActivoFijo af;
    private ActivoDiferido ad;
    private PasivoCirculante pc;
    private PasivoFijo pf;
    private Activo activo;
    private Pasivo pasivo;
    private Capital capital;
    private Otras otras;
    private DatosAnteriores da = new DatosAnteriores();
    private DatosRecientes dr = new DatosRecientes();
    private int trabajo;

    /** Creates new form AreaDeTrabajo */
    public AreaDeTrabajo( int trabajo ) {
        initComponents();
        this.toFront();
        this.trabajo = trabajo;
        this.setTitle("Trabajo " + numeroTrabajo++);
        indice = 0;
        peu.setVisible(false);
        datos.setVisible(false);
        invers.setVisible(false);
        disminuciones.setVisible(false);
    }

    public void inicio( int mmm ){
        switch(mmm){
            case 0:
                da.cargarDatos1();
                da.setAnio(1);
                anoI.setSelectedIndex(1);
                anoF.setSelectedIndex(1);
                empresa.setText("Consultores Asociados S.A.");
                dr.cargarDatos1();
                break;
            case 1:
                da.cargarDatos2();
                da.setAnio(0);
                anoI.setSelectedIndex(0);
                anoF.setSelectedIndex(0);
                empresa.setText("Cinépolis S.A.");
                dr.cargarDatos2();
                break;
        }
        pane.add(da,javax.swing.JLayeredPane.DEFAULT_LAYER);
        pane.add(dr,javax.swing.JLayeredPane.DEFAULT_LAYER);

        activo = dr.getActivo();
        pasivo = dr.getPasivo();
        capital = dr.getCapital();
        otras = dr.getOtras();
        abrir(activo,pasivo,capital,otras);
    }

    private void abrir( Activo a, Pasivo p, Capital c, Otras o){
        String nombreAC[] = {"Caja",
        "Bancos",
        "Almacen",
        "Clientes",
        "Deudores",
        "Documentos por Cobrar"};
        String nombreAF[] = {"Terrenos",
        "Edificios",
        "Mobiliario y Equipo",
        "Equipo de Computo",
        "Equipo de Reparto",
        "Equipo de Transporte"};
        String nombreAD[] = {"Depositos en Garantia",
        "Gastos de Instalacion",
        "Gastos de Organizacion"};
        String nombrePC[] = {"Proveedores",
        "Acreedores",
        "Documentos por Pagar (C)"};
        String nombrePF[] = {"Acreedores Hipotecarios",
        "Documentos por Pagar (F)"};
        String nombreC[]  = {"Capital Social"};
        String nombreO[]  = {"Ventas",
        "Costo de Ventas",
        "Gastos de Operacion",
        "Otros Gastos",
        "Otros Productos"};

        String ti;
        String cu;
        String no;
        double sa;

        ac = a.getCirculante();
        ti = "Activo"; cu = "Circulante";
        for( int i = 0; i < nombreAC.length; i++ ){
            no = nombreAC[i];
            sa = ac.getSaldo(no);
            if( sa != 0 )
            disminuir2(ti,cu,no,sa,3);
        }

        af = a.getFijo();
        ti = "Activo"; cu = "Fijo";
        for( int i = 0; i < nombreAF.length; i++ ){
            no = nombreAF[i];
            sa = af.getSaldo(no);
            if( sa != 0 )
                disminuir2(ti,cu,no,sa,3);
        }

        ad = a.getDiferido();
        ti = "Activo"; cu = "Diferido";
        for( int i = 0; i < nombreAD.length; i++ ){
            no = nombreAD[i];
            sa = ad.getSaldo(no);
            if( sa != 0 )
                disminuir2(ti,cu,no,sa,3);
        }

        pc = p.getCirculante();
        ti = "Pasivo"; cu = "Circulante";
        for( int i = 0; i < nombrePC.length; i++ ){
            no = nombrePC[i];
            sa = pc.getSaldo(no);
            if( sa != 0 )
                disminuir1(ti,cu,no,sa);
        }

        pf = p.getFijo();
        ti = "Pasivo"; cu = "Fijo";
        for( int i = 0; i < nombrePF.length; i++ ){
            no = nombrePF[i];
            sa = pf.getSaldo(no);
            if( sa != 0 )
                disminuir1(ti,cu,no,sa);
        }

        ti = "Capital"; cu = "";
        for( int i = 0; i < nombreC.length; i++ ){
            no = nombreC[i];
            sa = capital.getSaldo(no);
            if( sa != 0 )
                disminuir1(ti,cu,no,sa);
        }

        ti = "Otras"; cu = "";
        for( int i = 0; i < nombreO.length; i++ ){
            no = nombreO[i];
            sa = otras.getSaldo(no);
            if( sa != 0 ){
                if( no.equals("Ventas") || no.equals("Otros Productos") )
                    disminuir1(ti,cu,no,sa);
                else
                    disminuir2(ti,cu,no,sa,3);
            }
        }
    }

    private boolean verificar( String n ) {
        boolean existe = false;

        for( String e: cuentasAbiertas ){
            if( e.equals(n))
                existe = true;
        }

        return existe;
    }

    private void organizar() {
        String tipo[] = {"Activo","Pasivo","Capital","Otras"};
        String cuenta[] = {"Circulante","Fijo","Diferido"};
        int posicion1 = 0, posicion2 = 0;

        for( int i = 0; i < indice; i++ ){
            String t = arreglo[i].getTipo();
            String c = arreglo[i].getCuenta();
            String n = arreglo[i].getNombre();
            List cargos = arreglo[i].getCargos();
            List abonos = arreglo[i].getAbonos();

            for( int j = 0; j < tipo.length; j++ ){
                if( t.equals(tipo[j]))
                    posicion1 = j;
            }

            for( int j = 0; j < cuenta.length; j++ ){
                if( c.equals(cuenta[j]))
                    posicion2 = j;
            }

            switch( posicion1 ){
                case 0:
                    switch( posicion2 ){
                        case 0:
                            ac.setAbonos(n, abonos);
                            ac.setCargos(n, cargos);
                            break;
                        case 1:
                            af.setAbonos(n, abonos);
                            af.setCargos(n, cargos);
                            break;
                        case 2:
                            ad.setAbonos(n, abonos);
                            ad.setCargos(n, cargos);
                            break;
                    }
                    break;
                case 1:
                    switch( posicion2 ){
                        case 0:
                            pc.setAbonos(n, abonos);
                            pc.setCargos(n, cargos);
                            break;
                        case 1:
                            pf.setAbonos(n, abonos);
                            pf.setCargos(n, cargos);
                            break;
                    }
                    break;
                case 2:
                    capital.setAbonos(n, abonos);
                    capital.setCargos(n, cargos);
                    break;
                case 3:
                    otras.setAbonos(n, abonos);
                    otras.setCargos(n, cargos);
                    break;
            }
        }

        activo = new Activo(ac,af,ad);
        pasivo = new Pasivo(pc,pf);
        List lista = new ArrayList();
        lista.add(otras.utilidadNeta());
        capital.setAbonos("Utilidad Neta", lista);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane = new javax.swing.JDesktopPane();
        datos = new javax.swing.JInternalFrame();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        abrir = new javax.swing.JButton();
        tipos = new javax.swing.JComboBox();
        cuentas = new javax.swing.JComboBox();
        nombres = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        empresa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        diaI = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        mesI = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        anoI = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        diaF = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        mesF = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        anoF = new javax.swing.JComboBox();
        disminuciones = new javax.swing.JInternalFrame();
        aplicarDisminuciones = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        amortiz = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        deprec = new javax.swing.JTable();
        peu = new javax.swing.JInternalFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        resPEU = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        datosPEU = new javax.swing.JTable();
        calcularPEU = new javax.swing.JButton();
        aplicarPEU = new javax.swing.JButton();
        invers = new javax.swing.JInternalFrame();
        calcularInversion = new javax.swing.JButton();
        aplicarInversion = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        datosInversion = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        valorFuturo = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        contabilidad = new javax.swing.JMenu();
        abrirCuenta = new javax.swing.JMenuItem();
        estadoResultados = new javax.swing.JMenuItem();
        balanceGeneral = new javax.swing.JMenuItem();
        menuAnalisisFinanciero = new javax.swing.JMenu();
        estadoPorcentajes = new javax.swing.JMenuItem();
        balancePorcentajes = new javax.swing.JMenuItem();
        datosAnteriores = new javax.swing.JMenuItem();
        estadoComparativo = new javax.swing.JMenuItem();
        balanceComparativo = new javax.swing.JMenuItem();
        razonesFinancieras = new javax.swing.JMenuItem();
        razonesEstandar = new javax.swing.JMenuItem();
        estadoDiferencias = new javax.swing.JMenuItem();
        balanceDiferencias = new javax.swing.JMenuItem();
        menuPlaneacionFinanciera = new javax.swing.JMenu();
        disminuir = new javax.swing.JMenuItem();
        puntoEquilibrio = new javax.swing.JMenuItem();
        puntoEqulibrioUtilidad = new javax.swing.JMenuItem();
        invertir = new javax.swing.JMenuItem();
        propuestas = new javax.swing.JMenuItem();
        estadoProforma = new javax.swing.JMenuItem();
        balanceProforma = new javax.swing.JMenuItem();
        origenAplicacion = new javax.swing.JMenuItem();

        setIconifiable(true);
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1280, 705));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(finanzas.FinanzasApp.class).getContext().getResourceMap(AreaDeTrabajo.class);
        pane.setBackground(resourceMap.getColor("pane.background")); // NOI18N
        pane.setName("pane"); // NOI18N

        datos.setClosable(true);
        datos.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        datos.setTitle(resourceMap.getString("datos.title")); // NOI18N
        datos.setName("datos"); // NOI18N
        datos.setVisible(true);

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        abrir.setText(resourceMap.getString("abrir.text")); // NOI18N
        abrir.setName("abrir"); // NOI18N
        abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirActionPerformed(evt);
            }
        });

        tipos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Pasivo", "Capital", "Otras" }));
        tipos.setName("tipos"); // NOI18N
        tipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiposActionPerformed(evt);
            }
        });

        cuentas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Circulante", "Fijo", "Diferido" }));
        cuentas.setName("cuentas"); // NOI18N
        cuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuentasActionPerformed(evt);
            }
        });

        nombres.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Caja", "Bancos", "Almacen", "Clientes", "Deudores", "Documentos por Cobrar" }));
        nombres.setName("nombres"); // NOI18N

        javax.swing.GroupLayout datosLayout = new javax.swing.GroupLayout(datos.getContentPane());
        datos.getContentPane().setLayout(datosLayout);
        datosLayout.setHorizontalGroup(
            datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, datosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tipos, 0, 139, Short.MAX_VALUE)
                    .addComponent(cuentas, 0, 139, Short.MAX_VALUE)
                    .addComponent(nombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, datosLayout.createSequentialGroup()
                .addContainerGap(166, Short.MAX_VALUE)
                .addComponent(abrir)
                .addGap(65, 65, 65))
        );
        datosLayout.setVerticalGroup(
            datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(datosLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel7))
                    .addGroup(datosLayout.createSequentialGroup()
                        .addComponent(tipos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cuentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(nombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(abrir)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        datos.setBounds(20, 60, 340, 200);
        pane.add(datos, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setBounds(10, 10, 111, 14);
        pane.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        empresa.setName("empresa"); // NOI18N
        empresa.setBounds(130, 10, 210, 20);
        pane.add(empresa, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setBounds(380, 10, 70, 14);
        pane.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setBounds(460, 10, 15, 14);
        pane.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        diaI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        diaI.setName("diaI"); // NOI18N
        diaI.setBounds(490, 10, 40, 20);
        pane.add(diaI, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jLabel4.setBounds(540, 10, 19, 14);
        pane.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mesI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Noviembre", "Diciembre" }));
        mesI.setName("mesI"); // NOI18N
        mesI.setBounds(570, 10, 90, 20);
        pane.add(mesI, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setBounds(680, 10, 19, 14);
        pane.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        anoI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015" }));
        anoI.setName("anoI"); // NOI18N
        anoI.setBounds(710, 10, 60, 20);
        pane.add(anoI, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setBounds(800, 10, 60, 14);
        pane.add(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setBounds(880, 10, 15, 14);
        pane.add(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        diaF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        diaF.setName("diaF"); // NOI18N
        diaF.setBounds(910, 10, 40, 20);
        pane.add(diaF, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setBounds(970, 10, 20, 14);
        pane.add(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mesF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Noviembre", "Diciembre" }));
        mesF.setName("mesF"); // NOI18N
        mesF.setBounds(1000, 10, 90, 20);
        pane.add(mesF, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setBounds(1110, 10, 20, 14);
        pane.add(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        anoF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015" }));
        anoF.setName("anoF"); // NOI18N
        anoF.setBounds(1140, 10, 60, 20);
        pane.add(anoF, javax.swing.JLayeredPane.DEFAULT_LAYER);

        disminuciones.setClosable(true);
        disminuciones.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        disminuciones.setTitle(resourceMap.getString("disminuciones.title")); // NOI18N
        disminuciones.setName("disminuciones"); // NOI18N
        disminuciones.setVisible(true);

        aplicarDisminuciones.setText(resourceMap.getString("aplicarDisminuciones.text")); // NOI18N
        aplicarDisminuciones.setName("aplicarDisminuciones"); // NOI18N
        aplicarDisminuciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarDisminucionesActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        amortiz.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Amortización Anual"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
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
        amortiz.setName("amortiz"); // NOI18N
        jScrollPane1.setViewportView(amortiz);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        deprec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Depreciación Anual"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
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
        deprec.setName("deprec"); // NOI18N
        jScrollPane2.setViewportView(deprec);

        javax.swing.GroupLayout disminucionesLayout = new javax.swing.GroupLayout(disminuciones.getContentPane());
        disminuciones.getContentPane().setLayout(disminucionesLayout);
        disminucionesLayout.setHorizontalGroup(
            disminucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(disminucionesLayout.createSequentialGroup()
                .addGroup(disminucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(disminucionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(disminucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE)))
                    .addGroup(disminucionesLayout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(aplicarDisminuciones)))
                .addContainerGap())
        );
        disminucionesLayout.setVerticalGroup(
            disminucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(disminucionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aplicarDisminuciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        disminuciones.setBounds(10, 290, 400, 260);
        pane.add(disminuciones, javax.swing.JLayeredPane.DEFAULT_LAYER);

        peu.setClosable(true);
        peu.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        peu.setTitle(resourceMap.getString("peu.title")); // NOI18N
        peu.setName("peu"); // NOI18N
        peu.setVisible(true);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        resPEU.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Ventas", null},
                {"Numero de Unidades", null},
                {"Costo Variable Total", null}
            },
            new String [] {
                "Datos", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
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
        resPEU.setName("resPEU"); // NOI18N
        jScrollPane3.setViewportView(resPEU);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        datosPEU.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {new Double(1.0), new Double(0.01)}
            },
            new String [] {
                "Precio Promedio", "Porcentaje de Utilidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        datosPEU.setName("datosPEU"); // NOI18N
        jScrollPane4.setViewportView(datosPEU);

        calcularPEU.setText(resourceMap.getString("calcularPEU.text")); // NOI18N
        calcularPEU.setName("calcularPEU"); // NOI18N
        calcularPEU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularPEUActionPerformed(evt);
            }
        });

        aplicarPEU.setText(resourceMap.getString("aplicarPEU.text")); // NOI18N
        aplicarPEU.setName("aplicarPEU"); // NOI18N
        aplicarPEU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarPEUActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout peuLayout = new javax.swing.GroupLayout(peu.getContentPane());
        peu.getContentPane().setLayout(peuLayout);
        peuLayout.setHorizontalGroup(
            peuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peuLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(peuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, peuLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(calcularPEU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(83, 83, 83)
                        .addComponent(aplicarPEU)
                        .addGap(67, 67, 67))
                    .addGroup(peuLayout.createSequentialGroup()
                        .addGroup(peuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                        .addContainerGap(20, Short.MAX_VALUE))))
        );
        peuLayout.setVerticalGroup(
            peuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(peuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calcularPEU)
                    .addComponent(aplicarPEU))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        peu.setBounds(380, 60, 370, 210);
        pane.add(peu, javax.swing.JLayeredPane.DEFAULT_LAYER);

        invers.setClosable(true);
        invers.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        invers.setTitle(resourceMap.getString("invers.title")); // NOI18N
        invers.setName("invers"); // NOI18N
        invers.setVisible(true);

        calcularInversion.setText(resourceMap.getString("calcularInversion.text")); // NOI18N
        calcularInversion.setName("calcularInversion"); // NOI18N
        calcularInversion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularInversionActionPerformed(evt);
            }
        });

        aplicarInversion.setText(resourceMap.getString("aplicarInversion.text")); // NOI18N
        aplicarInversion.setName("aplicarInversion"); // NOI18N
        aplicarInversion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarInversionActionPerformed(evt);
            }
        });

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        datosInversion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {new Double(1.0), new Double(0.1), new Integer(1), new Double(1.0)}
            },
            new String [] {
                "Valor presente", "Interes", "n", "Periodos por año"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        datosInversion.setName("datosInversion"); // NOI18N
        jScrollPane5.setViewportView(datosInversion);

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        valorFuturo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Valor Futuro"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        valorFuturo.setName("valorFuturo"); // NOI18N
        jScrollPane6.setViewportView(valorFuturo);

        javax.swing.GroupLayout inversLayout = new javax.swing.GroupLayout(invers.getContentPane());
        invers.getContentPane().setLayout(inversLayout);
        inversLayout.setHorizontalGroup(
            inversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inversLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(calcularInversion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addComponent(aplicarInversion)
                .addGap(81, 81, 81))
            .addGroup(inversLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(inversLayout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );
        inversLayout.setVerticalGroup(
            inversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inversLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aplicarInversion)
                    .addComponent(calcularInversion))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        invers.setBounds(470, 300, 430, 220);
        pane.add(invers, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        contabilidad.setText(resourceMap.getString("contabilidad.text")); // NOI18N
        contabilidad.setName("contabilidad"); // NOI18N

        abrirCuenta.setText(resourceMap.getString("abrirCuenta.text")); // NOI18N
        abrirCuenta.setName("abrirCuenta"); // NOI18N
        abrirCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirCuentaActionPerformed(evt);
            }
        });
        contabilidad.add(abrirCuenta);

        estadoResultados.setText(resourceMap.getString("estadoResultados.text")); // NOI18N
        estadoResultados.setName("estadoResultados"); // NOI18N
        estadoResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoResultadosActionPerformed(evt);
            }
        });
        contabilidad.add(estadoResultados);

        balanceGeneral.setText(resourceMap.getString("balanceGeneral.text")); // NOI18N
        balanceGeneral.setName("balanceGeneral"); // NOI18N
        balanceGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceGeneralActionPerformed(evt);
            }
        });
        contabilidad.add(balanceGeneral);

        jMenuBar1.add(contabilidad);

        menuAnalisisFinanciero.setText(resourceMap.getString("menuAnalisisFinanciero.text")); // NOI18N
        menuAnalisisFinanciero.setName("menuAnalisisFinanciero"); // NOI18N

        estadoPorcentajes.setText(resourceMap.getString("estadoPorcentajes.text")); // NOI18N
        estadoPorcentajes.setName("estadoPorcentajes"); // NOI18N
        estadoPorcentajes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoPorcentajesActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(estadoPorcentajes);

        balancePorcentajes.setText(resourceMap.getString("balancePorcentajes.text")); // NOI18N
        balancePorcentajes.setName("balancePorcentajes"); // NOI18N
        balancePorcentajes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balancePorcentajesActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(balancePorcentajes);

        datosAnteriores.setText(resourceMap.getString("datosAnteriores.text")); // NOI18N
        datosAnteriores.setName("datosAnteriores"); // NOI18N
        datosAnteriores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datosAnterioresActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(datosAnteriores);

        estadoComparativo.setText(resourceMap.getString("estadoComparativo.text")); // NOI18N
        estadoComparativo.setName("estadoComparativo"); // NOI18N
        estadoComparativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoComparativoActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(estadoComparativo);

        balanceComparativo.setText(resourceMap.getString("balanceComparativo.text")); // NOI18N
        balanceComparativo.setName("balanceComparativo"); // NOI18N
        balanceComparativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceComparativoActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(balanceComparativo);

        razonesFinancieras.setText(resourceMap.getString("razonesFinancieras.text")); // NOI18N
        razonesFinancieras.setName("razonesFinancieras"); // NOI18N
        razonesFinancieras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                razonesFinancierasActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(razonesFinancieras);

        razonesEstandar.setText(resourceMap.getString("razonesEstandar.text")); // NOI18N
        razonesEstandar.setName("razonesEstandar"); // NOI18N
        razonesEstandar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                razonesEstandarActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(razonesEstandar);

        estadoDiferencias.setText(resourceMap.getString("estadoDiferencias.text")); // NOI18N
        estadoDiferencias.setName("estadoDiferencias"); // NOI18N
        estadoDiferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoDiferenciasActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(estadoDiferencias);

        balanceDiferencias.setText(resourceMap.getString("balanceDiferencias.text")); // NOI18N
        balanceDiferencias.setName("balanceDiferencias"); // NOI18N
        balanceDiferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceDiferenciasActionPerformed(evt);
            }
        });
        menuAnalisisFinanciero.add(balanceDiferencias);

        jMenuBar1.add(menuAnalisisFinanciero);

        menuPlaneacionFinanciera.setText(resourceMap.getString("menuPlaneacionFinanciera.text")); // NOI18N
        menuPlaneacionFinanciera.setName("menuPlaneacionFinanciera"); // NOI18N
        menuPlaneacionFinanciera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPlaneacionFinancieraActionPerformed(evt);
            }
        });

        disminuir.setText(resourceMap.getString("disminuir.text")); // NOI18N
        disminuir.setName("disminuir"); // NOI18N
        disminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disminuirActionPerformed(evt);
            }
        });
        menuPlaneacionFinanciera.add(disminuir);

        puntoEquilibrio.setText(resourceMap.getString("puntoEquilibrio.text")); // NOI18N
        puntoEquilibrio.setName("puntoEquilibrio"); // NOI18N
        puntoEquilibrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puntoEquilibrioActionPerformed(evt);
            }
        });
        menuPlaneacionFinanciera.add(puntoEquilibrio);

        puntoEqulibrioUtilidad.setText(resourceMap.getString("puntoEqulibrioUtilidad.text")); // NOI18N
        puntoEqulibrioUtilidad.setName("puntoEqulibrioUtilidad"); // NOI18N
        puntoEqulibrioUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puntoEqulibrioUtilidadActionPerformed(evt);
            }
        });
        menuPlaneacionFinanciera.add(puntoEqulibrioUtilidad);

        invertir.setText(resourceMap.getString("invertir.text")); // NOI18N
        invertir.setName("invertir"); // NOI18N
        invertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invertirActionPerformed(evt);
            }
        });
        menuPlaneacionFinanciera.add(invertir);

        propuestas.setText(resourceMap.getString("propuestas.text")); // NOI18N
        propuestas.setName("propuestas"); // NOI18N
        propuestas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propuestasActionPerformed(evt);
            }
        });
        menuPlaneacionFinanciera.add(propuestas);

        estadoProforma.setText(resourceMap.getString("estadoProforma.text")); // NOI18N
        estadoProforma.setName("estadoProforma"); // NOI18N
        estadoProforma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoProformaActionPerformed(evt);
            }
        });
        menuPlaneacionFinanciera.add(estadoProforma);

        balanceProforma.setText(resourceMap.getString("balanceProforma.text")); // NOI18N
        balanceProforma.setName("balanceProforma"); // NOI18N
        balanceProforma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceProformaActionPerformed(evt);
            }
        });
        menuPlaneacionFinanciera.add(balanceProforma);

        origenAplicacion.setText(resourceMap.getString("origenAplicacion.text")); // NOI18N
        origenAplicacion.setName("origenAplicacion"); // NOI18N
        origenAplicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                origenAplicacionActionPerformed(evt);
            }
        });
        menuPlaneacionFinanciera.add(origenAplicacion);

        jMenuBar1.add(menuPlaneacionFinanciera);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, 1264, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void abrirCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirCuentaActionPerformed
        datos.setLocation(450, 100);
        datos.setVisible(true);
        datos.toFront();
    }//GEN-LAST:event_abrirCuentaActionPerformed

    private void estadoResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoResultadosActionPerformed
       String e = empresa.getText().toString();
       String f = diaI.getSelectedItem().toString() + " de "  +
                  mesI.getSelectedItem().toString() + " del " +
                  anoI.getSelectedItem().toString() + " al "  +
                  diaF.getSelectedItem().toString() + " de "  +
                  mesF.getSelectedItem().toString() + " del " +
                  anoF.getSelectedItem().toString();
       EstadoResultados er = new EstadoResultados(dr.getOtras(),e,f);
       pane.add(er, javax.swing.JLayeredPane.DEFAULT_LAYER);
       er.toFront();
    }//GEN-LAST:event_estadoResultadosActionPerformed

    private void balanceGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceGeneralActionPerformed
       String e = empresa.getText().toString();
       String f = diaF.getSelectedItem().toString() + " de "  +
                  mesF.getSelectedItem().toString() + " del " +
                  anoF.getSelectedItem().toString();
       BalanceGeneral bg = new BalanceGeneral(dr.getActivo(),dr.getPasivo(),dr.getCapital(),e,f);
       pane.add(bg, javax.swing.JLayeredPane.DEFAULT_LAYER);
       bg.toFront();
    }//GEN-LAST:event_balanceGeneralActionPerformed

    private void datosAnterioresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datosAnterioresActionPerformed
        da.setVisible(true);
        da.toFront();
    }//GEN-LAST:event_datosAnterioresActionPerformed

    private void estadoComparativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoComparativoActionPerformed
        String e = empresa.getText().toString();
        EstadoComparativo erc = new EstadoComparativo(da.getOtras(),dr.getOtras(),e,da.getAnio(),anoF.getSelectedItem().toString());
        pane.add(erc, javax.swing.JLayeredPane.DEFAULT_LAYER);
        erc.toFront();
    }//GEN-LAST:event_estadoComparativoActionPerformed

    private void estadoDiferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoDiferenciasActionPerformed
        String e = empresa.getText().toString();
        EstadoDiferencias erd = new EstadoDiferencias(da.getOtras(),dr.getOtras(),e,da.getAnio(),anoF.getSelectedItem().toString());
        pane.add(erd, javax.swing.JLayeredPane.DEFAULT_LAYER);
        erd.toFront();
    }//GEN-LAST:event_estadoDiferenciasActionPerformed

    private void balanceComparativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceComparativoActionPerformed
        String e = empresa.getText().toString();
        BalanceComparativo bgc = new BalanceComparativo(da.getActivo(),da.getPasivo(),da.getCapital(),dr.getActivo(),dr.getPasivo(),dr.getCapital(),e,da.getAnio(),anoF.getSelectedItem().toString());
        pane.add(bgc, javax.swing.JLayeredPane.DEFAULT_LAYER);
        bgc.toFront();
    }//GEN-LAST:event_balanceComparativoActionPerformed

    private void balanceDiferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceDiferenciasActionPerformed
        String e = empresa.getText().toString();
        BalanceDiferencias bgd = new BalanceDiferencias(da.getActivo(),da.getPasivo(),da.getCapital(),dr.getActivo(),dr.getPasivo(),dr.getCapital(),e,da.getAnio(),anoF.getSelectedItem().toString());
        pane.add(bgd, javax.swing.JLayeredPane.DEFAULT_LAYER);
        bgd.toFront();
    }//GEN-LAST:event_balanceDiferenciasActionPerformed

    private void razonesFinancierasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_razonesFinancierasActionPerformed
        RazonesFinancieras rf = new RazonesFinancieras(dr.getActivo(),dr.getPasivo(),dr.getCapital(),dr.getOtras());
        pane.add(rf, javax.swing.JLayeredPane.DEFAULT_LAYER);
        rf.toFront();
    }//GEN-LAST:event_razonesFinancierasActionPerformed

    private void razonesEstandarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_razonesEstandarActionPerformed
        RazonesEstandar re = new RazonesEstandar(dr.getActivo(),dr.getPasivo(),dr.getCapital(),dr.getOtras(),trabajo);
        pane.add(re, javax.swing.JLayeredPane.DEFAULT_LAYER);
        re.toFront();
    }//GEN-LAST:event_razonesEstandarActionPerformed

    private void disminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disminuirActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) deprec.getModel();
        modelo.setRowCount(0);
        int x = 0;
        double saldo = 0.0;

        saldo = dr.getActivo().getFijo().getCuenta("Edificios").saldo();
        if( saldo != 0 ){
            modelo.addRow(new Object[2]);
            modelo.setValueAt("Edificios", x, 0);
            saldo = saldo * .05;
            modelo.setValueAt(saldo, x++, 1);
        }

        saldo = dr.getActivo().getFijo().getCuenta("Mobiliario y Equipo").saldo();
        if( saldo != 0 ){
            modelo.addRow(new Object[2]);
            modelo.setValueAt("Mobiliario y Equipo", x, 0);
            saldo = saldo * .10;
            modelo.setValueAt(saldo, x++, 1);
        }

        saldo = dr.getActivo().getFijo().getCuenta("Equipo de Computo").saldo();
        if( saldo != 0 ){
            modelo.addRow(new Object[2]);
            modelo.setValueAt("Equipo de Computo", x, 0);
            saldo = saldo * .25;
            modelo.setValueAt(saldo, x++, 1);
        }

        saldo = dr.getActivo().getFijo().getCuenta("Equipo de Reparto").saldo();
        if( saldo != 0 ){
            modelo.addRow(new Object[2]);
            modelo.setValueAt("Equipo de Reparto", x, 0);
            saldo = saldo * .20;
            modelo.setValueAt(saldo, x++, 1);
        }

        saldo = dr.getActivo().getFijo().getCuenta("Equipo de Transporte").saldo();
        if( saldo != 0 ){
            modelo.addRow(new Object[2]);
            modelo.setValueAt("Equipo de Transporte", x, 0);
            saldo = saldo * .20;
            modelo.setValueAt(saldo, x++, 1);
        }

        modelo = (DefaultTableModel) amortiz.getModel();
        modelo.setRowCount(0);
        x = 0;
        saldo = 0.0;

        saldo = dr.getActivo().getDiferido().getCuenta("Gastos de Instalacion").saldo();
        if( saldo != 0 ){
            modelo.addRow(new Object[2]);
            modelo.setValueAt("Gastos de Instalacion", x, 0);
            saldo = saldo * .05;
            modelo.setValueAt(saldo, x++, 1);
        }

        saldo = dr.getActivo().getDiferido().getCuenta("Gastos de Organizacion").saldo();
        if( saldo != 0 ){
            modelo.addRow(new Object[2]);
            modelo.setValueAt("Gastos de Organizacion", x, 0);
            saldo = saldo * .05;
            modelo.setValueAt(saldo, x++, 1);
        }

        disminuciones.setLocation(450, 100);
        disminuciones.setVisible(true);
        disminuciones.toFront();
    }//GEN-LAST:event_disminuirActionPerformed

    private void puntoEquilibrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puntoEquilibrioActionPerformed
        if( dr.getOtras().utilidadNeta() != 0 ){
            PuntoEquilibrio peq = new PuntoEquilibrio(dr.getOtras(), trabajo);
            pane.add(peq, javax.swing.JLayeredPane.DEFAULT_LAYER);
            peq.toFront();
        }
    }//GEN-LAST:event_puntoEquilibrioActionPerformed

    private void aplicarDisminucionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicarDisminucionesActionPerformed
        String t, c, n;
        double s = 0.0;
        int g;

        for( int i = 0; i < deprec.getRowCount(); i++ ){
            t = "Activo";
            c = "Fijo";
            n = "Depreciacion de " + deprec.getValueAt(i, 0).toString();
            s = Double.parseDouble(deprec.getValueAt(i, 1).toString());
            g = disminuir1(t,c,n,s);

            if( g == 1 || g == 2 ){
                t = "Otras";
                c = "";
                n = "Gastos de Operacion";
                disminuir2(t,c,n,s,g);
            }
        }

        for( int i = 0; i < amortiz.getRowCount(); i++ ){
            t = "Activo";
            c = "Diferido";
            n = "Amortizacion de " + amortiz.getValueAt(i, 0).toString();
            s = Double.parseDouble(amortiz.getValueAt(i, 1).toString());
            g = disminuir1(t,c,n,s);

            if( g == 1 || g == 2 ){
                t = "Otras";
                c = "";
                n = "Gastos de Operacion";
                disminuir2(t,c,n,s,g);
            }
        }

        disminuciones.setVisible(false);
}//GEN-LAST:event_aplicarDisminucionesActionPerformed

    private void cuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuentasActionPerformed
        String nombreAC[] = {"Caja",
        "Bancos",
        "Almacen",
        "Clientes",
        "Deudores",
        "Documentos por Cobrar"};
        String nombreAF[] = {"Terrenos",
        "Edificios",
        "Mobiliario y Equipo",
        "Equipo de Computo",
        "Equipo de Reparto",
        "Equipo de Transporte"};
        String nombreAD[] = {"Depositos en Garantia",
        "Gastos de Instalacion",
        "Gastos de Organizacion"};
        String nombrePC[] = {"Proveedores",
        "Acreedores",
        "Documentos por Pagar (C)"};
        String nombrePF[] = {"Acreedores Hipotecarios",
        "Documentos por Pagar (F)"};
        String nombreC[]  = {"Capital Social"};
        String nombreO[]  = {"Ventas",
        "Costo de Ventas",
        "Gastos de Operacion",
        "Otros Gastos",
        "Otros Productos"};

        JComboBox modelo = new JComboBox();
        int i = tipos.getSelectedIndex();
        int j = cuentas.getSelectedIndex();

        switch( i ){
            case 0:
                switch( j ){
                    case 0:
                        modelo = new JComboBox(nombreAC);
                        break;
                    case 1:
                        modelo = new JComboBox(nombreAF);
                        break;
                    case 2:
                        modelo = new JComboBox(nombreAD);
                        break;
                }
                break;
            case 1:
                switch( j ){
                    case 0:
                        modelo = new JComboBox(nombrePC);
                        break;
                    case 1:
                        modelo = new JComboBox(nombrePF);
                        break;
                }
                break;
            case 2:
                modelo = new JComboBox(nombreC);
                break;
            case 3:
                modelo = new JComboBox(nombreO);
                break;
        }
        nombres.setModel(modelo.getModel());
}//GEN-LAST:event_cuentasActionPerformed

    private void tiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiposActionPerformed
        String cuentaA[]  = {"Circulante",
        "Fijo",
        "Diferido"};
        String cuentaP[]  = {"Circulante",
        "Fijo"};
        String cuentaCO[] = {""};
        String nombreAC[] = {"Caja",
        "Bancos",
        "Almacen",
        "Clientes",
        "Deudores",
        "Documentos por Cobrar"};
        String nombrePC[] = {"Proveedores",
        "Acreedores",
        "Documentos por Pagar (C)"};
        String nombreC[]  = {"Capital Social"};
        String nombreO[]  = {"Ventas",
        "Costo de Ventas",
        "Gastos de Operacion",
        "Otros Gastos",
        "Otros Productos"};

        JComboBox modelo1 = new JComboBox();
        JComboBox modelo2 = new JComboBox();
        int i = tipos.getSelectedIndex();

        switch( i ){
            case 0:
                modelo1 = new JComboBox(cuentaA);
                modelo2 = new JComboBox(nombreAC);
                break;
            case 1:
                modelo1 = new JComboBox(cuentaP);
                modelo2 = new JComboBox(nombrePC);
                break;
            case 2:
                modelo1 = new JComboBox(cuentaCO);
                modelo2 = new JComboBox(nombreC);
                break;
            case 3:
                modelo1 = new JComboBox(cuentaCO);
                modelo2 = new JComboBox(nombreO);
                break;
        }
        cuentas.setModel(modelo1.getModel());
        nombres.setModel(modelo2.getModel());
}//GEN-LAST:event_tiposActionPerformed

    private void abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirActionPerformed
        String t = tipos.getSelectedItem().toString();
        String c = cuentas.getSelectedItem().toString();
        String n = nombres.getSelectedItem().toString();

        if( !verificar( n ) ){
            datos.setVisible(false);
            arreglo[indice] = new Cuenta(t, c, n);
            pane.add(arreglo[indice]);
            arreglo[indice].setVisible(true);
            arreglo[indice++].toFront();
            cuentasAbiertas.add( n );
        } else
            JOptionPane.showMessageDialog( null, "La cuenta ya esta abierta");
}//GEN-LAST:event_abrirActionPerformed

    private void calcularPEUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularPEUActionPerformed
        double pequ, por, pre, uti;
        pre = Double.parseDouble(datosPEU.getValueAt(0,0).toString());
        uti = Double.parseDouble(datosPEU.getValueAt(0,1).toString());
        por = pre * (dr.getOtras().getCuenta("Costo de Ventas").saldo()/ dr.getOtras().getCuenta("Ventas").saldo());
        pequ = (dr.getOtras().gastosDeOperacion()+(dr.getCapital().getCuenta("Capital Social").saldo()*uti))/(1-(por/pre));

        resPEU.setValueAt(pequ, 0, 1);
        resPEU.setValueAt(pequ/pre, 1, 1);
        resPEU.setValueAt((pequ/pre)*por, 2, 1);
}//GEN-LAST:event_calcularPEUActionPerformed

    private void aplicarPEUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicarPEUActionPerformed
        organizar();
        double ventas1 = Double.parseDouble(resPEU.getValueAt(0,1).toString());
        double costo1 = Double.parseDouble(resPEU.getValueAt(2,1).toString());
        double ventas2 = dr.getOtras().getCuenta("Ventas").saldo();
        double costo2 = dr.getOtras().getCuenta("Costo de Ventas").saldo();

        if( !(ventas1 == ventas2) ){
            if( ventas1 > ventas2 ){
                double ventas = ventas1 - ventas2;
                double costo = costo1 - costo2;
                disminuir2("Otras","","Ventas",ventas,2);
                disminuir2("Otras","","Costo de Ventas",costo,1);
                disminuir2("Activo","Circulante","Caja",ventas,1);
                disminuir2("Activo","Circulante","Almacen",costo,2);
            }else{
                double ventas = ventas2 - ventas1;
                double costo = costo2 - costo1;
                disminuir2("Otras","","Ventas",ventas,1);
                disminuir2("Otras","","Costo de Ventas",costo,2);
                disminuir2("Activo","Circulante","Caja",ventas,2);
                disminuir2("Activo","Circulante","Almacen",costo,1);
            }
        }
        JOptionPane.showMessageDialog( null, "Los cambios fueron aplicados a las cuentas: Ventas, Costo de Ventas, Caja y Almacén");
        peu.setVisible(false);
    }//GEN-LAST:event_aplicarPEUActionPerformed

    private void menuPlaneacionFinancieraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPlaneacionFinancieraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuPlaneacionFinancieraActionPerformed

    private void puntoEqulibrioUtilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puntoEqulibrioUtilidadActionPerformed
        peu.setLocation(450, 100);
        if( dr.getOtras().utilidadNeta() != 0 ){
            switch( trabajo ){
                case 0:
                    datosPEU.setValueAt(25000, 0, 0);
                    datosPEU.setValueAt(0.62, 0, 1);
                    break;
                case 1:
                    datosPEU.setValueAt(200, 0, 0);
                    datosPEU.setValueAt(0.24, 0, 1);
                    break;
            }
            peu.setVisible(true);
            peu.toFront();
        }
    }//GEN-LAST:event_puntoEqulibrioUtilidadActionPerformed

    private void calcularInversionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularInversionActionPerformed
        double valor = Double.parseDouble(datosInversion.getValueAt(0,0).toString());
        double interes = Double.parseDouble(datosInversion.getValueAt(0,1).toString());
        double anios = Double.parseDouble(datosInversion.getValueAt(0,2).toString());
        double periodos = Double.parseDouble(datosInversion.getValueAt(0,3).toString());

        double futuro = valor * Math.pow(( 1 + (interes/periodos)),anios);

        if( valor > activo.getCirculante().getCuenta("Bancos").saldo() )
            JOptionPane.showMessageDialog( null, "El valor presente excede el saldo de bancos");
        else
            valorFuturo.setValueAt(futuro, 0, 0);
}//GEN-LAST:event_calcularInversionActionPerformed

    private void aplicarInversionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicarInversionActionPerformed
        double valorF = 0;

        try {
            valorF = Double.parseDouble(valorFuturo.getValueAt(0, 0).toString());
        } catch (NumberFormatException numberFormatException) {
        }

        if( valorF != 0 ){
            disminuir2("Activo","Circulante","Bancos",valorF,1);
            disminuir2("Otras","","Gastos de Operacion",valorF,2);
            JOptionPane.showMessageDialog( null, "Los cambios fueron aplicados a las cuentas: Bancos y Gastos de Operación");
        }

        invers.setVisible(false);
}//GEN-LAST:event_aplicarInversionActionPerformed

    private void invertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invertirActionPerformed
        invers.setLocation(450, 100);
        if( activo.getCirculante().getCuenta("Bancos").saldo() != 0 ){
            switch(trabajo){
                case 0:
                    datosInversion.setValueAt(75000, 0, 0);
                    datosInversion.setValueAt(0.02, 0, 1);
                    datosInversion.setValueAt(1, 0, 2);
                    datosInversion.setValueAt(12, 0, 3);
                    break;
            }
            invers.setVisible(true);
            invers.toFront();
        }
    }//GEN-LAST:event_invertirActionPerformed

    private void estadoProformaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoProformaActionPerformed
       organizar();
       String e = empresa.getText().toString();
       EstadoProforma er = new EstadoProforma(otras,e);
       pane.add(er, javax.swing.JLayeredPane.DEFAULT_LAYER);
       er.toFront();
    }//GEN-LAST:event_estadoProformaActionPerformed

    private void balanceProformaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceProformaActionPerformed
       organizar();
       String e = empresa.getText().toString();
       BalanceProforma bg = new BalanceProforma(activo,pasivo,capital,e);
       pane.add(bg, javax.swing.JLayeredPane.DEFAULT_LAYER);
       bg.toFront();
    }//GEN-LAST:event_balanceProformaActionPerformed

    private void origenAplicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_origenAplicacionActionPerformed
       organizar();
       String e = empresa.getText().toString();
       EstadoOrigenAplicacion eoa = new EstadoOrigenAplicacion(activo,pasivo,capital,otras,dr.getActivo(),dr.getPasivo(),dr.getCapital(),dr.getOtras(),e,anoF.getSelectedItem().toString());
       pane.add(eoa, javax.swing.JLayeredPane.DEFAULT_LAYER);
       eoa.toFront();
    }//GEN-LAST:event_origenAplicacionActionPerformed

    private void estadoPorcentajesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoPorcentajesActionPerformed
       String e = empresa.getText().toString();
       EstadoPorcientos ep = new EstadoPorcientos(dr.getOtras(),e);
       pane.add(ep, javax.swing.JLayeredPane.DEFAULT_LAYER);
       ep.toFront();
    }//GEN-LAST:event_estadoPorcentajesActionPerformed

    private void balancePorcentajesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balancePorcentajesActionPerformed
       String e = empresa.getText().toString();
       BalancePorcientos bp = new BalancePorcientos(dr.getActivo(),dr.getPasivo(),dr.getCapital(),e);
       pane.add(bp, javax.swing.JLayeredPane.DEFAULT_LAYER);
       bp.toFront();
    }//GEN-LAST:event_balancePorcentajesActionPerformed

    private void propuestasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propuestasActionPerformed
        switch(trabajo){
            case 0:
                disminuir2("Pasivo","Circulante","Proveedores",pasivo.getCirculante().getSaldo("Proveedores")*.1,1);
                disminuir2("Activo","Circulante","Bancos",pasivo.getCirculante().getSaldo("Proveedores")*.1,2);
                disminuir2("Activo","Fijo","Equipo de Computo",70000,1);
                disminuir2("Activo","Circulante","Bancos",70000,2);
                JOptionPane.showMessageDialog(null, "Se aplicaron las siguientes propuestas:\n" +
                        "1) Pagar deuda de 10% de la cuenta de proveedores\n" +
                        "2) Comprar Equipo de computo por 70000");
                break;
            case 1:
                disminuir2("Pasivo","Circulante","Proveedores",pasivo.getCirculante().getSaldo("Proveedores")*.1,1);
                disminuir2("Activo","Circulante","Bancos",pasivo.getCirculante().getSaldo("Proveedores")*.1,2);
                disminuir2("Activo","Fijo","Mobiliario y Equipo",100000,1);
                disminuir2("Activo","Circulante","Bancos",100000,2);
                JOptionPane.showMessageDialog(null, "Se aplicaron las siguientes propuestas:\n" +
                        "1) Pagar deuda de 10% de la cuenta de proveedores\n" +
                        "2) Comprar Mobiliario y Equipo por 100000");
                break;
        }
    }//GEN-LAST:event_propuestasActionPerformed

    public int disminuir1( String t, String c, String n, double s ) {
        int l = 0;
        int f;

        if( !verificar( n ) ){
            arreglo[indice] = new Cuenta(t, c, n);
            arreglo[indice].abonar(s);
            arreglo[indice].setVisible(true);
            f = 1;
            pane.add(arreglo[indice], javax.swing.JLayeredPane.DEFAULT_LAYER);
            arreglo[indice++].toFront();
            if( !arreglo[indice-1].isIcon())
                try {
                arreglo[indice - 1].setIcon(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(AreaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            }
            cuentasAbiertas.add( n );
        } else {
            l = 0;
            for( String e: cuentasAbiertas ) {
                if( e.equals(n))
                    break;
                l++;
            }

            if( arreglo[l].getSaldo() != s ){
                if( arreglo[l].getSaldo() > s ){
                    arreglo[l].cargar(arreglo[l].getSaldo()-s);
                    f = 2;
                }
                else{
                    arreglo[l].abonar(s-arreglo[l].getSaldo());
                    f = 1;
                }
            }else
                f = 3;
         }

        return f;
    }

    public void disminuir2( String t, String c, String n, double s, int f ) {
        int l = 0;

        if( !verificar( n ) ){
            arreglo[indice] = new Cuenta(t, c, n);
            arreglo[indice].cargar(s);
            arreglo[indice].setVisible(true);
            pane.add(arreglo[indice], javax.swing.JLayeredPane.DEFAULT_LAYER);
            arreglo[indice++].toFront();
            if( !arreglo[indice-1].isIcon())
                try {
                arreglo[indice - 1].setIcon(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(AreaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            }
            cuentasAbiertas.add( n );
        } else {
            l = 0;
            for( String e: cuentasAbiertas ) {
                if( e.equals(n))
                    break;
                l++;
            }

            if( f == 1 )
                arreglo[l].cargar(s);

            if( f == 2 )
                arreglo[l].abonar(s);
         }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrir;
    private javax.swing.JMenuItem abrirCuenta;
    private javax.swing.JTable amortiz;
    private javax.swing.JComboBox anoF;
    private javax.swing.JComboBox anoI;
    private javax.swing.JButton aplicarDisminuciones;
    private javax.swing.JButton aplicarInversion;
    private javax.swing.JButton aplicarPEU;
    private javax.swing.JMenuItem balanceComparativo;
    private javax.swing.JMenuItem balanceDiferencias;
    private javax.swing.JMenuItem balanceGeneral;
    private javax.swing.JMenuItem balancePorcentajes;
    private javax.swing.JMenuItem balanceProforma;
    private javax.swing.JButton calcularInversion;
    private javax.swing.JButton calcularPEU;
    private javax.swing.JMenu contabilidad;
    private javax.swing.JComboBox cuentas;
    private javax.swing.JInternalFrame datos;
    private javax.swing.JMenuItem datosAnteriores;
    private javax.swing.JTable datosInversion;
    private javax.swing.JTable datosPEU;
    private javax.swing.JTable deprec;
    private javax.swing.JComboBox diaF;
    private javax.swing.JComboBox diaI;
    private javax.swing.JInternalFrame disminuciones;
    private javax.swing.JMenuItem disminuir;
    private javax.swing.JTextField empresa;
    private javax.swing.JMenuItem estadoComparativo;
    private javax.swing.JMenuItem estadoDiferencias;
    private javax.swing.JMenuItem estadoPorcentajes;
    private javax.swing.JMenuItem estadoProforma;
    private javax.swing.JMenuItem estadoResultados;
    private javax.swing.JInternalFrame invers;
    private javax.swing.JMenuItem invertir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JMenu menuAnalisisFinanciero;
    private javax.swing.JMenu menuPlaneacionFinanciera;
    private javax.swing.JComboBox mesF;
    private javax.swing.JComboBox mesI;
    private javax.swing.JComboBox nombres;
    private javax.swing.JMenuItem origenAplicacion;
    private javax.swing.JDesktopPane pane;
    private javax.swing.JInternalFrame peu;
    private javax.swing.JMenuItem propuestas;
    private javax.swing.JMenuItem puntoEquilibrio;
    private javax.swing.JMenuItem puntoEqulibrioUtilidad;
    private javax.swing.JMenuItem razonesEstandar;
    private javax.swing.JMenuItem razonesFinancieras;
    private javax.swing.JTable resPEU;
    private javax.swing.JComboBox tipos;
    private javax.swing.JTable valorFuturo;
    // End of variables declaration//GEN-END:variables

}
