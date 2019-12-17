/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class DatosCuenta {
    private String nombre;
    private List cargos;
    private List abonos;

    public DatosCuenta() {
        this.nombre = "";
        this.cargos = new ArrayList();
        this.abonos = new ArrayList();
    }

    public DatosCuenta( String n ) {
        this.nombre = n;
        this.cargos = new ArrayList();
        this.abonos = new ArrayList();
    }

    public DatosCuenta( String n, List cr, List ab ) {
        this.nombre = n;
        this.cargos = cr;
        this.abonos = ab;
    }

    public List getAbonos() {
        return abonos;
    }

    public void setAbonos(List abonos) {
        this.abonos = abonos;
    }

    public List getCargos() {
        return cargos;
    }

    public void setCargos(List cargos) {
        this.cargos = cargos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double saldo() {
        double sumaCargos = 0.0;
        double sumaAbonos = 0.0;
        double saldo;

        for( Object cargo : cargos )
            sumaCargos += Double.valueOf(cargo.toString());
        
        for( Object abono : abonos )
            sumaAbonos += Double.valueOf(abono.toString());

        saldo = ( sumaCargos > sumaAbonos )
                ? sumaCargos-sumaAbonos
                : sumaAbonos-sumaCargos;

        return saldo;
    }

}
