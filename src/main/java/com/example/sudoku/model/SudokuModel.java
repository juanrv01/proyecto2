package com.example.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * La clase SudokuModel implementa la lógica del juego Sudoku,
 * incluyendo la generación de un tablero resuelto y vacío,
 * así como la validación de las entradas del jugador.
 *
 * @version 1.0
 * @since 2024-10-23
 *
 * @author Juan Pablo Charry Ramirez
 * @author Juan Esteban Rodriguez Valencia
 */
public class SudokuModel implements ISudokuModel {

    // Tablero de juego y tablero con los intentos del jugador
    private ArrayList<ArrayList<Integer>> board, attempt;

    // Matriz con la solución completa del Sudoku


    /**
     * Constructor de la clase SudokuModel. Inicializa el tablero de juego, el tablero
     * con los intentos del jugador y la matriz con la solución completa.
     */
    public SudokuModel() {
        board = new ArrayList<>();
        attempt = new ArrayList<>();
        generateSudoku(); // Generar la solución
        generateEmptySudoku(); // Generar el tablero vacío para jugar
    }

    /**
     * Genera el tablero completo del Sudoku resolviéndolo.
     * Llena el tablero y la matriz de solución con los valores generados.
     *
     * @return El tablero generado.
     */
    @Override
    public ArrayList<ArrayList<Integer>> generateSudoku() {
        for (int i = 0; i < 6; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 6; j++) {
                board.get(i).add(0); // Llena el tablero con ceros temporalmente
            }
        }

        fillBoard(0, 0);

        return board; // Devuelve el tablero generado
    }

    /**
     * Genera un tablero vacío donde el jugador podrá ingresar sus respuestas.
     *
     * @return El tablero vacío generado.
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
     * Verifica si el valor ingresado por el jugador es el valor correcto
     * comparándolo con la solución del Sudoku.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param num El número ingresado por el jugador.
     * @return true si el valor es correcto, false si es incorrecto.
     */
    public boolean isCorrectValue(int row, int col, int num) {
        return board.get(row).get(col) == num;
    }

    /**
     * Método recursivo que llena el tablero usando backtracking.
     * Genera una solución válida para el Sudoku.
     *
     * @param row La fila actual.
     * @param col La columna actual.
     * @return true si el tablero se llenó correctamente, false si no se pudo.
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
     * Verifica si un número puede colocarse en una posición dada,
     * respetando las reglas del Sudoku (sin repetición en filas, columnas y bloques).
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param num El número a verificar.
     * @return true si el número puede colocarse, false si no.
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
     * Devuelve el tablero actual de Sudoku.
     *
     * @return El tablero de juego.
     */
    @Override
    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    /**
     * Devuelve el tablero de intentos del jugador.
     *
     * @return El tablero de intentos.
     */
    public ArrayList<ArrayList<Integer>> getAttempt() {
        return attempt;
    }

    /**
     * Imprime el estado actual del tablero en la consola.
     */
    @Override
    public void printBoard() {
        for (ArrayList<Integer> row : board) {
            System.out.println(row);
        }
    }
}
