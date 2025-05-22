package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BTreeTest {

    @Test
    void test() {
        BTree bTree = new BTree();
        for (int i = 0; i < 20; i++) {
            bTree.add(util.Utility.random(50));
        }
        System.out.println(bTree);//toString
        try {
            System.out.println("BTree size: "+bTree.size());
            for (int i = 0; i < 20; i++) {
                int value = util.Utility.random(50);
                System.out.println(
                        bTree.contains(value)
                        ?"The value ["+value+"] exists in the BTree"
                        :"The value ["+value+"] does not exist in the BTree");
                if (bTree.contains(value)){
                    bTree.remove(value);
                    System.out.println("The value ["+value+"] has been removed");
                }
            }
            System.out.println(bTree); //toString

//            System.out.println("BTree contains: " + bTree.contains(40));
//            System.out.println("BTree max: " + bTree.max());
//            System.out.println("BTree min: " + bTree.min());
//            System.out.println("BTree height: " + bTree.height());
//            System.out.println("BTree: -" + bTree.printNodes1Child());
//            System.out.println("BTree: " + bTree.printNodes2Children());
//            System.out.println("BTree remove ");
//            bTree.remove(25);
//            System.out.println(bTree);

        } catch (TreeException e) {

            throw new RuntimeException(e);

        }
    }

    @Test
    void testHeight(){
        BTree bTree = new BTree();
        bTree.add(20); bTree.add(30); bTree.add(18); bTree.add(4); bTree.add(5);
        bTree.add(50); bTree.add(70);
        System.out.println(bTree); //toString
        System.out.println();
        try {
            System.out.println("Binary Tree - height (20): " + bTree.height(20));
            System.out.println("Binary Tree - height (30): " + bTree.height(30));
            System.out.println("Binary Tree - height (18): " + bTree.height(18));
            System.out.println("Binary Tree - height (4): " + bTree.height(4));
            System.out.println("Binary Tree - height (5): " + bTree.height(5));
            System.out.println("Binary Tree - height (50): " + bTree.height(50));
            System.out.println("Binary Tree - height (70): " + bTree.height(70));
            System.out.println();
            System.out.println("Binary Tree - height (): " + bTree.height());
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}