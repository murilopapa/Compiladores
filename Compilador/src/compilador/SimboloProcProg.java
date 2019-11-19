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

    private int rotulo;
    
    public SimboloProcProg(String lexema, int rotulo) {
        this.rotulo = rotulo;
        this.setLexema(lexema);
        this.setEscopo(true);
    }
}
