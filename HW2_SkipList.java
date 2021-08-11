import java.io.File;
import java.util.Random;
import java.util.Scanner;
class SkipLists {

    class Node {
        // This is the Node class and method that is initializing node values

        public String key;
        public long value;

        public Node prev;
        public Node  next;
        public Node above;
        public Node  below;

        public Node(long value){
            this.prev = null;
            this. next = null;
            this.above = null;
            this. below = null;
            this.value = value;
        }
    }
    class skipList {
        // this class is purpose is to construct the skiplist.
        public long positiveInfinity = Integer.MAX_VALUE;
        public long negativeInfinity = Integer.MIN_VALUE;

        public Node head, tail;
        public int size, maxLevel;

        public skipList() {
            Node negInf = new Node(negativeInfinity);
            Node posInf = new Node(positiveInfinity);

            negInf. next = posInf;
            posInf.prev = negInf;

            this.head = negInf;
            this.tail = posInf;
            this.maxLevel = 1;
            this.size = 0;

        }

        public void addLevel() {
            // this methods purpose is to add a new level to the skiplist
            Node newNegInf = new Node(this.negativeInfinity);
            Node newPosInf = new Node(this.positiveInfinity);

            newNegInf. below = head;
            newNegInf. next = newPosInf;
            newPosInf. below = tail;
            newPosInf.prev = newNegInf;
            head.above = newNegInf;
            tail.above = newPosInf;

            head = newNegInf;
            tail = newPosInf;
            maxLevel++;
        }

    }
    public void insert(skipList list, int value, Random rand) {
    // this method purpose is to insert nodes into the skiplist.
        Node  newNode = new Node(value);
        Node currentPosition = search(list, value);
        int heightOfList;

        if (currentPosition.value != value) {

             newNode.prev = currentPosition;
             newNode. next = currentPosition. next;
            currentPosition. next =  newNode;
             newNode. next.prev =  newNode;

            heightOfList = 1;
            promote(list,currentPosition, newNode,heightOfList,value,rand);

            list.size++;
            if (heightOfList > list.maxLevel) {
                list.maxLevel = heightOfList;
            }
        }
    }
    public void promote(skipList list,Node currentPosition,Node  newNode, int heightOfList, int value,Random rand ){
    // this methods purpose is to promote nodes randomly and link them with current nodes.
        while (Math.abs(rand.nextInt() % 2) == 1) {

            if (heightOfList >= list.maxLevel) {
                list.addLevel();
            }

            while (currentPosition.above == null) {
                currentPosition = currentPosition.prev;
            }
            currentPosition = currentPosition.above;

            Node levelUp = new Node(value);
            levelUp.prev = currentPosition;
            levelUp. next = currentPosition. next;
            currentPosition. next.prev = levelUp;
            currentPosition. next = levelUp;
            levelUp. below =  newNode;
             newNode.above = levelUp;

             newNode = levelUp;

            heightOfList++;
        }


    }
    public Node search(skipList list, int key) {
        // this method searches for the node needed for processing
        Node x = list.head;
        for (int y = list.maxLevel; y > 0; y--) {
            while (x. next.value != list.positiveInfinity && x. next.value <= key) {
                x = x. next;
            }
            if (x. below != null) {
                x = x. below;
            } else {
                break;
            }
        }
        return x;
    }
    public void delete(skipList list, int key) {
        // This method deletes nodes based on how they are processed.
        Node currentPosition = search(list, key);
        if (currentPosition.value == key) {
            while (currentPosition != null) {
                currentPosition.prev. next = currentPosition. next;
                currentPosition. next.prev = currentPosition.prev;
                currentPosition = currentPosition.above;
            }
            System.out.println(key + " deleted");
            list.size--;
        } else {
            System.out.println(key + " integer not found - delete not successful");
        }
    }
    public void printAll(skipList list) {
        // This method prints the skiplist
        Node currentPosition = list.head;

        while (currentPosition. below != null) {
            currentPosition = currentPosition. below;
        }

        System.out.println("the current Skip List is shown below:");
        System.out.println("---infinity");
        while (currentPosition.value != list.tail.value) {
            currentPosition = currentPosition. next;
            if (currentPosition.value != list.positiveInfinity) {
                printColumns(currentPosition);
            }
        }
        System.out.println("+++infinity");
        System.out.println("---End of Skip List---");
    }

    public void printColumns(Node Columns) {
        // this method helps the print the columns/levels of the skiplist
        System.out.print(" " + Columns.value + "; ");
        if (Columns.above == null) {
            System.out.print("\n");
        }
        while (Columns.above != null) {
            Columns = Columns.above;
            System.out.print(" " + Columns.value + "; ");
            if (Columns.above == null) {
                System.out.print("\n");
                break;
            }
        }
    }
    void quit() {
        // this is to quit the program
        System.exit(0);
    }

    void complexityIndicator() {
        // this is the complexity inidcator
        System.err.println("ja441813;4.5;35");

    }

}

public class Hw02 {
    public static void main(String[] args) {

        if (args.length > 0) {

            // this part of the program checks the command line arguments to see if there exist
            // an R/r command.

            long unseeded = 42;
            long seeded = System.currentTimeMillis();
            int epoch = 0;
            File fileName = new File(args[0]);

            if (args.length == 1) {
                epoch = 1;
            } else if (args.length == 2) {
                epoch = 2;

            }
            // Creating skiplist and reading input file
            SkipLists List = new SkipLists();
            SkipLists.skipList list = new SkipLists().new skipList();

            try {
                // Random sequence
                Random rand = new Random();
                if (epoch == 1) {
                    rand.setSeed(42);
                } else if (epoch == 2) {
                    switch (args[1]) {
                        case "r":
                        rand.setSeed(System.currentTimeMillis());
                        break;
                        case "R":
                            rand.setSeed(System.currentTimeMillis());
                            break;
                        default:
                            System.out.println(args[1]+" is not a valid command");
                            System.out.println("R or r are valid commands please try again");
                            System.out.println("Program will now quit");
                            List.quit();

                    }
                }

                SkipLists.Node temp;
                Scanner scan = new Scanner(fileName);
                List.complexityIndicator();
                System.out.println("For the input file named " + fileName);
                if (epoch == 1) {
                    System.out.println("With the RNG unseeded,");
                } else if(epoch ==2) {
                    System.out.println("With the RNG seeded,");
                }
                while (scan.hasNextLine()) {
                    String lines = scan.nextLine();
                    String[] command = lines.split(" ");

                    // Read commands
                    switch (command[0]) {
                        case "i":
                            temp = List.search(list, Integer.parseInt(command[1]));
                            if (temp.value == Integer.parseInt(command[1])) {
                                //System.out.println(temp.value + " Already in list");
                                continue;
                            } else {
                                List.insert(list, Integer.parseInt(command[1]), rand);
                            }

                            break;
                        case "s":
                            temp = List.search(list, Integer.parseInt(command[1]));
                            if (temp.value == Integer.parseInt(command[1])) {
                                System.out.println(temp.value + " found");
                            } else {
                                System.out.println(command[1] + " Not found");
                            }
                            break;
                        case "d":
                            List.delete(list, Integer.parseInt(command[1]));
                            break;
                        case "p":
                            List.printAll(list);
                            break;
                        case "q":
                            List.quit();
                            break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Input file not specified please try again");
        }
    }
}
