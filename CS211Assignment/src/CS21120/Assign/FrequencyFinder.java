package CS21120.Assign;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Frequency Finder
 * @version 0.1
 * Find the frequency of characters in a
 * Created by aaron@vcra.net on 10/11/16.
 */
class FrequencyFinder {
    private BufferedInputStream reader;
    private String textFileLocation;
    private Map<Integer, Integer> charCount;

    /**
     * Sets the text file to read
     */
    void setTextFile(String file) {
        this.textFileLocation=file;
    }

    /**
     * Creates a reader to read the file
     */
    private void setReader(){
        try{
            this.reader = new BufferedInputStream(new FileInputStream(this.textFileLocation));
        }
        catch (FileNotFoundException e){e.printStackTrace();}
    }

    /**
     * Creates a new map to store the counts of files in
     */
    private void createMap(){
        charCount = new HashMap<>();
    }

    /**
     * @return a map with the frequencys of a file in
     */
    Map<Integer, Integer> getMap() {
        return charCount;
    }

    /**
     * Reads the file and adds it into the map
     */
    private void readFile(){
        int o;//Stores the current value of the object we are getting
        try {
            while ((o = reader.read()) != -1) { //The number of characters left in the file is greater than 0
                if(charCount.containsKey(o)) // Our map already contains the key that has just been found ing the file
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

    /**
     * Runs the sequence of events needed to create a map
     */
    void makeMap() {
        //We could throw an error if they is currently no text file set
        setReader();
        createMap();
        readFile();
        //charCount.forEach((a,b)-> System.out.println((char) a.intValue()+", "+b));
    }

}
