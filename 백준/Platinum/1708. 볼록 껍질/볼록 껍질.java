import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;

class Point {
    int x, y;
    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
//        System.out.println(CCW(new Point(-1,1), new Point(1, 1), new Point(1, 100)));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Point[] points = new Point[N];

        for (int i=0;i<points.length;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
            points[i] = new Point(x, y);
        }

        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.y == o2.y) return Integer.compare(o1.x, o2.x);
                return Integer.compare(o1.y, o2.y);
            }
        });

        Point s = points[0];
        points = Arrays.copyOfRange(points, 1, points.length);

        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                int c = CCW(s, o1, o2);
                if (c > 0) {
                    return -1;
                } else if (c == 0) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        Stack<Point> st = new Stack<>();
        st.push(s);
        st.push(points[0]);
        for (int i=1;i<points.length;i++) {
            st.push(points[i]);
            while(st.size() > 2) {
                Point c = st.pop(), b = st.pop(), a = st.pop();
                if (CCW(a, b, c) <= 0) {
                    st.push(a);
                    st.push(c);
                } else {
                    st.push(a);
                    st.push(b);
                    st.push(c);
                    break;
                }
            }
        }
        Point b = st.pop(), a = st.pop(), c = s;
        if (CCW(a, b, c) <= 0) {
            st.push(a);
        } else {
            st.push(a);
            st.push(b);
        }
        System.out.println(st.size());
    }

    public static int CCW (Point a, Point b, Point c) {
        long x = ((long) c.x - a.x) * (b.y - a.y) - ((long) b.x - a.x) * (c.y - a.y);
        return Long.compare(0, x);
        //반시계가 양수이다.
    }
}