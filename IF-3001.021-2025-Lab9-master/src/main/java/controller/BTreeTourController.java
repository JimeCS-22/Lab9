package controller;

import domain.BTree;
import domain.BTreeNode;
import domain.TreeException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Circle;
import util.Utility;

public class BTreeTourController {
    @javafx.fxml.FXML
    private Text txtMessage;
    @javafx.fxml.FXML
    private Pane mainPain;
    @javafx.fxml.FXML
    private Pane buttonPane;
    @javafx.fxml.FXML
    private Button autoButton1;
    @javafx.fxml.FXML
    private AnchorPane AP;
    @javafx.fxml.FXML
    private Button inOrderButton;
    @javafx.fxml.FXML
    private Button preOrderButton;
    @javafx.fxml.FXML
    private Button postOrderButton;
    @javafx.fxml.FXML
    private Text ordertxtMessage;
    @javafx.fxml.FXML
    private Pane treePane; // Panel donde se dibujará el árbol
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
    public void randomizeOnAction(ActionEvent actionEvent) {
        // Crear nuevo árbol con elementos aleatorios
        bTree = new BTree();
        int nodeCount = 15 + (int)(Math.random() * 10); // Entre 15 y 25 nodos

        for (int i = 0; i < nodeCount; i++) {
            bTree.add(Utility.random(50)); // Valores entre 1 y 100
        }

        // Redibujar el árbol
        drawTree();

        // Opcional: Mostrar información del nuevo árbol
        util.FXUtility.showMessage("INFORMATION","Tree regenerated with " + nodeCount + " nodes");
    }

    @javafx.fxml.FXML
    public void postOrderOnAction(ActionEvent actionEvent) {
        String result;
        try {
            result = bTree.postOrder();  // Obtener el recorrido post orden
            ordertxtMessage.setText("Post Order Transversal Tour (L-R-N)");  // Actualizar el texto
            txtMessage.setFill(Color.RED);  // Cambiar texto a rojo
            txtMessage.setText(result);  // Mostrar el resultado del recorrido
        } catch (TreeException e) {
            txtMessage.setText("Error: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void inOrderOnAction(ActionEvent actionEvent) {
        String result;
        try {
            result = bTree.inOrder();  // Obtener el recorrido en orden
            ordertxtMessage.setText("In Order Transversal Tour (L-N-R)");  // Actualizar el texto
            txtMessage.setFill(Color.RED);  // Cambiar texto a rojo
            txtMessage.setText(result);  // Mostrar el resultado del recorrido
        } catch (TreeException e) {
            txtMessage.setText("Error: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void preOrderOnAction(ActionEvent actionEvent) {
        String result;
        try {
            result = bTree.preOrder();  // Obtener el recorrido pre orden
            ordertxtMessage.setText("Pre Order Transversal Tour (N-L-R)");  // Actualizar el texto
            txtMessage.setFill(Color.RED);  // Cambiar texto a rojo
            txtMessage.setText(result);  // Mostrar el resultado del recorrido
        } catch (TreeException e) {
            txtMessage.setText("Error: " + e.getMessage());
        }
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
