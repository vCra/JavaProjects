package CS21120.Assign;

import java.util.*;


/**
 * Created by aaron@vcra.net on 12/11/16.
 * Builds a huffman tree and adds nodes to it in order to generate a dict for encoding a file
 */
class TreeBuilder {
    private final PriorityQueue<CharObj> charList = new PriorityQueue<>(5, new Comparator<CharObj>() {
        public int compare(CharObj char1, CharObj char2) {
            if (char1.getQty() < char2.getQty()){
                return -1;
            } else if (char1.getQty() == char2.getQty()){
                return 0;
            }
            else return 1;
        }
    });
    private Map<Integer, Integer> charCount;
    private Map<Integer, String> dict;
    private CharObj tree;

    /**
     * Constructor for the tree builder
     *
     * @param map the map to build the tree from
     */
    TreeBuilder(Map<Integer, Integer> map) {
        setMap(map);
    }

    /**
     * Returns the Priority queue used to construct the tree
     *
     * @return Map charList the charList used to create the tree
     */
    private PriorityQueue<CharObj> getCharList() {
        return charList;
    }

    /**
     * Adds a charObj to the priority queue
     * @param e the charObj to add
     */
    private void addToQ(CharObj e){
        charList.offer(e);
    }

    /**
     * Sets the map
     *
     * @param map the Map to set
     */
    private void setMap(Map<Integer, Integer> map){
        this.charCount = map;
    }

    /**
     * Converts 2 ints to a charObj
     * @param a the Key for the charObj
     * @param b the Quantity for the charObj
     * @return CharObj the new CharObj with the new values
     */
    private CharObj convert(int a, int b) {
        return new CharObj(a, b);
    }

    /**
     * Gets all elements from the map, converts them in to charObjs and then adds them to the priority Queue
     */
    void getFromMap(){
        Iterator it = charCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            addToQ(convert((int)pair.getKey(), (int)pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
/*

    private void printQ(){ //Will empty the charList...
        while (charList.size() > 0){
            System.out.println(charList.poll().toString());
        }
    }
*/

    /**
     * Creates the tree
     */
    void makeNewTree() {
        if (charList.size()<2){
            System.out.println("Specified file does not contain enough variation between characters!");
            System.exit(4);
        }
        CharObj currentHead = new CharObj();
        currentHead.setLeft(charList.poll());
        currentHead.setRight(charList.poll());
        currentHead.genQty();
        currentHead.setKey(-1);
        charList.offer(currentHead);
        makeTreeNode();
    }

    Map getDict() {
        return dict;
    }

    /**
     * Makes anode of a tree recursively
     */
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

        } else {
            dict = new HashMap<>();
            genCodes(newHead, "", dict);
            tree = newHead;
        }
    }

    /**
     * gets the total number of nodes in the tree
     *
     * @return int the number of nodes
     */
    int getNodeCount(){
        return genNodeCount(tree, 0);
    }
    private int genNodeCount(CharObj node, int currentCount){
        if (node.getKey() != (-1)){
            return currentCount+1;
        } else {
            return this.genNodeCount(node.getLeft(), currentCount) + this.genNodeCount(node.getRight(), currentCount) + 1;
        }
    }

    /**
     * gets the height of the tree
     * @return int the height of the tree
     */
    int getHeight(){
        return findDeepest(tree, 0);
    }
    private int findDeepest(CharObj node, int deepest){
        if (node.getKey() != (-1)){
            if (node.getDepth() > deepest){
                return node.getDepth();
            }
            else{
                return deepest;
            }
        }
        else {
            return Math.max(findDeepest(node.getLeft(), deepest), findDeepest(node.getRight(), deepest));
        }
    }

    /**
     * Gets the average depth of the tree
     *
     * @return float the average depth of the tree
     */
    float getAverageDepth(){
        //System.out.println("Total Depth:"+ genTotalDepths(tree));
        return (float) genTotalDepths(tree)/(float)genNodeCount(tree, 0);
    }
    private int genTotalDepths(CharObj node){
        if (node.getKey() != (-1)){
            return node.getDepth();
        }
        else {
            return this.genTotalDepths(node.getLeft()) + this.genTotalDepths(node.getRight()) + node.getDepth();
        }
    }

    /**
     * Generates the binary encodings for each node of the tre
     * Is a recursive function
     *
     * @param node     the head of the tree to start at
     * @param previous the previous binary string - for a new tree use ""
     * @param dict     - the dict used to store the encodings
     */
    private void genCodes(CharObj node, String previous, Map<Integer, String> dict){
        if (node.getKey() != (-1)){
            //System.out.println(node.getKey() + " - " + previous);
            dict.put(node.getKey(), previous);
            node.setDepth(previous.length());

        }
        else {
            this.genCodes(node.getLeft(),previous+"0", dict);
            this.genCodes(node.getRight(), previous+"1", dict);
            node.setDepth(previous.length());
        }
    }
}