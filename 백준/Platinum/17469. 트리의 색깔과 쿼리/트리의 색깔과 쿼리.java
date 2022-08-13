import java.io.*;
import java.util.*;

class Query {
    int m, node, answer;
    Query(int m, int node) {
        this.m = m;
        this.node = node;
    }
}

public class Main {
    public static final int MAX = 100001;
    public static ArrayList<ArrayList<Integer>> load;
    public static int[] parent = new int[MAX], up = new int[MAX], color = new int[MAX];
    public static HashSet<Integer>[] hs = new HashSet[MAX];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()), queries = Integer.parseInt(st.nextToken()) + N - 1;

        Query[] q = new Query[queries];

        load = new ArrayList<>(N+1);
        for (int i=0;i<=N;i++) load.add(new ArrayList<>());

        for (int i=2;i<=N;i++) {
            int temp = Integer.parseInt(br.readLine());
            up[i] = temp;
            load.get(i).add(temp);
            load.get(temp).add(i);
        }

        for (int i=1;i<=N;i++) color[i] = Integer.parseInt(br.readLine());

        for (int i=1;i<=N;i++) {
            parent[i] = i;
            hs[i] = new HashSet();
            hs[i].add(color[i]);
        }

        for (int i=0;i<q.length;i++) {
            st = new StringTokenizer(br.readLine());
            q[i] = new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        for (int i=q.length-1;i>=0;i--) {
            if (q[i].m == 2) {
                q[i].answer = hs[find(q[i].node)].size();
            } else {
                union(q[i].node, up[q[i].node]);
            }
        }


        for (int i=0;i<q.length;i++) if (q[i].m == 2) {
            bw.write(q[i].answer+"\n");
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

        if (hs[p1].size() > hs[p2].size()) {
            int temp = p1;
            p1 = p2;
            p2 = temp;
        }

        int finalP = p2;
        hs[p1].forEach(a -> hs[finalP].add(a));

        parent[p1] = p2;
    }
}