import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String fail = "FRULA";

        char[] a = br.readLine().toCharArray();
        char[] b = br.readLine().toCharArray();

        Stack<Character> st = new Stack<>();
        Stack<Character> temp = new Stack<>();

        char s = b[b.length-1];

        K: for (int i=0;i<a.length;i++) {
            if (a[i]==s) {
                for (int j=b.length-2;j>=0;j--) {
                    if (st.isEmpty()) {
                        while (!temp.isEmpty()) {
                            st.push(temp.pop());
                        }
                        st.push(a[i]);
                        continue K;
                    }
                    
                    temp.push(st.pop());
                    if (temp.peek()!=b[j]) {
                        while (!temp.isEmpty()) {
                            st.push(temp.pop());
                        }
                        st.push(a[i]);
                        continue K;
                    }
                }

                temp.clear();
            } else {
                st.push(a[i]);
            }
        }

        if (st.isEmpty()) {
            System.out.println(fail);
        } else {
            char[] result = new char[st.size()];
            for (int i=0;i<result.length;i++) {
                result[i] = st.pop();
            }

            StringBuilder sb = new StringBuilder();
            for (int i=result.length-1;i>=0;i--) {
                sb.append(result[i]);
            }
            System.out.println(sb);
        }


    }
}
