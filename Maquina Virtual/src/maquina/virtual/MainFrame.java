package maquina.virtual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {
    private MainFrame main_frame = this;
    // Tabela
    private JTabbedPane painel;
    // Menu superior
    private JMenuBar menuBar;
    private JMenu arquivos, executar, sobre;
    private JMenuItem importar;
    private Gerenciador INSTANCE = Gerenciador.getInstance();
    private Object teste[][];
    private JTable table;
    
    public MainFrame() {
        super("Maquina Virtual");
        
        Panel planoFundo = new Panel();
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
        painel = new JTabbedPane();
        planoFundo.setLayout(new BorderLayout());
        planoFundo.add(painel);
        
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
                    arquivo_obj = abrir_arquivo.getSelectedFile();  //pego o arquivo escolhido
                    FrameTabela frameTabela = new FrameTabela(arquivo_obj);
                        
        
                  //  String [] colunas = {"Comando", "Instrução 1", "Instrução 2"};
                  //  Object [][] dados = {
                  //      {"Ana Monteiro", "48 9923-7898", "ana.monteiro@gmail.com"},
                  //      {"João da Silva", "48 8890-3345", "joaosilva@hotmail.com"},
                  //      {"Pedro Cascaes", "48 9870-5634", "pedrinho@gmail.com"}
                  //  };
                  //  JTable tabela = new JTable(dados, colunas);
                  //  JScrollPane barraRolagem = new JScrollPane(tabela);
                  //  barraRolagem.setBounds(25, 25, 950, 500);
                  //  main_frame.add(barraRolagem);
                }
            }
        }
    }
}