import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static int N, visit = 1;
    public static boolean[][] board;
    public static ArrayList<String> ans = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());

        String input = br.readLine();
        board = new boolean[N+1][N+1];

        if (N == 2 || N == 3) {
            bw.write(-1+"");
            bw.flush();
            return;
        }

        StringTokenizer st = new StringTokenizer(input);
        int sx = Integer.parseInt(st.nextToken()), sy = Integer.parseInt(st.nextToken());

        board[sx][sy] = true;
        ans.add(sx +" "+sy);

        int ret = 1;

        while(visit != N*N) {
            int idx = -1;
            int temp = 10;

            for (int i=0;i<8;i++) {
                int ddx = dx[i] + sx;
                int ddy = dy[i] + sy;

                if (ddx < 1 || ddy < 1 || ddy > N || ddx > N) continue;

                if (!board[ddx][ddy]) {
                    int s = check(ddx, ddy);

                    if (s < temp) {
                        temp = s;
                        idx = i;
                    }
                }
            }

            if (idx == -1) {
                String s = ans.get(ans.size()-1-ret);
                ret+= 2;
                st = new StringTokenizer(s);
                sx = Integer.parseInt(st.nextToken());
                sy = Integer.parseInt(st.nextToken());
                ans.add(sx+" "+sy);
                continue;
            }

            sx += dx[idx];
            sy += dy[idx];

            board[sx][sy] = true;
            visit++;
            ans.add(sx+" "+sy);
            ret = 1;
        }

        bw.write(ans.size()+"\n");
        for (String s : ans) bw.write(s+"\n");
        bw.flush();
    }
    public static int[] dx = {-1, -2, -2, -1, 1, 2, 2, 1}, dy = {-2, -1, 1, 2, 2, 1, -1, -2};

    public static int check (int x, int y) {
        int res = 0;
        for (int i=0;i<8;i++) {
            int ddx = dx[i] + x;
            int ddy = dy[i] + y;

            if (ddx < 1 || ddy < 1 || ddy > N || ddx > N) continue;

            if (!board[ddx][ddy]) res++;
        }

        return res;
    }
}