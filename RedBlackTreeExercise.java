package rbtree;




public class RBTree<T extends Comparable<T>, P> {

    private static final boolean BLACK   = true;
    private static final boolean RED = false;

    private Node root;

    private class Node {
        private T s;
        private P p;
        private Node left, right;
        private boolean colour;
        private int size;

        public Node(T s, P p, boolean colour, int size) {
            this.s = s;
            this.p = p;
            this.colour = colour;
            this.size = size;

        }
    }

    public RBTree() {
    }

    private boolean isRed(Node z) {
        if (z == null) return false;
        return z.colour == RED;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T s) {
        return fetch(s) != null;
    }

    public P fetch(T s) {
        if (s == null) throw new IllegalArgumentException("argument to get() is null");
        return fetch(root, s);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private P fetch(Node z, T s) {
        while (z != null) {
            int cmp = s.compareTo(z.s);
            if      (cmp < 0) z = z.left;
            else if (cmp > 0) z = z.right;
            else              return z.p;
        }
        return null;
    }


    /**
     * Add a new item to the tree.
     *
     * @param s the item to add
     */
    public void add(T s, P p) {
        if (s == null) System.out.println("you must include an element");
        if (p == null) {
            delete(s);
            return;
        }

        root = add(root, s, p);
        root.colour = BLACK;

    }


    private Node add(Node temp, T s, P p) {
        if (temp == null) return new Node(s, p, RED, 1);
        int cmp = s.compareTo(temp.s);
        if      (cmp < 0) temp.left  = add(temp.left,  s, p);
        else if (cmp > 0) temp.right = add(temp.right, s, p);
        else              temp.p   = p;


        if (isRed(temp.right) && !isRed(temp.left))      temp = rotateLeft(temp);
        if (isRed(temp.left)  &&  isRed(temp.left.left)) temp = rotateRight(temp);
        if (isRed(temp.left)  &&  isRed(temp.right))     flipColors(temp);
        temp.size = size(temp.left) + size(temp.right) + 1;

        return temp;
    }

    public void delete(T s) {
        if (s == null) System.out.println ("the value of the element is null");
        if (!contains(s)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.colour = RED;

        root = delete(root, s);
        if (!isEmpty()) root.colour = BLACK;
        // assert check();
    }

    // delete the s-value pair with the given s rooted at h
    private Node delete(Node temp, T s) {
        // assert get(h, s) != null;

        if (s.compareTo(temp.s) < 0)  {
            if (!isRed(temp.left) && !isRed(temp.left.left))
                temp = moveRedLeft(temp);
            temp.left = delete(temp.left, s);
        }
        else {
            if (isRed(temp.left))
                temp = rotateRight(temp);
            if (s.compareTo(temp.s) == 0 && (temp.right == null))
                return null;
            if (!isRed(temp.right) && !isRed(temp.right.left))
                temp = moveRedRight(temp);
            if (s.compareTo(temp.s) == 0) {
                Node x = min(temp.right);
                temp.s = x.s;
                temp.p = x.p;
                // temp.val = get(temp.right, min(temp.right).s);
                // temp.s = min(temp.right).s;
                temp.right = deleteMin(temp.right);
            }
            else temp.right = delete(temp.right, s);
        }
        return balance(temp);
    }

    public T min() {
        if (isEmpty()) System.out.println ("empty symbol table");
        return min(root).s;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node z) {
        // assert x != null;
        if (z.left == null) return z;
        else                return min(z.left);
    }

    public T max() {
        if (isEmpty()) System.out.println ("empty symbol table");
        return max(root).s;
    }

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node z) {
        // assert x != null;
        if (z.right == null) return z;
        else                 return max(z.right);
    }

    public void deleteMin() {
        if (isEmpty()) System.out.println ("Table emptied");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.colour = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.colour = BLACK;
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node temp) {
        if (temp.left == null)
            return null;

        if (!isRed(temp.left) && !isRed(temp.left.left))
            temp = moveRedLeft(temp);

        temp.left = deleteMin(temp.left);
        return balance(temp);
    }


    // restore red-black tree invariant
    private Node balance(Node temp) {
        // assert (h != null);

        if (isRed(temp.right))                      temp = rotateLeft(temp);
        if (isRed(temp.left) && isRed(temp.left.left)) temp = rotateRight(temp);
        if (isRed(temp.left) && isRed(temp.right))     flipColors(temp);

        temp.size = size(temp.left) + size(temp.right) + 1;
        return temp;
    }

    private Node rotateRight(Node temp) {

        Node z = temp.left;
        temp.left = z.right;
        z.right = temp;
        z.colour = z.right.colour;
        z.right.colour = RED;
        z.size = temp.size;
        temp.size = size(temp.left) + size(temp.right) + 1;
        return z;
    }


    private Node rotateLeft(Node temp) {

        Node z = temp.right;
        temp.right = z.left;
        z.left = temp;
        z.colour = z.left.colour;
        z.left.colour = RED;
        z.size = temp.size;
        temp.size = size(temp.left) + size(temp.right) + 1;
        return z;
    }


    private void flipColors(Node temp) {

        temp.colour = !temp.colour;
        temp.left.colour = !temp.left.colour;
        temp.right.colour = !temp.right.colour;
    }


    private Node moveRedLeft(Node temp) {


        flipColors(temp);
        if (isRed(temp.right.left)) {
            temp.right = rotateRight(temp.right);
            temp = rotateLeft(temp);
            flipColors(temp);
        }
        return temp;
    }


    private Node moveRedRight(Node temp) {
        flipColors(temp);
        if (isRed(temp.left.left)) {
            temp = rotateRight(temp);
            flipColors(temp);
        }
        return temp;
    }



    /**
     * The number of items that have been added to the tree
     *
     * @return The count of items
     */
    public int size() {
        return size(root);
    }

    private int size(Node z) {
        if (z == null) return 0;
        return z.size;
    }


    /**
     * Find an item by its rank according to the natural comparable order
     * of all items that have been added to the tree.
     * @param rank The index of the item to find, which must satisfy: 0 <= rank < size()
     * @return The item previous added to the tree.
     */

    public T get(int rank) {
        if (rank < 0 || rank >= size()) {
            System.out.println ("invalid rank");
        }
        Node z = get(root, rank);
        return z.s;
    }

    // the key of rank k in the subtree rooted at x
    private Node get(Node z, int rank) {
        // assert x != null;
        // assert k >= 0 && k < size(x);
        int t = size(z.left);
        if      (t > rank) return get(z.left,  rank);
        else if (t < rank) return get(z.right, rank-t-1);
        else            return z;
    }



    /**
     * Search for an item that was previously added to the tree, and return its current
     * rank in the sorted collection according to the comparable natural order of all
     * items in the tree. Return -1 if the item has not been added to the tree yet.
     * @param s The item to search for in the tree
     * @return The rank/index of the item in this collection according to the natural
     *         comparable order of all items added to the tree, or -1 if the item
     *         has not been added.
     */

    public int rank(T s) {
        if (s == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(s, root);
    }

    // number of keys less than key in the subtree rooted at x
    private int rank(T s, Node z) {
        if (z == null) return 0;
        int cmp = s.compareTo(z.s);
        if      (cmp < 0) return rank(s, z.left);
        else if (cmp > 0) return 1 + size(z.left) + rank(s, z.right);
        else              return size(z.left);
    }

    public static void main(String[] args) {
        int i, ilen = a.lenth;
        RedBlackBST<String, Integer> tree = new RedBlackBST<String, Integer>();
        for (int i = 0; i < ilen; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        for(i=0; i<ilen; i++) {
            tree.add(a[i]);
            System.out.printf("== added node: %d\n", a[i]);
            tree.print();
            System.out.printf("\n");
        }

    }


}
