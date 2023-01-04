import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
}

class Line {
    Point p1, p2;
    boolean vertical, horizon;
    Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;

        if (p1.x == p2.x) vertical = true;
        if (p1.y == p2.y) horizon = true;
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

class Square {
    Point[] points = new Point[4];
    Line[] lines = new Line[4];

    Square (Point a, Point b) {
        points[0] = a;
        points[1] = b;

        points[2] = new Point(a.x, b.y);
        points[3] = new Point(b.x, a.y);

        if (Line.sub(lines[0] = new Line(points[0], points[2]), Point.origin)) Main.flag = true;
        if (Line.sub(lines[1] = new Line(points[0], points[3]), Point.origin)) Main.flag = true;
        if (Line.sub(lines[2] = new Line(points[1], points[2]), Point.origin)) Main.flag = true;
        if (Line.sub(lines[3] = new Line(points[1], points[3]), Point.origin)) Main.flag = true;
    }

    public static boolean isCross (Square s1, Square s2) {
        for (int i=0;i<4;i++) {
            for (int j=0;j<4;j++) {
                if (Line.isCross(s1.lines[i], s2.lines[j])) {
                    return true;
                }
            }

        }

        return false;
    }
}

public class Main {
    public static int[] parent;
    public static boolean flag;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        parent = new int[N+1];
        for (int i=0;i<parent.length;i++) parent[i] = i;

        Square[] sq = new Square[N];
        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken()), y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken()), y2 = Integer.parseInt(st.nextToken());
            sq[i] = new Square(new Point(x1, y1), new Point(x2, y2));
        }

        int ans = N;
        if (flag) ans--;

        for (int i=0;i<N;i++) {
            for (int j=i+1;j<N;j++) {
                if (find(i) != find(j) && Square.isCross(sq[i], sq[j])) {
                    union(i, j);

                    ans--;
                }
            }
        }

        System.out.println(ans);
    }

    public static int find (int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }

    public static void union (int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        parent[p1] = p2;
    }
}