 class AVLTree {

    /** The root of the binary tree */
    private AVLNode root = null;

    /** The number of nodes in the binary tree */
    private int size = 0;

    /** Constructs an empty tree. */
    public AVLTree() {
        root = createNode(-1, null, null, null);
        size = 1;
    }

    // Auxiliary methods

    /** Returns the root Position of the tree (or null if tree is empty). */
    public AVLNode root() { return root; }

    /** Returns the number of nodes in the tree. */
    public int size() { return size; }

    /** Tests whether the tree is empty. */
    public boolean isEmpty() { return size() == 0; }

    // Method for searching in the tree

    /** Returns the position with the maximum key in the subtree rooted at p. */
    private AVLNode treeMax(AVLNode p) {
        AVLNode walk = p;
        while (walk.isInternal())
            walk = walk.getRight();
        return walk.getParent();              // we want the parent of the leaf
    }

    /** Returns the position in p's subtree having the given key (or else the terminal leaf). */
    private AVLNode treeSearch(AVLNode p, int key) {
        if (p.isExternal())
            return p;                          // key not found; return the final leaf
        if (key == p.getElement())
            return p;                          // key found; return its position
        else if (key < p.getElement())
            return treeSearch(p.getLeft(), key);   // search left subtree
        else
            return treeSearch(p.getRight(), key);  // search right subtree
    }

    // AVL tree update methods

    /** Removes element from AVL tree and performs rotations if necessary */
    public void remove(int element) throws IllegalArgumentException {
        AVLNode p = treeSearch(root(), element);
        if (p.isExternal()) {
            System.out.println(element+" not in tree");// key not found
        } else {
            if (p.getLeft().isInternal() && p.getRight().isInternal()) { // both children are internal
                AVLNode replacement = treeMax(p.getLeft());
                set(p, replacement.getElement());
                p = replacement;
            } // now p has at most one child that is an internal node
            AVLNode leaf = (p.getLeft().isExternal() ? p.getLeft() : p.getRight());
            AVLNode sib = leaf.sibling();
            remove(leaf);
            remove(p);                            // sib is promoted in p's place
            rebalanceDelete(sib);                 // hook for balanced tree subclasses
        }
    }

    /** Adds element to AVL tree and performs rotations if necessary */
    public void put(int element) throws IllegalArgumentException {
        AVLNode p = treeSearch(root(), element);
        if (p.isExternal()) {                    // key is new
            expandExternal(p, element);
            rebalance(p);                   // hook for balanced tree subclasses
        } else {                                // replacing existing key
            System.out.println(element+" already in tree");
        }
    }

    // Auxiliary update methods

    /** Factory function to create a new node storing element e. */
    private AVLNode createNode(int e, AVLNode parent, AVLNode left, AVLNode right) {
        return new AVLNode(e, parent, left, right);
    }

    /** Utility used when inserting a new entry at a leaf of the tree */
    private void expandExternal(AVLNode p, int newEntry) {
        set(p, newEntry);       // store new entry at p
        addLeft(p, -1);         // add new sentinel leaves as children
        addRight(p, -1);
    }

    /** Creates a new left child of Position p storing element e and returns its Position.*/
    private AVLNode addLeft(AVLNode p, int e)
            throws IllegalArgumentException {
        if (p.getLeft() != null)
            throw new IllegalArgumentException("p already has a left child");
        AVLNode child = createNode(e, p, null, null);
        p.setLeft(child);
        size++;
        return child;
    }

    /** Creates a new right child of Position p storing element e and returns its Position. */
    private AVLNode addRight(AVLNode p,int e)
            throws IllegalArgumentException {
        if (p.getRight() != null)
            throw new IllegalArgumentException("p already has a right child");
        AVLNode child = createNode(e, p, null, null);
        p.setRight(child);
        size++;
        return child;
    }

    /** Replaces the element at Position p with element e and returns the replaced element. */
    private int set(AVLNode p, int e) throws IllegalArgumentException {
        int temp = p.getElement();
        p.setElement(e);
        return temp;
    }

    /** Removes the node at Position p and replaces it with its child, if any. */
    private int remove(AVLNode p) throws IllegalArgumentException {
        if (p.numChildren() == 2)
            throw new IllegalArgumentException("p has two children");
        AVLNode child = (p.getLeft() != null ? p.getLeft() : p.getRight() );
        if (child != null)
            child.setParent(p.getParent());  // child's grandparent becomes its parent
        if (p == root)
            root = child;                       // child becomes root
        else {
            AVLNode parent = p.getParent();
            if (p == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size--;
        int temp = p.getElement();
        p.setElement(-1);                // help garbage collection
        p.setLeft(null);
        p.setRight(null);
        p.setParent(p);                 // our convention for defunct node
        return temp;
    }

    // Methods to implement rotation

    /** Rebalancing called after a deletion. */
    private void rebalanceDelete(AVLNode p) {
        if (!p.isRoot())
            rebalance(p.getParent());
    }

    /**
     * Utility used to rebalance after an insert or removal operation. This traverses the
     * path upward from p, performing a trinode restructuring when imbalance is found,
     * continuing until balance is restored.
     */
    private void rebalance(AVLNode p) {
        int oldHeight, newHeight;
        do {
            oldHeight = p.getHeight();                    // not yet recalculated if internal
            if (!p.isBalanced()) {                        // imbalance detected
                // perform trinode restructuring, setting p to resulting root,
                // and recompute new local heights after the restructuring
                p = restructure(p.tallerChild().tallerChild());
                p.getLeft().recomputeHeight();
                p.getRight().recomputeHeight();
            }
            p.recomputeHeight();
            newHeight = p.getHeight();
            p = p.getParent();
        } while (oldHeight != newHeight && p != null);
    }

    /**
     *
     * Returns the AVLNode that becomes the root of the restructured subtree.
     *
     * Assumes the nodes are in one of the following configurations:
     *<pre>
     *     z=a                 z=c           z=a               z=c
     *    /  \                /  \          /  \              /  \
     *   t0  y=b             y=b  t3       t0   y=c          y=a  t3
     *      /  \            /  \               /  \         /  \
     *     t1  x=c         x=a  t2            x=b  t3      t0   x=b
     *        /  \        /  \               /  \              /  \
     *       t2  t3      t0  t1             t1  t2            t1  t2
     *</pre>
     * The subtree will be restructured so that the node with key b becomes its root.
     *<pre>
     *           b
     *         /   \
     *       a       c
     *      / \     / \
     *     t0  t1  t2  t3
     *</pre>
     * Caller should ensure that x has a grandparent.
     */
    private AVLNode restructure(AVLNode x) {
        AVLNode y = x.getParent();
        AVLNode z = y.getParent();
        if ((x == y.getRight()) == (y == z.getRight())) {   // matching alignments
            rotate(y);                                      // single rotation (of y)
            return y;                                       // y is new subtree root
        } else {                                            // opposite alignments
            rotate(x);                                      // double rotation (of x)
            rotate(x);
            return x;                                       // x is new subtree root
        }
    }

    /** Relinks a parent node with its oriented child node. */
    private void relink(AVLNode parent, AVLNode child, boolean makeLeftChild) {
        child.setParent(parent);
        if (makeLeftChild)
            parent.setLeft(child);
        else
            parent.setRight(child);
    }

    /**
     * Rotates AVLNode p above its parent.  Switches between these
     * configurations, depending on whether p is a or p is b.
     *<pre>
     *          b                  a
     *         / \                / \
     *        a  t2             t0   b
     *       / \                    / \
     *      t0  t1                 t1  t2
     *</pre>
     *  Caller should ensure that p is not the root.
     *
     *  Tip: The method is invoked from restructure(AVLNode x) and should make use
     *  of private void relink(AVLNode parent, AVLNode child, boolean makeLeftChild).
     */
    private void rotate(AVLNode x) {
        // Not implemented, yet.
    }
}
