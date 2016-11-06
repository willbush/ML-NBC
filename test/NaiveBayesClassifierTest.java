import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class NaiveBayesClassifierTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void arrange() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        out.reset();
    }

    @Test
    public void canClassifyDataSet1() throws IOException {
        final String trainingSetPath = "resources/dataSet1/train.dat";
        final String testSetPath = "resources/dataSet1/test.dat";
        final String expectedOutputPath = "resources/dataSet1/out.dat";

        assertClassified(trainingSetPath, testSetPath, expectedOutputPath);
    }

    @Test
    public void canClassifyDataSet2() throws IOException {
        final String trainingSetPath = "resources/dataSet2/train2.dat";
        final String testSetPath = "resources/dataSet2/test2.dat";
        final String expectedOutputPath = "resources/dataSet2/out2.dat";

        assertClassified(trainingSetPath, testSetPath, expectedOutputPath);
    }

    private void assertClassified(String trainPath, String testPath, String outPath) throws IOException {
        DataSet trainSet = DataSet.fromFile(trainPath);
        DataSet testSet = DataSet.fromFile(testPath);

        Function<List<Boolean>, Boolean> classifier = NaiveBayesClassifier.train(trainSet);

        NaiveBayesClassifier.printAccuracy(classifier, trainSet, "training");
        NaiveBayesClassifier.printAccuracy(classifier, testSet, "test");
        final String expectedOutput = readFile(outPath);

        assertEquals(expectedOutput, out.toString());
    }

    private static String readFile(String path) throws FileNotFoundException {
        return new Scanner(new File(path)).useDelimiter("\\Z").next();
    }
}
