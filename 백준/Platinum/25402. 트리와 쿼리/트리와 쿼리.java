import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static int N;
    public static int[] size = new int[250002];
    public static int[] parent = new int[250002];
    public static ArrayList<ArrayList<Integer>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        load = new ArrayList<>(N+1);
        for(int i=0;i<=N;i++) load.add(new ArrayList<>());
        for (int i=0;i<parent.length;i++) parent[i] = i;
        Arrays.fill(size, 1);

        StringTokenizer st;
        for (int i=1;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        dfs(1, -1);

        int queries = Integer.parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] arr = new int[250002];

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            sum = 0;

            int n = Integer.parseInt(st.nextToken());

            for (int i=0;i<n;i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                isOn[arr[i]] = true;
            }

            for (int i=0;i<n;i++) {
                if (isOn[up[arr[i]]]) {
                    union(arr[i], up[arr[i]]);
                }
            }

            bw.write(sum+"\n");

            for (int i=0;i<n;i++) {
                parent[arr[i]] = arr[i];
                size[arr[i]] = 1;
                isOn[arr[i]] = false;
            }
        }
        bw.flush();
    }
    public static long sum;
    public static int[] up = new int[250002];
    public static void dfs (int node, int prev) {
        for (int go : load.get(node)) {
            if (go != prev) {
                up[go] = node;
                dfs(go, node);
            }
        }
    }
    public static void union (int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        sum += (long) size[p1] * size[p2];
        parent[p1] = p2;
        size[p2] += size[p1];
    }

    public static int find(int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }

    public static boolean[] isOn = new boolean[250002];
}