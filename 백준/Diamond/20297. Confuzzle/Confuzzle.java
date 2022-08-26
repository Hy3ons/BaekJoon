import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static final int MAX = 100001;
    public static int[] arr = new int[MAX], size = new int[MAX];
    public static boolean[] cent = new boolean[MAX];
    public static ArrayList<ArrayList<Integer>> load;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        load = new ArrayList<>(N+1);
        for(int i=0;i<=N;i++) load.add(new ArrayList<>());

        for (int i=1;i<=N;i++) arr[i] = Integer.parseInt(st.nextToken());
        for (int i=1;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());

            load.get(n1).add(n2);
            load.get(n2).add(n1);
        }

        function(1);
        System.out.println(result);
    }
    public static int result = Integer.MAX_VALUE;
    public static HashMap<Integer, Integer> hm = new HashMap<>();
    public static void function(int anyNode) {
        int limit = getSize(anyNode, 0);
        int cet = getCentroid(anyNode, limit, 0);

        if(cent[cet]) return;
        cent[cet] = true;
        hm.clear();

        for (int go : load.get(cet)) if (!cent[go]) {
            check(go, 0, 1);
            update(go, 0, 1);
        }
        
        if (hm.containsKey(arr[cet])) {
            result = Math.min(result, hm.get(arr[cet]));
        }

        for (int go : load.get(cet)) if (!cent[go]) {
            function(go);
        }
    }

    public static void update (int node, int prev, int depth) {
        if (hm.containsKey(arr[node])) {
            hm.put(arr[node], Math.min(hm.get(arr[node]), depth));
        } else {
            hm.put(arr[node], depth);
        }

        for (int go : load.get(node)) if (prev != go && !cent[go]) {
            update(go, node, depth+1);
        }
    }
    public static void check (int node, int prev, int depth) {
        if (hm.containsKey(arr[node])) {
            result = Math.min(result, hm.get(arr[node]) + depth);
        }

        for (int go : load.get(node)) if (prev != go && !cent[go]) {
            check(go, node, depth+1);
        }
    }

    public static int getSize (int node, int prev) {
        size[node] = 1;
        for (int go : load.get(node)) if (go != prev && !cent[go]) {
            size[node] += getSize(go, node);
        }
        return size[node];
    }

    public static int getCentroid (int node, int limit, int prev) {
        for (int go : load.get(node)) if (go != prev && !cent[go] && size[go] * 2 > limit) {
            return getCentroid(go, limit, node);
        }
        return node;
    }
}