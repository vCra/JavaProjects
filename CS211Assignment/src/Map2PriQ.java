import java.util.*;
import java.lang.StringBuilder;
/**
 * Created by aaron@vcra.net on 12/11/16.
 */
public class Map2PriQ {
    private Map<Integer, Integer> charCount;
    private CharObj head;
    private Map dict;
    private PriorityQueue<CharObj> charList = new PriorityQueue<CharObj>(5, new Comparator<CharObj>() {public int compare(CharObj char1, CharObj char2){
            if (char1.getQty() < char2.getQty()){
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

    public void firstTree() {
        if (charList.size()<2){
            System.out.println("Specified file does not contain enough variation between characters!");
            System.exit(4);
        }
        CharObj currentHead = new CharObj();
        currentHead.setLeft((CharObj) charList.poll());
        currentHead.setRight((CharObj) charList.poll());
        currentHead.genQty();
        currentHead.setKey(-1);
        charList.offer(currentHead);
        this.head = currentHead;
        makeTree();
    }

    public Map getDict() {
        return dict;
    }

    public void makeTree() {
        //System.out.print(charList);

        CharObj newHead = new CharObj();
        CharObj obj1 = charList.poll();
        CharObj obj2 = charList.poll();

        newHead.setLeft(obj1);
        newHead.setRight(obj2);
        newHead.setKey(-1);
        newHead.genQty();

        //System.out.println("last qty - " + newHead.getQty());// + " | obj qty - " + obj.getQty());
        if (getCharList().size() > 0){
            charList.add(newHead);
            makeTree();

        }
        else{
            this.head = newHead;
            dict = new HashMap<Integer, String>();
            genCodes(newHead, "", dict);


        }
    }
    public void genCodes(CharObj node, String previous, Map dict){
        if (node.getKey() != (-1)){
            //System.out.println(node.getKey() + " - " + previous);
            dict.put(node.getKey(), previous);

        }
        else {
            this.genCodes(node.getLeft(),previous+"0", dict);
            this.genCodes(node.getRight(), previous+"1", dict);
        }
    }
}