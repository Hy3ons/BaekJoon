import java.io.*;

class Trie {
    private Trie[] leaf = new Trie[26];
    char now;
    boolean finish;
    Trie (char s) {
        now = s;
    }

    public static void update (Trie now, char[] sequence, int depth) {
        if (depth == sequence.length) {
            now.finish = true;
            return;
        }

        int go = sequence[depth] - 'a';

        if (now.leaf[go] == null) {
            now.leaf[go] = new Trie(sequence[depth]);
        }

        update(now.leaf[go], sequence, depth+1);
    }
    public static boolean match (Trie now, char[] sequence, int idx) {
        if (now.finish) return true;
        if (sequence.length == idx) return false;

        int go = sequence[idx] - 'a';

        if (now.leaf[go] == null) return false;
        return match(now.leaf[go], sequence, idx+1  );
    }
}

public class Main {
    public static int N, K;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        Trie trie = new Trie(' ');
        for (int i=0;i<N;i++) {
            Trie.update(trie, br.readLine().toCharArray(), 0);
        }

        K = Integer.parseInt(br.readLine());
        C: while(K-->0) {
            char[] test = br.readLine().toCharArray();

            for (int i=0;i<test.length;i++) {
                if (Trie.match(trie, test, i)) {
                    bw.write("YES\n");
                    continue C;
                }
            }
            bw.write("NO\n");
        }
        bw.flush();

    }
}