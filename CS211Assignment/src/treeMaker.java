import java.util.*;

/**
 * Created by aaron@vcra.net on 13/11/16.
 */
public class treeMaker {
    private CharObj head;
    private PriorityQueue charList;

    public PriorityQueue getCharList() {
        return charList;
    }

    public void setCharList(PriorityQueue charList) {
        this.charList = charList;
    }

    public void init() {
        CharObj currentHead = new CharObj();
        currentHead.setLeft((CharObj) charList.poll());
        currentHead.setRight((CharObj) charList.poll());
        currentHead.genQty();
        charList.offer(currentHead);
        this.head = currentHead;
    }

    public void main() {
        CharObj newHead = new CharObj();
        if (getCharList().size() > 1){
            CharObj obj1 = (CharObj) charList.poll();
            CharObj obj2 = (CharObj) charList.poll();
            if (obj2.getQty() > obj1.getQty()){
                newHead.setLeft(obj2);
                newHead.setRight(obj1);
                newHead.genQty();
                charList.offer(newHead);
            }
            else{
                newHead.setRight(obj2);
                newHead.setLeft(obj1);
                newHead.genQty();
                charList.offer(newHead);
            }
            System.out.println("last qty - " + newHead.getQty());// + " | obj qty - " + obj.getQty());
            main();
        }
        else{
            this.head = newHead;
        }
    }
    public void genNumbers(CharObj p, int bin){
        //
    }


}

