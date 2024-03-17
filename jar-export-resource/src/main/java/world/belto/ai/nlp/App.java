package world.belto.ai.nlp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import world.belto.ai.text.DominantFontTextExtractor;
import world.belto.ai.text.models.TextContainer;

public class App {
	
	
	public static void main(String[] args) {
        // Specify the file path
        String filePath = "belto-text-extract-warby-parker.txt";
        TextContainer tc = new TextContainer();
        ResourceLoader rl = new ResourceLoader();

        try (PDDocument document = PDDocument.load(rl.getPDF())) {
            DominantFontTextExtractor stripper = new DominantFontTextExtractor();
            stripper.processDocument(document);
            // Tokenize the sentences from the extracted text
            SentenceTokenizer tokenizer = new SentenceTokenizer();
            List<String> sentences = tokenizer.tokenizeSentences(stripper.getDominantFontTexts());


            // Print the tokenized sentences
            System.out.println("Tokenized Sentences:");
           for (String sentence : sentences) {
//         System.out.println(sentence);
        	   tc.add(sentence);
          }

           
        } catch (IOException e) {
            System.err.println("Error loading the PDF document: " + e.getMessage());
            e.printStackTrace();
        }
        
        
        

        // Write sentences to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        	
        	
        	for(int i = 0; i < tc.getSize(); i++) {          		
        		 writer.write(tc.get(i));
                 writer.newLine(); // Add a newline after each sentence
                }
        	
 	
          
            System.out.println("Sentences have been written to the file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
        
        
    }

	
	

}
