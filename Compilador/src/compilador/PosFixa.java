/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;

/**
 *
 * @author math_
 */
public class PosFixa {
    private ArrayList<ElementoOperador> elemento = new ArrayList<ElementoOperador>();
    private ArrayList<ElementoOperador> auxPosFixa = new ArrayList<ElementoOperador>();
    private ArrayList<ElementoOperador> posFixa = new ArrayList<ElementoOperador>();
    
    public void geraPosFixa(){
        int i = 0;
        for(ElementoOperador auxElemento : elemento){
            if(auxElemento.getNome().equals("+u") || auxElemento.getNome().equals("-u") || auxElemento.getNome().equals("not")){
                
            } else if (auxElemento.getNome().equals("*") || auxElemento.getNome().equals("/")){
                if(auxPosFixa.get(i).getNome().equals("*") ||auxPosFixa.get(i).getNome().equals("/")){
                    //caso a prioridade seja menor ou igual ao atual da pilha aux, coloco o elemento
                    // de menor prioridade na fila aux e o de maior na pos fixa 
                    posFixa.add(auxPosFixa.get(i));
                    auxPosFixa.remove(i);
                    auxPosFixa.add(auxElemento);
                }
                else{
                    //caso a prioridade seja maior, apenas o adiciono na pilha aux
                    auxPosFixa.add(auxElemento);
                }
            } else if (auxElemento.getNome().equals("+") || auxElemento.getNome().equals("-")){
                if(auxPosFixa.get(i).getNome().equals("*") ||auxPosFixa.get(i).getNome().equals("/") || auxPosFixa.get(i).getNome().equals("+") ||auxPosFixa.get(i).getNome().equals("-")){
                    posFixa.add(auxPosFixa.get(i));
                    auxPosFixa.remove(i);
                    auxPosFixa.add(auxElemento);
                }
                else{
                    auxPosFixa.add(auxElemento);
                }
            } else if (auxElemento.getNome().equals(">") || auxElemento.getNome().equals("<") || auxElemento.getNome().equals(">=") || auxElemento.getNome().equals("<=") || auxElemento.getNome().equals("=") || auxElemento.getNome().equals("!=")){
                if(!auxPosFixa.get(i).getNome().equals("e") || !auxPosFixa.get(i).getNome().equals("ou")){
                    posFixa.add(auxPosFixa.get(i));
                    auxPosFixa.remove(i);
                    auxPosFixa.add(auxElemento);
                }
                else{
                    auxPosFixa.add(auxElemento);
                }
            } else if (auxElemento.getNome().equals("e")){
                if(!auxPosFixa.get(i).getNome().equals("ou")){
                    posFixa.add(auxPosFixa.get(i));
                    auxPosFixa.remove(i);
                    auxPosFixa.add(auxElemento);
                }
                else{
                    auxPosFixa.add(auxElemento);
                }
            } else if (auxElemento.getNome().equals("ou")){
                posFixa.add(auxPosFixa.get(i));
                auxPosFixa.remove(i);
                auxPosFixa.add(auxElemento);
            } else {
                posFixa.add(auxElemento);
            }
            i++;
        }
        //descarrega se sobrou algo na pilha auxiliar
        for(ElementoOperador aux : auxPosFixa){
            posFixa.add(aux);
        }
        for(ElementoOperador aux : posFixa){
            System.out.println(aux.getNome());
        }
    }
}
