/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;

/**
 *
 * @author math_
 */
public class Sintatico {
    private Lexico lexico;
    private Token token;
    
    Sintatico(Lexico lexico) throws IOException {
        this.lexico = lexico;
        analisaInicio();
    }

    private void analisaInicio() {
        //pega token
        if(token.getSimbolo().equals("sprograma")){
            //pega token
            if(token.getSimbolo().equals(("sidentificador"))){
                //insere na tabela de simbolos
               //pega token
               if(token.getSimbolo().equals("sponto_virgula")){
                   analisaBloco();
                   if(token.getSimbolo().equals("sponto")){
                       //acabou e sucesso
                   } else {
                       //mostra erros
                   }
               } else{
                   //mostra erros
               }
            } else {
                // mostra erros
            }
        } else {
            // mostra erros
        }
    }

    private void analisaBloco() {
        //pega token
        analisaEtVariaveis();
        analisaSubRotinas();
        analisaComandos();
    }

    private void analisaEtVariaveis() {
        if(token.getSimbolo().equals("svar")){
            //pega token
            if(token.getSimbolo().equals("sidentificador")){
                while(token.getSimbolo().equals("sidentificador")){
                    analisaVariaveis();
                    if(token.getSimbolo().equals("sponto_virgula")){
                        //pega token
                    } else {
                        //erro
                    }
                }
            } else {
                // erro
            }
        }
    }

    private void analisaSubRotinas() {
        //doidera
        int flag = 1;
        if(token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sFuncao")){
            //fita de rotulo
        }
        while(token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sFuncao")){
            if(token.getSimbolo().equals("sprocedimento")){
                analisaDeclaracaoProcedimento();
            } else{
                analisaDeclaracaoFuncao();
            }
            if(token.getSimbolo().equals("sponto_virgula")){
                //pega token
            } else{
                //erro
            }
        }
        if(flag == 1){
            //doidera
        }
    }

    private void analisaComandos() {
        if(token.getSimbolo().equals("sinicio")){
            //pega token
            analisaComandoSimples();
            while(!token.getSimbolo().equals("sfim")){
                if(token.getSimbolo().equals("sponto_virgula")){
                    //pega token
                    if(!token.getSimbolo().equals("sfim")){
                        analisaComandoSimples();
                    }
                } else{
                    //erro
                }
            }
            //pega token
        } else{
            //erro
        }
    }

    private void analisaVariaveis() {
        do{
            if(token.getSimbolo().equals("sidentificador")){
                // insere lexema na tabela de simbolos se ja nao houver, se houver ERRO
                //pega token 
                if(token.getSimbolo().equals("svirgula") || token.getSimbolo().equals("sdoispontos")){
                    if(token.getSimbolo().equals("svirgula")){
                        //pega token
                        if(token.getSimbolo().equals("sdoispontos")){
                            // erro
                        }
                    }
                } else{
                    //erro
                }
            } else{
                //erro
            }
        }while(!token.getSimbolo().equals("sdoispontos"));
        //pega token
        analisaTipo();
    }

    private void analisaTipo() {
        if(!token.getSimbolo().equals("sinteiro") && !token.getSimbolo().equals("sbooleano")){
            //erro
        } else{
            // coloca token.lexema na tabela como tipo
            //pega token
        }
    }

    private void analisaComandoSimples() {
        if(token.getSimbolo().equals("sidentificador")){
            analisaAtribChProcedimento();
        } else if(token.getSimbolo().equals("sSe")){
            analisaSe();
        } else if(token.getSimbolo().equals("sEnquanto")){
            analisaEnquanto();
        } else if(token.getSimbolo().equals("sLeia")){
            analisaLeia();
        } else if(token.getSimbolo().equals("sEscreva")){
            analisaEscreva();
        } else{
            analisaComandos();
        }
    }

    private void analisaAtribChProcedimento() {
        //pega token
        if(token.getSimbolo().equals("sAtribuicao")){
            analisaAtribuicao();
        }
        else{
            analisaChProcedimento();
        }
    }

