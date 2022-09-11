import java.io.*;
import java.util.*;

class Go {
    int now, prev;
    Go(int now, int prev) {
        this.now = now;
        this.prev = prev;
    }
}

class Node {
    long max, left, right, sum, lazy;
    boolean isLazy;
    public static int MIN = -2000000000;

    Node () {
        clear();
    }
    public void set(long value) {
        max = left = right = sum = value;
        max = Math.max(max, 0);
    }
    public void clear () {
        max = left = right = sum = 0;
    }

    public Node reversed () {
        Node n = new Node();
        n.max = max;
        n.left = right;
        n.right = left;
        n.sum = sum;
        return n;
    }
    public long getMax () {
        return Math.max(max, Math.max(left, Math.max(right, sum)));
    }
}

class Segment {
    public static int[] nodesIdx;
    public static int result;
    private Node[] tree;
    private int start = 1, leftIdx, rightIdx, size, value;
    Segment(ArrayList<Integer> al) {
        while (start < al.size()) start *= 2;

        tree = new Node[start *2];

        for (int i=0;i<tree.length;i++) {
            tree[i] = new Node();
        }

        for (int i = 0; i < al.size(); i++) {
            nodesIdx[al.get(i)] = i;
            tree[start + i].set(Main.arr[al.get(i)]);
        }

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                making(tree[j], tree[j*2], tree[j*2+1]);
            }
        }

        rightIdx = size = al.size();
    }

    public static void making (Node result, Node L, Node R) {
        result.right = Math.max(R.right, R.sum + L.right);
        result.left = Math.max(L.left, L.sum + R.left);
        result.sum = L.sum + R.sum;
        result.max = max(L.max, R.max, L.right + R.left);
    }

    public static long max (long a, long b, long c) {
        return Math.max(a, Math.max(b,c ));
    }


    private void update (int node, int left, int right) {
        if (tree[node].isLazy) propagate(node, left, right);

        if (right < leftIdx || rightIdx < left) return;

        int mid = (left + right) / 2;
        if (leftIdx <= left && right <= rightIdx) {
            tree[node].isLazy = true;
            tree[node].lazy = value;
            propagate(node, left, right);
            return;
        }

        update(node*2, left, mid);
        update(node*2+1, mid+1, right);

        making(tree[node], tree[node*2], tree[node*2+1]);
    }

    public void uNext (int node, int value) {
        leftIdx = 1 + nodesIdx[node];
        rightIdx = size;
        this.value = value;
        update(1, 1, start);
    }
    private void setIdx (int a, int b) {
        leftIdx = Math.min(nodesIdx[a],nodesIdx[b]) + 1;
        rightIdx = Math.max(nodesIdx[a],nodesIdx[b]) + 1;
    }
    public void inFind(int left, int right) {
        setIdx(left, right);

        Main.answers.add(query(1, 1, start));
    }
    public void uFind (int node) {
        leftIdx = 1 + nodesIdx[node];
        rightIdx = size;

        Main.answers.add(query(1, 1, start));
    }
    public void reInFind(int left, int right) {
        setIdx(left, right);

        Main.answers.add(query(1, 1, start).reversed());
    }
    public void reFunc (int idx, int idx2) {
        leftIdx = Math.min(idx, idx2);
        rightIdx = Math.max(idx, idx2);

        Main.answers.add(query(1, 1, start).reversed());
    }
    public void reUFind (int node) {
        leftIdx = 1 + nodesIdx[node];
        rightIdx = size;

        Main.answers.add(query(1, 1, start).reversed());
    }

    public void inUpdate (int left, int right, int value) {
        setIdx(left, right);

        this.value = value;
        update(1, 1, start);
    }
    private void propagate (int node, int left, int right) {
        tree[node].isLazy = false;

        if (tree[node].lazy <= 0) {
            tree[node].set(tree[node].lazy);
            tree[node].sum = tree[node].lazy * (right - left + 1);
        } else {
            tree[node].set(tree[node].lazy * (right - left + 1));
        }

        if (node >= start) return;

        tree[node*2].lazy = tree[node*2+1].lazy = tree[node].lazy;
        tree[node*2].isLazy = tree[node*2+1].isLazy = true;
    }

    private Node query(int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return new Node();

        if (tree[node].isLazy) propagate(node, left, right);

        int mid = (left + right) / 2;
        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        Node L = query(node * 2, left, mid);
        Node R = query(node * 2 + 1, mid + 1, right);
        Node result = new Node();
        making(result, L , R);
        return result;
    }
}

public class Main {
    public static ArrayList<ArrayList<Integer>> load = new ArrayList<>();
    public static ArrayList<Node> answers = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int size = Integer.parseInt(br.readLine());
        Segment.nodesIdx = new int[size+1];

