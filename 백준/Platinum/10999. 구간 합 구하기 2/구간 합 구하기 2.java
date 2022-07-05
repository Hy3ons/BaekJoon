import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
class Tree {
    private long[] tree;
    private long[] lazy;
    private int leftIdx, rightIdx, start;
    private long value;
    Tree (long[] arr) {
        start = getStart(arr.length);

        tree = new long[start*2];
        lazy = new long[start*2];

        for (int i=0;i<arr.length;i++) {
            int treeIdx = start+i;
            tree[treeIdx] = arr[i];
            treeIdx /= 2;

            while (treeIdx!=0) {
                tree[treeIdx] = tree[treeIdx*2] + tree[treeIdx*2+1];
                treeIdx /= 2;
            }
        }

    }
    private int getStart (int testcase) {
        int result = 1;
        while (true) {
            if (result>=testcase) return result;
            result *= 2;
        }
    }

    public int getStart () {
        return start;
    }

    public void setIdx (int leftIdx, int rightIdx) {
        this.leftIdx = leftIdx;
        this.rightIdx = rightIdx;
    }

    public void setValue (long value) {
        this.value = value;
    }

    public long query (int node, int nowLeftIdx, int nowRightIdx) {
        if (nowRightIdx<leftIdx||rightIdx<nowLeftIdx) return 0;
        if (lazy[node]!=0) {
            propagate(node, nowLeftIdx, nowRightIdx);
        }

        if (leftIdx <= nowLeftIdx && nowRightIdx <= rightIdx) return tree[node];

        int mid = (nowRightIdx+nowLeftIdx)/2;

        return query(node*2, nowLeftIdx, mid)
                + query(node*2+1, mid+1, nowRightIdx);
    }

    public void update (int node, int nowLeftIdx, int nowRightIdx) {
        if (nowRightIdx<leftIdx||rightIdx<nowLeftIdx) return;

        if (leftIdx<=nowLeftIdx&&nowRightIdx<=rightIdx) {
            lazy[node] += value;
            return;
        } else {
            int afs = Math.min(nowRightIdx, rightIdx) - Math.max(leftIdx, nowLeftIdx) + 1;
            tree[node] += afs*value;
        }

        int mid = (nowLeftIdx+nowRightIdx)/2;

        update(node*2, nowLeftIdx, mid);
        update(node*2+1, mid+1, nowRightIdx);
    }

    private void propagate (int node, int nowLeftIdx, int nowRightIdx) {
        tree[node] += lazy[node]*(nowRightIdx-nowLeftIdx+1);

        if (node>=start) {
            lazy[node] = 0;
            return;
        }

        lazy[node*2] += lazy[node];
        lazy[node*2+1] += lazy[node];
        lazy[node] = 0;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int queryAmount = Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken());

        long[] arr = new long[testcase];

        for (int i=0;i<testcase;i++) arr[i] = Long.parseLong(br.readLine());
        Tree tree = new Tree(arr);

        int start = tree.getStart();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i=0;i<queryAmount;i++) {
            st = new StringTokenizer(br.readLine());
            int method = Integer.parseInt(st.nextToken());

            switch (method) {
                case 1 :
                    tree.setIdx(Integer.parseInt(st.nextToken())
                            , Integer.parseInt(st.nextToken()));

                    tree.setValue(Long.parseLong(st.nextToken()));
                    tree.update(1, 1, start);

                    break;
                case 2 :
                    tree.setIdx(Integer.parseInt(st.nextToken())
                            , Integer.parseInt(st.nextToken()));

                    bw.write(tree.query(1,1, start)+"\n");
                    break;
            }
        }

        bw.flush();
    }

}