    private void analisaSe() {
        //pega token
        analisaExpressao();
        if(token.getSimbolo().equals("sEntao")){
            //pega token
            analisaComandoSimples();
            if(token.getSimbolo().equals("sSenao")){
                //pega token
                analisaComandoSimples();
            }
        } else{
            //erro
        }
    }

    private void analisaEnquanto() {
        //negocio de rotulo doidera
        //pega token
        analisaExpressao();
        if(token.getSimbolo().equals("sFaca")){
            // negocio de rotulo de novo
            //pega token
            analisaComandoSimples();
            // rotulo de novo gri
        } else{
            //erro
        }
    }

    private void analisaLeia() {
        //pega token
        if(token.getSimbolo().equals("sAbreParenteses")){
            //pega token
            if(token.getSimbolo().equals("sidentificador")){
                // pesquisa declaracao na tabela com token.lexema
                //pega token
                if(token.getSimbolo().equals("sFechaParenteses")){
                    //pega token
                } else{
                    //erro
                }
            } else{
                //erro
            }
        } else{
            //erro
        }
    }

    private void analisaEscreva() {
        //pega token
        if(token.getSimbolo().equals("sAbreParenteses")){
            //pega token
            if(token.getSimbolo().equals("sidentificador")){
                // pesquisa declaracao na tabela com o token.lexema
                //pega token
                if(token.getSimbolo().equals("sFechaParenteses")){
                    //pega token
                } else{
                    //erro
                }
            } else{
                //erro
            }
        } else{
            //erro
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
        if(token.getSimbolo().equals("sMaior") || token.getSimbolo().equals("sMaiorIgual") || token.getSimbolo().equals("sIgual") || token.getSimbolo().equals("sMenor") || token.getSimbolo().equals("sMenorIgual") || token.getSimbolo().equals("sDiferente")){
            //pega token
            analisaExpressaoSimples();
        }
    }

    private void analisaDeclaracaoProcedimento() {
        //pega token
        //doidera
        if(token.getSimbolo().equals("sidentificador")){
            //pesquisa proc na tabela 
            //insere na tabela
            //doidera
            //pega token
            if(token.getSimbolo().equals("sponto_virgula")){
                analisaBloco();
            } else{
                //erro
            }
        } else{
            //erro
        }
    }

    private void analisaDeclaracaoFuncao() {
        //pega token
        // doidera
        if(token.getSimbolo().equals("sidentificador")){
            // doidera
            //insere token na tabela de simbolos das func
            // pega token
            if(token.getSimbolo().equals("sdoispontos")){
                //pega token
                if(token.getSimbolo().equals("sinteiro") || token.getSimbolo().equals("sbooleano")){
                    //doidera
                    //pega token
                    if(token.getSimbolo().equals("sponto_virgula")){
                        analisaBloco();
                    }
                } else{
                    //eroo
                }
            } else{
                //erro
            }
        } else{
            //erro
        }
    }

    private void analisaExpressaoSimples() {
        if(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos")){
            //pega token
            analisaTermo();
        }
        while(token.getSimbolo().equals("smenos") || token.getSimbolo().equals("smais") || token.getSimbolo().equals("sou")){
            //pega token
            analisaTermo();
        }
    }

    private void analisaTermo() {
        analisaFator();
        while(token.getSimbolo().equals("smult") || token.getSimbolo().equals("sdiv") || token.getSimbolo().equals("se")){
            //pega token
            analisaFator();
        }
    }

    private void analisaFator() {
        if(token.getSimbolo().equals("sidentificador")){
            //doidera
            
            
        } else if(token.getSimbolo().equals("snumero")){
                //pega token
        
            } else if (token.getSimbolo().equals("snao")){
                //pega token
                analisaFator();
            } else if(token.getSimbolo().equals("sabre_parenteses")){
                //pega token
                analisaExpressao();
                if(token.getSimbolo().equals("sfecha_parenteses")){
                    //pega token
                } else{
                    //erro
                }
            } else if(token.getLexema().equals("verdadeiro") || token.getLexema().equals("falso")){
            //pega token
            } else {
            //erro
            }
    }
}
