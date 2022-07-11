import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Load {
    double value;
    int p1, p2;
    Load (int p1, int p2, Pointe pp1, Pointe pp2) {
        value = Math.sqrt(Math.pow(Math.abs(pp1.x - pp2.x), 2) + Math.pow(Math.abs(pp1.y - pp2.y) , 2));
        this.p1 = p1;
        this.p2 = p2;
    }
}

class Pointe {
    double x, y;
    Pointe (double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        final int V = Integer.parseInt(br.readLine());

        Pointe[] pointers = new Pointe[V];
        ArrayList<Load> loads = new ArrayList<>();

        for (int i=0;i<V;i++) {
            st = new StringTokenizer(br.readLine());
            double n1 = Double.parseDouble(st.nextToken());
            double n2 = Double.parseDouble(st.nextToken());

            pointers[i] = new Pointe(n1, n2);
        }

        for (int i=0;i<V;i++) {
            for (int j=i+1;j<V;j++) {
                loads.add(new Load(i,j, pointers[i], pointers[j]));
            }
        }

        Collections.sort(loads, new Comparator<Load>() {
            @Override
            public int compare(Load o1, Load o2) {
                return Double.compare(o1.value, o2.value);
            }
        });

        int[] parent = new int[V+1];
        for (int i=1;i<parent.length;i++) parent[i] = i;
        double result = 0;

        for (int i=0;i<loads.size();i++) {
            if (find(parent, loads.get(i).p1) == find(parent, loads.get(i).p2)) continue;

            union(parent, loads.get(i).p1, loads.get(i).p2);
            result += loads.get(i).value;
        }
        System.out.println(result);

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
