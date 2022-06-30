import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair {
    int x, y;
    Pair (int x, int y) {
        this.x = x;
        this. y= y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int size = (int) Math.pow(2, Integer.parseInt(st.nextToken()));
        board = new int[size+1][size+1];

        while (testcase-->0) {
            for (int[] arr : board) Arrays.fill(arr, 0);
            for (int i=0;i<size+1;i++)
                board[i][0] = board[0][i] = -2;


            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            recur(size, x, y, 1, 1, 1);
            board[x][y] = -3;

            for (int i=1;i<=size;i++) {
                for (int j=1;j<=size;j++) {
                    if (board[i][j]==1||board[i][j]==3) {
                        bw.write("c");
                    } else if (board[i][j]==2||board[i][j]==4) {
                        bw.write('b');
                    } else if (board[i][j]==0) {
                        bw.write('a');
                    } else if (board[i][j]==-3) {
                        bw.write('@');
                    }
                }
                bw.newLine();
            }
        }
        bw.flush();
    }
    public static int[][] board;
    public static int[] ddx = {-1,0,0,1,1}, ddy = {-1,1,0,0,1};

    public static void recur (int size, int x, int y, int xStart, int yStart, int myType) {
        int type = getType(size, x, y, xStart, yStart);
        if (size==2) {
            for (int i=1;i<=4;i++) {
                if (type==i) continue;
                int dx = xStart+ddx[i];
                int dy = yStart + ddy[i];

                board[dx][dy] = myType;
            }
            return;
        }

        int xEnd = xStart + size -1;
        int yEnd = yStart + size -1;

        int xmid = (xStart + xEnd) / 2;
        int ymid = (yStart + yEnd) / 2;

        for (int i=1;i<=4;i++) {
            if (type==i) {
                switch (i) {
                    case 1 :
                        recur(size/2, x, y, xStart, ymid+1, 1);
                        break;
                    case 2 :
                        recur(size/2 ,x , y, xStart, yStart, 2);
                        break;
                    case 3 :
                        recur(size/2, x, y, xmid+1, yStart, 3);
                        break;
                    case 4 :
                        recur(size/2, x, y, xmid+1, ymid+1, 4);
                        break;
                }
            } else {
                switch (i) {
                    case 1 :
                        recur(size/2, xmid, ymid+1, xStart, ymid+1, 1);
                        break;
                    case 2 :
                        recur(size/2 , xmid, ymid, xStart, yStart, 2);
                        break;
                    case 3 :
                        recur(size/2, xmid+1, ymid, xmid+1, yStart, 3);
                        break;
                    case 4 :
                        recur(size/2, xmid+1, ymid+1, xmid+1, ymid+1, 4);
                        break;
                }
            }
        }
    }

    public static int getType (int size, int x, int y, int xRange, int yRange) {
        int xmid = (xRange+size+xRange-1) / 2;
        int ymid = (yRange*2+size-1)/2;
        if (x>xmid) {
            if (y>ymid) {
                return 4;
            } else {
                return 3;
            }
        } else {
            if (y>ymid) {
                return 1;
            } else {
                return 2;
            }
        }
    }
}