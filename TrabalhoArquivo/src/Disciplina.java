
import java.util.ArrayList;

public class Disciplina 
{
    private String nome;
    private String gabarito;
    ArrayList<Aluno> alunos = new ArrayList<>();

    public Disciplina( String nome)
    {
        this.nome= nome;
        this.gabarito = "xxxxxxxxxx";
    }

    public String toString()
    {
        return getNome() + " | " + getGabarito();
    }

    public String getGabarito() 
    {
        return gabarito;
    }

    public void setGabarito(String gabarito) 
    {
        this.gabarito = gabarito;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
}

  
