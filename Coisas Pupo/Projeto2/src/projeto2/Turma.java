package projeto2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Turma implements Serializable{
    private String nome_da_turma;
    private Collection<Aluno> alunos = new ArrayList<Aluno>();
    
    public Turma(String recebe_nome_da_turma)
    {
        nome_da_turma = recebe_nome_da_turma;
    }
    
    //get and set
    
    public void addAluno(String recebe_nome_aluno, Collection<Nota> nota_nova)
    {
        
        Aluno novo_aluno = new Aluno(recebe_nome_aluno);
        novo_aluno.addNota(nota_nova);
        novo_aluno.calculaMedia();
        alunos.add(novo_aluno);
    }
    
    public String getNome_da_turma()
    {
        return nome_da_turma;
    }
    public void setNome_da_turma(String novo_nome_da_turma)
    {
        nome_da_turma = novo_nome_da_turma;
    }
    public Collection<Aluno> getAlunos()
    {
        return alunos;
    }
    
    
}
