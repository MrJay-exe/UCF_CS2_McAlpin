import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Hw01 {
    // node class for nodes.
    class Node {
        int key;
        Node left, right;

        public Node(int data) {
            key = data;
            left = right = null;
        }
    }

    Node root;
    public static boolean flag = false;

    Hw01() {
        root = null;
    }

    // *********This is the insert part of the binary search tree*********************************

    //this method is to make insertrecursive recursive.
    void insertNode(int key) {
        root = insertRecursive(root, key);
    }

    // This is method is responsible for inserting the node in the bst.
    Node insertRecursive(Node root, int key) {

        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key < root.key)
            root.left = insertRecursive(root.left, key);
        else if (key >= root.key)
            root.right = insertRecursive(root.right, key);
        return root;
    }

    //**********This is the delete part of the binary search tree********************

    // this method is to make delete recursive recursive.
    void deleteNode(int key) {
        root = deleteRecursive(root, key);
    }

    // this method handles deleting the nodes in the bst for different cases.
    Node deleteRecursive(Node root, int key) {
        if (root == null) {
            return root;
        }
        if (key < root.key)
            root.left = deleteRecursive(root.left, key);
        else if (key > root.key)
            root.right = deleteRecursive(root.right, key);
        else {

            if (root.left == null)
                return root.right;
            if (root.right == null)
                return root.left;

            root.key = minValue(root.right);
            root.right = deleteRecursive(root.right, root.key);
        }
        return root;
    }

    // this method
    int minValue(Node root) {
        int minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }
    // *******************This is to search for the key in the tree *******************************************

    // this method is to make searchNode recursive.
    boolean searchNodeTF(int key) {
        boolean check = searchNode(root, key);
        return check;
    }

    // this method is responsible for searching for the key inputted.
    boolean searchNode(Node node, int key) {
        if (node == null)
            return false;

        if (node.key == key)
            return true;

        boolean res1 = searchNode(node.left, key);

        if (res1) return true;

        boolean res2 = searchNode(node.right, key);

        return res2;
    }

    //*********************** in order print functions ***************************************************************

    //this method is for making inorderrec recursive.
    void inorder() {

        inorderRec(root);
    }

    // this method prints the bst inorder.
    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }

    //************************************This is to count the children of the tree******************************************************
    int countChildren(Node root) {

        if (root == null) {
            return 0;
        }
        /*if (root.left == null && root.right == null) {
            return 1;
        }*/
        return 1 + countChildren(root.left) + countChildren(root.right);
    }
    //************************************ This is to get the depth of the tree ******************************************************

    int getDepth(Node root) {
        if (root == null) {
            return 0;
        } else {

            int leftDepth = 0, rightDepth = 0;

            if (root.left != null)
                leftDepth = getDepth(root.left);

            if (root.right != null)
                rightDepth = getDepth(root.right);

            int max = (leftDepth > rightDepth) ? leftDepth : rightDepth;

            return (max + 1);
        }
    }

    // **************************** This is to quit the program ******************
    void quit() {
        System.exit(0);
    }

    //*********** This is to call the complexity indicator***************************
    void complexityIndicator() {
        System.err.println("ja441813;3.5;23.5");
    }

    // ************************************ This is main *****************************
    public static void main(String[] args) {
        // This is to read in the file into an arraylist.
        ArrayList<String> arr = new ArrayList<>();
        File file = new File(args[0]);
        try {
            String current;
            BufferedReader br = new BufferedReader(new FileReader(file));
            System.out.println(file + " contains: ");
            while ((current = br.readLine()) != null) {
                System.out.println(current);
                arr.add(current);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Hw01 tree = new Hw01();
        /* this for loop takes in the elements from the arraylist checks what what command it starts with
         * and parses the key and turns them into an integer to be processes by the bst methods.
         * The integer is either processed according to there command or if the integer cannot be processed
         * if its not found then an error message will show up.This for loop has a try catch block around it
         * in the case a certain commands that are supposed to have integers don't, then an error message will show.
         */
        for (String s : arr) {
            try {
                if (s.startsWith("i")) {
                    tree.insertNode(Integer.parseInt(s.substring(2)));

                } else if (s.startsWith("d")) {
                    boolean test = tree.searchNodeTF(Integer.parseInt(s.substring(2)));
                    if (test) {
                        tree.deleteNode(Integer.parseInt(s.substring(2)));
                    } else {
                        System.out.println(s.substring(2) + ": NOT found - NOT deleted");
                    }

                } else if (s.startsWith("s")) {

                    boolean test = tree.searchNodeTF(Integer.parseInt(s.substring(2)));
                    if (test) {
                        System.out.println(s.substring(2) + ": found");
                    } else {
                        System.out.println(s.substring(2) + ": NOT found");
                    }
                } else if (s.startsWith("p")) {
                    tree.inorder();
                    System.out.print("\n");

                } else if (s.startsWith("q")) {

                    System.out.println("left children:         " + tree.countChildren(tree.root.left));
                    System.out.print("left depth:            " + tree.getDepth(tree.root.left));
                    System.out.print("\nright children:        " + tree.countChildren(tree.root.right));
                    System.out.print("\nright depth:           " + tree.getDepth(tree.root.right) + "\n");
                    tree.complexityIndicator();
                    tree.quit();
                }
            } catch (NumberFormatException e) {
                if (s.startsWith("i")) {
                    System.out.println("missing numeric parameter");
                } else if (s.startsWith("d")) {
                    System.out.println("missing numeric parameter");
                } else if (s.startsWith("s")) {
                    System.out.println("missing numeric parameter");

                }

            }

        }
    }
}
