import java.io.*;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by aaron@vcra.net on 17/11/16.
 */
public class fileHandler {
    Map dict;
    BufferedReader reader;
    public void setDict(Map dict){
        this.dict = dict;
    }
    public void main(){
        StringBuilder output = new StringBuilder();
        Iterator it = dict.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            //addToQ(convert((int)pair.getKey(), (int)pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    private void setupReader(String file){
        try{
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public int genFixedLengthBits(int Count) { //Generates the minimunm number of bits we need need per item to store all possible objects.
        return (int) Math.ceil(Math.log(Count) / Math.log(2));
    }

    public long genFixedLengthSize(File file, Map m) {
        int bitLength = genFixedLengthBits(m.size());
        long fileLengthBits = (file.length());
        return (bitLength * fileLengthBits) / 8;
    }
    public long genSize(String file){
        setupReader(file);
        long length = 0;
        int o;//Stores the current value of the object we are getting

        try {
            while ((o=reader.read())>0){
                 //System.out.println(o)
                try{
                    //System.out.println(dict.get(o));
                    length = length +((String) dict.get(o)).length();
                }
                catch (java.lang.NullPointerException e){
                    System.out.println("Error - key: " + o + " | "+ e);
                }
            }
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
        if ((length % 8) == 0){
            length = length/8;
        }
        else{
            length=(length/8)+1;
        }
        //System.out.println("size - " + length + " bytes");
        return length;
    }

    public void genDictFile(Map dict, String fileLocation) {
        try {
            PrintWriter writer = new PrintWriter(fileLocation.concat(".dict"), "UTF-8");
            Iterator it = dict.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                writer.println(pair.getKey() + "-" + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}