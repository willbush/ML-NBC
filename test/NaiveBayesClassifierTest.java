import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NaiveBayesClassifierTest {
    @Test
    public void canCalcProbability() {
        final String xor = "A B C Y\n" +
                "0 0 0 0\n" +
                "0 0 1 1\n" +
                "0 1 0 1\n" +
                "0 1 1 0\n" +
                "1 0 0 1\n" +
                "1 0 1 0\n" +
                "1 1 0 0\n" +
                "1 1 1 1\n";
        NaiveBayesClassifier classifier = new NaiveBayesClassifier(DataSet.fromString(xor));
        int classIndex = 3;
        double expected = 0.5;
        assertEquals(expected, classifier.calcProbability(classIndex), 0.001);
    }
}
