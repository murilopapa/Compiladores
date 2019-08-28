package maquina.virtual;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

public class MaquinaVirtual {

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
