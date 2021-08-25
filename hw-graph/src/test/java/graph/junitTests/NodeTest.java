package graph.junitTests;

import org.junit.Test;

import graph.*;


import static org.junit.Assert.*;

public class NodeTest {
    private Node<String> x = new Node<>("x");
    private Node<String> y = new Node<>("y");
    private Node<String> z = new Node<>("z");
    private Node<String> u = new Node<>("x");

    @Test
    public void testGetLabel() {
        assertEquals("x", x.getLabel());
        assertEquals("y", y.getLabel());
        assertEquals("y", y.getLabel());

    }

    @Test
    public void testGetLabelNotEQUAL() {
        assertNotEquals("x", y.getLabel());
        assertNotEquals("x", y.getLabel());
        assertNotEquals("z", u.getLabel());

    }

    @Test
    public void testFalseHashCode() {
        assertNotEquals(x.hashCode(), z.hashCode());
        assertNotEquals(y.hashCode(), u.hashCode());
    }


    @Test
    public void testFalse() {
        assertFalse(x.equals(y));
        assertFalse(x.equals(z));
    }

}
