package compilador;

import java.io.IOException;

public class Sintatico {

    private Lexico lexico;
    private Token token;
    private Gerenciador INSTANCE = Gerenciador.getInstance();

    Sintatico(Lexico lexico) throws IOException {
        this.lexico = lexico;
        analisaInicio();
    }

    private void analisaInicio() {
        //pega token
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sprograma")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals(("sidentificador"))) {
                //insere na tabela de simbolos
                //pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sponto_virgula")) {
                    analisaBloco();
                    if (token.getSimbolo().equals("sponto")) {
                        //acabou e sucesso
                    } else {
                        //mostra erros
                        if (token.getSimbolo().equals("serro")) {
                            //erro lexico
                        }
                    }
                } else {
                    //mostra erros
                    if (token.getSimbolo().equals("serro")) {
                        //erro lexico
                    }
                }
            } else {
                // mostra erros
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
            }
        } else {
            // mostra erros
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }

    private void analisaBloco() {
        //pega token
        token = INSTANCE.getToken();
        analisaEtVariaveis();
        analisaSubRotinas();
        analisaComandos();
    }

    private void analisaEtVariaveis() {
        if (token.getSimbolo().equals("svar")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sidentificador")) {
                while (token.getSimbolo().equals("sidentificador")) {
                    analisaVariaveis();
                    if (token.getSimbolo().equals("sponto_virgula")) {
                        //pega token
                        token = INSTANCE.getToken();
                    } else {
                        //erro
                        if (token.getSimbolo().equals("serro")) {
                            //erro lexico
                        }
                    }
                }
            } else {
                // erro
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
            }
        }
    }

    private void analisaSubRotinas() {
        //doidera
        int flag = 1;
        if (token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sFuncao")) {
            //fita de rotulo
        }
        while (token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sFuncao")) {
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
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
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
            while (!token.getSimbolo().equals("sfim")) {
                if (token.getSimbolo().equals("sponto_virgula")) {
                    //pega token
                    token = INSTANCE.getToken();
                    if (!token.getSimbolo().equals("sfim")) {
                        analisaComandoSimples();
                    }
                } else {
                    //erro
                    if (token.getSimbolo().equals("serro")) {
                        //erro lexico
                    }
                }
            }
            //pega token
            token = INSTANCE.getToken();
        } else {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }

    private void analisaVariaveis() {
        do {
            if (token.getSimbolo().equals("sidentificador")) {
                // insere lexema na tabela de simbolos se ja nao houver, se houver ERRO
                //pega token 
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("svirgula") || token.getSimbolo().equals("sdoispontos")) {
                    if (token.getSimbolo().equals("svirgula")) {
                        //pega token
                        token = INSTANCE.getToken();
                        if (token.getSimbolo().equals("sdoispontos")) {
                            // erro
                            if (token.getSimbolo().equals("serro")) {
                                //erro lexico
                            }
                        }
                    }
                } else {
                    //erro
                    if (token.getSimbolo().equals("serro")) {
                        //erro lexico
                    }
                }
            } else {
                //erro
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
            }
        } while (!token.getSimbolo().equals("sdoispontos"));
        //pega token
        token = INSTANCE.getToken();
        analisaTipo();
    }

    private void analisaTipo() {
        if (!token.getSimbolo().equals("sinteiro") && !token.getSimbolo().equals("sbooleano")) {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        } else {
            // coloca token.lexema na tabela como tipo
            //pega token
            token = INSTANCE.getToken();
        }
    }

    private void analisaComandoSimples() {
        if (token.getSimbolo().equals("sidentificador")) {
            analisaAtribChProcedimento();
        } else if (token.getSimbolo().equals("sSe")) {
            analisaSe();
        } else if (token.getSimbolo().equals("sEnquanto")) {
            analisaEnquanto();
        } else if (token.getSimbolo().equals("sLeia")) {
            analisaLeia();
        } else if (token.getSimbolo().equals("sEscreva")) {
            analisaEscreva();
        } else {
            analisaComandos();
        }
    }

    private void analisaAtribChProcedimento() {
        //pega token
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sAtribuicao")) {
            analisaAtribuicao();
        } else {
            analisaChProcedimento();
        }
    }

    private void analisaSe() {
        //pega token
        token = INSTANCE.getToken();
        analisaExpressao();
        if (token.getSimbolo().equals("sEntao")) {
            //pega token
            token = INSTANCE.getToken();
            analisaComandoSimples();
            if (token.getSimbolo().equals("sSenao")) {
                //pega token
                token = INSTANCE.getToken();
                analisaComandoSimples();
            }
        } else {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }

    private void analisaEnquanto() {
        //negocio de rotulo doidera
        //pega token
        token = INSTANCE.getToken();
        analisaExpressao();
        if (token.getSimbolo().equals("sFaca")) {
            // negocio de rotulo de novo
            //pega token
            token = INSTANCE.getToken();
            analisaComandoSimples();
            // rotulo de novo gri
        } else {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }

    private void analisaLeia() {
        //pega token
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sAbreParenteses")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sidentificador")) {
                // pesquisa declaracao na tabela com token.lexema
                //pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sFechaParenteses")) {
                    //pega token
                    token = INSTANCE.getToken();
                } else {
                    //erro
                    if (token.getSimbolo().equals("serro")) {
                        //erro lexico
                    }
                }
            } else {
                //erro
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
            }
        } else {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }

    private void analisaEscreva() {
        //pega token
        token = INSTANCE.getToken();
        if (token.getSimbolo().equals("sAbreParenteses")) {
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sidentificador")) {
                // pesquisa declaracao na tabela com o token.lexema
                //pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sFechaParenteses")) {
                    //pega token
                    token = INSTANCE.getToken();
                } else {
                    //erro
                    if (token.getSimbolo().equals("serro")) {
                        //erro lexico
                    }
                }
            } else {
                //erro
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
            }
        } else {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }

    private void analisaAtribuicao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void analisaChProcedimento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void analisaExpressao() {
        analisaExpressaoSimples();
        if (token.getSimbolo().equals("sMaior") || token.getSimbolo().equals("sMaiorIgual") || token.getSimbolo().equals("sIgual") || token.getSimbolo().equals("sMenor") || token.getSimbolo().equals("sMenorIgual") || token.getSimbolo().equals("sDiferente")) {
            //pega token
            token = INSTANCE.getToken();
            analisaExpressaoSimples();
        }
    }

    private void analisaDeclaracaoProcedimento() {
        //pega token
        token = INSTANCE.getToken();
        //doidera
        if (token.getSimbolo().equals("sidentificador")) {
            //pesquisa proc na tabela 
            //insere na tabela
            //doidera
            //pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sponto_virgula")) {
                analisaBloco();
            } else {
                //erro
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
            }
        } else {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }

    private void analisaDeclaracaoFuncao() {
        //pega token
        token = INSTANCE.getToken();
        // doidera
        if (token.getSimbolo().equals("sidentificador")) {
            // doidera
            //insere token na tabela de simbolos das func
            // pega token
            token = INSTANCE.getToken();
            if (token.getSimbolo().equals("sdoispontos")) {
                //pega token
                token = INSTANCE.getToken();
                if (token.getSimbolo().equals("sinteiro") || token.getSimbolo().equals("sbooleano")) {
                    //doidera
                    //pega token
                    token = INSTANCE.getToken();
                    if (token.getSimbolo().equals("sponto_virgula")) {
                        analisaBloco();
                    }
                } else {
                    //eroo
                    if (token.getSimbolo().equals("serro")) {
                        //erro lexico
                    }
                }
            } else {
                //erro
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
            }
        } else {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }

    private void analisaExpressaoSimples() {
        if (token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos")) {
            //pega token
            token = INSTANCE.getToken();
            analisaTermo();
        }
        while (token.getSimbolo().equals("smenos") || token.getSimbolo().equals("smais") || token.getSimbolo().equals("sou")) {
            //pega token
            token = INSTANCE.getToken();
            analisaTermo();
        }
    }

    private void analisaTermo() {
        analisaFator();
        while (token.getSimbolo().equals("smult") || token.getSimbolo().equals("sdiv") || token.getSimbolo().equals("se")) {
            //pega token
            token = INSTANCE.getToken();
            analisaFator();
        }
    }

    private void analisaFator() {
        if (token.getSimbolo().equals("sidentificador")) {
            //doidera

        } else if (token.getSimbolo().equals("snumero")) {
            //pega token
            token = INSTANCE.getToken();

        } else if (token.getSimbolo().equals("snao")) {
            //pega token
            token = INSTANCE.getToken();
            analisaFator();
        } else if (token.getSimbolo().equals("sabre_parenteses")) {
            //pega token
            token = INSTANCE.getToken();
            analisaExpressao();
            if (token.getSimbolo().equals("sfecha_parenteses")) {
                //pega token
                token = INSTANCE.getToken();
            } else {
                //erro
                if (token.getSimbolo().equals("serro")) {
                    //erro lexico
                }
            }
        } else if (token.getLexema().equals("verdadeiro") || token.getLexema().equals("falso")) {
            //pega token
            token = INSTANCE.getToken();
        } else {
            //erro
            if (token.getSimbolo().equals("serro")) {
                //erro lexico
            }
        }
    }
}
