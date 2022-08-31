import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Point {
    int x, y;
    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        final int N = Integer.parseInt(st.nextToken()), L = Integer.parseInt(st.nextToken());
        ArrayList<Point> al = new ArrayList<Point>(N+1);

        for (int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            al.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        Collections.sort(al, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.x == o2.x) return Integer.compare(o1.y, o2.y);
                return Integer.compare(o1.x, o2.x);
            }
        });

        Point central = al.get(0);
        al.remove(0);

        double result = 2.0 * Math.PI * L;

        Collections.sort(al, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                int c = CCW(central, o1, o2);
                if (c == 0) {
                    if (o1.y == o2.y) {
                        return Integer.compare(o1.x, o2.x);
                    } else
                        return Integer.compare(o2.y, o1.y);
                } else
                    return c;
            }
        });

        //case 처리를 아직 하지 않았다.

        Stack<Point> s = new Stack<>();
        s.push(central);
        s.push(al.get(0));

        for (int i=1;i<al.size();i++) {
            s.push(al.get(i));
            while(s.size()>2) {
                Point c = s.pop(), b = s.pop(), a = s.peek();
                int ccw = CCW(a, b, c);
                if (ccw < 0) {
                    s.push(b);
                    s.push(c);
                    break;
                }
                s.push(c);
            }
        }
        Point b = s.pop();
        if (CCW(s.peek(), b, central) < 0)
            s.push(b);

        if (s.size() < 3) {
            long temp = 0;
            for (Point p : al) {
                temp = Math.max(temp, dist(central, p));
            }

            result += Math.sqrt(temp) * 2;
            System.out.println(Math.round(result));
            return;
        }

        al.clear();
        al.add(central);
        while(!s.isEmpty()) al.add(s.pop());
        al.add(central);

        for (int i=0;i<al.size()-1;i++) {
            result += Math.sqrt(dist(al.get(i), al.get(i+1)));
        }
        System.out.println(Math.round(result));


    }

    public static int CCW (Point a, Point b, Point c) {
        // 반시계일때 -1을 리턴한다.
        int r = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
        return Integer.compare(r, 0);
    }

    public static int dist (Point a, Point b) {
        int x = a.x - b.x, y = a.y - b.y;
        return x * x + y * y;
    }
}