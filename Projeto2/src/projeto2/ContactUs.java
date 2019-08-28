package projeto2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ContactUs extends JFrame {
    private ContactUs contato = this;
    private JButton buttonOk;
    private ImageIcon planoFundo = new ImageIcon(getClass().getResource("images/fundoContactUs.png"));

    public ContactUs() {
        buttonOk = new JButton("Ok");
        buttonOk.setBounds(175, 150, 150, 28);
        add(buttonOk);
        
        Panel planoFundo = new Panel();
        add(planoFundo);
        
        ContactUs.TextFieldHandler handler = new ContactUs.TextFieldHandler();
        buttonOk.addActionListener(handler);
    }
    
    private class TextFieldHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == buttonOk)
            {
                contato.dispose();
            }
        }
    }

    public class Panel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image img = planoFundo.getImage();
            g.drawImage(img, 0, 0, this);
        }
    }
        
}