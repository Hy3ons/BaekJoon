import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int people = Integer.parseInt(st.nextToken());
        int works = Integer.parseInt(st.nextToken());

        load = new ArrayList<>(people + works + 3);
        limit = new int[people + works + 3][people + works + 3];
        flow = new int[people + works + 3][people + works + 3];
        cost = new int[people + works + 3][people + works + 3];

        for (int i = 0; i < people + works + 3; i++) load.add(new ArrayList<>());

        int start = people + works + 1, goal = people + works + 2;
        for (int i=1;i<=people;i++) {
            load.get(start).add(i);
            limit[start][i] = 1;
        }

        for (int i=1;i<=works;i++) {
            load.get(i + people).add(goal);
            limit[i + people][goal] = 1;
        }

        for (int person = 1; person <= people; person++) {
            st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());

            while (time-- > 0) {
                int work = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());

                load.get(person).add(people + work);
                load.get(people + work).add(person);
                limit[person][people + work] = 1;
                cost[person][people + work] = value;
                cost[people + work][person] = -value;
            }
        }

        int result = 0, money = 0;
        int[] re = new int[people + works + 3], nowD = new int[people + works + 3];
        boolean[] isExist = new boolean[people + works + 3];
        Queue<Integer> qu = new LinkedList<>();

        while (true) {
            Arrays.fill(re, -1);
            Arrays.fill(nowD, Integer.MAX_VALUE);

            nowD[start] = 0;
            qu.offer(start);

            while (!qu.isEmpty()) {
                int now = qu.poll();
                isExist[now] = false;

                for (int go : load.get(now)) {
                    if (limit[now][go] > flow[now][go] && nowD[go] > nowD[now] + cost[now][go]) {
                        nowD[go] = nowD[now] + cost[now][go];
                        re[go] = now;

                        if (!isExist[go]) {
                            isExist[go] = true;
                            qu.offer(go);
                        }
                    }
                }
            }

            if (re[goal] == -1) break;

            int canFlow = Integer.MAX_VALUE;
            for (int i = goal; i!= start; i = re[i]) {
                canFlow = Math.min(canFlow, limit[re[i]][i] - flow[re[i]][i]);
            }

            for (int i = goal; i!= start; i = re[i]) {
                flow[re[i]][i] += canFlow;
                flow[i][re[i]] -= canFlow;
                money += cost[re[i]][i];
            }
            result += canFlow;
        }
        System.out.println(result+"\n"+money);

    }

    public static ArrayList<ArrayList<Integer>> load;
    public static int[][] limit, flow, cost;
}