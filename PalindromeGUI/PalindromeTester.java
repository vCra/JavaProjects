package PalindromeGUI;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PalindromeTester{
    final String trueMsg = " IS a palindrome";
    final String falseMsg = " is NOT a palindrome";
    public PalindromeTester() {
// enter your code here
    }
    public boolean isAPalindrome(String myString)
    {
        Stack s = new Stack();
        LinkedList q = new LinkedList();
        int stringLength = myString.length();
        char [] stringChars = new char[stringLength];
        boolean palindrome = true;

        //copy characters of the string to the character array
        myString.getChars(0,stringLength-1, stringChars, 0);


        //Below is a horrible example of having to meet requirements even though it may not be the best way of doing something
        //It would be a lot easier to just reverse the string and do a comparison between the original
            for ( int i = 0; i < stringLength; i++){
                s.add(myString.charAt(i));
            }
            for ( int i = 0; i < stringLength; i++ ){
                q.add(s.pop());
            }
            for ( int i = 0; i < stringLength-1; i++ ){
                if (q.get(i).equals(stringChars[i])){
                    ;
                }
                else{
                    palindrome = false;
                    break;
                }
        }



        return palindrome;

    }
    public void run(){
        Scanner user_input = new Scanner( System.in );
        String inputString = user_input.next();
        System.out.print(this.isAPalindrome(inputString));
    }
}