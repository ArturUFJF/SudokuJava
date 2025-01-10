package br.ufjf.dcc.dcc025;

import java.util.ArrayList;
import java.util.List;

public class SudokuValidator {
    //Validação e interpretação de inputs
    public static int[][] parseInputs(String input, String choice) {
        // Remove caracteres indesejados e verifica o formato
        input = input.replaceAll("[^0-9,()]", ""); // Remove caracteres que não sejam números, vírgulas ou parênteses

        if (!input.startsWith("(") || !input.endsWith(")")) {
            System.out.println("Formato do input está incorreto. Certifique-se de que o input esteja no formato: (linha,coluna,valor)(linha,coluna,valor)... Ignorando entrada.");
            return new int[0][0];
        }

        // Remove os parênteses externos
        input = input.substring(1, input.length() - 1);

        // Divide as tuplas
        String[] tuplas = input.split("\\)\\("); // Cada entrada é separada por `)(`

        List<int[]> validTuplas = new ArrayList<>();

        // Determina o número de elementos esperado por tupla com base na escolha
        int expectedLength = "add".equalsIgnoreCase(choice) ? 3 : 2;

        for (String tupla : tuplas) {
            String[] stringNumbers = tupla.split(",");

            if (stringNumbers.length != expectedLength) {
                // Ignora entradas com número incorreto de elementos
                System.out.println("Entrada com número incorreto de elementos: " + tupla);
                continue;
            }

            try {
                int[] group = new int[expectedLength];

                for (int j = 0; j < stringNumbers.length; j++) {
                    group[j] = Integer.parseInt(stringNumbers[j])-1;

                    // Valida limites para linha e coluna (0 a 8)
                    if (j < 2 && (group[j] < 0 || group[j] > 8)) {
                        throw new IllegalArgumentException("Linha e coluna devem estar entre 1 e 9.");
                    }

                    // Valida o valor apenas para a opção "add" (1 a 9)
                    if (j == 2 && "add".equalsIgnoreCase(choice) && (group[j] < 0 || group[j] > 8)) { //Entre 0 e 8 porque o valor foi decrescido
                        throw new IllegalArgumentException("O valor deve estar entre 1 e 9.");
                    }
                }

                validTuplas.add(group);
            } catch (IllegalArgumentException e) {
                // Ignora entradas inválidas e exibe mensagem de erro
                System.out.println("Entrada inválida: " + tupla + " - " + e.getMessage());
            }
        }

        return validTuplas.toArray(new int[0][0]);
    }


        //Validação de valores possíveis com funções auxiliares

    public static boolean validateGame(boolean showReports, int[][] board) {
        // Verificar linhas
        if (showReports) System.out.println("Relatório de erros:");
        for (int i = 0; i < 9; i++) {
            if (!validateGroup(board[i])) {
                if (showReports) System.out.println("Erro na linha " + (i + 1));
                return false;
            }
        }

        // Verificar colunas
        for (int j = 0; j < 9; j++) {
            int[] column = new int[9];
            for (int i = 0; i < 9; i++) {
                column[i] = board[i][j];
            }
            if (!validateGroup(column)) {
                if (showReports) System.out.println("Erro na coluna " + (j + 1));
                return false;
            }
        }

        // Verificar subgrades 3x3
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!validateGrid(row, col, board)) {
                    if (showReports) System.out.println("Erro na subgrade começando em (" + (row+1) + "," + (col+1) + ")");
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean validateGroup(int[] group) {
        boolean[] seen = new boolean[9];
        for (int value : group) {
            if (value != 0) { // Ignorar células vazias
                if (seen[value - 1]) {
                    return false; // Valor duplicado
                }
                seen[value - 1] = true;
            }
        }
        return true;
    }
    //Valida subgrade 3x3
    private static boolean validateGrid(int startRow, int startCol, int[][] board) {
        boolean[] seen = new boolean[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = board[startRow + i][startCol + j];
                if (value != 0) { // Ignorar células vazias
                    if (seen[value - 1]) {
                        return false; // Valor duplicado
                    }
                    seen[value - 1] = true;
                }
            }
        }
        return true;
    }


    //Verificações de endgame

    private static boolean isBoardFull(int [][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) { // Se encontrar uma célula vazia
                    return false; // Tabela com valores ainda nulos
                }
            }
        }
        return true; // Tabela cheia
    }

    public static boolean isEndgame(int[][] board){
        return isBoardFull(board) && validateGame(false, board);
    }


}
