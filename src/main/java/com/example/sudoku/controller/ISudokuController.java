package com.example.sudoku.controller;

import java.util.ArrayList;

/**
 * La interfaz ISudokuController define los métodos que el controlador de un juego Sudoku debe implementar.
 * Se encarga de manejar la lógica del juego, las interacciones del usuario y mostrar ayudas cuando sea necesario.
 *
 * @version 1.0
 * @since 2024-10-23
 *
 * @author Juan Pablo Charry Ramirez
 * @author Juan Esteban Rodriguez Valencia
 */
public interface ISudokuController {

    /**
     * Inicializa el juego de Sudoku, configurando el tablero y la interfaz gráfica.
     */
    void initializeGame();

    /**
     * Maneja la entrada del usuario en una celda específica, validando el valor ingresado.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param value El valor ingresado por el usuario.
     */
    void handleCellInput(int row, int col, String value);

    /**
     * Establece el valor de un TextField específico en la cuadrícula del Sudoku.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param value El valor que se quiere establecer en el TextField.
     */
    void setTextFieldValue(int row, int col, String value);

    /**
     * Muestra una ayuda al jugador colocando un valor correcto en una celda vacía o incorrecta.
     */
    void showHelp();

    boolean isGameOver(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> attempt);
}