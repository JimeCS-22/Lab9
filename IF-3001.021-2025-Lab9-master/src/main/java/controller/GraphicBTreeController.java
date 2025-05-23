package controller;

import domain.BTree;
import domain.BTreeNode;
import domain.TreeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.Utility;

public class GraphicBTreeController {
    @javafx.fxml.FXML
    private Text txtMessage;
    @javafx.fxml.FXML
    private Pane mainPain;
    @javafx.fxml.FXML
    private Button levelsButton;
    @javafx.fxml.FXML
    private Button tourInfoButton;
    @javafx.fxml.FXML
    private AnchorPane AP;
    @javafx.fxml.FXML
    private Button randomizeButton;
    @FXML
    private Pane treePane;
    private BTree bTree;

    public void initialize() {
        // Crear árbol binario con valores aleatorios
        bTree = new BTree();

        for (int i = 0; i < 20; i++) {
            bTree.add(Utility.random(50));
        }

        drawTree();


    }

    private void drawTree() {
        treePane.getChildren().clear(); // Limpiar el panel antes de dibujar

        if (!bTree.isEmpty()) {
            drawBTreeNode(bTree.getRoot(), 400, 50, 300);
        }
    }

    private void drawBTreeNode(BTreeNode node, double x, double y, double hSpace) {
        if (node == null) return;

        // Dibujar el nodo
        Circle circle = new Circle(x, y, 20, Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 10, y + 5, String.valueOf(node.data));
        text.setFont(new Font(12));

        treePane.getChildren().addAll(circle, text);

        // Dibujar hijos recursivamente
        double childY = y + 80;
        double childHSpace = hSpace * 0.6; // Reducir espacio para niveles más profundos

        // Hijo izquierdo
        if (node.left != null) {
            double leftX = x - hSpace/2;
            Line line = new Line(x, y + 20, leftX, childY - 20);
            treePane.getChildren().add(line);
            drawBTreeNode(node.left, leftX, childY, childHSpace);
        }

        // Hijo derecho
        if (node.right != null) {
            double rightX = x + hSpace/2;
            Line line = new Line(x, y + 20, rightX, childY - 20);
            treePane.getChildren().add(line);
            drawBTreeNode(node.right, rightX, childY, childHSpace);
        }
    }

    // Método para regenerar el árbol con nuevos valores aleatorios
    @javafx.fxml.FXML
    private void regenerateTree() {
        bTree.clear();
        for (int i = 0; i < 20; i++) {
            bTree.add(Utility.random(50));
        }
        drawTree();
    }

    @javafx.fxml.FXML
    public void levelsOnAction(ActionEvent actionEvent) {


    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {

        // Crear nuevo árbol con elementos aleatorios
        bTree = new BTree();
        int nodeCount = 15 + (int)(Math.random() * 10); // Entre 15 y 25 nodos

        for (int i = 0; i < nodeCount; i++) {
            bTree.add(Utility.random(50)); // Valores entre 1 y 100
        }

        drawTree();

        util.FXUtility.showMessage("INFORMATION","The tree is randomized" );

    }



    @javafx.fxml.FXML
    public void tourInfoOnAction(ActionEvent actionEvent) {

        String tourInfoContent = "";
        try {
            tourInfoContent = "--- Transversal Tour Info  ---\n" + "\n" +
                    "Preorden: " + bTree.preOrder() + "\n" + "\n" +
                    "Inorden: " + bTree.inOrder() + "\n" + "\n" +
                    "Postorden: " + bTree.postOrder() + "\n";

        } catch (TreeException e) {
            tourInfoContent = "Error: " + e.getMessage();
        }

        util.FXUtility.showTextAreaAlert("Recorridos del Árbol", "Resultados de los Recorridos", tourInfoContent);

    }

    @javafx.fxml.FXML
    private void handleScrollZoom(ScrollEvent event) {
        double zoomFactor = 1.1;
        if (event.getDeltaY() < 0) {
            zoomFactor = 1 / zoomFactor;
        }

        Pane pane = (Pane) event.getSource(); // Obtiene el Pane que emitió el evento
        pane.setScaleX(pane.getScaleX() * zoomFactor);
        pane.setScaleY(pane.getScaleY() * zoomFactor);
    }

}
