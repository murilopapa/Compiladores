package maquina.virtual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {

    private MainFrame main_frame = this;
    // Tabela
    //private JTabbedPane painel;
    // Menu superior
    private JMenuBar menuBar;
    private JMenu arquivos, executar, sobre;
    private JMenuItem importar;
    private Gerenciador INSTANCE = Gerenciador.getInstance();
    private Panel planoFundo = new Panel();

    public MainFrame() {
        super("Maquina Virtual");

        add(planoFundo);

        // Variável Barra de Menu
        menuBar = new JMenuBar();
        // Variáveis Items Barra Menu
        arquivos = new JMenu("Arquivos");
        executar = new JMenu("Executar");
        sobre = new JMenu("Sobre...");
        // Variáveis Opcoes Items Menu
        importar = new JMenuItem("Importar OBJ");

        // Add Barra Menu
        setJMenuBar(menuBar);
        // Add Items Barra Menu
        menuBar.add(arquivos);
        menuBar.add(executar);
        menuBar.add(sobre);
        // Add Opcoes nos Items dos Menus
        // Arquivos
        arquivos.add(importar);

        // Painel de instrucoes e plano de fundo
        //painel = new JTabbedPane();
        planoFundo.setLayout(new BorderLayout());
        //planoFundo.add(painel);

        MainFrame.TextFieldHandler handler = new MainFrame.TextFieldHandler();
        importar.addActionListener(handler);

    }

    public class Panel extends JPanel {

    }

    private class TextFieldHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == importar) {
                JFileChooser abrir_arquivo = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Obj", "obj");    //seta apenas extensoes .obj
                abrir_arquivo.setFileFilter(filter);
                File arquivo_obj;
                abrir_arquivo.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
                int valor = abrir_arquivo.showOpenDialog(null);

                if (valor == JFileChooser.APPROVE_OPTION) {
                    arquivo_obj = abrir_arquivo.getSelectedFile(); //pego o arquivo escolhido

                    Object[][] codigo;
                    String[] titulos;


                    if (INSTANCE.LerArquivo(arquivo_obj)
                            == false) {
                        JOptionPane.showMessageDialog(null, "Erro ao abrir arquivo");
                    }
                    int TAM1 = (INSTANCE.TamanhoMemoria());    //quantidade de alunos
                    int TAM2 = 5;

                    codigo = new Object[TAM1][TAM2];
                    titulos = new String[TAM2];

                    titulos[0] = "LINHA";
                    titulos[1] = "BREAKPOINT";
                    titulos[2] = "COMANDO";
                    titulos[3] = "ARG1";
                    titulos[4] = "ARG2";
                    Memoria recebe_memoria = INSTANCE.getMemoria();
                    for (int i = 0;
                            i < recebe_memoria.getTotalLinhas();
                            i++) {
                        Funcoes funcao_atual = recebe_memoria.getFuncByIndex(i);
                        codigo[i][0] = i + 1;
                        codigo[i][1] = "X";
                        codigo[i][2] = funcao_atual.getFuncao();
                        codigo[i][3] = funcao_atual.getArg1();
                        codigo[i][4] = funcao_atual.getArg2();
                    }
                    JTable table = new JTable(codigo, titulos);

                    JScrollPane scrollPane = new JScrollPane(table);
                    table.setFillsViewportHeight(true);

                    planoFundo.add(scrollPane);
                    planoFundo.updateUI();
                }
            }
        }
    }
}
