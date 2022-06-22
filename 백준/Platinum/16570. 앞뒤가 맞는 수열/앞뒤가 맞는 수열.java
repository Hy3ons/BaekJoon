import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        int[] a = new int[t];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=a.length-1;i>=0;i--) a[i] = Integer.parseInt(st.nextToken());

        int[] fail = failure(a);
        int maxValue = Integer.MIN_VALUE;

        for (int i=0;i<a.length;i++) {
            maxValue = Math.max(maxValue, fail[i]);
        }

        if (maxValue==0) {
            System.out.println(-1);
            return;
        }

        int count = 0;

        for (int i=0;i<fail.length;i++) {
            if (fail[i]==maxValue) count++;
        }
        System.out.print(maxValue+" ");
        System.out.println(count);

    }

    public static int[] failure(int[] arr) {
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
