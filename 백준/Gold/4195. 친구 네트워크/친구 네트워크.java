import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static int[] parent = new int[700000], size = new int[700000];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());
        while(testcase-->0) {
            int N = Integer.parseInt(br.readLine()  );
            HashMap<String, Integer> hm = new HashMap<>();
            for (int i=0;i<3*N;i++) {
                parent[i] = i;
                size[i] = 1;
            }
            int next = 1;
            StringTokenizer st;

            for (int i=0;i<N;i++) {
                st = new StringTokenizer(br.readLine());
                String a = st.nextToken(), b = st.nextToken();
                if (!hm.containsKey(a)) hm.put(a, next++);
                if(!hm.containsKey(b)) hm.put(b, next++);

                union(hm.get(a), hm.get(b));
                bw.write(size[find(hm.get(a))]+"\n");
            }
        }
        bw.flush();
    }

    public static int find(int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }

    public static void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p1 == p2) return;

        parent[p1] = p2;
        size[p2] += size[p1];
    }
}