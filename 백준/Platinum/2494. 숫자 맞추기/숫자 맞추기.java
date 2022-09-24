import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class Tuple {
    int idx, spin, re;
    Tuple(int idx, int spin, int re) {
        this.idx = idx;
        this.spin = spin;
        this.re = re;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        char[] now = br.readLine().toCharArray();
        char[] goal = br.readLine().toCharArray();

        int[][] dp = new int[10][n+1];
        Tuple[][] re = new Tuple[10][n+1];

        for(int[] d : dp) Arrays.fill(d, -1);
        dp[0][0] = 0;



        for (int i=0;i<n;i++) {
            for (int s = 0;s<10;s++) {
                if (dp[s][i] >= 0) {
                    int number = spining(now[i]-'0', s);
                    int goal_number = goal[i] - '0';

                    int right = number - goal_number;
                    if (right < 0) right += 10;

                    if (dp[s][i+1] == -1) {
                        dp[s][i+1] = dp[s][i] + right;
                        re[s][i+1] = new Tuple(i+1, -right, s);
                    } else {
                        if (dp[s][i+1] > dp[s][i] + right) {
                            dp[s][i+1] = dp[s][i] + right;
                            re[s][i+1] = new Tuple(i+1, -right, s);
                        }
                    }

                    int left = goal_number - number;
                    if (left < 0) left += 10;

                    int idx = (s + left) % 10;
                    if (dp[idx][i+1] == -1) {
                        dp[idx][i+1] = dp[s][i] + left;
                        re[idx][i+1] = new Tuple(i+1, left, s);
                    } else {
                        if (dp[idx][i+1] > dp[s][i] + left) {
                            dp[idx][i+1] = dp[s][i] + left;
                            re[idx][i+1] = new Tuple(i+1, left, s);
                        }
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        int idx = -1;
        for (int i=0;i<10;i++) {
            if(dp[i][n] == -1) continue;
            if (min > dp[i][n]) {
                min = dp[i][n];
                idx = i;
            }
        }

        ArrayList<Tuple> ans = new ArrayList<>();
        for (int i = n;i>0;idx = re[idx][i--].re) {
            ans.add(re[idx][i]);
        }
        ans.sort(new Comparator<Tuple>() {
            @Override
            public int compare(Tuple o1, Tuple o2) {
                return Integer.compare(o1.idx, o2.idx);
            }
        });

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(min+"\n");
        for (Tuple s: ans) bw.write(s.idx+" "+s.spin+"\n");
        bw.flush();
    }

    public static int spining(int number, int spined) {
        return (number + spined) % 10;
    }
}