import java.util.Random;

public class MyString {
    private String s;
    private Random rnd;

    public MyString()
    {
        s = new String();
        rnd = new Random();
    }

    public void set(String s)
    {
        this.s = s;
        long mySeed = 0;
        for (int i=0; i < s.length(); i++)
        {
            mySeed += (long) s.charAt(i);
        }
        rnd.setSeed(mySeed);
    }

    public String print()
    {
        return s;
    }

    /** Compares this string to the specified object. The result is true if and
     * only if the argument is not null and is a String object that represents
     * the same sequence of characters as this object.*/
    public boolean equals(Object anObject)
    {
        return s.equals(anObject);
    }

    /** The standard hashCode method from String: The hash code for a String
     * object is computed as s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * using int arithmetic, where s[i] is the ith character of the string,
     * n is the length of the string, and ^ indicates exponentiation.
     * (The hash value of the empty string is zero.) 
     * Note that this is a polynomial hash code, but with slightly different
     * coefficients than in the lecture.*/
    public int hashCode()
    {
        return s.hashCode();
    }

    /** Component Sum (Lecture 13, Slide 13) */
    public int componentSumHashCode()
    {
        int count = 0;
        for (int i=0; i < s.length(); i++){
            char c = s.charAt(i);
            count =+ Character.getNumericValue(c);
        }
        return count;
    }

    /** Component XOR (Lecture 13, Slide 13) */
    public int componentXORHashCode()
    {
        return 0;
    }

    /** Polynomial Hash Code with parameter a (Lecture 13, Slide 15) */
    public int polynomialHashCode(int a)
    {
        return 0;
    }

    /** Polynomial Hash Code with random coefficients (Variation of Lecture 13,
     * Slide 15): Instead of using different powers of a as coefficients, we
     * select a random positive coefficient <= max for each character. The random
     * number generator is initialised for each assigned string above (set method).
     * You can get a random coefficient by using rnd.nextInt(max); 
     * (this returns a random integer in [0,max-1]) 
     * To be more precise: for s[i] the ith character of the string, n the length
     * of the string and a0, a1, ... an-1 random integers in [0,max-1] we calculate:
     * a0 * s[0] + a1 * s[1] + ... + an-1 * s[n-1] using int arithmetic */
    public int randomPolynomialHashCode(int max)
    {
        return 0;
    }

    /** Cyclic-Shift Hash Code with shift length l (Lecture 13, Slide 17) */
    public int cyclicShiftHashCode(int l)
    {
        return 0;
    }
}