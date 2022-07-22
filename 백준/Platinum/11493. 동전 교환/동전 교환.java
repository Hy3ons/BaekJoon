import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static int INF = 1000000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        while(testcase-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int size = Integer.parseInt(st.nextToken());
            int loadAmount = Integer.parseInt(st.nextToken());

            limit = new int[size+3][size+3];
            flow = new int[size+3][size+3];
            cost = new int[size+3][size+3];
            load = new ArrayList<>(size+3);
            int start = size+1, end = size+2;

            for (int i=0;i<size+3;i++) load.add(new ArrayList<>());

            for (int i=0;i<loadAmount;i++) {
                st = new StringTokenizer(br.readLine());
                int n1 = Integer.parseInt(st.nextToken());
                int n2 = Integer.parseInt(st.nextToken());

                cost[n1][n2] = cost[n2][n1] = 1;
                limit[n1][n2] = limit[n2][n1] = INF;

                load.get(n1).add(n2);
                load.get(n2).add(n1);
            }
            st = new StringTokenizer(br.readLine());
            for (int i=1;i<=size;i++) {
                int out = Integer.parseInt(st.nextToken());
                if (out==0) {
                    load.get(i).add(end);
                    limit[i][end] = 1;
                }
            }
            st = new StringTokenizer(br.readLine());
            for (int i=1;i<=size;i++) {
                int in = Integer.parseInt(st.nextToken());
                if (in == 0) {
                    load.get(start).add(i);
                    limit[start][i] = 1;
                }
            }

            int[] re = new int[size+3];
            int[] d = new int[size+3];
            Queue<Integer> qu = new LinkedList<>();
            long result = 0;

            while(true) {
                Arrays.fill(d, INF);
                Arrays.fill(re, -1);

                d[start] = 0;
                qu.offer(start);

                while(!qu.isEmpty()) {
                    int now = qu.poll();

                    for (int go : load.get(now)) {
                        if (limit[now][go] > flow[now][go] && d[go] > d[now] + cost[now][go] * (flow[now][go]>=0 ? 1: -1)) {
                            d[go] = d[now] + cost[now][go] * (flow[now][go]>=0 ? 1: -1);
                            re[go] = now;
                            qu.offer(go);
                        }
                    }
                }
                if (re[end] == -1) break;

                for (int i=end;i!=start;i = re[i]) {
                    result += cost[re[i]][i] * (flow[re[i]][i]>=0 ? 1: -1);
                    flow[re[i]][i]++;
                    flow[i][re[i]]--;
                }
            }
            bw.write(result+"\n");
        }
        bw.flush();

    }
    public static int[][] limit, flow, cost;
    public static ArrayList<ArrayList<Integer>> load;

}