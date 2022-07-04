import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String input;
        int caseCount = 0;

        while (true) {
            input = br.readLine();
            if (input.equals("0 0")) break;
            caseCount++;

            StringTokenizer st = new StringTokenizer(input);
            int testcase = Integer.parseInt(st.nextToken());
            int amount = Integer.parseInt(st.nextToken());

            parent = new int[testcase+1];
            for (int i=1;i<=testcase;i++) parent[i] = i;

            while(amount-->0) {
                st = new StringTokenizer(br.readLine());
                int n1 = Integer.parseInt(st.nextToken());
                int n2 = Integer.parseInt(st.nextToken());

                if (getParent(n1) == getParent(n2))
                    union(n1, 0);

                union(n1, n2);
            }
            boolean[] visited = new boolean[testcase+1];
            int count = 0;
            for (int i=1;i<=testcase;i++) {
                int parent = getParent(i);

                if (parent==0) continue;
                if (!visited[parent]) {
                    visited[parent] = true;
                    count++;
                }
            }
            bw.write("Case "+caseCount+": ");
            if (count==1)
                bw.write("There is one tree.\n");
            else if (count==0)
                bw.write("No trees.\n");
            else
                bw.write("A forest of "+count+" trees.\n");
        }
        bw.flush();
    }
    public static int[] parent;

    public static void union (int node1, int node2) {
        int n1 = getParent(node1);
        int n2 = getParent(node2);

        if (n1 > n2)
            parent[n1] = n2;
        else
            parent[n2] = n1;
    }

    public static int getParent (int node) {
        if (parent[node]==node) return node;
        return parent[node] = getParent(parent[node]);
    }
}