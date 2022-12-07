import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

class Node {
  int type, lLength, rLength, size, answer;
  
  
  public void setType (int type) {
    size = 1;
    lLength = rLength = this.type = answer = type;
  }
  
  
  public static Node makeNode (Node res ,Node left, Node right) {
    if (left == null) return right;
    if (right == null) return left;
    
    res.answer = Math.max(left.answer, right.answer);
    res.answer = Math.max(res.answer, left.rLength + right.lLength);
    res.size = left.size + right.size;
    
    if (left.lLength == left.size) {
      res.lLength = left.size + right.lLength;
    } else {
      res.lLength = left.lLength;
    }
    
    if (right.rLength == right.size) {
      res.rLength = left.rLength + right.size;
    } else {
      res.rLength = right.rLength;
    }
    return res;
  }
}

class Seg {
  Node[] tree;
  public int start = 1;
  
  Seg (ArrayList<Point> al) {
    while(start < al.size()) start <<= 1;
    tree = new Node[start << 1];
    
    for (int i=0;i<tree.length;i++) tree[i] = new Node();
    for (int i=0;i<al.size();i++) {
      tree[i + start].setType(al.get(i).type);
    }
    
    for (int i=start>>1;i!=0;i>>=1) {
      for (int j=i;j< i << 1; j++) {
        Node.makeNode(tree[j], tree[j << 1], tree[j << 1 | 1]);
      }
    }
  }
  
  public void updateIdx (int idx, int type) {
    tree[idx + start].setType(type);
    
    for (int i=idx + start >> 1;i!=0;i>>=1) {
      Node.makeNode(tree[i], tree[i << 1], tree[i << 1 | 1]);
    }
  }
}

class Point {
  long x, y;
  int idx, type;
  
  Point (long x, long y, int type) {
    this.x=x;this.y=y;
    this.type = type;
  }
}

class Line {
  Point a, b, d;
  Line (Point a, Point b) {
    this.a=a;
    this.b=b;
    
    d = new Point(a.x - b.x, a.y - b.y, -1);
    
    if (d.x < 0 || (d.x == 0 && d.y < 0)) {
      d.x *= -1;
      d.y *= -1;
    }
  }
}

public class Main {
  public static void main (String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    
    ArrayList<Point> points = new ArrayList<>();
    for (int i=0;i<N;i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
      char t = st.nextToken().charAt(0);
      
      points.add(new Point(x, y, t == 'R' ? 1 : 0));
    }
    
    points.sort(new Comparator<Point>() {
      @Override
      public int compare(Point o1, Point o2) {
        return pointCompare(o1, o2);
      }
    });
    
    Point[] pointIdx = new Point[N];
    for (int i=0;i<N;i++) {
      pointIdx[i] = points.get(i);
      points.get(i).idx = i;
    }
    
    ArrayList<Line> lines = new ArrayList<>();
    for (int i=0;i<N;i++) {
      for (int j=i+1;j<N;j++) {
        lines.add(new Line(pointIdx[i], pointIdx[j]));
      }
    }
    
    Point origin = new Point(0,0,0);
    
    lines.sort(new Comparator<Line>() {
      @Override
      public int compare(Line o1, Line o2) {
        if (calCCW(origin, o1.d, o2.d) != 0) return ccw(origin, o1.d, o2.d);
        if (pointCompare(o1.a, o2.a) != 0) return pointCompare(o1.a, o2.a);
        return pointCompare(o1.b, o2.b);
      }
    });
    
    Seg tree = new Seg(points);
    int answer = 0;
    
    for (Line line : lines) {
      int temp = line.a.idx;
      line.a.idx = line.b.idx;
      line.b.idx = temp;
      
      pointIdx[line.a.idx]=line.a;
      pointIdx[line.b.idx] = line.b;
      
      tree.updateIdx(line.a.idx, line.a.type);
      tree.updateIdx(line.b.idx, line.b.type);
      
      answer = Math.max(tree.tree[1].answer, answer);
    }
    System.out.println(answer);
    
    
    
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