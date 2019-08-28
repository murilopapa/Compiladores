package projeto2;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JDialog;
import javax.swing.JMenuItem;

public class FrameInsercao extends JDialog {

	private JButton buttonOk, buttonAddAtividade, buttonCancelar;
	private JLabel textoNome;
	private JTextField nomeTurma, quantidadeAtividades;
	private String nome_turma, pegaQntd;
	private int quantidade_atividades = 0;
	private FrameInsercao frameInsercao = this;
	private File arquivo_csv;
	private JTabbedPane painel;
	private Object nome_e_pesos[][];
	private boolean custo = true;
	private JMenuItem exportar;

	public FrameInsercao(File recebe_arquivo_csv, JTabbedPane recebe_painel, JMenuItem menu) {
		setLayout(null);
		// Botão para salvar e sair
		buttonOk = new JButton("Ok");
		buttonOk.setBounds(96, 280, 150, 28);
		add(buttonOk);

		// Botão para sair sem salvar
		buttonCancelar = new JButton("Cancelar");
		buttonCancelar.setBounds(252, 280, 150, 28); // (Posicao largura, posicao altura, largura botao, altura botao)
		add(buttonCancelar);

		buttonAddAtividade = new JButton("Adicionar Atividades");
		buttonAddAtividade.setBounds(92, 60, 180, 28);
		add(buttonAddAtividade);

		textoNome = new JLabel("Nome da Turma: ");
		textoNome.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textoNome.setBounds(40, 20, 120, 20); // (Posicao largura, posicao altura, largura botao, altura botao)
		add(textoNome);

		nomeTurma = new JTextField();
		nomeTurma.setBounds(160, 20, 200, 24);
		add(nomeTurma);

		quantidadeAtividades = new JTextField("0");
		quantidadeAtividades.setBounds(56, 58, 32, 32);
		add(quantidadeAtividades);
		exportar = menu;
		FrameInsercao.TextFieldHandler handler = new FrameInsercao.TextFieldHandler();
		buttonOk.addActionListener(handler);
		nomeTurma.addActionListener(handler);
		buttonAddAtividade.addActionListener(handler);
		quantidadeAtividades.addActionListener(handler);
		buttonCancelar.addActionListener(handler);
		arquivo_csv = recebe_arquivo_csv;
		painel = recebe_painel;
	}

	private class TextFieldHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == buttonOk) {
				Gerenciador INSTANCE = Gerenciador.getInstance();

				String nome_atividades[] = new String[quantidade_atividades]; // salvo em vetor, o nome das atividades
				Float peso_atividades[] = new Float[quantidade_atividades]; // salvo em vetor, o peso das atividades

				for (int i = 0; i < quantidade_atividades; i++) {
					nome_atividades[i] = nome_e_pesos[i][0].toString(); // converto pra string pq na tabela é um objeto
					peso_atividades[i] = Float.parseFloat(nome_e_pesos[i][1].toString()); // o mesmo acima
				}
				boolean turma_ja_existe = false;
				for (Turma array : INSTANCE.getTurmas()) {
					if (array.getNome_da_turma().equals(nomeTurma.getText())) {
						turma_ja_existe = true;
					}
				}
				if (turma_ja_existe) {
					JOptionPane.showMessageDialog(null, "Nome da turma ja existe!");
				} else {
					if (INSTANCE.addTurma(arquivo_csv, nomeTurma.getText(), nome_atividades, peso_atividades) == false) // adiciono

					{
						JOptionPane.showMessageDialog(null, "Erro ao abrir arquivo");
					}
					Turma ultima_turma = INSTANCE.getUltimaTurmaAdd(); // pego a ultima turma adicionada
					exportar.setEnabled(true); // botao exportar agora clicavel
					painel.addTab(ultima_turma.getNome_da_turma(), new FrameTabela(ultima_turma)); // adiciono a tab com
					
				}
				frameInsercao.dispose();
			}
			if ((event.getSource() == buttonAddAtividade) && (custo == true)) {
				quantidadeAtividades.getText();
				if ((quantidade_atividades = Integer.parseInt(quantidadeAtividades.getText())) > 0) {
					custo = false;
					nome_e_pesos = new Object[quantidade_atividades][2];

					JTable table = new JTable(nome_e_pesos, new String[] { "NOME", "PESO" }); // crio uma tabela para salvar nome da atividade e peso
					JScrollPane barraRolagem = new JScrollPane(table);

					barraRolagem.setBounds(31, 100, 426, 175);
					table.setRowSelectionAllowed(false);
					frameInsercao.add(barraRolagem);
				} else {
					JOptionPane.showMessageDialog(null, "Insira número válido de atividades");
				}

			}
			if (event.getSource() == buttonCancelar) {
				frameInsercao.dispose();
			}

		}
	}
}
