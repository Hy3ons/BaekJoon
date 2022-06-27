import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Trie {
    private Trie[] leaf = new Trie[2];
    public int finished = 0;
    public int floor, value, tracked;
    private Trie father;

    Trie (int floor, Trie father) {
        this.floor = floor;
        this.father = father;
    }

    public static Trie update (Trie node, int[] arr, int depth, int value) {
        node.tracked++;
        if (depth==31) {
            node.finished++;
            node.value = value;
            return node;
        }

        if (node.leaf[arr[depth]]==null)
            node.leaf[arr[depth]] = new Trie(depth + 1, node);

        return update(node.leaf[arr[depth]], arr, depth+1, value);
    }

    public static int findValue (Trie node, int[] arr, int depth) {
        if (depth==31) {
            return node.value;
        }

        if (node.leaf[arr[depth]]==null||node.leaf[arr[depth]].tracked==0) {
            return findValue(node.leaf[arr[depth]^1], arr, depth+1);
        } else {
            return findValue(node.leaf[arr[depth]], arr, depth+1);
        }
    }

    public static void remove (Trie node, int[] arr, int depth) {
        node.tracked--;

        if (depth==31) {
            return;
        }

        remove(node.leaf[arr[depth]], arr, depth+1);
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        Trie tree = new Trie(0, null);

        // 0을 넣어준다.
        int[] zero = new int[31];
        Trie.update(tree, zero, 0, 0);

        while(testcase-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int method = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            switch (method) {
                case 1:
                    int[] v = new int[31];
                    for (int i=0;i<31;i++) {
                        if ((value&(1<<i))!=0) {
                            v[30-i]++;
                        }
                    }
                    Trie.update(tree, v, 0, value);
                    break;
                case 2:
                    int[] v2 = new int[31];
                    for (int i=0;i<31;i++) {
                        if ((value&(1<<i))!=0) {
                            v2[30-i]++;
                        }
                    }

                    Trie.remove(tree, v2, 0);
                    break;
                case 3 :
                    int[] v3 = new int[31];
                    for (int i=0;i<31;i++) {
                        if ((value&(1<<i))==0) {
                            v3[30-i]++;
                        }
                    }
                    //31번째 비트가 1로 가면 안된다.

                    int findV = Trie.findValue(tree, v3, 0);
                    bw.write((findV^value)+"\n");

                    break;
            }
        }
        bw.flush();
    }
}
