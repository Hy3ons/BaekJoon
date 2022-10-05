import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Cow {
  int left, right;
  Cow (int left, int right) {
    this.left=left;
    this.right = right;
  }
}

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    final int chicken = Integer.parseInt(st.nextToken());
    final int cow = Integer.parseInt(st.nextToken());
  
    PriorityQueue<Integer> fried = new PriorityQueue<>();
    PriorityQueue<Cow> meat = new PriorityQueue<>(new Comparator<Cow>() {
      @Override
      public int compare(Cow o1, Cow o2) {
        return Integer.compare(o1.left, o2.left);
      }
    });
    PriorityQueue<Cow> standby = new PriorityQueue<>(new Comparator<Cow>() {
      @Override
      public int compare(Cow o1, Cow o2) {
        return Integer.compare(o1.right, o2.right);
      }
    });
    
    for (int i=0;i<chicken;i++) {
      fried.offer(Integer.parseInt(br.readLine()));
    }
    
    for (int i=0;i<cow;i++) {
      st = new StringTokenizer(br.readLine());
      meat.add(new Cow(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
    }
    
    int res = 0;
    while(!fried.isEmpty()) {
      int now = fried.poll();
      while(!standby.isEmpty() && standby.peek().right < now) standby.poll();
      
      while(!meat.isEmpty()) {
        if (meat.peek().right < now) meat.poll();
        else if (meat.peek().left <= now && now <= meat.peek().right) standby.offer(meat.poll());
        else if (now < meat.peek().left) break;
      }
      
      if (!standby.isEmpty()) {
        standby.poll();
        res++;
      }
    }
  
    System.out.println(res);
  
  }
}