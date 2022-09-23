import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair{
    int x, y;
    Pair(int x, int y) {
        this.x=x;this.y=y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
        N = b;
        board = new int[a][b];

        for (int i=0;i<a;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<b;j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        load = new ArrayList<>(a*b+1);

        for (int i=0;i<=a*b;i++) load.add(new ArrayList<>());

        Queue<Pair> qu = new LinkedList<>();
        qu.offer(new Pair(0,0));
        int[] indegree = new int[a*b+1];
        boolean[][] visit = new boolean[a][b];

        while(!qu.isEmpty()) {
            Pair now = qu.poll();

            for (int i=0;i<4;i++) {
                int x = now.x + dx[i];
                int y = now.y + dy[i];

                if (x == -1 || x == a || y == -1 || y==b) continue;
                if (board[x][y] >= board[now.x][now.y]) continue;

                if (!visit[x][y]) {
                    visit[x][y] = true;
                    qu.offer(new Pair(x,y));
                }
                load.get(encode(now.x, now.y)).add(encode(x, y));
                indegree[encode(x,y)]++;
            }

        }

        int[] dp = new int[a*b+1];

        dp[0] = 1;
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);

        while(!q.isEmpty()) {
            int n = q.poll();

            for (int go : load.get(n)) {
                dp[go] += dp[n];
                if (--indegree[go] == 0) {
                    q.offer(go);
                }
            }
        }

        System.out.println(dp[a*b-1]);
    }
    public static int encode(int x, int y) {
        return x * N + y;
    }
    public static int N;
    public static ArrayList<ArrayList<Integer>> load;
    public static int[] dx = {0,0,-1,1}, dy = {1,-1,0,0};
    public static int[][] board;
}