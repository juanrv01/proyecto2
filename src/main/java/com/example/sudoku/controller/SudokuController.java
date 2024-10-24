package com.example.sudoku.controller;

import com.example.sudoku.model.SudokuModel;
import com.example.sudoku.model.ISudokuModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javafx.scene.Node;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase SudokuController maneja la lógica del juego de Sudoku, incluyendo la interacción
 * con el modelo de datos y la interfaz gráfica de usuario (GUI). Se encarga de procesar las entradas del
 * usuario y gestionar la validación y visualización del estado del juego.
 *
 * @version 1.0
 * @since 2024-10-23
 *
 * @author Juan Pablo Charry Ramirez
 * @author Juan Esteban Rodriguez Valencia
 */
public class SudokuController implements ISudokuController {

    // Modelo del Sudoku que gestiona la lógica del juego
    private ISudokuModel sudokuModel;

    @FXML
    private VBox sudokuBase; // Contenedor base de la interfaz gráfica

    @FXML
    private Label congrats_one;

    @FXML
    private Label congrats_two;

    @FXML
    private ImageView congrats_image;

    @FXML
    GridPane grid = new GridPane(); // Cuadrícula de 6x6 del Sudoku

    private boolean gameOver = false;

    /**
     * Inicializa el juego de Sudoku, configurando el tablero y mostrando la interfaz gráfica.
     * También muestra algunas ayudas iniciales al usuario.
     */
    @Override
    public void initializeGame() {
        sudokuModel = new SudokuModel(); // Inicializa el modelo del Sudoku

        grid.getStyleClass().add("custom-grid");

        // Crea el tablero de 6x6 con TextFields
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField cell = createTextField(row, col);
                grid.add(cell, col, row);
            }
        }
        sudokuBase.getChildren().add(grid);

        // Muestra 12 ayudas iniciales al usuario
        for (int i = 0; i < 18; i++) {
            showHelp();
        }
    }

    /**
     * Método que se ejecuta cuando el botón de ayuda es presionado.
     * Llama al método que muestra una pista al usuario.
     *
     * @param event El evento del botón de ayuda.
     */
    @FXML
    void helpButton(ActionEvent event) {
        showHelp();
    }

    /**
     * Establece el valor de una celda específica en la cuadrícula del Sudoku.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param value El valor que se quiere establecer.
     */
    public void setTextFieldValue(int row, int col, String value) {
        // Busca el nodo (TextField) en la posición específica
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                // Si se encuentra el TextField en la fila y columna especificadas
                if (node instanceof TextField) {
                    ((TextField) node).setText(value); // Asignar el valor al TextField
                }
                break;
            }
        }
    }

    /**
     * Muestra una ayuda al usuario colocando un valor correcto en una celda vacía o incorrecta.
     */
    @Override
    public void showHelp() {
        Random random = new Random();
        boolean helpGiven = false;

        // Seleccionar una celda aleatoria hasta encontrar una vacía o incorrecta
        while (!helpGiven) {
            int randomRow = random.nextInt(6); // Selecciona una fila aleatoria
            int randomCol = random.nextInt(6); // Selecciona una columna aleatoria
            Integer number = sudokuModel.getBoard().get(randomRow).get(randomCol);

            // Obtener el valor correcto del modelo
            String correctValue = number.toString();

            // Obtener el TextField en la posición aleatoria
            TextField cell = getTextField(randomRow, randomCol);

            // Si la celda está vacía o tiene un valor incorrecto, muestra la ayuda
            if (cell != null && (cell.getText().isEmpty() || !cell.getText().equals(correctValue))) {
                setTextFieldValue(randomRow, randomCol, correctValue);
                helpGiven = true; // Salir del ciclo cuando se haya dado la ayuda
            }

            sudokuModel.getAttempt().get(randomRow).set(randomCol, number);
        }
    }

    /**
     * Obtiene un TextField de la cuadrícula en una posición específica.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @return El TextField en la posición especificada, o null si no existe.
     */
    private TextField getTextField(int row, int col) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof TextField) {
                return (TextField) node; // Devuelve el TextField en esa posición
            }
        }
        return null; // Si no se encuentra el TextField, devuelve null
    }

    /**
     * Maneja la entrada del usuario en una celda específica, validando el número ingresado.
     * Si el número es correcto, lo coloca en el tablero; si es incorrecto, marca la celda en rojo.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param value El valor ingresado por el usuario.
     */
    @Override
    public void handleCellInput(int row, int col, String value) {
        // Validar si el valor ingresado es un número entre 1 y 6
        if (!value.matches("[1-6]")) {
            return; // Salir si el valor no es un número válido
        }

        int num = Integer.parseInt(value); // Convertir el valor a un número entero

        TextField cell = getTextField(row, col);

        // Validar contra la solución predefinida
        if (sudokuModel.isCorrectValue(row, col, num) || value.isEmpty()) {
            sudokuModel.getBoard().get(row).set(col, num); // Actualizar el modelo con el valor ingresado
            setTextFieldValue(row, col, value); // Mostrar el valor en la celda
            if (cell != null) {
                cell.setStyle(""); // Eliminar el borde rojo si el valor es correcto
            }

            // Actualizar el intento del usuario
            sudokuModel.getAttempt().get(row).set(col, num);

        } else {
            if (cell != null) {
                cell.setStyle("-fx-border-color: red;"); // Marcar en rojo si es incorrecto
            }
        }

        // Verificar si el juego ha terminado
        if (isGameOver(sudokuModel.getBoard(), sudokuModel.getAttempt())) {
            congrats_one.setVisible(true);
            congrats_two.setVisible(true);
            congrats_image.setVisible(true);
        }
    }

    /**
     * Crea un TextField para representar una celda del Sudoku y le añade un listener
     * para manejar las entradas del usuario.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @return El TextField configurado.
     */
    private TextField createTextField(int row, int col) {
        TextField textField = new TextField();
        textField.setPrefWidth(50);
        textField.setMaxWidth(50);

        // Añadir un listener para detectar cuando el usuario ha terminado de ingresar un número
        textField.setOnKeyReleased(event -> {
            String input = textField.getText(); // Obtener el valor del TextField

            // Verificar si el valor ingresado es un número válido entre 1 y 6
            if (input.matches("[1-6]")) {
                handleCellInput(row, col, input);// Procesar el input solo después de que el valor ha sido ingresado
                sudokuModel.getBoard().get(row).set(col, Integer.valueOf(input));
            }
        });

        // Añadir el estilo del archivo CSS al TextField
        textField.getStyleClass().add("text-field");
        return textField;
    }

    public boolean isGameOver(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> attempt) {
        // Recorrer todas las posiciones de la matriz 6x6
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                // Comparar los valores en la posición correspondiente
                if (!board.get(row).get(col).equals(attempt.get(row).get(col))) {
                    return false; // Si algún valor no coincide, devolver false
                }
            }
        }
        return true; // Si todos los valores coinciden, devolver true
    }


    /**
     * Método que inicializa el juego llamando a la función de configuración inicial del Sudoku.
     */
    @FXML
    public void initialize() {
        initializeGame(); // Llamar al método de inicialización del juego
    }
}
