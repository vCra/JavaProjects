/**
 * Created by aaron@vcra.net on 10/11/16.
 */
import java.util.Map;
public class aaw13CS21120Assign {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        FrequencyFinder finder = new FrequencyFinder();
        finder.setTextFile("/test/shakespere.txt");
        finder.makeMap();

        Map chars = finder.getMap();
        Map2PriQ mapConverter = new Map2PriQ(chars);
        mapConverter.getFromMap();
        //mapConverter.printQ();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        treeMaker maker = new treeMaker();
        maker.setCharList(mapConverter.getCharList());
        maker.init();
        maker.main();
        System.out.println(totalTime);

    }
}
