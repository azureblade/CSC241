package dsprog3;
/*
 * @author Matthew Wallace
 */
import java.util.*;
import java.util.regex.*; //Matcher and Pattern
import util.WebDoc;

public class DSProg3 {

	public static void main(String[] args) {
		final int maxPairs = 30; //maximum number of pairs to print
		
		String url;   // test URLs: use others as you like
//		url = "http://en.wikipedia.org/wiki/Jimi_Hendrix";
		url = "http://www.cs.wcupa.edu/~rkline/prog3test.html";
		
		//word_pattern: a string of 5 or more letters (be careful of syntax)
		String word_pattern = "[A-Za-z]{5,}";
		
		String content = null;
		try{
			content = WebDoc.getBodyContent(url); //get body of the web content
		} catch (Exception ex){
			ex.printStackTrace();
			System.exit(1);
		}
		Set<String> skip = new HashSet<String>(Arrays.asList(new String[]{
				"after", "which", "later", "other", "during", "their", "about",
		}));
		
		Map<String,Integer> words = new HashMap<String,Integer>();
		
		int total = 0;
		Matcher match = Pattern.compile(word_pattern).matcher(content);
		while(match.find()){
			// get the next word which matches the word_pattern and normalize
			// it by making it lower case (done in the next statement)
			String word = match.group().toLowerCase();
			
			//System.out.println("Word: " + word); // testing
			//System.out.println("Total: " + total); // testing
			
			/**ADD CODE
			 * 
			 * ignore any word in the "skip" set, otherwise add
			 * one more occurrence of key, word, into the words map
			 */		
			
			//System.out.println(words); // testing	
			
			Boolean skipWord = false;
			if(skip.contains(word)){
				skipWord = true;
			}

			if(!skipWord){
				total++;
				if(words.containsKey(word)){
					words.put(word, (words.get(word))+1);
				}
				else{
					words.put(word, 1);
				}
			}
		}
		
		//System.out.println(words); //use this for testing
		
		class WordFrequency{
			String word;
			Integer numocc;   // frequency of occurrence
			WordFrequency(String word, Integer numocc){
				this.word = word;
				this.numocc = numocc;
			}
			public int getNumocc(){
				return numocc;
			}
		}
	
		/**ADD CODE
		 * 
		 * Iterate through words and store the Map entry pairs into your 
		 * array/list structure of WordFrequency (or Map.Entry) objects.
		 */
		ArrayList<WordFrequency> pairs = new ArrayList<WordFrequency>(); //Stores words
		for(String key : words.keySet()){
			pairs.add(new WordFrequency(key, words.get(key)));
		}
		/**ADD CODE
		 * 
		 * Create a comparator for WordFrequency (or Map.Entry) objects which 
		 * compares by numocc. The sort your array/list using this comparator.
		 */
		Comparator<WordFrequency> cmp = new Comparator<WordFrequency>(){ //Comparator reads in values and inserts them 
			@Override													 //with the largest number of comparisons to the	
			public int compare(WordFrequency lhs, WordFrequency rhs){	 //smallest number of comparisons
				Integer lhsValue = lhs.getNumocc();
				Integer rhsValue = rhs.getNumocc();
				return rhsValue.compareTo(lhsValue);
			}
		};
	
		Collections.sort(pairs, cmp);
		
		/**ADD CODE
		 * 
		 * Print:
	 	 * 	total words entered into words (counting duplicates)
	 	 * 	number of different words (ignore duplicates)
	 	 * 	up to maxPairs "word: numocc" having the LARGEST numocc values.
	 	 */

		System.out.println("words processed (With duplications): " + total); //Displays Words and number of occurrences
		System.out.println("number words (no duplicates): " + pairs.size());
		
			
        System.out.println("________________");
        int maxSize = maxPairs;
        int pairNum = 0;
        if(pairs.size() < maxPairs)
        	maxSize = pairs.size();
        while(pairNum < maxSize){
        	String word = pairs.get(pairNum).word;
        	int occur = pairs.get(pairNum).numocc;
        	System.out.println(word + ": " + occur);
        	pairNum++;
        }
	}
}
