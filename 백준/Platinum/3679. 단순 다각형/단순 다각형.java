import java.io.*;
import java.util.*;

class Point {
    int x, y, idx;
    Point (int x, int y, int idx) {
        this.x= x;
        this.y = y;
        this.idx = idx;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int testcase = Integer.parseInt(br.readLine());

        while(testcase-->0) {
            st = new StringTokenizer(br.readLine());
            final int N = Integer.parseInt(st.nextToken());

            ArrayList<Point> points = new ArrayList<>(N);

            for (int i=0;i<N;i++) {
                points.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i));
            }

            points.sort(new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.x == o2.x) return Integer.compare(o1.y, o2.y);
                    return Integer.compare(o1.x, o2.x);
                }
            });

            Point standard = points.get(0);
            points.remove(0);

            points.sort(new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    int c = CCW(standard, o1, o2);
                    if (c == 0) {
                        return Integer.compare(dist(standard, o1), dist(standard, o2));
                    } else {
                        return c;
                    }
                }
            });

            bw.write(standard.idx+" ");

            if (CCW(standard, points.get(points.size()-1), points.get(points.size()-2)) == 0) {
                Stack<Point> ss = new Stack<>();

                for (int i=0;i<points.size()-1;i++) {
                    if (CCW(standard, points.get(points.size()-1), points.get(i)) == 0) {
                        ss.push(points.get(i));
                    } else {
                        bw.write(points.get(i).idx +" ");
                    }
                }

                ss.push(points.get(points.size()-1));
                while(!ss.isEmpty()) {
                    bw.write(ss.pop().idx+" ");
                }
            } else {
                for (Point point : points) bw.write(point.idx+" ");
            }

            bw.write('\n');
        }
        bw.flush();
    }

    public static int CCW (Point a, Point b, Point c) {
        // return -1 when a -> b -> c is an deClock
        int g = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
        return Integer.compare(g, 0);
    }

    public static int dist (Point a, Point b) {
        int x = a.x - b.x, y = a.y - b.y;
        return x*x + y*y;
    }
}