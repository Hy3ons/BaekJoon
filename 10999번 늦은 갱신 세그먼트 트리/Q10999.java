    /*
    3월동안 배운 객체의 개념을 다 끌어내어 해당 문제를 풀어내는 tree 클래스를 정의하였다.
    세그먼트 트리는 tree 배열에 저장되며, 늦은 갱신을 위한 정보는 lazy에 담겨있게 된다.
    
    각 인덱스에 모두 값을 갱신하면, 시간이 nlog n 이 걸리지만, 탑다운으로 갱신후, 대표 노드에서 멈춘다음,
    propagate 함수로 query로 접근된 노드에 대해 값을 갱신하면 query함수를 실행시키는 시간 O(log N) 에서 갱신작업이 되는 것이기 때문에,
    총 연산량은 2 log n 이 된다. n 번반복 하면 O(NlogN) 이다.
    */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Tree {
    private long[] tree, lazy;
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

public class Q10999 {
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
