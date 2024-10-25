package com.example.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;

public class SudokuModel implements ISudokuModel {

    // Tablero de juego y tablero con los intentos del jugador
    private ArrayList<ArrayList<Integer>> board, attempt;


    /**
     * SudokuModel
     * Metodo contructor de la clase SudokuModelo
     */
    public SudokuModel() {
        board = new ArrayList<>();
        attempt = new ArrayList<>();
        generateSudoku();
        generateEmptySudoku();
    }

    /**
     * getBoard() devuelve el sudoku completado
     * @return la matriz 6x6 board
     */
    @Override
    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    /**
     * getAttempt() devuelve el sudoku que ingreso el usuario
     * @return la matriz 6x6 attempt
     */
    public ArrayList<ArrayList<Integer>> getAttempt() {
        return attempt;
    }

    /**
     * generateSudoku, crea un una matriz board 6x6 de Integers con ArrayList y la llena con ceros
     * posteriormente se llena con valores de un sudoku completado y se devuelce
     * @return devuelve el sudoku resuelto
     */
    @Override
    public ArrayList<ArrayList<Integer>> generateSudoku() {
        for (int i = 0; i < 6; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 6; j++) {
                board.get(i).add(0); // Llena el tablero con ceros temporalmente
            }
        }

        fillBoard(0, 0); //Se llena el sudoku

        return board; // Devuelve el tablero generado
    }

    /**
     * generateEmptySoduku , crea un una matriz attempt 6x6 de Integers con ArrayList y la llena con ceros
     *@return devuelve la matriz con ceros
     */
    public ArrayList<ArrayList<Integer>> generateEmptySudoku() {
        for (int i = 0; i < 6; i++) {
            attempt.add(new ArrayList<>());
            for (int j = 0; j < 6; j++) {
                attempt.get(i).add(0); // Llena el tablero con ceros temporalmente
            }
        }
        return attempt;
    }

    /**
     * fillBoard(int row, int col)
     * Llena el tablero de Sudoku 6x6 utilizando backtracking, colocando números del 1 al 6 en
     * celdas vacías de forma aleatoria y validando su posición según las reglas del Sudoku.
     * @param row fila actual
     * @param col columna actual
     * @return true si el tablero se llena correctamente, false si no es posible llenar la celda.
     */
    private boolean fillBoard(int row, int col) {
        // Si llegamos al final del tablero, hemos terminado
        if (row == 6) {
            return true;
        }

        // Si llegamos al final de una fila, pasamos a la siguiente
        int nextRow = (col == 5) ? row + 1 : row;
        int nextCol = (col == 5) ? 0 : col + 1;

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers); // Mezclar los números para hacer la generación aleatoria

        // Probar cada número en la celda actual
        for (int num : numbers) {
            if (isValidPlacement(row, col, num)) {
                board.get(row).set(col, num); // Coloca el número

                // Llamada recursiva para intentar llenar la siguiente celda
                if (fillBoard(nextRow, nextCol)) {
                    return true; // Si se llena correctamente el resto del tablero, retorna verdadero
                }

                // Si falla, deshacemos el número colocado
                board.get(row).set(col, 0);
            }
        }
        return false; // No se pudo colocar un número válido en esta celda
    }

    /**
     * isValidPlacement(int row, int col, int num)
     * Verifica si un número puede colocarse en una celda específica, asegurándose de que no se
     * repita en la misma fila, columna o subcuadrícula de 2x3.
     * @param row Fila de la celda.
     * @param col Columna de la celda
     * @param num  Número a colocar.
     * @return true si el número puede colocarse, false en caso contrario.
     */
    @Override
    public boolean isValidPlacement(int row, int col, int num) {
        // Verificar la fila
        for (int i = 0; i < 6; i++) {
            if (board.get(row).get(i) == num) {
                return false;
            }
        }

        // Verificar la columna
        for (int i = 0; i < 6; i++) {
            if (board.get(i).get(col) == num) {
                return false;
            }
        }

        // Verificar la subcuadrícula de 2x3
        int boxRowStart = (row / 2) * 2; // Encuentra el inicio de la subcuadrícula
        int boxColStart = (col / 3) * 3;
        for (int i = boxRowStart; i < boxRowStart + 2; i++) {
            for (int j = boxColStart; j < boxColStart + 3; j++) {
                if (board.get(i).get(j) == num) {
                    return false;
                }
            }
        }
        return true; // El número puede ser colocado
    }

    /**
     * isCorrectValue(int row, int col, int num)
     * Comprueba si el número en una celda específica coincide con el valor correcto del tablero.
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     * @param num Número a verificar.
     * @return true si el número es correcto, false si es incorrecto.
     */
    public boolean isCorrectValue(int row, int col, int num) {
        return board.get(row).get(col) == num;
    }

    /**
     * Imprime el tablero de sudoku en la consola
     */
    @Override
    public void printBoard() {
        for (ArrayList<Integer> row : board) {
            System.out.println(row);
        }
    }

    @Override
    public void printAttempt() {
        for (ArrayList<Integer> row : attempt) {
            System.out.println(row);
        }
    }
}
