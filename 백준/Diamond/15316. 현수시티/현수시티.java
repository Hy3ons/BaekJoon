import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

class Pair {
    int n1, n2;
    Pair(int n1, int n2) {
        this.n1 = Math.min(n1, n2);
        this.n2 = Math.max(n1, n2);
    }
}

class Seg {
    private ArrayList<Pair>[] tree;
    public int start = 1, MAX = 200002;
    public BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private int[] parent = new int[MAX], rank = new int[MAX];
    private Stack<tuple> st = new Stack<>();

    class tuple {
        int x, y, z;
        tuple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z= z;
        }
    }

    Seg() {
        while (start < MAX) start <<= 1;
        tree = new ArrayList[start << 1];
        for (int i=0;i<tree.length;i++) tree[i] = new ArrayList<>();

        for (int i=0;i<parent.length;i++) parent[i]  = i;
    }

    public void update (int node, int left, int right, int leftIdx, int rightIdx, Pair c) {
        if (right < leftIdx || rightIdx < left) return;

        if (leftIdx <= left && right <= rightIdx) {
            tree[node].add(c);
            return;
        }

        int mid = left + right >> 1;
        update(node*2, left, mid, leftIdx, rightIdx, c);
        update(node*2+1, mid+1, right, leftIdx, rightIdx, c);
    }

    public void answer (int node, int left, int right, int end) throws IOException{
        if (right < 1 || end < left) return;

        int s = 0;
        for (Pair q : tree[node]) {
            s += union(q.n1, q.n2);
        }

        if (left == right) {
            Pair p = Main.answer.get(left);
            bw.write((findParent(p.n1) == findParent(p.n2) ? "YES" : "NO")+"\n");
            cut(s);
            return;
        }

        int mid = left + right >> 1;
        answer(node*2, left, mid, end);
        answer(node*2+1, mid+1, right, end);
        cut(s);
    }

    private void cut (int time) {
        while(time-->0) {
            tuple t = st.pop();
            parent[t.x] = t.x;
            rank[t.y] -= t.z;
        }
    }

    private int union (int n1, int n2) {
        n1 = findParent(n1);
        n2 = findParent(n2);

        if (n1 == n2) return 0;

        if (rank[n1] > rank[n2]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        parent[n1] = n2;
        if (rank[n1] == rank[n2]) {
            rank[n2]++;
            st.push(new tuple(n1, n2, 1));
        } else {
            st.push(new tuple(n1, n2, 0));
        }
        return 1;
    }

    private int findParent(int node) {
        if (parent[node] == node) return node;
        return findParent(parent[node]);
    }
}

public class Main {
    public static ArrayList<Pair> answer = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        answer.add(new Pair(0, 0));

        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        boolean[] using = new boolean[M];

        HashMap<Integer, Integer> hm = new HashMap<>();
        Seg tree = new Seg();

        Pair[] loads = new Pair[M];
        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            loads[i] = new Pair(n1, n2);
        }

        st = new StringTokenizer(br.readLine());
        for (int i=0;i<M;i++) {
            using[i] = st.nextToken().equals("1");
            if (using[i])
                hm.put(i, answer.size());
        }
        int A = Integer.parseInt(br.readLine());
        for (int i=0;i<A;i++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());

            if (m == 2) {
                int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
                answer.add(new Pair(n1, n2));
                continue;
            }

            int n = Integer.parseInt(st.nextToken()) - 1;
            using[n] = !using[n];

            if (using[n]) {
                hm.put(n, answer.size());
            } else {
                int l = hm.get(n), r = answer.size();
                hm.remove(n);

                if (l == r) continue;
                tree.update(1, 1, tree.start, l, r-1, loads[n]);
            }
        }

        ArrayList<Integer> al = new ArrayList<>(hm.keySet());
        for (int s : al) {
            tree.update(1, 1, tree.start, hm.get(s), answer.size()-1, loads[s]);
        }

        tree.answer(1, 1, tree.start, answer.size()-1);
        tree.bw.flush();
    }
}
