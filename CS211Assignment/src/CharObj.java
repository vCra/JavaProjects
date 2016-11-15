/**
 * Created by aaron@vcra.net on 12/11/16.
 */
public class CharObj {
    public int key;
    public int qty;
    public int bin;
    private CharObj left;
    private CharObj right;

    public CharObj getLeft() {
        return left;
    }

    public void setLeft(CharObj left) {
        this.left = left;
    }

    public CharObj getRight() {
        return right;
    }

    public void setRight(CharObj right) {
        this.right = right;
    }

    public void genQty(){
        setQty(getLeft().getQty()+getRight().getQty());
    }
    public CharObj(int key, int qty){
        setKey(key);
        setQty(qty);
    }
    public CharObj(){};

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String toString(){
        return ("Key "+ getKey() + " occurs " + getQty() + " times.");
    }
}
