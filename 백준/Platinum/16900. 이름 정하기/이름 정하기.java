import java.io.BufferedReader;
import java.io.IOError;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        char[] a = st.nextToken().toCharArray();
        int t = Integer.parseInt(st.nextToken());

        int[] fail = failure(a);
        int s = fail[fail.length-1];

        t -= 1;
        System.out.println((long)a.length + (long) (a.length - s) * t);


    }

    public static int[] failure(char[] arr) {
        int[] result = new int[arr.length];
        int j = 0;

        for (int i=1;i<arr.length;i++) {

            while (j>0 && arr[j]!=arr[i]) {
                j = result[j-1];
            }

            if (arr[i]==arr[j]) {
                result[i] = ++j;
            }
        }
        return result;
    }
}
