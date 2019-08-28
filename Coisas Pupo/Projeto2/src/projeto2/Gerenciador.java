package projeto2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Formatter;
import java.util.Scanner;

public class Gerenciador {
    private int contadora_de_turmas = 0;
    private Collection<Turma> turmas = new ArrayList<Turma>();
    private static Gerenciador INSTANCE;
    private Gerenciador() {
    }

    public static Gerenciador getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gerenciador();

        }
        return INSTANCE;
    }

    public Collection<Turma> getTurmas() {
        return turmas;
    }
    
    public int getContadoraDeTurmas()
    {
        return contadora_de_turmas;
    }
    public void incrementaContadoraDeTurmas()
    {
        contadora_de_turmas++;
    }
    
    public boolean addTurma(File arquivo_csv, String recebe_nome_da_turma, String[] nomes_atividades, Float[] peso_atividades) {
        Scanner leitura_arquivo = null;
        try {
            leitura_arquivo = new Scanner(arquivo_csv);     //tento abrir arquivo
        } catch (FileNotFoundException ex) {
            return false;
        }

        Turma turma_nova = new Turma(recebe_nome_da_turma);

        Collection<Nota> nota_nova = new ArrayList<Nota>();     //crio uma collection com notas

        for (int i = 0; i < nomes_atividades.length; i++) {
            nota_nova.add(new Nota(nomes_atividades[i], peso_atividades[i], 0));        //salvo as notas, inicializando com 0
        }

        String nome_aluno = " ";
        String atual = "";
        leitura_arquivo.useDelimiter("");
        while (leitura_arquivo.hasNext()) //enquanto tem coisa para ler
        {
            atual = leitura_arquivo.next();
            if (atual.equals(",") == true) {            //se for virgula, para e salva o nome do aluno
                turma_nova.addAluno(nome_aluno, nota_nova);
                nome_aluno = "";
            } else {
                nome_aluno = String.format("%s%s", nome_aluno, atual);          //se nao for virgula, vai adicionando ao vetor
            }
        }

        turmas.add(turma_nova);
        contadora_de_turmas++;
        leitura_arquivo.close();
        return true;
    }

    public Turma getUltimaTurmaAdd() {

        Turma ultima_turma = null;

        for (Turma array : turmas) {
            ultima_turma = array;       //retorno apenas a ultima turma adicionada
        }
        return ultima_turma;

    }

    public Turma getTurma(String nome_da_turma) {

        Turma turma_devolver = null;

        for (Turma array : turmas) {
            if (array.getNome_da_turma().equals(nome_da_turma)) {
                return array;           //retorno apenas a turma que corresponde ao nome passado por referencia
            }
        }
        return null;

    }

    public void atualiza_BD(Turma turma_atualizar, String nome_da_turma) {
        for (Turma array_turma : turmas) //percorre o vetor de turmas
        {
            if (array_turma.getNome_da_turma() == nome_da_turma) //se a turma tiver o mesmo nome da passada por referencia
            {
                turmas.remove(array_turma);     //removo ela
                turmas.add(turma_atualizar);    //add a nova
                break;
            }
        }

    }

    public void salvaDados() {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream("banco_de_dados.ser"));        //tento criar o arquivo
            output.writeObject(turmas);                                                         //e salvo o vetor de turmas
        } catch (IOException e) {
            //erro ao abrir arquivo
        }

    }

    public void leDados() {
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new FileInputStream("banco_de_dados.ser"));   //tento abrir o arquivo
            try {
                turmas = (Collection<Turma>) input.readObject();        //salvo no collection
            } catch (ClassNotFoundException ex) {

            }
        } catch (IOException e) {
            //erro ao abrir arquivo
        }
    }

    public boolean exportar_CSV(String name) {
        Formatter exportar_CSV = null;
        try {
            exportar_CSV = new Formatter(name + ".csv");      //tento criar o arquivo com o nome da turma.csv

        } catch (FileNotFoundException ex) {
            return false;
        }

        //Nomealuno, nota, nota, media /n
        Turma turma_para_exportar = null;
        for (Turma array : turmas) {
            if ((array.getNome_da_turma()).equals(name)) {
                turma_para_exportar = array;            //salvo a turma que tem que exportar
                break;
            }
        }

        for (Aluno array : turma_para_exportar.getAlunos()) {
            exportar_CSV.format("%s", array.getNome_do_aluno());      //nome do aluno, 
            for (Nota array2 : array.getNotas()) {
                exportar_CSV.format("; %s", array2.getNotaDoAluno());//notas do aluno, 
            }
            exportar_CSV.format("; %s", array.getMedia());
            exportar_CSV.format("\n");      //\n para separar
        }
        exportar_CSV.close();
        return true;

    }

    public void ApagaTurma(String nome_da_turma) {
        for(Turma array : turmas)
        {
            if(array.getNome_da_turma().equals(nome_da_turma))
            {
                turmas.remove(array);
                contadora_de_turmas--;
                break;
            }
        }
    }
}
