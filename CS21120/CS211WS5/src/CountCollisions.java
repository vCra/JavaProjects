import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/** A program that counts collision for words found in a document. */
public class CountCollisions {
    /* Choice of method to determine hash code:
     * 0: Java default for String
     * 1: Component Sum
     * 2: Component XOR
     * 3: Polynomial (see parameter below)
     * 4: Cyclic Shift (see parameter below)
     * 5: Random Polynomial (see parameter below)
     */
    private static int method = 1;

    // Text file used for analysis
    private static String filename = "words-mac.txt";

    // parameter for polynomial hash codes
    private static int coefficient = 31;

    // parameter for cyclic shift hash codes
    private static int shiftLength = 5;

    //parameter for random polynomial hash codes
    private static int maxCoefficient = Integer.MAX_VALUE;

    public static void main(String[] args) throws FileNotFoundException {
        // Map of the words already seen
        HashMap<String,String> wordTable = new HashMap<String,String>();

        // Map of the hash codes
        HashMap<Integer,Integer> hashCodeTable = new HashMap<Integer, Integer>();

        // scan input for words
        Scanner doc = new Scanner(new File(filename));

        MyString word = new MyString();
        int hashCode = 0;

        int numWords = 0;
        int numDuplicates = 0;
        int totalCollisions = 0;
        int numWordsCollide = 0;
        int maxAtAnyPosition = 1;

        while (doc.hasNext()) {
            // get next word and calculate hash code
            word.set(doc.next());

            /** determine hash code using different methods */
            switch (method)
            {
                case 0:
                    hashCode = word.hashCode(); // standard Java implementation
                    break;
                case 1:
                    hashCode = word.componentSumHashCode();
                    break;
                case 2:
                    hashCode = word.componentXORHashCode();
                    break;
                case 3:
                    hashCode = word.polynomialHashCode(coefficient);
                    break;
                case 4:
                    hashCode = word.cyclicShiftHashCode(shiftLength);
                    break;
                case 5:
                    hashCode = word.randomPolynomialHashCode(maxCoefficient);
                    break;
                default: System.out.println("Method undefined.");
                    System.exit(0);
            }

            // check if new word; if already seen ignore
            if (!wordTable.containsKey(word.print()))
            {
                // increment number of words seen and add word to map
                numWords++;
                wordTable.put(word.print(), word.print());

                // if hash code seen previously increase counter
                if (hashCodeTable.containsKey(hashCode))
                {
                    int count = hashCodeTable.get(hashCode);
                    hashCodeTable.put(hashCode, count+1);

                    // increase collisions by count
                    totalCollisions += count;

                    // check if new max found
                    if (count+1 > maxAtAnyPosition)
                        maxAtAnyPosition = count+1;

                    // increase number of words that collide
                    numWordsCollide++;
                    if (count == 1) // if first collision of this hash code
                        numWordsCollide++;
                }
                else // add new hash code seen
                {
                    hashCodeTable.put(hashCode, 1);
                }
            }
            else
            {
                numDuplicates++;
            }
        }

        System.out.println("Words processed: "+numWords+" ("+numDuplicates+" duplicates)");
        System.out.println("Unique Hash Codes: "+hashCodeTable.size());
        System.out.println("Number of words that collide with at least one other: "+numWordsCollide);
        System.out.println("Maximum number of words colliding at any one hash code: "+maxAtAnyPosition);
        System.out.println("Total number of collisions: "+totalCollisions);

        doc.close();
    }
}
