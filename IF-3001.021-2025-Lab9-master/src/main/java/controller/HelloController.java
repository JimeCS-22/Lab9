package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import util.FXUtility;

import java.io.IOException;

public class HelloController {

    @FXML
    private Text txtMessage;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    private void load(String formName) {
        try {
            // Construye la ruta correcta al archivo FXML
            String fxmlPath = "/ucr/lab8/" + formName;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));

            if (fxmlLoader.getLocation() == null) {
                throw new IOException("No se pudo encontrar el archivo: " + fxmlPath);
            }

            this.bp.setCenter(fxmlLoader.load());
            txtMessage.setText("Cargado: " + formName);

        } catch (IOException e) {
            FXUtility.showErrorAlert("Error", "No se pudo cargar la vista: " + formName + "\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void Home(ActionEvent actionEvent) {
        this.txtMessage.setText("Laboratory 8");
        this.bp.setCenter(ap);
    }

    @FXML
    public void Exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void exampleOnMousePressed(Event event) {
        this.txtMessage.setText("Loading Example. Please wait!!!");
    }

    // Métodos para cargar los diferentes algoritmos de ordenamiento
    @FXML
    public void BubbleSortOnAction(ActionEvent actionEvent) {
        load("elementary/BubbleSort.fxml");
    }

    @FXML
    public void mergeSortOnAction(ActionEvent actionEvent) {
        load("complex/MergeSort.fxml");
    }

    @FXML
    public void shellSortOnAction(ActionEvent actionEvent) {
        load("complex/ShellSort.fxml");
    }

    @FXML
    public void quickSortOnAction(ActionEvent actionEvent) {
        load("complex/QuickSorting.fxml");
    }

    @FXML
    public void countingSortOnAction(ActionEvent actionEvent) {
        load("elementary/CountingSorting.fxml");
    }

    @FXML
    public void impBubbleSortOnAction(ActionEvent actionEvent) {
        load("elementary/ImpBubbleSort.fxml");
    }

    @FXML
    public void selectionSortOnAction(ActionEvent actionEvent) {
        load("elementary/SelectionSorting.fxml");
    }

    @FXML
    public void radixSortOnAction(ActionEvent actionEvent) {
        load("complex/RadixSorting.fxml");
    }
}