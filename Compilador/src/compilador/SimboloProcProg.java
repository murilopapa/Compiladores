/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author murilo
 */
public class SimboloProcProg extends Simbolo {

    public SimboloProcProg(String lexema) {
        this.setLexema(lexema);
        this.setEscopo(true);
    }
}
