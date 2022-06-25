import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        char[] s = br.readLine().toCharArray();
        int[] fail = failure(s);

        K: for (int i=0;i<fail.length;i++) {
            if (fail[i]==0) continue;
            if ((i+1) % (i+1-fail[i])==0) {
                // 육개장은 매일 먹어서 질림 육개장 말고는 맛없음
                bw.write(i+1+" "+((i+1) / (i+1-fail[i]))+"\n");
            }
        }
        bw.flush();

    }

    public static int[] failure (char[] arr) {
        int[] result = new int[arr.length];

        int j = 0;
        for (int i=1;i<arr.length;i++) {
            while(j > 0 && arr[i]!=arr[j]) {
                j = result[j-1];
            }

            if (arr[i]==arr[j]) {
                result[i] = ++j;
            }
        }

        return result;
    }
}
