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
    public static Point getVector(Point a, Point b) {
        return new Point(b.x - a.x, b.y - a.y);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        while(testcase-->0) {
            int N = Integer.parseInt(br.readLine());
            ArrayList<Point> p = new ArrayList<>(N+1);

            for (int i=0;i<N;i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
                p.add(new Point(x,y));
            }

            if (N == 2) {
                bw.write(p.get(0)+" "+p.get(1)+"\n");
                continue;
            }

            Collections.sort(p, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.x == o2.x) return Long.compare(o1.y, o2.y);
                    return Long.compare(o1.x, o2.x);
                }
            });

            Point cent = p.get(0);
            p.remove(0);

            Collections.sort(p, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    int c = CCW(cent, o1, o2);
                    if (c==0) {
                        if (o1.y == o2.y) {
                            return Long.compare(o1.x, o2.x);
                        } else
                            return Long.compare(o2.y, o1.y);
                    } else {
                        return c;
                    }
                }
            });

            if (cent.y == p.get(p.size()-1).y) {
                Collections.sort(p, new Comparator<Point>() {
                    @Override
                    public int compare(Point o1, Point o2) {
                        int c = CCW(cent, o1, o2);
                        if (c==0) {
                            if (o1.y == o2.y) {
                                return Long.compare(o2.x, o1.x);
                            } else
                                return Long.compare(o2.y, o1.y);
                        } else {
                            return c;
                        }
                    }
                });
            }

            Stack<Point> st = new Stack<>();
            st.push(cent);
            st.push(p.get(0));
            for (int i=1;i<p.size();i++) {
                st.push(p.get(i));
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

            if (st.size() < 3) {
                long temp = 0;
                int idx = 0;
                for (int i=0;i<p.size();i++) {
                    long a = getDist(cent, p.get(i));
                    if (a > temp) {
                        temp = a;
                        idx = i;
                    }
                }
                
                bw.write(cent +" " + p.get(idx)+"\n");
                continue;
            }


            p = new ArrayList<>(st.size()+1);
            while(!st.isEmpty()) {
                p.add(st.pop());
            }

            Point vector = new Point(0, 0);
            int l = 0, r = 0;
            final int K = p.size();

            for (int i=0;i<K;i++) {
                if (p.get(l).x > p.get(i).x) l = i;
                if (p.get(r).x < p.get(i).x) r = i;
            }

            long temp = getDist(p.get(l), p.get(r));
            Point ans = new Point(l, r);

            for (int i=0;i<K;i++) {
                if (CCW(vector, Point.getVector(p.get(l), p.get((l+1) % K)), Point.getVector(p.get((r+1) % K), p.get(r))) > 0) {
                    l = (l + 1) % K;
                } else {
                    r = (r + 1) % K;
                }

                if (temp < getDist(p.get(l), p.get(r))) {
                    temp = getDist(p.get(l), p.get(r));
                    ans.x = l;
                    ans.y = r;
                }
            }

            bw.write(p.get((int) ans.x)+" "+p.get((int) ans.y)+"\n");
        }
        bw.flush();
    }
    public static long getDist (Point a, Point b) {
        long xx = a.x - b.x, yy = a.y - b.y;
        return xx * xx + yy * yy;
    }

    public static int CCW (Point a, Point b, Point c) {
        long x = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
        return Long.compare(x, 0);
        //반시계가 음수이다.
    }
}