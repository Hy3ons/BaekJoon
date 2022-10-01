import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.function.IntPredicate;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int N = Integer.parseInt(br.readLine());

        char[] s = br.readLine().toCharArray();

        int[] fail = new int[N];

        for (int i=1,j=0;i<fail.length;i++) {
            while(j>0 && s[i] != s[j]) j = fail[j-1];

            if (s[i] == s[j]) fail[i] = ++j;
        }

        System.out.println(N - fail[N-1]);
    }
}