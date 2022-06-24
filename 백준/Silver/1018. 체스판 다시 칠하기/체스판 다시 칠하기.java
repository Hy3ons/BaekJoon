import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int hang = Integer.parseInt(st.nextToken());
        int length = Integer.parseInt(st.nextToken());

        char[][] ary = new char[hang][];
        for (int i=0;i<hang;i++) {
            ary[i] = br.readLine().toCharArray();
        }

        int min = Integer.MAX_VALUE;

        for (int i=0;i<hang-7;i++) {
            for (int j=0;j<length-7;j++) {
                // 기준 i 행 j 열
                int change = 0;

                for (int k=i;k<i+8;k++) {
                    for (int g=j;g<j+8;g++) {
                        if (k%2==0) {
                            if (g%2==0) {
                                if (ary[k][g]!='B') change++;
                            } else {
                                if(ary[k][g]!='W') change++;
                            }
                        } else {
                            if (g%2==0) {
                                if (ary[k][g]!='W') change++;
                            } else {
                                if(ary[k][g]!='B') change++;
                            }
                        }
                    }
                }

                min = Math.min(min, change);
                change = 0;

                for (int k=i;k<i+8;k++) {
                    for (int g=j;g<j+8;g++) {
                        if (k%2==0) {
                            if (g%2==0) {
                                if (ary[k][g]!='W') change++;
                            } else {
                                if(ary[k][g]!='B') change++;
                            }
                        } else {
                            if (g%2==0) {
                                if (ary[k][g]!='B') change++;
                            } else {
                                if(ary[k][g]!='W') change++;
                            }
                        }
                    }
                }
                min = Math.min(min, change);
            }
        }
        System.out.println(min);




    }
}
