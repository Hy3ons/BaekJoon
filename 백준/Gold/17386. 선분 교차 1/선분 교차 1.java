import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

class Point {
    double x, y;
    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point (double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Line {
    Point p1, p2;
    double A, C;

    Line (Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;

        A = (double) (p1.y - p2.y) / (p1.x - p2.x);
        C = (double) p2.y - A * p2.x;
    }

    double function (double x) throws Exception {
        return A*x + C;
    }


}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Point a1 = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        Point a2 = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        Line line1 = new Line(a1, a2);

        st = new StringTokenizer(br.readLine());
        Point b1 = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        Point b2 = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        Line line2 = new Line(b1, b2);

        if (line1.A == line2.A ) end();

        boolean first = func(line1, line2);
        boolean second = func(line2, line1);

        if (!first||!second) end();

//        Point cross = crossing(line1, line2);

//        if (Math.min(line1.p1.x, line1.p2.x)<=cross.x
//                &&cross.x<= Math.max(line1.p1.x, line1.p2.x)
//                &&Math.min(line1.p1.y, line1.p2.y)<=cross.y
//                &&cross.y<= Math.max(line1.p1.y, line1.p2.y)) {
//
//        } else end();
//
//        if (Math.min(line2.p1.x, line2.p2.x)<=cross.x
//                &&cross.x<= Math.max(line2.p1.x, line2.p2.x)
//                &&Math.min(line2.p1.y, line2.p2.y)<=cross.y
//                &&cross.y<= Math.max(line2.p1.y, line2.p2.y)) {
//
//        } else end();

        System.out.println(1);

    }

    public static boolean func (Line line1, Line line2) {
        double z1 = (line1.p2.x-line1.p1.x)
                * (line2.p1.y - line1.p1.y) - (line1.p2.y - line1.p1.y)
                * (line2.p1.x - line1.p1.x);
        double z2 = (line1.p2.x - line1.p1.x) * (line2.p2.y - line1.p1.y)
                - (line1.p2.y - line1.p1.y) * (line2.p2.x - line1.p1.x);

        int a = 0, b = 0;
        if (z1<0) a = -1;
        else a= 1;

        if (z2<0) b= -1;
        else b = 1;

        if (a*b<0) return true;
        else return false;
    }

    public static Point crossing (Line line1, Line line2) {
        double x = (line2.C - line1.C) / (line1.A - line2.A);
        double y = -1;
        try {
            y = line1.function(x);

        } catch (Exception ex) {
            end();
        }

        return new Point(x, y);
    }

    public static void end () {
        System.out.println(0);
        System.exit(0);
    }
}
