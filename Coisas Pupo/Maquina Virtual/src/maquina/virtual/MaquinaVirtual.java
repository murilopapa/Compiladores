/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquina.virtual;

import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Matheus Pupo, Murilo Martos, Mateus Zorzi
 */
public class MaquinaVirtual {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainFrame main_frame = new MainFrame();
        //main_frame.setUndecorated(true);
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setSize(1000, 700);
        main_frame.setLocationRelativeTo(null);
        main_frame.setVisible(true);
        //main_frame.setResizable(false);
        
    }
    
}
