import java.util.*;

/**
 * Created by aaron@vcra.net on 13/11/16.
 */
public class treeMaker {
    private parent head;
    private PriorityQueue charList;

    public PriorityQueue getCharList() {
        return charList;
    }

    public void setCharList(PriorityQueue charList) {
        this.charList = charList;
    }

    public void init() {
        parent currentHead = new parent();
        currentHead.setLeft((CharObj) charList.poll());
        currentHead.setRight((CharObj) charList.poll());
        currentHead.genQty();
        this.head = currentHead;
    }

    public void main() {
        parent lastHead = this.head;
        this.head = new parent();
        if (getCharList().size() > 0){
            CharObj obj = (CharObj) charList.poll();
            System.out.println("last qty - " + lastHead.getQty());// + " | obj qty - " + obj.getQty());
            if (lastHead.getQty() > obj.getQty()){
                this.head.setLeft(lastHead);
                this.head.setRight(obj);
                this.head.genQty();
            }
            else{
                this.head.setRight(lastHead);
                this.head.setLeft(obj);
                this.head.genQty();
            }
            main();
        }
    }
    public void genNumbers(parent p, int bin){
        //
    }
}

