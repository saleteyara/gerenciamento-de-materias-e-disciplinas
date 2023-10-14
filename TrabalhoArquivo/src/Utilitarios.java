import java.io.IOException;
import java.util.Scanner;

public class Utilitarios 
{
    static Scanner scan = new Scanner(System.in);
    public static final String RESET = "\u001B[0m", RED = "\u001B[31m", GREEN = "\u001B[32m", WHITE = "\u001B[37m";

    public static String scanValido(String situacao)
    {
        String s = scan.nextLine();

        if(situacao.equals("Double"))
        {
            while(true)
            {
                try 
                {
                    Double.parseDouble(s);
                    break;
                } catch (Exception e) {
                    System.out.println(RED + "Numero Invalido:" + RESET);
                    s = scan.nextLine();
                } 
            }
            return s;
        }
        if(situacao.equals("Integer"))
        {
            while(true)
            {
                try 
                {
                    Integer.parseInt(s);
                    break;
                } catch (Exception e) {
                    System.out.println(RED + "Numero Invalido:" + RESET);
                    s = scan.nextLine();
                } 
            }
            return s;
        }
        if(situacao.equals("String"))
        {
            while(s.equals(""))
            {
                System.out.println(RED + "String Invalida:" + RESET);
                s = scan.nextLine();
            }
            return s;
        }

        return s;
    }

    public static String scanValido(String situacao, double min, double max)
    {
        String s = scan.nextLine();

        while(s.equals(""))
        {
            System.out.println(RED + "String Invalida:" + RESET);
            s = scan.nextLine();
        }

        if(situacao.equals("Double") || s.equals(""))
        {
            while(true)
            {
                try 
                {
                    double nota = Double.parseDouble(s);
                    while(nota < min || nota > max)
                    {
                        System.out.println(RED + "Numero fora do alcance [" + min + ", " + max + "] tente novamente:" + RESET);
                        s = scan.nextLine();
                        nota = Double.parseDouble(s);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(RED + "Numero invalido, tente novamente(ex: 9.8, 10, 7.33):" + RESET);
                    s = scan.nextLine();
                } 
            }

            return s;
        }

    
        if(situacao.equals("Integer") || s.equals(""))
        {   
            while(true)
            {

                try 
                {   
                    int num = Integer.parseInt(s);
                    while(num < min || num > max)
                    {
                        System.out.println(RED + "Inteiro fora do alcance [" + min + ", " + max + "] tente novamente:" + RESET);
                        s = scan.nextLine();
                        num = Integer.parseInt(s);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(RED + "Inteiro invalido, tente novamente:" + RESET);
                    s = scan.nextLine();
                } 
            }
            
            
        }

        return s;
    }

    public static String scanValido(String situacao, double min)
    {
        String s = scan.nextLine();

        while(s.equals(""))
        {
            System.out.println(RED + "String Invalida:" + RESET);
            s = scan.nextLine();
        }

        if(situacao.equals("Double") || s.equals(""))
        {
            while(true)
            {
                try 
                {
                    double nota = Double.parseDouble(s);
                    while(nota < min )
                    {
                        System.out.println(RED + "Numero fora do alcance (<" + min + ")" + " tente novamente:" + RESET);
                        s = scan.nextLine();
                        nota = Double.parseDouble(s);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(RED + "Numero invalido, tente novamente(ex: 9.8, 10, 7.33):" + RESET);
                    s = scan.nextLine();
                } 
            }

            return s;
        }
        if(situacao.equals("Integer") || s.equals(""))
        {   
            while(true)
            {
                try 
                {
                    int num = Integer.parseInt(s);
                    while(num < min)
                    {
                        System.out.println(RED + "Numero fora do alcance (<" + min + ")" + " tente novamente:" + RESET);
                        s = scan.nextLine();
                        num = Integer.parseInt(s);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(RED + "Inteiro invalido, tente novamente:" + RESET);
                    s = scan.nextLine();
                } 
            }
        }

        return s;
    }

    public static void clearScreen()
    {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int simOuNao()
    {
        System.out.println("(1) Sim");
        System.out.println("(2) Nao");
        int click = Integer.parseInt(scanValido("Integer", 1, 2));
        
        return click;
    }

    public static void waitForEnter()
    {
        System.out.println("\n Pressione "+ GREEN + "ENTER" + WHITE +" para continuar");
        scan.nextLine();
    }
}