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

    public static Gerenciador getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gerenciador();
        }
        return INSTANCE;
    }

    public boolean LerArquivo(File lpdLido) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(lpdLido));
            String text;
            while ((text = reader.readLine()) != null) {
                System.out.println(text);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gerenciador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Gerenciador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
