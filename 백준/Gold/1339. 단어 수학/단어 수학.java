import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long[] value = new long[26];

        for (int i=0;i<N;i++) {
            char[] s = br.readLine().toCharArray();

            for (int j=0;j<s.length;j++) {
                value[s[s.length-1-j]-'A'] += Math.pow(10, j);
            }
        }

        Arrays.sort(value);

        long answer = 0, cnt = 9;

        for (int i=25;i>=0;i--) {
            answer += value[i] * cnt--;
        }

        System.out.println(answer);
    }
}