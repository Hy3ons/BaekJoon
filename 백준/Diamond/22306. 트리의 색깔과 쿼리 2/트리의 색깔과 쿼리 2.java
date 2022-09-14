import java.io.*;
import java.util.*;

class SizeHLD {
    class Pair {
        int x, y;
        Pair (int x, int y){
            this.x =x ;
            this.y= y;
        }
    }

    class Seg {
        int start = 1, end;
        private int[] tree;

        Seg(ArrayList<Integer> al) {
            while(start < al.size()) start <<= 1;
            tree = new int[start << 1];

            for (int i=0;i<al.size();i++) {
                nodesidx[al.get(i)] = i+1;
            }

            end = al.size();
        }

        public void update(int node, int s, int e, int left, int right, int value) {
            if (e < left || right < s) return;

            if (s <= left && right <= e) {
                tree[node] +=value;
                return;
            }
            if (tree[node] != 0) propagate(node);
            int mid = left + right >> 1;
            update(node*2, s, e, left, mid, value);
            update(node*2+1, s, e, mid+1, right, value);
        }

        private void propagate (int node) {
            if (node >= start) return;

            try {
                tree[node*2] += tree[node];
                tree[node*2+1] += tree[node];
                tree[node] = 0;
            } catch (Exception ignored){
            }
        }

        public int query(int node, int s, int e, int left, int right) {
            if (e < left || right < s) return 0;

            if (s <= left && right <= e) {
                return tree[node];
            }
            if (tree[node] != 0) propagate(node);
            int mid = left + right >> 1;
            return query(node*2, s, e, left, mid) + query(node*2+1, s, e, mid+1, right);
        }

    }
    private int[] nextchain, mychain, level;
    private Seg[] chaines;
    private static int[] nodesidx = new int[100001];

    SizeHLD () {
        Queue<Pair> qu = new LinkedList<>();
        Stack<Integer> st = new Stack<>();

        qu.offer(new Pair(1,0));

        while(!qu.isEmpty()) {
            Pair now = qu.poll();
            boolean c = true;

            for (int go : Main.load.get(now.x)) {
                if (go != now.y) {
                    qu.offer(new Pair(go, now.x));
                    c = false;
                }
            }

            if (c) {
                st.push(now.x);
            }
        }

        nextchain = new int[st.size()];
        mychain = new int[100001];
        chaines = new Seg[st.size()];
        level = new int[st.size()];
        Arrays.fill(mychain, -1);

        int cha = 0;

        while(!st.isEmpty()) {
            int n = st.pop();
            mychain[n] = cha;
            ArrayList<Integer> al = new ArrayList<>();

            while(true) {
                al.add(n);
                if (Main.up[n] == 0) break;
                if (mychain[Main.up[n]] == -1) {
                    mychain[Main.up[n]] = cha;
                    n = Main.up[n];
                } else {
                    nextchain[cha] = Main.up[n];
                    level[cha] = level[mychain[Main.up[n]]] + 1;
                    break;
                }
            }

            chaines[cha] = new Seg(al);
            cha++;
        }

    }

    public int getSize(int node) {
        return chaines[mychain[node]].query(1, nodesidx[node], nodesidx[node], 1, chaines[mychain[node]].start);
    }

    public void update(int top, int bottom, int size) {
        while(mychain[top] != mychain[bottom]) {
            chaines[mychain[bottom]].update(1, nodesidx[bottom], chaines[mychain[bottom]].end, 1, chaines[mychain[bottom]].start, size);
            bottom = nextchain[mychain[bottom]];
        }

        Seg c = chaines[mychain[top]];

        c.update(1, Math.min(nodesidx[bottom], nodesidx[top]), Math.max(nodesidx[bottom], nodesidx[top]), 1, c.start, size);
    }

}

public class Main {
    public static int N, k, cnt;
    public static int[] up, down, color, size, parent, leader = new int[100001];

    public static ArrayList<ArrayList<Integer>> load;
    public static HashMap<Integer, Integer>[] hashMaps = new HashMap[100001];
    public static boolean[] cut = new boolean[100001];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        if (N==1) {
            for (int i=0;i<k;i++) {
                bw.write("1\n");
            }
            bw.flush();
            return;
        }

        up = new int[N+1];
        color = new int[N+1];
        size = new int[N+1];
        down = new int[N+1];
        parent = new int[N+1];

        load = new ArrayList<>(N+1);
        for (int i=0;i<=N;i++) {
            load.add(new ArrayList<>());
        }
        st = new StringTokenizer(br.readLine());

        for (int i=2;i<=N;i++) {
            up[i] = Integer.parseInt(st.nextToken());

            load.get(up[i]).add(i);
            load.get(i).add(up[i]);
        }
        st = new StringTokenizer(br.readLine());
        for (int i=1;i<=N;i++) color[i] = Integer.parseInt(st.nextToken());

        getSize(1, 0);
        search(1, 0, (hashMaps[1] = new HashMap<>()), null, 1);

        SizeHLD sizes = new SizeHLD();
        leader[1] = 1;

        k += N-1;
        int last = 0;
        while(k-->0) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken()) ^ last;

            if (a == 1) {
                int original_parent = leader[parent[b]];
                int sub_size = size[b] - sizes.getSize(b);
                int compare = size[original_parent] - sizes.getSize(original_parent) -sub_size;
                sizes.update(original_parent, up[b], sub_size);

                cut[b] = true;

                if (compare >= sub_size) {
                    leader[b] = b;
                    search(b, up[b], hashMaps[b] = new HashMap<>(), hashMaps[parent[original_parent]], b);
                } else {
                    leader[parent[b]] = b;
                    leader[b] = original_parent;
                    search(original_parent, up[original_parent], hashMaps[b] = new HashMap<>(), hashMaps[parent[original_parent]], b);
                }
            } else {
                bw.write((last = hashMaps[parent[b]].size())+"\n");
            }

        }
        bw.flush();
    }

    public static void search(int node, int prev, HashMap<Integer, Integer> put, HashMap<Integer, Integer> out, int p) {
        parent[node] = p;
        if (put.containsKey(color[node])) {
            put.put(color[node], put.get(color[node]) + 1);
        } else {
            put.put(color[node], 1);
        }

        if (out != null && out.containsKey(color[node])) {
            out.put(color[node], out.get(color[node]) - 1);
            if (out.get(color[node]) == 0) out.remove(color[node]);
        }

        for(int go : load.get(node)) if (go != prev && !cut[go]) search(go, node, put, out, p);
    }
    public static int getSize(int node, int prev) {
        size[node] = 1;
        for (int go : load.get(node)) if (go != prev) size[node] += getSize(go, node);
        return size[node];
    }
}