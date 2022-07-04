import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());
        char[] arr = br.readLine().toCharArray();
        int[] fail = failure(arr);
        System.out.println(testcase - fail[fail.length-1]);
//        for (int i = 0; i < testcase; i++) {
//
//            int remain = i + 1 - fail[i];
//        }

    }

    public static int[] failure(char[] arr) {
        int[] result = new int[arr.length];

        for (int i = 1, j = 0; i < arr.length; i++) {
            while (j > 0 && arr[i] != arr[j])
                j = result[j - 1];

            if (arr[i] == arr[j]) result[i] = ++j;
        }
        return result;
    }
}
