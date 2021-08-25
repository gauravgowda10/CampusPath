package graph;

import java.util.*;


/**
 * <b>Graph</b> represents a mutable collection of nodes and edges.
 * Each node and edge can only appear once in the graph
 *
 * <p>Examples of graphs include node A ===- B, where node A is the parent
 * node, Node B is the child node, and "===-" is the edge that connects A (parent)
 * to B (child).
 *
 * @param <T> represents the type of each node's label within Graph
 *
 * @param <E> represents the type of each edge's label within Graph
 *
 */
public class Graph<T,E> {


    // Abstraction Function:
    // Graph, g, represents a graph data structure that consists of
    // a set of vertices, or nodes, and a set of links between
    // these vertices, or edges.
    //
    // Graph contained in 'graph':
    //
    // Nodes {n1, n2, ..., n_k} are put in the map as the key
    // in the same order as they are added to the graph.
    //
    // Each key in the map would have an associated value in the map to
    // store the child and parent nodes and the associated edges connecting them.
    //
    // If there are no nodes or edges, then the Graph represents the
    // an empty graph data structure.
    //
    // For example, node n_i (parentNode) and n_j (childNode) is connected by
    // the edge e_c and node n_i (parentNode) and n_j (childNode) is connected by
    // the edge e_d, then the digraph will look like HashMap<n_i, Set<e_c, e_d>.
    // where the edges e_c and e_d are inside the pair linked to the key node n_i.
    //
    //  Representation invariant:
    //     this.graph != null
    //     Every node in the this.graph is not null.
    //	   Every edge in the this.graph is not null
    //     Graph must contain Node a if edges originate or end at node a
    //     A node and a copy of the node cannot be in this.graph
    //     An edge and a copy of the edge cannot be in this.graph

    // debug flag
    private static final boolean DEBUG = false;

    private final Map<Node<T>, Set<Edge<T,E>>> graph;

    /**
     * Constructor  creates a new Graph
     * @spec.modifies this.graph
     * @spec.effects constructs new instance of graph
     */
    public Graph() {
        graph = new HashMap<>();
        checkRep();
    }


    /**
     * Adds a node that is connected to the head
     * @param head node to be added to the graph
     * @spec.requires head != null
     * @spec.modifies this.graph
     * @spec.effects add the head to the graph
     */
    public void addNode(Node<T> head) throws IllegalArgumentException{
        if (head == null) {
            throw new IllegalArgumentException();
        }
        if(containsNode(head)) {
            return;
        }
        checkRep();
        graph.put(head, new HashSet<>());
        checkRep();
        //return addPossible;
    }


    /**
     * Subtracts the given node from the graph and returns true
     * if successful
     * @param arg to be added to the graph
     * @spec.requires arg is in the graph and arg != null
     * @spec.modifies this.graph
     * @spec.effects removes the node from graph
     * @return true if node is subtracted, otherwise false
     */
    public boolean subNode(Node<T> arg) {
        if(arg == null){
            throw new IllegalArgumentException();
        }
        checkRep();
        boolean subPossible = true;
        if (!this.containsNode(arg)) {
            subPossible =  false;
        } else {
            for (Node<T> n: graph.keySet()) {
                for (Edge<T, E> e: graph.get(n)) {
                    if (e.getChild().equals(arg)) {
                        subEdge(e.getLabel(), n, e.getChild());
                    }
                }
            }
            graph.remove(arg);
        }
        checkRep();
        return subPossible;
    }


    /**
     * Adds an edge that is connects two nodes and returns true
     * if successful
     * @param parent is where the edge originates from
     * @param child is where the edge is pointed too
     * @param label is the name of the edge
     * @spec.requires child != null and label != null and parent != null
     * @spec.modifies this.graph
     * @spec.effects add the vertex to the graph
     * @throws IllegalArgumentException this.isEmpty()
     */
    public void addEdge(E label, Node<T> parent, Node<T> child){
        if(this.isEmpty()){
            throw new IllegalArgumentException();
        }
        checkRep();
        Edge<T,E> e = new Edge<>(label, parent, child);
        if(!this.containsNode(parent)) {
            this.addNode(parent);
        }
        if(!this.containsNode(child)) {
            this.addNode(child);
        }
        graph.get(parent).add(e);
        checkRep();
    }

