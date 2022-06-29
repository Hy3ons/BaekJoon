import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

//4분컷

class Pair {
    int x, y;
    Pair (int x, int y) {
        this.x= x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken())
                , b = Integer.parseInt(st.nextToken());

        int[][] ary1 = new int[a][b]
                , ary2 = new int[a][b];
        boolean[][] visited = new boolean[a][b];

        for (int i=0;i<a;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<b;j++) {
                ary1[i][j]= Integer.parseInt(st.nextToken());

            }
        }
        for (int i=0;i<a;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<b;j++) {
                ary2[i][j]= Integer.parseInt(st.nextToken());

            }
        }
        ArrayList<Pair> al = new ArrayList<>();

        for (int i=0;i<a;i++) {
            for (int j=0;j<b;j++) {
                if (ary1[i][j]!=ary2[i][j]) {
                    al.add(new Pair(i,j));
                }
            }
        }

        if (al.size()==0) {
            System.out.println("YES");
            return;
        }

        Queue<Pair> qu = new LinkedList<>();
        qu.offer(al.get(0));
        visited[al.get(0).x][al.get(0).y] = true;
        int standard = ary1[al.get(0).x][al.get(0).y];
        int s2 = ary2[al.get(0).x][al.get(0).y];

        while(!qu.isEmpty()) {
            Pair now = qu.poll();

            for (int i=0;i<4;i++) {
                int x = now.x + dx[i];
                int y = now.y + dy[i];

                if (x==-1||y==-1||x==a||y==b) continue;
                if (standard!=ary1[x][y]) continue;
                
                if (s2!=ary2[x][y]) {
                    System.out.println("NO");
                    return;
                }

                if (!visited[x][y]) {
                    visited[x][y]= true;
                    qu.offer(new Pair(x,y));
                }
            }

        }

        for (Pair lc : al) {
            if (!visited[lc.x][lc.y]) {
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");

    }

    public static int[] dx = {1,-1,0,0} , dy = {0,0,-1,1};
}
