import java.io.*;
import java.util.*;
import java.util.logging.XMLFormatter;

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
    private long[] tree, amount;
    private int start = 1, leftIdx, rightIdx, N, value;
    Segment(ArrayList<Integer> al) {
        while (start < al.size()) start *= 2;

        tree = new long[start << 1];
        amount = new long[start << 1];

        for (int i = 0; i < al.size();i++) {
            nodesIdx[al.get(i)] = i;
        }

        rightIdx = N = al.size();
    }

    public long getAllAmount () {
        return amount[1];
    }

    public long getAllLevel () {
        return tree[1];
    }

    public void update (int node, int d, int ori) {
        int idx = start + nodesIdx[node];
        amount[idx] += d;
        tree[idx] = amount[idx] * Main.level[node];

        while((idx >>= 1) != 0) {
            tree[idx] = tree[idx*2] + tree[idx*2+1];
            amount[idx] = amount[idx*2] + amount[idx*2+1];
        }

    }

    public long getLevel (int node) {
        leftIdx = nodesIdx[node] + 2;
        rightIdx = N;
        return level(1, 1, start);
    }

    public long getAmount (int node) {
        leftIdx = 1;
        rightIdx = nodesIdx[node] + 1;

        return amount(1, 1, start);
    }

    private long amount (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (leftIdx <= left && right <= rightIdx) return amount[node];

        int mid = left + right >> 1;

        return amount(node*2, left, mid) + amount(node*2+1, mid+1, right);
    }

    private long level (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (leftIdx <= left && right <= rightIdx) return tree[node];

        int mid = left + right >> 1;

        return level(node*2, left, mid) + level(node*2+1, mid+1, right);
    }


}

public class Main {
    public static ArrayList<ArrayList<Integer>> load = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()), queries = Integer.parseInt(st.nextToken());
        Segment.nodesIdx = new int[N+1];

        for (int i=0;i<N+1;i++) load.add(new ArrayList<>());

        arr = new int[N+1];
        st = new StringTokenizer(br.readLine());

        for (int i=1;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i=2;i<=N;i++) {
            int n1 = Integer.parseInt(st.nextToken());

            load.get(n1).add(i);
            load.get(i).add(n1);
        }

        Queue<Go> qu = new LinkedList<>();
        Stack<Integer> starts = new Stack<>();
        level = new int[N+1];
        myChain = new int[N+1];

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

        for (int i=1;i<=N;i++) if (arr[i] == 1) {
            answer(i, 1);
            update(i, 1);
        }
        bw.write(result+"\n");


        while(queries-->0) {
            int x = Integer.parseInt(br.readLine());
            if (arr[x] == 1) {
                arr[x] = 0;
                update(x, -1);
                answer(x, -1);
            } else {
                arr[x] = 1;
                answer(x, 1);
                update(x, 1);
            }
            bw.write(result+"\n");
        }

        bw.flush();

    }
    public static int[] myChain, nextChain, chainDepth, level, arr;
    public static Segment[] chaines;
    public static long result;

    public static void answer (int node, int d) {
        long prevA = 0;
        while(node != 0) {
            long a = chaines[myChain[node]].getAmount(node);

            result += (a - prevA) * level[node] * d;
            result += chaines[myChain[node]].getLevel(node) * d;
            prevA = chaines[myChain[node]].getAllAmount();
            node = nextChain[myChain[node]];
        }
    }

    public static void update(int node, int d) {
        int temp = node;
        while(temp != 0) {
            chaines[myChain[temp]].update(temp, d, node);
            temp = nextChain[myChain[temp]];
        }
    }

}