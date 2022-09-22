import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()), k = Integer.parseInt(st.nextToken());

        char[] s = br.readLine().toCharArray();

        int goal = n-k;

        Stack<Integer> sts = new Stack<>(), st2 = new Stack<>();
        C: for (int i = 0;i<n;i++) {
            int f = s[i] - '0';

            while(!sts.isEmpty()) {
                if (n - i + sts.size() == n-k) {
                    for (;i<n;i++) {
                        sts.push(s[i] - '0');
                    }
                    break C;
                }

                if (sts.peek() < f) {
                    sts.pop();
                } else {
                    break;
                }
            }

            sts.push(f);
        }
        while(!sts.isEmpty()) st2.push(sts.pop());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(!st2.isEmpty() && goal-->0) bw.write(st2.pop()+"");
        bw.flush();

    }
}