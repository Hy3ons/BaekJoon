//https://lem0nad3.tistory.com/93 참조함. 너무 시간복잡도가 이해가 되지 않음.

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

class Pair {
    int n1, n2;
    Pair() {
        this(0,0);
    }
    Pair(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }
}

class Seg {
    private ArrayList<Pair>[] tree;
    public int start = 1;
    public BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private int[] parent = new int[100001], rank = new int[100001];
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
        while (start < 100001) start <<= 1;
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
            bw.write((findParent(p.n1) == findParent(p.n2) ? 1 : 0)+"\n");
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

        //리스트를 편하게 관리하기 위함.
        answer.add(new Pair());

        int N = Integer.parseInt(st.nextToken()), A = Integer.parseInt(st.nextToken());
        HashMap<String, Integer> hm = new HashMap<>();
        Seg tree = new Seg();

        for (int i=0;i<A;i++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken()), n1 = Integer.parseInt(st.nextToken())
                    , n2 = Integer.parseInt(st.nextToken());

            if (m == 3) {
                answer.add(new Pair(n1, n2));
                continue;
            }


            int nn1 = Math.min(n1, n2);
            int nn2 = Math.max(n1, n2);
            String s = nn1 + " " + nn2;
            if (hm.containsKey(s)) {
                int e = answer.size();
                int g = hm.get(s);
                hm.remove(s);

                if (g == e) continue;

                tree.update(1, 1, tree.start, g, e-1, new Pair(nn1, nn2));

            } else {
                hm.put(s, answer.size());
            }
        }

        ArrayList<String> al = new ArrayList<>(hm.keySet());
        for (String s : al) {
            st = new StringTokenizer(s);
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            int g = hm.get(s);
            tree.update(1, 1, tree.start, g, answer.size()-1, new Pair(n1, n2));
        }

        tree.answer(1, 1, tree.start, answer.size()-1);
        tree.bw.flush();
    }
}
