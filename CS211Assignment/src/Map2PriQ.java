import java.util.*;
/**
 * Created by aaron@vcra.net on 12/11/16.
 */
public class Map2PriQ {
    private Map<Integer, Integer> charCount;
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

    private PriorityQueue<CharObj> getCharList() {
        return charList;
    }

    private void addToQ(CharObj e){
        charList.offer(e);
    }

    private void setMap(Map map){
        this.charCount = map;
    }

    private CharObj convert(int a, int b){
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

    private void printQ(){ //Will empty the charList...
        while (charList.size() > 0){
            System.out.println(charList.poll().toString());
        }
    }

    public void makeNewTree() {
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
        makeTreeNode();
    }

    public Map getDict() {
        return dict;
    }

    private void makeTreeNode() {
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
            makeTreeNode();

        }
        else{
            dict = new HashMap<Integer, String>();
            genCodes(newHead, "", dict);


        }
    }
    public int genNodeCount(CharObj node, int currentCount){
        int count = currentCount;
        if (node.getKey() != (-1)){
            return count+1;
        }
        else {
            count = count + this.genNodeCount(node.getLeft(),currentCount);
            count = count + this.genNodeCount(node.getRight(), currentCount);
        }
        return count;
    }
    public void getAverageTreeDepth(CharObj tree){
        {}
    }
    private int genNodeDepth(CharObj node, int depth, int count){
        {}
        return 1;
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