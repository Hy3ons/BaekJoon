import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int testcase = Integer.parseInt(st.nextToken());
        int queryAmount = Integer.parseInt(st.nextToken());

        parent = new int[testcase+1];
        for (int i=1;i<=testcase;i++) parent[i] = i;

        while(queryAmount-->0) {
            st = new StringTokenizer(br.readLine());
            int method = Integer.parseInt(st.nextToken());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            switch (method) {
                case 0 :
                    union(n1, n2);
                    break;
                case 1 :
                    if (getParent(n1) == getParent(n2))
                        bw.write("YES\n");
                    else
                        bw.write("NO\n");
                    break;
            }
        }
        bw.flush();

    }
    public static int[] parent;

    public static void union (int node1, int node2) {
        int p1 = getParent(node1);
        int p2 = getParent(node2);

        if (p1 > p2)
            parent[p1] = p2;
        else
            parent[p2] = p1;
    }
    public static int getParent(int node) {
        if (parent[node]==node) return node;
        return parent[node] = getParent(parent[node]);
    }
}