import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

class Gold {
  int x, y;
  Gold (int x, int y) {
    this.x=x;this.y=y;
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st= new StringTokenizer(br.readLine());
  
    HashSet<Integer> x = new HashSet<>(), y = new HashSet<>();
    int xLit = Integer.parseInt(st.nextToken()), yLit = Integer.parseInt(st.nextToken());
    
    int T = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    Gold[] golds = new Gold[T];
    
    for (int i=0;i<T;i++) {
      st = new StringTokenizer(br.readLine());
      golds[i] = new Gold(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
      x.add(golds[i].x); y.add(golds[i].y);
    }
    
    for (int i=0;i<K;i++) {
      y.remove(i);
    }
    
    for (int i=xLit-K+1;i<=xLit;i++) {
      x.remove(i);
    }
    
    x.add(xLit-K);
    y.add(yLit);
    
    
    int fx = -1, fy = -1;
    int count = -1;
    
    for (int dx : x) for (int dy : y) {
      int c= 0;
      int downY = dy - K;
      int upX = dx + K;
      
      for (Gold gold : golds) {
        if (dx <= gold.x && gold.x <= upX && downY <= gold.y && gold.y <= dy) {
          c++;
        }
      }
      
      if (count < c) {
        count = c;
        fx = dx;
        fy = dy;
      }
    }
    
    System.out.println(fx+" "+fy+"\n"+count);
    
    
    
    
  }
}