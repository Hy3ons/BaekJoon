import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class Trie {
    private Trie[] leaf = new Trie[10];
    private boolean finished, hasLeaf;
    public static void update (Trie node, char[] a, int index) throws Exception {
        if (index==a.length) {
            if (node.hasLeaf) throw new Exception();
            node.finished = true;
            return;
        }

        if (node.finished) throw new Exception();

        int number = a[index] - '0';

        if (node.leaf[number] == null) {
            node.leaf[number] = new Trie();
            node.hasLeaf = true;
        }

        update(node.leaf[number], a, index+1);
    }


}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        K: while (testcase-->0) {
            int amount = Integer.parseInt(br.readLine());
            boolean fail = false;
            Trie tree = new Trie();

            for (int i=0;i<amount;i++) {
                char[] number = br.readLine().toCharArray();

                if (fail) continue;
                try {
                    Trie.update(tree, number, 0);
                } catch (Exception ex) {
                    fail = true;
                }

            }
            if (fail) bw.write("NO\n");
            else bw.write("YES\n");
        }
        bw.flush();
    }
}
