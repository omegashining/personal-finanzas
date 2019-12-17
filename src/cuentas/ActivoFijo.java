/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class ActivoFijo extends Cuentas {
    public ActivoFijo() {
        String nombre[] = {"Terrenos","Edificios","Mobiliario y Equipo",
        "Equipo de Computo","Equipo de Reparto","Equipo de Transporte",
        "Depreciacion de Edificios","Depreciacion de Mobiliario y Equipo",
        "Depreciacion de Equipo de Computo","Depreciacion de Equipo de Reparto",
        "Depreciacion de Equipo de Transporte"};
        this.cuentas = new DatosCuenta[nombre.length];

        for( int i = 0; i < cuentas.length; i++ )
            cuentas[i] = new DatosCuenta(nombre[i]);
    }
}
