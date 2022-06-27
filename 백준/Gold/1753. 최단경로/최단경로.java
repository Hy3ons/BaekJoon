import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

class Pair {
    int loc, value;
    Pair (int loc, int value) {
        this.loc = loc;
        this.value = value;
    }
}

public class Main {
    public static final int INF = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(st.nextToken());

        int start = Integer.parseInt(br.readLine());

        int[] dp = new int[testcase+1];
        ArrayList<ArrayList<Pair>> load = new ArrayList<>(testcase+1);
        for (int i=0;i<testcase+1;i++) load.add(new ArrayList<>());

        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o1.value - o2.value;
            }
        });

        while(amount-->0) {
            st = new StringTokenizer(br.readLine());

            int from = Integer.parseInt(st.nextToken());
            int go = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            load.get(from).add(new Pair(go, value));
        }

        Arrays.fill(dp, INF);
        dp[start] = 0;

        for (int i=0;i<load.get(start).size();i++) {
            Pair go = load.get(start).get(i);
            
            if (dp[go.loc] > go.value) {
                dp[go.loc] = go.value;
                pq.offer(new Pair(go.loc, dp[go.loc]));
            }
        }

        while(!pq.isEmpty()) {
            Pair from = pq.poll();
            if (from.value != dp[from.loc]) continue;

            for (int i = 0; i<load.get(from.loc).size(); i++) {
                Pair go = load.get(from.loc).get(i);

                if (from.value + go.value < dp[go.loc]) {
                    dp[go.loc] = from.value + go.value;
                    pq.offer(new Pair(go.loc, dp[go.loc]));
                }
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        dp[start] = 0;
        for (int i=1;i<=testcase;i++) {
            if (dp[i]==INF) {
                bw.write("INF\n");
            } else {
                bw.write(dp[i]+"\n");
            }
        }
        bw.flush();

    }
}
