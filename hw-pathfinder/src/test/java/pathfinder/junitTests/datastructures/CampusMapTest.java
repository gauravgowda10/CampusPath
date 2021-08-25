package pathfinder.junitTests.datastructures;

import graph.Node;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Test;
import pathfinder.CampusMap;

public class CampusMapTest {

    private CampusMap c;

    @Before
    public void setUp() throws Exception {
        c = new CampusMap();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLongNameForShortWithNull() {
       c.longNameForShort(null);
    }

}
