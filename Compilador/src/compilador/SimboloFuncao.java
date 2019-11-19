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
public class SimboloFuncao extends Simbolo {

    private String tipo;
    private int rotulo;
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public SimboloFuncao(String lexema, int rotulo) {
        this.rotulo = rotulo;
        this.setLexema(lexema);
        this.setEscopo(true);
    }
}
