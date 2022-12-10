import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Game {
    int redX, redY, blueX, blueY, prevDirected;
    Game (int redX, int redY, int blueX, int blueY) {
        this(redX, redY, blueX, blueY, -1);
    }
    Game (int redX, int redY, int blueX, int blueY, int prevDirected) {
        this.redX = redX;
        this.redY = redY;
        this.blueX = blueX;
        this.blueY = blueY;
        this.prevDirected = prevDirected;
    }
}

public class Main {
    public static int a,b, result;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        ary = new char[a][];
        for (int i=0;i<a;i++) ary[i] = br.readLine().toCharArray();

        int redX = 0, redY = 0, blueX = 0, blueY = 0;

        for (int i=0;i<a;i++) {
            for (int j=0;j<b;j++) {
                if (ary[i][j]=='R') {
                    ary[i][j] = '.';
                    redX = i;
                    redY = j;
                }
                if (ary[i][j]=='B') {
                    ary[i][j] = '.';
                    blueX = i;
                    blueY = j;
                }
            }
        }

        check.add(new Game(redX, redY, blueX, blueY));
        qu.offer(check.get(0));

        while(!qu.isEmpty()) {
            int quSize = qu.size();
            result++;

            for (int j=0;j<quSize;j++) {
                Game gm = qu.poll();

                for (int i=0;i<4;i++) {
                    if (gm.prevDirected==i) continue;

                    if (gm.prevDirected!=-1) {
                        if (gm.prevDirected<=1) {
                            if (gm.prevDirected+2==i) continue;
                        } else {
                            if (gm.prevDirected-2==i) continue;
                        }
                    }

                    boolean end = roll(i, gm);

                    if (end) end();
                }
            }
            if (result==10) break;

        }

        System.out.println(0);
    }
    public static char[][] ary;
    public static Queue<Game> qu = new LinkedList<>();
    public static int[] dx = {-1,0,1,0} , dy = {0,1,0,-1};
    public static ArrayList<Game> check = new ArrayList<>();

    public static boolean roll (int d, Game gm) {
        int redX = gm.redX, redY = gm.redY, blueX = gm.blueX, blueY = gm.blueY;

        boolean redFinish = false;
        boolean BlueFinish = false;

        boolean recon = true;
        while (recon) {
            recon = false;
            if (!BlueFinish&&ary[blueX+dx[d]][blueY+dy[d]]!='#'&&!(blueX+dx[d]==redX&&blueY+dy[d]==redY)) {
                if (ary[blueX+dx[d]][blueY+dy[d]]=='O') {
                    blueX = blueY = -1;
                    BlueFinish = true;
                } else {
                    blueX += dx[d];
                    blueY += dy[d];
                }
                recon = true;
            }
            if (!redFinish&&ary[redX+dx[d]][redY+dy[d]]!='#'&&!(redX+dx[d]==blueX&&redY+dy[d]==blueY)) {
                if (ary[redX+dx[d]][redY+dy[d]]=='O') {
                    redFinish = true;
                    redX = redY = -1;
                } else {
                    redX += dx[d];
                    redY += dy[d];
                }
                recon = true;
            }
        }

        if (redFinish||BlueFinish) {

            if (redFinish&&!BlueFinish) {
                return true;
            }

            return false;
        }

        if (gm.redX!=redX||gm.redY!=redY||gm.blueX!=blueX||gm.blueY!=blueY) {

            for (int i=0;i<check.size();i++) {
                Game gl = check.get(i);
                if (gl.blueX==blueX&&gl.blueY==blueY&&gl.redY==redY&&gl.redX==redX) {
                    return false;
                }
            }


            check.add(new Game(redX, redY, blueX, blueY, d));
            qu.offer(check.get(check.size()-1));
        }

        return false;
    }
    public static void end () {
        System.out.println(1);

        System.exit(0);
    }

}
