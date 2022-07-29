import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Segment {
    private int[] tree, lazy;
    private int start =1, leftIdx, rightIdx;

    Segment (int size) {
        while (start < size) start *= 2;

        tree = new int[start * 2];
        lazy = new int[start * 2];
    }

    public void update (int left, int right, int value) {
        leftIdx = left;
        rightIdx = right;

        update(1, 1, start, value);
    }
    private void update (int node, int left, int right, int value) {
        if (lazy[node] != 0) propagate(node, left, right);

        if (right < leftIdx || rightIdx < left) return;

        if (leftIdx <= left && right <= rightIdx) {
            lazy[node] = value;
            propagate(node, left, right);
            return;
        }

        int mid = (left + right) / 2;

        update(node*2, left, mid, value);
        update(node*2+1, mid+1, right, value);

        tree[node] = tree[node*2] + tree[node*2+1];
    }
    private void propagate (int node, int left, int right) {
        if (node >= start) {
            tree[node] += lazy[node];
            lazy[node] = 0;
            return;
        }

        lazy[node*2] += lazy[node];
        lazy[node*2+1] += lazy[node];
        lazy[node] = 0;
    }
    private int query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (lazy[node] != 0) propagate(node, left, right);

        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        int mid = (left + right) / 2;

        return query(node*2, left, mid) + query(node*2+1, mid+1, right);
    }
    public int find (int person) {
        leftIdx = rightIdx = person;
        return query(1, 1, start);
    }
}

public class Main {
    public static ArrayList<ArrayList<Integer>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int size = Integer.parseInt(st.nextToken());
        int queries = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        st.nextToken();

        load = new ArrayList<>(size+1);
        for (int i=0;i<=size;i++) load.add(new ArrayList<>());

        for (int i=2;i<=size;i++) {
            load.get(Integer.parseInt(st.nextToken())).add(i);
        }

        in = new int[size+1];
        out = new int[size+1];

        dfs(1);
        Segment tree = new Segment(size);
        while (queries-->0) {
            st = new StringTokenizer(br.readLine());

            int M = Integer.parseInt(st.nextToken());

            if (M == 1) {
                int boss = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());

                tree.update(in[boss], out[boss], value);

            } else {
                int person = Integer.parseInt(st.nextToken());
                bw.write(tree.find(in[person])+"\n");
            }
        }
        bw.flush();

    }

    public static int[] in, out;
    public static int cnt;

    public static void dfs (int node) {
        in[node] = ++cnt;
        for (int go : load.get(node)) dfs(go);
        out[node] = cnt;
    }
}