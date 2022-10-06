import java.io.*;
import java.util.*;

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
    
    int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken()), K = Integer.parseInt(st.nextToken());
    st = new StringTokenizer(br.readLine());
    
    int[] arr = new int[N];
    ArrayList<Integer> ar = new ArrayList<>();
    for (int i=0;i<arr.length;i++) {
      arr[i] = Integer.parseInt(st.nextToken());
    }
    st = new StringTokenizer(br.readLine());
    for (int i=0;i<K;i++) {
      ar.add(Integer.parseInt(st.nextToken())-1);
    }
    ar.add(N);
    Collections.sort(ar);
    
    ArrayList<Gold> al = new ArrayList<>();
    
    for (int i=0;i+1<ar.size();i++) {
      int sum = 0;
      for (int j=ar.get(i);j<ar.get(i+1);j++) {
        sum += arr[j];
      }
      al.add(new Gold(sum, ar.get(i)+1));
    }
    
    al.sort(new Comparator<Gold>() {
      @Override
      public int compare(Gold o1, Gold o2) {
        if (o1.x == o2.x) return Integer.compare(o1.y, o2.y);
        return Integer.compare(o2.x, o1.x);
      }
    });
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    
    for (int i=0;i<M;i++) {
      pq.offer(al.get(i).y);
    }
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    while(!pq.isEmpty()) {
      bw.write(pq.poll()+"\n");
    }
    bw.flush();
  }
}