import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

class Line {
    int from, go, value;
    Line (int from, int go, int value) {
        this.from = from;
        this.go = go;
        this.value = value;
    }
}

public class Main {
    public static int INF = 1000000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        while(testcase-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int size = Integer.parseInt(st.nextToken());
            int load_size = Integer.parseInt(st.nextToken());
            limit = new int[size+1][size+1];
            flow = new int[size+1][size+1];
            load = new ArrayList<>();
            for (int i=0;i<=size;i++) load.add(new ArrayList<>());

            ArrayList<Line> lines = new ArrayList<>();
            HashSet<String> hs = new HashSet<>();

            for (int i=0;i<load_size;i++) {
                st  =new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int go = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());

                limit[from][go] += value;
                load.get(from).add(go);
                load.get(go).add(from);

                lines.add(new Line(from, go, value));

            }
            flowing(1, size);
            result = 0;

            int[] re = new int[size+1];
            Queue<Integer> qu = new LinkedList<>();
            for (Line L : lines) {
                if (flow[L.go][L.from] < 0 &&limit[L.from][L.go] == flow[L.from][L.go]) {
                    Arrays.fill(re, -1);
                    re[L.from] = L.go;
                    qu.offer(L.from);

                    while(!qu.isEmpty()) {
                        int now = qu.poll();

                        for (int go : load.get(now)) {
                            if (re[go] == -1 && limit[now][go] > flow[now][go]) {
                                re[go] = now;
                                qu.offer(go);
                            }
                        }
                    }

                    if (re[L.go] == -1) result++;
                }
            }

            bw.write(result+"\n");
        }

        bw.flush();

    }
    public static int[][] limit, flow;
    public static ArrayList<ArrayList<Integer>> load;
    public static int result;
    public static void flowing (int start, int goal) {
        int[] re = new int[load.size()];
        Queue<Integer> qu = new LinkedList<>();

        while (true) {
            Arrays.fill(re, -1);

            qu.offer(start);
            while(!qu.isEmpty()) {
                int now = qu.poll();

                for (int go : load.get(now)) {
                    if (re[go] == -1 && limit[now][go] > flow[now][go]) {
                        re[go] = now;
                        qu.offer(go);
                    }
                }
            }

            if (re[goal] == -1) return;

            int maxFlow = Integer.MAX_VALUE;

            for (int i=goal;i!=start;i = re[i]) {
                maxFlow = Math.min(maxFlow, limit[re[i]][i] - flow[re[i]][i]);
            }
            for (int i=goal;i!=start;i = re[i]) {
                flow[re[i]][i] += maxFlow;
                flow[i][re[i]] -= maxFlow;
            }
        }
    }
    public static void visiting (int start, int goal) {

    }
}