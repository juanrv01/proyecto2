package com.example.sudoku.model;

import java.util.ArrayList;

/**
 * La interfaz ISudokuModel define los métodos que deben ser implementados por cualquier clase que maneje
 * la lógica del modelo del juego Sudoku. Incluye la generación del tablero, validaciones y métodos
 * para obtener el estado del juego.
 *
 * @version 1.0
 * @since 2024-10-23
 *
 * @author Juan Pablo Charry Ramirez
 * @author Juan Esteban Rodriguez Valencia
 */
public interface ISudokuModel {

    /**
     * Genera un nuevo tablero de Sudoku completamente resuelto.
     *
     * @return Una matriz 2D representando el tablero resuelto.
     */
    ArrayList<ArrayList<Integer>> generateSudoku();

    /**
     * Genera un nuevo tablero vacío de Sudoku donde el jugador podrá ingresar sus respuestas.
     *
     * @return Una matriz 2D representando el tablero vacío.
     */
    ArrayList<ArrayList<Integer>> generateEmptySudoku();

    /**
     * Verifica si se puede colocar un número en una celda específica, respetando las reglas del Sudoku.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param num El número a verificar.
     * @return true si el número puede ser colocado, false si no.
     */
    boolean isValidPlacement(int row, int col, int num);

    /**
     * Obtiene el tablero actual de juego.
     *
     * @return Una matriz 2D representando el estado actual del tablero.
     */
    ArrayList<ArrayList<Integer>> getBoard();

    /**
     * Obtiene el tablero con los intentos del jugador.
     *
     * @return Una matriz 2D representando el tablero con los intentos actuales.
     */
    ArrayList<ArrayList<Integer>> getAttempt();

    /**
     * Imprime el estado actual del tablero en la consola.
     */
    void printBoard();

    /**
     * Verifica si un valor ingresado por el jugador coincide con la solución predefinida.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param num El número ingresado por el jugador.
     * @return true si el valor es correcto, false si es incorrecto.
     */
    boolean isCorrectValue(int row, int col, int num);
}