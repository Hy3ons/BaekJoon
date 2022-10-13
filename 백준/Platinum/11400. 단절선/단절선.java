import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

class Line {
    int a, b;
    String name;
    boolean iscut;
    Line (int n1, int n2) {
        a = Math.min(n1, n2);
        b = Math.max(n1, n2);
        name = a+" "+b;
    }
}

class Pair {
    int go , idx;
    Pair (int go , int idx) {
        this.go = go;
        this.idx = idx;
    }
}

public class Main {
    public static ArrayList<ArrayList<Pair>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        final int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        load = new ArrayList<>(N+1);
        
        for (int i=0;i<=N;i++) load.add(new ArrayList<>());
        
        lines = new Line[M];
        
        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            
            load.get(n1).add(new Pair(n2, i));
            load.get(n2).add(new Pair(n1, i));
            
            lines[i] = new Line(n1, n2);
        }
        for (int i=1;i<=N;i++) if (in[i] == 0) dfs(i, 0, true);
        bw.write(ans.size()+"\n");
        ans.sort(new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                if (o1.a == o2.a) return Integer.compare(o1.b, o2.b);
                return Integer.compare(o1.a, o2.a);
            }
        });
        for (Line line : ans) bw.write(line.name+"\n");
        bw.flush();
        
    }
    public final static int MAX = 100001;
    public static int[] in = new int[MAX];
    public static int count;
    public static Line[] lines;
    public static ArrayList<Line> ans = new ArrayList<>();
    
    
    public static int dfs (int node, int prev, boolean root) {
        int res = in[node] = ++count;
        for (Pair go : load.get(node)) if (go.go != prev){
            if (in[go.go] == 0) {
                int k = dfs(go.go, node, false);
                res = Math.min(res, k);
                if (k > in[node])
                    ans.add(lines[go.idx]);
            } else {
                res = Math.min(res, in[go.go]);
            }
        }
        return res;
    }
}