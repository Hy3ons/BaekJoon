import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

class Tree {
    Tree left;
    Tree right;
    int value;

    Tree (int value) {
        this.value = value;
    }

    public static void update (Tree node, int value) {
        if (node.value<value) {
            if (node.right==null) {
                node.right = new Tree(value);
                return;
            }
            update(node.right, value);
        } else {
            if (node.left==null) {
                node.left = new Tree(value);
                return;
            }
            update(node.left, value);
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Tree tree = new Tree(Integer.parseInt(br.readLine()));
        String input = "";

        while((input = br.readLine())!=null) {
            int value = Integer.parseInt(input);
            Tree.update(tree, value);
        }

        out(tree);
    }
    public static void out (Tree node) {
        if (node.left!=null)
            out(node.left);
        if (node.right!=null)
            out(node.right);

        System.out.println(node.value);
    }
}