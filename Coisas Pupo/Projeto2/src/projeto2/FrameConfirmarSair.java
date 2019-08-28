package projeto2;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class FrameConfirmarSair extends JDialog {
    private FrameConfirmarSair frameConfirmarSair = this;
    private JButton buttonSim, buttonNao;
    private JLabel textoConfirmar;
    private MainFrame main_frame;
    private ImageIcon confirmarSair = new ImageIcon(getClass().getResource("images/confirmarSair.png"));

    public FrameConfirmarSair(MainFrame main_frame) {
        setLayout(null);
        this.main_frame = main_frame;
        // Mensagem para o usuario confirmar
        textoConfirmar = new JLabel("Deseja realmente sair do sistema?");
        textoConfirmar.setFont(new Font("Times New Roman", Font.BOLD, 13));
        textoConfirmar.setBounds(98, 14, 224, 30); // (Posicao largura, posicao altura, largura botao, altura botao)
        textoConfirmar.setIcon(confirmarSair);
        add(textoConfirmar);

        // Botão para confirmar
        buttonSim = new JButton("Sim");
        buttonSim.setBounds(107, 52, 100, 22); // (Posicao largura, posicao altura, largura botao, altura botao)
        add(buttonSim);

        // Botão para cancelar
        buttonNao = new JButton("Não");
        buttonNao.setBounds(213, 52, 100, 22); // (Posicao largura, posicao altura, largura botao, altura botao)
        add(buttonNao);

        // Action Listeners
        FrameConfirmarSair.TextFieldHandler handler = new FrameConfirmarSair.TextFieldHandler();
        buttonNao.addActionListener(handler);
        buttonSim.addActionListener(handler);
    }

    private class TextFieldHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == buttonNao)
            {
                frameConfirmarSair.dispose();
            }
            if (event.getSource() == buttonSim)
            {
                Gerenciador INSTANCE = Gerenciador.getInstance();
                INSTANCE.salvaDados();
                main_frame.dispose();
                frameConfirmarSair.dispose();
            }
        }
    }
}
