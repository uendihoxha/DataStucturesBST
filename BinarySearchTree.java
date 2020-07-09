package weiss.nonstandard;

import weiss.nonstandard.Stack;
import weiss.nonstandard.BinaryNode;
// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// void removeMin( )      --> Remove minimum item
// Comparable find( x )   --> Return item that matches x
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Exceptions are thrown by insert, remove, and removeMin if warranted

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 *
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

    /**
     * The tree root.
     */
    protected BinaryNode<AnyType> root;

    /**
     * Construct the tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    // Test program
    public static void main(String[] args) {
    }

    /**
     * Insert into the tree.
     *
     * @param x the item to insert.
     * @throws DuplicateItemException if x is already present.
     */
    public void insert(AnyType x, long line) {
        root = insert(x, line, root);
    }

    /**
     * Remove from the tree..
     *
     * @param x the item to remove.
     * @throws ItemNotFoundException if x is not found.
     */
    public void remove(AnyType x) {
        root = remove(x, root);
    }

    /**
     * Remove minimum item from the tree.
     *
     * @throws ItemNotFoundException if tree is empty.
     */
    public void removeMin() {
        root = removeMin(root);
    }

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public AnyType findMin() {
        return elementAt(findMin(root));
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item or null if empty.
     */
    public AnyType findMax() {
        return elementAt(findMax(root));
    }

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return the matching item or null if not found.tv
     */
    public AnyType find(AnyType x) {
        return elementAt(find(x, root));
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Internal method to get element field.
     *
     * @param t the node.
     * @return the element field or null if t is null.
     */
    private AnyType elementAt(BinaryNode<AnyType> t) {
        return t == null ? null : t.element;
    }

    private String pos = "";

    public void set(String x) {
        this.pos = x;
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws DuplicateItemException if x is already present.
     */
    protected BinaryNode<AnyType> insert(AnyType x, long line, BinaryNode<AnyType> t) {
        if (t == null) {
            t = new BinaryNode<>(x);
            t.setPosition(line);
        } else if (x.compareTo(t.element) < 0) {
            t.left = insert(x, line, t.left);
        } else if (x.compareTo(t.element) > 0) {
            t.right = insert(x, line, t.right);
        } else {
            t.setPosition(line);
        }
        // Duplicate

        return t;
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param x the item to remove.
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws ItemNotFoundException if x is not found.
     */
    protected BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null) {
            throw new ItemNotFoundException(x.toString());
        }
        if (x.compareTo(t.element) < 0) {
            t.left = remove(x, t.left);
        } else if (x.compareTo(t.element) > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) // Two children
        {
            t.element = findMin(t.right).element;
            t.right = removeMin(t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    /**
     * Internal method to remove minimum item from a subtree.
     *
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws ItemNotFoundException if t is empty.
     */
    protected BinaryNode<AnyType> removeMin(BinaryNode<AnyType> t) {
        if (t == null) {
            throw new ItemNotFoundException();
        } else if (t.left != null) {
            t.left = removeMin(t.left);
            return t;
        } else {
            return t.right;
        }
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    protected BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t != null) {
            while (t.left != null) {
                t = t.left;
            }
        }

        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return node containing the matched item.
     */
    private BinaryNode<AnyType> find(AnyType x, BinaryNode<AnyType> t) {
        while (t != null) {
            if (x.compareTo(t.element) < 0) {
                t = t.left;
            } else if (x.compareTo(t.element) > 0) {
                t = t.right;
            } else {
                return t;    // Match
            }
        }

        return null;         // Not found
    }

    //display line numbers on which a certain word is foun, inputted by the user 
    public void linenumbers(AnyType x) {

        String temp = find(x, root).getPositionsAsString();

        System.out.println("\"" + x.toString() + "\"" + " appears in lines: " + temp);
    }

    //delete the first occurrence of a given word
    public void delete_first(AnyType x) {

        BinaryNode<AnyType> bn = find(x, root);

        if (bn != null) {
            bn.deleteFirstLine();
            if (bn.positions.isEmpty()) {
                remove(x);
            }
        }
        System.out.println("First occurrence removed!");
    }

    //print all words that appear more than some number, inputted by the user in a sorted manner
    public void more_than(int i) {

        BinaryNode curr = root;
        boolean flag = false;

        weiss.util.Stack<BinaryNode> s = new weiss.util.Stack<>();
        while (curr != null || !s.isEmpty()) {

            while (curr != null) {

                s.push(curr);
                curr = curr.left;
            }

            curr = s.pop();

            if (curr.getPositions().size() > i) {
                System.out.println("\"" + curr.getElement() + "\"" + " occurs more than " + i + " time(s)!");
                flag = true;
            }

            curr = curr.right;

        }

        if (!flag) {
            System.out.println("Sorry, there are no words that occur more than " + i + " time/s.");
        }

    }

    //display number of occurences of a particular word
    public void occurrence(AnyType t) {
        BinaryNode<AnyType> bn = find(t, root);

        if (bn != null) {
            int i = bn.positions.size();
            System.out.println("Frequency of \"" + t + "\" : " + i);
        } else {
            System.out.println("This word does not exist!");
        }

    }

    //displa number of distinct words
    public void printdistinct() {

        BinaryNode curr = root;

        weiss.util.Stack<BinaryNode> s = new weiss.util.Stack<>();
        int count = 0;

        while (curr != null || !s.isEmpty()) {

            while (curr != null) {

                s.push(curr);
                curr = curr.left;
            }

            curr = s.pop();

            count++;
            System.out.println("Word: " + curr.element + " Pos: " + curr.getPositionsAsString());

            curr = curr.right;
        }

        System.out.println("The number of distinct words: " + count);
    }

    //Print all the words more than some number
    static int SearchBinary(String[] arr, String x, int index) {
        int l = 0, r = index - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = x.compareTo(arr[m]);

            // Check if x is present at mid
            if (res == 0) {
                return m;
            }

            // If x greater, ignore left half
            if (res > 0) {
                l = m + 1;
            } // If x is smaller, ignore right half
            else {
                r = m - 1;
            }
        }

        return -1;
    }

}
