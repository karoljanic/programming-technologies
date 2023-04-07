/**
 * Class represents BinaryTree. This struct consist of Nodes.
 * T is type of values stored in BinaryTree
 */
public class BinaryTree<T extends Comparable<T>> {
    /** Node root */
    private Node<T> root;

    /** Counter of Node IDs */
    private int id;

    /**
     * Constructor of a new BinaryTree object
     */
    BinaryTree() {
        root = null;
        id = 0;
    }

    /**
     * Inserting new value to BinaryTree
     * 
     * @param key value to insert
     */
    void insertElement(T key) {
        insertElement(root, key);
    }

    /**
     * Deleting value from BinaryTree
     * 
     * @param key value to delete
     */
    void deleteElement(T key) {
        root = deleteElement(root, key);
    }

    /**
     * Searching value in BinaryTree
     * 
     * @param key value to find
     * @return true if value exist in BinaryTree, false otherwise
     */
    boolean searchElement(T key) {
        return searchElement(root, key);
    }

    /**
     * Drawing BinaryTree
     */
    void draw() {
        printHierarchy(root, "");
    }

    /**
     * Getting Root of BinaryTree which consist all information about tree
     * 
     * @return BinaryTree Root
     */
    Node getRepresentation() {
        return root;
    }

    /**
     * Inserting new value to BinaryTree
     * 
     * @param node starting Node
     * @param key  value to insert
     */
    private void insertElement(Node<T> node, T key) {
        Node<T> tmpNode = root;
        Node<T> nextNode = new Node<T>(key);
        id++;

        if (root == null) {
            nextNode.parent = null;
            root = nextNode;
            return;
        } else {
            while (tmpNode != null) {
                if (tmpNode.key.compareTo(nextNode.key) < 0) {
                    if (tmpNode.right == null) {
                        nextNode.parent = tmpNode;
                        tmpNode.right = nextNode;
                        return;
                    } else {
                        tmpNode = tmpNode.right;
                    }
                }
                else if (tmpNode.key.compareTo(nextNode.key) > 0) {
                    if (tmpNode.left == null) {
                        nextNode.parent = tmpNode;
                        tmpNode.left = nextNode;
                        return;
                    } else {
                        tmpNode = tmpNode.left;
                    }
                }
                else {
                    return;
                }
            }
        }
    }

    /**
     * Deleting value from BinaryTree
     * 
     * @param root starting Node
     * @param key  value to delete
     * @return root of BinaryTree
     */
    private Node<T> deleteElement(Node<T> root, T key) {
        if (root == null) {
            return null;
        }

        if (key.compareTo(root.key) < 0) {
            root.left = deleteElement(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = deleteElement(root.right, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                Node<T> temp = root.right;
                root = null;

                return temp;
            } else if (root.right == null) {
                Node<T> temp = root.left;
                root = null;

                return temp;
            }

            Node<T> current = root.right;
            while (current != null && current.left != null) {
                current = current.left;
            }

            root.key = current.key;
            root.right = deleteElement(root.right, current.key);
        }

        return root;
    }

    /**
     * Searching value in BinaryTree
     * 
     * @param node starting Node
     * @param key  value to find
     * @return true if value exist in BinaryTree, false otherwise
     */
    private boolean searchElement(Node<T> node, T key) {
        if (node == null) {
            return false;
        } else if (node.key.compareTo(key) == 0) {
            return true;
        } else if (node.key.compareTo(key) > 0) {
            return searchElement(node.left, key);
        } else {
            return searchElement(node.right, key);
        }
    }

    /**
     * Printing BinaryTree to Console
     * 
     * @param node    starting Node
     * @param padding characters from start of printing line to print
     */
    private void printHierarchy(Node<T> node, String padding) {
        if (node != null) {
            System.out.println(padding + "--" + node.key);

            printHierarchy(node.left, padding + "|  ");
            printHierarchy(node.right, padding + "|  ");
        }
    }
};