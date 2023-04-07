/**
 * @brief Class represents single Node. Node is used to build BinaryTree
 *
 * @tparam T type of key value in Node
 */
template <typename T>
class Node
{
public:
    /**
     * @brief Value stored in Node
     */
    T key;

    /**
     * @brief Pointer to Node parent
     */
    Node *parent;

    /**
     * @brief Pointer to Node left child
     */
    Node *left;

    /**
     * @brief Pointer to Node right child
     */
    Node *right;

    /**
     * @brief Constructor of a new Node object
     *
     * @param value value to store in Node
     */
    Node(T value)
    {
        this->key = value;
        this->left = nullptr;
        this->right = nullptr;
    }

    /**
     * @brief Destructor of the Node object
     */
    ~Node()
    {
        delete left;
        delete right;
    }
};