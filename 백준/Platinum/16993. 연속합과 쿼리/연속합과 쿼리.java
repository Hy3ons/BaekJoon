import java.io.*;
import java.util.StringTokenizer;

class Node {
    long max, left, right, sum;
    public static int MIN = -2000000000;

    Node (int value) {
        max = left = right = sum = value;
    }
    public void set(int value) {
        max = left = right = sum = value;
    }
}

class Segment {
    private Node[] tree;
    private int start =1 , leftIdx, rightIdx;
    public int result;

    Segment(int[] arr) {
        while (start < arr.length) start *= 2;

        tree = new Node[start * 2];
        for (int i=0;i<tree.length;i++) tree[i] = new Node(0);

        for (int i=0;i<arr.length;i++) {
            tree[i + start].set(arr[i]);
        }


        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                making(tree[j], tree[j*2], tree[j*2+1]);
            }
        }
    }
    public static long max (long a, long b, long c) {
        return Math.max(a, Math.max(b,c));
    }

    public Node find (int left, int right) {
        leftIdx = left;
        rightIdx = right;

        return query(1, 1, start);
    }
    private Node query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return new Node(Node.MIN);

        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        int mid = (left + right) / 2;

        Node L = query(node*2, left, mid);
        Node R = query(node*2+1,mid+1, right);
        Node result = new Node(0);
        making(result, L, R);
        return result;
    }
    public static void making (Node result, Node L, Node R) {
        result.right = Math.max(R.right, R.sum + L.right);
        result.left = Math.max(L.left, L.sum + R.left);
        result.sum = L.sum + R.sum;
        result.max = max(L.max, R.max, L.right + R.left);
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

            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            Node result = tree.find(left, right);
            bw.write(result.max+"\n");
        }
        bw.flush();

    }
}
