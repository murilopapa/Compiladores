/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquina.virtual;


import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FrameTabela extends JPanel {

    private FrameTabela frameTabela = this;
    private File arquivo_obj;

    private Object[][] codigo;
    private String[] titulos;

    private JTable table;
    private JPanel tabela = this;
    private JScrollPane barraRolagem;

    Gerenciador INSTANCE = Gerenciador.getInstance();

    public FrameTabela(File recebe_arquivo_obj) {
        arquivo_obj = recebe_arquivo_obj;

        if (INSTANCE.LerArquivo(arquivo_obj) == false) {
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
        for (int i = 0; i < recebe_memoria.getTotalLinhas(); i++) {
            Funcoes funcao_atual = recebe_memoria.getFuncByIndex(i);
            codigo[i][0] = i + 1;
            codigo[i][1] = "X";
            codigo[i][2] = funcao_atual.getFuncao();
            codigo[i][3] = funcao_atual.getArg1();
            codigo[i][4] = funcao_atual.getArg2();
        }

        table = new JTable(codigo, titulos);

        //table.setRowSelectionAllowed(false);        //deixa selecionar apenas uma celula ao inves da coluna toda
        //setLayout(new GridLayout(1, 1));            //pra ficar resizeble
        barraRolagem = new JScrollPane(table);      //scroll padrao de projeto bem feito 
        barraRolagem.setBounds(25, 25, 950, 500);
        frameTabela.add(barraRolagem);
        //setTableModelListener(table);  //vai usar pro break point
    }

}