    /**
     * Removes Edge starting from start node to end node with label,
     * return true if the Edge can be removed, otherwise false
     *
     * @param parent is where the edge originates from
     * @param child is where the edge is pointed too
     * @param label is the name of the edge
     * @spec.modifies this.graph
     * @spec.requires parent != null and child != null and label != null
     * @spec.effects Remove all the Edges starting from start pointing to end labeled
     *         with label from the Graph
     * @return true if the Edge can be removed, otherwise false
     *
     */
    public boolean subEdge(E label, Node<T> parent, Node<T> child) throws IllegalArgumentException{
        if (label == null || parent == null || child == null) {
            throw new IllegalArgumentException();
        }
        if (!graph.containsKey(parent)) {
            return false;
        }
        Edge<T,E> e = new Edge<>(label, parent, child);
        Set<Edge<T,E>> edges = graph.get(parent);
        boolean subPossible = edges.contains(e);
        if (subPossible) {
            edges.remove(e);
        }
        return subPossible;
    }


    /**
     * Checks to see if there are zero nodes in the graph
     *
     * @return true if the graph is empty, otherwise false
     */
    public boolean isEmpty() {
        checkRep();
        boolean empty = (this.size() == 0);
        checkRep();
        return empty;
    }

    /**
     * Gets the number of nodes in the graph
     *
     * @return the size of graph
     */
    public int size() {
        checkRep();
        int size = graph.size();
        checkRep();
        return size;
    }
/*
    /**
     * Gets the number of nodes in the graph
     *
     * @return the size of graph

    public int numOfEdges() {
        checkRep();
        int size = 0;
        int i =0;
        for(Node x: graph.keySet()) {
            i++;
            //System.out.println(i);
            size += graph.get(x).size();
            if(graph.get(x).size() != 0) {
                //.println("size: " + graph.get(x).size());
            }
        }
        checkRep();
        return size;
    }
 */
    /**
     * Clears all nodes and edges in the graph.
     *
     * @spec.modifies this.graph
     * @spec.effects this.isEmpty() == true and this.size() == 0
     */
    public void clearGraph() {
        checkRep();
        graph.clear();
        checkRep();
    }

    /**
     * Check if the graph has the  node
     *
     * @param current is the node to be checked in this
     * @spec.requires node != null
     * @return true if the node is in this, otherwise false
     */
    public boolean containsNode(Node current){
        checkRep();
        boolean contains = graph.containsKey(current);
        checkRep();
        return contains;
    }

    /**
     * Check if the graph has the edge
     *
     * @param parent is where the edge originates from
     * @param child is where the edge is pointed too
     * @param label is the name of the edge
     * @spec.requires parent != null, child != null, and label != null
     * @return true if the edge is in the graph, otherwise false
     */
    public boolean containsEdge(E label, Node<T> parent, Node<T> child){
        checkRep();
        if(containsNode(parent) && containsNode(child)) {
            Edge<T,E> e = new Edge<>(label, parent, child);
            return graph.get(parent).contains(e);
        }
        checkRep();
        return false;
    }

    /**
     * Gets all edges originating from the given node
     *
     * @param curr the node to which the edges come from
     * @spec.requires curr != null
     * @return a set containing all the edges originating from curr node
     */
    public Set<Edge<T,E>> setOfEdges(Node<T> curr){
        if(curr == null) {
            throw new IllegalArgumentException();
        }
        checkRep();
        Set<Edge<T,E>> setOfEdges= Collections.unmodifiableSet(graph.get(curr));
        checkRep();
        return setOfEdges;
    }

/*
    /**
     * Get all edges in this
     *
     * @spec.requires !this.equals(null)
     * @return an set containing all the edges in this graph

    public Set<Edge> setOfEdges(){
        throw new RuntimeException("Method not yet implemented");
    }
    */

    /**
     * Get all nodes in the graph
     *
     * @spec.requires !this.equals(null)
     * @return set containing all the nodes in this graph
     */
    public Set<Node<T>> setOfNodes(){
        checkRep();
        Set<Node<T>> setOfNodes = Collections.unmodifiableSet(graph.keySet());
        checkRep();
        return setOfNodes;
    }


    private void checkRep() {
        if (DEBUG) {
            assert this.graph != null : "Graph cannot be null";
            for (Node node : graph.keySet()) {
                assert node != null : "Node cannot be null";
                int nCount = 0;
                for (Node duplicate : graph.keySet()) {
                    if (duplicate.equals(node)) {
                        nCount++;
                    }
                }
                assert (nCount >= 1) : "duplicate node";
                int eCount = 0;
                for (Edge edge : graph.get(node)) {
                    for (Edge duplicate : graph.get(node)) {
                        if (edge.equals(duplicate)) {
                            eCount++;
                        }
                    }
                    assert (eCount >= 1) : "duplicate node";
                    assert edge != null : "Edge cannot be null";
                    assert graph.containsKey(edge.getChild()) : "Graph cannot have edges of nodes that are" +
                            "not in this graph";
                }
            }
        }
    }


}
