package com.example.sudoku.view;

import com.example.sudoku.controller.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La clase SudokuStage extiende la clase Stage de JavaFX y configura la ventana principal del juego Sudoku.
 * Carga la interfaz gráfica del usuario desde un archivo FXML y vincula el controlador del juego.
 *
 * @version 1.0
 * @since 2024-10-23
 *
 * @author Juan Pablo Charry Ramirez
 * @author Juan Esteban Rodriguez Valencia
 */
public class SudokuStage extends Stage {

    // Controlador del juego Sudoku
    private SudokuController sudokuController;

    /**
     * Constructor de la clase SudokuStage. Carga la interfaz gráfica desde el archivo FXML
     * y configura la escena principal.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public SudokuStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sudoku/main-view.fxml"));
        Parent root = fxmlLoader.load();
        sudokuController = fxmlLoader.getController(); // Obtener el controlador del archivo FXML
        Scene scene = new Scene(root);
        setScene(scene); // Establecer la escena
        setTitle("Sudoku"); // Título de la ventana
        setResizable(false); // No permitir cambiar el tamaño de la ventana
        show(); // Mostrar la ventana
    }

    /**
     * Devuelve el controlador del juego Sudoku.
     *
     * @return El controlador SudokuController asociado con esta ventana.
     */
    public SudokuController getSudokuController() {
        return sudokuController;
    }
}