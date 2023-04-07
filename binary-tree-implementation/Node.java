import java.io.Serializable;

/**
 * Class represents single Node. Node is used to build BinaryTree.
 * T is type of values in Nodes.
 */
public class Node<T extends Comparable<T>> implements Serializable {
    /** Value stored in Node */
    public T key;

    /** Node parent */
    public Node<T> parent;

    /** Node left child */
    public Node<T> left;

    /** Node right child */
    public Node<T> right;

    /**
     * Node constructor
     * 
     * @param value value of Node
     */
    Node(T value) {
        this.key = value;
        this.left = null;
        this.right = null;
    }
};