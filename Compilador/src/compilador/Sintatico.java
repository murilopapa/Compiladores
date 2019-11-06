package compilador;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class Sintatico {

    private Lexico lexico;
    private Token token;
    private Gerenciador INSTANCE = Gerenciador.getInstance();
    private JTextArea jTextAreaErro, jTextAreaPrograma;
    private ArrayList<ElementoOperador> filaInFixa = new ArrayList<ElementoOperador>();
    private boolean erro = false;

    Sintatico(String codigo, JTextArea jTextAreaErro, JTextArea jTextAreaPrograma) {
        INSTANCE.resetaSimbolos();
        this.jTextAreaErro = jTextAreaErro;
        this.jTextAreaPrograma = jTextAreaPrograma;
        jTextAreaErro.setForeground(new java.awt.Color(204, 0, 0));
        try {
            lexico = new Lexico(codigo, jTextAreaErro);
            //INSTANCE.printaTokens();
            try {
                analisaInicio();
            } catch (IndexOutOfBoundsException c) {
                printaErro(c.toString());

            }
            //INSTANCE.printaSimbolos();
        } catch (IOException ex) {
            Logger.getLogger(Sintatico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void analisaInicio() {
        //pega token
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sprograma")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals(("sidentificador"))) {
                //insere na tabela de simbolos
                SimboloProcProg newSimbolo = new SimboloProcProg(token.getLexema());
                INSTANCE.addSimbolo(newSimbolo);

                //pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sponto_virgula")) {
                    analisaBloco();
                    if (token.getSimbolo().equals("sponto")) {
                        jTextAreaErro.setForeground(new java.awt.Color(0, 204, 0));
                        jTextAreaErro.setText("Completo !!");
                    } else {
                        //mostra erros
                        printaErro("' . '");
                    }
                } else {
                    //mostra erros
                    printaErro(" ; ");
                }
            } else {
                // mostra erros
                printaErro("identificador");
            }
        } else {
            // mostra erros
            printaErro("programa");
        }
    }

    private void analisaBloco() {
        //pega token
        token = INSTANCE.getToken();
        analisaEtVariaveis();
        analisaSubRotinas();
        analisaComandos();
        INSTANCE.removeFuncProcSimbolos();
    }

    private void analisaEtVariaveis() {
        if (token.getSimbolo().equals("svar")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sidentificador")) {
                while (token.getSimbolo().equals("sidentificador") && erro == false) {
                    analisaVariaveis();
                    if (token.getSimbolo().equals("sponto_virgula")) {
                        //pega token
                        token = INSTANCE.getToken();
                    } else {
                        //erro
                        printaErro("' ; '");
                    }
                }
            } else {
                // erro
                printaErro("identificador");
            }
        }
    }

    private void analisaSubRotinas() {
        //doidera
        int flag = 1;
        if (token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")) {
            //fita de rotulo
        }
        while ((token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")) && erro == false) {
            if (token.getSimbolo().equals("sprocedimento")) {
                analisaDeclaracaoProcedimento();
            } else {
                analisaDeclaracaoFuncao();
            }
            if (token.getSimbolo().equals("sponto_virgula")) {
                //pega token
                token = INSTANCE.getToken();
            } else {
                //erro
                printaErro("' ; '");
            }
        }
        if (flag == 1) {
            //doidera
        }
    }

    private void analisaComandos() {
        if (token.getSimbolo().equals("sinicio")) {
            //pega token
            token = INSTANCE.getToken();
            analisaComandoSimples();
            while (!token.getSimbolo().equals("sfim") && erro == false) {
                if (token.getSimbolo().equals("sponto_virgula")) {
                    //pega token
                    token = INSTANCE.getToken();
                    if (!token.getSimbolo().equals("sfim")) {
                        analisaComandoSimples();
                    }
                } else {
                    //erro
                    printaErro("' ; '");
                }
            }

            //pega token
            token = INSTANCE.getToken();
        } else {
            //erro
            printaErro("inicio");
        }
    }

    private void analisaVariaveis() {
        boolean varExiste = false;
        do {
            if (token.getSimbolo().equals("sidentificador")) {
                // insere lexema na tabela de simbolos se ja nao houver, se houver ERRO
                SimboloVariavel newSimbolo = new SimboloVariavel(token.getLexema());
                for (int i = INSTANCE.getSimbolos().size(); i > 0; i--) {
                    if (INSTANCE.getSimbolos().get(i - 1) instanceof SimboloFuncao || INSTANCE.getSimbolos().get(i - 1) instanceof SimboloProcProg) {
                        break;
                    }
                    if (INSTANCE.getSimbolos().get(i - 1).getLexema().equals(newSimbolo.getLexema())) {
                        varExiste = true;
                    }
                }
                if (!varExiste) {
                    INSTANCE.addSimbolo(newSimbolo);
                    //pega token 
                    token = INSTANCE.getToken();
                    if (token.getSimbolo().equals("svirgula") || token.getSimbolo().equals("sdoispontos")) {
                        if (token.getSimbolo().equals("svirgula")) {
                            //pega token
                            token = INSTANCE.getToken();
                            if (token.getSimbolo().equals("sdoispontos")) {
                                // erro
                                printaErro("nao ' : '");
                            }
                        }
                    } else {
                        //erro
                        printaErro("' , ' ou ' : '");
                    }
                } else {
                    printaErro("var existe");
                }
            } else {
                //erro
                printaErro("identificador");
            }
        } while (!token.getSimbolo().equals("sdoispontos") && erro == false);
        //pega token
        token = INSTANCE.getToken();
        analisaTipo();
    }

    private void analisaTipo() {
        if (!token.getSimbolo().equals("sinteiro") && !token.getSimbolo().equals("sbooleano")) {
            //erro
            printaErro("inteiro ou booleano");
        } else {
            // coloca token.lexema na tabela como tipo
            ArrayList<Simbolo> simbolos = new ArrayList<Simbolo>();

            for (Simbolo simbolo : INSTANCE.getSimbolos()) {

                if (simbolo instanceof SimboloVariavel) {
                    if (((SimboloVariavel) simbolo).getTipo().contentEquals("")) {
                        ((SimboloVariavel) simbolo).setTipo(token.getLexema());
                    }
                }
                simbolos.add(simbolo);

            }
            INSTANCE.setSimbolos(simbolos);

        }
        //pega token
        token = INSTANCE.getToken();
    }

    private void analisaComandoSimples() {
        if (token.getSimbolo().equals("sidentificador")) {
            analisaAtribChProcedimento();
        } else if (token.getSimbolo().equals("sse")) {
            analisaSe();
        } else if (token.getSimbolo().equals("senquanto")) {
            analisaEnquanto();
        } else if (token.getSimbolo().equals("sleia")) {
            analisaLeia();
        } else if (token.getSimbolo().equals("sescreva")) {
            analisaEscreva();
        } else {
            analisaComandos();
        }
    }

    private void analisaAtribChProcedimento() {
        //pega token
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("satribuicao")) {
            analisaAtribuicao();
        } else {
            analisaChProcedimento();
        }
    }

    private void analisaSe() {
        //pega token
        token = INSTANCE.getToken();
        analisaExpressao();
        printaInFixa();
        if (token.getSimbolo().equals("sentao")) {
            //pega token
            token = INSTANCE.getToken();
            analisaComandoSimples();
            if (token.getSimbolo().equals("ssenao")) {
                //pega token
                token = INSTANCE.getToken();
                analisaComandoSimples();
            }
        } else {
            //erro
            printaErro("entao");
        }
    }

    private void analisaEnquanto() {
        //negocio de rotulo doidera
        //pega token
        token = INSTANCE.getToken();
        analisaExpressao();
        printaInFixa();
        if (token.getSimbolo().equals("sfaca")) {
            // negocio de rotulo de novo
            //pega token
            token = INSTANCE.getToken();
            analisaComandoSimples();
            // rotulo de novo gri
        } else {
            //erro
            printaErro("faca");
        }
    }

    private void analisaLeia() {
        boolean varExiste = false;
        //pega token
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sabre_parenteses")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sidentificador")) {
                // pesquisa declaracao na tabela com token.lexema
                for (int i = INSTANCE.getSimbolos().size(); i > 0; i--) {
                    if (INSTANCE.getSimbolos().get(i - 1).getLexema().equals(token.getLexema())) {
                        varExiste = true;
                        break;
                    }
                }
                if (varExiste) {
                    //pega token
                    token = INSTANCE.getToken();
                    if (token.getSimbolo().equals("sfecha_parenteses")) {
                        //pega token
                        token = INSTANCE.getToken();
                    } else {
                        //erro
                        printaErro("'' ) ");
                    }
                } else {
                    printaErro("var nao declarada");
                }
            } else {
                //erro
                printaErro("identificador");
            }
        } else {
            //erro
            printaErro("' ( '");

        }
    }

    private void analisaEscreva() {
        //pega token
        boolean varExiste = false;
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sabre_parenteses")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sidentificador")) {
                // pesquisa declaracao na tabela com o token.lexema
                for (int i = INSTANCE.getSimbolos().size(); i > 0; i--) {
                    if (INSTANCE.getSimbolos().get(i - 1).getLexema().equals(token.getLexema())) {
                        varExiste = true;
                        break;
                    }
                }
                if (varExiste) {
                    //pega token
                    token = INSTANCE.getToken();
                    if (token.getSimbolo().equals("sfecha_parenteses")) {
                        //pega token
                        token = INSTANCE.getToken();
                    } else {
                        //erro
                        printaErro("' ) '");
                    }
                } else {
                    printaErro("var nao declarada");
                }
            } else {
                //erro
                printaErro("identificador");
            }
        } else {
            //erro
            printaErro("' ( '");
        }
    }

    private void analisaAtribuicao() {
        token = INSTANCE.getToken();
        analisaExpressao();
        printaInFixa();
        //
        //aqui vai a funcao de gerar a pos fixa
    }

    private void analisaChProcedimento() {
        //nada acontece no sintatico, apenas no semantico
    }

    private void analisaExpressao() {
        analisaExpressaoSimples();
        if (token.getSimbolo().equals("smaior") || token.getSimbolo().equals("smaiorig") || token.getSimbolo().equals("sig") || token.getSimbolo().equals("smenor") || token.getSimbolo().equals("smenorig") || token.getSimbolo().equals("sdif")) {
            //pega token
            filaInFixa.add(new ElementoOperador(token.getLexema()));
            token = INSTANCE.getToken();
            analisaExpressaoSimples();
        }
    }

    private void analisaDeclaracaoProcedimento() {
        boolean procExiste = false;
        //pega token
        token = INSTANCE.getToken();
        //doidera
        if (token.getSimbolo().equals("sidentificador")) {
            //pesquisa proc na tabela 
            for (int i = INSTANCE.getSimbolos().size(); i > 0; i--) {
                if (INSTANCE.getSimbolos().get(i - 1).getLexema().equals(token.getLexema())) {
                    procExiste = true;
                    break;
                }
            }
            if (!procExiste) {
                //insere na tabela
                //doidera

                SimboloProcProg newSimbolo = new SimboloProcProg(token.getLexema());
                INSTANCE.addSimbolo(newSimbolo);

                //pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sponto_virgula")) {
                    analisaBloco();
                } else {
                    //erro
                    printaErro("' ; '");
                }
            } else {
                printaErro("proc ja declarado");
            }
        } else {
            //erro
            printaErro("identificador");
        }
    }

    private void analisaDeclaracaoFuncao() {
        boolean procExiste = false;
        //pega token
        token = INSTANCE.getToken();
        // doidera
        if (token.getSimbolo().equals("sidentificador")) {
            // doidera
            for (int i = INSTANCE.getSimbolos().size(); i > 0; i--) {
                if (INSTANCE.getSimbolos().get(i - 1).getLexema().equals(token.getLexema())) {
                    procExiste = true;
                    break;
                }
            }
            if (!procExiste) {
                //insere token na tabela de simbolos das func
                SimboloFuncao newSimbolo = new SimboloFuncao(token.getLexema());

                // pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sdoispontos")) {
                    //pega token
                    token = INSTANCE.getToken();
                    if (token.getSimbolo().equals("sinteiro") || token.getSimbolo().equals("sbooleano")) {
                        //doidera
                        newSimbolo.setTipo(token.getSimbolo());
                        INSTANCE.addSimbolo(newSimbolo);
                        //pega token
                        token = INSTANCE.getToken();
                        if (token.getSimbolo().equals("sponto_virgula")) {
                            analisaBloco();
                        }
                    } else {
                        //eroo
                        printaErro("inteiro ou booleano");
                    }
                } else {
                    //erro
                    printaErro("' : '");
                }
            } else {
                printaErro("func ja declarada");
            }
        } else {
            //erro
            printaErro("identificador");
        }
    }

    private void analisaExpressaoSimples() {
        if (token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos")) {
            //pega token
            if (token.getSimbolo().equals("smais")) {
                filaInFixa.add(new ElementoOperador("+U"));
            } else {
                filaInFixa.add(new ElementoOperador("-U"));
            }
            token = INSTANCE.getToken();

        }
        analisaTermo();
        while ((token.getSimbolo().equals("smenos") || token.getSimbolo().equals("smais") || token.getSimbolo().equals("sou")) && erro == false) {
            //pega token
            filaInFixa.add(new ElementoOperador(token.getLexema()));
            token = INSTANCE.getToken();
            analisaTermo();
        }
    }

    private void analisaTermo() {
        analisaFator();
        while ((token.getSimbolo().equals("smult") || token.getSimbolo().equals("sdiv") || token.getSimbolo().equals("se")) && erro == false) {
            //pega token
            filaInFixa.add(new ElementoOperador(token.getLexema()));
            token = INSTANCE.getToken();
            analisaFator();
        }
    }

    private void analisaChamadaFuncao() {
        token = INSTANCE.getToken();
    }

    private void analisaFator() {
        boolean boolaux = false;
        Simbolo simbaux = null;
        if (token.getSimbolo().equals("sidentificador")) {
            //doidera
            for (int i = INSTANCE.getSimbolos().size(); i > 0; i--) {
                if (INSTANCE.getSimbolos().get(i - 1).getLexema().equals(token.getLexema())) {
                    simbaux = INSTANCE.getSimbolos().get(i - 1);
                    boolaux = true;
                    break;
                }
            }
            if (boolaux) {//if pesquisatabela
                filaInFixa.add(new ElementoOperador(token.getLexema()));
                if (simbaux instanceof SimboloVariavel || simbaux instanceof SimboloFuncao) {//se inteiro ou booleano
                    analisaChamadaFuncao();
                } else {
                    token = INSTANCE.getToken();
                }
            } else {
                //erro semantico
            }

        } else if (token.getSimbolo().equals("snumero")) {
            //pega token
            filaInFixa.add(new ElementoOperador(token.getLexema()));
            token = INSTANCE.getToken();

        } else if (token.getSimbolo().equals("snao")) {
            //pega token
            filaInFixa.add(new ElementoOperador(token.getLexema()));
            token = INSTANCE.getToken();
            analisaFator();
        } else if (token.getSimbolo().equals("sabre_parenteses")) {
            //pega token
            filaInFixa.add(new ElementoOperador(token.getLexema()));
            token = INSTANCE.getToken();
            analisaExpressao();

            if (token.getSimbolo().equals("sfecha_parenteses")) {
                //pega token
                filaInFixa.add(new ElementoOperador(token.getLexema()));
                token = INSTANCE.getToken();
            } else {
                //erro
                printaErro(" ) ");
            }
        } else if (token.getLexema().equals("verdadeiro") || token.getLexema().equals("falso")) {
            //pega token
            filaInFixa.add(new ElementoOperador(token.getLexema()));
            token = INSTANCE.getToken();
        } else {
            //erro
            printaErro("verdadeiro ou falso");
        }
    }

    private void printaErro(String esperado) {
        if (erro == false) {
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
                System.out.println("Erro Léxico linha " + token.getLinha());
                jTextAreaErro.setText("Erro na Léxico na linha " + String.valueOf(token.getLinha()) + "!!");
                erro = true;
            } else {
                System.err.println("Erro Sintatico linha'" + token.getLinha());
                String texterro = "Erro Sintático na linha " + String.valueOf(token.getLinha()) + '\n' + "Recebido: '" + token.getLexema() + "' mas é esperado: '" + esperado + "'";
                jTextAreaErro.setText(texterro);

                erro = true;
            }
            Highlighter.HighlightPainter painter;
            painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
            try {
                jTextAreaPrograma.getHighlighter().addHighlight(token.getIndexStart() - 1, token.getIndexEnd() - 1, painter);
            } catch (BadLocationException ex) {
                Logger.getLogger(Sintatico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void printaInFixa() {
        System.out.println("IN FIXA:");
        for (ElementoOperador e : filaInFixa) {
            System.out.print(" " + e.getNome());
        }
        System.out.println("");
        System.out.println("-------------------");
        filaInFixa.clear();
    }
}
