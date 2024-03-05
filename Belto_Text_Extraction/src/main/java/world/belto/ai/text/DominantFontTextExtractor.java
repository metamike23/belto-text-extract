package world.belto.ai.text;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DominantFontTextExtractor extends PDFTextStripper {
    private Map<String, Integer> fontCounter = new HashMap<>();
    private List<String> dominantFontTexts = new ArrayList<>();
    private String dominantFont = null;
    private float dominantFontSize = 0;
    public DominantFontTextExtractor() throws IOException {
    }
    public static void main(String[] args) throws IOException {
        try (PDDocument document = PDDocument.load(new File("sample2.pdf"))) {
            DominantFontTextExtractor stripper = new DominantFontTextExtractor();
            stripper.processDocument(document);
            stripper.printDominantFontTexts();
        }
    }
    private void processDocument(PDDocument document) throws IOException {
        // First pass to determine the dominant font and size
        stripPage(document);
        determineDominantFontAndSize();
        System.out.println("Dominant Font and Size: " + dominantFont + ", " + dominantFontSize);
        
        // Clear previous data for the second pass
        dominantFontTexts.clear();
        fontCounter.clear();
        // Second pass to collect text with the dominant font and size
        stripPage(document, true);
    }
    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        if (dominantFont != null) {
            // Processing for the second pass
            TextPosition firstPosition = textPositions.get(0);
            String fontName = firstPosition.getFont().getName();
            float fontSize = firstPosition.getFontSizeInPt();
            if (fontName.equals(dominantFont) && fontSize == dominantFontSize) {
                dominantFontTexts.add(text);
            }
        } else {
            // Processing for the first pass
            for (TextPosition position : textPositions) {
                String fontName = position.getFont().getName();
                float fontSize = position.getFontSizeInPt();
                String fontKey = fontName + "#" + fontSize;
                fontCounter.merge(fontKey, 1, Integer::sum);
            }
        }
    }
    private void stripPage(PDDocument document, boolean isSecondPass) throws IOException {
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            setStartPage(page + 1);
            setEndPage(page + 1);
            writeText(document, new NullWriter()); // This will either update fontCounter or collect texts
        }
    }
    // Overload method for the first pass
    private void stripPage(PDDocument document) throws IOException {
        stripPage(document, false);
    }
    private void determineDominantFontAndSize() {
        String dominantKey = fontCounter.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        if (dominantKey != null) {
            String[] parts = dominantKey.split("#");
            dominantFont = parts[0];
            dominantFontSize = Float.parseFloat(parts[1]);
        }
    }
    public void printDominantFontTexts() {
        System.out.println("Texts with the Dominant Font and Size:\n");
        int counter = 0; // Initialize the counter
        for (String text : dominantFontTexts) {
            counter++; // Increment the counter for each text
            System.out.println(counter + ": " + text); // Print the counter and the text
        }
    }
    // A dummy Writer that discards its output
    private static class NullWriter extends java.io.Writer {
        @Override
        public void write(char[] cbuf, int off, int len) {
            // Do nothing
        }
        @Override
        public void flush() {
            // Do nothing
        }
        @Override
        public void close() {
            // Do nothing
        }
    }
}
