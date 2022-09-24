import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Seg {
    int start = 1;
    private long[] tree;
    Seg (int size) {
        while(start < size) start <<= 1;

        tree = new long[start << 1];
    }

    public void update(int idx, long value) {
        idx += start;
        tree[idx] = value;
        idx >>= 1;
        while(idx != 0) {
            tree[idx] = Math.max(tree[idx*2], tree[idx*2+1]);
            idx >>= 1;
        }
    }

    public long query(int node, int s, int e, int left, int right) {
        if (e < left || right < s) return Long.MIN_VALUE;

        if (s <= left && right <= e) return tree[node];
        int mid = left + right >> 1;

        return Math.max(query(node*2, s, e, left, mid), query(node*2+1, s, e, mid+1, right));
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()), k = Integer.parseInt(st.nextToken());

        Seg tree = new Seg(n);

        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        long[] dp = new long[n];
        long max = Long.MIN_VALUE;
        max = Math.max(max, arr[0]);
        dp[0] = arr[0];
        tree.update(0, arr[0]);

        for (int i=1;i<dp.length;i++) {
            dp[i] = tree.query(1, Math.max(1, i+1-k), i+1, 1, tree.start);
            dp[i] += arr[i];
            tree.update(i, dp[i]);
            max = Math.max(max, dp[i]);
        }
        System.out.println(max);
    }
}