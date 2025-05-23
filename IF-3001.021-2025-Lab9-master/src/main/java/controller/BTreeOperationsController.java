package controller;

import domain.BTree;
import domain.BTreeNode;
import domain.TreeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.FXUtility;
import util.Utility;

import java.util.Optional;

public class BTreeOperationsController {

    private BTree bTree;
    private static final double NODE_RADIUS = 10;
    private static final double LEVEL_SPACING = 100;
    private static final double HORIZONTAL_SPACING = 200;

    @FXML
    private AnchorPane AP;
    @FXML
    private Pane mainPain;

    private double scaleFactor = 1.0;
    @FXML
    private Pane pane2;

    public void initialize() {
        this.bTree = new BTree();
        drawTree(); // Dibujar el árbol inicial (vacío)
    }

    @javafx.fxml.FXML
    private void handleScrollZoom(ScrollEvent event) {
        double zoomFactor = 1.1;
        if (event.getDeltaY() < 0) {
            zoomFactor = 1 / zoomFactor;
        }

        scaleFactor *= zoomFactor;
        scaleFactor = Math.max(0.1, scaleFactor);

        pane2.setScaleX(scaleFactor);
        pane2.setScaleY(scaleFactor);
    }

    @FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        bTree.clear();
        for (int i = 0; i < 30; i++) {
            bTree.add(Utility.random(51));
        }
        drawTree();
    }

    @FXML
    public void containsOnAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Elemento");
        dialog.setHeaderText("Ingrese el número a buscar:");
        dialog.setContentText("Número:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(numberStr -> {
            try {
                int numberToFind = Integer.parseInt(numberStr);
                boolean exists = bTree.contains(numberToFind);
                String message;
                if (exists) {
                    message = "El número " + numberToFind + " existe en el árbol.";
                } else {
                    message = "El número " + numberToFind + " no existe en el árbol.";
                }
                FXUtility.showAlert("Resultado de la búsqueda", message, Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                FXUtility.showAlert("Error", "Por favor, ingrese un número entero válido.", Alert.AlertType.ERROR);
            } catch (TreeException e) {
                FXUtility.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        int numberToAdd = Utility.random(51);
        bTree.add(numberToAdd);
        drawTree();
        FXUtility.showAlert("Éxito", "El número " + numberToAdd + " ha sido añadido al árbol.", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void removeOnAction(ActionEvent actionEvent) {
        try {
            if (bTree.isEmpty()) {
                FXUtility.showAlert("Árbol Vacío", "El árbol está vacío, no hay nodos para eliminar.", Alert.AlertType.WARNING);
                return;
            }

            java.util.List<BTreeNode> allNodes = getAllNodes(bTree.getRoot(), new java.util.ArrayList<>());
            if (!allNodes.isEmpty()) {
                int randomIndex = Utility.random(allNodes.size());
                BTreeNode nodeToRemove = allNodes.get(randomIndex);
                Object valueToRemove = nodeToRemove.data;

                bTree.remove(valueToRemove);
                drawTree(); // Redibujar el árbol después de la eliminación
                FXUtility.showAlert("Nodo Removido", "Se ha eliminado un nodo con valor: " + valueToRemove, Alert.AlertType.INFORMATION);
            } else {
                FXUtility.showAlert("Árbol Vacío", "El árbol está vacío, no hay nodos para eliminar.", Alert.AlertType.WARNING);
            }
        } catch (TreeException e) {
            FXUtility.showAlert("Error al Remover", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //Guarda todos los nodos en una lista de manera in-order
    private java.util.List<BTreeNode> getAllNodes(BTreeNode node, java.util.List<BTreeNode> nodeList) {
        if (node != null) {
            getAllNodes(node.left, nodeList);
            nodeList.add(node);
            getAllNodes(node.right, nodeList);
        }
        return nodeList;
    }

    @FXML
    public void nodeHeightOnAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Altura y Camino del Nodo");
        dialog.setHeaderText("Ingrese el número del nodo:");
        dialog.setContentText("Número:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(numberStr -> {
            try {
                int nodeValue = Integer.parseInt(numberStr);
                try {
                    int height = bTree.height(nodeValue);
                    String path = getNodePath(bTree.getRoot(), nodeValue, "root");
                    if (path != null) {
                        FXUtility.showAlert("Altura y Camino del Nodo",
                                "La altura del nodo con valor " + nodeValue + " es: " + height + "\nCamino: " + path,
                                Alert.AlertType.INFORMATION);
                    } else {
                        FXUtility.showAlert("Altura y Camino del Nodo",
                                "El nodo con valor " + nodeValue + " no se encuentra en el árbol.",
                                Alert.AlertType.INFORMATION);
                    }
                } catch (TreeException e) {
                    FXUtility.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                FXUtility.showAlert("Error", "Por favor, ingrese un número entero válido.", Alert.AlertType.ERROR);
            }
        });
    }

    private String getNodePath(BTreeNode node, int value, String currentPath) {
        if (node == null) {
            return null;
        }
        if (util.Utility.compare(node.data, value) == 0) {
            return currentPath + "/" + node.data;
        }
        String leftPath = getNodePath(node.left, value, currentPath + "/" + node.data);
        if (leftPath != null) {
            return leftPath;
        }
        String rightPath = getNodePath(node.right, value, currentPath + "/" + node.data);
        return rightPath;
    }

    @FXML
    public void treeHeightOnAction(ActionEvent actionEvent) {
        try {
            int height = bTree.height();
            FXUtility.showAlert("Altura del Árbol", "La altura actual del árbol es: " + height, Alert.AlertType.INFORMATION);
        } catch (TreeException e) {
            FXUtility.showAlert("Error", "Ocurrió un error al obtener la altura del árbol: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void drawTree() {
        pane2.getChildren().clear(); // Limpiar el Pane antes de dibujar
        if (!bTree.isEmpty()) {
            drawBTreeNode(bTree.getRoot(), pane2.getWidth() / 2, 50, pane2.getWidth() / 4, 0); // Pasar el nivel inicial como 0
        }
    }

    private void drawBTreeNode(BTreeNode node, double x, double y, double hSpace, int level) {
        if (node == null) return;

        // Dibujar el nodo
        double scaledRadius = NODE_RADIUS * scaleFactor;
        Circle circle = new Circle(x, y, scaledRadius, Color.CYAN);
        circle.setStroke(Color.BLACK);

        Text text = new Text(String.valueOf(node.data));
        text.setFont(new Font(12 * scaleFactor));
        text.setX(x - text.getLayoutBounds().getWidth() / 2);
        text.setY(y + text.getLayoutBounds().getHeight() / 4);

        pane2.getChildren().addAll(circle, text);

        // Dibujar hijos recursivamente
        double childY = y + LEVEL_SPACING * scaleFactor;
        // Ajustar el factor de reducción de hSpace basado en el nivel
        double hSpaceReductionFactor = 0.7 + (level * 0.07); // Empieza en 0.5 y aumenta con la profundidad
        double childHSpace = hSpace * hSpaceReductionFactor * scaleFactor;

        // Hijo izquierdo
        if (node.left != null) {
            double leftX = x - hSpace / 2 * scaleFactor;
            Line line = new Line(x, y + scaledRadius, leftX, childY - scaledRadius);
            pane2.getChildren().add(line);
            drawBTreeNode(node.left, leftX, childY, childHSpace, level + 1);
        }

        // Hijo derecho
        if (node.right != null) {
            double rightX = x + hSpace / 2 * scaleFactor;
            Line line = new Line(x, y + scaledRadius, rightX, childY - scaledRadius);
            pane2.getChildren().add(line);
            drawBTreeNode(node.right, rightX, childY, childHSpace, level + 1);
        }
    }
}