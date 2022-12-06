import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

class Point {
  long x, y;
  int idx;
  
  Point (long x, long y) {
    this.x=x;this.y=y;
  }
  
  void rotate () {
    long temp = y;
    y = x;
    x = temp;
    
    y *= -1;
  }
}

class Line {
  Point a, b, d;
  Line (Point a, Point b) {
    this.a=a;
    this.b=b;
    
    d = new Point(a.x - b.x, a.y - b.y);
    
    if (d.x < 0 || (d.x == 0 && d.y < 0)) {
      d.x *= -1;
      d.y *= -1;
    }
  }
}

public class Main {
  public static void main (String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    while(true) {
      int N = Integer.parseInt(br.readLine());
      if (N == 0) break;
  
      ArrayList<Point> points = new ArrayList<>(N);
      Point[] pointIdx = new Point[N];
      
      for (int i=0;i<N;i++) {
        StringTokenizer st= new StringTokenizer(br.readLine());
        points.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
      }
      
      points.sort(new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
          return pointCompare(o1, o2);
        }
      });
      
      for (int i=0;i<points.size();i++) {
        pointIdx[i] = points.get(i);
        points.get(i).idx = i;
      }
      
      ArrayList<Line> lines = new ArrayList<>();
      
      for (int i=0;i<N;i++) {
        for (int j=i+1;j<N;j++) {
          lines.add(new Line(pointIdx[i], pointIdx[j]));
        }
      }
      
      Point origin = new Point(0,0);
      
      lines.sort(new Comparator<Line>() {
        @Override
        public int compare(Line o1, Line o2) {
          if (calCCW(origin, o1.d, o2.d) != 0) return ccw(origin, o1.d, o2.d);
          if (pointCompare(o1.a, o2.a) != 0) return pointCompare(o1.a, o2.a);
          return pointCompare(o1.b, o2.b);
        }
      });
      
      long max = 0, min = Long.MAX_VALUE;
      
      for (Line line : lines) {
        int temp = line.a.idx;
        line.a.idx = line.b.idx;
        line.b.idx = temp;
        
        pointIdx[line.a.idx] = line.a;
        pointIdx[line.b.idx] = line.b;
        
        int left = Math.min(line.a.idx, line.b.idx);
        int right = Math.max(line.a.idx, line.b.idx);
        
        max = Math.max(max, getSize(line, pointIdx[0]));
        max = Math.max(max, getSize(line, pointIdx[N-1]));
        
        if (left != 0) {
          min = Math.min(min, getSize(line, pointIdx[left-1]));
        }
        
        if (right != N-1) {
          min = Math.min(min, getSize(line, pointIdx[right+1]));
        }
      }
      
      bw.write(print(min)+" "+print(max)+"\n");
      
      
      System.gc();
      
    }
    bw.flush();
  }
  public static String print (long a) {
    if ((a & 1) == 1) {
      return (a/2) +".5";
    }
    return (a>>1)+".0";
  }
  public static long getSize (Line line, Point a) {
    long res = Math.abs(calCCW(a, line.a, line.b));
    return res;
  }
  
  public static int pointCompare(Point a, Point b) {
    if (a.x == b.x) return Long.compare(a.y, b.y);
    return Long.compare(a.x, b.x);
  }
  
  public static long calCCW (Point a, Point b, Point c) {
    return (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
  }
  
  public static int ccw (Point a, Point b, Point c) {
    return Long.compare(calCCW(a, b, c), 0);
  }
}