import java.io.*;
import java.util.*;

class Top {
  int x, y;
  
  Top (int x, int y) {
    this.x= x;
    this.y=y;
  }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int testcase = Integer.parseInt(br.readLine());
        ArrayList<Top> al = new ArrayList<>();
        while(testcase-->0) {
          StringTokenizer st=  new StringTokenizer(br.readLine());
          int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
          al.add(new Top(x, y));
        }
        al.sort(new Comparator<Top>() {
          @Override
          public int compare(Top o1, Top o2) {
            return Integer.compare(o1.x, o2.x);
          }
        });
        
        int idx = -1, h = -1;
        for (int i=0;i<al.size();i++) {
          if (h < al.get(i).y) {
            h = al.get(i).y;
            idx = i;
          }
        }
        
        ArrayList<Top> left = new ArrayList<>(), right = new ArrayList<>();
        
        for (int i=0;i<idx;i++) {
          left.add(al.get(i));
        }
        Collections.reverse(left);
        
        for (int i=idx+1;i<al.size();i++) {
          right.add(al.get(i));
        }
        
        left = func(left);
        right = func(right);
        long res = 0;
        for (int i=0;i+1<left.size();i++) {
          res += (long) left.get(i).y * (left.get(i+1).x - left.get(i).x);
        }
        if (!left.isEmpty())
          res += (long) left.get(left.size() - 1).y * (al.get(idx).x - left.get(left.size()-1).x);
        res += al.get(idx).y;
        if (!right.isEmpty()) {
          res += (long) right.get(0).y * (right.get(0).x - al.get(idx).x);
        }
  
      for (int i=0;i+1<right.size();i++) {
        res += (long) right.get(i+1).y * (right.get(i+1).x - right.get(i).x);
      }
      
      System.out.println(res);
    }
    public static ArrayList<Top> func (ArrayList<Top> al) {
      Stack<Top> st = new Stack<>();
      if (al.isEmpty()) return new ArrayList<>();
      
      st.push(al.get(0));
      for (int i=1;i<al.size();i++) {
        while(!st.isEmpty() && st.peek().y < al.get(i).y) {
          st.pop();
        }
        
        st.push(al.get(i));
      }
      ArrayList<Top> res = new ArrayList<>(st);
      res.sort(new Comparator<Top>() {
        @Override
        public int compare(Top o1, Top o2) {
          return Integer.compare(o1.x, o2.x);
        }
      });
      
      return res;
    }
}
