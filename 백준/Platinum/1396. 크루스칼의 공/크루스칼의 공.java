import java.io.*;
import java.util.*;

class Line {
  int n1, n2, w;
  Line(int n1, int n2,int c) {
    this.n1= n1;
    this.n2 = n2;
    w = c;
  }
}


public class Main {
  public static ArrayList<Line> lines = new ArrayList<>();
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
    for (int i=0;i<M;i++) {
      st = new StringTokenizer(br.readLine());
      lines.add(new Line(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())
          , Integer.parseInt(st.nextToken())));
    }
    
    lines.sort(new Comparator<Line>() {
      @Override
      public int compare(Line o1, Line o2) {
        return Integer.compare(o1.w, o2.w);
      }
    });
    
    ArrayList<Line> real = new ArrayList<>(N-1);
    
    int[] P = makeParent();
    for (Line l : lines) {
      if (union(l.n1, l.n2, P)) real.add(l);
    }
    
    lines = real;
    
    
    int queries = Integer.parseInt(br.readLine());
    Queue<Line> qu = new LinkedList<>();
    
    answer = new int[queries];
    hot = new int[queries];
    
    Line[] query = new Line[queries];
    for (int i=0;i<queries;i++) {
      st = new StringTokenizer(br.readLine());
      query[i] = new Line(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i);
    }
    
    Arrays.fill(answer, -1);
    ArrayList<ArrayList<Integer>> mid = new ArrayList<>(queries+100);
    for (int i=0;i<queries+100;i++) mid.add(new ArrayList<>());
    
    int[] left = new int[queries], right = new int[queries];
    int[] S = makeSize();
    
    Arrays.fill(right, lines.size()+1);
    
    boolean con = false;
    while(true) {
      con = false;
      for(ArrayList<Integer> al : mid) al.clear();
      
      for (int i=0;i<queries;i++) {
        if (left[i] + 1 == right[i]) {
          continue;
        }
        
        mid.get(left[i] + right[i] >> 1).add(i);
        con = true;
      }
      if (!con) break;
      
      Arrays.fill(S, 1);
      for(int i=0;i<P.length;i++) P[i] = i;
      
      for (int i=0;i<lines.size();i++) {
        union(lines.get(i).n1, lines.get(i).n2, P, S);
        
        for (int q : mid.get(i+1)) {
          if (find(query[q].n1, P) != find(query[q].n2, P)) {
            left[q] = i+1;
          } else {
            right[q] = i+1;
            answer[q] = S[find(query[q].n1, P)];
          }
        }
        
      }
    }
    
    BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(System.out));
    for (int i=0;i<queries;i++) {
      if (right[i] == lines.size()+1) {
        bw.write("-1\n");
      } else {
        bw.write(lines.get(right[i]-1).w+" "+answer[i]+"\n");
      }
      
    }
    bw.flush();
  }
  
  public static int[] answer, hot;
  public static int find (int node, int[] parent) {
    if (parent[node] == node) return node;
    return parent[node] = find(parent[node], parent);
  }
  
  public static boolean union (int n1, int n2, int[] parent) {
    int p1 = find(n1, parent);
    int p2 = find(n2, parent);
    
    if (p1 == p2) return false;
    parent[p1] = p2;
    return true;
  }
  public static boolean union (int n1, int n2, int[] parent, int[] size) {
    int p1 = find(n1, parent);
    int p2 = find(n2, parent);
    
    if (p1 == p2) return false;
    parent[p1] = p2;
    size[p2] += size[p1];
    return true;
  }
  
  public static int[] makeParent () {
    int[] p = new int[100001];
    for (int i=0;i<p.length;i++) p[i]= i;
    return p;
  }
  
  public static int[] makeSize () {
    int[] p = new int[100001];
    Arrays.fill(p, 1);
    return p;
  }
}