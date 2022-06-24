import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());
        int count = 0;

        K: for (int T=0;T<testcase;T++) {
            char[] arr = br.readLine().toCharArray();
            boolean[] visited = new boolean[26];

            L: for (int j=0;j<arr.length;j++) {
                if (!visited[arr[j]-'a']) {
                    visited[arr[j]-'a'] = true;
                    char standard = arr[j];
                    for (int k=j;k<arr.length;k++) {
                        if (standard!=arr[k]) {
                            j = k-1;
                            continue L;
                        }
                    }
                    break;
                } else {
                    continue K;
                }
            }
            count++;

        }

        System.out.println(count);
    }
}
