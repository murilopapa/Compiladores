package projeto2;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

public class FrameTabela extends JPanel {

    private JTable table;
    private JPanel tabela = this;
    private JScrollPane barraRolagem;
    private Object[][] alunos;
    private String[] titulos;
    private Turma essa_turma;
    private int TAM2 = 0;

    public FrameTabela(Turma nova_turma) {

        essa_turma = nova_turma;    //pego a turma para deixar salvo na tabela
        int TAM1 = (nova_turma.getAlunos()).size();    //quantidade de alunos
        Aluno aluno_qualquer = null;            //inicializo para pegar um aluno qualquer
        for (Aluno array : nova_turma.getAlunos()) {
            TAM2 = (array.getNotas()).size();      //pego a quantidade de notas que a turma vai ter
            aluno_qualquer = array;         //salvo um aluno qualquer
        }

        alunos = new Object[TAM1][TAM2 + 2];
        titulos = new String[TAM2 + 2];
        int i = 0;
        titulos[0] = "NOME";
        int j = 1;
        for (Nota array : aluno_qualquer.getNotas()) {
            titulos[j] = array.getNome_da_atividade();      //salvo o nome das atividades
            j++;
        }
        titulos[j] = "MEDIA";

        for (Aluno array : nova_turma.getAlunos()) {
            j = 0;
            alunos[i][j] = array.getNome_do_aluno();            //pego o nome do aluno
            for (Nota array2 : array.getNotas()) {
                j++;
                alunos[i][j] = array2.getNotaDoAluno();            //vou salvando as notas
            }
            alunos[i][TAM2 + 1] = array.getMedia();             //pego a media
            i++;
        }

        
        table = new JTable(alunos, titulos){        //tudo isso serve para deixar media <5 vermelha e >=5 em verde
            @Override
            public Class<?> getColumnClass(int column) {
                if(convertColumnIndexToModel(column)==TAM2+1) return Float.class;
                return super.getColumnClass(column);
            }
        };
        table.setDefaultRenderer(Float.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
                c.setForeground((Float.parseFloat(value.toString()))>=5 ? Color.GREEN : Color.RED);
                return c;
            }
        });         //ate aqui
        
        table.setRowSelectionAllowed(false);        //deixa selecionar apenas uma celula ao inves da coluna toda
        setLayout(new GridLayout(1, 1));            //pra ficar resizeble

        barraRolagem = new JScrollPane(table);      //scroll padrao de projeto bem feito 
        
        add(barraRolagem);
        setTableModelListener(table);
    }

    public void atualizar_dados() {

        Gerenciador INSTANCE = Gerenciador.getInstance();

        Turma turma_atualizada = new Turma(essa_turma.getNome_da_turma());      //crio uma turma atualizada

        int i = 0, j = 0;
        for (Aluno array_aluno : essa_turma.getAlunos()) {
            Collection<Nota> notas_novas = new ArrayList<Nota>();               //um collection de notas para as novas notas atualizadas
            j = 0;
            for (Nota array_notas : array_aluno.getNotas()) {
                notas_novas.add(new Nota(array_notas.getNome_da_atividade(), array_notas.getPeso_da_atividade(), Float.parseFloat(alunos[i][j + 1].toString())));  // vou preenchendo o vetor de notas com as notas da tabela
                j++;
            }

            turma_atualizada.addAluno(array_aluno.getNome_do_aluno(), notas_novas); //vou adicionando os alunos com suas respectivas notas

            i++;

        }
        i = 0;
        j = 0;
        for (Aluno array : turma_atualizada.getAlunos()) {
            j = 0;
            alunos[i][j] = array.getNome_do_aluno();
            for (Nota array2 : array.getNotas()) {
                j++;
                alunos[i][j] = array2.getNotaDoAluno();
            }
            alunos[i][j + 1] = array.getMedia();
            i++;
        }
        
        INSTANCE.atualiza_BD(turma_atualizada, essa_turma.getNome_da_turma());      //atualizo o banco passando o nome da turma a ser atualizada e a turma atualizada
        table.updateUI();
    }
    
    private void setTableModelListener(JTable tabela)       //handler para ver qualquer alteração de celula
    {
    		Object tableModelListener = new TableModelListener()
    		{
	    		public void tableChanged(TableModelEvent e)
	    		{
	    			if(e.getType() == TableModelEvent.UPDATE)
	    			{
	    				
	    				atualizar_dados();
	    				table.updateUI();
	    			}
	    		}
    		};
    		tabela.getModel().addTableModelListener((TableModelListener) tableModelListener);
    }
}
