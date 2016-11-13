/**
 * Created by aaron@vcra.net on 13/11/16.
 */
public class parent extends CharObj {
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
        super.setQty(getLeft().getQty()+getRight().getQty());
    }

}
