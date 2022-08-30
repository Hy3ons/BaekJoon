import java.io.*;
import java.util.*;

class Point {
    int x,y;
    Point (int x, int y) {
        this.x= x;
        this.y =y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        ArrayList<Point> points = new ArrayList<>()
                , temp = new ArrayList<>();

        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
            if (st.nextToken().equals("N")) continue;
            points.add(new Point(x,y));
        }


        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.x == o2.x) return Integer.compare(o1.y , o2.y);
                return Integer.compare(o1.x, o2.x);
            }
        });

        Point s = points.get(0);
        bw.write(points.size()+"\n");
        bw.write(s.x+" "+s.y+"\n");

        for (int i=1;i<points.size();i++) temp.add(points.get(i));
        points = temp;

        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                int c = CCW(s, o1, o2);
                if (c == 0) {
                    if (o1.y == o2.y) {
                        return Integer.compare(o1.x, o2.x);
                    } else
                        return Integer.compare(o2.y, o1.y);
                } else
                    return Integer.compare(0, c);
            }
        });

        if (points.get(points.size()-1).y == s.y) {
            Collections.sort(points, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    int c = CCW(s, o1, o2);
                    if (c == 0) {
                        if (o1.y == o2.y) {
                            return Integer.compare(o2.x, o1.x);
                        } else
                            return Integer.compare(o2.y, o1.y);
                    } else
                        return Integer.compare(0, c);
                }
            });
        }

        for (Point p : points) bw.write(p.x+" " + p.y +"\n");
        bw.flush();
    }

    public static int CCW (Point a, Point b, Point c) {
        long x = ((long) c.x - a.x) * (b.y - a.y) - ((long) b.x - a.x) * (c.y - a.y);
        return Long.compare(0, x);
        //반시계가 양수이다.
    }
}