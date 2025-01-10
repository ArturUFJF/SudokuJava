package br.ufjf.dcc.dcc025;
import java.util.Random;

public class SudokuRandomizer {

    //Função para gerar um tabuleiro resolvido padrão
    private static void fillSudokuBoard(int[][] board) {
        int[][] solvedBoard = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        for (int i = 0; i < 9; i++) {
            System.arraycopy(solvedBoard[i], 0, board[i], 0, 9);
        }
    }

    // Função para remover valores aleatórios do tabuleiro
    public static void removeRandomValues(int filledPositions, int[][] board) {
        Random rand = new Random();
        int removedCount = 0;

        while (removedCount < (81 - filledPositions)) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                removedCount++;
            }
        }
    }

    //Permuta todas as ocorrências de um número por outro
    private static void permuteNumbers(int[][] board) {
        Random rand = new Random();
        int num1 = rand.nextInt(9) + 1; // Número aleatório entre 1 e 9
        int num2;
        do {
            num2 = rand.nextInt(9) + 1;
        } while (num1 == num2); // Certifica-se de que os números são diferentes

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == num1) {
                    board[i][j] = num2;
                } else if (board[i][j] == num2) {
                    board[i][j] = num1;
                }
            }
        }
    }

    //Permuta linhas da mesma subgrade
    private static void permuteRowsInSubgrid(int[][] board) {
        Random rand = new Random();
        int subgrid = rand.nextInt(3); // Escolhe a subgrade (0, 1 ou 2)
        int row1 = subgrid * 3 + rand.nextInt(3); // Linha na subgrade escolhida
        int row2;
        do {
            row2 = subgrid * 3 + rand.nextInt(3);
        } while (row1 == row2);

        int[] temp = board[row1];
        board[row1] = board[row2];
        board[row2] = temp;
    }

    //Permuta colunas da mesma subgrade
    private static void permuteColumnsInSubgrid(int[][] board) {
        Random rand = new Random();
        int subgrid = rand.nextInt(3); // Escolhe a subgrade (0, 1 ou 2)
        int col1 = subgrid * 3 + rand.nextInt(3); // Coluna na subgrade escolhida
        int col2;
        do {
            col2 = subgrid * 3 + rand.nextInt(3);
        } while (col1 == col2);

        for (int i = 0; i < 9; i++) {
            int temp = board[i][col1];
            board[i][col1] = board[i][col2];
            board[i][col2] = temp;
        }
    }

    //Permuta subgrades de linhas diferentes
    private static void permuteRowSubgrids(int[][] board) {
        Random rand = new Random();
        int subgrid1 = rand.nextInt(3); // Escolhe a primeira subgrade (0, 1 ou 2)
        int subgrid2;
        do {
            subgrid2 = rand.nextInt(3);
        } while (subgrid1 == subgrid2);

        for (int i = 0; i < 3; i++) {
            int[] temp = board[subgrid1 * 3 + i];
            board[subgrid1 * 3 + i] = board[subgrid2 * 3 + i];
            board[subgrid2 * 3 + i] = temp;
        }
    }

    //Permuta subgrades de colunas diferentes
    private static void permuteColumnSubgrids(int[][] board) {
        Random rand = new Random();
        int subgrid1 = rand.nextInt(3); // Escolhe a primeira subgrade (0, 1 ou 2)
        int subgrid2;
        do {
            subgrid2 = rand.nextInt(3);
        } while (subgrid1 == subgrid2);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                int col1 = subgrid1 * 3 + j;
                int col2 = subgrid2 * 3 + j;
                int temp = board[i][col1];
                board[i][col1] = board[i][col2];
                board[i][col2] = temp;
            }
        }
    }

    //Rotaciona o tabuleiro
    private static void rotateBoard(int[][] board) {
        Random rand = new Random();
        int rotations = rand.nextInt(4); // Número de rotações (0 a 3)

        for (int r = 0; r < rotations; r++) {
            int[][] rotated = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    rotated[j][8 - i] = board[i][j];
                }
            }
            board = rotated;
        }
    }

    //"Espelha" o tabuleiro de formas diferentes
    private static void reflectBoard(int[][] board) {
        Random rand = new Random();
        int reflectionType = rand.nextInt(4); // Escolhe o tipo de reflexão

        if (reflectionType == 0) { // Refletir horizontalmente
            for (int i = 0; i < 4; i++) {
                int[] temp = board[i];
                board[i] = board[8 - i];
                board[8 - i] = temp;
            }
        } else if (reflectionType == 1) { // Refletir verticalmente
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 4; j++) {
                    int temp = board[i][j];
                    board[i][j] = board[i][8 - j];
                    board[i][8 - j] = temp;
                }
            }
        } else if (reflectionType == 2) { // Refletir diagonal principal
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < i; j++) {
                    int temp = board[i][j];
                    board[i][j] = board[j][i];
                    board[j][i] = temp;
                }
            }
        } else { // Refletir diagonal secundária
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9 - i; j++) {
                    int temp = board[i][j];
                    board[i][j] = board[8 - j][8 - i];
                    board[8 - j][8 - i] = temp;
                }
            }
        }
    }

    // Atribui valores aleatórios iniciais ao tabuleiro, com auxílio de outras funções
    public static void randomAssignValues(int filledPositions, int[][] board) {
        //Atribui valor fixo inicial
        fillSudokuBoard(board);

        //Operações de embaralhamento
        permuteNumbers(board);
        permuteRowsInSubgrid(board);
        permuteColumnsInSubgrid(board);
        permuteRowSubgrids(board);
        permuteColumnSubgrids(board);
        rotateBoard(board);
        reflectBoard(board);

        //Remove 81-filledPositions valores do tabuleiro completo
        removeRandomValues(filledPositions, board);
        SudokuGame.displayBoard();
    }
}
