/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cuentas;

/**
 *
 * @author Gabriel
 */
public class Pasivo {
    PasivoCirculante circulante;
    PasivoFijo fijo;

    public Pasivo(PasivoCirculante circulante, PasivoFijo fijo) {
        this.circulante = circulante;
        this.fijo = fijo;
    }

    public PasivoCirculante getCirculante() {
        return circulante;
    }

    public void setCirculante(PasivoCirculante circulante) {
        this.circulante = circulante;
    }

    public PasivoFijo getFijo() {
        return fijo;
    }

    public void setFijo(PasivoFijo fijo) {
        this.fijo = fijo;
    }

    public double saldo(){
        double saldo = 0.0;

        saldo = circulante.saldo() + fijo.saldo();

        return saldo;
    }
}
