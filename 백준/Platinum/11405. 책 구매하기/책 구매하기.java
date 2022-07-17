import java.io.*;
import java.util.*;

public class Main {
    public static int INF = 2100000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int people = Integer.parseInt(st.nextToken());
        int stores = Integer.parseInt(st.nextToken());

        int size = (people + stores) * 2 + 3;
        int part = people * 2;

        load = new ArrayList<>(size);
        limit = new int[size][size];
        flow = new int[size][size];
        cost = new int[size][size];

        for (int i = 0; i < size; i++) load.add(new ArrayList<>());

        int start = size-2, goal = size-1;
        for (int i=1;i<=stores;i++) {
            load.get(start).add(i + part);
            limit[start][i+part] = INF;
        }

        for (int i=1;i<=people;i++) {
            load.get(i + people).add(goal);
            limit[i+people][goal] = INF;
        }

        st = new StringTokenizer(br.readLine());
        for (int i=1;i<=people;i++) {
            limit[i][people + i] = Integer.parseInt(st.nextToken());

            load.get(i).add(people + i);
            load.get(people + i).add(i);
        }
        st = new StringTokenizer(br.readLine());
        for (int i=1;i<=stores;i++) {
            limit[part + i][part + i + stores] = Integer.parseInt(st.nextToken());

            load.get(part + i).add(part + i + stores);
            load.get(part + i + stores).add(part + i);
        }

        for (int i=1;i<=stores;i++) {
            st = new StringTokenizer(br.readLine());

            for (int person = 1; person<=people; person++) {
                cost[part + i + stores][person] = Integer.parseInt(st.nextToken());
                cost[person][part + i + stores] = -cost[part + i + stores][person];

                limit[part + i + stores][person] = INF;

                load.get(part + i + stores).add(person);
                load.get(person).add(part + i + stores);
            }
        }

        int money = 0;
        int[] re = new int[size], nowD = new int[size];
        boolean[] isExist = new boolean[size];
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
                money += cost[re[i]][i] * canFlow;
            }
        }
        System.out.println(money);

    }

    public static ArrayList<ArrayList<Integer>> load;
    public static int[][] limit, flow, cost;
}