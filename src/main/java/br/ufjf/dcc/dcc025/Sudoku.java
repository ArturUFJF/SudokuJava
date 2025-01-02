package br.ufjf.dcc.dcc025;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Sudoku {
    private int[][] table;

    public Sudoku() {
        this.table = new int[9][9];
    }

    private static int[][] parseInput(String input, Sudoku table){ //Formata as entradas para receber os valores corretamente
        String[] tuplas = input.split("\\)\\("); //Dá split em cada )( pra separar os grupos;

        tuplas[0] = tuplas[0].substring(1); //Remove parêntese inicial
        tuplas[(tuplas.length) - 1] = tuplas[tuplas.length - 1].substring(0, tuplas[tuplas.length - 1].length() - 1); //Remove parêntese final

        int[][] group = new int[tuplas.length][3]; //Array de arrays com espaço para o número de strings presentes em "tuplas" e informações que serão extraídas de cada uma
        for (int i=0; i<tuplas.length; i++) {
            String[] stringNumbers = tuplas[i].split(",");

            for (int j=0; j<3; j++) {
                group[i][j] = Integer.parseInt(stringNumbers[j]); // Converter cada parte em um inteiro
            }
        }
        return group;
    }

    public static void gameMake(String input, Sudoku table, int option) { //Inicia o jogo de acordo com os inputs
        if (option == 1) {
            int counter = Integer.parseInt(input);
            table.randomAssignValues(counter);
        }

        if (option == 2){
            table.assignValues(parseInput(input, table));
        }
    }

    private void randomAssignValues(int counter) { //Atribui valores aleatórios a posições não repetidas
        Set<String> usedPositions = new HashSet<>(); //Hash de posições já utilizadas

        int i = 0;
        while (i < counter) {
            int randomLine = (int) (Math.random() * 9); //0 a 8
            int randomColumn = (int) (Math.random() * 9); //0 a 8

            String position = randomLine + "," + randomColumn;

            if (!usedPositions.contains(position)) {
                int randomValue = (int) (Math.random() * 9) + 1; //(0 a 8)+1 = 1 a 9

                this.table[randomLine][randomColumn] = randomValue;
                usedPositions.add(position);
                i++;
            }
        }

        printTable();
    }

    private void assignValues(int[][] group) { //Atribui os valores às posições inseridas
        for (int[] info : group){
            this.table[info[0]][info[1]] = info[2];
        }
        printTable();
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
}

