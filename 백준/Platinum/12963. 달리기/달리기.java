import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;

class Query {
    int n1, n2, i;

    Query (int n1, int n2, int i) {
        this.n1 = n1;
        this.n2 = n2;
        this.i = i;
    }

    void con () {
        ArrayList<ArrayList<Integer>> load = Main.load;

        load.get(n1).add(i+2000);
        load.get(i+2000).add(n1);

        load.get(n2).add(i+2000);
        load.get(i+2000).add(n2);
    }
}

public class Main {
    public static int MOD = 1000000007;
    public static boolean[] used = new boolean[5000], visit = new boolean[4001];
    public static ArrayList<ArrayList<Integer>> load = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());

        for (int i=0;i<=4000;i++) load.add(new ArrayList<>());
        ArrayList<Query> queries = new ArrayList<>();

        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            queries.add(new Query(n1, n2, i));
        }

        Collections.reverse(queries);

        int start = 0, goal = N-1;
        long answer = 0;

        for (Query query : queries) {
            query.con();
            Arrays.fill(visit, false);

            dfs(query.i+2000);

            if (visit[start] && visit[goal]) {
                answer += cal(query.i, 3);
                answer %= MOD;
                used[query.i+2000] = true;
            }
        }
        System.out.println(answer);
    }
    public static void dfs (int node) {
        for (int go : load.get(node)) {
            if (used[go] || visit[go]) continue;
            visit[go] = true;

            dfs(go);
        }
    }

    public static long cal (int exp, int base) {
        long res = 1, mul = base;

        while(exp > 0) {
            if ((exp & 1) == 1) {
                res *= mul;
                res %= MOD;
            }

            mul *= mul;
            mul %= MOD;

            exp >>= 1;
        }

        return res;
    }
}