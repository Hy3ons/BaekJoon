import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        tims = new int[30];
        tims[0] = 1;
        for (int i=1;i<tims.length;i++) {
            tims[i] = tims[i-1] << 1;
        }

        N = Integer.parseInt(br.readLine());
        StringTokenizer st=  new StringTokenizer(br.readLine());

        goal = new int[N];
        temp = new int[N];

        for (int i=0;i<N;i++) goal[i] = Integer.parseInt(st.nextToken());
        make = new int[N];

        C: for (int k=1;tims[k] < N;k++) {
            qu.clear();
            stack.clear();
            for (int i=1;i<=N;i++) qu.offer(i);

            recur(1, k);
            for (int i=0;i<temp.length;i++) {
                temp[i] = stack.pop();
            }

            K: for (int kk=1;tims[kk]<N;kk++) {
                qu.clear();
                stack.clear();
                for (int i=0;i<temp.length;i++) qu.offer(temp[i]);
                recur(1, kk);

                for (int i=0;i<N;i++) {
                    if (stack.pop() != goal[i]) {
                        continue K;
                    }
                }

                System.out.println(k+" "+kk);
                return;
            }
        }


    }
    public static int N;
    public static Queue<Integer> qu = new LinkedList<>();
    public static Stack<Integer> stack = new Stack<>(), st2 = new Stack<>();
    public static int[] goal, tims, make, temp;
    public static void recur (int depth, int k) {
        if (k == -1) {
            while(!qu.isEmpty()) st2.push(qu.poll());
            while(!st2.isEmpty()) stack.push(st2.pop());
            return;
        }

        for (int i=qu.size()-tims[k];i>0;i--) {
            st2.push(qu.poll());
        }
        while(!st2.isEmpty()) stack.push(st2.pop());
        recur(depth+1, k-1);
    }
}