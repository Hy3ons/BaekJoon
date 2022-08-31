import java.io.*;
import java.util.*;

class Point {
    long x,y;
    Point (long x, long y) {
        this.x= x;
        this.y =y;
    }
    public String toString() {
        return x +" " + y;
    }
    Point getReverse() {
        return new Point(-x, -y);
    }
    Long getDist () {
        return x * x + y * y;
    }
    public static Point getVector(Point a, Point b) {
        return new Point(b.x - a.x, b.y - a.y);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

//        int testcase = Integer.parseInt(br.readLine());
        int testcase = 1;
        while(testcase-->0) {
            int N = Integer.parseInt(br.readLine());
            Point[] p = new Point[N];
            for (int i=0;i<N;i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
                p[i] = new Point(x, y);
            }

            if (N < 3) {
                bw.write(p[0]+" "+p[1]+"\n");
                continue;
            }

            Arrays.sort(p, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.x == o2.x) return Long.compare(o1.y, o2.y);
                    return Long.compare(o1.x, o2.x);
                }
            });

            Point cent = p[0];
            p = Arrays.copyOfRange(p, 1, p.length);


            Arrays.sort(p, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    return CCW(cent, o1, o2);
                }
            });

            Stack<Point> st = new Stack<>();
            st.push(cent);
            st.push(p[0]);
            for (int i=1;i<p.length;i++) {
                st.push(p[i]);
                while(st.size() > 2) {
                    Point c = st.pop(), b = st.pop(), a = st.peek();

                    if (CCW(a, b, c) < 0) {
                        st.push(b);
                        st.push(c);
                        break;
                    }
                    st.push(c);
                }
            }
            Point b = st.pop();
            if (CCW(st.peek(), b, cent) < 0)
                st.push(b);

            System.out.println(st.size());
            if (true) return;

            if (st.size() < 3) {
                bw.write(cent+" "+p[p.length-1]+"\n");
                continue;
            }


            p = new Point[st.size()];
            for (int i=0;i<p.length;i++)
                p[i] = st.pop();

            Point vector = new Point(0, 1);
            int l = 0, r = 0;
            final int K = p.length;

            for (int i=0;i<K;i++) {
                if (p[l].x > p[i].x) l = i;
                if (p[r].x < p[i].x) r = i;
            }

            long temp = getDist(p[l], p[r]);
            Point ans = new Point(l, r);

            for (int i=0;i<K;i++) {
                double leftAngle = getTheta(vector, Point.getVector(p[(l + 1) % K], p[l]));
                double rightAngle = getTheta(vector, Point.getVector(p[r], p[(r + 1) % K]));

                if (leftAngle < rightAngle) {
                    vector = Point.getVector(p[(l + 1) % K], p[l]);
                    l = (l + 1) % K;
                } else {
                    vector = Point.getVector(p[r], p[(r + 1) % K]);
                    r = (r + 1) % K;
                }

                if (temp < getDist(p[l], p[r])) {
                    temp = getDist(p[l], p[r]);
                    ans.x = l;
                    ans.y = r;
                }
            }

            bw.write(p[(int)ans.x]+" "+p[(int)ans.y]+"\n");
        }
        bw.flush();
    }
    public static long getDist (Point a, Point b) {
        return pow(a.x - b.x) + pow(a.y - b.y);
    }
    public static long pow (long a) {
        return a*a;
    }
    public static double getTheta (Point calipers, Point vector) {
        return Math.acos((calipers.x * vector.x + calipers.y * vector.y)
                / (Math.sqrt(calipers.getDist() * vector.getDist())));
    }

    public static int CCW (Point a, Point b, Point c) {
        long x = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
        return Long.compare(x, 0);
    }
}