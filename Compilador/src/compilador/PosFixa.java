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
        for (Elemento auxElemento : this.elemento) {
            if (auxPosFixa.isEmpty() && !posFixa.isEmpty()) {
                if (auxElemento.getNome().equals("+U") || auxElemento.getNome().equals("-U") || auxElemento.getNome().equals("nao")) {
                    switch (auxElemento.getNome()) {
                        case "+U":
                            break;
                        case "-U":
                            ElementoOperador auxUnitario = new ElementoOperador("-U");
                            posFixa.add(auxUnitario);
                            break;
                        case "nao":
                            ElementoOperador auxUnitarioNot = new ElementoOperador("nao");
                            auxPosFixa.add(auxUnitarioNot);
                            break;
                    }
                } else if (auxElemento instanceof ElementoOperando) {
                    posFixa.add(auxElemento);
                } else {
                    auxPosFixa.add(auxElemento);
                }

            } else {
                if (auxElemento.getNome().equals("+U") || auxElemento.getNome().equals("-U") || auxElemento.getNome().equals("nao")) {
                    switch (auxElemento.getNome()) {
                        case "+U":
                            break;
                        case "-U":
                            ElementoOperador auxUnitario = new ElementoOperador("-U");
                            posFixa.add(auxUnitario);
                            break;
                        case "nao":
                            ElementoOperador auxUnitarioNot = new ElementoOperador("nao");
                            auxPosFixa.add(auxUnitarioNot);
                            break;
                    }
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
                    if (!auxPosFixa.get(i).getNome().equals("(")) {
                        posFixa.add(auxPosFixa.get(i));
                        auxPosFixa.remove(i);
                    }
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
        for (int j = auxPosFixa.size() - 1; j >= 0; j--) {
            posFixa.add(auxPosFixa.get(j));
        }

        /*System.out.println("POS FIXA:");
        for (Elemento e : posFixa) {
            System.out.print(" " + e.getNome() );
        }
        System.out.println("");
        System.out.println("-------------------");*/
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
        ArrayList<Elemento> auxPosFixa2 = posFixa;
        int i = 0;

        while (auxPosFixa2.size() != 1) {
            if (auxPosFixa2.get(i) instanceof ElementoOperador) {
                switch (auxPosFixa2.get(i).getNome()) {
                    // case not??
                    case "+U":
                    case "-U":
                    case "nao":
                        auxPosFixa2.remove(i);
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "div":
                        if (((ElementoOperando) auxPosFixa2.get(i - 1)).getTipo().equals("sinteiro") && ((ElementoOperando) auxPosFixa2.get(i - 2)).getTipo().equals("sinteiro")) {
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.add(i - 2, new ElementoOperando("AUX", "sinteiro", 0));
                        } else {
                            return "ERRO";
                        }
                        break;

                    case "<":
                    case "<=":
                    case ">":
                    case ">=":
                        if (((ElementoOperando) auxPosFixa2.get(i - 1)).getTipo().equals("sinteiro") && ((ElementoOperando) auxPosFixa2.get(i - 2)).getTipo().equals("sinteiro")) {
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.add(i - 2, new ElementoOperando("AUX", "sbooleano", 0));
                        } else {
                            return "ERRO";
                        }
                        break;

                    case "=":
                    case "!=":
                        if (((ElementoOperando) auxPosFixa2.get(i - 1)).getTipo().equals("sinteiro") && ((ElementoOperando) auxPosFixa2.get(i - 2)).getTipo().equals("sinteiro")) {
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.add(i - 2, new ElementoOperando("AUX", "sbooleano", 0));
                        } else if (((ElementoOperando) auxPosFixa2.get(i - 1)).getTipo().equals("sbooleano") && ((ElementoOperando) auxPosFixa2.get(i - 2)).getTipo().equals("sbooleano")) {
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.add(i - 2, new ElementoOperando("AUX", "sbooleano", 0));

                        } else {
                            return "ERRO";
                        }
                        break;

                    case "e":
                    case "ou":
                        if (((ElementoOperando) auxPosFixa2.get(i - 1)).getTipo().equals("sbooleano") && ((ElementoOperando) auxPosFixa2.get(i - 2)).getTipo().equals("sbooleano")) {
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.remove(i - 2);
                            auxPosFixa2.add(i - 2, new ElementoOperando("AUX", "sbooleano", 0));
                        } else {
                            return "ERRO";
                        }
                        break;
                }
                i = 0;
            } else {

                i++;
            }
        }

        return ((ElementoOperando) auxPosFixa2.get(0)).getTipo();
    }

    public ArrayList<Elemento> getPosFixa() {
        return posFixa;
    }
}
