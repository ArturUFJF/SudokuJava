package br.ufjf.dcc.dcc025;
import javax.swing.JOptionPane;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Permite rejogabilidade
        while (true) {

            //Criação do objeto Sudoku
            SudokuGame sudokuGameObject = new SudokuGame();
            int[][] board = sudokuGameObject.getBoard();

            String option = JOptionPane.showInputDialog("Forma de começar o jogo: \n (1) Gerar um jogo aleatório \n (2) Definir o próprio jogo");
            switch (option) {
                case "1": {
                    String stringCounter = JOptionPane.showInputDialog("Quantos números você deseja sortear?");
                    SudokuGame.initializeGame(stringCounter, sudokuGameObject, 1);
                    sudokuGameObject.lockPresetPositions();
                    break;
                }

                case "2": {
                    Scanner scanner = new Scanner(System.in);
                    JOptionPane.showMessageDialog(null, "Definindo seu próprio jogo! (digitar no console)");
                    System.out.println("Insira os valores no formato (linha,coluna,valor), sendo que (-1,-1,-1) encerra a inserção.");

                    while (true) {
                        String input = scanner.nextLine();

                        if (input.equals("(-1,-1,-1)")) {
                            sudokuGameObject.lockPresetPositions();
                            break;
                        }

                        SudokuGame.initializeGame(input, sudokuGameObject, 2);
                        //Input em string é convertido para um array de arrays de informação e o jogo é criado com os valores em cada posição
                    }
                    break;
                }

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida, escolha 1 ou 2!");
                    break;
            }

            while (!SudokuValidator.isEndgame(board)) {
                System.out.println("\nEscolha: \n1) Adicionar jogada \n2) Remover jogada \n3) Verificar\n4) Dica \n5) Sair");
                Scanner ingameScanner = new Scanner(System.in);
                String choice = ingameScanner.nextLine();

                switch (choice) {
                    case "1": {
                        System.out.println("Insira sua(s) jogada(s) no formato (linha,coluna,valor):");
                        String ingameInput = ingameScanner.nextLine();
                        SudokuGame.processPlayerAction(ingameInput, sudokuGameObject, "add");
                        break;
                    }

                    case "2": {
                        System.out.println("Insira a posição de remoção no formato (linha,coluna):");
                        String ingameInput = ingameScanner.nextLine();
                        SudokuGame.processPlayerAction(ingameInput, sudokuGameObject, "remove");
                        break;
                    }

                    case "3": {
                        SudokuValidator.validateGame(true, board);
                        break;
                    }

                    case "4": {
                        System.out.println("Insira a posição em que quer receber a dica no formato (linha,coluna):");
                        String ingameInput = ingameScanner.nextLine();
                        SudokuGame.processPlayerAction(ingameInput, sudokuGameObject, "hint");
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
