package br.ufjf.dcc.dcc025;
import javax.swing.JOptionPane;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Permite rejogabilidade
        while (true) {

            //Criação do tabuleiro
            Sudoku table = new Sudoku();

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

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida, escolha 1 ou 2!");
                    break;
            }

            while (!table.isEndgame()) {
                System.out.println("\nEscolha: \n1) Adicionar jogada \n2) Remover jogada \n3) Verificar\n4) Dica \n5) Sair");
                Scanner ingameScanner = new Scanner(System.in);
                String choice = ingameScanner.nextLine();

                switch (choice) {
                    case "1": {
                        System.out.println("Insira sua(s) jogada(s) no formato (linha,coluna,valor):");
                        String ingameInput = ingameScanner.nextLine();
                        Sudoku.play(ingameInput, table, "add");
                        break;
                    }

                    case "2": {
                        System.out.println("Insira a posição de remoção no formato (linha,coluna):");
                        String ingameInput = ingameScanner.nextLine();
                        Sudoku.play(ingameInput, table, "remove");
                        break;
                    }

                    case "3": {
                        table.validateGame(true);
                        break;
                    }

                    case "4": {
                        System.out.println("Insira a posição em que quer receber a dica no formato (linha,coluna):");
                        String ingameInput = ingameScanner.nextLine();
                        Sudoku.play(ingameInput, table, "hint");
                        break;
                    }

                    case "5": {
                        System.out.println("Saindo do jogo...");
                        System.exit(0);
                    }

                    default:
                        break;
                }
            }


            System.out.println("\nSudoku finalizado com sucesso, parabéns!");
            System.out.println("Jogar de novo?\n(1) Sim\n(2) Não");

            Scanner replayScanner = new Scanner(System.in);

            while (true) {
                System.out.print("Escolha uma opção: ");
                String escolha = replayScanner.nextLine();

                if (escolha.equals("1")) {
                    System.out.println("Recomeçando...");
                    break;
                } else if (escolha.equals("2")) {
                    System.out.println("Finalizando programa...");
                    System.exit(0);
                } else {
                    System.out.println("Valor inválido, escolha 1 ou 2.");
                }
            }
            }
        }
    }
