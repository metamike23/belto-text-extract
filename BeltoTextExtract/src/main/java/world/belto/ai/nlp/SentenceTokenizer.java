package world.belto.ai.nlp;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceTokenizer {

    private SentenceDetectorME sentenceDetector;

    public SentenceTokenizer() throws IOException {
        // Load the sentence detection model from the classpath
        try (InputStream modelIn = getClass().getResourceAsStream("/en-sent.bin")) {
            if (modelIn == null) {
                throw new IOException("Model file 'opennlp.bin' not found in classpath");
            }
            SentenceModel model = new SentenceModel(modelIn);
            sentenceDetector = new SentenceDetectorME(model);
        }
    }

    public List<String> tokenizeSentences(List<String> texts) {
        String combinedText = String.join(" ", texts);
        String[] detectedSentences = sentenceDetector.sentDetect(combinedText);
        return Arrays.asList(detectedSentences);
    }
}
