package rbtree;

import rbtree.RBTree.inOrderDo;


public class RBTreeTest {
    static RBTree<Integer> tree;
    private static final int a[] = {57941, 19935, 2175, 32282, 20911, 1960, 79968, 6392, 20000, 37815, 21162, 12894, 35126, 30770, 71252, 58001, 17209, 37841, 34888, 11160, 3320, 17107, 99045, 16244, 48333, 61838, 36339, 10222, 55102, 29108, 58750, 57739, 9340, 32630, 3616, 52891, 69059, 81097, 22233, 27319, 35931, 92675, 76167, 21625, 75647, 72613, 85224, 92924, 78440, 47786, 63182, 80427, 24990, 73918, 58248, 485, 38046, 91659, 60152, 68019, 98886, 46951, 6406, 28109, 10805, 74760, 16212, 23752, 77153, 34023, 36495, 76768, 58283, 38535, 7105, 23906, 56312, 29960, 6945, 96364, 23522, 19659, 77994, 30940, 7319, 39119, 5315, 99762, 38197, 72606, 93325, 46032, 45295, 80234, 54237, 93732, 97855, 96864, 35241, 72474};
    private static final boolean mDebugInsert = false;    
    private static final boolean mDebugDelete = false;    

    public static void main(String[] args) {
        int i, ilen = a.length;
        tree = new RBTree<Integer>();

        System.out.printf("== Raw Input: ");
        for (i = 0; i < ilen; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        for (i = 0; i < ilen; i++) {
            tree.insert(a[i]);
            
            if (mDebugInsert) {
                System.out.printf("== added node: %d\n", a[i]);
                System.out.printf("== tree details: \n");
                tree.print();
                System.out.printf("\n");
            }
        }

        System.out.printf("== PreOrder Traversal: ");
        tree.preOrder();

        System.out.printf("\n== InOrder Traversal: ");
        tree.inOrder();

        System.out.printf("\n== PostOrder Traversal: ");
        tree.postOrder();
        System.out.printf("\n");

        System.out.printf("== min: %s\n", tree.minimum());
        System.out.printf("== max: %s\n", tree.maximum());
        System.out.printf("== details: \n");
        tree.print();
        System.out.printf("\n");

        // 设置mDebugDelete=true,test remove func
        if (mDebugDelete) {
            for (i = 0; i < ilen; i++) {
                tree.remove(a[i]);

                System.out.printf("== Delete Node: %d\n", a[i]);
                System.out.printf("== Tree Details: \n");
                tree.print();
                System.out.printf("\n");
            }
        }

        System.out.println(tree.search(6392).key + "");
        System.out.println(tree.xxing(7105).key + "");
        System.out.println(tree.sxing(23906).key + "");
        System.out.println(tree.xxing(38535).key + "");

    }
}



