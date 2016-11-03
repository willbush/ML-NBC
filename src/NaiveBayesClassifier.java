import java.util.List;

class NaiveBayesClassifier {
    private final List<String> columnNames;
    private final List<List<Boolean>> observations;
    private final List<Boolean> labels;

    NaiveBayesClassifier(DataSet dataSet) {
        columnNames = dataSet.getColumnNames();
        observations = dataSet.getObservations();
        labels = dataSet.getLabels();
    }

    double calcProbability(int i) {
        return 0;
    }
}
