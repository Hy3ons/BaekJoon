import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Load {
    long value;
    int go;
    Load (int go , long value) {
        this.go = go;
        this.value = value;
    }
}

public class Main {
    public static int INF = 1000000;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int testcase = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(st.nextToken());

        load = new ArrayList<>(testcase+1);
        for (int i=0;i<=testcase;i++) load.add(new ArrayList<>());


        while (amount-->0) {
            st = new StringTokenizer(br.readLine());

            int loc1 = Integer.parseInt(st.nextToken());
            int loc2 = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            load.get(loc1).add(new Load(loc2, value));
            load.get(loc2).add(new Load(loc1, value));
        }

        long[][] dp = new long[3][testcase+1];
        for (long[] arr : dp) Arrays.fill(arr, INF);

        st = new StringTokenizer(br.readLine());
        int[] goals = new int[2];
        goals[0] = Integer.parseInt(st.nextToken());
        goals[1] = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Load>> myLoad = new ArrayList<>(4);
        for (int i=0;i<4;i++) myLoad.add(new ArrayList<>());

        for (int i=0;i<2;i++)
            dijkstra(goals[i], load, dp[i+1]);
        dijkstra(1, load, dp[0]);

        if (dp[0][testcase]==INF) {
            System.out.println(-1);
            return;
        }

        long[] loads = new long[5];
        loads[0] = dp[0][goals[0]];
        loads[1] = dp[0][goals[1]];
        loads[2] = dp[1][goals[1]];
        loads[3] = dp[1][testcase];
        loads[4] = dp[2][testcase];

        long load1 = loads[0] + loads[2] + loads[4];
        long load2 = loads[1] + loads[2] + loads[3];

        System.out.println(Math.min(load1, load2));


    }

    public static ArrayList<ArrayList<Load>> load;

    public static void dijkstra (int start, ArrayList<ArrayList<Load>> load, long[] dp) {
        PriorityQueue<Load> pq = new PriorityQueue<>(new Comparator<Load>() {
            @Override
            public int compare(Load o1, Load o2) {
                return Long.compare(o1.value, o2.value);
            }
        });

        dp[start] = 0;

        for (Load go : load.get(start)) {
            if (dp[go.go] > go.value) {
                dp[go.go] = go.value;
                pq.offer(new Load(go.go, go.value));
            }
        }

        while(!pq.isEmpty()) {
            Load now = pq.poll();
            if (now.value != dp[now.go]) continue;

            for (Load go : load.get(now.go)) {
                if (dp[go.go] > dp[now.go] + go.value) {
                    dp[go.go] = dp[now.go] + go.value;
                    pq.offer(new Load(go.go, dp[go.go]));
                }
            }
        }
    }
}
