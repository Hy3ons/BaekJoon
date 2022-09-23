import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] txt = br.readLine().toCharArray();
        char[] ser = br.readLine().toCharArray();

        int[] fail = failure(ser);

        int count = 0;
        for (int i=0, j =0;i<txt.length;i++) {
            while(j > 0 && txt[i] != ser[j]) {
                j = fail[j-1];
            }

            if (txt[i] == ser[j]) {
                if (++j == ser.length) {
                    j = 0;
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    public static int[] failure (char[] s) {
        int[] res = new int[s.length];

        for (int i=1,j=0;i<res.length;i++) {
            while(j > 0 && s[i] != s[j]) {
                j = res[j-1];
            }

            if (s[i] == s[j]) res[i] = ++j;
        }
        return res;
    }
}