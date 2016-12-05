package CS21120.Assign;


import java.io.File;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by aaron@vcra.net on 10/11/16.
 * Assignment for CS21120 - The huffman project
 *
 * @author aaw13
 * @version 0.1
 */
public class aaw13CS21120Assign {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the huffman compression Size Comparator!");
        System.out.print("Please enter a file: ");
        String fileName = scanner.next();
        File file = new File(fileName);

        if (file.isFile()) {
            System.out.println("File found!");
            long startTime = System.currentTimeMillis();
            FrequencyFinder finder = new FrequencyFinder();
            finder.setTextFile(fileName);
            finder.makeMap();

            Map<Integer, Integer> chars = finder.getMap();
            treeBuilder tb = new treeBuilder(chars);
            tb.getFromMap();
            //tb.printQ();
            tb.makeNewTree();

            fileHandler fh = new fileHandler();
            fh.setDict(tb.getDict());

            float huffmanSize = fh.genSize(fileName);
            float originalSize = file.length();
            float fixedSize = fh.genFixedLengthSize(file, tb.getDict());
            //float percentageDifference = (originalSize - huffmanSize) / originalSize * 100;
            float ratio = originalSize/huffmanSize;
            float ratio2 = fixedSize/huffmanSize;
            int nodeCount = tb.getNodeCount();
            int treeHeight = tb.getHeight();
            float averageDepth = tb.getAverageDepth();
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;


            System.out.println("The Original size of the file was " + (long) originalSize + "bytes.");
            System.out.println("When Compressed with huffman encoding the size is " + (long) huffmanSize + "bytes.");
            System.out.println("This gives a difference of ratio " + ratio) ;
            System.out.println("When Compressed with Fixed Length encoding the size is " + (long) fixedSize + "bytes");
            System.out.println("The compression ratio between Fixed length and huffman is: "+ ratio2);
            System.out.println("The height is " + treeHeight);
            System.out.println("The tree has " + nodeCount + " nodes.");
            System.out.println("The average depth of the tree is "+ averageDepth);

            //System.out.println("Difference is " + percentageDifference + "%\n");
            System.out.println("Time - " + totalTime + "ms\n");
            System.out.println("Would you like to save a dictionry for the encoding? (y/n): ");
            if (scanner.next().equals("y")) {
                fh.genDictFile(tb.getDict(), fileName);
            }

        } else {
            System.out.println("Unable to find file " + fileName);
        }
    }
}
