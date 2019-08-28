package projeto2;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Aluno implements Serializable{
    private String nome_do_aluno;
    private Collection<Nota> notas = new ArrayList<Nota>();
    private Float media;

    public Aluno(String recebe_nome_aluno) {
        
        nome_do_aluno = recebe_nome_aluno;
        media = 0f;
    }
    
    public void addNota(Collection<Nota> nota_nova)
    {
        for(Nota nota : nota_nova )
        {
            notas.add(nota);
        }
    }

    public String getNome_do_aluno() 
    {
        return nome_do_aluno;
    }
    public void setNome_do_aluno(String novo_nome) 
    {
        nome_do_aluno = novo_nome;
    }
    public Float getMedia()
    {
        return media;
    }
    public void setMedia(Float nova_media)
    {
        media = nova_media;
    }
    
    public  Collection<Nota> getNotas()
    {
    		return notas;
    }
    
    public void calculaMedia()
    {
        media = 0f;
        for(Nota array : notas)
        {
            media = media + (array.getNotaDoAluno()*array.getPeso_da_atividade());
        }
    }
    
    
}
