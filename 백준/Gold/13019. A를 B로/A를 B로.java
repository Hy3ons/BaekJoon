import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        char[] up = br.readLine().toCharArray();
        char[] down = br.readLine().toCharArray();

        if (up.length != down.length) {
            System.out.println(-1);
            return;
        }

        int[] spell = new int[26], spell2 = new int[26];
        Stack<Character> st = new Stack<>(), st2 = new Stack<>();

        for (int i=0;i<up.length;i++) {
            spell[up[i]-'A']++;
            spell2[down[i]-'A']++;

            st.push(up[i]);
            st2.push(down[i]);
        }


        for (int i=0;i<spell.length;i++) {
            if (spell[i] != spell2[i]) {
                System.out.println(-1);
                return;
            }
        }
        int count = 0;
        while(!st.isEmpty()) {
            if (st.peek() == st2.peek()) {
                st.pop(); st2.pop();
            } else {
                count++;
                st.pop();
            }
        }

        System.out.println(count);

    }
}