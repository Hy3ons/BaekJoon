import java.io.*;
import java.util.*;

class Go {
    int now, prev;
    Go(int now, int prev) {
        this.now = now;
        this.prev = prev;
    }
}

class Segment {
    public static int[] nodesIdx;
    public static int result;
    private long[] tree, lazy, lazy2;
    private int[] time, re_time;
    private boolean[] something;
    private int start = 1, leftIdx, rightIdx, size, value;
    Segment(ArrayList<Integer> al) {
        while (start < al.size()) start *= 2;

        tree = new long[start * 2];
        lazy = new long[start * 2];
        lazy2 = new long[start * 2];
        re_time = new int[start * 2];
        time = new int[start * 2];
        something = new boolean[start * 2];

        for (int i = 0; i < al.size(); i++)
            nodesIdx[al.get(i)] = i;

        rightIdx = size = al.size();
    }

    private void update (int node, int left, int right) {
        if (something[node]) propagate(node, left, right);

        if (right < leftIdx || rightIdx < left) return;

        int mid = (left + right) / 2;

        if (leftIdx <= left && right <= rightIdx) {
            lazy[node] += value;
            time[node]++;
            value += right - left + 1;
            something[node] = true;
            propagate(node, left, right);
            return;
        }

        update(node*2, left, mid);
        update(node*2+1, mid+1, right);

        tree[node] = tree[node*2] + tree[node*2+1];
    }

    private void reUpdate (int node, int left, int right) {
        if (something[node]) propagate(node, left, right);

        if (right < leftIdx || rightIdx < left) return;

        int mid = (left + right) / 2;

        if (leftIdx <= left && right <= rightIdx) {
            lazy2[node] += value;
            re_time[node]++;
            value += right - left + 1;
            something[node] = true;
            propagate(node, left, right);
            return;
        }

        reUpdate(node*2+1, mid+1, right);
        reUpdate(node*2, left, mid);

        tree[node] = tree[node*2] + tree[node*2+1];
    }

    public int nextTree(int node, int value) {
        leftIdx = 1 + nodesIdx[node];
        rightIdx = size;
        this.value = value;
        update(1, 1, start);

        int temp = rightIdx - leftIdx + 1;
        return value + temp;
    }
    public int reNextTree(int node, int value) {
        leftIdx = 1 + nodesIdx[node];
        rightIdx = size;
        this.value = value;
        reUpdate(1, 1, start);

        int temp = rightIdx - leftIdx + 1;
        return value + temp;
    }

    public long find(int left, int right) {
        leftIdx = 1 + nodesIdx[left];
        rightIdx = 1 + nodesIdx[right];

        return query(1, 1, start);
    }

    public int finals (int left, int right, int value) {
        leftIdx = Math.min(nodesIdx[left],nodesIdx[right]) + 1;
        rightIdx = Math.max(nodesIdx[left],nodesIdx[right]) + 1;

        this.value = value;
        update(1, 1, start);
        return this.value;
    }
    public int reFinals (int left, int right, int value) {
        leftIdx = Math.min(left, right);
        rightIdx = Math.max(left, right);

        this.value = value;
        reUpdate(1, 1, start);
        return this.value;
    }

    public void propagate (int node, int left, int right) {
        long N = right - left + 1;

        if (node >= start){
            tree[node] += ((lazy[node] * 2 + time[node] * (N - 1)) / 2 ) * N;
            tree[node] += ((lazy2[node] * 2 + re_time[node] * (N - 1)) / 2 ) * N;
        }

        if (node >= start) {
            lazy[node] = lazy2[node] = time[node] = re_time[node] = 0;
            return;
        }

        int mid = (left + right) / 2;

        lazy[node*2] += lazy[node];
        lazy[node*2+1] += lazy[node] + (long) time[node] * (mid - left + 1);

        lazy2[node*2] += lazy2[node] + (long) re_time[node] * (right - mid);
        lazy2[node*2+1] += lazy2[node];

        re_time[node*2] += re_time[node];
        re_time[node*2+1] += re_time[node];
        time[node*2] += time[node];
        time[node*2+1] += time[node];

        something[node*2] = something[node*2+1] = true;
        lazy[node] = lazy2[node] = time[node] = re_time[node] = 0;
    }

    public long query(int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (something[node]) propagate(node, left, right);

        int mid = (left + right) / 2;
        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        return query(node * 2, left, mid) + query(node * 2 + 1, mid + 1, right);
    }
}

public class Main {
    public static ArrayList<ArrayList<Integer>> load = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int size = Integer.parseInt(br.readLine());

        for (int i=0;i<size+1;i++) load.add(new ArrayList<>());
        Segment.nodesIdx = new int[size+1];

        for (int i=1;i<size;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken())
                    , n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        Queue<Go> qu = new LinkedList<>();
        Stack<Integer> starts = new Stack<>();
        int[] level = new int[size+1]
                ,previous = new int[size+1];
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

        while(N-->0) {
            st = new StringTokenizer(br.readLine());

            int method = Integer.parseInt(st.nextToken());
            if (method == 1) {
                int from = Integer.parseInt(st.nextToken());
                int goal = Integer.parseInt(st.nextToken());

                int n1 = from;
                int n2 = goal;

                if (chainDepth[myChain[n1]] > chainDepth[myChain[n2]]) {
                    int temp = n1;
                    n1 = n2;
                    n2 = temp;
                }

                while(chainDepth[myChain[n1]] != chainDepth[myChain[n2]]) {
                    n2 = nextChain[myChain[n2]];
                }

                while(myChain[n1] != myChain[n2]) {
                    if (chainDepth[myChain[n1]] != 0)
                        n1 = nextChain[myChain[n1]];
                    if (chainDepth[myChain[n2]] != 0)
                        n2 = nextChain[myChain[n2]];
                }

                lca = level[n1] > level[n2] ? n2 : n1;

                int mul = 0;

                while(chainDepth[myChain[from]] != chainDepth[myChain[lca]]) {
                    mul = chaines[myChain[from]].nextTree(from, mul);
                    from = nextChain[myChain[from]];
                }

                mul = chaines[myChain[from]].finals(from, lca, mul);
                if (lca == goal) continue;

                dfs(lca, goal, mul);

//                for (int i=1;i<=size;i++) {
//                    System.out.println(i+" : "+chaines[myChain[i]].find(i, i));
//                }
//                System.out.println();
            } else {
                int goal = Integer.parseInt(st.nextToken());
                bw.write(chaines[myChain[goal]].find(goal, goal)+"\n");
            }
        }
        bw.flush();

    }
    public static int[] myChain, nextChain, chainDepth;
    public static Segment[] chaines;
    public static int lca;

    public static int dfs (int node2, int node, int mul) {
        if (chainDepth[myChain[node]] != chainDepth[myChain[lca]]) {
            mul = dfs(lca, nextChain[myChain[node]], mul);
            return chaines[myChain[node]].reNextTree(node, mul);
        }


        if (lca == node) return mul;
        return chaines[myChain[node]].reFinals(Segment.nodesIdx[lca], Segment.nodesIdx[node] + 1, mul);
    }

}