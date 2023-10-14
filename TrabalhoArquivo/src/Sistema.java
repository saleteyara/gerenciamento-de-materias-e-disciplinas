
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Sistema 
{
    ArrayList<Disciplina> disciplinas = new ArrayList<>();
    boolean continuar = true;

    public static final String RESET = "\u001B[0m", RED = "\u001B[31m", GREEN = "\u001B[32m",
    YELLOW = "\u001B[33m",BLUE = "\u001B[34m", PURPLE = "\u001B[35m",  CYAN= "\u001B[36m", WHITE_BOLD = "\033[1;97m",
    RED_BACKGROUND = "\u001B[41m", GREEN_BACKGROUND = "\u001B[42m", YELLOW_BACKGROUND = "\u001B[43m",
    BLUE_BACKGROUND = "\u001B[44m", PURPLE_BACKGROUND = "\u001B[45m",_BACKGROUND = "\u001B[46m",WHITE_BACKGROUND = "\u001B[47m",
    WHITE = "\u001B[37m", BLACK = "\u001B[30m"; 

    public void executar() throws IOException
    {
        // lendo todos os arquivos de disciplina
        new File("src/disciplinas").mkdirs();
        File arquivosDisciplina = new File("src/disciplinas");
        String pathDisciplinas[] = arquivosDisciplina.list();

        new File("src/gabaritos").mkdirs();
        File arquivosGabarito = new File("src/gabaritos");
        String pathsGabaritos[] = arquivosGabarito.list();

        for (String fileName : pathDisciplinas) 
        {
            String nome_txt[] = fileName.split("\\.");
            disciplinas.add(new Disciplina(nome_txt[0]));
        }

        for (String path : pathsGabaritos) // ler arquivos de gabarito
        {
            System.out.println(path);
            BufferedReader bf = new BufferedReader(new FileReader("src/gabaritos/" + path)); 
            String nome_txt[] = path.split("\\.");

            String line = bf.readLine();

            /*if(getIndice(nome_txt[0]) == -1) // se a disciplina nao existir
            {
                System.out.println("Gabarito com nome de disciplina: " + nome_txt[0] + " foi encontrado mas nao foi encontrada disciplina correspondente!");
                System.out.println("Deseja criar uma disciplina para esse gabarito?");
                int click = Utilitarios.simOuNao();
                if(click == 1)
                    addDisciplina(nome_txt[0]);
                Utilitarios.clearScreen();
            }*/

            while(!gabaritoValido(line, true)) // se o gabarito nao estiver na forma valida
            {
                System.out.println(RED + "Arquivo de gabarito invalido!" + RESET);
                System.out.println("O Gabarito deve conter 10 respostas de 'V' ou 'F' ");
                System.out.println("Digite o gabarito novamente: ");
                line = Utilitarios.scanValido("String");

                FileWriter fw = new FileWriter("src/gabaritos/" + path, false);
                fw.append(line);
                fw.close();
            }

            disciplinas.get(getIndice(nome_txt[0])).setGabarito(line);

            bf.close();
        }

        for (String path : pathDisciplinas) // ler arquivos de disciplina
        {
            BufferedReader bf = new BufferedReader(new FileReader("src/disciplinas/" + path)); 

            String nome_txt[] = path.split("\\.");
            int i = getIndice(nome_txt[0]);

            String line = bf.readLine();
            while ((line = bf.readLine()) != null)
            {
                String dados[] = line.split("\t");
                disciplinas.get(i).alunos.add(new Aluno(dados[1], dados[0], calcularNota(dados[0], disciplinas.get(i))));
            }
            bf.close();
        }
        
        while(continuar)
        {
            menu();
        }
    }   

    public void menu() throws IOException
    {
        Utilitarios.clearScreen();
        System.out.print("\n\n------------------------------------------------------------------\n");
        System.out.print("Digite o número da operação desejada:\n\n");
        System.out.println("("+YELLOW+"1"+RESET+") Criar disciplina");
        System.out.println("("+YELLOW+"2"+RESET+") Adicionar respostas de alunos");
        System.out.println("("+YELLOW+"3"+RESET+") Menu das Disciplinas");
        System.out.println("("+YELLOW+"4"+RESET+") Sair");
        int opcao = Integer.parseInt(Utilitarios.scanValido("Integer", 1, 4));

        switch(opcao) 
        {
            case 1:
                addDisciplina();
                break;
            case 2:
                addAluno();
                break;
            case 3:
                menuDisciplinas();
                break;
            case 4:
                continuar = false;
                break;         
        }
    }

    public boolean addAluno() throws IOException
    {
        try{
            disciplinas.get(0);
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Nenhuma disciplina cadastrada! Deseja cadastrar?");
            int click = Utilitarios.simOuNao();
            if(click == 1)
            {
                addDisciplina();
                return false;
            }
            Utilitarios.waitForEnter();
            return false;
        }

        System.out.println("Digite o numero de alunos a cadastrar: ");
        int num_alunos = Integer.parseInt(Utilitarios.scanValido("Integer", 0));
        
        if(num_alunos == 0)
        {
            System.out.println(" Volte ao Menu Principal");
            Utilitarios.waitForEnter();
            Utilitarios.clearScreen();
        }else{
            System.out.println("Qual nome da disciplina?" + RESET);
            String nomeDisciplina = getNomeValido();

            for(int i = 1; i <= num_alunos; i++)
            {
                Utilitarios.clearScreen();
                System.out.print("\n\n------------------------------------------------------------------\n");

                System.out.print("Qual nome do "+ i +"° aluno a ser adicionado?\n" + RESET);
                String nome = Utilitarios.scanValido("String");

                System.out.print("Quais foram as respostas dos aluno?\n" + RESET);
                String respostas = Utilitarios.scanValido("String");
                while(!gabaritoValido(respostas, false))
                {
                    System.out.println("Respostas invalidas! A resposta deve conter 10 respostas com 'V' e 'F'");
                    System.out.println("Digite as respostas novamente:");
                    respostas = Utilitarios.scanValido("String");
                }

                disciplinas.get(getIndice(nomeDisciplina)).alunos.add(new Aluno(nome, respostas, calcularNota(respostas, disciplinas.get(getIndice(nomeDisciplina)))));
                
                FileWriter fw = new FileWriter("src/disciplinas/" + nomeDisciplina + ".txt", true);
                fw.write(respostas +"\t" + nome + "\n");
                fw.close();
            }
            System.out.println(GREEN+ "\n Cadastro realizado com sucesso!" + RESET);
            Utilitarios.waitForEnter();
            Utilitarios.clearScreen();
        }
        return true;
    }

    public boolean addDisciplina() throws IOException
    {
        Utilitarios.clearScreen();
        System.out.print("Qual nome da nova disciplina?\n" + RESET);
        String nome = Utilitarios.scanValido("String");

        if(getIndice(nome) != -1)
        {
            System.out.println(RED + "Disciplina já existe!" + RESET);
            Utilitarios.waitForEnter();
            Utilitarios.clearScreen();
            return false;
        }

        disciplinas.add(new Disciplina(nome));
        FileWriter fw = new FileWriter("src/disciplinas/" + nome + ".txt", false);
        fw.write(nome +"\n");
        fw.close();
        criarGabarito(nome);
        System.out.println(GREEN + "Disciplina " + nome + " adicionada com sucesso!" + RESET);
        Utilitarios.waitForEnter();
        Utilitarios.clearScreen();
        return true;
    }

    public boolean addDisciplina(String nomeDisciplina) throws IOException
    {
        Utilitarios.clearScreen();

        FileWriter fw = new FileWriter("src/disciplinas/" + nomeDisciplina + ".txt", false);
        fw.write(nomeDisciplina +"\n");
        fw.close();

        disciplinas.add(new Disciplina(nomeDisciplina));
        System.out.println(GREEN+"Disciplina adicionada com sucesso!\n"+RESET);
        Utilitarios.waitForEnter();
        return true;
        
    }

    public boolean criarGabarito(String nome) throws IOException 
    {
        Utilitarios.clearScreen();
        System.out.println("Deseja criar um gabarito para " + nome + "?");
        System.out.println("("+YELLOW+"1"+RESET+") Sim");
        System.out.println("("+YELLOW+"2"+RESET+") Nao");

        int click = Integer.parseInt(Utilitarios.scanValido("Integer", 1, 2));
        if (click == 2)
            return false;     

        FileWriter fw = new FileWriter("src/gabaritos/" + nome + ".txt", false);

        Utilitarios.clearScreen();
        System.out.println("Digite o gabarito da prova: ");
        String gabarito = Utilitarios.scanValido("String");

        while(!gabaritoValido(gabarito, true))
        {
            System.out.println(RED + "Gabarito Invalido! O Gabarito deve conter 10 respostas com 'V' e 'F' " + RESET);
            System.out.println("Digite o gabarito novamente:");
            gabarito = Utilitarios.scanValido("String");
        }

        disciplinas.get(getIndice(nome)).setGabarito(gabarito);
        fw.write(gabarito);
        fw.close();
        System.out.println(GREEN + "Arquivo de respostas criado com sucesso!" + RESET);
        Utilitarios.waitForEnter();
        Utilitarios.clearScreen();

        return false;
    }

    public boolean criarGabarito() throws IOException 
    {
        try{
            disciplinas.get(0);
        } catch(IndexOutOfBoundsException e) {
            System.out.println(RED+"Nenhuma disciplina cadastrada!\n" + RESET+" Deseja cadastrar uma disciplina?");
            int click = Utilitarios.simOuNao();
            if(click == 1)
            {
                addDisciplina();
                return false;
            }
        }

        System.out.print("\nDigite o nome da disciplina para criar um gabarito (Gabarito já existente sera alterado):");
        String nome = getNomeValido();

        FileWriter fw = new FileWriter("src/gabaritos/" + nome + ".txt", false);

        System.out.println("Digite o gabarito da prova: ");
        String gabarito = Utilitarios.scanValido("String");
        while(!gabaritoValido(gabarito, true))
        {
            System.out.println(RED + "Gabarito Invalido! O Gabarito deve conter 10 respostas com 'V' e 'F' " + RESET);
            System.out.println("Digite o gabarito novamente:");
            gabarito = Utilitarios.scanValido("String");
        }

        disciplinas.get(getIndice(nome)).setGabarito(gabarito);
        fw.write(gabarito);

        fw.close();
        System.out.println(GREEN + "Arquivo de respostas criado com sucesso!" + RESET);
        Utilitarios.waitForEnter();
        Utilitarios.clearScreen();

        return false;
    }

    public boolean menuDisciplinas() throws IOException
    {
        Utilitarios.clearScreen();
        try{
            disciplinas.get(0);
        }
        catch(IndexOutOfBoundsException e)
        {
            System.out.println(RED+"Nenhuma disciplina cadastrada!\n" + RESET+" Deseja cadastrar uma disciplina?");
            System.out.println("("+YELLOW+"1"+RESET+") Sim");
            System.out.println("("+YELLOW+"2"+RESET+") Nao");
            int click = Integer.parseInt(Utilitarios.scanValido("Integer", 1, 2));
            if(click == 1)
                addDisciplina();
            else
                return false;
        }
        boolean prosseguir = true;
        while(prosseguir)
        {
            Utilitarios.clearScreen();
            System.out.print("\n\n------------------------------------------------------------------\n");
            System.out.print("Digite o número da operação desejada:\n\n");
            System.out.println("("+YELLOW+"1"+RESET+") Mostrar Gabarito oficial");
            System.out.println("("+YELLOW+"2"+RESET+") Alunos por ordem alfabetica ");
            System.out.println("("+YELLOW+"3"+RESET+") Alunos por ordem de notas");
            System.out.println("("+YELLOW+"4"+RESET+") Criar Gabarito");
            System.out.println("("+YELLOW+"5"+RESET+") Voltar menu principal");
            int subopcao = Integer.parseInt(Utilitarios.scanValido("Integer", 1, 5));

            switch (subopcao) {
                case 1:  
                    mostrarGabarito(); 
                    break;
                case 2:
                    alunosOrdenadosAlfabetico();
                    break;
                case 3:
                    alunosOrdenadosNota();
                    break;
                case 4:
                    criarGabarito();
                    break;
                case 5:
                    prosseguir = false;
                    break;  
            }
        }
        return true;
    }

    private boolean alunosOrdenadosAlfabetico() throws IOException 
    {
        System.out.println("Digite o nome da disciplina:");
        String nomeDisciplina = getNomeValido();
        int k = getIndice(nomeDisciplina);

        if(disciplinas.get(k).alunos.size() == 0)
        {
            System.out.println(GREEN + "Adicione alguns alunos antes de olhar a lista!" + RESET);
            Utilitarios.waitForEnter();
            Utilitarios.clearScreen();
            return false;
        }

        if(disciplinas.get(k).getGabarito() == "xxxxxxxxxx")
        {
            System.out.println(RED+"Disciplina nao possui gabarito para gerar notas!" + RESET);
            Utilitarios.waitForEnter();
            return false;
        }

        double soma = 0;
        for (Aluno aluno : disciplinas.get(k).alunos) 
        {
            aluno.setNota(calcularNota(aluno.getRepostas(), disciplinas.get(k)));
            soma += aluno.getNota();
        }
        
        for (int i = 0; i < disciplinas.get(k).alunos.size()-1; i++) 
        {
            for (int j = i+1; j < disciplinas.get(k).alunos.size(); j++) 
            {
                if(disciplinas.get(k).alunos.get(i).getNome().compareTo(disciplinas.get(k).alunos.get(j).getNome()) > 0)
                {
                    Aluno temp = disciplinas.get(k).alunos.get(i);
                    disciplinas.get(k).alunos.set(i, disciplinas.get(k).alunos.get(j));
                    disciplinas.get(k).alunos.set(j, temp);
                }
            }
        }

        new File("src/resultados_alfabetica").mkdirs();
        FileWriter fw = new FileWriter("src/resultados_alfabetica/" + nomeDisciplina + ".txt", false);
        fw.write(nomeDisciplina +"\n");
        fw.close();
        
        FileWriter fw2 = new FileWriter("src/resultados_alfabetica/" + nomeDisciplina + ".txt", true);
        System.out.println("========== Lista de Alunos ordem Alfabetica ==========");
        for (Aluno a : disciplinas.get(k).alunos) 
        {
            fw2.append(a.toString() + "\n");
            System.out.println(a.toString());
        }
        System.out.println("======================================================");
        fw2.append("Media da turma: " + soma/(disciplinas.get(k).alunos.size()));
        System.out.println(GREEN+"Media da Turma: " + RESET);
        fw2.close();
        System.out.print(soma/(disciplinas.get(k).alunos.size()) + "\n");

        Utilitarios.waitForEnter();

        return true;
    }

    private boolean alunosOrdenadosNota() throws IOException 
    {
        System.out.println("Digite o nome da disciplina:");
        String nomeDisciplina = getNomeValido();
        int k = getIndice(nomeDisciplina);

        if(disciplinas.get(k).alunos.size() == 0)
        {
            System.out.println(GREEN + "Adicione alguns alunos antes de olhar a lista!" + RESET);
            Utilitarios.waitForEnter();
            Utilitarios.clearScreen();
            return false;
        }

        if(disciplinas.get(k).getGabarito() == "xxxxxxxxxx")
        {
            System.out.println(RED+"Disciplina nao possui gabarito para gerar notas!");
            Utilitarios.waitForEnter();
            return false;
        }

        double soma = 0;
        for (Aluno aluno : disciplinas.get(k).alunos) 
        {
            aluno.setNota(calcularNota(aluno.getRepostas(), disciplinas.get(k)));
            soma += aluno.getNota();
        }

        for (int i = 0; i < disciplinas.get(k).alunos.size()-1; i++) 
        {
            for (int j = i+1; j < disciplinas.get(k).alunos.size(); j++) 
            {
                if(disciplinas.get(k).alunos.get(i).getNota() < disciplinas.get(k).alunos.get(j).getNota())
                {
                    Aluno temp = disciplinas.get(k).alunos.get(i);
                    disciplinas.get(k).alunos.set(i, disciplinas.get(k).alunos.get(j));
                    disciplinas.get(k).alunos.set(j, temp);
                }
            }
        }

        new File("src/resultados_nota").mkdirs();
        FileWriter fw = new FileWriter("src/resultados_nota/" + nomeDisciplina + ".txt", false);
        fw.write(nomeDisciplina +"\n");
        fw.close();

        FileWriter fw2 = new FileWriter("src/resultados_nota/" + nomeDisciplina + ".txt", true);
        System.out.println("========== Lista de Alunos ordem de Nota ==========");
        for (Aluno a : disciplinas.get(k).alunos) 
        {
            fw2.append(a.toString() + "\n");
            System.out.println(a.toString());
        }
        System.out.println("======================================================");
        fw2.append("Media da turma: " + soma/(disciplinas.get(k).alunos.size()));
        System.out.println(GREEN+"Media da Turma: " + RESET);
        fw2.close();
        System.out.print(soma/(disciplinas.get(k).alunos.size()) + "\n");

        Utilitarios.waitForEnter();

        return true;
    }

    public boolean mostrarGabarito() throws IOException
    {
        Utilitarios.clearScreen();
        System.out.println("Qual disciplina deseja ver o gabarito?" + RESET);
        String nomeDisciplina = getNomeValido();

        if(disciplinas.get(getIndice(nomeDisciplina)).getGabarito().equals("xxxxxxxxxx"))
        {
            System.out.println("Disciplina sem gabarito cadastrado! \n Deseja criar um gabarito?");
            int click = Utilitarios.simOuNao();
            if(click == 1)
            {
                criarGabarito(nomeDisciplina);
                return false;
            }
        }
        System.out.println("Gabarito de " + nomeDisciplina + ": ");
        System.out.println(disciplinas.get(getIndice(nomeDisciplina)).getGabarito());

        Utilitarios.waitForEnter();
        return true;
    }

    public String getNomeValido()
    {
        String nomeDisciplina = Utilitarios.scanValido("String");
        while(getIndice(nomeDisciplina) == -1)
        {
            System.out.println(RED + "Disciplina nao encontrada! \n" + RESET);
            System.out.println("Lista de disciplinas cadastradas:");
            for (int i = 0; i < disciplinas.size(); i++) 
            {
                System.out.print(disciplinas.get(i).toString() + "\n");
            }
            System.out.println("Tente novamente:");
            nomeDisciplina = Utilitarios.scanValido("String");
        }
        return nomeDisciplina;
    }

    public int getIndice(String nome)
    {
        for (int i = 0; i < disciplinas.size(); i++) 
        {
            if (disciplinas.get(i).getNome().equals(nome)) 
            {
                return i;
            }
        }
        return -1;  
    }

    public boolean gabaritoValido(String gabarito, boolean isGabarito)
    {
        if(gabarito.length() != 10)
            return false;
    
        boolean tudoIgual = false;
        for (int j = 0; j < gabarito.length(); j++) 
        {
            if(gabarito.charAt(0) == gabarito.charAt(j))
                tudoIgual = true;
            else
            {
                tudoIgual = false;
                break;
            }
        }

        if(tudoIgual && isGabarito)
            return false;

        for (int i = 0; i < gabarito.length(); i++) 
        {
            if(gabarito.charAt(i) != 'V' && gabarito.charAt(i) != 'F')
                return false;
        }
    
        return true;
    }

    public int calcularNota(String respostas, Disciplina disciplina)
    {
        String gabarito = disciplina.getGabarito();

        int nota = 0; 
        String todasVerdadeiras = "VVVVVVVVVV";
        String todasFalsas = "FFFFFFFFFF";

        if (todasFalsas.equals(respostas) || todasVerdadeiras.equals(respostas)) 
        { 
            nota = 0;
        }
        else
        {
            for (int i = 0; i < gabarito.length(); i++) 
            {
                if(gabarito.charAt(i) == respostas.charAt(i))
                    nota++;
            }
        }
        return nota;
    }
}