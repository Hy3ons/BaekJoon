import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Stack;
import java.util.StringTokenizer;

class Triee {
    public static HashSet<String> names = new HashSet<>();
    private char s;
    private Triee[] leaf = new Triee[26];
    private boolean fin;

    Triee (char s) {
        this.s = s;
    }

    public static void update (Triee node, char[] arr, int depth) {
        if (depth==arr.length) {
            node.fin = true;
            return;
        }

        if (node.leaf[arr[depth]-'a']==null)
            node.leaf[arr[depth]-'a'] = new Triee(arr[depth]);

        update(node.leaf[arr[depth]-'a'], arr, depth+1);
    }

    public static boolean check (Triee node, char[] arr, int depth, String s) {
        if (arr.length == depth) {
            return false;
        }

        if (node.fin) {
            String temp = s.substring(depth, s.length());
            if (Triee.names.contains(temp)) {
                return true;
            }
        }

        if (node.leaf[arr[depth]-'a']==null)
            return false;

        return check(node.leaf[arr[depth]-'a'], arr, depth+1, s);
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int colAmount = Integer.parseInt(st.nextToken());
        int namAmount = Integer.parseInt(st.nextToken());

        Triee trie = new Triee('*');
        HashSet<String> names = Triee.names;

        while(colAmount-->0) {
            char[] s = br.readLine().toCharArray();
            Triee.update(trie, s, 0);
        }

        while (namAmount-->0) {
            String str = br.readLine();
            names.add(str);
        }

        int testcase = Integer.parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


        while (testcase-->0) {
            String str = br.readLine();
            char[] s = str.toCharArray();
            if (Triee.check(trie, s, 0, str))
                bw.write("Yes\n");
            else
                bw.write("No\n");
        }
        bw.flush();
    }
}
