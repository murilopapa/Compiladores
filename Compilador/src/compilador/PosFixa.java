package compilador;

import java.util.ArrayList;

public class PosFixa {

    private ArrayList<Elemento> elemento;
    private ArrayList<Elemento> auxPosFixa = new ArrayList<Elemento>();
    private ArrayList<Elemento> posFixa = new ArrayList<Elemento>();

    public void geraPosFixa(ArrayList<Elemento> elemento) {
        this.elemento = elemento;
        auxPosFixa.clear();
        posFixa.clear();
        int i = 0;
        for (Elemento auxElemento : elemento) {
            if (auxPosFixa.isEmpty() && !posFixa.isEmpty()) {
                auxPosFixa.add(auxElemento);
            } else {
                if (auxElemento.getNome().equals("+u") || auxElemento.getNome().equals("-u") || auxElemento.getNome().equals("not")) {

                } else if (auxElemento.getNome().equals("*") || auxElemento.getNome().equals("div")) {

                    if (!auxPosFixa.get(i).getNome().equals("(") && (auxPosFixa.get(i).getNome().equals("*") || auxPosFixa.get(i).getNome().equals("div"))) {
                        //caso a prioridade seja menor ou igual ao atual da pilha aux, coloco o elemento
                        // de menor prioridade na fila aux e o de maior na pos fixa 
                        posFixa.add(auxPosFixa.get(i));
                        auxPosFixa.remove(i);
                        auxPosFixa.add(auxElemento);
                    } else {
                        //caso a prioridade seja maior, apenas o adiciono na pilha aux
                        auxPosFixa.add(auxElemento);
                    }
                } else if (auxElemento.getNome().equals("+") || auxElemento.getNome().equals("-")) {
                    if (!auxPosFixa.get(i).getNome().equals("(") && (auxPosFixa.get(i).getNome().equals("*") || auxPosFixa.get(i).getNome().equals("div") || auxPosFixa.get(i).getNome().equals("+") || auxPosFixa.get(i).getNome().equals("-"))) {
                        posFixa.add(auxPosFixa.get(i));
                        auxPosFixa.remove(i);
                        auxPosFixa.add(auxElemento);
                    } else {
                        auxPosFixa.add(auxElemento);
                    }
                } else if (auxElemento.getNome().equals(">") || auxElemento.getNome().equals("<") || auxElemento.getNome().equals(">=") || auxElemento.getNome().equals("<=") || auxElemento.getNome().equals("=") || auxElemento.getNome().equals("!=")) {
                    if (!auxPosFixa.get(i).getNome().equals("(") && (!auxPosFixa.get(i).getNome().equals("e") || !auxPosFixa.get(i).getNome().equals("ou"))) {
                        posFixa.add(auxPosFixa.get(i));
                        auxPosFixa.remove(i);
                        auxPosFixa.add(auxElemento);
                    } else {
                        auxPosFixa.add(auxElemento);
                    }
                } else if (auxElemento.getNome().equals("e")) {
                    if (!auxPosFixa.get(i).getNome().equals("(") && !auxPosFixa.get(i).getNome().equals("ou")) {
                        posFixa.add(auxPosFixa.get(i));
                        auxPosFixa.remove(i);
                        auxPosFixa.add(auxElemento);
                    } else {
                        auxPosFixa.add(auxElemento);
                    }
                } else if (auxElemento.getNome().equals("ou")) {
                    posFixa.add(auxPosFixa.get(i));
                    auxPosFixa.remove(i);
                    auxPosFixa.add(auxElemento);
                } else if (auxElemento.getNome().equals("(")) {
                    auxPosFixa.add(auxElemento);
                } else if (auxElemento.getNome().equals(")")) {
                    while (!auxPosFixa.get(i).getNome().equals("(")) {
                        posFixa.add(auxPosFixa.get(i));
                        auxPosFixa.remove(i);
                        i = auxPosFixa.size() - 1;
                    }
                    i = auxPosFixa.size() - 1;
                    auxPosFixa.remove(i);
                } else {
                    posFixa.add(auxElemento);
                }
            }
            i = auxPosFixa.size() - 1;
        }
        //descarrega se sobrou algo na pilha auxiliar
        for (Elemento aux : auxPosFixa) {
            posFixa.add(aux);
        }

        System.out.println("POS FIXA:");
        for (Elemento e : posFixa) {
            System.out.print(" " + e.getNome());
        }
        for (Elemento e : posFixa) {
            if (e instanceof ElementoOperando) {
                System.out.print(" " + ((ElementoOperando) e).getTipo());
            }
        }
        System.out.println("");
        System.out.println("-------------------");

    }

    public String getTipoPosfixa() {
        /* + - * / > < <= >= = != e ou
        
        + - * / 
        INT INT = INT
        
        < <= > >= 
        INT INT = BOOLEAN
        
        = !=
        INT INT = BOOLEAN 
        BOOLEAN BOOLEAN = BOOLEAN
        
        e ou 
        BOOLEAN BOOLEAN = BOOLEAN
         */
        String tipo = "";
        auxPosFixa.clear();
        auxPosFixa = posFixa;
        int i = 0;

        while (auxPosFixa.size() != 1) {
            if (auxPosFixa.get(i) instanceof ElementoOperador) {
                switch (auxPosFixa.get(i).getNome()) {
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        if (((ElementoOperando) auxPosFixa.get(i - 1)).getTipo().equals("inteiro") && ((ElementoOperando) auxPosFixa.get(i - 2)).getTipo().equals("inteiro")) {
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.add(i-2, new ElementoOperando("AUX", "inteiro"));
                        } else {
                            //erro
                        }
                        break;

                    case "<":
                    case "<=":
                    case ">":
                    case ">=":
                        if (((ElementoOperando) auxPosFixa.get(i - 1)).getTipo().equals("inteiro") && ((ElementoOperando) auxPosFixa.get(i - 2)).getTipo().equals("inteiro")) {
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.add(i-2, new ElementoOperando("AUX", "booleano"));
                        } else {
                            //erro
                        }
                        break;

                    case "=":
                    case "!=":
                        if (((ElementoOperando) auxPosFixa.get(i - 1)).getTipo().equals("inteiro") && ((ElementoOperando) auxPosFixa.get(i - 2)).getTipo().equals("inteiro")) {
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.add(i-2, new ElementoOperando("AUX", "booleano"));
                        } else if (((ElementoOperando) auxPosFixa.get(i - 1)).getTipo().equals("booleano") && ((ElementoOperando) auxPosFixa.get(i - 2)).getTipo().equals("booleano")) {
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.add(i-2, new ElementoOperando("AUX", "booleano"));
                            
                        } else {
                            //erro
                        }
                        break;

                    case "e":
                    case "ou":
                        if (((ElementoOperando) auxPosFixa.get(i - 1)).getTipo().equals("booleano") && ((ElementoOperando) auxPosFixa.get(i - 2)).getTipo().equals("booleano")) {
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.remove(i-2);
                            auxPosFixa.add(i-2, new ElementoOperando("AUX", "booleano"));
                        } else {
                            //erro
                        }
                        break;
                }
                i=0;
            } else {
                i++;
            }
        }

        return ((ElementoOperando)auxPosFixa.get(0)).getTipo();
    }
}
