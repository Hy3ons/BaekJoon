import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Load {
    int n1, n2, value;
    boolean used;
    Load (int n1, int n2, int value) {
        this.n1 = n1;
        this.n2 = n2;
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        final int V = Integer.parseInt(st.nextToken())
                    ,E = Integer.parseInt(st.nextToken());

        Load[] loads = new Load[E];

        for (int i=0;i<E;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            loads[i] = new Load(n1, n2, value);
        }

        Arrays.sort(loads, new Comparator<Load>() {
            @Override
            public int compare(Load o1, Load o2) {
                return Integer.compare(o1.value, o2.value);
            }
        });

        int[] parent = new int[V+1];
        for (int i=1;i<parent.length;i++) parent[i] = i;
        long result = 0;

        for (int i=0;i<E;i++) {
            if (find(parent, loads[i].n1) == find(parent, loads[i].n2)) continue;

            union(parent, loads[i].n1, loads[i].n2);
            result += loads[i].value;
            loads[i].used = true;
        }
        int max = Integer.MIN_VALUE;

        for (int i=0;i<E;i++) {
            if (loads[i].used) {
                max = Math.max(max, loads[i].value);
            }
        }

        System.out.println(result - max);

    }
    public static int find (int[] parent, int node) {
        if (parent[node]==node) return node;
        return parent[node] = find(parent, parent[node]);
    }
    public static void union (int[] parent, int n1, int n2) {
        int p1 = find(parent, n1);
        int p2 = find(parent, n2);

        if (p1 < p2) {
            parent[p2] = p1;
        } else {
            parent[p1] = p2;
        }
    }

}
