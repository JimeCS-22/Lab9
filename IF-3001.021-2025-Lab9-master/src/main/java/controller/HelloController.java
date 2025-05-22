package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import util.FXUtility;

import java.awt.event.MouseEvent;
import java.io.IOException;

import static java.lang.System.load;

public class HelloController {

        @FXML
        private AnchorPane ap;

        @FXML
        private BorderPane bp;

        @FXML
        private Text txtMessage;

        @FXML
        void BTreeOperationsOnAction(ActionEvent event) {

            load("BTreeOperations.fxml");

        }

        @FXML
        void BTreeTourOnAction(ActionEvent event) {

            load("BTreeTour.fxml");

        }

        @FXML
        void Exit(ActionEvent event) {

        }

        @FXML
        void GraphicBTreeOnAction(ActionEvent event) {

            load("GraphicBTree.fxml");

        }

        @FXML
        void Home(ActionEvent event) {

        }

        @FXML
        public void exampleOnMousePressed(javafx.scene.input.MouseEvent mouseEvent) {
         }

}
