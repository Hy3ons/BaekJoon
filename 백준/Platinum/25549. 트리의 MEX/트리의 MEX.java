import java.io.*;
import java.util.*;

class Pair {
    int go;
    long dist, water;
    Pair (int go , long idst) {
        this.go = go;
        this.dist = idst;
    }

    Pair (int go, long dist, long water) {
        this.go = go;
        this.dist = dist;
        this.water = water;
    }
}

public class Main {
    public static int[] num, parent;
    public static int[] answer = new int[200001], real = new int[200001];
    public static HashMap<Integer, Integer>[] hms = new HashMap[200001];
    public static ArrayList<ArrayList<Integer>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N+1];
        parent = new int[N+1];

        for (int i=0;i<parent.length;i++) parent[i] = i;
        load = new ArrayList<>(N+1);
        for (int i=0;i<=N;i++) load.add(new ArrayList<>());
        int[] indegree = new int[N+1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=1;i<=N;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            if (arr[i] == -1) continue;
            load.get(arr[i]).add(i);
            indegree[i]++;
        }
        num = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for (int i=1;i<=N;i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        for (int i=0;i<hms.length;i++) hms[i] = new HashMap<>();

        for (int i=1;i<=N;i++) {
            hms[i].put(num[i], 1);
            answer[i] = num[i] == 0 ? 1 : 0;
        }


        for (int i=1;i<=N;i++) {
            if (indegree[i] == 0) dfs(i, 0);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i=1;i<=N;i++) {
            bw.write(real[i]+"\n");
        }
        bw.flush();


    }

    public static void dfs (int node,int prev) {
        for (int go : load.get(node)) if (go != prev) dfs(go, node);

        for (int go : load.get(node)) if (go != prev) {
            union(node, go);
        }
        real[node] = answer[find(node)];
    }
    public static int find (int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }

    public static void union (int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (hms[p1].size() > hms[p2].size()) {
            int temp = p2;
            p2 = p1;
            p1 = temp;
        }

        parent[p1] = p2;

        ArrayList<Integer> keys = new ArrayList<>(hms[p1].keySet());
        for (int key : keys) {
            if (hms[p2].containsKey(key)) {
                hms[p2].put(key, hms[p2].get(key) + hms[p1].get(key));
            } else hms[p2].put(key, hms[p1].get(key));
        }

        answer[p2] = Math.max(answer[p2], answer[p1]);
        for (int i=answer[p2];;i++){
            if (!hms[p2].containsKey(i)) {
                answer[p2] = i;
                break;
            }
        }
    }
}
