import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class segTree {
    private long[] lazy, tree;
    public int start = 1;
    private int leftIdx, rightIdx, value;

    segTree (long[] arr) {
        while (start<arr.length) start *=2;

        lazy = new long[start*2];
        tree = new long[start*2];
        for (int i=0;i<arr.length;i++) {
            tree[start+i] = arr[i];
        }
    }

    public void update (int node, int nowLeft, int nowRight) {
        if (nowRight<leftIdx||rightIdx<nowLeft) return;
        if (lazy[node]!=0) propagate(node);

        if (leftIdx<=nowLeft&&nowRight<=rightIdx) {
            lazy[node] ^= value;
            return;
        }

        int mid = (nowLeft + nowRight) / 2;

        update(node*2, nowLeft, mid);
        update(node*2+1, mid+1, nowRight);
    }

    private void propagate (int node) {
        if (node>=start) {
            tree[node] ^= lazy[node];
            lazy[node] = 0;
            return;
        }

        lazy[node*2] ^= lazy[node];
        lazy[node*2+1] ^= lazy[node];
        lazy[node] = 0;
    }

    public void setValue (int value) {
        this.value = value;
    }

    public void setIdx (int left, int right) {
        this.leftIdx = left;
        this.rightIdx = right;
    }

    public long query (int node, int nowLeft, int nowRight) {
        if (nowRight<leftIdx||rightIdx<nowLeft) return 0;
        if (lazy[node]!=0) propagate(node);

        if (leftIdx<=nowLeft&&nowRight<=rightIdx) {
            return tree[node];
        }

        int mid = (nowLeft + nowRight) / 2;

        return query(node*2, nowLeft, mid) +
            query(node*2+1, mid+1, nowRight);
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        long[] arr = new long[testcase];

        StringTokenizer st = new StringTokenizer(br.readLine());
        int queryAmount = Integer.parseInt(br.readLine());

        for (int i=0;i<arr.length;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        segTree tree = new segTree(arr);

        for (int i=0;i<queryAmount;i++) {
            st = new StringTokenizer(br.readLine());
            int method = Integer.parseInt(st.nextToken());

            switch (method) {
                case 1:
                    tree.setIdx(Integer.parseInt(st.nextToken()) + 1,
                            Integer.parseInt(st.nextToken())+ 1);
                    tree.setValue(Integer.parseInt(st.nextToken()));
                    tree.update(1, 1, tree.start);
                    break;
                case 2 :
                    int set = Integer.parseInt(st.nextToken()) + 1;
                    tree.setIdx(set, set);
                    bw.write(tree.query(1, 1, tree.start)+"\n");
                    break;
            }
        }
        bw.flush();
    }
}
