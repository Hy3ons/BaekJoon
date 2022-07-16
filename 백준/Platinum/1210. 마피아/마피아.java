import java.io.*;
import java.util.*;

public class Main {
    public static int INF = 2100000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        gate = Integer.parseInt(st.nextToken());
        int loadAmount = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        start = Integer.parseInt(st.nextToken());
        goal = Integer.parseInt(st.nextToken());

        limit = new int[gate*2+1][gate*2+1];
        flow = new int[gate*2+1][gate*2+1];
        load = new ArrayList<>(gate*2+1);

        for (int i=0;i<gate*2+1;i++) load.add(new ArrayList<>());

        for (int i=1;i<=gate;i++) {
            int value = Integer.parseInt(br.readLine());

            load.get(i).add(i+gate);
            load.get(i+gate).add(i);

            limit[i][i+gate] = value;
        }

        for (int i=0;i<loadAmount;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            limit[n1+gate][n2] = limit[n2+gate][n1] = INF;
            load.get(n1+gate).add(n2);
            load.get(n2).add(n1+gate);

            load.get(n2+gate).add(n1);
            load.get(n1).add(n2+gate);
        }

        int[] level = dinic_Algorithm(start, goal + gate);
        BufferedWriter bw=  new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i=1;i<=gate;i++) {
            if (level[i] != -1 && level[i + gate] == -1) bw.write(i+" ");
        }
        bw.flush();


    }
    public static ArrayList<ArrayList<Integer>> load;
    public static int[][] limit, flow;
    public static int start, goal, gate;
    public static int[] dinic_Algorithm (int start, int end) {
        Queue<Integer> qu = new LinkedList<>();
        int[] level = new int[flow.length], re = new int[flow.length], visited = new int[flow.length];
        while (true) {
            Arrays.fill(level, -1);
            qu.offer(start);

            level[start] = 0;

            while(!qu.isEmpty()) {
                int now = qu.poll();

                for (int go : load.get(now)) {
                    if (level[go] == -1 && limit[now][go] > flow[now][go]) {
                        level[go] = level[now] + 1;
                        qu.offer(go);
                    }
                }
            }
            if (level[end] == -1) return level;

            while (true) {
                Arrays.fill(re, -1);
                qu.offer(start);

                while (!qu.isEmpty()) {
                    int now = qu.poll();

                    for (int go : load.get(now)) {
                        if (re[go] == -1 && limit[now][go] > flow[now][go]) {
                            re[go] = now;
                            qu.offer(go);
                        }
                    }
                }

                if (re[end] == -1) break;

                int flowing = Integer.MAX_VALUE;

                for (int i = end ; i!= start; i = re[i])
                    flowing = Math.min(flowing, limit[re[i]][i] - flow[re[i]][i]);
                for (int i = end ; i!= start; i = re[i]) {
                    flow[re[i]][i] += flowing;
                    flow[i][re[i]] -= flowing;
                }
            }

        }
    }

}