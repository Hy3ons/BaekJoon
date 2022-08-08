import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static int[] dx = {-1,0,1}, dy = {1,1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        board = new char[a][];
        for (int i=0;i<a;i++) board[i] = br.readLine().toCharArray();

        visited = new boolean[a][b];
        int result = 0;

        for (int i=0;i<a;i++) {
            if (dfs(i, 0)) result++;
        }

        System.out.println(result);

    }

    public static boolean[][] visited;
    public static int a, b;
    public static char[][] board;

    public static boolean dfs(int x, int y) {
        for (int i=0;i<3;i++) {
            int ddx = x + dx[i];
            int ddy = y + dy[i];

            if (ddx == -1 || ddx == a || ddy == -1 || ddy==b) continue;
            if (board[ddx][ddy] == 'x' || visited[ddx][ddy]) continue;
            visited[ddx][ddy] = true;

            if (ddy == b-1) return true;

            if (dfs(ddx, ddy)) {
                return true;
            }
        }

        return false;
    }
}
