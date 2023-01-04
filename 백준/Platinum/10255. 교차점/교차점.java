import java.io.*;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

class Point {
    public static Point origin = new Point(0, 0);
    long x, y;
    Point (long x, long y) {
        this.x= x;
        this.y = y;
    }

    public boolean equals (Point o) {
        return x == o.x && y == o.y;
    }
}

class Line {
    Point p1, p2;
    boolean vertical, horizon;
    long xMax, xMin, yMax, yMin;

    public boolean equal (Line o) {
        return (isSame(p1, o.p1) && isSame(p2, o.p2)) || (isSame(p1, o.p2) && isSame(p2, o.p1));
    }

    Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;

        xMax = Math.max(p1.x, p2.x);
        xMin = Math.min(p1.x, p2.x);
        yMax = Math.max(p1.y, p2.y);
        yMin = Math.min(p1.y, p2.y);

        if (p1.x == p2.x) vertical = true;
        if (p1.y == p2.y) horizon = true;

        if (p1.x > p2.x || (p1.x == p2.x && p1.y > p2.y)) {
            Point temp = p1;
            p1 = p2;
            p2 = temp;
        }
    }

    public static boolean sssub (Line l1, Point p) {
        return !l1.p1.equals(p) && !l1.p2.equals(p) && Math.min(l1.p1.x, l1.p2.x) <= p.x
                && p.x <= Math.max(l1.p1.x, l1.p2.x)
                && Math.min(l1.p1.y, l1.p2.y) <= p.y
                && p.y <= Math.max(l1.p1.y, l1.p2.y);
    }

    public static boolean isCross (Line l1, Line l2) throws Exception {
        if (l1.equal(l2)) throw new Exception();

        if (CCW(l1.p1, l1.p2, l2.p1) == 0 && CCW(l1.p1, l1.p2, l2.p2) == 0) {
            //평행할때

            if (between(l1, l2) || between(l2, l1)) {
                if (sub(l1, l2.p1) && sub(l1, l2.p2)) throw new Exception();
                if (sub(l2, l1.p1) && sub(l2, l1.p2)) throw new Exception();

                if (sssub(l1, l2.p1) || sssub(l1, l2.p2) || sssub(l2, l1.p1) || sssub(l2, l1.p2))
                    throw new Exception();
            }


        }

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

class Square {
    Point[] points = new Point[4];
    Line[] lines = new Line[4];

    Square (Point a, Point b) {
        points[0] = a;
        points[1] = b;

        points[2] = new Point(a.x, b.y);
        points[3] = new Point(b.x, a.y);

        lines[0] = new Line(points[0], points[2]);
        lines[1] = new Line(points[0], points[3]);
        lines[2] = new Line(points[1], points[2]);
        lines[3] = new Line(points[1], points[3]);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        while(T-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken()), y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken()), y2 = Integer.parseInt(st.nextToken());

            Square sq = new Square(new Point(x1, y1), new Point(x2, y2));

            st = new StringTokenizer(br.readLine());
            x1 = Integer.parseInt(st.nextToken()); y1 = Integer.parseInt(st.nextToken());
            x2 = Integer.parseInt(st.nextToken()); y2 = Integer.parseInt(st.nextToken());

            Line line = new Line(new Point(x1, y1), new Point(x2, y2));

            int ans = 0;

            try {
                for (int i=0;i<4;i++) {
                    if (Line.isCross(line, sq.lines[i])) ans++;
                }

                for (int i=0;i<4;i++) {
                    if (Line.sub(line, sq.points[i])) ans--;
                }
                bw.write(ans+"\n");
            } catch (Exception ex) {
                bw.write("4\n");
            }
        }
        bw.flush();


    }

}