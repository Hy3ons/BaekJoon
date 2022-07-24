import java.io.*;
import java.util.StringTokenizer;

class Segment {
    private long[] tree;
    private int[] time;
    private long[] lazy;

    private int start =1 , leftIdx, rightIdx, value;

    Segment (int[] arr) {
        while (start < arr.length) start *= 2;

        tree = new long[start *2];
        time = new int[start * 2];
        lazy = new long[start * 2];

        for (int i=0;i<arr.length;i++) {
            tree[start + i] = arr[i];
        }
    }
    public void setValue (int value) {
        this.value = value;
    }

    public void update (int left, int right) {
        leftIdx = left;
        rightIdx = right;
        this.value = 1;
        update(1, 1, start);
    }
    private void update (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return;

        if (lazy[node] != 0) propagate(node, left, right);

        if (leftIdx <= left && right <= rightIdx) {
            lazy[node] += value;
            time[node]++;
            value += right - left + 1;
            return;
        }

        int mid = (left + right) / 2;
        update(node*2, left, mid);
        update(node*2+1, mid+1, right);
    }
    private void propagate (int node, int left, int right) {
        int N = right - left + 1;

        if (node >= start) {
            tree[node] += lazy[node];
            lazy[node] = time[node] = 0;
            return;
        }
        int mid = (left + right) / 2;

        lazy[node*2] += lazy[node];
        lazy[node*2+1] += lazy[node] + ((long) time[node] * (mid - left + 1));
        time[node*2] += time[node];
        time[node*2+1] += time[node];

        lazy[node] = time[node] = 0;
    }
    public long find (int node) {
        leftIdx = rightIdx = node;
        return query(1,1,start);
    }
    private long query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (lazy[node] != 0) propagate(node, left, right);

        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        int mid = (left + right) / 2;
        return query(node*2, left, mid) + query(node*2+1, mid+1, right);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int size = Integer.parseInt(br.readLine());
        int[] arr = new int[size];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        int queries = Integer.parseInt(br.readLine());
        Segment tree = new Segment(arr);

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int method = Integer.parseInt(st.nextToken());

            if (method == 1) {
                int left = Integer.parseInt(st.nextToken());
                int right = Integer.parseInt(st.nextToken());
                tree.update(left, right);
            } else {
                bw.write(tree.find(Integer.parseInt(st.nextToken()))+"\n");
            }
        }
        bw.flush();
    }
}