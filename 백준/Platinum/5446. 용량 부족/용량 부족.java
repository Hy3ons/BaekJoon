import java.io.*;
import java.util.ArrayList;

class Storage {
    private ArrayList<Storage> leaf = new ArrayList<Storage>();
    private char c;
    private boolean fin, ban;
    private int tracked;

    Storage(char c) {
        this.c = c;
    }

    public static void update (Storage node, char[] arr, int depth) {
        node.tracked++;

        if (arr.length==depth) {
            node.fin = true;
            return;
        }

        for (Storage st : node.leaf) {
            if (st.c==arr[depth]) {
                update(st, arr, depth+1);
                return;
            }
        }

        Storage temp = new Storage(arr[depth]);
        node.leaf.add(temp);
        update(temp, arr, depth+1);
    }

    public static void ban (Storage node, char[] arr, int depth) {
        node.ban = true;
        if (depth==arr.length) return;

        for (Storage st : node.leaf) {
            if (st.c == arr[depth]) {
                ban(st, arr, depth+1);
                return;
            }
        }
    }

    public static int answer (Storage node) {
        if (!node.ban) {
//            System.out.println(node.c +": "+node.tracked);
            return 1;
        }

        int answer = node.fin ? 1 : 0;
        for (Storage st : node.leaf)
            answer += answer(st);

        return answer;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());

        while (testcase-->0) {
            Storage st = new Storage('*');
            int N = Integer.parseInt(br.readLine());
            while (N-->0) {
                char[] s = br.readLine().toCharArray();
                Storage.update(st, s, 0);
            }

            N = Integer.parseInt(br.readLine());
            while (N-->0) {
                char[] s = br.readLine().toCharArray();
                Storage.ban(st, s, 0);
            }

            int result = Storage.answer(st);
            bw.write(result+"\n");
        }
        bw.flush();
    }
}
