/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class ActivoDiferido extends Cuentas {
    public ActivoDiferido() {
        String nombre[] = {"Depositos en Garantia","Gastos de Instalacion",
        "Gastos de Organizacion","Amortizacion de Gastos de Instalacion",
        "Amortizacion de Gastos de Organizacion"};
        this.cuentas = new DatosCuenta[nombre.length];

        for( int i = 0; i < cuentas.length; i++ )
            cuentas[i] = new DatosCuenta(nombre[i]);
    }
}
