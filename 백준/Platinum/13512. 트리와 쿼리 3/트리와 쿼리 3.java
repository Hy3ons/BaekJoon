import java.io.*;
import java.util.*;

class Go {
    int now, prev;
    Go (int now, int prev) {
        this.now = now;
        this.prev = prev;
    }
}

class Segment {
    public static int[] nodesIdx;
    public static int result;
    private int[] tree;
    private int start = 1, leftIdx, rightIdx;
    private ArrayList<Integer> al;
    Segment(ArrayList<Integer> al) {
        while (start < al.size()) start *= 2;
        this.al = al;

        tree = new int[start*2];

        for (int i=0;i<al.size();i++) {
            nodesIdx[al.get(i)] = i;
        }
        rightIdx = al.size();
    }

    public void update (int node) {
        int idx = start + nodesIdx[node];
        tree[idx] ^= 1;
        idx/= 2;

        while(idx!=0) {
            tree[idx] = tree[idx*2] | tree[idx*2+1];
            idx /= 2;
        }
    }

    public boolean find (int value) {
        if (tree[1] == 0) return false;

        leftIdx = nodesIdx[value] + 1;
        return query(1, 1, start);
    }

    public boolean query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return false;

        int mid = (left + right) / 2;
        if (leftIdx <= left && right <= rightIdx) {
            if (node >= start) {
                Segment.result = al.get(right-1);
                return true;
            }

            if (tree[node*2+1] == 1) {
                query(node*2+1, mid+1, right);
            } else {
                query(node*2, left, mid);
            }
            return true;
        }



        if (tree[node*2+1] == 1 && query(node*2+1, mid+1, right)) return true;
        if (tree[node*2] == 1 && query(node*2, left, mid)) return true;
        return false;
    }
}

public class Main {
    public static ArrayList<ArrayList<Integer>> load = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int size = Integer.parseInt(br.readLine());
        Segment.nodesIdx = new int[size+1];
        for (int i=0;i<=size;i++) load.add(new ArrayList<>());

        for (int i=1;i<size;i++) {
            st = new StringTokenizer(br.readLine());

            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        Queue<Go> qu = new LinkedList<>();
        int[] myChain = new int[size+1]
                , level = new int[size+1];

        qu.offer(new Go(1,0));
        Stack<Integer> firsts = new Stack<>();
        while(!qu.isEmpty()) {
            Go now = qu.poll();
            boolean beFirst = true;

            for (int go : load.get(now.now)) {
                if (go == now.prev) continue;

                level[go] = level[now.now] + 1;
                qu.offer(new Go(go, now.now));
                beFirst = false;
            }
            if (beFirst) firsts.push(now.now);
        }

        Arrays.fill(myChain, -1);
        int nowChain = -1;
        Segment[] chaines = new Segment[firsts.size()];
        int[] nextChain = new int[firsts.size()];

        while(!firsts.isEmpty()) {
            nowChain++;
            int first = firsts.pop();
            myChain[first] = nowChain;
            qu.offer(new Go(first, 0));

            ArrayList<Integer> al = new ArrayList<>();

            while(!qu.isEmpty()) {
                Go now = qu.poll();
                al.add(now.now);

                for (int go : load.get(now.now)) {
                    if (level[go] == level[now.now] - 1) {
                        if (myChain[go] == -1) {
                            myChain[go] = myChain[now.now];
                            qu.offer(new Go(go, 0));
                        } else {
                            nextChain[nowChain] = go;
                        }
                        break;
                    }
                }
            }

            chaines[nowChain] = new Segment(al);
        }

        int queries = Integer.parseInt(br.readLine());

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int method = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());
            Segment.result = -1;

            if (method == 1) {
                chaines[myChain[value]].update(value);
            } else {
                while(value != 0) {
                    chaines[myChain[value]].find(value);
                    value = nextChain[myChain[value]];
                }

                bw.write(Segment.result+"\n");
            }
        }
        bw.flush();

    }
}