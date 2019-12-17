/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

import java.util.List;

/**
 *
 * @author Gabriel
 */
public abstract class Cuentas {
    DatosCuenta[] cuentas;

    public DatosCuenta[] getCuentas() {
        return cuentas;
    }

    public void setCuentas(DatosCuenta[] cuentas) {
        this.cuentas = cuentas;
    }

    public void setCargos( String nombre, List cargos ) {
        for( int i = 0; i < cuentas.length; i++ ){
            if( cuentas[i].getNombre().equals(nombre) )
                cuentas[i].setCargos(cargos);
        }
    }

    public void setAbonos( String nombre, List abonos ) {
        for( int i = 0; i < cuentas.length; i++ ){
            if( cuentas[i].getNombre().equals(nombre) )
                cuentas[i].setAbonos(abonos);
        }
    }

    public DatosCuenta getCuenta( String nombre ) {
        DatosCuenta cuenta = null;

        for( int i = 0; i < cuentas.length; i++ ){
            if( cuentas[i].getNombre().equals(nombre) ){
                cuenta = cuentas[i];
                break;
            }
        }

        return cuenta;
    }

    public double getSaldo( String nombre ) {
        double saldo = 0.0;

        for( int i = 0; i < cuentas.length; i++ ){
            if( cuentas[i].getNombre().equals(nombre) ){
                saldo = cuentas[i].saldo();
                break;
            }
        }

        return saldo;
    }

    public double saldo() {
        double saldo = 0.0;

        for( int i = 0; i < cuentas.length; i++ ){
            saldo += cuentas[i].saldo();
        }

        return saldo;
    }
}
