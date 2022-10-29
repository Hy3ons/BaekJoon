import java.io.*;
import java.util.Scanner;

public class Main {
    public static int N, M;
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();

        memorize = new int[M];
        recursive(0);
        bw.flush();
    }
    public static int[] memorize;

    public static void recursive (int depth) throws IOException{
        if (depth == M) {
            for(int e : memorize) bw.write(e+" ");
            bw.write('\n');
            return;
        }

        for (int i=1;i<=N;i++) {
            memorize[depth] = i;
            recursive(depth+1);
        }
    }
}