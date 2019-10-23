package compilador;

import java.io.IOException;

public class Lexico {

    Gerenciador INSTANCE = Gerenciador.getInstance();

    private int linha_atual = 1;
    private int tamanho_string;
    private char caracter;
    private boolean file_not_finished = true;
    private int char_to_read = 0;

    Lexico(String reader, javax.swing.JTextArea jTextAreaErro) throws IOException {
        INSTANCE.resetaTokens();
        tamanho_string = reader.length();
        int caractere_int = reader.charAt(char_to_read);      //pego 1 char
        char_to_read++;
        if (char_to_read == tamanho_string) {
            file_not_finished = false;
        }
        caracter = (char) caractere_int;    //converto pra char

        while (file_not_finished) {     //enquanto a booleana for verdadeira

            switch (caracter) {         //switch case pra ver qual ação devo tomar
                case '{':               //se for comentário
                    boolean stop_loop = false;  //crio uma variavel pra controlar o while
                    do {
                        caractere_int = reader.charAt(char_to_read);      //pego 1 char
                        char_to_read++;
                        if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                            stop_loop = true;            //paro ESSE do while
                            file_not_finished = false;  //paro o while externo
                        } else {
                            caracter = (char) caractere_int; //se nao, continuo
                            if (caracter == '}') {      //se for o fim do comentario
                                stop_loop = true;       //paro o loop
                            }
                            if (caracter == '\n') {
                                linha_atual++;
                            }
                        }
                    } while (!stop_loop);
                    //depois do loop, eu tenho que ler mais um, pra enviar na proxima vez do while
                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                        file_not_finished = false;  //paro o while externo
                    } else {
                        caracter = (char) caractere_int;
                    }
                    break;
                case ' ':
                    //se for espaço, eu só ignoro e pego o proximo
                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                        file_not_finished = false;  //paro o while externo
                    } else {
                        caracter = (char) caractere_int;
                    }
                    break;
                case 13:
                    //n sei pq, mas tem que ler essa merda
                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                        file_not_finished = false;  //paro o while externo
                    } else {
                        caracter = (char) caractere_int;
                    }
                    break;
                case 9:
                    //n sei pq, mas tem que ler essa merda
                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                        file_not_finished = false;  //paro o while externo
                    } else {
                        caracter = (char) caractere_int;
                    }
                    break;
                case '\n':
                    //se for \n eu tb so ignoro, mas eu add 1 na linha que eu to lendo, pra ter o controle de qual linha é e pego o prox char
                    linha_atual++;
                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                        file_not_finished = false;  //paro o while externo
                    } else {
                        caracter = (char) caractere_int;
                    }
                    break;
                default:
                    pegaToken(reader, jTextAreaErro);
                    break;
            }

        }
    }

    public void pegaToken(String reader, javax.swing.JTextArea jTextAreaErro) throws IOException {
        //se for digito
        int caractere_int;
        if (caracter >= 48 && caracter <= 57) {
            trataDigito(reader);
        } //se for letra
        else if (caracter >= 65 && caracter <= 122) {
            trataIdentificador(reader);

        } //se for :
        else if (caracter == 58) {
            trataAtribuicao(reader);

        } //se for + - *
        else if (caracter == 43 || caracter == 45 || caracter == 42) {
            trataOpAritimetica(reader);
            caractere_int = reader.charAt(char_to_read);      //pego 1 char
            char_to_read++;
            if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                file_not_finished = false;  //paro o while externo
            } else {
                caracter = (char) caractere_int;
            }
        } //se for < > = !=
        else if ((caracter >= 60 && caracter <= 62) || (caracter == 33)) {
            if (trataOpRelacional(reader)) {
                if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                    file_not_finished = false;  //paro o while externo
                }
            } else {
                Token new_token = new Token();
                new_token.setLexema("ERRO");
                new_token.setSimbolo("serro");
                new_token.setLinha(linha_atual);
                new_token.setIndexStart(char_to_read - new_token.getLexema().length());
                new_token.setIndexEnd(char_to_read);
                INSTANCE.addToken(new_token);
                jTextAreaErro.setText("Erro na Linha " + String.valueOf(linha_atual) + "!!");
                System.err.println("Erro na Linha " + linha_atual + "!!");
                file_not_finished = false;

            }

        } //se for ; , ( ) .
        else if (caracter == 59 || caracter == 44 || caracter == 40 || caracter == 41 || caracter == 46) {
            trataPontuacao(reader);
            caractere_int = reader.charAt(char_to_read);      //pego 1 char
            char_to_read++;
            if (char_to_read == tamanho_string) {        //se for eof, paro o loop
                file_not_finished = false;  //paro o while externo
            } else {
                caracter = (char) caractere_int;
            }
        }//se nao, erro
        else {
            Token new_token = new Token();
            new_token.setLexema("ERRO");
            new_token.setSimbolo("serro");
            new_token.setLinha(linha_atual);
            new_token.setIndexStart(char_to_read - new_token.getLexema().length());
            new_token.setIndexEnd(char_to_read);
            INSTANCE.addToken(new_token);
            jTextAreaErro.setText("Erro na Linha " + String.valueOf(linha_atual) + "!!");
            System.err.println("Erro na Linha " + linha_atual + "!!");
            file_not_finished = false;

        }

    }

    public void trataDigito(String reader) throws IOException {
        String palavra = "";
        palavra = palavra.concat(String.valueOf(caracter));

        boolean stop_loop = false;
        do {
            int caractere_int = reader.charAt(char_to_read);      //pego 1 char
            char_to_read++;

            if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                file_not_finished = false;
                stop_loop = true;               //paro ESSE do while
            } else {
                caracter = (char) caractere_int; //se nao, continuo
                if (caracter >= 48 && caracter <= 57) {      //se for numero, concateno
                    palavra = palavra.concat(String.valueOf(caracter));
                } else {        //se nao, eu paro
                    stop_loop = true;
                }

            }
        } while (!stop_loop);

        Token new_token = new Token();
        new_token.setLexema(palavra);
        new_token.setSimbolo("snumero");
        new_token.setLinha(linha_atual);
        new_token.setIndexStart(char_to_read - new_token.getLexema().length());
        new_token.setIndexEnd(char_to_read);
        INSTANCE.addToken(new_token);
    }

    public void trataIdentificador(String reader) throws IOException {
        String palavra = "";
        palavra = palavra.concat(String.valueOf(caracter));

        boolean stop_loop = false;
        do {
            int caractere_int = reader.charAt(char_to_read);      //pego 1 char
            char_to_read++;
            if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                file_not_finished = false;
                stop_loop = true;               //paro ESSE do while
            } else {
                caracter = (char) caractere_int; //se nao, continuo
                if ((caracter >= 48 && caracter <= 57) || (caracter >= 65 && caracter <= 122) || (caracter == 95)) {      //se for numero ou letra ou _, concateno
                    palavra = palavra.concat(String.valueOf(caracter));
                } else {        //se nao, eu paro
                    stop_loop = true;
                }

            }
        } while (!stop_loop);
        Token new_token = new Token();
        new_token.setLexema(palavra);

        switch (palavra) {
            case "programa":
                new_token.setSimbolo("sprograma");
                break;
            case "se":
                new_token.setSimbolo("sse");
                break;
            case "entao":
                new_token.setSimbolo("sentao");
                break;
            case "senao":
                new_token.setSimbolo("ssenao");
                break;
            case "enquanto":
                new_token.setSimbolo("senquanto");
                break;
            case "faca":
                new_token.setSimbolo("sfaca");
                break;
            case "inicio":
                new_token.setSimbolo("sinicio");
                break;
            case "fim":
                new_token.setSimbolo("sfim");
                break;
            case "escreva":
                new_token.setSimbolo("sescreva");
                break;
            case "leia":
                new_token.setSimbolo("sleia");
                break;
            case "var":
                new_token.setSimbolo("svar");
                break;
            case "inteiro":
                new_token.setSimbolo("sinteiro");
                break;
            case "booleano":
                new_token.setSimbolo("sbooleano");
                break;
            case "verdadeiro":
                new_token.setSimbolo("sverdadeiro");
                break;
            case "falso":
                new_token.setSimbolo("sfalso");
                break;
            case "procedimento":
                new_token.setSimbolo("sprocedimento");
                break;
            case "funcao":
                new_token.setSimbolo("sfuncao");
                break;
            case "div":
                new_token.setSimbolo("sdiv");
                break;
            case "e":
                new_token.setSimbolo("se");
                break;
            case "ou":
                new_token.setSimbolo("sou");
                break;
            case "nao":
                new_token.setSimbolo("snao");
                break;
            default:
                new_token.setSimbolo("sidentificador");
                break;

        }
        new_token.setLinha(linha_atual);
        new_token.setIndexStart(char_to_read - new_token.getLexema().length());
        new_token.setIndexEnd(char_to_read);
        INSTANCE.addToken(new_token);
    }

    public void trataAtribuicao(String reader) throws IOException {
        String palavra = "";
        palavra = palavra.concat(String.valueOf(caracter));

        int caractere_int = reader.charAt(char_to_read);      //pego 1 char
        char_to_read++;
        if (char_to_read == tamanho_string) {          //se for eof, paro o loop
            file_not_finished = false;
        } else {
            caracter = (char) caractere_int; //se nao, continuo
            if (caracter == 61) {      //se for =
                palavra = palavra.concat(String.valueOf(caracter));
                Token new_token = new Token();
                new_token.setLexema(palavra);
                new_token.setSimbolo("satribuicao");
                new_token.setLinha(linha_atual);
                new_token.setIndexStart(char_to_read - new_token.getLexema().length() + 1);
                new_token.setIndexEnd(char_to_read + 1);
                INSTANCE.addToken(new_token);
                //aqui eu leio um char de novo, pq eu trato o : e o =, entao tenho que ler outro
                caractere_int = reader.charAt(char_to_read);      //pego 1 char
                char_to_read++;
                if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                    file_not_finished = false;          //paro ESSE do while
                } else {
                    caracter = (char) caractere_int; //se nao, continuo
                }
            } else {
                //adicionar como : e n le o prox
                //aqui eu n leio o prox, pq o prox ele vai considerar como sendo o caracter lido que nao é o = , ja que a gnt n tratou aqui
                Token new_token = new Token();
                new_token.setLexema(palavra);
                new_token.setSimbolo("sdoispontos");
                new_token.setLinha(linha_atual);
                new_token.setIndexStart(char_to_read - 1);
                new_token.setIndexEnd(char_to_read - 0);
                INSTANCE.addToken(new_token);
            }

        }

    }

    public void trataOpAritimetica(String reader) {
        String palavra = "";
        palavra = palavra.concat(String.valueOf(caracter));
        Token new_token = new Token();
        new_token.setLexema(palavra);
        switch (palavra) {
            case "+":
                new_token.setSimbolo("smais");
                break;
            case "-":
                new_token.setSimbolo("smenos");
                break;
            case "*":
                new_token.setSimbolo("smult");
                break;
        }
        new_token.setLinha(linha_atual);
        new_token.setIndexStart(char_to_read - new_token.getLexema().length());
        new_token.setIndexEnd(char_to_read);
        INSTANCE.addToken(new_token);

    }

    public boolean trataOpRelacional(String reader) throws IOException {
        String palavra = "";
        palavra = palavra.concat(String.valueOf(caracter));
        Token new_token = new Token();
        int caractere_int;

        switch (palavra) {
            case "<":
                caractere_int = reader.charAt(char_to_read);      //pego 1 char
                char_to_read++;
                if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                    file_not_finished = false;
                } else {
                    caracter = (char) caractere_int; //se nao, continuo
                }
                if (caracter == '=') {

                    palavra = palavra.concat(String.valueOf(caracter));
                    new_token.setLexema(palavra);
                    new_token.setSimbolo("smenorig");
                    new_token.setLinha(linha_atual);
                    new_token.setIndexStart(char_to_read - new_token.getLexema().length() + 1);
                    new_token.setIndexEnd(char_to_read + 1);
                    INSTANCE.addToken(new_token);

                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                        file_not_finished = false;
                    } else {
                        caracter = (char) caractere_int; //se nao, continuo
                    }
                } else {
                    new_token.setLexema(palavra);
                    new_token.setSimbolo("smenor");
                    new_token.setLinha(linha_atual);
                    new_token.setIndexStart(char_to_read - new_token.getLexema().length());
                    new_token.setIndexEnd(char_to_read);
                    INSTANCE.addToken(new_token);
                }
                break;
            case ">":
                caractere_int = reader.charAt(char_to_read);      //pego 1 char
                char_to_read++;
                if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                    file_not_finished = false;
                } else {
                    caracter = (char) caractere_int; //se nao, continuo
                }
                if (caracter == '=') {

                    palavra = palavra.concat(String.valueOf(caracter));
                    new_token.setLexema(palavra);
                    new_token.setSimbolo("smaiorig");
                    new_token.setLinha(linha_atual);
                    new_token.setIndexStart(char_to_read - new_token.getLexema().length() + 1);
                    new_token.setIndexEnd(char_to_read + 1);
                    INSTANCE.addToken(new_token);

                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                        file_not_finished = false;
                    } else {
                        caracter = (char) caractere_int; //se nao, continuo
                    }
                } else {
                    new_token.setLexema(palavra);
                    new_token.setSimbolo("smaior");
                    new_token.setLinha(linha_atual);
                    new_token.setIndexStart(char_to_read - new_token.getLexema().length());
                    new_token.setIndexEnd(char_to_read);
                    INSTANCE.addToken(new_token);
                }
                break;
            case "=":
                caractere_int = reader.charAt(char_to_read);      //pego 1 char
                char_to_read++;
                if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                    file_not_finished = false;
                } else {
                    caracter = (char) caractere_int; //se nao, continuo
                }
                if (caracter == '=') {

                    palavra = palavra.concat(String.valueOf(caracter));
                    new_token.setLexema(palavra);
                    new_token.setSimbolo("smenorig");
                    new_token.setLinha(linha_atual);
                    new_token.setIndexStart(char_to_read - new_token.getLexema().length() + 1);
                    new_token.setIndexEnd(char_to_read + 1);
                    INSTANCE.addToken(new_token);

                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                        file_not_finished = false;
                    } else {
                        caracter = (char) caractere_int; //se nao, continuo
                    }
                } else {
                    new_token.setLexema(palavra);
                    new_token.setSimbolo("sig");
                    new_token.setLinha(linha_atual);
                    new_token.setIndexStart(char_to_read - new_token.getLexema().length());
                    new_token.setIndexEnd(char_to_read);
                    INSTANCE.addToken(new_token);
                }
                break;
            case "!":
                caractere_int = reader.charAt(char_to_read);      //pego 1 char
                char_to_read++;
                if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                    file_not_finished = false;
                } else {
                    caracter = (char) caractere_int; //se nao, continuo
                }
                if (caracter == '=') {

                    palavra = palavra.concat(String.valueOf(caracter));
                    new_token.setLexema(palavra);
                    new_token.setSimbolo("sdif");
                    new_token.setLinha(linha_atual);
                    new_token.setIndexStart(char_to_read - new_token.getLexema().length() + 1);
                    new_token.setIndexEnd(char_to_read + 1);
                    INSTANCE.addToken(new_token);

                    caractere_int = reader.charAt(char_to_read);      //pego 1 char
                    char_to_read++;
                    if (char_to_read == tamanho_string) {          //se for eof, paro o loop
                        file_not_finished = false;
                    } else {
                        caracter = (char) caractere_int; //se nao, continuo
                    }

                } else {
                    caracter = palavra.charAt(0);
                    return false;
                }
                break;

        }
        return true;

    }

    public void trataPontuacao(String reader) {
        String palavra = "";
        palavra = palavra.concat(String.valueOf(caracter));
        Token new_token = new Token();
        new_token.setLexema(palavra);

        switch (palavra) {
            case ";":
                new_token.setSimbolo("sponto_virgula");
                break;
            case ",":
                new_token.setSimbolo("svirgula");
                break;
            case "(":
                new_token.setSimbolo("sabre_parenteses");
                break;
            case ")":
                new_token.setSimbolo("sfecha_parenteses");
                break;
            case ".":
                new_token.setSimbolo("sponto");
                break;
        }
        new_token.setLinha(linha_atual);
        new_token.setIndexStart(char_to_read - new_token.getLexema().length() + 1);
        new_token.setIndexEnd(char_to_read + 1);
        INSTANCE.addToken(new_token);
    }
}
