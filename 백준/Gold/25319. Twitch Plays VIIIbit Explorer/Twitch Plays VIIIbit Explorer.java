import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair {
    int x, y;

    Pair (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        int nameL = Integer.parseInt(st.nextToken());

        ary = new char[a][];
        for (int i=0;i<a;i++)
            ary[i] = br.readLine().toCharArray();

        int[] have = new int[26];
        for (int i=0;i<a;i++) {
            for (int j=0;j<b;j++) {
                have[ary[i][j]-'a']++;
            }
        }

        char[] name = br.readLine().toCharArray();
        int[] need = new int[26];
        for (char e : name) {
            need[e-'a']++;
        }

        Pair now = new Pair(0,0);
        int itemPick=0;
        StringBuilder sb = new StringBuilder();
        int result = 0;

        boolean check = true;

        K: while(true) {
            if (check) {
                check = false;
                for (int i=0;i<26;i++) {
                    if (have[i]>=need[i]) {
                        have[i]-=need[i];
                    } else {
                        break K;
                    }
                }
                result++;
            }


            Pair goal = search(now, name[itemPick]);
            go(now, goal, sb);
            sb.append('P');
            now = goal;

            if (++itemPick==name.length) {
                itemPick=0;
                check = true;
            }
        }

        go(now, new Pair(a-1,b-1), sb);

        System.out.println(result+" "+sb.toString().length());
        System.out.println(sb);
    }

    public static int[] dx = {0,0,-1,1} , dy = {1,-1,0,0};
    public static int a,b;
    public static char[][] ary;

    public static Pair search (Pair nows, char s) {
        if (ary[nows.x][nows.y]==s) {
            ary[nows.x][nows.y] = '*';
            return nows;
        }

        Queue<Pair> qu = new LinkedList<>();
        qu.offer(new Pair(nows.x,nows.y));
        boolean[][] visited = new boolean[a][b];
        visited[nows.x][nows.y] = true;

        while(!qu.isEmpty()) {
            Pair now = qu.poll();

            for (int i=0;i<4;i++) {
                int ddx = now.x + dx[i];
                int ddy = now.y + dy[i];

                if (ddx==-1||ddx==a||ddy==-1||ddy==b) continue;
                if (ary[ddx][ddy]==s) {
                    ary[ddx][ddy] = '*';
                    return new Pair(ddx, ddy);
                }

                if (!visited[ddx][ddy]) {
                    visited[ddx][ddy] = true;
                    qu.offer(new Pair(ddx, ddy));
                }
            }
        }

        return new Pair(-1,-1);
    }

    public static void go (Pair from, Pair go, StringBuilder sb) {
        while(from.x > go.x) {
            from.x--;
            sb.append('U');
        }
        while(from.x < go.x) {
            from.x++;
            sb.append('D');
        }
        while(from.y < go.y) {
            from.y++;
            sb.append('R');
        }
        while(from.y > go.y) {
            from.y--;
            sb.append('L');
        }
    }
}