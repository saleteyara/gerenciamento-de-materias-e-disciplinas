public class Aluno 
{
    private String nome;
    private double nota;
    private String respostas;

    public Aluno(String nome, String respostas, double nota)
    {
        this.nome = nome;
        this.respostas = respostas;
        this.nota = nota;
    }

    public String toString()
    {
        return "Nome: " + nome + " | Respostas: " + respostas + " | Nota: " + nota;
    }

    // get e set
    public String getRepostas() 
    {
        return respostas;
    }

    public void setRepostas(String repostas) 
    {
        this.respostas = repostas;
    }

    public double getNota() 
    {
        return nota;
    }

    public void setNota(double nota) 
    {
        this.nota = nota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }
    
}
