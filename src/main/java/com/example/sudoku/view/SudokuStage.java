package com.example.sudoku.view;

import com.example.sudoku.controller.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuStage extends Stage {


    private SudokuController sudokuController; //Se llama al controlador

    public SudokuStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sudoku/main-view.fxml")); //Se vincula el archivo .fxml
        Parent root = fxmlLoader.load();
        sudokuController = fxmlLoader.getController(); // Obtener el controlador del archivo .fxml
        Scene scene = new Scene(root);
        setScene(scene); // Establecer la escena
        setTitle("Sudoku"); // Título de la ventana
        setResizable(false); // No permitir cambiar el tamaño de la ventana
        show(); // Mostrar la ventana
    }

    public SudokuController getSudokuController() {
        return sudokuController;
    }
}