package graph.junitTests;

import org.junit.Test;

import graph.*;

import org.junit.Before;


import static org.junit.Assert.*;

public class EdgeTest {
    // create new EdgesOfNodes
    private Edge<String, String> a;
    private Edge<String, String> b;
    private Edge<String, String> c;
    private Edge<String, String> d;

    private Node<String> x;
    private Node<String> y;
    private Node<String> z;
    private Node<String> u;


    @Before
    public void setUp(){
        x = new Node<>("x");
        y = new Node<>("y");
        z = new Node<>("z");
        u = new Node<>("u");
        a = new Edge<>("a", x, y);
        b = new Edge<>("b", y, x);
        c = new Edge<>("c", x, z);
        d = new Edge<>("d", u, x);

    }

    @Test
    public void testGetParent() {
        assertEquals(x, a.getParent());
        assertEquals(y, b.getParent());
        assertEquals(x, c.getParent());
        assertEquals(u, d.getParent());
    }

    @Test
    public void testGetChild() {
        assertEquals(y, a.getChild());
        assertEquals(x, b.getChild());
        assertEquals(z, c.getChild());
        assertEquals(x, d.getChild());
    }


    @Test
    public void testGetLabel() {
        assertEquals("a",a.getLabel());
        assertEquals("b",b.getLabel());
        assertEquals("c",c.getLabel());
        assertEquals("d",d.getLabel());
    }


    @Test
    public void testEquals() {
        assertTrue(x.equals(x));
        assertFalse(x.equals(y));
        assertFalse(y.equals(u));
        assertFalse(u.equals(z));
        assertFalse(z.equals(y));
    }



}
