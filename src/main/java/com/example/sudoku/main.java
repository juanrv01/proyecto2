package com.example.sudoku;

import com.example.sudoku.view.SudokuStage;
import com.example.sudoku.model.SudokuModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La clase main inicia la aplicación del juego Sudoku.
 * Se extiende de la clase Application de JavaFX para habilitar
 * la interfaz gráfica del usuario.
 *
 * @version 1.0
 * @since 2024-10-23
 *
 * @author Juan Pablo Charry Ramirez
 * @author Juan Esteban Rodriguez Valencia
 */
public class main extends Application {

    /**
     * Método principal que lanza la aplicación del juego Sudoku.
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * El método start inicializa el escenario principal de la aplicación.
     * @param primaryStage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar la vista del juego.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        new SudokuStage();
    }
}


