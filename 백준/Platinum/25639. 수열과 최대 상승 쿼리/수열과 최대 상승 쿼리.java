import java.io.*;
import java.util.StringTokenizer;

class Node {
    long max, min, w;
    Node (long x) {
        max = min = x;
        w = 0;
    }
    public static Node making (Node res, Node left, Node right) {
        res.w = Math.max(left.w, right.w);
        res.w = Math.max(res.w, right.max - left.min);
        res.max = Math.max(left.max, right.max);
        res.min = Math.min(left.min, right.min);
        return res;
    }
}

class Seg {
    int start = 1;
    private Node[] tree;
    Seg (long[] arr) {
        while(start < arr.length) start<<=1;

        tree = new Node[start << 1];
        for (int i=0;i<tree.length;i++) tree[i] =new Node(-1000000000000000L);
        for (int i=0;i<arr.length;i++) {
            tree[i + start] = new Node(arr[i]);
        }

        for (int i=start>>1;i!=0;i>>=1) {
            for (int j=i;j<i*2;j++) {
                Node.making(tree[j], tree[j*2], tree[j*2+1]);
            }
        }
    }

    public void update (int idx, long num) {
        int node = idx - 1 + start;
        tree[node] = new Node(num);
        while((node>>=1) != 0) {
            Node.making(tree[node], tree[node*2], tree[node*2+1]);
        }
    }

    public Node query (int node, int s, int e, int left, int right) {
        if (right < s) {
            return new Node(100000000000000000L);
        }
        if (e < left) {
            return new Node(-1000000000000000000L);
        }

        int mid = left + right >> 1;
        if (s <= left && right <= e) {
            return tree[node];
        }

        return Node.making(new Node(0), query(node*2, s, e, left, mid), query(node*2+1, s, e, mid+1, right));
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        long[] arr = new long[N];

        for (int i=0;i<arr.length;i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        int k = Integer.parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Seg tree = new Seg(arr);
        while(k-->0) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
            if (m == 1) {
                tree.update(a, b);
            } else {
                Node d = tree.query(1, a, b, 1, tree.start);
                bw.write(d.w+"\n");
            }
        }
        bw.flush();
    }
}