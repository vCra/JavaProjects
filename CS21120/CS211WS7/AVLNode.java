public class AVLNode {
    private int element;        // an element stored at this node
    private AVLNode parent;     // a reference to the parent node (if any)
    private AVLNode left;       // a reference to the left child (if any)
    private AVLNode right;      // a reference to the right child (if any)

    private int height=0;       // the height of the node

    AVLNode (int e)
    {
        element = e;
        this.parent = null;
        left = null;
        right = null;
    }
    AVLNode(int e, AVLNode parent, AVLNode leftChild, AVLNode rightChild) {
        element = e;
        this.parent = parent;
        left = leftChild;
        right = rightChild;
    }

    // get methods
    public int getElement() { return element; }
    public AVLNode getParent() { return parent; }
    public AVLNode getLeft() { return left; }
    public AVLNode getRight() { return right; }
    public int getHeight() { return height; }

    // set methods
    public void setElement(int e) { element = e; }
    public void setParent(AVLNode parentNode) { parent = parentNode; }
    public void setLeft(AVLNode leftChild) { left = leftChild; }
    public void setRight(AVLNode rightChild) { right = rightChild; }
    public void setHeight(int value) { height = value; }

    /** Return true if node is root **/
    public boolean isRoot() { return (parent == null);};

    /** Returns true if Position p has one or more children. */
    public boolean isInternal() { return numChildren() > 0; }

    /** Returns true if Position p does not have any children. */
    public boolean isExternal() { return numChildren() == 0; }

    /** Returns the Position of p's sibling (or null if no sibling exists). */
    public AVLNode sibling() {
        if (parent == null) return null;    // p must be the root
        if (this == parent.getLeft())       // p is a left child
            return parent.getRight();       // (right child might be null)
        else                                // p is a right child
            return parent.getLeft();        // (left child might be null)
    }

    /** Returns the number of children of Position p. */
    public int numChildren() {
        int count=0;
        if (left != null)
            count++;
        if (right != null)
            count++;
        return count;
    }

    /** Recomputes the height of the given position based on its children's heights. */
    public void recomputeHeight() {
        height = 1 + Math.max(left.getHeight(), right.getHeight());
    }

    /** Returns whether a position has balance factor between -1 and 1 inclusive. */
    public boolean isBalanced() {
        return Math.abs(left.getHeight() - right.getHeight()) <= 1;
    }

    public void rotate(){

    }
    /** Returns a child of p with height no smaller than that of the other child. */
    public AVLNode tallerChild() {
        if (left.getHeight() > right.getHeight()) return left;     // clear winner
        if (left.getHeight() < right.getHeight()) return right;    // clear winner
        // equal height children; break tie while matching parent's orientation
        if (isRoot()) return left;                      // choice is irrelevant
        if (this == parent.getLeft()) return left;      // return aligned child
        else return right;
    }
}
