import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

class Point {
    long x, y;
    Point (long x, long y) {
        this.x=x;this.y=y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int percentage = Integer.parseInt(br.readLine());
        int goal = N*percentage;
        if (goal % 100 == 0) {
            goal /= 100;
        } else {
            goal /= 100;
            goal++;
        }

        if (N <= 2) {
            System.out.println("possible");
            return;
        }


        ArrayList<Point> al = new ArrayList<>();

        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
            al.add(new Point(x, y));
        }

        Random ran = new Random();

        for (int i=0;i<500;i++) {
            int a = ran.nextInt(N), b = ran.nextInt(N);
            if (a == b) continue;
            int cross = 2;

            for (int j=0;j<N;j++) {
                if (j == a || j == b) continue;

                if (ccw(al.get(a), al.get(b), al.get(j)) == 0){
                    cross++;
                }
            }

            if (goal <= cross) {
                System.out.println("possible");
                return;
            }
        }
        System.out.println("impossible");




    }

    public static long ccw (Point a, Point b, Point c) {
        return (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y-a.y);
    }
}