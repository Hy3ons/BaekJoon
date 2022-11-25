import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

class Point {
  long x, y;
  Point (long x, long y) {
    this.x= x;
    this.y = y;
  }
}

class Line {
  Point p1, p2;
  double a, b;
  boolean vertical, horizon;
  Line(Point p1, Point p2){
    this.p1 = p1;
    this.p2 = p2;
    
    if (p1.x == p2.x) vertical = true;
    if (p1.y == p2.y) horizon = true;
    
    if (p1.x != p2.x) {
      a = (p1.y - p2.y) / (double) (p1.x - p2.x);
      b = p1.y - a * p1.x;
    }
    
  }
  
  public static boolean isCross (Line l1, Line l2) {
    if (isSame(l1.p1, l2.p1)) return true;
    if (isSame(l1.p1, l2.p2)) return true;
    if (isSame(l1.p2, l2.p1)) return true;
    if (isSame(l1.p2, l2.p2)) return true;
    
    if (between(l1, l2)) return true;
    if (between(l2, l1)) return true;
    
    if (CCW(l1.p1, l1.p2, l2.p1) == 0 && CCW(l1.p1, l1.p2, l2.p2) == 0) return false;
    
    if (CCW(l1.p1, l1.p2, l2.p1) * CCW(l1.p1, l1.p2, l2.p2) <= 0
        && CCW(l2.p1, l2.p2, l1.p1) * CCW(l2.p1, l2.p2, l1.p2) <= 0) return true;
    
    return false;
  }
  
  public double function (double x) {
    return a * x + b;
  }
  
  public static boolean isSame(Point p1, Point p2) {
    return p1.x == p2.x && p1.y == p2.y;
  }
  
  public static int CCW (Point a, Point b, Point c) {
    long cww = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
    return Long.compare(cww, 0);
  }
  
  public static boolean between(Line l1, Line l2) {
    if (sub(l1, l2.p1)) return true;
    if (sub(l1, l2.p2)) return true;
    
    return false;
  }
  public static boolean sub (Line l1, Point p) {
    return CCW(l1.p1, l1.p2, p) == 0 && Math.min(l1.p1.x, l1.p2.x) <= p.x
        && p.x <= Math.max(l1.p1.x, l1.p2.x)
        && Math.min(l1.p1.y, l1.p2.y) <= p.y
        && p.y <= Math.max(l1.p1.y, l1.p2.y);
  }
  
  public static boolean ssub (Line l1, Point p) {
    return sub(l1, p) && !(isSame(l1.p1, p) || isSame(l1.p2, p));
  }
  
  public static double cross (Line l1, Line l2) {
    return (l1.b - l2.b) / (l2.a - l1.a);
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    Point a = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))
        , b = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    st = new StringTokenizer(br.readLine());
    
    Point c = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))
        , d = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    
    Line l1 = new Line(a, b);
    Line l2 = new Line(c, d);
    
    if (Line.isCross(l1, l2)) {
      System.out.println("1");
      if (Line.sub(l1, l2.p1) && Line.sub(l1, l2.p2)) return;
      if (Line.sub(l2, l1.p1) && Line.sub(l2, l1.p2)) return;
      
      if (Line.CCW(l1.p1, l1.p2, l2.p1) == 0 && Line.CCW(l1.p1, l1.p2, l2.p2) == 0) {
        
        if (Line.ssub(l1, l2.p1) || Line.ssub(l1, l2.p2)) return;
        if (Line.ssub(l2, l1.p1) || Line.ssub(l2, l1.p2)) return;
        
        if (Line.sub(l1, l2.p1) ^ Line.sub(l1, l2.p2)) {
          if (Line.sub(l1, l2.p1)) {
            System.out.println(l2.p1.x +" "+l2.p1.y);
          } else {
            System.out.println(l2.p2.x+" "+l2.p2.y);
          }
          
        } else if (Line.sub(l2, l1.p1) ^ Line.sub(l2, l1.p2)) {
          if (Line.sub(l2, l1.p1)) {
            System.out.println(l1.p1.x+" "+l1.p1.y);
          } else {
            System.out.println(l1.p2.x+" "+l1.p2.y);
          }
        }
      } else {
        
        
        if (l1.vertical || l2.vertical) {
          if (l1.vertical) {
            long x = l1.p1.x;
            System.out.println(x +" "+l2.function(x));
          } else {
            long x = l2.p2.x;
            System.out.println(x +" "+l1.function(x));
          }
        } else {
          double x = Line.cross(l1, l2);
          
          System.out.println(x +" "+l1.function(x));
          
        }
        
        
        
      
      }
    } else {
      System.out.println("0");
    }
  }
}