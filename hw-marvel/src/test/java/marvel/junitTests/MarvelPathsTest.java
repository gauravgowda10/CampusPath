package marvel.junitTests;

import graph.Graph;
import graph.Node;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Test;

public class MarvelPathsTest {
    private Graph<String, String> g;

    @Before
    public void setUp() throws Exception {
        g = MarvelPaths.buildGraph("staffSuperheroes.tsv");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildGraphWithNull() throws Exception {
        MarvelPaths.buildGraph(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullOrigin() {
        MarvelPaths.bfsSearch(g, null, new Node<>("Perkins-the-Magical-Singing-Instructor"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullDest() {
        MarvelPaths.bfsSearch(g, new Node<>("Perkins-the-Magical-Singing-Instructor"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullGraph() {
        MarvelPaths.bfsSearch(null, new Node<>("Grossman-the-Youngest-of-them-all"), new Node<>("Perkins-the-Magical-Singing-Instructor"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullOriginAndDest() {
        MarvelPaths.bfsSearch(g, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullGraphAndDest() {
        MarvelPaths.bfsSearch(null, new Node<>("Grossman-the-Youngest-of-them-all"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullGraphAndOrigin() {
        MarvelPaths.bfsSearch(null, null, new Node<>("Grossman-the-Youngest-of-them-all"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithAllNull() {
        MarvelPaths.bfsSearch(null, null, null);
    }
}
