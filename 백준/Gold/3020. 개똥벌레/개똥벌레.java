import java.io.*;
import java.util.StringTokenizer;

class Seg {
    private long[] tree;
    int start = 1, h;
    Seg (int size) {
        while (start < size) start <<= 1;
        tree = new long[start << 1];
        h = size;
    }

    public void update (int node, int s, int e, int left, int right) {
        if (e < left || right < s) return;

        if (s <= left && right <= e) {
            tree[node]++;
            return;
        }

        int mid = left + right >> 1;
        update(node*2, s, e, left, mid);
        update(node*2+1, s, e, mid+1, right);
    }
    public void query() throws IOException {
        for (int i=0;i<start;i++) {
            tree[i*2] += tree[i];
            tree[i*2+1] += tree[i];
        }

        int count = 0;
        long min = Long.MAX_VALUE;
        for (int i=0;i<h;i++) {
            if (tree[start + i] < min) {
                min = tree[start + i];
                count = 1;
            } else if (tree[start + i] == min) count++;
        }
        System.out.println(min+" "+count);
    }
}


public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int queries = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        Seg tree = new Seg(h);

        for (int i=0;i<queries;i++) {
            if ((i & 1) == 1) {
                tree.update(1, h - Integer.parseInt(br.readLine()) + 1, h, 1, tree.start);
            } else {
                tree.update(1, 1, Integer.parseInt(br.readLine()), 1, tree.start);
            }
        }
        tree.query();
    }
}