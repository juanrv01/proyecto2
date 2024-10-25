package com.example.sudoku.controller;

import java.util.ArrayList;

/**
 * Interfaz que define las operaciones básicas para un controlador de Sudoku.
 * Proporciona métodos para manejar la entrada de celdas, mostrar ayudas,
 * establecer valores en el tablero y verificar el estado del juego.
 */
public interface ISudokuController {

    /**
     * Procesa la entrada del usuario en una celda específica.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param value El valor ingresado por el usuario.
     */
    void handleCellInput(int row, int col, String value);

    /**
     * Establece el valor de una celda en el tablero.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param value El valor a asignar.
     */
    void setTextFieldValue(int row, int col, String value);

    /**
     * Muestra una pista al usuario sobre el estado del juego.
     */
    void showHelp();

    /**
     * Inicia un nuevo juego con la dificultad especificada.
     *
     * @param difficulty El nivel de dificultad del juego.
     */
    void startGame(int difficulty);

    /**
     * Verifica si el juego ha terminado comparando el tablero y los intentos del usuario.
     *
     * @param board El tablero correcto.
     * @param attempt Los intentos del usuario.
     * @return true si el juego ha terminado, false en caso contrario.
     */
    boolean isGameOver(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> attempt);
}
