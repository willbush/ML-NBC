import java.util.List;
import java.util.function.Function;

public class Main {
    private static final String PROGRAM_USAGE = "The program requires exactly 2 arguments:\n" +
            "1st argument: the path to the train data file\n" +
            "2nd argument: the path to the test data file\n" +
            "Example usage:\n" +
            "java Main ../resources/dataSet1/train.dat ../resources/dataSet1/test.dat";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(PROGRAM_USAGE);
            System.exit(1);
        }
        try {
            final DataSet trainSet = DataSet.fromFile(args[0]);
            final DataSet testSet = DataSet.fromFile(args[1]);
            Function<List<Boolean>, Boolean> classifier = NaiveBayesClassifier.train(trainSet);

            NaiveBayesClassifier.printAccuracy(classifier, trainSet, "training");
            NaiveBayesClassifier.printAccuracy(classifier, testSet, "test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
