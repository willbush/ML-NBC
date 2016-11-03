import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class is data model of the data set. It contains helper functions for converting from / to a data set.
 * <p>
 * The data structures, while of interface type List, should be of actual type ArrayList for constant time indexing,
 * except in the case of the list of observations where constant time indexing does not matter. However, each list in
 * the observations should be ArrayList, again for constant time indexing. List are used instead of simple 2 dimensional
 * arrays because they are more flexible and give easy access to Java 8 streams.
 */
class DataSet {
    static final String EMPTY_FILE_ERROR = "The file at the following given path is empty: ";
    private static final String WHITE_SPACE_REGEX = "\\s+";
    private final List<String> columnNames;
    // observations (aka examples) is a list of observations where each has a list of attribute values.
    private final List<List<Boolean>> observations;
    // labels are an ordered list of class labels associated with each observation.
    private final List<Boolean> labels;

    /**
     * @param columnNames  column names associated with the attribute values in the observations list and the lass
     *                     column name is the name of the class label.
     * @param observations a list of observations (aka examples) where each contains a list of attribute values.
     * @param labels       class labels that are associated with each observation.
     * @throws IllegalArgumentException if size of column names does not equal the number of columns
     *                                  if size of labels does not equal observation size.
     */
    private DataSet(List<String> columnNames, List<List<Boolean>> observations, List<Boolean> labels) {
        if (columnNames.size() != observations.get(0).size() + 1) // plus one counts for the class label
            throw new IllegalArgumentException("There must exist an column name for each column.");
        if (labels.size() != observations.size())
            throw new IllegalArgumentException("There must exists a label for each observation.");

        this.columnNames = Collections.unmodifiableList(columnNames);
        this.observations = Collections.unmodifiableList(observations);
        this.labels = Collections.unmodifiableList(labels);
    }

    List<List<Boolean>> getObservations() {
        return observations;
    }

    List<Boolean> getLabels() {
        return labels;
    }

    List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * This is mostly a conveyance method for unit testing.
     *
     * @return a string similar to the format of the input data set files, but with out the
     * "class" label on the last column.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String> names = columnNames;

        for (int i = 0; i < names.size(); ++i) {
            sb.append(names.get(i));
            if (i != names.size() - 1)
                sb.append(" ");
        }
        sb.append("\n");

        int labelIndex = 0;
        for (List<Boolean> elements : observations) {
            for (int i = 0; i < elements.size(); ++i) {
                sb.append(elements.get(i) ? "1 " : "0 ");
                if (i == elements.size() - 1)
                    sb.append(labels.get(labelIndex) ? "1" : "0");
            }
            sb.append("\n");
            ++labelIndex;
        }
        return sb.toString();
    }

    /**
     * Converts data in a data set file into a data object.
     *
     * @param path path to the data set dat file.
     * @return a data set data object.
     * @throws IOException if file is not found or the reader throws an exception while performing readLine.
     */
    static DataSet fromFile(String path) throws IOException {
        List<String> columnNames;
        List<List<Boolean>> observations = new LinkedList<>();
        List<Boolean> labels = new ArrayList<>();

        File f = new File(path);

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            columnNames = readAttributesNames(br).orElseThrow(() -> new EmptyFileException(EMPTY_FILE_ERROR + path));
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] elements = line.split(WHITE_SPACE_REGEX);
                    List<Boolean> attributeValues = new ArrayList<>(elements.length - 1);

                    for (int i = 0; i < elements.length; ++i) {
                        boolean isOne = elements[i].equals("1");
                        if (i == elements.length - 1)
                            labels.add(isOne);
                        else
                            attributeValues.add(isOne);
                    }
                    observations.add(attributeValues);
                }
            }
        }
        return new DataSet(columnNames, observations, labels);
    }

    public static DataSet fromString(String data) {
        String[] lines = data.split("\\n");
        List<List<Boolean>> observations = new LinkedList<>();
        List<String> columnNames = Arrays.asList(lines[0].split(WHITE_SPACE_REGEX));
        List<Boolean> labels = new ArrayList<>(observations.size());

        for (int i = 1; i < lines.length; ++i) {
            String[] rowValues = lines[i].split(WHITE_SPACE_REGEX);
            labels.add(rowValues[rowValues.length - 1].equals("1"));

            List<Boolean> attributeValues = new ArrayList<>(rowValues.length);

            for (int j = 0; j < rowValues.length - 1; ++j) {
                attributeValues.add(rowValues[j].equals("1"));
            }

            observations.add(attributeValues);
        }
        return new DataSet(columnNames, observations, labels);
    }

    /**
     * Moves past any white space and parsing the column names (excluding the "class" word in the last column).
     *
     * @param br a buffered reader
     * @return Attribute names (the names on the top of the columns) not including "class" in the last column.
     * @throws IOException if the reader throws an exception while performing readLine.
     */
    private static Optional<List<String>> readAttributesNames(BufferedReader br) throws IOException {
        String line;

        // move reader past blank lines.
        do {
            line = br.readLine();
        } while (line != null && line.trim().isEmpty());


        List<String> columnNames = null;

        if (line != null) {
            String[] words = line.split(WHITE_SPACE_REGEX);
            columnNames = Arrays.asList(Arrays.copyOfRange(words, 0, words.length));
        }
        return Optional.ofNullable(columnNames);
    }

    static class EmptyFileException extends IOException {
        EmptyFileException(String msg) {
            super(msg);
        }
    }
}
