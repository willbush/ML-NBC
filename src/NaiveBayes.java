import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

class NaiveBayes {
    /**
     * @param set the data set to train the classifier with
     * @return the classifier function
     */
    static Function<List<Boolean>, Boolean> train(DataSet set) {
        List<List<Boolean>> obs = set.getObservations();
        List<Boolean> labels = set.getLabels();
        final int attributeCount = set.getColumnNames().size() - 1;

        int classTrueCount = 0;

        // The true count of an attribute A given class C = true
        int[] AGivenC = new int[attributeCount];
        // The true count of an attribute A given class C = false
        int[] AGivenNotC = new int[attributeCount];

        for (int i = 0; i < obs.size(); ++i) {
            boolean labelIsTrue = labels.get(i);
            if (labelIsTrue) ++classTrueCount;

            List<Boolean> ob = obs.get(i);
            for (int j = 0; j < ob.size(); ++j) {
                boolean attributeIsTrue = ob.get(j);
                if (labelIsTrue && attributeIsTrue)
                    ++AGivenC[j];
                else if (!labelIsTrue && attributeIsTrue)
                    ++AGivenNotC[j];
            }
        }

        final int labelTrueCount = classTrueCount;
        final int labelCount = labels.size();
        final int labelFalseCount = labelCount - labelTrueCount;

        printStats(set, AGivenNotC, labelFalseCount, "0");
        printStats(set, AGivenC, labelTrueCount, "1");

        // return the classifier function
        return attributes -> {
            double probabilityOfTrue = Math.log10((double) labelTrueCount / labelCount);
            double probabilityOfFalse = Math.log10((double) labelFalseCount / labelCount);

            for (int i = 0; i < attributes.size(); ++i) {
                if (attributes.get(i)) {
                    probabilityOfTrue += Math.log10((double) (AGivenC[i]) / labelTrueCount);
                    probabilityOfFalse += Math.log10((double) (AGivenNotC[i]) / labelFalseCount);

                } else {
                    probabilityOfTrue += Math.log10((double) (labelTrueCount - AGivenC[i]) / labelTrueCount);
                    probabilityOfFalse += Math.log10((double) (labelFalseCount - AGivenNotC[i]) / labelFalseCount);
                }
            }
            return probabilityOfTrue >= probabilityOfFalse;
        };
    }

    private static void printStats(DataSet set, int[] attributeCounts, int classTrueCount, String givenClass) {
        final List<Boolean> labels = set.getLabels();
        final List<String> colNames = set.getColumnNames();
        final int attributeCount = colNames.size() - 1;

        double probabilityOfTrue = (double) classTrueCount / labels.size();
        DecimalFormat df = new DecimalFormat("0.00");
        String classProb = df.format(probabilityOfTrue);
        String className = colNames.get(colNames.size() - 1);

        System.out.print(String.format("P(%s=%s)=%s ", className, givenClass, classProb));

        for (int i = 0; i < attributeCount; ++i) {
            String attribName = colNames.get(i);
            String attribGivenClass = df.format((double) (attributeCounts[i]) / classTrueCount);
            String notAttribGivenClass = df.format((double) (classTrueCount - attributeCounts[i]) / classTrueCount);

            System.out.print(String.format("P(%s=0|%s)=%s ", attribName, givenClass, notAttribGivenClass));
            System.out.print(String.format("P(%s=1|%s)=%s ", attribName, givenClass, attribGivenClass));
        }
        System.out.println();
    }

    /**
     * Uses the classifier on the given data set to classify the data and prints the accuracy results.
     *
     * @param classifier classifier function
     * @param set        data set
     * @param setName    name of the data set
     */
    static void printAccuracy(Function<List<Boolean>, Boolean> classifier, DataSet set, String setName) {
        final int obsSize = set.getObservations().size();
        int correctCount = 0;

        for (int i = 0; i < obsSize; ++i) {
            List<Boolean> obs = set.getObservations().get(i);
            if (classifier.apply(obs) == set.getLabels().get(i))
                correctCount++;
        }
        double accuracy = ((double) correctCount / set.getLabels().size()) * 100;
        DecimalFormat df = new DecimalFormat("00.0");
        String s = String.format("Accuracy on %s set (%s instances): %s%%", setName, obsSize, df.format(accuracy));
        System.out.println(s);
    }
}
