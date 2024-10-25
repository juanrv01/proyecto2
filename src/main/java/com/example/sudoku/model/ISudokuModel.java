package com.example.sudoku.model;

import java.util.ArrayList;

public interface ISudokuModel {

    /**
     * Genera el sudoku resuelto
     */
    ArrayList<ArrayList<Integer>> generateSudoku();

    /**
     * Genera una matriz vacia que llenara el jugador
     */
    ArrayList<ArrayList<Integer>> generateEmptySudoku();

    /**
     * Revisa si un valor es valido de ingresar en esa casilla
     */
    boolean isValidPlacement(int row, int col, int num);

    /**
     * Devuelve el sudoku
     */
    ArrayList<ArrayList<Integer>> getBoard();

    /**
     * Devuelve el sudoku del usuario
     */
    ArrayList<ArrayList<Integer>> getAttempt();

    void printBoard();

    void printAttempt();
    /**
     * Revisa si el valor ingresado es el indicado
     */
    boolean isCorrectValue(int row, int col, int num);
}