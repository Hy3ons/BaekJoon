import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

class Tree {
    public static long MOD = 1000000007;
    private long[] tree, lazy, lazy2;
    boolean[] isLazy;
    private int leftIdx, rightIdx;
    public int value, start = 1;

    Tree (int[] arr) {
        while (start < arr.length) start *= 2;

        tree = new long[start*2];
        lazy = new long[start*2];
        lazy2 = new long[start*2];
        isLazy = new boolean[start*2];

        Arrays.fill(lazy, 1);

        for (int i=0;i<arr.length;i++) {
            tree[start+i] = arr[i];
        }

        for (int i = start/2;i!=0;i /=2) {
            for (int j=i;j<i*2;j++) {
                tree[j] = tree[j*2] + tree[j*2+1];
            }
        }

        for (int i=0;i<tree.length;i++) tree[i] %= MOD;
    }

    public void setIdx (int left, int right) {
        this.leftIdx = left;
        this.rightIdx = right;
    }

    private void propagate (int node, int left , int right) {
        if (!isLazy[node]) return;
        isLazy[node] = false;

        tree[node] = (((lazy[node] * tree[node]) % MOD)+ (lazy2[node] * (right - left + 1) % MOD))% MOD;

        if (node >= start) {
            lazy[node] = 1;
            lazy2[node] = 0;
            return;
        }
        isLazy[node*2] = isLazy[node*2+1] = true;

        lazy[node*2] *= lazy[node];
        lazy[node*2+1] *= lazy[node];
        lazy2[node*2] *= lazy[node];
        lazy2[node*2+1] *= lazy[node];

        lazy2[node*2] += lazy2[node];
        lazy2[node*2+1] += lazy2[node];

        lazy[node*2] %= MOD;
        lazy2[node*2] %= MOD;
        lazy[node*2+1] %= MOD;
        lazy2[node*2+1] %= MOD;
        lazy[node] = 1;
        lazy2[node] = 0;
    }

    public long answer () {
        return query(1,1, start) % MOD;
    }

    private long query (int node, int left, int right) {
        if (right < leftIdx || rightIdx  < left) return 0;

        if (isLazy[node]) propagate(node, left, right);

        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        int mid = (left + right) / 2;

        return (query(node*2, left, mid) % MOD) +
                (query(node*2+1, mid+1,right) % MOD);
    }

    public void update(int node, int left, int right, int a, int b) {
        if (isLazy[node]) propagate(node, left, right);

        if (right < leftIdx || rightIdx  < left) return;

        if (leftIdx <= left && right <= rightIdx) {
            lazy[node] = a;
            lazy2[node] = b;
            isLazy[node] = true;
            propagate(node, left, right);
            return;
        }

        int mid = (left + right) / 2;

        update(node*2, left, mid, a,b);
        update(node*2+1, mid+1, right, a,b);

        tree[node] = (tree[node*2] + tree[node*2+1]) % MOD;
    }
}

public class Main {
    public static void main(String[] args ) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int length = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[length];

        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        int amount = Integer.parseInt(br.readLine());
        Tree tree = new Tree(arr);

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(br.readLine());

            int method = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            tree.setIdx(left, right);

            if (method==4) {
                bw.write(tree.answer()+"\n");
                continue;
            }

            tree.value = Integer.parseInt(st.nextToken()) % 1000000007;

            switch (method) {
                case 1:
                    //더하기
                    tree.update(1, 1, tree.start, 1, tree.value);
                    break;
                case 2 :
                    tree.update(1, 1, tree.start, tree.value, 0);
                    break;
                case 3 :
                    tree.update(1, 1, tree.start, 0, tree.value);
                    break;
            }
        }
        bw.flush();
    }
}
