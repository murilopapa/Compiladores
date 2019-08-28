package projeto2;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {

    private JTabbedPane painel;
    private MainFrame main_frame = this;
    private ImageIcon fundo = new ImageIcon(getClass().getResource("images/fundo.png"));
    private ImageIcon arquivoImg = new ImageIcon(getClass().getResource("images/arquivoImg.png"));
    private ImageIcon ajudaImg = new ImageIcon(getClass().getResource("images/ajudaImg.png"));
    private ImageIcon importarImg = new ImageIcon(getClass().getResource("images/importarImg.png"));
    private ImageIcon exportarImg = new ImageIcon(getClass().getResource("images/exportarImg.png"));
    private ImageIcon sairImg = new ImageIcon(getClass().getResource("images/sairImg.png"));
    private ImageIcon contactUsImg = new ImageIcon(getClass().getResource("images/contactusImg.png"));
    private ImageIcon editarImg = new ImageIcon(getClass().getResource("images/editarImg.png"));
    private Gerenciador INSTANCE = Gerenciador.getInstance();
    private JMenuBar menuBar;
    private JMenu arquivo, ajuda, editar;
    private JMenuItem importar, exportar, sair, contactUs, excluir;
    URL url = this.getClass().getResource("images/imagemAplicacao.png");  
    Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);  

    // https://www.devmedia.com.br/criando-uma-barra-de-menu/2384
    // https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
    public MainFrame() {
        super("Administrador Universitário");
        this.setIconImage(iconeTitulo);

        // Adicionar o plano de fundo
        Panel planoFundo = new Panel();
        add(planoFundo);

        // Variável Barra de Menu
        menuBar = new JMenuBar();
        // Variáveis Items Barra Menu
        arquivo = new JMenu("Arquivo");
        arquivo.setIcon(arquivoImg); //getScaledInstance
        editar = new JMenu("Editar");
        editar.setIcon(editarImg);
        ajuda = new JMenu("Ajuda");
        ajuda.setIcon(ajudaImg);

        // Variáveis Opcoes Items Menu
        importar = new JMenuItem("Importar CSV");
        importar.setIcon(importarImg);
        importar.setToolTipText("Importa um arquivo CSV para o programa");
        exportar = new JMenuItem("Exportar");
        exportar.setIcon(exportarImg);
        exportar.setToolTipText("Exporta um arquivo CSV da tabela aberta");
        sair = new JMenuItem("Sair");
        sair.setIcon(sairImg);
        sair.setToolTipText("Sai do programa");
        contactUs = new JMenuItem("Contato");
        contactUs.setIcon(contactUsImg);

        // Add Barra Menu
        setJMenuBar(menuBar);
        // Add Items Barra Menu
        menuBar.add(arquivo);
        menuBar.add(editar);
        menuBar.add(ajuda);
        // Add opcoes nos items do menu
        // Arquivo
        arquivo.add(importar);
        arquivo.add(exportar);
        arquivo.add(sair);
        // Ajuda
        ajuda.add(contactUs);

        importar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        exportar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        sair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        contactUs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));

        painel = new JTabbedPane();
        planoFundo.setLayout(new BorderLayout());
        planoFundo.add(painel);

        exportar.setEnabled(false);
        MainFrame.TextFieldHandler handler = new MainFrame.TextFieldHandler();
        exportar.addActionListener(handler);
        sair.addActionListener(handler);
        importar.addActionListener(handler);
        contactUs.addActionListener(handler);

        //Inicializa contas
        INSTANCE.leDados();

        //condiçao para deixar clicavel ou nao o botao exportar
        if (INSTANCE.getTurmas() != null) {
            for (Turma array : INSTANCE.getTurmas()) {
                painel.addTab(array.getNome_da_turma(), new FrameTabela(array));

                exportar.setEnabled(true);
                INSTANCE.incrementaContadoraDeTurmas();
            }
        }

    }

    public class Panel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image img = fundo.getImage();
            g.drawImage(img, 0, 0, this);
        }
    }

    private class TextFieldHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == contactUs) {
                ContactUs frameContato = new ContactUs();
                frameContato.setUndecorated(true);
                frameContato.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frameContato.setSize(500, 200);
                frameContato.setLocationRelativeTo(null);
                frameContato.setResizable(false);
                frameContato.setVisible(true);
            }
            if (event.getSource() == importar) {
                //abre o jfilechooser para pegar o arquivo
                JFileChooser abrir_arquivo = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Csv", "csv");    //seta apenas extensoes .csv
                abrir_arquivo.setFileFilter(filter);
                File arquivo_csv;
                abrir_arquivo.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
                int valor = abrir_arquivo.showOpenDialog(null);

                if (valor == JFileChooser.APPROVE_OPTION) {
                    arquivo_csv = abrir_arquivo.getSelectedFile();  //pego o arquivo escolhido

                    FrameInsercao frameInsercao = new FrameInsercao(arquivo_csv, painel, exportar);
                    frameInsercao.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    frameInsercao.setSize(500, 350);
                    frameInsercao.setLocationRelativeTo(null);
                    frameInsercao.setModal(true);
                    frameInsercao.setVisible(true);

                }

            }
            if (event.getSource() == exportar) {

                String teste = painel.getTitleAt(painel.getSelectedIndex());
                if (INSTANCE.exportar_CSV(teste) == true) {
                    JOptionPane.showMessageDialog(null, "EXPORTADO COM SUCESSO");
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO DE EXPORTAÇÃO");
                }

            }

            if (event.getSource() == sair) {

                FrameConfirmarSair frameConfirmarSair = new FrameConfirmarSair(main_frame);
                frameConfirmarSair.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                frameConfirmarSair.setSize(420, 110);
                frameConfirmarSair.setLocationRelativeTo(null);
                frameConfirmarSair.setModal(true);
                frameConfirmarSair.setVisible(true);
            }
        }
    }
}
