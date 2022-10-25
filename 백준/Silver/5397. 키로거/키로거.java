import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int testcase = Integer.parseInt(br.readLine());
        Stack<Character> st = new Stack<>(), st2 = new Stack<>();
        Stack<Character> qu = new Stack<>();
        
        
        while(testcase-->0) {
          char[] s = br.readLine().toCharArray();
          
          for (int i=0;i<s.length;i++) {
            if (s[i] == '<') {
              if (!st.isEmpty()) {
                qu.add(st.pop());
              }
            } else if (s[i] == '>') {
              if (!qu.isEmpty()) {
                st.push(qu.pop());
              }
            } else if (s[i] == '-') {
              if (!st.isEmpty()) st.pop();
            } else {
              st.push(s[i]);
            }
          }
          while(!st.isEmpty()) st2.push(st.pop());
          while(!st2.isEmpty()) bw.write(st2.pop());
          while(!qu.isEmpty()) bw.write(qu.pop());
          bw.write('\n');
        }
        bw.flush();
    }
}
