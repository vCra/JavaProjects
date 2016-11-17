/**
 * Created by aaron@vcra.net on 10/11/16.
 */
import java.util.Map;
public class aaw13CS21120Assign {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        FrequencyFinder finder = new FrequencyFinder();
        finder.setTextFile("/test/bitFull");
        finder.makeMap();

        Map chars = finder.getMap();
        Map2PriQ mapConverter = new Map2PriQ(chars);
        mapConverter.getFromMap();
        //mapConverter.printQ();
        mapConverter.firstTree();

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);

    }
}
