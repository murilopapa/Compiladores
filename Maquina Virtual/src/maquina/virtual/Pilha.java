//pilha vai conter as variaveis
package maquina.virtual;

import java.util.ArrayList;

public class Pilha {

    private ArrayList<Integer> dados = new ArrayList<Integer>(); // Classe Pilha
    private int s = -1;

    public int getTopoPilha() {
        int valor_topo = dados.get(s);
        dados.remove(s);
        s--;
        return valor_topo;
    }

    public int getIndexPilha(int index) {
        return dados.get(index);
    }

    public void setIndexPilha(int index, int value) {
        dados.set(index, value);
        s--;
    }

    public void setTopoPilha(int variavel) {
        dados.add(variavel);
        s++;
    }

    public int getDadosSize() {
        return dados.size();
    }
}
