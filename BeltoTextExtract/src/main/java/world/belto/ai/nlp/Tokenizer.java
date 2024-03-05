package world.belto.ai.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Tokenizer {
	
	
	public static void main(String[] args) {
        String input = "Borrowing From Apple, Zappos,” AdvertisingAge, November 28, 2011, http://adage.com/article/special-report- americas-hottest-brands/america-s-hottest-brands-2011-warby-parker/231157/, accessed February 2012. 6nigga 2011.5";
        System.out.println(input + "\n\n");
        Tokenizer t = new Tokenizer();
        String str = t.cleanNumerics(input);
        str = t.removeLinks(str);
        str = t.cleanSpecialCharacters(str);
        System.out.println(str);
    }
	
	
	
	public String replaceIfMultipleOccurrences(String input, int threshold) {
        int slashCount = 0;
        int dashCount = 0;

        // Count occurrences of '/' and '-' and '_'
        for (char c : input.toCharArray()) {
            if (c == '/') {
                slashCount++;
            } else if (c == '-') {
                dashCount++;
            }
            else if (c == '_') {
            	dashCount++;
            }
        }

        // Check if counts exceed the threshold
        if (slashCount > threshold || dashCount > threshold) {
            return ""; // Return blank space if counts exceed the threshold
        } else {
            return input; // Return original string if counts are within the threshold
        }
    }
	
	
	
	
	
	
	
	
	public String removeLinks(String input) {
        StringBuilder result = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(input, " ");
        
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (!containsLink(token)) {
                result.append(token).append(" ");
            } else {
                result.append(""); // Replace link-containing token with "empty"
            }
        }
        
        return result.toString().trim(); // Return the string with link-containing tokens replaced with "empty"
    }
    
    private boolean containsLink(String token) {
        return token.startsWith("http://") || token.startsWith("https://");
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// This method tokenizes a string and checks each token for repeated special characters 
	// If found, any combination of more than two consecutive special characters are trimmed from each token
	// The only combination of special characters repeated in sequence are period/commas followed by either double or single quote
	// Any token that exclusively consist of special characters is removed entirely 
	public String cleanSpecialCharacters(String input) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(input, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (containsAlphanumeric(token)) {
                if (isValidNumericToken(token)) {
                    tokens.add(token); // Preserve valid numeric tokens, including decimals and commas
                } else {
                    token = stripNonAlphabetic(token);
                    if (!token.isEmpty()) { // Add token if it's not empty after stripping
                        tokens.add(token);
                    }
                }
            }
        }
        return concatenateArrayListToString(tokens);
    }
	
	

    private boolean containsAlphanumeric(String token) {
        return token.chars().anyMatch(Character::isLetterOrDigit);
    }

    private boolean isValidNumericToken(String token) {
        // Adjusted to handle numeric tokens with periods or commas correctly
        token = token.replaceAll(",", ""); // Remove commas for validation
        if (token.endsWith(".")) {
            token = token.substring(0, token.length() - 1); // Remove trailing period for validation
        }
        return token.matches("\\d*\\.?\\d+"); // Matches numbers with optional period & decimals
    }

    private String stripNonAlphabetic(String token) {
        StringBuilder cleanedToken = new StringBuilder();
        int consecutiveSpecialCount = 0;
        char lastChar = '\0';

        for (char c : token.toCharArray()) {
            if (!Character.isLetter(c)) {
                consecutiveSpecialCount++;
                if (consecutiveSpecialCount >= 2 && !isPreservedCombination(lastChar, c)) {
                    continue;
                }
            } else {
                consecutiveSpecialCount = 0;
            }
            cleanedToken.append(c);
            lastChar = c;
        }
        return cleanedToken.toString();
    }

    private boolean isPreservedCombination(char firstChar, char secondChar) {
        return (firstChar == ',' && secondChar == '"') ||
               (firstChar == '"' && secondChar == ',') ||
               (firstChar == '.' && secondChar == '"') ||
               (firstChar == '"' && secondChar == '.');
    }
    
    
	    
    
    

	// This method tokenizes a String and checks each token to see if the first and last character type in the token are off a coresponing type
    // Uncessesary leading and trailing numeric characters are a side effect of the PDF text extraction process
    // This method cleans each token for leading and trailing numerics 
    // Ex) "end.555" --> "end."
    // Ex) "5Hello" --> "Hello"
	public String cleanNumerics(String input) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(input, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            // Check if the token begins and ends with specific characters based on its starting character
            if (token.matches("[a-zA-Z].*")) {
                // If the token starts with an alphabetic character
                if (token.matches("[a-zA-Z].*[a-zA-Z\\p{Punct}]+$")) {
                    tokens.add(token);
                } else {
                    // Strip leading or trailing numeric characters if they don't represent a real number
                    if (!isRealNumber(token)) {
                        token = token.replaceAll("^\\d+|\\d+$", "");
                    }
                    tokens.add(token);
                }
            } else if (token.matches("\\d.*")) {
                // If the token starts with a numeric character
                if (token.matches("\\d.*[.,!?;:]$|\\d+")) {
                    tokens.add(token);
                } else {
                    // Strip leading or trailing numeric characters if they don't represent a real number
                    if (!isRealNumber(token)) {
                        token = token.replaceAll("^\\d+|\\d+$", "");
                    }
                    tokens.add(token);
                }
            }
        }
        return concatenateArrayListToString(tokens);
    }

    // Method to check if a token represents a real number
    private boolean isRealNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
    private String concatenateArrayListToString(List<String> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        
        
        for(int i = 0; i < arrayList.size(); i++) {
        	if(i == arrayList.size() - 1) {
        		stringBuilder.append(arrayList.get(i));
        	}
        	else {
        		stringBuilder.append(arrayList.get(i) + " ");
        	}
        }
        
        return stringBuilder.toString();
    }
    
    
    
	

}
