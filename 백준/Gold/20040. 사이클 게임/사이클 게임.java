import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(st.nextToken());

        parent = new int[testcase];
        for (int i=1;i<testcase;i++) parent[i] = i;

        for (int i=1;i<=amount;i++) {
            st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());

            if (getParent(node1) == getParent(node2)) {
                System.out.println(i);
                return;
            }
            union(node1, node2);
        }
        System.out.println(0);

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