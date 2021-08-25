package graph;


/**
 * <b>Edge</b> represents an immutable one-way connection between a parent and
 * child node.
 *
 * <p>Examples of Edges include a connection between two nodes "======-"
 *
 * @param <T> represents the type of each node's label within Edge
 *
 * @param <E> represents the type of each edge's lavel within Edge
 */
public class Edge <T, E>  {
    //  Rep Invariant:
    //      label != null
    //      parentNode != null
    //      childNode != null
    //  Abstraction Function:
    //  Node, n, represents a node in a graph data structure.
    //  The 'label' represents the name of the node.

    private E label;

    private Node<T> parentNode;

    private Node<T> childNode;

    /**
     * Constructor that creates an Edge with the label point parent node
     * to child node
     *  @param label Label(name) of the this
     *  @param parentNode The node this is originating from
     *  @param childNode The node this is pointing to
     *  @spec.requires label != null and childNode != null and parentNode != null
     *  @spec.effects construct a new Edge with the label(name) from parentNode to
     *                  childNode.
     */
    public Edge(E label, Node<T> parentNode, Node<T> childNode) {
        if(label == null || parentNode == null || childNode == null){
            throw new IllegalArgumentException();
        }
        this.label = label;
        this.parentNode = parentNode;
        this.childNode = childNode;
        checkRep();
    }

    /**
     * Return the value of the label on this Edge
     *  @return the label on this
     */
    public E getLabel() {
        return label;
    }

    /**
     * Return the node this is pointing to
     *  @return childNode of this
     */
    public Node<T> getChild() {
        return childNode;
    }

    /**
     * Return the node this is originating from
     *  @return parentNode of this
     */
    public Node<T> getParent() {
        return parentNode;
    }

    /**
     * Return hash code of this edge.
     *
     *  @return hash code of this
     */
    @Override
    public int hashCode() {
        checkRep();
        int code = label.hashCode() + parentNode.hashCode() + childNode.hashCode();
        checkRep();
        return code;
    }

    /**
     * Returns true if arg represent same edge as this edge.
     * (same childNode, parentNode and label) as this edge
     *  @param obj object to be compared
     *  @return true if obj represents same edge, otherwise false
     *
     */
    @Override
    public boolean equals(Object obj) {
        checkRep();
        if (!(obj instanceof Edge)) {
            return false;
        }
        Edge compare = (Edge) obj;
        boolean result = label.equals(compare.getLabel()) && parentNode.equals(compare.getParent())
                && childNode.equals(compare.getChild());
        checkRep();
        return result;
    }


    private void checkRep() {
        assert (label != null) : "label should never be null.";
        assert (parentNode != null) : "parentNode should never be null.";
        assert (childNode != null) : "childNode should never be null.";
    }

}
