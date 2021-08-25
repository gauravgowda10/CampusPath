package graph.junitTests;

import org.junit.Test;

import graph.*;

import org.junit.Before;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class GraphTest {
    private final Node<String> a = new Node<>("a");
    private final Node<String> b = new Node<>("b");
    private final Node<String> c = new Node<>("c");
    private final Node<String> d = new Node<>("d");
    private final Node<String> e = new Node<>("e");
    private final Node<String> empty = new Node<>("");
    private final Node<String> repeatA = new Node<>("a");
    private final Node<String> repeatB = new Node<>("b");


    private final Edge<String, String> aa = new Edge<String, String>("aa", a, a);
    private final Edge<String, String> ab = new Edge<String, String>("ab", a, b);
    private final Edge<String, String> ac = new Edge<String, String>("ac", a, c);
    private final Edge<String, String> ca = new Edge<String, String>("ab", c, a);
    private final Edge<String, String> dd = new Edge<String, String>("dd", d, d);
    private final Edge<String, String> ec = new Edge<String, String>("ec", e, c);

    private Graph<String, String> g = new Graph<String, String>();
    private Graph<String, String> g2 = new Graph<String, String>();
    private Graph<String, String> g3 = new Graph<String, String>();
    private Graph<String, String> g4 = new Graph<String, String>();
    private Graph<String, String> g5 = new Graph<String, String>();

    private String emptyS =  "";
    private String random = "random";

    private Set<Node<String>> nodes, nodes1, nodes2, nodes3;

    private Set<Edge<String, String>> edges, edges1, edges2, edges3;


    @Before
    public void setUp() {
        nodes = new HashSet<>();
        nodes1 = new HashSet<>();
        nodes2 = new HashSet<>();
        nodes3 = new HashSet<>();
        edges = new HashSet<>();
        edges1 = new HashSet<>();
        edges2 = new HashSet<>();
        edges3 = new HashSet<>();
        g2.addNode(new Node<> ("a"));
        g3.addNode(new Node<> ("a"));
        g4.addNode(new Node<> ("a"));
        g5.addNode(new Node<> ("a"));
        g3.addNode(new Node<> ("b"));
        g4.addNode(new Node<> ("b"));
        g5.addNode(new Node<> ("b"));
        g4.addNode(new Node<> ("c"));
        g5.addNode(new Node<> ("c"));
        g5.addNode(new Node<> ("d"));
        g3.addEdge("edgeAB", a, b);
        g3.addEdge("edgeBA", b, a);
        g4.addEdge("edgeAB", a, b);
        g4.addEdge("edgeBA", b, a);
        g4.addEdge("edgeBC", b, c);
        g4.addEdge("edgeCB", c, b);
        g4.addEdge("edgeAC", a, c);
        g4.addEdge("edgeCA", c, a);
        nodes1.add(a);
        nodes2.add(a);
        nodes3.add(a);
        nodes2.add(b);
        nodes3.add(b);
        nodes3.add(c);
        edges1.add(new Edge<>("edgeAB", a, b));
        edges1.add(new Edge<>("edgeBA", b, a));
        edges2.add(new Edge<>("edgeAB", a, b));
        edges2.add(new Edge<>("edgeBA", b, a));
        edges2.add(new Edge<>("edgeBC", b, c));
        edges2.add(new Edge<>("edgeCB", c, b));
        edges2.add(new Edge<>("edgeAC", a, c));
        edges2.add(new Edge<>("edgeCA", c, a));
    }

    @Test
    public void testContainsEdge() {
        assertTrue(g3.containsEdge("edgeAB", a, b));
    }


    @Test
    public void testIsEmptyConstructed() {
        assertTrue(g.isEmpty());
        assertFalse(g2.isEmpty());
        assertFalse(g3.isEmpty());
    }

    @Test
    public void testIsEmptyAfterAdd() {
        // before
        assertTrue(g.isEmpty());
        assertFalse(g2.isEmpty());
        assertFalse(g3.isEmpty());
        assertFalse(g4.isEmpty());

        // after
        g.addNode(new Node<> ("a"));
        g2.addNode(new Node<> ("a"));
        g3.addNode(new Node<> ("b"));
        g4.addNode(new Node<> ("e"));
        assertFalse(g.isEmpty());
        assertFalse(g2.isEmpty());
        assertFalse(g3.isEmpty());
        assertFalse(g4.isEmpty());
    }

    @Test
    public void testIsEmptyAfterClearGraph() {
        g2.clearGraph();
        g3.clearGraph();
        g4.clearGraph();
        assertTrue(g2.isEmpty());
        assertTrue(g3.isEmpty());
        assertTrue(g4.isEmpty());
    }

    @Test
    public void testSizeConstructed() {
        assertEquals(0, g.size());
        assertEquals(1, g2.size());
        assertEquals(2, g3.size());
        assertEquals(3, g4.size());
        assertEquals(4, g5.size());
    }

    @Test
    public void testSizeAfterAddingOneNode() {
        g.addNode(new Node<> ("a"));
        assertEquals(1, g.size());
        assertEquals(1, g2.size());
    }

    @Test
    public void testSizeAdding() {
        // before
        assertEquals(0, g.size());
        assertEquals(1, g2.size());
        assertEquals(2, g3.size());
        assertEquals(3, g4.size());

        // after
        g.addNode(new Node<> ("a"));
        assertEquals(1, g.size());

    }


    @Test
    public void testSizeAfterClearAll() {
        // before
        assertEquals(1, g2.size());
        assertEquals(2, g3.size());
        assertEquals(3, g4.size());

        // after
        g2.clearGraph();
        g3.clearGraph();
        g4.clearGraph();
        assertEquals(0, g2.size());
        assertEquals(0, g3.size());
        assertEquals(0, g4.size());
    }

    @Test
    public void testClearAllOnGraphsWithNoEdges() {
        // before clearGraph
        assertEquals(4, g5.size());

        // after clearGraph
        g5.clearGraph();
        assertEquals(0, g5.size());
    }

    @Test
    public void testContainsNodeAdd() {
        g.addNode(new Node<> ("e"));
        g2.addNode(new Node<> ("e"));
        g3.addNode(new Node<> ("e"));
        g4.addNode(new Node<> ("e"));
        assertTrue(g.containsNode(e));
        assertTrue(g2.containsNode(e));
        assertTrue(g3.containsNode(e));
        assertTrue(g4.containsNode(e));
    }



    @Test
    public void testContainsNodeXAfterAddNode() {
        g.addNode(new Node<> ("a"));
        assertTrue(g.containsNode(a));
    }





	/* addNode null test
	@Test(expected = IllegalArgumentException.class)
	public void testAddNodeNull() {
		g.addNode(null);
	}
	 */



	// subNode null test
	@Test(expected = IllegalArgumentException.class)
	public void testSubNodeNull() {
		g.subNode(null);
	}


    @Test(expected = IllegalArgumentException.class)
    public void testSetOfEdgesOfNodeNull() {
        g.setOfEdges(null);
    }

	// 4 addEdge tests
	@Test(expected = IllegalArgumentException.class)
	public void testAddEdgeOfStartNodeDNE1() {
            g.addEdge(random, b, c);
    }


    // 7 null tests for SubEdge
    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull1 () {
        g.addEdge(emptyS, a, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull2 () {
        g.addEdge(emptyS, null, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull3 () {
        g.addEdge(null, a, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull4 () {
        g.addEdge(emptyS, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull5 () {
        g.addEdge(null, a, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull6 () {
        g.addEdge(null, null, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull7 () {
        g.addEdge(null, null, null);
    }

    // 7 removeEdge null tests
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNull1 () {
        g.subEdge(null, null, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNull2 () {
        g.subEdge(null, a, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNull3 () {
        g.subEdge(emptyS, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNull4 () {
        g.subEdge(null, a, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNull5 () {
        g.subEdge(emptyS, a, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNul6 () {
        g.subEdge(emptyS, null, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNull7 () {
        g.subEdge(null, null, null);
    }

    @Test
    public void testSetOfNodes() {
        assertEquals(nodes, g.setOfNodes());
        assertEquals(nodes1, g2.setOfNodes());
        assertEquals(nodes2, g3.setOfNodes());
        assertEquals(nodes3, g4.setOfNodes());
    }

    @Test
    public void testSetOfNodesAfterClearGraph() {
        g.clearGraph();
        g2.clearGraph();
        g3.clearGraph();
        g4.clearGraph();
        assertEquals(nodes, g.setOfNodes());
        assertEquals(nodes, g2.setOfNodes());
        assertEquals(nodes, g3.setOfNodes());
        assertEquals(nodes, g4.setOfNodes());
    }

    @Test
    public void testSetOfNodesWithGivenSets() {
        assertEquals(nodes1, g2.setOfNodes());
        assertEquals(nodes2, g3.setOfNodes());
        assertEquals(nodes3, g4.setOfNodes());
    }

    @Test
    public void testSetOfNodesWithNoNodes() {
        assertEquals(nodes, g.setOfNodes());
    }



}
