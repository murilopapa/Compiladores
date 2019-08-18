package maquina.virtual;

import java.util.ArrayList;

public class Memoria {

    private ArrayList<Funcoes> funcoes = new ArrayList<Funcoes>();
    private ArrayList<Label> labels = new ArrayList<Label>();
    private int i = 0;
    private int utlima_linha_lida = 0;

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
        if ("NULL".equals(nome)) {
            Label new_label = new Label();
            new_label.setLabel(arg1);
            new_label.setLinha(utlima_linha_lida);
            labels.add(new_label);
        }
        utlima_linha_lida++;
    }

    public Funcoes getFuncByIndex(int index) {
        return funcoes.get(index);
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }
    
}
