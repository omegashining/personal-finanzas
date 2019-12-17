/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class PasivoFijo extends Cuentas {
    public PasivoFijo() {
        String nombre[] = {"Acreedores Hipotecarios", "Documentos por Pagar (F)"};
        this.cuentas = new DatosCuenta[nombre.length];

        for( int i = 0; i < cuentas.length; i++ )
            cuentas[i] = new DatosCuenta(nombre[i]);
    }
}
