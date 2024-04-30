import java.util.Scanner;
import java.io.*;

/**
 * Represents a node in the decision tree. Each node can either be a question node
 * leading to yes or no branches, or an answer leaf node.
 */
class TreeNode implements Serializable {
    String data;
    TreeNode yes;
    TreeNode no;

    TreeNode(String data) {
        this.data = data;
        this.yes = null;
        this.no = null;
    }
}

/**
 * The GuessingGame class handles the gameplay, learning, and storage of a yes/no
 * decision tree to guess objects.
 */
public class GuessingGame {
    private TreeNode root;
    private Scanner scanner;

    /**
     * Constructor initializes the game, either loading from saved state or starting anew.
     */
    // ANSI color code declarations
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public GuessingGame() {
        scanner = new Scanner(System.in);
        File f = new File("tree.ser");
        if (f.exists() && !f.isDirectory()) {
            loadTree();
        } else {
            initializeTree();
        }
    }

    /**
     * Initializes the tree with a set of default questions and answers.
     */
    private void initializeTree() {
        root = new TreeNode("Is it an animal?");
        root.yes = new TreeNode("Can it fly?");
        root.yes.yes = new TreeNode("Is it a bird?");
        root.yes.yes.yes = new TreeNode("eagle");
        root.yes.yes.no = new TreeNode("penguin");
        root.yes.no = new TreeNode("elephant");
        root.no = new TreeNode("Does it have wheels?");
        root.no.yes = new TreeNode("car");
        root.no.no = new TreeNode("tree");
    }

    /**
     * Starts a new game session, guiding the user through the process.
     */
    public void play() {
        System.out.println("\n------- Welcome to the Guessing Game! -------");
        System.out.println("Think of an object, and I will try to guess it by asking yes/no questions.");
        System.out.println("Please answer with 'yes' or 'no' only.\n");
        traverse(root);
        System.out.println("\n-------- Thank you for playing! --------");
    }

    /**
     * Traverses the tree based on user responses until an answer is guessed or learning is needed.
     */
    private void traverse(TreeNode current) {
        while (!isLeaf(current)) {
            System.out.println(current.data);
            String response = getUserResponse();
            current = response.equalsIgnoreCase("yes") ? current.yes : current.no;
        }
        guessObject(current);
    }

    /**
     * Enforces the user to provide 'yes' or 'no' responses only.
     */
    private String getUserResponse() {
        String answer = "";
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.print("Please answer (yes/no): ");
            answer = scanner.nextLine().trim();
            if (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                System.out.println("Invalid response. Please only answer 'yes' or 'no'.");
            }
        }
        return answer;
    }

    /**
     * Makes a final guess and handles the outcome based on user's response.
     */
    private void guessObject(TreeNode current) {
        System.out.println("Is your object a " + current.data + "?");
        String answer = getUserResponse();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("I win! I guessed your object correctly.");
        } else {
            learn(current);
        }
    }

    /**
     * Checks if the current node is a leaf node (answer node with no children).
     */
    private boolean isLeaf(TreeNode node) {
        return (node.yes == null && node.no == null);
    }

    /**
     * Expands the tree by learning from incorrect guesses through user input.
     */
    private void learn(TreeNode wrongAnswer) {
        System.out.println("I give up. What were you thinking of?");
        String correctObject = scanner.nextLine().trim();

        System.out.println("Please give me a yes/no question that would distinguish " + correctObject + " from " + wrongAnswer.data + ".");
        String newQuestion = scanner.nextLine().trim();

        System.out.println("For " + correctObject + ", what is the correct answer to your question? (yes/no)");
        String correctAnswer = getUserResponse();

        TreeNode newQuestionNode = new TreeNode(newQuestion);
        if (correctAnswer.equalsIgnoreCase("yes")) {
            newQuestionNode.yes = new TreeNode(correctObject);
            newQuestionNode.no = new TreeNode(wrongAnswer.data);
        } else {
            newQuestionNode.yes = new TreeNode(wrongAnswer.data);
            newQuestionNode.no = new TreeNode(correctObject);
        }

        replaceNode(null, root, wrongAnswer, newQuestionNode);
        saveTree();
    }

    /**
     * Replaces the incorrect answer node with a new question node, updating the tree.
     */
    private void replaceNode(TreeNode parent, TreeNode current, TreeNode target, TreeNode newNode) {
        if (current == null) return;
        if (current == target) {
            if (parent == null) {
                root = newNode; // Updating the root if necessary
            } else if (parent.yes == target) {
                parent.yes = newNode;
            } else if (parent.no == target) {
                parent.no = newNode;
            }
        } else {
            replaceNode(current, current.yes, target, newNode);
            replaceNode(current, current.no, target, newNode);
        }
    }

    /**
     * Saves the current state of the tree to a file.
     */
    private void saveTree() {
        try {
            FileOutputStream fileOut = new FileOutputStream("tree.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(root);
            out.close();
            fileOut.close();
            System.out.println("Knowledge has been saved.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //Loads the tree from a file if previously saved.

    private void loadTree() {
        try {
            FileInputStream fileIn = new FileInputStream("tree.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            root = (TreeNode) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading saved data. Starting with a new tree.");
            initializeTree();
        }
    }

    /**
     * Main method to run the application, allowing multiple games in a session.
     */
    public static void main(String[] args) {
        GuessingGame game = new GuessingGame();
        String playAgain;
        do {
            game.play();
            System.out.print("\nWould you like to play again? (yes/no): ");
            playAgain = game.scanner.nextLine().trim();
        } while (playAgain.equalsIgnoreCase("yes"));
        game.scanner.close();
    }
}
