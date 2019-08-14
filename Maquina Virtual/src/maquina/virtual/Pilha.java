//pilha vai conter as variaveis
package maquina.virtual;

import java.util.ArrayList;

public class Pilha {

    ArrayList<Integer> dados = new ArrayList<Integer>(); // Classe Pilha
    private int s = -1;

    //pop tira
    public int getTopoPilha() {
        s--;
        return dados.get(s + 1);
    }

    public int getIndexPilha(int index) {
        return dados.get(index);
    }

    //push coloca
    public void setTopoPilha(int variavel) {
        dados.add(variavel);
        s++;
    }

}
