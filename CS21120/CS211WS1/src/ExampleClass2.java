public class ExampleClass2 {
	
	//concatenates two strings that are passed in
		public String concatOfStrings(String a, String b){
			return a+b;
			
		}
		

	//adds two ints that are passed to teh method
	public int additionOfInts(int a, int b){
		return a+b;
	}
	
	
	//checks XOR function on two Booleans
	public boolean isXOR(boolean a, boolean b){
		boolean c = false;
		if (a ^ b) c = true;
		return c;
	}

	
	//manually builds an array of size 10 and populates the array with 
        //the numbers 0-9 in reverse order: 9 in index[0], 8 in index[1] etc
	public int [] manualArray(){
		
		int sampleArray [] = new int [10]; 
		int numToAdd = 9;
		for(int i=0; i<10; i++){
			sampleArray[i] = numToAdd;
			numToAdd--;
		}
		return sampleArray;
	}
}