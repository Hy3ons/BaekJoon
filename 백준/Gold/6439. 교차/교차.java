import java.io.*;
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

class Square {
    Point[] points = new Point[4];
    Line[] lines = new Line[4];
    long minX, maxX, minY, maxY;

    Square (Point p1, Point p2) {

        points[0] = p1;
        points[2] = p2;
        points[1] = new Point(p1.x, p2.y);
        points[3] = new Point(p2.x, p1.y);

        minX = Math.min(p1.x, p2.x);
        maxX = Math.max(p1.x, p2.x);
        minY = Math.min(p1.y, p2.y);
        maxY = Math.max(p1.y, p2.y);

        lines[0] = new Line(points[0], points[1]);
        lines[1] = new Line(points[0], points[3]);
        lines[2] = new Line(points[2], points[1]);
        lines[3] = new Line(points[2], points[3]);
    }

}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        C: while(testcase-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
            int xx = Integer.parseInt(st.nextToken()), yy = Integer.parseInt(st.nextToken() );

            Line line = new Line(new Point(x, y), new Point(xx, yy));

            x = Integer.parseInt(st.nextToken()); y = Integer.parseInt(st.nextToken());
            xx = Integer.parseInt(st.nextToken()); yy = Integer.parseInt(st.nextToken() );

            Square sq = new Square(new Point(x, y), new Point(xx, yy));

            for (int i=0;i<4;i++) {
                if (isCross(sq.lines[i], line)) {
                    bw.write("T\n");
                    continue C;
                }
            }

            if (c(sq, line.p1) || c(sq, line.p2)) {
                bw.write("T\n");
            } else {
                bw.write("F\n");
            }
        }
        bw.flush();


    }

    public static boolean c (Square sq, Point p) {
        return sq.minX <= p.x && p.x <= sq.maxX && sq.minY <= p.y && p.y <= sq.maxY;
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