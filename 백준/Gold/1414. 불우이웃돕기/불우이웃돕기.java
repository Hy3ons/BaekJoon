import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.StringTokenizer;

class Line {
  int x, y, cost;
  Line (int x, int y,int cost) {
    this.cost = cost;
    this.x=x;this.y=y;
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
  
    ArrayList<Line> lines = new ArrayList<>(N*N);
    int sum = 0;
    
    for (int i=1;i<=N;i++) {
      char[] s = br.readLine().toCharArray();
      for (int j=1;j<=N;j++) {
        if (s[j-1] == '0') continue;
        lines.add(new Line(i, j, c(s[j-1])));
        sum += c(s[j-1]);
      }
    }
    
    lines.sort(new Comparator<Line>() {
      @Override
      public int compare(Line o1, Line o2) {
        return Integer.compare(o1.cost, o2.cost);
      }
    });
    
    for (int i=0;i<parent.length;i++) parent[i] = i;
    int cost = 0, ff = 0;
    
    for (Line l : lines) {
      if (find(l.x) != find(l.y)) {
        union(l.x, l.y);
        cost += l.cost;
        ff++;
      }
    }
    
    if (ff == N-1) {
      System.out.println(sum - cost);
    } else {
      System.out.println(-1);
    }
    
    
  }
  
  public static int[] parent = new int[100];
  public static int find (int node ) {
    if (parent[node] == node) return node;
    return parent[node] = find(parent[node]);
  }
  
  public static void union (int n1, int n2) {
    int p1 = find(n1);
    int p2 = find(n2);
    
    parent[p1] = p2;
  }
  
  public static int c (char s) {
    if ('a' <= s && s<= 'z') return s - 'a' + 1;
    else return s-'A'+27;
  }
}