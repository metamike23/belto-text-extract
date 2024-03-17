package world.belto.ai.nlp;



public class CharacterCheck {
   

	public static boolean startsWithCapital(String input) {
        if (input == null || input.isEmpty()) {
            return false; // Return false for empty or null strings
        }
        char firstChar = input.charAt(0);
        return Character.isUpperCase(firstChar);
    }
	
    
    public static boolean containsNoConsecutiveSpaces(String tokens) {
            if (tokens.contains("    ")) { // Four consecutive spaces
                return false;
            }
        
        return true;
    }
    
    
    
  
    
    
    
    
    public static boolean containsLink(String token) {
        return token.contains("http://") || token.contains("https://");
    }
    
    
    
    
    
    
    
    
    // returns true if the sentence starts with a lowercase letter
    public static boolean startsWithLowerCase(String str) {
        // Check if the first character of the string is lowercase
    	if(str.length() == 0) {
    		return false;
    	}
    	else {
    		char firstChar = str.charAt(0);
            return Character.isLowerCase(firstChar);
    	}
        
    }
       
   public static boolean endsWithPunctuation(String str) {
       // Regular expression to match any form of punctuation at the end of the string
       String regex = ".*[.,!?;:]$";

       return str.matches(regex);
   }
   
   
   
   
   
   
   

   
}