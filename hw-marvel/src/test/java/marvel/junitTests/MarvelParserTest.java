package marvel.junitTests;

import marvel.MarvelParser;
import marvel.MarvelPaths;
import org.junit.Test;

public class MarvelParserTest {
    @Test(expected = IllegalArgumentException.class)
    public void testBuildGraphWithNull() throws Exception {
        MarvelParser.parseData(null);
    }
}
