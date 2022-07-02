import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Tree {
    public char s;
    public Tree left, right;
    Tree (char s) {
        this.s = s;
    }
}

public class Main {
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        Tree[] nodes = new Tree[26];
        for (int i=0;i<26;i++) {
            nodes[i] = new Tree((char)('A'+i));
        }

        while(testcase-->0) {
            char[] ary = br.readLine().toCharArray();
            if (ary[2]!='.')
                nodes[ary[0]-'A'].left = nodes[ary[2]-'A'];
            if (ary[4]!='.')
                nodes[ary[0]-'A'].right = nodes[ary[4]-'A'];
        }

        recursive1(nodes[0]);
        System.out.println();
        recursive2(nodes[0]);
        System.out.println();
        recursive3(nodes[0]);

    }

    public static void recursive1 (Tree node) {
        if (node==null) return;
        System.out.print(node.s);
        recursive1(node.left);
        recursive1(node.right);
    }
    public static void recursive2 (Tree node) {
        if (node==null) return;
        recursive2(node.left);
        System.out.print(node.s);
        recursive2(node.right);
    }
    public static void recursive3 (Tree node) {
        if (node==null) return;
        recursive3(node.left);
        recursive3(node.right);
        System.out.print(node.s);
    }
}
