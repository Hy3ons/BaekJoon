import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.StringTokenizer;

class Point {
  long x, y;
  int idx;
  long dist;
  
  Point another;
  Point (int x, int y, int idx) {
    this.x=x;this.y=y;
    this.idx = idx;
  }
  Point (long x, long y, int idx) {
    this.x=x;this.y=y;this.idx=idx;
  }
}

class Line {
  Point a, b;
  int y;
  Line (int x1, int x2, int y, int idx) {
    a = new Point(Math.min(x1, x2), y, idx);
    b = new Point(Math.max(x1, x2), y, idx);
    this.y=y;
    a.another = b;
    b.another = a;
    a.dist = b.dist = Math.abs(a.x - b.x);
  }
}

public class Main {
  public static ArrayList<Line> lines = new ArrayList<>();
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
  
    ArrayList<Point> points = new ArrayList<>(N<<2);
    
    
    for (int i=0;i<N;i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      lines.add(new Line(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())
          , Integer.parseInt(st.nextToken()), i));
      Line temp = lines.get(i);
    }
    
    lines.sort(new Comparator<Line>() {
      @Override
      public int compare(Line o1, Line o2) {
        if (o1.y == o2.y) return Long.compare(o1.a.x, o2.a.x);
        return Integer.compare(o1.y, o2.y);
      }
    });
    
    for (int i=0;i<lines.size();i++) {
      solve(points, lines.get(i).a);
      solve(points, lines.get(i).b);
    }
    System.out.println(ans);
    
    
  }
  public static long ans = 0;
  public static HashSet<Integer> hs = new HashSet<>();
  public static void solve (ArrayList<Point> points, Point standard) {
    hs.clear();
    points.clear();
  
    for (Line l : lines) {
      if (l.y < standard.y) {
        Point a = inverse(standard, l.a), b = inverse(standard, l.b);
        a.another = b;
        b.another = a;
        a.dist = b.dist = l.a.dist;
        points.add(a);
        points.add(b);
      } else if (l.y > standard.y) {
        points.add(l.a);
        points.add(l.b);
      }
    }
    
    points.sort(new Comparator<Point>() {
      @Override
      public int compare(Point o1, Point o2) {
        int ccw = CCW(standard, o1, o2);
      
        if (ccw == 0) {
          int lcw = CCW(standard, o1, o1.another);
          int rcw = CCW(standard, o2, o2.another);
          
          return Integer.compare(lcw, rcw);
        } else return ccw;
      }
    });
    
    long now = standard.dist;
    ans = Math.max(ans, now);
    
    for (Point p : points) {
      if (hs.contains(p.idx)) now -= p.dist;
      else {
        hs.add(p.idx);
        ans = Math.max(ans, (now += p.dist));
      }
    }
  }
  
  public static int CCW (Point a, Point b, Point c) {
    long ccw = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
    return Long.compare(ccw, 0);
  }
  
  public static Point inverse (Point cent, Point comp) {
    return new Point((cent.x << 1) - comp.x, (cent.y << 1) - comp.y, comp.idx);
  }
}