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
import java.sql.Timestamp;

public class Sintatico {

    private Lexico lexico;
    private int memoriaIndex = 0, rotulo = 0;
    private PosFixa posfixa = new PosFixa();
    private Token token;
    private Gerenciador INSTANCE = Gerenciador.getInstance();
    private JTextArea jTextAreaErro, jTextAreaPrograma;
    private ArrayList<Elemento> filaInFixa = new ArrayList<Elemento>();
    private GeradorDeCodigo geraCodigo = new GeradorDeCodigo();
    private int auxRetorno = 0;

    private boolean erro = false;

    Sintatico(String codigo, JTextArea jTextAreaErro, JTextArea jTextAreaPrograma) {
        INSTANCE.resetaSimbolos();
        this.jTextAreaErro = jTextAreaErro;
        this.jTextAreaPrograma = jTextAreaPrograma;
        jTextAreaErro.setForeground(new java.awt.Color(204, 0, 0));
        try {
            lexico = new Lexico(codigo, jTextAreaErro);
            //INSTANCE.printaTokens();
            //try {
            analisaInicio();
            //} catch (IndexOutOfBoundsException c) {
            //printaErro(c.toString());

            //}
            //INSTANCE.printaSimbolos();
        } catch (IOException ex) {
            Logger.getLogger(Sintatico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void analisaInicio() {//ok gera codigo
        //pega token
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sprograma")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals(("sidentificador"))) {
                //insere na tabela de simbolos
                SimboloProcProg newSimbolo = new SimboloProcProg(token.getLexema(), rotulo);
                rotulo++;
                INSTANCE.addSimbolo(newSimbolo);

                //pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sponto_virgula")) {
                    geraCodigo.geraSTART();
                    analisaBloco();
                    if (token.getSimbolo().equals("sponto")) {
                        jTextAreaErro.setForeground(new java.awt.Color(0, 204, 0));
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        jTextAreaErro.setText("Completo em " + timestamp + " !");
                        geraCodigo.geraHLT();
                        try {
                            geraCodigo.printaCodigo();
                        } catch (IOException ex) {
                            Logger.getLogger(Sintatico.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
        int numVariaveisDalloc = INSTANCE.removeFuncProcSimbolos();

        if (numVariaveisDalloc != 0) {
            if (INSTANCE.getSimbolos().get(INSTANCE.getSimbolos().size() - 1) instanceof SimboloFuncao) {

                memoriaIndex = memoriaIndex - numVariaveisDalloc;
            } else {
                geraCodigo.geraDALLOC(memoriaIndex - numVariaveisDalloc, numVariaveisDalloc);
                memoriaIndex = memoriaIndex - numVariaveisDalloc;
            }
        } else {
            if (INSTANCE.getSimbolos().get(INSTANCE.getSimbolos().size() - 1) instanceof SimboloFuncao) {

            }
        }

    }

    private void analisaEtVariaveis() { //ok gera codigo
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
        int flag = 0, auxRot = 0;
        if (token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")) {
            //fita de rotulo
            auxRot = rotulo;
            geraCodigo.geraJMP(rotulo);
            rotulo++;
            flag = 1;
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
            geraCodigo.geraNULL(auxRot);
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
        int count = 0;
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
                    newSimbolo.setMemoria(memoriaIndex);
                    count++;
                    memoriaIndex++;
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
        geraCodigo.geraALLOC(memoriaIndex - count, count);
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
                        ((SimboloVariavel) simbolo).setTipo(token.getSimbolo());
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
        boolean isFuncao = false;
        Token tokenAnterior = token;
        tokenAnterior = new Token(token);
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("satribuicao")) {     //se for atrb, tenho que ver se o elemento anterior foi declarado na tabela de simbolos
            boolean existe = false;
            Simbolo auxSimb = null;
            ArrayList<Simbolo> simb = INSTANCE.getSimbolos();
            for (int i = simb.size() - 1; i >= 0; i--) {

                if (simb.get(i).getLexema().equals(tokenAnterior.getLexema())) {
                    existe = true;
                    if (simb.get(i) instanceof SimboloFuncao) {
                        isFuncao = true;
                        auxSimb = (SimboloFuncao) simb.get(i);
                    } else {
                        auxSimb = (SimboloVariavel) simb.get(i);
                    }

                    break;
                }

            }
            if (existe) {
                String tipo = analisaAtribuicao();
                if (isFuncao) {
                    auxRetorno++;
                    if (tipo.equals(((SimboloFuncao) auxSimb).getTipo())) {
                        //gera o codigo da atribuicao
                        int count = 0;
                        for (int i = INSTANCE.getSimbolos().size() - 1; i >= 0; i--) {
                            if (INSTANCE.getSimbolos().get(i) instanceof SimboloVariavel) {
                                count++;
                            }
                            if (INSTANCE.getSimbolos().get(i) instanceof SimboloFuncao) {
                                break;
                            }
                        }
                        if (count == 0) {
                            geraCodigo.geraRETURNF(0, 0);
                        } else {
                            geraCodigo.geraRETURNF(memoriaIndex - count, count);
                        }

                    } else {
                        printaErro("Tipos incompativeis");
                    }
                } else {

                    if (tipo.equals(((SimboloVariavel) auxSimb).getTipo())) {
                        //gera o codigo da atribuicao
                        geraCodigo.geraSTR(((SimboloVariavel) auxSimb).getMemoria());
                    } else {
                        printaErro("Tipos incompativeis");
                    }
                }

            } else {
                printaErro("Var n declarada");
            }
        } else {
            analisaChProcedimento(tokenAnterior);
        }

    }

    private void analisaSe() {
        //pega token
        int rotuloAux = rotulo;
        token = INSTANCE.getToken();
        analisaExpressao();
        posfixa.geraPosFixa(filaInFixa);
        printaInFixa();
        geraCodigo.geraPOSFIXA(posfixa.getPosFixa());
        String tipo = posfixa.getTipoPosfixa();
        geraCodigo.geraJMPF(rotuloAux);
        rotulo++;
        if (!tipo.equals("sbooleano")) {
            //erro de tipos de operandos
            printaErro("Esperada uma expressao booleana");
        } else {
            if (token.getSimbolo().equals("sentao")) {
                //pega token
                token = INSTANCE.getToken();
                analisaComandoSimples();
                if (token.getSimbolo().equals("ssenao")) {
                    //pega token
                    //jmp pro fim do senao
                    geraCodigo.geraJMP(rotulo);
                    int aux = rotulo;
                    rotulo++;
                    geraCodigo.geraNULL(rotuloAux);
                    token = INSTANCE.getToken();
                    analisaComandoSimples();
                    //null com o fim do senao
                    geraCodigo.geraNULL(aux);
                } else {
                    geraCodigo.geraNULL(rotuloAux);
                }
            } else {
                //erro
                printaErro("entao");
            }
        }
    }

    private void analisaEnquanto() {
        //negocio de rotulo doidera
        //pega token
        int rotuloInicio = rotulo, rotuloFim;
        token = INSTANCE.getToken();
        analisaExpressao();
        posfixa.geraPosFixa(filaInFixa);
        printaInFixa();
        geraCodigo.geraNULL(rotulo);
        rotulo++;
        geraCodigo.geraPOSFIXA(posfixa.getPosFixa());
        String tipo = posfixa.getTipoPosfixa();
        rotuloFim = rotulo;
        rotulo++;
        geraCodigo.geraJMPF(rotuloFim);

        if (!tipo.equals("sbooleano")) {
            //erro de tipos de operandos
            printaErro("Esperada uma expressao booleana");
        } else {

            if (token.getSimbolo().equals("sfaca")) {
                // negocio de rotulo de novo
                //pega token
                token = INSTANCE.getToken();
                analisaComandoSimples();
                geraCodigo.geraJMP(rotuloInicio);
                geraCodigo.geraNULL(rotuloFim);
                // rotulo de novo gri
            } else {
                //erro
                printaErro("faca");
            }
        }
    }

    private void analisaLeia() {
        boolean varExiste = false;
        SimboloVariavel simb = null;
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
                        simb = (SimboloVariavel) INSTANCE.getSimbolos().get(i - 1);
                        break;
                    }
                }
                if (varExiste) {
                    //pega token
                    token = INSTANCE.getToken();
                    if (token.getSimbolo().equals("sfecha_parenteses")) {
                        //pega token
                        token = INSTANCE.getToken();
                        geraCodigo.geraRD();
                        geraCodigo.geraSTR(simb.getMemoria());
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
        boolean isFuncao = false;
        Simbolo simb = null;

        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sabre_parenteses")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sidentificador")) {
                // pesquisa declaracao na tabela com o token.lexema

                for (int i = INSTANCE.getSimbolos().size(); i > 0; i--) {
                    if (INSTANCE.getSimbolos().get(i - 1).getLexema().equals(token.getLexema())) {
                        varExiste = true;
                        if (INSTANCE.getSimbolos().get(i - 1) instanceof SimboloFuncao) {
                            isFuncao = true;
                            simb = (SimboloFuncao) INSTANCE.getSimbolos().get(i - 1);
                        } else {
                            simb = (SimboloVariavel) INSTANCE.getSimbolos().get(i - 1);
                        }
                        break;
                    }
                }
                if (varExiste) {
                    //pega token
                    token = INSTANCE.getToken();
                    if (token.getSimbolo().equals("sfecha_parenteses")) {
                        //pega token
                        if (isFuncao) {
                            token = INSTANCE.getToken();
                            geraCodigo.geraCALL(((SimboloFuncao) simb).getRotulo());
                            geraCodigo.geraPRN();
                        } else {
                            token = INSTANCE.getToken();
                            geraCodigo.geraLDV(((SimboloVariavel)simb).getMemoria());
                            geraCodigo.geraPRN();
                        }
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

    private String analisaAtribuicao() {
        token = INSTANCE.getToken();
        analisaExpressao();
        posfixa.geraPosFixa(filaInFixa);
        printaInFixa();
        geraCodigo.geraPOSFIXA(posfixa.getPosFixa());
        String tipo = posfixa.getTipoPosfixa();

        if (tipo.equals("ERRO")) {
            //erro de tipos de operandos
            printaErro("TIPO VARIAVEL");
        }
        return tipo;
    }

    private void analisaChProcedimento(Token tk) {
        boolean existe = false;
        SimboloProcProg aux = null;
        for (Simbolo e : INSTANCE.getSimbolos()) {
            if (e.getLexema().equals(tk.getLexema())) {
                existe = true;
                aux = (SimboloProcProg) e;
                break;
            }
        }
        if (existe) {
            geraCodigo.geraCALL(aux.getRotulo());
        } else {
            printaErro("Chamada nao existe");
        }

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

                SimboloProcProg newSimbolo = new SimboloProcProg(token.getLexema(), rotulo);
                geraCodigo.geraNULL(rotulo);
                rotulo++;

                INSTANCE.addSimbolo(newSimbolo);

                //pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sponto_virgula")) {
                    analisaBloco();
                    geraCodigo.geraRETURN();
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
                auxRetorno = 0;
                SimboloFuncao newSimbolo = new SimboloFuncao(token.getLexema(), rotulo);
                geraCodigo.geraNULL(rotulo);
                rotulo++;

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
                            if (auxRetorno == 0){
                                printaErro("função retorno");
                            }
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
                if (simbaux instanceof SimboloVariavel) {
                    filaInFixa.add(new ElementoOperando(token.getLexema(), ((SimboloVariavel) simbaux).getTipo(), ((SimboloVariavel) simbaux).getMemoria()));
                    token = INSTANCE.getToken();
                } else if (simbaux instanceof SimboloFuncao) {//se inteiro ou booleano
                    filaInFixa.add(new ElementoOperando(token.getLexema(), ((SimboloFuncao) simbaux).getTipo(), ((SimboloFuncao) simbaux).getRotulo()));
                    analisaChamadaFuncao();

                } else {
                    printaErro("erro de fator");
                }

            } else {
                printaErro("identificador nao declarado");
            }

        } else if (token.getSimbolo().equals("snumero")) {
            //pega token
            filaInFixa.add(new ElementoOperando(token.getLexema(), "sinteiro", 0));
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
        } else if (token.getLexema().equals("verdadeiro")) {
            //pega token
            filaInFixa.add(new ElementoOperando("1", "sbooleano", 0));
            token = INSTANCE.getToken();
        } else if(token.getLexema().equals("falso")){
            filaInFixa.add(new ElementoOperando("0", "sbooleano", 0));
            token = INSTANCE.getToken();
        } 
        else {
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
        /*System.out.println("IN FIXA:");
        for (Elemento e : filaInFixa) {
            System.out.print(" " + e.getNome());
        }
        System.out.println("");
        System.out.println("-------------------");*/
        filaInFixa.clear();
    }
}
