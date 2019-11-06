/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matheus
 */
public class Gerenciador {

    private static Gerenciador INSTANCE;
    private static Scanner scanner = new Scanner(System.in);
    private static Lexico analisadorLexico;
    private ArrayList<Token> tokens = new ArrayList<Token>();
    private ArrayList<Simbolo> simbolos = new ArrayList<Simbolo>();

    public static Gerenciador getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gerenciador();
        }
        return INSTANCE;
    }

    public boolean LerArquivo(File lpdLido, javax.swing.JTextArea jTextAreaPrograma) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(lpdLido));
            if (reader != null) {
                jTextAreaPrograma.read(reader, "jTextAreaPrograma");
            } else {
                return false;
            }
            //analisadorLexico = new Lexico(reader);
            //printaTokens();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gerenciador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Gerenciador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //String text = jTextAreaPrograma.getText();
        //System.out.println(text);

        return true;
    }

    public void addToken(Token new_token) {
        tokens.add(new_token);
    }

    public Token getToken() {
        return tokens.remove(0);
    }

    public void resetaTokens() {
        tokens.clear();
    }

    public void resetaSimbolos() {
        simbolos.clear();
    }

    public void printaTokens() {
        System.out.println("LISTA DE TOKENS");
        for (Token token : tokens) {
            System.out.println("token.lexema: " + token.getLexema());
            System.out.println("token.simbolo: " + token.getSimbolo());
            System.out.println("----------------");
        }
    }

    public void addSimbolo(Simbolo simbolo) {
        this.simbolos.add(simbolo);
    }

    public ArrayList<Simbolo> getSimbolos() {
        return simbolos;
    }

    public void removeFuncProcSimbolos() {
        boolean finish = false;

        do {
            if (!simbolos.get(simbolos.size() - 1).isEscopo()) {
                simbolos.remove(simbolos.size() - 1);
            } else {
                Simbolo simbAux = simbolos.get(simbolos.size() - 1);
                simbAux.setEscopo(false);
                simbolos.set(simbolos.size() - 1, simbAux);
                finish = true;
            }
        } while (!finish);
    }

    public void setSimbolos(ArrayList<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }

    void printaSimbolos() {
        System.out.println("LISTA DE SIMBOLOS");
        for (Simbolo simbolo : simbolos) {
            System.out.println("token.lexema: " + simbolo.getLexema());
            System.out.println("----------------");
        }
    }
}
