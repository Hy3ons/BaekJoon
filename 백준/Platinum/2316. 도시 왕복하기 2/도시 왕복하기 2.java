import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken()) * 2;
        int amount = Integer.parseInt(st.nextToken());

        int[][] limit = new int[testcase+1][testcase+1]
                , flow = new int[testcase+1][testcase+1];

        ArrayList<ArrayList<Integer>> load = new ArrayList<>(testcase+1);
        for (int i=0;i<=testcase;i++) load.add(new ArrayList<>());

        for (int i=1;i<=testcase/2;i++) {
            load.get(i).add(i+testcase/2);
            load.get(i + testcase/2).add(i);
            limit[i][i+testcase/2] = 1;
        }

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int go = Integer.parseInt(st.nextToken());

            limit[from + testcase/2][go] = limit[go + testcase/2][from] = 1;

            load.get(from + testcase/2).add(go);
            load.get(go).add(from + testcase/2);
            load.get(go + testcase/2).add(from);
            load.get(from).add(go + testcase/2);
        }
        long result = 0;
        int[] re = new int[testcase+1];
        Queue<Integer> qu = new LinkedList<>();

        while (true) {
            Arrays.fill(re, -1);
            qu.offer(1 + testcase/2);

            while (!qu.isEmpty()) {
                int now = qu.poll();

                for (int go : load.get(now)) {
                    if (re[go] == -1 && limit[now][go] > flow[now][go]) {
                        re[go] = now;
                        qu.offer(go);
                    }
                }
            }
            //2에 도달 못함
            if (re[2] == -1) break;

            for (int i = 2; i != 1 + testcase/2; i = re[i]) {
                flow[re[i]][i]++;
                flow[i][re[i]]--;
            }
            result++;
        }

        System.out.println(result);

    }
}
