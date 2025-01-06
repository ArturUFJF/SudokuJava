package br.ufjf.dcc.dcc025;
import java.util.*;
import java.lang.IllegalArgumentException;

public class Sudoku {
    private int[][] table;

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

    //Inicializa o jogo de acordo com os inputs
    public static void gameMake(String input, Sudoku table, int option) {
        if (option == 1) {
            int counter = Integer.parseInt(input);
            table.randomAssignValues(counter);
        }

        if (option == 2){
            table.assignValues(parseInput(input,"add"));
        }
    }

    //Função para gerar um tabuleiro resolvido padrão
    public void fillSudokuBoard() {
        int[][] solvedTable = {
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
            System.arraycopy(solvedTable[i], 0, this.table[i], 0, 9);
        }
    }



    // Função para remover valores aleatórios do tabuleiro
    public void removeValues(int filledPositions) {
        Random rand = new Random();
        int removedCount = 0;

        while (removedCount < (81 - filledPositions)) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (this.table[row][col] != 0) {
                this.table[row][col] = 0;
                removedCount++;
            }
        }
    }

    private void permuteNumbers() {
        Random rand = new Random();
        int num1 = rand.nextInt(9) + 1; // Número aleatório entre 1 e 9
        int num2;
        do {
            num2 = rand.nextInt(9) + 1;
        } while (num1 == num2); // Certifica-se de que os números são diferentes

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (table[i][j] == num1) {
                    table[i][j] = num2;
                } else if (table[i][j] == num2) {
                    table[i][j] = num1;
                }
            }
        }
    }

    private void permuteRowsInSubgrid() {
        Random rand = new Random();
        int subgrid = rand.nextInt(3); // Escolhe a subgrade (0, 1 ou 2)
        int row1 = subgrid * 3 + rand.nextInt(3); // Linha na subgrade escolhida
        int row2;
        do {
            row2 = subgrid * 3 + rand.nextInt(3);
        } while (row1 == row2);

        int[] temp = table[row1];
        table[row1] = table[row2];
        table[row2] = temp;
    }

    private void permuteColumnsInSubgrid() {
        Random rand = new Random();
        int subgrid = rand.nextInt(3); // Escolhe a subgrade (0, 1 ou 2)
        int col1 = subgrid * 3 + rand.nextInt(3); // Coluna na subgrade escolhida
        int col2;
        do {
            col2 = subgrid * 3 + rand.nextInt(3);
        } while (col1 == col2);

        for (int i = 0; i < 9; i++) {
            int temp = table[i][col1];
            table[i][col1] = table[i][col2];
            table[i][col2] = temp;
        }
    }

    private void permuteRowSubgrids() {
        Random rand = new Random();
        int subgrid1 = rand.nextInt(3); // Escolhe a primeira subgrade (0, 1 ou 2)
        int subgrid2;
        do {
            subgrid2 = rand.nextInt(3);
        } while (subgrid1 == subgrid2);

        for (int i = 0; i < 3; i++) {
            int[] temp = table[subgrid1 * 3 + i];
            table[subgrid1 * 3 + i] = table[subgrid2 * 3 + i];
            table[subgrid2 * 3 + i] = temp;
        }
    }

    private void permuteColumnSubgrids() {
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
                int temp = table[i][col1];
                table[i][col1] = table[i][col2];
                table[i][col2] = temp;
            }
        }
    }

    private void rotateBoard() {
        Random rand = new Random();
        int rotations = rand.nextInt(4); // Número de rotações (0 a 3)

        for (int r = 0; r < rotations; r++) {
            int[][] rotated = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    rotated[j][8 - i] = this.table[i][j];
                }
            }
            this.table = rotated;
        }
    }

    private void reflectBoard() {
        Random rand = new Random();
        int reflectionType = rand.nextInt(4); // Escolhe o tipo de reflexão

        if (reflectionType == 0) { // Refletir horizontalmente
            for (int i = 0; i < 4; i++) {
                int[] temp = table[i];
                table[i] = table[8 - i];
                table[8 - i] = temp;
            }
        } else if (reflectionType == 1) { // Refletir verticalmente
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 4; j++) {
                    int temp = table[i][j];
                    table[i][j] = table[i][8 - j];
                    table[i][8 - j] = temp;
                }
            }
        } else if (reflectionType == 2) { // Refletir diagonal principal
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < i; j++) {
                    int temp = table[i][j];
                    table[i][j] = table[j][i];
                    table[j][i] = temp;
                }
            }
        } else if (reflectionType == 3) { // Refletir diagonal secundária
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9 - i; j++) {
                    int temp = table[i][j];
                    table[i][j] = table[8 - j][8 - i];
                    table[8 - j][8 - i] = temp;
                }
            }
        }
    }

    // Atribui valores aleatórios iniciais ao tabuleiro, com auxílio de outras funções
    private void randomAssignValues(int filledPositions) {
        fillSudokuBoard();
        permuteNumbers();
        permuteRowsInSubgrid();
        permuteColumnsInSubgrid();
        permuteRowSubgrids();
        permuteColumnSubgrids();
        rotateBoard();
        reflectBoard();

        removeValues(filledPositions);
        printTable();
    }

    //Permite a atribuição de valores iniciais para o tabuleiro pelo próprio usuário
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

            case "hint": {
                int [][] position = parseInput(input, choice);
                table.giveHint(position);
                break;
            }

            default: System.out.println("Escolha inválida!");
            break;
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

    public void giveHint(int[][] position){
        int line = position[0][0];
        int column = position[0][1];

        int previousValue = table[line][column];
        ArrayList<Integer> possibleValues = new ArrayList<>();

        for (int testValue=1; testValue<10; testValue++){
            this.table[line][column] = testValue;

            if(validateGame(false)){
                possibleValues.add(testValue);
            }
        }

        table[line][column] = previousValue;

        System.out.print("Aqui, cabem os valores: ");
        for (int values : possibleValues) {
            System.out.print(values + " ");
        }
    }

    public boolean validateGame(boolean showReports) {
        // Verificar linhas
        if (showReports) System.out.println("Relatório de erros:");
        for (int i = 0; i < 9; i++) {
            if (!validateGroup(table[i])) {
                if (showReports) System.out.println("Erro na linha " + i);
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
                if (showReports) System.out.println("Erro na coluna " + j);
                return false;
            }
        }

        // Verificar subgrades 3x3
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!validateGrid(row, col)) {
                    if (showReports) System.out.println("Erro na subgrade começando em (" + row + "," + col + ")");
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
        return isTableFull() && validateGame(false);
    }
}

