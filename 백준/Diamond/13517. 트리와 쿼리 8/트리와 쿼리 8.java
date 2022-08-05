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
    private ArrayList<Node> starts = new ArrayList<>();
    private int size, start = 1, leftIdx, rightIdx;

    SegP() {
        size = 1000000;
        while (start < size) start *= 2;

        Node[] nodes = new Node[start * 2];
        for (int i=0;i<nodes.length;i++) nodes[i] = new Node(0);

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                nodes[j].left = nodes[j*2];
                nodes[j].right = nodes[j*2+1];
            }
        }
        starts.add(nodes[1]);
    }

    public int sUpdate (int idx, int value, int prev) {
        leftIdx = rightIdx = idx;
        starts.add(update(starts.get(Main.myTree[prev]), 1, start, value));
        return starts.size()-1;
    }

    public int find (int n1, int lca, int n2, int K) {
        return query(starts.get(Main.myTree[n1]), starts.get(Main.myTree[n2]), starts.get(Main.myTree[lca])
                , starts.get(Main.myTree[Main.dp[0][lca]]), 1, start, K);
    }


    private int query (Node n1, Node n2, Node lca, Node lca2, int left, int right, int K) {
        if (right == left) return right;

        int mid = (left + right)/2;
        int dis = n1.left.value + n2.left.value - lca.left.value - lca2.left.value;

        if (dis >= K) {
            return query(n1.left, n2.left, lca.left, lca2.left, left, mid, K);
        } else {
            return query(n1.right, n2.right, lca.right, lca2.right, mid+1, right, K - dis);
        }
    }

    private Node update (Node node, int left, int right, int value) {
        if (right < leftIdx || rightIdx < left) return node;

        if (leftIdx <= left && right <= rightIdx) {
            return new Node(value + node.value);
        }

        int mid = (left + right) / 2;

        Node left_leaf = update(node.left, left, mid, value);
        Node right_leaf = update(node.right, mid+1, right, value);
        return new Node(left_leaf, right_leaf, left_leaf.value+ right_leaf.value);
    }
}

public class Main {
    public static int K;
    public static ArrayList<ArrayList<Integer>> load;
    public static SegP tree = new SegP();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        arr = new int[N];
        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        load = new ArrayList<>(N+1);
        for (int i=0;i<=N;i++) load.add(new ArrayList<>());

        for (int i=1;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        dp = new int[30][N+1];
        myTree = new int[N+1];
        level = new int[N + 1];

        setting(1, 0);
        for (int i=1;i<dp.length;i++) {
            for (int j=1;j<=N;j++) {
                dp[i][j] = dp[i-1][dp[i-1][j]];
            }
        }

        int queries = Integer.parseInt(br.readLine());

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int G = Integer.parseInt(st.nextToken());

            K = Integer.parseInt(st.nextToken());

            bw.write(tree.find(S, findLCA(S, G), G, K) + "\n");
        }
        bw.flush();
    }

    public static int[] myTree, arr, level;
    public static int[][] dp;
    public static void setting (int now, int prev) {
        myTree[now] = tree.sUpdate(arr[now - 1], 1, prev);
        level[now] = level[prev] + 1;
        dp[0][now] = prev;
        for (int go : load.get(now)) {
            if (go != prev) {
                setting(go, now);
            }
        }
    }

    public static int findLCA (int n1, int n2) {
        if (level[n1] > level[n2]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        int dist = level[n2] - level[n1];

        for (int i=29;i>=0;i--) {
            if ((dist & 1<<i)!=0) {
                n2 = dp[i][n2];
            }
        }

        for (int i=29;i>=0;i--) {
            if (dp[i][n1] != dp[i][n2]) {
                n1 = dp[i][n1];
                n2 = dp[i][n2];
            }
        }

        return n1 != n2 ? dp[0][n1] : n1;
    }

}
