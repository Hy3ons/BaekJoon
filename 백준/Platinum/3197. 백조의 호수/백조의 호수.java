import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class loc {
    int x, y;
    loc (int x, int y){
        this.x= x;
        this.y = y;
    }
}

public class Main {

    public static int mki (String a) { return Integer.parseInt(a); }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");

        a = mki(st.nextToken());
        b = mki(st.nextToken());

        graph = new char[a][];
        noQu = new boolean[a][b];
        noRecheck = new boolean[a][b];

        for (int i=0;i<a;i++) {
            graph[i] = br.readLine().toCharArray();
        }

        baekJo = new loc[2];
        int count = 0;
        ArrayList<loc> al = new ArrayList<>();


        for (int i=0;i<a;i++) {
            for (int j=0;j<b;j++) {
                if (graph[i][j]=='L') baekJo[count++] = new loc(i,j);

                if (graph[i][j]=='X') {
                    if (firstMeltCheck(i,j)) {
                        al.add(new loc(i,j));
                    }
                }
            }
        }

        count = 0;

        while(true) {
            if (count==0) {
                if (canMeet(baekJo[0].x, baekJo[0].y)) {
                    System.out.println(count);
                    return;
                }
            } else {

                int q = nextCheckQu.size();

                for (int i=0;i<q;i++) {
                    loc lc = nextCheckQu.poll();
                    if (canMeet(lc.x , lc.y)) {
                        System.out.println(count);
                        return;
                    }
                }

            }



            if (count==0) for (int i=0;i<al.size();i++) nextIce.offer(al.get(i));

            melting();
            count++;
        }

    }
    public static loc[] baekJo;
    public static char[][] graph;
    public static int a,b;
    public static int[] dx = {0,0,-1,1}, dy = {1,-1,0,0};
    public static Queue<loc> nextIce = new LinkedList<>();
    public static Queue<loc> qu = new LinkedList<>();
    public static Queue<loc> qqq = new LinkedList<>();
    public static Queue<loc> nextCheckQu = new LinkedList<>();
    public static boolean[][] noQu, noRecheck;


    public static void melting () {

        int q = nextIce.size();

        for (int i=0;i<q;i++) {
            loc lc = nextIce.poll();

            for (int j=0;j<4;j++) {
                int x = lc.x + dx[j];
                int y = lc.y + dy[j];

                if (x==-1||x==a||y==-1||y==b) continue;

                if (graph[x][y]=='X'||graph[x][y]=='L') {
                    if (!noQu[x][y]) {
                        noQu[x][y] = true;
                        nextIce.offer(new loc(x,y));
                    }
                }
            }

            qu.offer(new loc(lc.x, lc.y));

        }

        while (!qu.isEmpty()) {
            loc lc = qu.poll();
            graph[lc.x][lc.y] = '.';
        }
    }

    public static boolean firstMeltCheck (int x, int y) {

        for (int i=0;i<4;i++) {
            int ddx = x + dx[i];
            int ddy = y + dy[i];

            if (ddx==-1||ddx==a||ddy==-1||ddy==b) continue;

            if (graph[ddx][ddy]=='.'||graph[ddx][ddy]=='L') {
                return true;
            }
        }
        return false;
    }

    public static boolean canMeet (int ax, int ay) {

        qqq.offer(new loc(ax, ay));
        noRecheck[ax][ay] = true;

        while(!qqq.isEmpty()) {
            loc lc = qqq.poll();

            for (int i=0;i<4;i++) {
                int x = lc.x + dx[i];
                int y = lc.y + dy[i];

                if (x==-1||x==a||y==-1||y==b) continue;

                if (x==baekJo[1].x&&y==baekJo[1].y) return true;

                if (graph[x][y]=='.'&&!noRecheck[x][y]) {
                    noRecheck[x][y] = true;
                    qqq.offer(new loc(x,y));
                }
                if (graph[x][y]=='X'&&!noRecheck[x][y]) {
                    noRecheck[x][y] = true;
                    nextCheckQu.offer(new loc(x,y));
                }
            }
        }

        return false;
    }

}
