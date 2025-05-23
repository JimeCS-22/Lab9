package domain;

public class BTree implements Tree {
    private BTreeNode root; //se refiere a la raiz del arbol

    @Override
    public int size() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return size(root);
    }

    private int size(BTreeNode node){
        if(node==null) return 0;
        else return 1 + size(node.left) + size(node.right);
    }

    @Override
    public void clear() {

        root = null;
    }

    @Override
    public boolean isEmpty() {

        return root==null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {

        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearch(root, element);

    }

    private boolean binarySearch(BTreeNode node, Object element) {
        if (node == null) {
            return false; // El elemento no se encontro
        }

        // Compara el elemento actual
        if (util.Utility.compare(node.data , element) == 0) {
            return true; // El elemento se encontró
        }
        return binarySearch(node.left, element) || binarySearch(node.right, element);

    }


    @Override
    public void add(Object element) {
       //this.root = add(root, element);
        this.root = add(root, element, "root");
    }

    private BTreeNode add(BTreeNode node, Object element){
        if(node==null)
            node = new BTreeNode(element);
        else{
            int value = util.Utility.random(100);
            if(value%2==0)
                node.left = add(node.left, element);
            else node.right = add(node.right, element);
        }
        return node;
    }

    private BTreeNode add(BTreeNode node, Object element, String path){
        if(node==null)
            node = new BTreeNode(element, path);
        else{
            int value = util.Utility.random(100);
            if(value%2==0)
                node.left = add(node.left, element, path+"/left");
            else node.right = add(node.right, element, path+"/right");
        }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is Empty");
        }
        if (!contains(element)) { // Primero verifica si el elemento existe
            throw new TreeException("The element [" + element + "] does not exist");
        }

        this.root = remove(this.root, element);

    }



    private BTreeNode remove(BTreeNode node, Object element) throws TreeException {
        if (node != null) {
            if (util.Utility.compare(node.data, element) < 0) {
                //caso1, es un nodo sin hijos, es una hoja
                if (node.left == null && node.right == null) {
                    return null;
                }
                //Caso2-a, el nodo solo tiene un hijo izq
                else if (node.left != null && node.right == null) {
                   // node.left = newPath(node.left, node.path);
                    return node.left;
                }
                //Caso2-b, el nodo solo tiene un hijo der
                else if (node.left == null && node.right != null) {
                   // node.right = newPath(node.right, node.path);
                    return node.right;

                }
                //Caso3, el nodo tiene dos hijos
                else if (node.left != null && node.right != null) {
                    /**
                     * El algoritmo de supresión dice que cuando el nodo a suprimir
                     * tiene dos hijos, entonces busque una hoja del subarbol derecho
                     * y sustituye la data del nodo a suprimir por la data de esa hoja,
                     * luego elimine esa hoja
                     */
                    Object value = getLeaf(node.right);
                    node.data = value;
                    node.right = removeLeaf(node.right, value, false);
                }
            }
            node.left = remove(node.left, element); //llamado recursivo por la izq
            node.right = remove(node.right, element); //llamado recursivo por la der
        }
        return node;// retorna el nodo modificado
    }

    private Object getLeaf(BTreeNode node) {
        Object aux;
        if(node==null) return null;
        else if(node.left==null && node.right==null) return node.data; //Es una hoja
        else {
            aux = getLeaf(node.left); // siga bajando por el subarbol izq
            if(aux==null) aux = getLeaf(node.right);
        }
        return aux;

    }

    private BTreeNode removeLeaf(BTreeNode node, Object value, boolean removed) {
        if(node==null) return null;
        //Si es una hoja y esa hoja es la que estamos buscando la eliminamos
        else if(node.left==null && node.right==null&&util.Utility.compare(node.data, value)==0) {
            removed = true; // ya eliminó la hoja
            return null;
        }else {
            if(!removed) node.left = removeLeaf(node.left, value, removed);
            else if(!removed) node.right = removeLeaf(node.right, value, removed);
        }
        return node;
    }

    //Número de ancestros
    @Override
    public int height(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return height(root, element, 0);
    }

    //Número de ancestros
    private int height(BTreeNode node, Object element, int level) {
        if (node == null)
            return 0; // Elemento no encontrado en este subárbol
        else if (util.Utility.compare(node.data, element) == 0) return level;
        else return Math.max(height(node.left, element, ++level),
                    height(node.right, element, level));
    }

    //La altura max de la raíz hasta cualquier hoja
    @Override
    public int height() throws TreeException {

        if (isEmpty())
            throw new TreeException("Binary Tree is empty");

        //return height(root, 0);//Opción 1
        return height(root); //opción 2
    }

    //La altura max de la raíz hasta cualquier hoja

    //Opción1
    private int height(BTreeNode node, int level) {
        if (node == null)
            return level - 1; //Se le resta 1 al nivel pq no cuente el nulo
        return Math.max(height(node.left, ++level),
                height(node.right, level));
    }

    //Opción 2
    private int height(BTreeNode node){
        if(node==null) return -1; //Retorna valor negativo para eliminar el nivel del nulo
        return Math.max(height(node.left),height(node.right))+1;
    }

    @Override
    public Object min() throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");

        return min(root);
    }

    private Object min(BTreeNode node){
        if (node == null) {
            return null; // No hay elementos en este subárbol
        }

        Object currentMin = node.data;
        Object leftMin = min(node.left);
        Object rightMin = min(node.right);

        // Comparar con el mínimo del subárbol izquierdo
        if (leftMin != null && ((Comparable) leftMin).compareTo(currentMin) < 0) {
            currentMin = leftMin;
        }
        // Comparar con el mínimo del subárbol derecho
        if (rightMin != null && ((Comparable) rightMin).compareTo(currentMin) < 0) {
            currentMin = rightMin;
        }
        return currentMin;
    }

    @Override
    public Object max() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return max(root);
    }

    private Object max(BTreeNode node) {
        if (node == null) {
            return null; // En este contexto, un subárbol nulo no tiene máximo.
        }

        Object currentMax = node.data;
        Object leftMax = max(node.left);
        Object rightMax = max(node.right);

        // Comparar con el máximo del subárbol izquierdo
        if (leftMax != null && ((Comparable) leftMax).compareTo(currentMax) > 0) {
            currentMax = leftMax;
        }
        // Comparar con el máximo del subárbol derecho
        if (rightMax != null && ((Comparable) rightMax).compareTo(currentMax) > 0) {
            currentMax = rightMax;
        }
        return currentMax;
    }

    @Override
    public String preOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return preOrder(root);
    }

    //recorre el árbol de la forma: nodo-hijo izq-hijo der
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            //result = node.data+" ";
            result  = node.data+"("+node.path+")"+" ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return  result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return inOrder(root);
    }

    //recorre el árbol de la forma: hijo izq-nodo-hijo der
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+" ";
            result += inOrder(node.right);
        }
        return  result;
    }

    //para mostrar todos los elementos existentes
    @Override
    public String postOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return postOrder(root);
    }

    //recorre el árbol de la forma: hijo izq-hijo der-nodo,
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+" ";
        }
        return result;
    }

    @Override
    public String toString() {
        String result= "Binary Tree Content:";
        try {
            result = "PreOrder: "+preOrder();
            result+= "\nInOrder: "+inOrder();
            result+= "\nPostOrder: "+postOrder();

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public String printLeaves() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return printLeaves(root);
    }
    private String printLeaves(BTreeNode node){
        if(node==null) return "";
        else{
            if(node.left==null && node.right==null) // Es una hoja
                return node.data.toString() + " ";
            else
                return printLeaves(node.left) + printLeaves(node.right);
        }
    }

    public String printNodes1Child() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return printNodes1Child(root);
    }
    private String printNodes1Child(BTreeNode node) {
        if (node == null)
            return "";
        else {
            String result = "";
            if ((node.left != null && node.right == null) || (node.left == null && node.right != null)) {
                result += node.data.toString() + " ";
            }
            return result + printNodes1Child(node.left) + printNodes1Child(node.right);
        }
    }

    public String printNodes2Children() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return printNodes2Children(root);
    }
    private String printNodes2Children(BTreeNode node) {
        if (node == null)
            return "";
        else {
            String result = "";
            if (node.left != null && node.right != null) {{

            }
                result += node.data.toString() + " ";
            }
            return result + printNodes2Children(node.left) + printNodes2Children(node.right);
        }
    }

    public String printNodesWithChildren() throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is Empty");

        return printNodesWithChildren(root);
    }

    private String printNodesWithChildren(BTreeNode node){

        if (node == null) return "";

        String result = "";

        if (node.left != null || node.right == null){

            result += "Node " + node.data + " children " ;

            if (node.left != null) {
                result += node.left.data + "" ;
            }

            if (node.right != null){

                result += node.right.data + "";

            }

            result += "\n";
        }

        result += printNodesWithChildren(node.left);
        result += printNodesWithChildren(node.right);

        return result;

    }

    public String printSubTree() throws TreeException{

        if (isEmpty())
            throw new TreeException("Binary Tree is Empty");

        return printSubTree(root);
    }

    private String printSubTree(BTreeNode node){


        return printSubTree(node.left);
    }

    public int totalLeaves() throws TreeException{

        if (isEmpty())
            throw new TreeException("Binary Tree is Empty");

        return totalLeaves(root);
    }

    private int totalLeaves(BTreeNode node){
            if (node == null) {
                return 0;  // Si el nodo es nulo, no hay hojas en este subárbol
            }

            // Si el nodo no tiene hijos (es una hoja)
            if (node.left == null && node.right == null) {
                return 1;  // Contamos esta hoja
            }

            // Recursión en los subárboles izquierdo y derecho
            return  totalLeaves(node.left) + totalLeaves(node.right);

    }



}
