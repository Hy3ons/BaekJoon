import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Node {
    int height, min, max = Segment.INF, isLazy;

    public void clear () {
        max = Segment.INF;
        min = 0;
        isLazy = 0;
    }
}

class Segment {
    private Node[] tree;
    private int start =1, leftIdx, rightIdx, size;
    public static int INF = 1000000000;

    Segment (int size) {
        while (start < size) start *= 2;

        this.size = size;
        tree = new Node[start * 2];
        for (int i=0;i<tree.length;i++) tree[i] = new Node();
    }

    public void update (int left, int right, int value, int mode) {
        leftIdx = left;
        rightIdx = right;

        update(1, 1, start, value, mode);
    }
    private void update (int node, int left, int right, int value, int mode) {
        if (tree[node].isLazy == 1) propagate(node, 0);

        if (right < leftIdx || rightIdx < left) return;

        if (leftIdx <= left && right <= rightIdx) {
            if (mode == 1)
                tree[node].min = value;
            else
                tree[node].max = value;

            propagate(node, mode);
            return;
        }

        int mid = (left + right) / 2;

        update(node*2, left, mid, value, mode);
        update(node*2+1, mid+1, right, value, mode);
    }
    private void propagate (int node, int mode) {
        if (node >= start) {
            int min = Math.max(tree[node].height, tree[node].min);
            tree[node].height = Math.min(min, tree[node].max);
            tree[node].clear();
            return;
        }
        subPropa(tree[node], tree[node*2], mode);
        subPropa(tree[node], tree[node*2+1], mode);
        tree[node].clear();
    }

    private void subPropa (Node from, Node to, int mode) {
        to.isLazy = 1;

        if (to.min > from.max) {
            to.max = to.min = from.max;
        } else if (from.min > to.max) {
            to.min = to.max = from.min;
        } else {
            to.max = Math.min(to.max, from.max);
            to.min = Math.max(to.min, from.min);
        }
    }

    public void reload () {
        for (int i=0;i<tree.length;i++) {
            if (tree[i].isLazy != 0) {
                propagate(i, 0);
            }
        }
    }

    public void answer () throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i=0;i<size;i++) {
            bw.write(tree[i+ start].height+"\n");
        }
        bw.flush();
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int size = Integer.parseInt(st.nextToken());
        int queries = Integer.parseInt(st.nextToken());

        Segment tree = new Segment(size);
        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            // 1 : 더하기 2: 빼기
            int left = Integer.parseInt(st.nextToken())+1;
            int right = Integer.parseInt(st.nextToken())+ 1;
            int value = Integer.parseInt(st.nextToken());

            tree.update(left, right, value, M);

        }
        tree.reload();
        tree.answer();

    }
}