import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        char[] s = input.toCharArray();
        int[] fail = failure(s);

        int length = fail.length - fail[fail.length-1];

        if (length == fail.length) {
            System.out.println(-1);
            return;
        }
        String go = input.substring(length);
        if (fuc(s, go)) {
            System.out.println(go);
            return;
        }

        for (int i=1;i<go.length();i++) {
            if (go.charAt(i)==s[0]) {
                if (fuc(s, go.substring(i))) {
                    System.out.println(go.substring(i));
                    return;
                }
            }
        }

        System.out.println(-1);

    }

    public static boolean fuc (char[] arr, String s) {
        char[] compare = s.toCharArray();
        for (int i=0;i< compare.length;i++)
            if (arr[i]!=compare[i]) return false;

        int[] fail = failure(compare);

        for (int i=1,j=0;i<arr.length-1;i++) {
            while (j>0 && arr[i] != compare[j]) {
                j = fail[j-1];
            }

            if (arr[i] == compare[j]) {
                if (++j == compare.length) {
                    return true;
                }
            }
        }
        return false;
    }
    public static int[] failure (char[] arr) {
        int[] result = new int[arr.length];

        for (int i=1,j=0;i<arr.length;i++) {
            while (j > 0 && arr[i] != arr[j])
                j = result[j-1];
            if (arr[i] == arr[j])
                result[i] = ++j;

        }
        return result;
    }
}
