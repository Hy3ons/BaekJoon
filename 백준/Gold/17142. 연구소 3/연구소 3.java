import java.io.*;
import java.util.*;

class Pair {
    int x, y;
    Pair (int x, int y) {
        this.x=x;this.y=y;
    }
}

public class Main {
    public static ArrayList<Pair> virus = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        visit = new boolean[N][N];

        int zero = 0;

        for (int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<N;j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                if (board[i][j] ==0) zero++;

                if (board[i][j] == 2) {
                    virus.add(new Pair(i, j));
                }

            }
        }

        goal = zero;
        if (k >= virus.size()) {
            //all virus select code
            k = virus.size();
            for (int i=0;i<virus.size();i++)
                memorize[i] = i;
            simulate();
        } else {
            getVirus(0, 0);
        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    public static int[][] board;
    public static int N, k, answer = Integer.MAX_VALUE, goal;
    public static int[] memorize = new int[20];

    public static void getVirus(int depth, int start) {
        if (depth == k) {
            simulate();
            return;
        }

        for (int i=start;i<virus.size();i++) {
            memorize[depth] = i;
            getVirus(depth+1, i+1);
        }
    }

    public static Queue<Pair> qu = new LinkedList<>();
    public static boolean[][] visit;
    public static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public static void simulate () {
        for (boolean[] v : visit) Arrays.fill(v, false);
        qu.clear();

        int time = 0, infect = 0;

        for (int i=0;i<k;i++) {
            int idx = memorize[i];
            visit[virus.get(idx).x][virus.get(idx).y] = true;
            qu.offer(virus.get(idx));
        }

        while(!qu.isEmpty() && infect != goal) {
            for (int i = qu.size();i>0;i--) {
                Pair now = qu.poll();

                for (int j=0;j<4;j++) {
                    int x = now.x + dx[j];
                    int y = now.y + dy[j];

                    if (x == -1 || x == N || y == -1 || y == N) continue;
                    if (visit[x][y] || board[x][y] == 1) continue;

                    visit[x][y] = true;
                    qu.offer(new Pair(x, y));
                    if (board[x][y] != 2)
                        infect++;
                }
            }
            time++;
        }

        if (infect == goal)
            answer = Math.min(answer, time);
    }
}