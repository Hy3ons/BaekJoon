import java.time.temporal.Temporal;
import java.util.Scanner;

class Tree {
    Tree left;
    Tree right;
    int value;

    public static Tree update (Tree node, int value) {
        if (node==null) {
            node = new Tree();
            node.value = value;
            return node;
        }

        if (node.value<value) {
            Tree temp = update(node.right, value);
            if (temp!=null) node.right = temp;
        }
        else {
            Tree temp = update(node.left, value);
            if (temp!=null) node.left = temp;
        }
        return null;
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Tree tree = null;

        while(sc.hasNextLine()) {
            int value = Integer.parseInt(sc.nextLine());
            Tree temp = Tree.update(tree, value);
            if (temp!=null) tree = temp;
        }

        out(tree);
    }
    public static void out (Tree node) {
        if (node==null) return;

        out(node.left);
        out(node.right);
        System.out.println(node.value);
    }
}