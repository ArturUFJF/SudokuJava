package br.ufjf.dcc.dcc025;
import java.util.*;

public class SudokuGame {
    //Definição de variáveis
    private static int[][] board;
    boolean[][] lockedPositions;

    //Construtor
    public SudokuGame() {
        board = new int[9][9];
        this.lockedPositions = new boolean[9][9];
    }

    //Acesso ao tabuleiro por outros arquivos
    public int[][] getBoard(){
        return board;
    }

    //Inicializa o jogo de acordo com a opção de randomizar ou não
    public static void initializeGame(String input, SudokuGame table, int option) {
        if (option == 1) {
            int counter = Integer.parseInt(input);
            SudokuRandomizer.randomAssignValues(counter, board);
        }

        if (option == 2){
            table.assignValues(SudokuValidator.parseInputs(input,"add"));
        }
    }

    //"Tranca" a alteração a qualquer posição do tabuleiro que foi definida na inicialização do jogo
    public void lockPresetPositions(){
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] != 0) { // Se a posição não for zero, ela está bloqueada porque já recebeu um preset
                    lockedPositions[row][col] = true;
                }
            }
        }
    }

    //Permite a atribuição de valores a posições específicas do tabuleiro
    private void assignValues(int[][] group) {
        for (int[] info : group) {
            // Verifica se não está adicionando em uma posição inválida
            if (!lockedPositions[info[0]][info[1]]) {
                if (info.length == 3) {
                    // Caso de atribuir um valor específico (linha, coluna, valor)
                    board[info[0]][info[1]] = info[2]+1; //+1 porque 1 é decrescido nas posições (inserção de 1 a 9, posições de 0 a 8)
                }

                else if (info.length == 2) {
                    // Caso de remover o valor (linha, coluna)
                    board[info[0]][info[1]] = 0;
                }

                else {
                    // Caso de entrada inválida
                    System.out.println("Entrada inválida: " + Arrays.toString(info));
                }
            }
            else {
                System.out.println("Erro: impossível alterar valores iniciais do tabuleiro!");
        }
                }
        displayBoard();
    }

    //Processa qual a ação escolhida pelo usuário ingame
    public static void processPlayerAction(String input, SudokuGame table, String choice){
        switch (choice){
            case "add": initializeGame(input, table, 2);
            break;

            case "remove": {
                int[][] removePositions = SudokuValidator.parseInputs(input, choice);
                table.assignValues(removePositions);
                break;
            }

            case "hint": {
                int [][] position = SudokuValidator.parseInputs(input, choice);
                table.provideHint(position);
                break;
            }

            default: System.out.println("Escolha inválida!");
            break;
        }
    }

    //Verifica todos os valores possíveis em uma posição específica utilizando validateGame
    public void provideHint(int[][] position){
        if (position.length != 1 || position[0].length != 2) {
            System.out.println("Erro: A posição deve conter exatamente duas coordenadas (linha e coluna).");
            return; // Interrompe o método para evitar comportamentos inesperados
        }

        int line = position[0][0];
        int column = position[0][1];

        int previousValue = board[line][column];
        ArrayList<Integer> possibleValues = new ArrayList<>();

        for (int testValue=1; testValue<10; testValue++){
            board[line][column] = testValue;

            if(SudokuValidator.validateGame(false, board)){
                possibleValues.add(testValue);
            }
        }

        board[line][column] = previousValue;

        System.out.print("Aqui, cabem os valores: ");
        for (int values : possibleValues) {
            System.out.print(values + " ");
        }
    }

    //Imprime o tabuleiro
    public static void displayBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Imprime o valor da célula ou um "." se estiver vazio
                System.out.print((board[i][j] == 0 ? "." : board[i][j]) + " ");
                // Insere divisores verticais entre cada 3x3
                if ((j + 1) % 3 == 0 && j != 8) {
                    System.out.print("| ");
                }
            }
            System.out.println(); // Fim de uma linha

            // Insere divisores horizontais para subgrades
            if ((i + 1) % 3 == 0 && i != 8) {
                System.out.println("---------------------");
            }
        }
    }
}
