import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;
/**
 * Created by aaron@vcra.net on 12/11/16.
 */
public class Map2PriQ {
    private Map<Integer, Integer> charCount;
    private CharObj head;
    private PriorityQueue<CharObj> charList = new PriorityQueue<CharObj>(5, new Comparator<CharObj>() {public int compare(CharObj char1, CharObj char2){
            if (char1.getQty() > char2.getQty()){
                return -1;
            } else if (char1.getQty() == char2.getQty()){
                return 0;
            }
            else return 1;
        }
    });


    Map2PriQ(Map map){
        setMap(map);
    }
    public PriorityQueue<CharObj> getCharList() {
        return charList;
    }

    public void addToQ(CharObj e){
        charList.offer(e);
    }

    public void setMap(Map map){
        this.charCount = map;
    }

    public CharObj convert(int a, int b){
        return new CharObj(a,b);

    }

    public void getFromMap(){
        Iterator it = charCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            addToQ(convert((int)pair.getKey(), (int)pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    public void printQ(){
        while (charList.size() > 0){
            System.out.println(charList.poll().toString());
        }
    }



















    public void init() {
        System.out.print("hi");
        CharObj currentHead = new CharObj();
        currentHead.setLeft((CharObj) charList.poll());
        currentHead.setRight((CharObj) charList.poll());
        currentHead.genQty();
        charList.offer(currentHead);
        this.head = currentHead;
    }

    public void main() {
        System.out.print(charList);
        CharObj newHead = new CharObj();
        if (getCharList().size() > 1){
            CharObj obj1 = (CharObj) charList.poll();
            CharObj obj2 = (CharObj) charList.poll();
            if (obj2.getQty() > obj1.getQty()){
                newHead.setLeft(obj2);
                newHead.setRight(obj1);
                newHead.genQty();
            }
            else{
                newHead.setRight(obj2);
                newHead.setLeft(obj1);
                newHead.genQty();

            }
            charList.offer(newHead);
            System.out.println("last qty - " + newHead.getQty());// + " | obj qty - " + obj.getQty());
            main();
        }
        else{
            this.head = newHead;
        }
    }
}
