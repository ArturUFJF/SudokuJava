package br.ufjf.dcc.dcc025;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Criação do tabuleiro
        Sudoku table = new Sudoku();

        /*String nome = JOptionPane.showInputDialog("Digite seu nome: ");
        JOptionPane.showMessageDialog(null,"Bem-vindo, " + nome + "!");*/

        String option = JOptionPane.showInputDialog("Forma de começar o jogo: \n (1) Gerar um jogo aleatório \n (2) Definir o próprio jogo");
        switch (option) {
            case "1": String sortNumber = JOptionPane.showInputDialog("Quantos números você deseja sortear?");
            break;

            case "2": {
                Scanner scanner = new Scanner(System.in);
                List<int[]> entries = new ArrayList<>();
                JOptionPane.showMessageDialog(null, "Definindo seu próprio jogo! (digitar no console)");
                System.out.println("Insira os valores no formato (linha,coluna,valor), sendo que (-1,-1,-1) encerra a inserção.");

                while (true) {
                    String input = scanner.nextLine();

                    if (input.equals("(-1,-1,-1)")) {
                        break;
                    }

                    Sudoku.gameMake(input, table);
                    //Input em string é convertido para um array de arrays de informação e o jogo é criado com os valores em cada posição
                }
                break;
            }

            default: JOptionPane.showMessageDialog(null,"Opção inválida, escolha 1 ou 2!");
            break;
        }




        }
    }
