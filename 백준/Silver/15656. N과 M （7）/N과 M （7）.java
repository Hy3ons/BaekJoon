import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int N, M;
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();

        num = new int[N];
        for (int i=0;i<N;i++) num[i] = sc.nextInt();

        Arrays.sort(num);

        memorize = new int[M];
        recursive(0,0);
        bw.flush();
    }
    public static int[] num;
    public static int[] memorize;

    public static void recursive (int depth, int start) throws IOException{
        if (depth == M) {
            for(int e : memorize) bw.write(e+" ");
            bw.write('\n');
            return;
        }

        for (int i=start;i<N;i++) {
            memorize[depth] = num[i];
            recursive(depth+1, 0);
        }
    }
}