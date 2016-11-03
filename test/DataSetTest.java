import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DataSetTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void fromFile_throwsWhenGivenEmptyFile() throws Exception {
        final String emptyFilePath = "resources/formatTest/emptyFile.dat";
        expectedException.expect(DataSet.EmptyFileException.class);
        expectedException.expectMessage(DataSet.EMPTY_FILE_ERROR + emptyFilePath);
        DataSet.fromFile(emptyFilePath);
    }

    @Test
    public void fromFile_canParseSimpleXOR() throws IOException {
        final String xor = "resources/simpleSet1/XOR.dat";
        final DataSet set = DataSet.fromFile(xor);
        final String actual = set.toString();
        final String expected = "A B C Y\n" +
                "0 0 0 0\n" +
                "0 0 1 1\n" +
                "0 1 0 1\n" +
                "0 1 1 0\n" +
                "1 0 0 1\n" +
                "1 0 1 0\n" +
                "1 1 0 0\n" +
                "1 1 1 1\n";

        assertEquals(expected, actual);
    }

    @Test
    public void fromString_canParseSimpleXOR() throws IOException {
        final String expected = "A B C Y\n" +
                "0 0 0 0\n" +
                "0 0 1 1\n" +
                "0 1 0 1\n" +
                "0 1 1 0\n" +
                "1 0 0 1\n" +
                "1 0 1 0\n" +
                "1 1 0 0\n" +
                "1 1 1 1\n";
        final String actual = DataSet.fromString(expected).toString();

        assertEquals(expected, actual);
    }
}
