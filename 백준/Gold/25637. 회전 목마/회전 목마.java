import java.io.*;
import java.util.*;

public class Main {
    public static int start = 2001, end = 2002, INF = 100000000;
    public static int[][] flow = new int[2003][2003], limit = new int[2003][2003];
    public static int[][] cost = new int[2003][2003];
    public static ArrayList<ArrayList<Integer>> load = new ArrayList<>(2004);
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i=0;i<2004;i++) load.add(new ArrayList<>());
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=1;i<=n;i++) {
            limit[start][i] = Integer.parseInt(st.nextToken());
            limit[i][end] = 1;
            load.get(start).add(i);
            load.get(i).add(end);
        }

        for (int i=1;i<n;i++) {
            limit[i][i+1] = limit[i+1][i] = INF;
            cost[i][i+1] = cost[i+1][i] = 1;

            load.get(i).add(i+1);
            load.get(i+1).add(i);
        }
        limit[1][n] = limit[n][1] = INF;
        cost[1][n] = cost[n][1] = 1;

        load.get(1).add(n);
        load.get(n).add(1);

        int[] re = new int[2003];
        int[] nc = new int[2003];

        Queue<Integer> qu = new LinkedList<>();
        long ans = 0;

        while(true) {
            Arrays.fill(re, -1);
            Arrays.fill(nc, INF);

            nc[start] = 0;
            qu.offer(start);
            while(!qu.isEmpty()) {
                int now = qu.poll();

                for (int go : load.get(now)) {
                    if (nc[go] > nc[now] + c(flow[now][go]) * cost[now][go]&& limit[now][go] > flow[now][go]) {
                        qu.offer(go);
                        re[go] = now;
                        nc[go] = nc[now] + c(flow[now][go]) * cost[now][go] ;
                    }
                }
            }
            if (re[end] == -1) break;

            for (int i=end;i!=start;i=re[i]) {
                flow[re[i]][i]++;
                flow[i][re[i]]--;
            }
            ans += nc[end];
        }
        System.out.println(ans);


    }
    public static int c (int s) {
        return s >= 0 ? 1 : -1;
    }

}
