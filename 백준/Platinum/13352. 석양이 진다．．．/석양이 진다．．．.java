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
        
        if (N <= 4) {
            System.out.println("success");
            return;
        }

        Point[] points = new Point[N];
        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()), y= Integer.parseInt(st.nextToken());

            points[i] = new Point(x, y);
        }

        Random ran = new Random();

        C: for (int k=0;k<150;k++) {
            //무작위로 2명 뽑고 그 일렬로 쏜다음에, 나머지 살아남은 인원이 모두 일직선에 일치하는지 확인
            //이거 25% 정도로 되는 거 같음.

            int a = ran.nextInt(N), b = ran.nextInt(N);
            if (a == b) continue;

            ArrayList<Point> live = new ArrayList<>();
            for (int i=0;i<N;i++) {
                if (i == a || i == b) continue;
                if (ccw(points[a], points[b], points[i]) != 0) live.add(points[i]);
            }

            if (live.size() < 2) {
                System.out.println("success");
                return;
            } else {
                for (int i=2;i<live.size();i++) {
                    if (ccw(live.get(0), live.get(1), live.get(i)) != 0) {
                        continue C;
                    }
                }

                System.out.println("success");
                return;
            }
        }

        System.out.println("failure");


    }

    public static long ccw (Point a, Point b, Point c) {
        return (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y-a.y);
    }
}