/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class PasivoCirculante extends Cuentas {
    public PasivoCirculante() {
        String nombre[] = {"Proveedores","Acreedores","Documentos por Pagar (C)",};
        this.cuentas = new DatosCuenta[nombre.length];

        for( int i = 0; i < cuentas.length; i++ )
            cuentas[i] = new DatosCuenta(nombre[i]);
    }
}
