import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.jar.JarEntry;

public class Main {
    public static final int MAX = 1000000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        load = new ArrayList<>();
        ArrayList<Integer> peopleA = new ArrayList<>(),
                peopleB = new ArrayList<>()
                , normal = new ArrayList<>();


        int amount = Integer.parseInt(br.readLine());
        start = amount;
        goal = amount+1;
        long result = 0;

        int[] level = new int[amount+2]
                ,re = new int[amount+2];
        Queue<Integer> qu = new LinkedList<>();

        for (int i=0;i< amount+2;i++) load.add(new ArrayList<>());

        int[] mind = new int[amount];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=0;i<mind.length;i++) {
            mind[i] = Integer.parseInt(st.nextToken());
            if (mind[i]==0) {
                normal.add(i);
            } else if (mind[i]==1) {
                peopleA.add(i);
            } else {
                peopleB.add(i);
            }
        }

        int[][] cost = new int[amount+2][amount+2]
                ,flow = new int[amount+2][amount+2];
        visited = new int[amount+2];

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<amount;j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int A : peopleA){
            cost[start][A] = MAX;
            load.get(start).add(A);
        }
        for (int B : peopleB) {
            cost[B][goal] = MAX;
            load.get(B).add(goal);
        }

        for (int i=0;i< normal.size();i++) {
            for (int j=i+1;j< normal.size();j++) {
                load.get(normal.get(i)).add(normal.get(j));
                load.get(normal.get(j)).add(normal.get(i));
            }
        }

        for (int A : peopleA) {
            for (int B : peopleB) {
                result += cost[A][B];
            }
        }

        for (int N : normal) {

            for (int A : peopleA) {
//                load.get(N).add(A);
                load.get(A).add(N);
            }

            for (int B : peopleB) {
                load.get(N).add(B);
//                load.get(B).add(N);
            }
        }

        while (true) {
            Arrays.fill(level, -1);
            qu.offer(start);
            level[start] = 0;

            while(!qu.isEmpty()) {
                int now = qu.poll();

                for (int go : load.get(now)) {
                    if (level[go]==-1 && cost[now][go] > flow[now][go]) {
                        level[go] = level[now] + 1;
                        qu.offer(go);
                    }
                }
            }

            if (level[goal]==-1) break;

            stack++;
            while (true) {
                int temp = dfs(level, cost, flow, MAX, start);
                if (temp == 0) break;
                result += temp;
            }
        }

        //정답 출력부분.
        bw.write(result+"\n");

        for (int i=0;i<mind.length;i++) {
            if (level[i]!=-1) {
                bw.write(i+1+" ");
            }
        }

        bw.write('\n');
        for (int i=0;i<mind.length;i++) {
            if (level[i]==-1) {
                bw.write(i+1+" ");
            }
        }

        bw.write('\n');
        bw.flush();

    }
    public static ArrayList<ArrayList<Integer>> load;
    public static int start, goal, stack;
    public static int[] visited;
    public static int dfs (int[] level, int[][] cost, int[][] flow, int maxFlow, int now) {
        if (now == goal) return maxFlow;

        for (int go : load.get(now)) {
            if (visited[go] == stack) continue;

            if (level[now] + 1 == level[go] && cost[now][go] > flow[now][go]) {
                int flowing = dfs(level, cost, flow, Math.min(maxFlow, cost[now][go] - flow[now][go]), go);

                if (flowing !=0) {
                    flow[now][go] += flowing;
                    flow[go][now] -= flowing;
                    return flowing;
                } else {
                    visited[go] = stack;
                }
            }
        }

        return 0;
    }
}