import javax.swing.plaf.BorderUIResource;
import java.io.*;
import java.util.*;

class Node {
    Node left, right;
    int value;
    Node (int value) {
        this.value = value;
    }

    Node (Node left, Node right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
}

class SegP {
    private Node[] starts = new Node[500001];
    private int size = 524287, start = 1, leftIdx, rightIdx;
    private int nowCapacity;

    SegP() {
        while (start < 524288) start <<= 1;
        Node[] nodes = new Node[start * 2];
        for (int i=0;i<nodes.length;i++) nodes[i] = new Node(0);

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                nodes[j].left = nodes[j*2];
                nodes[j].right = nodes[j*2+1];
            }
        }
        starts[0] = (nodes[1]);
    }

    public void sUpdate (int value) {
        leftIdx = rightIdx = value;
        starts[nowCapacity+1] = update(starts[nowCapacity], 0, size, 1);
        nowCapacity++;
    }

    public int findKth (int left, int right, int K) {
        return query(starts[left - 1], starts[right], 0, size, K);
    }

    public void remove (int value) {
        nowCapacity -= value;
    }

    public int maxXOR (int left, int right, int value) {
        boolean[] arr = new boolean[19];
        for (int i=18;i>=0;i--) {
            arr[18-i] = (value & 1<<i) != 0;
        }

        return findXOR(starts[left-1], starts[right], 0, size, 0, arr);
    }

    public int findXOR (Node prev, Node now, int left, int right, int depth, boolean[] arr) {
        if (depth == 19) return right;

        int mid = left + right >> 1;

        if (arr[depth]) {
            if (now.left.value - prev.left.value > 0) return findXOR(prev.left, now.left, left, mid, depth+1, arr);
            else return findXOR(prev.right, now.right, mid+1, right, depth+1, arr);
        } else {
            if (now.right.value - prev.right.value > 0) return findXOR(prev.right, now.right, mid+1, right, depth+1, arr);
            else return findXOR(prev.left, now.left, left, mid, depth+1, arr);
        }
    }


    private int query (Node prev, Node now, int left, int right, int K) {
        if (right == left) return right;

        int mid = left + right >> 1;
        int dis = now.left.value - prev.left.value;

        if (dis >= K) {
            return query(prev.left, now.left, left, mid, K);
        } else {
            return query(prev.right, now.right, mid+1, right, K - dis);
        }
    }
    public int findMin (int left, int right, int value) {
        leftIdx = 1;
        rightIdx = value;

        return sumQuery(starts[left-1], starts[right], 0, size);
    }

    private int sumQuery (Node prev, Node now, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (leftIdx <= left && right <= rightIdx) {
            return now.value - prev.value;
        }

        int mid = left + right >> 1;

        return sumQuery(prev.left, now.left, left, mid)
                + sumQuery(prev.right, now.right, mid+1, right);
    }

    private Node update (Node node, int left, int right, int value) {
        if (right < leftIdx || rightIdx < left) return node;

        if (leftIdx <= left && right <= rightIdx) {
            return new Node(value + node.value);
        }

        int mid = left + right >> 1;

        Node left_leaf = update(node.left, left, mid, value);
        Node right_leaf = update(node.right, mid+1, right, value);
        return new Node(left_leaf, right_leaf, left_leaf.value+ right_leaf.value);
    }
}

public class Main {
    public static SegP tree = new SegP();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int queries = Integer.parseInt(br.readLine());

        while(queries-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int left, right, value;

            int M = Integer.parseInt(st.nextToken());
            switch (M) {
                case 1:
                    value = Integer.parseInt(st.nextToken());
                    tree.sUpdate(value);
                    break;
                case 2:
                    left = Integer.parseInt(st.nextToken());
                    right = Integer.parseInt(st.nextToken());
                    value = Integer.parseInt(st.nextToken());

                    bw.write(tree.maxXOR(left, right, value)+"\n");

                    break;
                case 3:
                    value = Integer.parseInt(st.nextToken());
                    tree.remove(value);
                    break;
                case 4:
                    left = Integer.parseInt(st.nextToken());
                    right = Integer.parseInt(st.nextToken());
                    value = Integer.parseInt(st.nextToken());

                    bw.write(tree.findMin(left, right, value)+"\n");
                    break;
                case 5:
                    left = Integer.parseInt(st.nextToken());
                    right = Integer.parseInt(st.nextToken());
                    value = Integer.parseInt(st.nextToken());

                    bw.write(tree.findKth(left, right, value)+"\n");
            }
        }
        bw.flush();
    }

}
