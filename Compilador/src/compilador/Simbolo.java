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
public class Simbolo {

    private String lexema;
    boolean escopo;

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public boolean isEscopo() {
        return escopo;
    }

    public void setEscopo(boolean escopo) {
        this.escopo = escopo;
    }
    
}
