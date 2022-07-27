import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Tree {
    private long[] tree, lazy;
    private int start = 1, leftIdx, rightIdx, value;

    Tree(int[] arr, int[] in, int[] out) {
        while (start < arr.length-1) start *= 2;

        tree = new long[start * 2];
        lazy = new long[start * 2];

        for (int i=1;i<arr.length;i++) {
            tree[start + in[i] - 1] = arr[i];
        }

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j< i*2;j++) {
                tree[j] = tree[j*2] + tree[j*2+1];
            }
        }
    }

    private void propagate (int node, int left, int right) {
        tree[node] += (right - left + 1) * lazy[node];

        if (node >= start) {
            lazy[node] = 0;
            return;
        }

        lazy[node*2] += lazy[node];
        lazy[node*2+1] += lazy[node];
        lazy[node] = 0;
    }
    private void update (int node, int left, int right) {
        if (lazy[node] != 0) propagate(node, left, right);

        if (right < leftIdx || rightIdx < left) return;

        if (leftIdx <= left && right <= rightIdx) {
            lazy[node] = value;
            propagate(node, left, right);
            return;
        }

        int mid = (left + right) / 2;

        update(node*2, left, mid);
        update(node*2+1, mid+1, right);

        tree[node] = tree[node*2] + tree[node*2+1];
    }
    private long query(int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (lazy[node] != 0) propagate(node, left, right);

        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        int mid = (left + right) / 2;

        return query(node*2, left, mid)
                + query(node*2+1, mid+1, right);
    }
    public void function (int left, int right, int value) {
        this.value = value;
        this.leftIdx = left;
        this.rightIdx = right;

        update(1, 1, start);
    }
    public long find (int node) {
        leftIdx = rightIdx = node;
        return query(1, 1, start);
    }
}

public class Main {
    public static ArrayList<ArrayList<Integer>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int people = Integer.parseInt(st.nextToken());
        int queries = Integer.parseInt(st.nextToken());

        int[] first = new int[people+1];
        first[1] = Integer.parseInt(br.readLine());

        load = new ArrayList<>(people+1);
        for (int i=0;i<=people;i++) load.add(new ArrayList<>());

        for (int i=2;i<=people;i++) {
            st = new StringTokenizer(br.readLine());
            int money = Integer.parseInt(st.nextToken());
            int boss = Integer.parseInt(st.nextToken());

            load.get(boss).add(i);
            first[i] = money;
        }

        in = new int[people+1];
        out = new int[people+1];

        dfs(1);
        Tree tree = new Tree(first, in, out);

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            char m = st.nextToken().charAt(0);

            if (m == 'p') {
                int boss = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                tree.function(in[boss]+1, out[boss], value);
            } else {
                int find = Integer.parseInt(st.nextToken());

                bw.write(tree.find(in[find])+"\n");
            }
        }
        bw.flush();

    }
    public static int cnt;
    public static int[] in, out;

    public static void dfs (int node) {
        in[node] = ++cnt;
        for (int go : load.get(node)) dfs(go);
        out[node] = cnt;
    }
}