        for (int i=0;i<size+1;i++) load.add(new ArrayList<>());

        arr = new int[size+1];
        st = new StringTokenizer(br.readLine());

        for (int i=1;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        for (int i=1;i<size;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken())
                    , n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        Queue<Go> qu = new LinkedList<>();
        Stack<Integer> starts = new Stack<>();
        level = new int[size+1];
        myChain = new int[size+1];

        qu.offer(new Go(1, 0));

        while(!qu.isEmpty()) {
            Go now = qu.poll();
            boolean beFirst = true;

            for (int go : load.get(now.now)) {
                if (go == now.prev) continue;

                level[go] = level[now.now] + 1;
                qu.offer(new Go(go, now.now));
                beFirst = false;
            }
            if (beFirst) starts.push(now.now);
        }

        int nowChain = -1;
        chaines = new Segment[starts.size()];
        nextChain = new int[starts.size()];
        chainDepth = new int[starts.size()];
        Arrays.fill(myChain, -1);

        while(!starts.isEmpty()) {
            nowChain++;
            int start = starts.pop();
            qu.offer(new Go(start, 0));
            myChain[start] = nowChain;
            ArrayList<Integer> elements = new ArrayList<>();

            while(!qu.isEmpty()) {
                Go now = qu.poll();
                elements.add(now.now);

                for (int go : load.get(now.now)) {
                    if (level[go] == level[now.now] - 1) {
                        if (myChain[go] == -1) {
                            qu.offer(new Go(go, 0));
                            myChain[go] = nowChain;
                        } else {
                            nextChain[nowChain] = go;
                            chainDepth[nowChain] = chainDepth[myChain[go]] + 1;
                        }
                        break;
                    }
                }
            }

            chaines[nowChain] = new Segment(elements);
        }

        int N = Integer.parseInt(br.readLine());
        ArrayList<Node> temp = new ArrayList<>();

        while(N-->0) {
            st = new StringTokenizer(br.readLine());

            int method = Integer.parseInt(st.nextToken());
            int from = Integer.parseInt(st.nextToken());
            int goal = Integer.parseInt(st.nextToken());

            int lca = findLCA(from, goal);

            if (method == 1) {
                int n1 = from;
                int n2 = goal;
                answers.clear();

                while(myChain[n1] != myChain[lca]) {
                    chaines[myChain[n1]].uFind(n1);
                    n1 = nextChain[myChain[n1]];
                }

                while(myChain[n2] != myChain[lca]) {
                    n2 = nextChain[myChain[n2]];
                }

                if (level[n1] >= level[n2]) {
                    chaines[myChain[lca]].inFind(n1, n2);
                } else {
                    chaines[myChain[lca]].reInFind(n1, n2);
                }

                if (myChain[goal] != myChain[lca])
                    dfs(goal, lca);

//                if (answers.size() % 2 == 1) {
//                    answers.add(new Node());
//                }

                while(answers.size()!=1) {
                    temp.clear();
                    for (int i=0;i+1< answers.size();i+=2) {
                        Node n = new Node();
                        temp.add(n);
                        Segment.making(n, answers.get(i), answers.get(i+1));
                    }
                    if (answers.size() % 2 == 1)
                        temp.add(answers.get(answers.size() - 1));

                    ArrayList<Node> a = answers;
                    answers = temp;
                    temp = a;
                }
                bw.write(answers.get(0).getMax()+"\n");


            } else {
                int value = Integer.parseInt(st.nextToken());

                while (myChain[goal] != myChain[lca]) {
                    chaines[myChain[goal]].uNext(goal, value);
                    goal = nextChain[myChain[goal]];
                }
                while (myChain[from] != myChain[lca]) {
                    chaines[myChain[from]].uNext(from, value);
                    from = nextChain[myChain[from]];
                }

                chaines[myChain[lca]].inUpdate(goal, from, value);
            }
        }
        bw.flush();

    }
    public static int[] myChain, nextChain, chainDepth, level, arr;
    public static Segment[] chaines;

    public static void dfs(int node, int lca) {
        if (myChain[node] != myChain[lca]) {
            dfs(nextChain[myChain[node]], lca);
        } else return;

        chaines[myChain[node]].reUFind(node);
    }

    public static int findLCA (int n1, int n2) {
        if (chainDepth[myChain[n1]] > chainDepth[myChain[n2]]) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        while(chainDepth[myChain[n1]] != chainDepth[myChain[n2]]) {
            n2 = nextChain[myChain[n2]];
        }

        while(myChain[n1] != myChain[n2]) {
            n1 = nextChain[myChain[n1]];
            n2 = nextChain[myChain[n2]];
        }

        return level[n1] > level[n2] ? n2 : n1;
    }

}