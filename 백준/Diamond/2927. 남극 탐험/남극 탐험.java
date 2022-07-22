import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.function.IntPredicate;

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
    private int start = 1, leftIdx, rightIdx, size;
    Segment(ArrayList<Integer> al) {
        while (start < al.size()) start *= 2;

        tree = new int[start*2];
        for (int i=0;i<al.size();i++) {
            nodesIdx[al.get(i)] = i;
        }
        rightIdx = al.size();
        size = al.size();
    }

    public void update (int node, int value) {
        int idx = start + nodesIdx[node];
        tree[idx] = value;
        idx/= 2;

        while(idx!=0) {
            tree[idx] = tree[idx*2] + tree[idx*2+1];
            idx /= 2;
        }
    }

    public int nextTree (int value) {
        leftIdx = 1 + nodesIdx[value];
        rightIdx = size;
        return query(1, 1, start);
    }
    public int find (int left, int right) {
        leftIdx = 1 + nodesIdx[left];
        rightIdx = 1 + nodesIdx[right];
        return query(1, 1, start);
    }

    public int query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        int mid = (left + right) / 2;
        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        return query(node*2, left, mid) + query(node*2+1, mid+1, right);
    }
}

class Query {
    String result = "";
    int value1, value2;
    int methodType;
    boolean shouldAnswer = true;

    Query (String[] input) {
        if (input[0].equals("penguins")) {
            shouldAnswer = false;
            methodType = 1;
        } else if (input[0].equals("bridge")) {
            methodType = 2;
        } else if (input[0].equals("excursion")) {
            methodType = 3;
        }

        value1 = Integer.parseInt(input[1]);
        value2 = Integer.parseInt(input[2]);
    }
}

public class Main {
    public static ArrayList<ArrayList<Integer>> load = new ArrayList<>();
    public static int[] parent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int size = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int query_Amount = Integer.parseInt(br.readLine());

        parent = new int[size+1];
        for(int i=0;i< parent.length;i++) parent[i] = i;

        int[] arr = new int[size+1];
        Query[] queries = new Query[query_Amount];
        for (int i=1;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        Segment.nodesIdx = new int[size+1];
        for (int i=0;i<=size;i++) load.add(new ArrayList<>());

        for (int i=0;i<queries.length;i++) {
            queries[i] = new Query(br.readLine().split(" "));

            if (queries[i].methodType == 2) {
                if (find(queries[i].value1) == find(queries[i].value2)) {
                    queries[i].shouldAnswer = false;
                    queries[i].result = "no\n";
                } else {
                    load.get(queries[i].value1).add(queries[i].value2);
                    load.get(queries[i].value2).add(queries[i].value1);

                    union(queries[i].value1, queries[i].value2);
                    queries[i].result = "yes\n";
                }
            } else if (queries[i].methodType == 3) {
                if (find(queries[i].value1) != find(queries[i].value2)) {
                    queries[i].shouldAnswer = false;
                    queries[i].result = "impossible\n";
                }
            }
        }

        Queue<Go> qu = new LinkedList<>();
        int[] myChain = new int[size+1]
                , level = new int[size+1];
        Stack<Integer> firsts = new Stack<>();

        for (int i=1;i<=size;i++) {
            if (level[i] == 0) {
                qu.offer(new Go(i,0));

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
            }
        }

        Arrays.fill(myChain, -1);
        int nowChain = -1;
        Segment[] chaines = new Segment[firsts.size()];
        int[] nextChain = new int[firsts.size()]
                ,chainDepth = new int[firsts.size()];

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
                            chainDepth[nowChain] = chainDepth[myChain[go]] + 1;
                        }
                        break;
                    }
                }
            }

            chaines[nowChain] = new Segment(al);
        }

        for (int i=1;i<arr.length;i++) {
            chaines[myChain[i]].update(i, arr[i]);
        }

        for (int i=0;i<queries.length;i++) {
            if (queries[i].methodType == 1) {
                chaines[myChain[queries[i].value1]].update(queries[i].value1, queries[i].value2);
            } else if (queries[i].methodType == 3 && queries[i].shouldAnswer) {
                int n1 = queries[i].value1;
                int n2 = queries[i].value2;

                long result = 0;

                if (chainDepth[myChain[n1]] > chainDepth[myChain[n2]]) {
                    int temp = n1;
                    n1 = n2;
                    n2 = temp;
                }

                while(chainDepth[myChain[n1]] != chainDepth[myChain[n2]]) {
                    result += chaines[myChain[n2]].nextTree(n2);
                    n2 = nextChain[myChain[n2]];
                }

                while (myChain[n1] != myChain[n2]) {
                    if (chainDepth[myChain[n1]] != 0) {
                        result += chaines[myChain[n1]].nextTree(n1);
                        n1 = nextChain[myChain[n1]];
                    }
                    if (chainDepth[myChain[n2]] != 0) {
                        result += chaines[myChain[n2]].nextTree(n2);
                        n2 = nextChain[myChain[n2]];
                    }
                }

                if (level[n1] < level[n2]) {
                    int temp = n1;
                    n1 = n2;
                    n2 = temp;
                }

                result += chaines[myChain[n1]].find(n1, n2);
                queries[i].result = result+"\n";
            }
        }
        for (Query q : queries) {
            bw.write(q.result);
        }
        bw.flush();

    }
    public static int find (int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }
    public static void union (int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p1 > p2) {
            parent[p1] = p2;
        } else {
            parent[p2] = p1;
        }
    }
}