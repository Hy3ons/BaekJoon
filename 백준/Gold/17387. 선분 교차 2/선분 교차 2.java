import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Point {
    long x, y;
    Point (long x, long y) {
        this.x= x;
        this.y = y;
    }
}

class Line {
    Point p1, p2;
    Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Line l1 = new Line(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))
                , new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));

        st = new StringTokenizer(br.readLine());

        Line l2 = new Line(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))
                , new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));

        System.out.println(isCross(l1, l2) ? 1 : 0);
    }

    public static boolean isCross (Line l1, Line l2) {
        if (isSame(l1.p1, l2.p1)) return true;
        if (isSame(l1.p1, l2.p2)) return true;
        if (isSame(l1.p2, l2.p1)) return true;
        if (isSame(l1.p2, l2.p2)) return true;

        if (between(l1, l2)) return true;
        if (between(l2, l1)) return true;

        if (CCW(l1.p1, l1.p2, l2.p1) == 0 && CCW(l1.p1, l1.p2, l2.p2) == 0) return false;

        if (CCW(l1.p1, l1.p2, l2.p1) * CCW(l1.p1, l1.p2, l2.p2) <= 0
                && CCW(l2.p1, l2.p2, l1.p1) * CCW(l2.p1, l2.p2, l1.p2) <= 0) return true;

        return false;
    }

    public static boolean isSame(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    public static int CCW (Point a, Point b, Point c) {
        long cww = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
        return Long.compare(cww, 0);
    }

    public static boolean between(Line l1, Line l2) {
        if (sub(l1, l2.p1)) return true;
        if (sub(l1, l2.p2)) return true;

        return false;
    }
    public static boolean sub (Line l1, Point p) {
        return CCW(l1.p1, l1.p2, p) == 0 && Math.min(l1.p1.x, l1.p2.x) <= p.x
                && p.x <= Math.max(l1.p1.x, l1.p2.x)
                && Math.min(l1.p1.y, l1.p2.y) <= p.y
                && p.y <= Math.max(l1.p1.y, l1.p2.y);
    }
}