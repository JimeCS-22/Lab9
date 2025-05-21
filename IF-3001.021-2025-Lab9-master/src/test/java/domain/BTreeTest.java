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
        System.out.println(bTree);
        try {
            System.out.println("BTree size: "+bTree.size());
            System.out.println("BTree contains: " + bTree.contains(40));
            System.out.println("BTree max: " + bTree.max());
            System.out.println("BTree min: " + bTree.min());
            System.out.println("BTree height: " + bTree.height());
            System.out.println("BTree remove number 20");
            bTree.remove(40);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}