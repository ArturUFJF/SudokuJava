package br.ufjf.dcc.dcc025;

public class Sudoku {
    private int[][] table;

    public Sudoku() {
        this.table = new int[9][9];
    }

    public static void gameMake(String input, Sudoku table) { //Formata as entradas para receber os valores corretamente
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

        table.assignValues(group);
    }

    private void assignValues(int[][] group) {
        for (int[] info : group){
            this.table[info[0]][info[1]] = info[2];
        }
    }
}
