import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static int[] color = new int[100001];
    public static int[] r = new int[100001] , b = new int[100001];
    public static long[] ans = new long[100001];
    public static ArrayList<ArrayList<Integer>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=1;i<=n;i++) {
            color[i] = Integer.parseInt(st.nextToken());
        }

        load = new ArrayList<>(n+1);
        for (int i=0;i<=n;i++) load.add(new ArrayList<>());

        for (int i=1;i<n;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }
        dfs(1, 0);

        int q = Integer.parseInt(br.readLine());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(q-->0) {
            int node = Integer.parseInt(br.readLine());
            long f = ans[node];
            if (color[node] == 1) {
                f += ((long)(r[1]-r[node]) * b[node]);
                f += ((long)(b[1] - b[node]) * (r[node] - 1));
            } else {
                f += ((long)(r[1]-r[node]) * (b[node] - 1));
                f += ((long)(b[1] - b[node]) * (r[node]));
            }
            bw.write(f+"\n");
        }

        bw.flush();
    }

    public static void dfs (int node, int prev) {
        for (int go : load.get(node)) if (go != prev) {
            dfs(go, node);
            ans[node] += (long) r[go] * b[node];
            ans[node] += (long) b[go] * r[node];
            r[node] += r[go]; b[node] += b[go];
        }

        add(node);
    }

    public static void add (int node) {
        if (color[node] == 1) {
            r[node]++;
        } else b[node]++;
    }
    public static void pop(int node) {
        if (color[node] == 1) {
            r[node]--;
        } else b[node]--;

    }
}
