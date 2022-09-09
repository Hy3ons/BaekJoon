import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

class Point {
    long x, y;
    Point (long x, long y) {
        this.x=x;
        this.y=y;
    }
}

class Line{
    Point p1, p2;
    long weight;
    Line(long a, long b, long c, long d, long e) {
        p1 = new Point(a,b);
        p2 = new Point(c,d);
        weight = e;
    }
}

public class Main {
    public static StringTokenizer st;
    public static int mki() {
        return Integer.parseInt(st.nextToken());
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int N = Integer.parseInt(br.readLine());
        ArrayList<Line> lines = new ArrayList<>(N);

        for (int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            lines.add(new Line(mki(), mki(), mki(), mki(), mki()));
        }

        Collections.sort(lines, new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                return Long.compare(o2.weight, o1.weight);
            }
        });
        long result = 0;

        for (int i=0;i<N;i++) {
            long cnt = 1;
            for (int j=0;j<i;j++) {
                if (cross(lines.get(i), lines.get(j))) cnt++;
            }

            result += cnt * lines.get(i).weight;
        }
        System.out.println(result);
    }

    public static boolean cross(Line l1, Line l2) {
        return CCW(l1.p1, l1.p2, l2.p1) * CCW(l1.p1, l1.p2, l2.p2) <= 0
                && CCW(l2.p1, l2.p2, l1.p1) * CCW(l2.p1, l2.p2, l1.p2) <=0;
    }

    public static int CCW (Point a, Point b, Point c) {
        long k = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
        return Long.compare(k, 0);
    }
}