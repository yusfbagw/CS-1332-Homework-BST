import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Yusuf Bagwan
 * @version 1.0
 * @userid ybagwan3 (i.e. gburdell3)
 * @GTID 903891335 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 * 
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of 
 * this file. If this is left blank, this homework will receive a zero.
 * 
 * Agree Here: I agree
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        for (T element : data) {
            if (data == null) {
                throw new IllegalArgumentException("The data can't be null.");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
           throw new IllegalArgumentException("The data can't be null");
        }
        root = addHelper(root, data);
    }

    private BSTNode<T> addHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        }
        int cmp = data.compareTo(curr.getData())
        if (cmp < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        }
        else if (cmp > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can't be null.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }   

    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("The data isn't in the tree.");
        }
        int cmp = data.compareTo(curr.getData());
        if (cmp < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        }
        else if (cmp > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        }
        else {
            size--;
            dummy.setData(curr.getData());
            
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            }
            else if (curr.getLeft() == null) {
                return curr.getRight();
            } 
            else if (curr.getRight() == null) {
                return curr.getLeft();
            }
            else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    private BSTNode<T> removePredecessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        }
        curr.setRight(removePredecessor(curr.getRight(), dummy));
        return curr;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can't be null.");
        }
        return getHelper(root, data);
    }

    private T getHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("The data isn't in the tree.");
        } 
        int cmp = data.compareTo(curr.getData());
        if (cmp < 0) {
            return getHelper(curr.getLeft(), data);
        }
        if (cmp > 0) {
            return getHelper(curr.getRight(), data);
        }
        else {
            return curr.getData();
        }

    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null.");
        }
        return containsHelper(root, data);
    }

    private boolean containsHelper(BSTNode<T> curr, T data){
        if (curr == null) {
            return false;
        }
        int cmp = data.compareTo(curr.getData());
        if (cmp < 0) {
            return containsHelper(curr.getLeft(), data);
        }
        if (cmp > 0) {
            return containsHelper(curr.getRight(), data);
        }
        return true;
    }
    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        return preOrderHelper(root);
    }
    private List<T> preOrderHelper(BSTNode<T> node) {
        List<T> newArrayList = new ArrayList<>();
        if (node != null) {
            newArrayList.add(node.getData());
            newArrayList.addAll(preOrderHelper(node.getLeft()));
            newArrayList.addAll(preOrderHelper(node.getRight()));
        }
        return newArrayList;
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        return inOrderHelper(root);
    }
    private List<T> inOrderHelper(BSTNode<T> node) {
        List<T> newArrayList = new ArrayList<>();
        if (node != null) {
            newArrayList.addAll(inOrderHelper(node.getLeft()););
            newArrayList.add(node.getData());
            newArrayList.addAll(inOrderHelper(node.getRight()););
        }
        return newArrayList;
    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        return postOrderHelper(root);
    }
    private List<T> postOrderHelper(BSTNode<T> node) {
        List<T> newArrayList = new ArrayList<>();
        if (node != null) {
            newArrayList.addAll(postOrderHelper(node.getLeft()));
            newArrayList.addAll(postOrderHelper(node.getRight()));
            newArrayList.add(node.getData());
        }
        return newArrayList;
    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> newQueue = new LinkedList<>();
        List<T> newArrayList = new ArrayList<>();
        if (root != null) {
            newQueue.add(root); 
        }
        while (newQueue.size() > 0) {
            BSTNode<T> curr = newQueue.remove();
            newArrayList.add(curr.getData());
            if (curr.getLeft() != null) {
                newQueue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                newQueue.add(curr.getRight());
            }
        }
        return newArrayList;
    }
    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        return 1 + Math.max(heightHelper(curr.getLeft()), heightHelper(curr.getRight()));
    }
    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
