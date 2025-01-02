package br.ufjf.dcc.dcc025;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Criação do tabuleiro
        Sudoku table = new Sudoku();
        boolean endgame = false;

        String option = JOptionPane.showInputDialog("Forma de começar o jogo: \n (1) Gerar um jogo aleatório \n (2) Definir o próprio jogo");
        switch (option) {
            case "1": {
                String stringCounter = JOptionPane.showInputDialog("Quantos números você deseja sortear?");
                Sudoku.gameMake(stringCounter, table, 1);
                break;
            }

            case "2": {
                Scanner scanner = new Scanner(System.in);
                JOptionPane.showMessageDialog(null, "Definindo seu próprio jogo! (digitar no console)");
                System.out.println("Insira os valores no formato (linha,coluna,valor), sendo que (-1,-1,-1) encerra a inserção.");

                while (true) {
                    String input = scanner.nextLine();

                    if (input.equals("(-1,-1,-1)")) {
                        break;
                    }

                    Sudoku.gameMake(input, table, 2);
                    //Input em string é convertido para um array de arrays de informação e o jogo é criado com os valores em cada posição
                }
                break;
            }

            default: JOptionPane.showMessageDialog(null,"Opção inválida, escolha 1 ou 2!");
            break;
        }

        while (!endgame){
            System.out.println("Insira sua(s) jogada(s) no formato (linha,coluna,valor):");
            Scanner ingameScanner = new Scanner(System.in);
            String ingameInput = ingameScanner.nextLine();
            Sudoku.gameMake(ingameInput, table, 2);

            endgame = table.isTableFull();
        }

        System.out.println("\nSudoku finalizado com sucesso!");
        }
    }
