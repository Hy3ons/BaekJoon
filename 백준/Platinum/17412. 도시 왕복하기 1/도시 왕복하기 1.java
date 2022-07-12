import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(st.nextToken());

        int[][] limit = new int[testcase+1][testcase+1]
                , flow = new int[testcase+1][testcase+1];
        ArrayList<ArrayList<Integer>> load = new ArrayList<>();
        for (int i=0;i<=testcase;i++) load.add(new ArrayList<>());

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int go = Integer.parseInt(st.nextToken());

            limit[from][go] = 1;
            load.get(from).add(go);
            load.get(go).add(from);
        }
        long result = 0;
        int[] re = new int[testcase+1];
        Queue<Integer> qu = new LinkedList<>();

        while (true) {
            Arrays.fill(re, -1);

            qu.offer(1);
            while (!qu.isEmpty()) {
                int now = qu.poll();

                for (int go : load.get(now)) {
                    if (re[go] == -1 && limit[now][go] - flow[now][go] > 0) {
                        re[go] = now;
                        qu.offer(go);
                    }
                }
            }
            //2에 도달 못함
            if (re[2] == -1) break;

            for (int i = 2; i != 1; i = re[i]) {
                flow[re[i]][i]++;
                flow[i][re[i]]--;
            }
            result++;
        }
        System.out.println(result);

    }
}
