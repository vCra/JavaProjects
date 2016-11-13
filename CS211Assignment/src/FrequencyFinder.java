import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Frequency Finder
 *
 * Find the frequency of characters in a
 * Created by aaron@vcra.net on 10/11/16.
 */
public class FrequencyFinder {
    private BufferedReader reader;
    private String textFileLocation;
    private Map<Integer, Integer> charCount;

    public void setTextFile(String file){
        this.textFileLocation=file;
    }

    private void setReader(){
        try{
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.textFileLocation)));
        }
        catch (FileNotFoundException e){e.printStackTrace();}
    }

    private void createMap(){
        charCount = new HashMap<>();
    }
    public Map getMap(){
        return charCount;
    }
    private void file2map(){
        int o;//Stores the current value of the object we are getting
        try {
            while ((o=reader.read())>0){
                if(charCount.containsKey(o))
                {
                    charCount.put(o,charCount.get(o)+1); //If it exists in the map already, then get the current count and add 1 to it.
                }
                else //If it does not exist
                {
                    charCount.put(o, 1); //set the count as 1
                }
            }
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
    }
    public void makeMap(){
        setReader();
        createMap();
        file2map();
        //charCount.forEach((a,b)-> System.out.println((char) a.intValue()+", "+b));
    }

}
