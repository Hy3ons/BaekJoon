import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        HashSet<String> hs = new HashSet<>();

        int[] lefts = new int[1000001];
        int[] rights = new int[1000001];
        Arrays.fill(lefts, -1);
        Arrays.fill(rights, Integer.MAX_VALUE);

        for (int i=0;i<N;i++) {
            String input = br.readLine();
            StringTokenizer st = new StringTokenizer(input);
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            hs.add(input);

            lefts[left] = Math.max(lefts[left], right);
            rights[right] = Math.min(rights[right], left);
        }

        int n = Integer.parseInt(br.readLine());

        while(n-->0) {
            String input = br.readLine();
            StringTokenizer st = new StringTokenizer(input);
            int left= Integer.parseInt(st.nextToken()), right = Integer.parseInt(st.nextToken());

            if (hs.contains(input)) {
                bw.write("1\n");
                continue;
            }

            if (rights[right] <= left && right <= lefts[left]) {
                bw.write("2\n");
            } else {
                bw.write("-1\n");
            }


        }
        bw.flush();

    }
}