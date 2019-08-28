/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquina.virtual;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Formatter;
import java.util.Scanner;
/**
 *
 * @author matheus
 */
public class Gerenciador {
    private static Gerenciador INSTANCE;
    
    public static Gerenciador getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gerenciador();

        }
        return INSTANCE;
    }
    
    public boolean addExecutavel (File arquivo_obj) {
        Scanner leitura_arquivo = null;
        try {
            leitura_arquivo = new Scanner(arquivo_obj);     //tenta abrir arquivo
            //System.out.println("Arquivo OBJ aberto com sucesso!");
        } catch (FileNotFoundException ex) {
            return false;
        }
        
        String comando = "";
        String atual = "";
        leitura_arquivo.useDelimiter("");
        
        
        while (leitura_arquivo.hasNext()) { //enquanto tem coisa para ler
            atual = leitura_arquivo.nextLine();
            System.out.println(atual);            
        }
        

        leitura_arquivo.close();
        return true;
    }
}
