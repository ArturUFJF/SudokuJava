package br.ufjf.dcc.dcc025;
import java.util.*;
import java.lang.IllegalArgumentException;

public class Sudoku {
    private final int[][] table;

    public Sudoku() {
        this.table = new int[9][9];
    }

    private static int[][] parseInput(String input, String choice) {
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


    public static void gameMake(String input, Sudoku table, int option) { //Inicia o jogo de acordo com os inputs
        if (option == 1) {
            int counter = Integer.parseInt(input);
            table.randomAssignValues(counter);
        }

        if (option == 2){
            table.assignValues(parseInput(input,"add"));
        }
    }

    private List<Integer> getPossibleValues(int row, int col) {
        if (table[row][col] != 0) {
            // Se a posição já está preenchida, nenhum valor é válido
            return Collections.emptyList();
        }

        boolean[] used = new boolean[9]; // Índices 0 a 8 representam os números 1 a 9

        // Verificar linha
        for (int j = 0; j < 9; j++) {
            if (table[row][j] != 0) {
                used[table[row][j] - 1] = true;
            }
        }

        // Verificar coluna
        for (int i = 0; i < 9; i++) {
            if (table[i][col] != 0) {
                used[table[i][col] - 1] = true;
            }
        }

        // Verificar subgrade 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = table[startRow + i][startCol + j];
                if (value != 0) {
                    used[value - 1] = true;
                }
            }
        }

        // Compilar lista de números possíveis
        List<Integer> possibleValues = new ArrayList<>();
        for (int num = 0; num < 9; num++) {
            if (!used[num]) {
                possibleValues.add(num + 1);
            }
        }

        return possibleValues;
    }

    private void randomAssignValues(int counter) {
        List<int[]> positions = new ArrayList<>();

        // Preenche a lista com todas as posições possíveis
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                positions.add(new int[]{row, col});
            }
        }

        // Embaralha as posições
        Collections.shuffle(positions);

        int i = 0;
        for (int[] position : positions) {
            if (i >= counter) break;

            int row = position[0];
            int col = position[1];

            // Obter valores possíveis para a posição
            List<Integer> possibleValues = getPossibleValues(row, col);
            if (!possibleValues.isEmpty()) {
                // Selecionar aleatoriamente um valor possível
                int randomValue = possibleValues.get((int) (Math.random() * possibleValues.size()));
                this.table[row][col] = randomValue;

                i++;
            }
        }

        printTable();
    }

    private void assignValues(int[][] group) {
        for (int[] info : group) {
            if (info.length == 3) {
                // Caso de atribuir um valor específico (linha, coluna, valor)
                this.table[info[0]][info[1]] = info[2]+1; //+1 porque 1 é decrescido nas posições (inserção de 1 a 9, posições de 0 a 8)
            } else if (info.length == 2) {
                // Caso de remover o valor (linha, coluna)
                this.table[info[0]][info[1]] = 0;
            } else {
                // Caso de entrada inválida
                System.out.println("Entrada inválida: " + Arrays.toString(info));
            }
        }
        printTable();

        // Valida o jogo após cada jogada
        if (!validateGame()) {
            System.out.println("ATENÇÃO: A tabela possui erros.");
        } else {
            System.out.println("A tabela está válida.");
        }
    }

    public static void play(String input, Sudoku table, String choice){
        switch (choice){
            case "add": gameMake(input, table, 2);
            break;

            case "remove": {
                int[][] removePositions = parseInput(input, choice);
                table.assignValues(removePositions);
                break;
            }

            default: System.out.println("Escolha inválida!");
            break;
        }

        // Valida o jogo após cada jogada
        if (!table.validateGame()) {
            System.out.println("ATENÇÃO: A tabela possui erros.");
        } else {
            System.out.println("A tabela está válida.");
        }
    }

    private void printTable() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Imprime o valor da célula ou um "." se estiver vazio
                System.out.print((table[i][j] == 0 ? "." : table[i][j]) + " ");
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

    public boolean validateGame() {
        // Verificar linhas
        System.out.println("Relatório de erros:");
        for (int i = 0; i < 9; i++) {
            if (!validateGroup(table[i])) {
                System.out.println("Erro na linha " + i);
                return false;
            }
        }

        // Verificar colunas
        for (int j = 0; j < 9; j++) {
            int[] column = new int[9];
            for (int i = 0; i < 9; i++) {
                column[i] = table[i][j];
            }
            if (!validateGroup(column)) {
                System.out.println("Erro na coluna " + j);
                return false;
            }
        }

        // Verificar subgrades 3x3
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!validateGrid(row, col)) {
                    System.out.println("Erro na subgrade começando em (" + row + "," + col + ")");
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validateGroup(int[] group) {
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

    private boolean validateGrid(int startRow, int startCol) {
        boolean[] seen = new boolean[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = table[startRow + i][startCol + j];
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


    public boolean isTableFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (table[i][j] == 0) { // Se encontrar uma célula vazia
                    return false; // Tabela com valores ainda nulos
                }
            }
        }
        return true; // Tabela cheia
    }

    public boolean isEndgame(){
        return isTableFull() && validateGame();
    }
}

