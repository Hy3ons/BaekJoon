import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

class Pair {
    int n1, n2, weight;
    
    Pair (int n1, int n2, int weight) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = weight;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        ArrayList<Pair> al = new ArrayList<>();
        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            al.add(new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())
                    , Integer.parseInt(st.nextToken())));
        }
        
        al.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o2.weight, o1.weight);
            }
        });
        
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
        for (int i=0;i<parent.length;i++) parent[i] = i;
        
        for (Pair p : al) {
            if (find(p.n1) != find(p.n2)) {
                union(p.n1, p.n2);
            }
            
            if (find(a) == find(b)) {
                System.out.println(p.weight);
                return;
            }
        }
    }
    
    public static int[] parent = new int[100001];
    public static int find (int node) {
        return (parent[node] == node) ? node : (parent[node] = find(parent[node]));
    }
    
    public static void union (int n1 , int n2 ) {
        int p1 = find(n1);
        int p2 = find(n2);
        
        parent[p1] = p2;
    }
}