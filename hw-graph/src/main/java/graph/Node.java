package graph;



/**
 * <b>Node</b> represents an immutable class with a label stored in it
 *
 * <p> Examples of nodes includes node A.
 * @param <T> represents the type of each node's label in Node
 */
public class Node<T> {
    //  Rep Invariant:
    //      label != null
    //  Abstract Function:
    //
    //  Edge, e, represents an edge in a graph data structure where
    //  the edge connects a parent node (origin) to a child node (destination).
    //
    //  The 'label' represents the name of the edge
    //  The 'parentNode' represents the origin of the node the edge is coming from
    //  The 'childNode' represents the destination of the node the edge is going to
    private T label;

    /**
     * Constructor that creates a new Node object with the
     * specified label
     *
     * @param label stored in the this
     * @spec.requires label != null
     */
    public Node(T label) {
        if (label == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        this.label = label;
        checkRep();
    }


    /**
     * Returns true if arg represent same node as this.
     *
     *  @param obj to be compared
     *  @return true if obj represents same node, otherwise returns false
    */
    @Override
    public boolean equals(Object obj) {
        checkRep();
        if (!(obj instanceof Node)) {
            return false;
        }
        Node<?> compare = (Node<?>) obj;
        boolean result = label.equals(compare.label);
        checkRep();
        return result;
    }

    /**
     * Returns hash code of this node
     *
     * @return hash code of this node
    */
    @Override
    public int hashCode() {
        return label.hashCode();
    }

    /**
     * Return data stored in the Node
     *
     *  @return data Data stored in the Node
     */
    public T getLabel(){
        return label;
    }

    private void checkRep() {
        assert (label != null) : "label should never be null.";
    }


}
