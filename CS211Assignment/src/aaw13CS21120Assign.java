/**
 * Created by aaron@vcra.net on 10/11/16.
 */
import java.io.File;
import java.util.Map;
public class aaw13CS21120Assign {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String fileName="/test/shakespere.txt";

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
        long compressedSize = fh.genSize(fileName);
        File file = new File(fileName);
        float originalSize = file.length();
        System.out.println("Original size   - " + (long) originalSize);
        System.out.println("Compressed size - " + compressedSize);
        float percentageDifference = (originalSize-compressedSize)/originalSize*100;
        System.out.println("Difference is " + percentageDifference + "%");

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time - " + totalTime);

    }
}
