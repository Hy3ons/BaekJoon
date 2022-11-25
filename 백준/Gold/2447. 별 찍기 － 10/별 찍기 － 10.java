import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static char[][] board;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        board = new char[N][N];

        for (char[] s : board) Arrays.fill(s, ' ');

        dnc(N >> 1, N >> 1, N / 3);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                bw.write(board[i][j]);
            }
            bw.write('\n');
        }
        bw.flush();
    }

    public static void dnc (int x, int y, int key) {
        if (key == 1) {
            for (int i=0;i<8;i++) {
                board[x + dx[i]][y + dy[i]] = '*';
            }

            return;
        }
        int d = key;

        for (int i=0;i<8;i++) {
            dnc(x + dx[i] * d, y + dy[i] * d, key / 3);
        }
    }

    public static int[] dx = {-1,-1,-1,0,0,1,1,1}, dy = {-1,0,1,-1,1,-1,0,1};

}