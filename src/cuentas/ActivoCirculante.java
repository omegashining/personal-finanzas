/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class ActivoCirculante extends Cuentas {
    public ActivoCirculante() {
        String nombre[] = {"Caja","Bancos","Almacen","Clientes","Deudores",
        "Documentos por Cobrar"};
        this.cuentas = new DatosCuenta[nombre.length];

        for( int i = 0; i < cuentas.length; i++ )
            cuentas[i] = new DatosCuenta(nombre[i]);
    }
}
