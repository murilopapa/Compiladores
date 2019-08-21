package maquina.virtual;

import java.util.ArrayList;

public class Memoria {

    private ArrayList<Funcoes> funcoes = new ArrayList<Funcoes>();
    private int i = 0;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void addFuncao(String nome, String arg1, String arg2) {
        Funcoes funcao = new Funcoes();
        funcao.setFuncao(nome);
        funcao.setArg1(arg1);
        funcao.setArg2(arg2);
        funcoes.add(funcao);
        
    }

    public Funcoes getFuncByIndex(int index) {
        return funcoes.get(index);
    }

    
}
