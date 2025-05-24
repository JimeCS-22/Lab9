package controller;

import domain.BTree;
import domain.BTreeNode;

import javafx.event.ActionEvent;

import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Circle;

import util.Utility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BTreeTourController {

    @javafx.fxml.FXML
    private Text ordertxtMessage;
    @javafx.fxml.FXML
    private Pane treePane; // Panel donde se dibujará el árbol
    private BTree bTree;
    private Map<BTreeNode, Text> positionLabels = new HashMap<>();
    private Map<BTreeNode, Circle> nodeCircles = new HashMap<>();


    public void initialize() {
            // Crear árbol binario con valores aleatorios
            bTree = new BTree();

            for (int i = 0; i < 20; i++) {
                bTree.add(Utility.random(50));
            }

            drawTree();

        }

    private void drawBTreeNode(BTreeNode node, double x, double y, double levelWidth) {
        if (node == null) return;

        // Dibujar el nodo
        Circle circle = new Circle(x, y, 20, Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);

        // Texto para la posición en el recorrido (inicialmente vacío y en rojo)
        Text positionText = new Text(x + 20, y - 5, "");
        positionText.setFill(Color.RED);
        positionText.setFont(new Font(10));

        treePane.getChildren().add(positionText);

        // Guardar referencias
        nodeCircles.put(node, circle);
        positionLabels.put(node, positionText);

        Text text = new Text(x - 10, y + 5, String.valueOf(node.data));
        text.setFont(new Font(12));

        treePane.getChildren().addAll(circle, text);

        // Calcular espacio para hijos
        double childY = y + 90;

        // Determinar si tenemos uno o dos hijos
        boolean hasLeft = node.left != null;
        boolean hasRight = node.right != null;

        if (hasLeft || hasRight) {
            if (hasLeft && hasRight) {
                // Caso con dos hijos: dividir espacio equitativamente
                double leftX = x - levelWidth/4;
                double rightX = x + levelWidth/4;

                // Dibujar hijo izquierdo
                Line leftLine = new Line(x, y + 23, leftX, childY - 23);
                treePane.getChildren().add(leftLine);
                drawBTreeNode(node.left, leftX, childY, levelWidth/2);

                // Dibujar hijo derecho
                Line rightLine = new Line(x, y + 23, rightX, childY - 23);
                treePane.getChildren().add(rightLine);
                drawBTreeNode(node.right, rightX, childY, levelWidth/2);
            } else {
                // Caso con un solo hijo: colocarlo directamente abajo pero desplazado
                double childX = x;
                if (hasLeft) {
                    childX = x - levelWidth/4; // Desplazar a la izquierda
                } else {
                    childX = x + levelWidth/4; // Desplazar a la derecha
                }

                Line line = new Line(x, y + 23, childX, childY - 23);
                treePane.getChildren().add(line);
                drawBTreeNode(hasLeft ? node.left : node.right, childX, childY, levelWidth/2);
            }
        }
    }

    private void updatePositions(String traversalType) {
        // Limpiar todas las posiciones anteriores
        positionLabels.values().forEach(text -> text.setText(""));

        String orderType;

        switch (traversalType) {
            case "preOrder":
                orderType = "Pre Order Transversal Tour (N-L-R)";
                break;
            case "inOrder":
                orderType = "In Order Transversal Tour (L-N-R)";
                break;
            case "postOrder":
                orderType = "Post Order Transversal Tour (L-R-N)";
                break;
            default:
                return;
        }

        ordertxtMessage.setText(orderType);

        List<BTreeNode> traversalNodes = getTraversalNodes(traversalType, bTree.getRoot());

        for (int i = 0; i < traversalNodes.size(); i++) {
            BTreeNode node = traversalNodes.get(i);
            int position = i + 1;
            if (positionLabels.containsKey(node)) {
                positionLabels.get(node).setText(String.valueOf(position));
            }
        }


    }



    @javafx.fxml.FXML
    public void preOrderOnAction(ActionEvent actionEvent) {
        updatePositions("preOrder");
    }

    @javafx.fxml.FXML
    public void inOrderOnAction(ActionEvent actionEvent) {
        updatePositions("inOrder");
    }

    @javafx.fxml.FXML
    public void postOrderOnAction(ActionEvent actionEvent) {
        updatePositions("postOrder");
    }

    private void drawTree() {
        treePane.getChildren().clear();
        positionLabels.clear();
        nodeCircles.clear();
        if (!bTree.isEmpty()) {
            drawBTreeNode(bTree.getRoot(), 400, 50, 600);
        }
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

    private List<BTreeNode> getTraversalNodes(String traversalType, BTreeNode root) {
        List<BTreeNode> result = new ArrayList<>();
        switch (traversalType) {
            case "preOrder":
                preOrder(root, result);
                break;
            case "inOrder":
                inOrder(root, result);
                break;
            case "postOrder":
                postOrder(root, result);
                break;
        }
        return result;
    }

    private void preOrder(BTreeNode node, List<BTreeNode> list) {
        if (node != null) {
            list.add(node);
            preOrder(node.left, list);
            preOrder(node.right, list);
        }
    }

    private void inOrder(BTreeNode node, List<BTreeNode> list) {
        if (node != null) {
            inOrder(node.left, list);
            list.add(node);
            inOrder(node.right, list);
        }
    }

    private void postOrder(BTreeNode node, List<BTreeNode> list) {
        if (node != null) {
            postOrder(node.left, list);
            postOrder(node.right, list);
            list.add(node);
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
