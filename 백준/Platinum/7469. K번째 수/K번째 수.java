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
    ArrayList<Node> starts = new ArrayList<>();
    int size, start = 1, leftIdx, rightIdx;

    SegP(int[] arr) {
        size = arr.length;
        while (start < arr.length) start *= 2;

        Node[] nodes = new Node[start * 2];
        for (int i=0;i<nodes.length;i++) nodes[i] = new Node(0);

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                nodes[j].left = nodes[j*2];
                nodes[j].right = nodes[j*2+1];
            }
        }
        starts.add(nodes[1]);

        for (int i=0;i<arr.length;i++) {
            sUpdate(Main.encode.get(arr[i]) + 1, 1);
        }
    }

    public void sUpdate (int idx, int value) {
        leftIdx = rightIdx = idx;
        starts.add(update(starts.get(starts.size()-1), 1, start, value));
    }

    public int find (int left, int right, int K) {
        return Main.ordered[query(starts.get(left-1), starts.get(right), 1, start, K)-1];
    }

    private int query (Node prev, Node using, int left, int right, int K) {
        if (right == left) return right;

        int mid = (left + right)/2;
        int dis = using.left.value - prev.left.value;

        if (dis >= K) {
            return query(prev.left, using.left, left, mid, K);
        } else {
            return query(prev.right, using.right, mid+1, right, K - dis);
        }
    }

    private Node update (Node node, int left, int right, int value) {
        if (right < leftIdx || rightIdx < left) return node;

        if (leftIdx <= left && right <= rightIdx) {
            return new Node(value);
        }

        int mid = (left + right) / 2;

        Node left_leaf = update(node.left, left, mid, value);
        Node right_leaf = update(node.right, mid+1, right, value);
        return new Node(left_leaf, right_leaf, left_leaf.value+ right_leaf.value);
    }
}

//Persistent Segment Tree Solved

public class Main {
    public static HashMap<Integer, Integer> encode = new HashMap<>();
    public static int K;
    public static int[] ordered;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int queries = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        int[] arr = new int[N];
        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        ordered = arr.clone();
        Arrays.sort(ordered);

        for (int i=0;i< ordered.length;i++) {
            encode.put(ordered[i], i);
        }

        SegP tree = new SegP(arr);

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            K = Integer.parseInt(st.nextToken());

            bw.write(tree.find(left, right, K)+"\n");
        }
        bw.flush();
    }
}
