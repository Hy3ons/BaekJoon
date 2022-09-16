import java.io.*;
import java.util.Scanner;

public class Main {
    public static int[] s = {2, 3, 5, 7}, add = {1,3,7,9};
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in) ;
        N = sc.nextInt();
        for(int i=0;i<4;i++) f(s[i], 1);
        bw.flush();
    }
    static int N;

    public static void f (int n, int depth) throws IOException{
        if(N == depth) {
            bw.write(n+"\n");
            return;
        }

        C: for(int i=0;i<4;i++) {
            int test = n * 10 + add[i];

            for (int j=3;j<=Math.sqrt(test);j+=2) {
                if (test % j == 0) continue C;
            }

            f(test, depth+1);
        }
    }
}