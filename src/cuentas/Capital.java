/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class Capital extends Cuentas {
    public Capital() {
        String nombre[] = {"Capital Social","Utilidad Neta"};
        this.cuentas = new DatosCuenta[nombre.length];

        for( int i = 0; i < cuentas.length; i++ )
            cuentas[i] = new DatosCuenta(nombre[i]);
    }
}
