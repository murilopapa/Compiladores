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
    
    public static Gerenciador getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gerenciador();
        }
        return INSTANCE;
    }

    public boolean LerArquivo(File lpdLido, javax.swing.JTextArea jTextArea1) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(lpdLido));
            String atual = reader.readLine();
            while (atual != null) {
                //System.out.println(atual);
                jTextArea1.read(reader, "jTextArea1");
                atual = reader.readLine();
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
        //String text = jTextArea1.getText();
        //System.out.println(text);

        return true;
    }

    public void addToken(Token new_token) {
        tokens.add(new_token);
    }

    public void printaTokens() {
        for (Token token : tokens) {
            System.out.println("token.lexema: " + token.getLexema());
            System.out.println("token.simbolo: " + token.getSimbolo());
            System.out.println("----------------");
        }
    }

}
