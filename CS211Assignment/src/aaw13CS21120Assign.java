/**
 * Created by aaron@vcra.net on 10/11/16.
 */
import java.io.File;
import java.util.Map;
import java.util.Scanner;

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

            Map chars = finder.getMap();
            Map2PriQ mapConverter = new Map2PriQ(chars);
            mapConverter.getFromMap();
            //mapConverter.printQ();
            mapConverter.makeNewTree();

            fileHandler fh = new fileHandler();
            fh.setDict(mapConverter.getDict());

            float huffmanSize = fh.genSize(fileName);
            float originalSize = file.length();
            float fixedSize = fh.genFixedLengthSize(file, mapConverter.getDict());
            float percentageDifference = (originalSize - huffmanSize) / originalSize * 100;

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            System.out.println("The Original size of the file was " + (long) originalSize + "bytes.");
            System.out.println("When Compressed with huffman encoding the size is " + (long) huffmanSize + "bytes.");
            System.out.println("When Compressed with Fixed Length encoding the size is " + (long) fixedSize + "bytes\n");

            System.out.println("Difference is " + percentageDifference + "%\n");
            System.out.println("Time - " + totalTime + "ms\n");
            System.out.println("Would you like to save a dictionry for the encoding? (y/n): ");
            if (scanner.next().equals("y")) {
                fh.genDictFile(mapConverter.getDict(), fileName);
            }

        } else {
            System.out.println("Unable to find file " + fileName);
        }
    }
}
