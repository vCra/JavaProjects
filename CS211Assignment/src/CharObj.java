/**
 * Created by aaron@vcra.net on 12/11/16.
 * @author aaw13@aber.ac.uk
 * @version 0.1
 * Stores data about a node
 */
class CharObj {
    private int key; //The ASCII Value of the character we read
    private int qty; //The quantity of these values
    private CharObj left; //The node on the left of the tree
    private CharObj right; //The node on the right of the tree
    private int depth; //How deep the current node is

    CharObj(int key, int qty) {
        setKey(key);
        setQty(qty);
    }

    CharObj() {
    }

    /**
     * Gets the depth of the current node
     *
     * @return the depth of the node
     */
    int getDepth() {
        return depth;
    }

    void setDepth(int depth) {
        this.depth = depth;
    }

    CharObj getLeft() {
        return left;
    }

    void setLeft(CharObj left) {
        this.left = left;
    }

    CharObj getRight() {
        return right;
    }

    void setRight(CharObj right) {
        this.right = right;
    }

    int getKey() {
        return key;
    }

    void setKey(int key) {
        this.key = key;
    }

    int getQty() {
        return qty;
    }

    private void setQty(int qty) {
        this.qty = qty;
    }

    void genQty() {
        setQty(getLeft().getQty() + getRight().getQty());
    }

    public String toString(){
        return ("Key "+ getKey() + " occurs " + getQty() + " times.");
    }
}
