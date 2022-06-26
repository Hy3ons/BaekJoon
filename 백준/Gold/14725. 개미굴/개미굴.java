import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

class Trie {
    private ArrayList<Trie> leaf = new ArrayList<>();
    private boolean finished, hasLeaf;
    private int floor;
    private String s, f = "--";

    Trie (int floor, String s) {
        this.floor = floor;
        this.s = s;
    }
    public static void update (Trie node, String[] a, int index) throws Exception {
        if (index==a.length) {
            node.finished = true;
            return;
        }


        for (int i=0;i<node.leaf.size();i++) {
            if (node.leaf.get(i).s.equals(a[index])) {
                update(node.leaf.get(i), a, index+1);
                return;
            }
        }

        Trie temp = new Trie(index+1, a[index]);
        node.leaf.add(temp);
        update(temp, a, index+1);

    }

    public static void print(Trie node){
        Collections.sort(node.leaf, new Comparator<Trie>() {
            @Override
            public int compare(Trie o1, Trie o2) {
                return o1.s.compareTo(o2.s);
            }
        });

        for (int i=0;i<node.leaf.size();i++) {
            Trie temp = node.leaf.get(i);
            sub(node);
            System.out.println(temp.s);
            print(temp);
        }
    }

    private static void sub (Trie node) {
        for (int i=0;i<node.floor;i++) {
            System.out.print(node.f);
        }
    }


}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());
        Trie tree = new Trie(0, null);

        K: while (testcase-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int amount = Integer.parseInt(st.nextToken());
            String[] s = new String[amount];

            for (int i=0;i<amount;i++) {
                s[i] = st.nextToken();
            }
            Trie.update(tree, s, 0);
        }

        Trie.print(tree);
    }
}
