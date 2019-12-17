/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class Otras extends Cuentas {
    public Otras() {
        String nombre[] = { "Ventas", "Costo de Ventas", "Gastos de Operacion",
        "Otros Gastos", "Otros Productos"};
        this.cuentas = new DatosCuenta[nombre.length];

        for( int i = 0; i < cuentas.length; i++ )
            cuentas[i] = new DatosCuenta(nombre[i]);
    }

    public double utilidadBruta() {
        return getCuenta("Ventas").saldo()
             - getCuenta("Costo de Ventas").saldo();
    }

    public double gastosDeOperacion() {
        return getCuenta("Gastos de Operacion").saldo();
    }

    public double utilidadDeOperacion() {
        return utilidadBruta() - gastosDeOperacion();
    }

    public double utilidadNeta() {
        return utilidadDeOperacion()
             - getCuenta("Otros Gastos").saldo()
             + getCuenta("Otros Productos").saldo();
    }

    /*public double ISR() {
    return utilidadAntesDeImpuestos() * .36;
    }

    public double PTU() {
    return utilidadAntesDeImpuestos() * .10;
    }

    public double utilidadNeta() {
    return utilidadAntesDeImpuestos() - ISR() - PTU();
    }*/
}
