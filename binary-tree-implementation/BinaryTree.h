#include "Node.h"

#include <iostream>
#include <string>

/**
 * @brief Class represents BinaryTree. This structure consist of Nodes
 *
 * @tparam T type of values stored in BinaryTree
 */
template <typename T>
class BinaryTree
{
public:
    /**
     * @brief Constructor of a new BinaryTree object
     */
    BinaryTree()
    {
        root = nullptr;
    }

    /**
     * @brief Constructor of a new BinaryTree object
     */
    ~BinaryTree()
    {
        delete root;
    }

    /**
     * @brief Inserting new value to BinaryTree
     *
     * @param key value to insert
     */
    void insertElement(T key)
    {
        insertElement(root, key);
    }

    /**
     * @brief Deleting a value from BinaryTree
     *
     * @param key value to delete
     */
    void deleteElement(T key)
    {
        root = deleteElement(root, key);
    }

    /**
     * @brief Searching a value in BinaryTree
     *
     * @param key value to find
     * @return true if value exist in BinaryTree
     * @return false if value not exist in BinaryTree
     */
    bool searchElement(T key)
    {
        return searchElement(root, key);
    }

    /**
     * @brief Drawing BinaryTree
     */
    void draw()
    {
        // printInOrder(root);
        printHierarchy(root, "");
    }

private:
    /**
     * @brief Root of the BinaryTree
     */
    Node<T> *root;

    /**
     * @brief Inserting new value to BinaryTree
     *
     * @param node starting Node
     * @param key value to insert
     */
    void insertElement(Node<T> *node, T key)
    {
        Node<T> *tmpNode = root;
        Node<T> *nextNode = new Node<T>(key);

        if (root == nullptr)
        {
            nextNode->parent = nullptr;
            root = nextNode;
            return;
        }
        else
        {
            while (tmpNode != nullptr)
            {
                if (tmpNode->key < nextNode->key)
                {
                    if (tmpNode->right == nullptr)
                    {
                        nextNode->parent = tmpNode;
                        tmpNode->right = nextNode;
                        return;
                    }
                    else
                    {
                        tmpNode = tmpNode->right;
                    }
                }
                else if (tmpNode->key > nextNode->key)
                {
                    if (tmpNode->left == nullptr)
                    {
                        nextNode->parent = tmpNode;
                        tmpNode->left = nextNode;
                        return;
                    }
                    else
                    {
                        tmpNode = tmpNode->left;
                    }
                }
                else {
                    return;
                }
            }
        }
    }

    /**
     * @brief Deleting a value from BinaryTree
     *
     * @param root starting Node
     * @param key value to delete
     * @return Node<T>* pointer to BinaryTree root Node
     */
    Node<T> *deleteElement(Node<T> *root, T key)
    {
        if (root == nullptr)
        {
            return nullptr;
        }

        if (key < root->key)
        {
            root->left = deleteElement(root->left, key);
        }
        else if (key > root->key)
        {
            root->right = deleteElement(root->right, key);
        }
        else
        {
            if (root->left == nullptr && root->right == nullptr)
            {
                return nullptr;
            }
            else if (root->left == nullptr)
            {
                Node<T> *temp = root->right;
                root = nullptr;

                return temp;
            }
            else if (root->right == nullptr)
            {
                Node<T> *temp = root->left;
                root = nullptr;

                return temp;
            }

            Node<T> *current = root->right;
            while (current != nullptr && current->left != nullptr)
            {
                current = current->left;
            }

            root->key = current->key;
            root->right = deleteElement(root->right, current->key);
        }

        return root;
    }

    /**
     * @brief Searching a value in BinaryTree
     *
     * @param node starting Node
     * @param key value to find
     * @return true if value exist in this part of BinaryTree
     * @return false if value not exist in this part of BinaryTree
     */
    bool searchElement(Node<T> *node, T key)
    {
        if (node == nullptr)
        {
            return false;
        }
        else if (node->key == key)
        {
            return true;
        }
        else if (node->key > key)
        {
            return searchElement(node->left, key);
        }
        else
        {
            return searchElement(node->right, key);
        }
    }

    /**
     * @brief Drawing BinaryTree with basic In Order method
     *
     * @param node starting Node
     */
    void printInOrder(Node<T> *node)
    {
        if (node == nullptr)
        {
            return;
        }

        printInOrder(node->left);
        std::cout << node->key << " ";
        printInOrder(node->right);
    }

    /**
     * @brief Drawing BinaryTree with hierarchy visualisation
     *
     * @param node starting Node
     * @param padding characters from start of printing line to print
     */
    void printHierarchy(Node<T> *node, std::string padding)
    {
        if (node != nullptr)
        {
            std::cout << padding << "--" << node->key << '\n';

            printHierarchy(node->left, padding + "|  ");
            printHierarchy(node->right, padding + "|  ");
        }
    }
};