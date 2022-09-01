import java.io.*;
import java.util.*;

class Point {
    int x, y;
    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
class Line {
    Point p1, p2;
    Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
//        System.out.println(CCW(new Point(1, 1), new Point(3, 3), new Point(2, 2)));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int testcase = Integer.parseInt(br.readLine());
        C: while(testcase-->0) {
            st = new StringTokenizer(br.readLine());
            final int B = Integer.parseInt(st.nextToken()), W = Integer.parseInt(st.nextToken());

            ArrayList<Point> blacks = new ArrayList<>(B), whites = new ArrayList<>(W);

            for (int i=0;i<B;i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
                blacks.add(new Point(x, y));
            }

            for (int i=0;i<W;i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
                whites.add(new Point(x, y));
            }
            ArrayList<Line> bl = convexHull((ArrayList<Point>) blacks.clone()), wl = convexHull((ArrayList<Point>) whites.clone());

            if (B == 1 && W == 1) {
                bw.write("YES\n");
                continue;
            }

            for (Line line : bl) {
                for (Point white : whites) {
                    if (sub__check(line.p1, line.p2, white)) {
                        bw.write("NO\n");
                        continue C;
                    }
                }
            }

            for (Line line : wl) {
                for (Point black : blacks) {
                    if (sub__check(line.p1, line.p2, black)) {
                        bw.write("NO\n");
                        continue C;
                    }
                }
            }

            /**
             * 1. 볼록 다각형이 교차 하는 관계인가.
             * 2. 볼록 다각형이 포함 관계인가.
             */

            for (Line bL : bl) {
                for (Line wL : wl) {
                    if (check(bL, wL)) {
                        bw.write("NO\n");
                        continue C;
                    }
                }
            }

            if (bl.size() == 1 && wl.size() == 1) {
                bw.write("YES\n");
                continue;
            }

            // 포함 검사하면된다.
            if (bl.size() > 1) {
                for (Point w_point : whites) {
                    int bits = 0;
                    for (Line l : bl) {
                        bits |= 1 << (CCW(l.p1, l.p2, w_point) + 1);
                    }

                    if (bits == 4) {
                        bw.write("NO\n");
                        continue C;
                    }
                }
            }

            if (wl.size() > 1) {
                for (Point b_point : blacks) {
                    int bits = 0;
                    for (Line l : wl) {
                        bits |= 1 << (CCW(l.p1, l.p2, b_point) + 1);
                    }

                    if (bits == 4) {
                        bw.write("NO\n");
                        continue C;
                    }
                }
            }

            bw.write("YES\n");
        }
        bw.flush();
    }

    public static int CCW (Point a, Point b, Point c) {
        // 반시계일때 -1을 리턴한다.
        int r = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
        return Integer.compare(r, 0);
    }

    public static boolean check(Line l1, Line l2) {
        if (sub_check(l1, l2)) return true;
        if (sub_check(l2, l1)) return true;

        if (CCW(l1.p1, l1.p2, l2.p1) == 0 && CCW(l1.p1, l1.p2, l2.p2) == 0) return false;
        if (CCW(l2.p1, l2.p2, l1.p1) == 0 && CCW(l2.p1, l2.p2, l1.p2) == 0) return false;

        if ((CCW(l1.p1, l1.p2, l2.p1) * CCW(l1.p1, l1.p2, l2.p2) <= 0) &&
                (CCW(l2.p1, l2.p2, l1.p1) * CCW(l2.p1, l2.p2, l1.p2) <= 0))
            return true;
        return false;
    }
    public static boolean sub_check (Line l1, Line l2) {
        if (sub__check(l1.p1, l1.p2, l2.p1))
            return true;

        if (sub__check(l1.p1, l1.p2, l2.p2))
            return true;

        return false;
    }
    public static boolean sub__check(Point a, Point b, Point c) {
        return CCW(a, b, c) == 0 &&
                (Math.min(a.x, b.x) <= c.x && c.x <= Math.max(a.x, b.x)) &&
                (Math.min(a.y, b.y) <= c.y && c.y <= Math.max(a.y, b.y));
    }

    public static int dist (Point a, Point b) {
        int x = a.x - b.x, y = a.y - b.y;
        return x * x + y * y;
    }

    public static ArrayList<Line> convexHull (ArrayList<Point> al) {
        ArrayList<Point> result = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();
        if (al.size() < 2) return lines;
        else if (al.size() == 2) {
            lines.add(new Line(al.get(0), al.get(1)));
            return lines;
        }

        al.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.x == o2.x) return Integer.compare(o1.y, o2.y);
                return Integer.compare(o1.x, o2.x);
            }
        });

        Point central = al.get(0);
        result.add(central);
        al.remove(0);

        al.sort(new Comparator<Point>() {
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
            Point r = null;
            for (Point p : al) {
                long g = dist(central, p);
                if (g > temp) {
                    temp = g;
                    r = p;
                }
            }

            result.add(r);
        } else {
            while(!s.isEmpty()) result.add(s.pop());
        }

        for (int i=0;i<result.size()-1;i++)
            lines.add(new Line(result.get(i), result.get(i+1)));
        return lines;
    }
}