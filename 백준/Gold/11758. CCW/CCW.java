import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {
    static class Point {
        int x, y;

        Point (String s) {
            StringTokenizer st = new StringTokenizer(s);
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Point[] p = new Point[3];
        for (int i=0;i<3;i++) p[i] = new Point(br.readLine());

        System.out.println(CCW(p));
    }
    public static int CCW (Point[] p) {
        int x = (p[2].x - p[0].x) * (p[1].y - p[0].y) - (p[1].x - p[0].x) * (p[2].y - p[0].y);
        return -Integer.compare(x, 0);
    }
}
