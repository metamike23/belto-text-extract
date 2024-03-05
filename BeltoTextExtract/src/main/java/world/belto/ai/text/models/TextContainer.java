package world.belto.ai.text.models;



import world.belto.ai.nlp.CharacterCheck;
import world.belto.ai.nlp.Tokenizer;

public class TextContainer implements TextContainerInterface {

	
	
	
	
	/**
	 * 
	 */
	private Tokenizer tokenizer;
	private String[] text;
	private int size = 0;
	
	public TextContainer() {
		text = new String[5];
		tokenizer = new Tokenizer();
	}
	
	
	private void resize(int capacity) {
		String[] temp = new String[capacity];
		for(int i = 0; i < size; i++) {
			temp[i] = text[i];
		}
		text = temp;
	}
	
	@Override
    public void add(String sentence) {
		// If the array is full resize it to a new capacity
		if(size == text.length) {
			int cap = size * 2;
			resize(cap);
		}
		
		
		
		if(CharacterCheck.containsNoConsecutiveSpaces(sentence)) {
			
			String str = tokenizer.removeLinks(sentence);
			str = tokenizer.cleanNumerics(str);
			str = tokenizer.cleanSpecialCharacters(str);
			str = tokenizer.replaceIfMultipleOccurrences(str, 2);
			System.out.println(str);
			
			if(str.length() > 1 && CharacterCheck.startsWithCapital(str)) {
				text[size] = str.stripTrailing();
				size++;
			}
			
		}
		
		
    }
	
	
	
	
	
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < size; i++) {
			result = result + "\n" + text[i];
		}
		return result;
	}
	
	
	private void appendToLastStringInArray(String appendString, int index) {
        if (text.length > 0) {
          
            // Append the string to the last element
            text[index] += " " + appendString;
        }
    }


	@Override
	public String get(int i) {
		if(i > size) {
			return null;
		}
		else {
			return text[i];
		}
	}
	
	public int getSize() {
		return size;
	}
	
	
	public static void main(String[] args) {
		TextContainer tc = new TextContainer();
		tc.add("Hello my name is.");
		tc.add("Hello my namethrthrth457657u56hj is:");
		tc.add("hello my name     is.");
		tc.add("Hello my name is");
		tc.add("wps/portal/!ut/p/b1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOIt3YxNPL3dDQ0sLJxNDIx8wjw8Df1CjQ2CDfQLs");
		tc.add("Hdfsdfklj       odfodosfod    knfsdfsdf ");
		tc.add("Last entry.");
		tc.add("http://adage.com/article/special-report- americas-hottest-brands/america-s-hottest-brands-2011-warby-parker/231157/,");
		
		
		for(int i = 0; i < tc.getSize(); i++) {
			System.out.println(tc.get(i));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
