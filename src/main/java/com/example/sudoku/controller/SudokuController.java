package com.example.sudoku.controller;

import com.example.sudoku.model.SudokuModel;
import com.example.sudoku.model.ISudokuModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;

public class SudokuController implements ISudokuController {

    // Modelo del Sudoku que gestiona la lógica del juego
    private ISudokuModel sudokuModel;
    private boolean gameOver = false;

    @FXML
    private VBox sudokuBase; // Contenedor base de la interfaz gráfica

    @FXML
    private Label congrats_one,congrats_two,helpLabel; //labels de la interfaz

    @FXML
    private ImageView congrats_image;//ImageView de la interfaz

    @FXML
    private Button helpButton; //Boton de ayuda


    @FXML
    GridPane grid = new GridPane(); // Cuadrícula de 6x6 del Sudoku

    /**
     * initializeGame() crea un modelo SudokuModel
     * oculta el boton y el indicador de ayuda
     */
    public void initializeGame() {
        sudokuModel = new SudokuModel();
        helpButton.setVisible(false);
        helpLabel.setVisible(false);
    }

    /**
     * Pone la dificultad en dificil y le da 8 pistas al jugador
     * @param event pulsar el boton del usuario
     */
    @FXML
    void setDifficultyHard(ActionEvent event) {
        removeButtons();
        startGame(8);
    }

    /**
     * Pone la dificultad en dificil y le da 14 pistas al jugador
     * @param event pulsar el boton del usuario
     */
    @FXML
    void setDifficultyMedium(ActionEvent event) {
        removeButtons();
        startGame(14);
    }

    /**
     * Pone la dificultad en dificil y le da 20 pistas al jugador
     * @param event pulsar el boton del usuario
     */
    @FXML
    void setDifficultyEasy(ActionEvent event) {
        removeButtons();
        startGame(20);
    }

    /**
     * limpia el Vbox borrando los botones de dificultad y su letrero
     */
    public void removeButtons() {
        sudokuBase.getChildren().clear();
    }

    /**
     * Genera un GridPane de tamaño 6x6 y en cada casilla agrega un Textfield
     * Se muestran la cantiad de casillas que indique difficulty
     * @param difficulty cantidad de pistas
     */
    @Override
    public void startGame(int difficulty) {
        grid.getStyleClass().add("custom-grid");

        // Crea el tablero de 6x6 con TextFields
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField cell = createTextField(row, col); //Se añade un textField
                grid.add(cell, col, row);
            }
        }
        sudokuBase.getChildren().add(grid); //Se añade el grid al VBox

        // Muestra las ayudas iniciales al usuario
        for (int i = 0; i < difficulty; i++) {
            showHelp(); //Se muestran las ayudas
        }

        //Se vuelve visible la opcion de ayuda
        helpButton.setVisible(true);
        helpLabel.setVisible(true);
    }

    /**
     * Muestra el valor que va en un casilla
     * @param event
     */
    @FXML
    void helpButton(ActionEvent event) {
        showHelp();
    }

    /**
     * Establece el valor de un {@code TextField} específico en la cuadrícula del Sudoku.
     *
     * Este metodo busca el {@code TextField} en la posición especificada dentro del {@code GridPane}
     * y, si lo encuentra, le asigna el valor proporcionado.
     *
     * @param row La fila del {@code TextField} en la cuadrícula.
     * @param col La columna del {@code TextField} en la cuadrícula.
     * @param value El valor que se quiere establecer en el {@code TextField}.
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
     *
     * Este metodo selecciona aleatoriamente una celda en la cuadrícula del Sudoku y, si la celda está vacía
     * o tiene un valor incorrecto, coloca el valor correcto y actualiza el intento en el modelo.
     *
     * Usa un ciclo para seguir seleccionando posiciones aleatorias hasta encontrar una celda que cumpla
     * con las condiciones para mostrar la ayuda.
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
     * Obtiene el TextField ubicado en una posición específica de la cuadrícula.
     * @param row La fila de la celda en la cuadrícula.
     * @param col La columna de la celda en la cuadrícula.
     * @return El TextField en la posición especificada, o null si no se encuentra.
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
     * Revisa si el valor que ingresa el usuario es valido si no lo es, lo desecha
     * Ingresa el valor a la matriz Attempt, en caso de que se ingrese de la ayuda tambien lo guarda
     * Si el valor es incorrecto, se marca el borde rojo hasta que se ingrese el valor correcro
     * Se comparan los 2 arreglos, en caso de que sean iguals se acaba el juego
     * @param row Fila
     * @param col Columna
     * @param value Valor de la celda
     */
    @Override
    public void handleCellInput(int row, int col, String value) {
        TextField cell = getTextField(row, col); // Obtener el TextField correspondiente

        if (!value.isEmpty()) {
            sudokuModel.getAttempt().get(row).set(col, Integer.parseInt(value)); // Solo actualizar el modelo si no está vacío
        } else {
            sudokuModel.getAttempt().get(row).set(col, null); // O establecer como nulo o vacío en el modelo
        }

        if (value.isEmpty()) {
            // Si el campo está vacío, restablecer el borde
            if (cell != null) {
                cell.setStyle(""); // Restablecer el estilo
            }
            return; // Salir si el campo está vacío
        }

        Integer num = Integer.parseInt(value); // Convertir el valor a un número entero



        if (!num.equals(sudokuModel.getBoard().get(row).get(col))) {
            cell.setStyle("-fx-border-color: red;");

        } else {
            cell.setStyle("-fx-border-color: grey;");
        }


        // Comprobar si el juego ha terminado

        if (isGameOver(sudokuModel.getBoard(), sudokuModel.getAttempt())) {
            congrats_one.setVisible(true);
            congrats_two.setVisible(true);
            congrats_image.setVisible(true);
        }
    }

    /**
     * Crea y configura un TextField para una celda del Sudoku.
     * El listener detecta cuando el usuario ha terminado de ingresar un valor y valida que el valor
     * sea un número entre 1 y 6, actualizando el modelo en consecuencia.
     *
     * @param row La fila de la celda en la cuadrícula.
     * @param col La columna de la celda en la cuadrícula.
     * @return El TextField configurado para la celda especificada.
     */
    private TextField createTextField(int row, int col) {
        TextField textField = new TextField();
        textField.setPrefWidth(50);
        textField.setMaxWidth(50);

        // Añadir un listener para detectar cuando el usuario ha terminado de ingresar un número
        textField.setOnKeyReleased(event -> {
            String input = textField.getText(); // Obtener el valor del TextField

            // Verificar si el valor ingresado es un número válido entre 1 y 6
            if (input.matches("[1-6]|")) { // Aceptar números del 1 al 6 o cadena vacía
                handleCellInput(row, col, input); // Procesar el input
            }
        });

        // Añadir el estilo del archivo CSS al TextField
        textField.getStyleClass().add("text-field");
        return textField;
    }

    /**
     * Toma cada valor del sudoku correcto y lo compara con el valor en la misma casilla que Attempt
     * @param board Sudoku correcto
     * @param attempt Intento del jugador
     * @return True si son iguales, False si no
     */
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


    @FXML
    public void initialize() {
        initializeGame(); // Llamar al metodo de inicialización del juego
    }

    /**
     * Control que permite salir del juego
     * @param event
     */
    @FXML
    private void handleExitButtonStage(ActionEvent event) {
        // Obtener el Stage a partir del evento
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Cierra la ventana actual
    }
}
