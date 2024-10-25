package com.example.sudoku;

import com.example.sudoku.view.SudokuStage;
import com.example.sudoku.model.SudokuModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metodo que inicia el Stage
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        new SudokuStage();
    }

}


