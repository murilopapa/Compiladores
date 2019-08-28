package projeto2;

import java.io.Serializable;

class Nota implements Serializable{

    private String nome_da_atividade;
    private float peso;
    private float nota_do_aluno;

    public Nota(String nome_da_atividade_receber, float peso_receber, float nota_receber) {
        nome_da_atividade = nome_da_atividade_receber;
        peso = peso_receber;
        nota_do_aluno = nota_receber;
    }

    public Nota(String nome_da_atividade_receber, float peso_receber) {
        nome_da_atividade = nome_da_atividade_receber;
        peso = peso_receber;
    }
    public String getNome_da_atividade()
    {
        return nome_da_atividade;
    }
    public void setNome_da_atividade(String novo_nome)
    {
        nome_da_atividade=novo_nome;
    }
    public Float getPeso_da_atividade()
    {
        return peso;
    }
    public void setPeso_da_atividade(Float novo_peso)
    {
        peso=novo_peso;
    }
    public Float getNotaDoAluno()
    {
    		return nota_do_aluno;
    }
    public void setNotaDoAluno(Float nova_nota)
    {
    		nota_do_aluno=nova_nota;
    }
}
