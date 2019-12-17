/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class Activo {
    ActivoCirculante circulante;
    ActivoFijo fijo;
    ActivoDiferido diferido;

    public Activo(ActivoCirculante circulante, ActivoFijo fijo, ActivoDiferido diferido) {
        this.circulante = circulante;
        this.fijo = fijo;
        this.diferido = diferido;
    }

    public ActivoCirculante getCirculante() {
        return circulante;
    }

    public void setCirculante(ActivoCirculante circulante) {
        this.circulante = circulante;
    }

    public ActivoDiferido getDiferido() {
        return diferido;
    }

    public void setDiferido(ActivoDiferido diferido) {
        this.diferido = diferido;
    }

    public ActivoFijo getFijo() {
        return fijo;
    }

    public void setFijo(ActivoFijo fijo) {
        this.fijo = fijo;
    }

    public double saldo() {
        double saldo = 0.0;

        saldo = circulante.saldo() + fijo.saldo() + diferido.saldo();

        return saldo;
    }
}
