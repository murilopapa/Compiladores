package projeto2;

import javax.swing.JFrame;

public class Projeto2 {

    public static void main(String[] args) {
        
        MainFrame main_frame = new MainFrame();
        //main_frame.setUndecorated(true);
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setSize(1000, 700);
        main_frame.setLocationRelativeTo(null);
        main_frame.setVisible(true);
        //main_frame.setResizable(false);
    }

}
