import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    public static ArrayList<ArrayList<Integer>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
        StringTokenizer st = new StringTokenizer(br.readLine());
        final int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        load = new ArrayList<>(N+1);
        
        for (int i=0;i<=N;i++) load.add(new ArrayList<>());
        
        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }
        for (int i=1;i<=N;i++) if (in[i] == 0) dfs(i, 0, true);
        bw.write(ans.size()+"\n");
        Collections.sort(ans);
        
        for (int e : ans) {
            bw.write(e+" ");
        }
        bw.flush();
        
    }
    public final static int MAX = 10001;
    public static int[] in = new int[MAX];
    public static boolean[] cut = new boolean[MAX];
    public static int count;
    public static ArrayList<Integer> ans = new ArrayList<>();
    
    public static int dfs (int node, int prev, boolean root) {
        int res = in[node] = ++count;
        int dfsCount = 0;
        for (int go : load.get(node)) if (go != prev){
            if (in[go] == 0) {
                int k = dfs(go, node, false);
                res = Math.min(res, k);
                if (!root && k >= in[node]) {
                    cut[node] = true;
                }
                dfsCount++;
            } else {
                res = Math.min(res, in[go]);
            }
        }
        if (root) {
            if (dfsCount > 1) ans.add(node);
        } else if (cut[node]) ans.add(node);
        return res;
    }
}