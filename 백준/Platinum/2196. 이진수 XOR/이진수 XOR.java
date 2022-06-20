import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        dp = new int[1<<a];
        ary = new int[b];
        check = new boolean[1<<a];

        ac = calculate(br.readLine());
        boolean startZero = true;
        boolean hasAnswer = false;

        for (int i=0;i<b;i++) {
            ary[i] = calculate(br.readLine());
            check[ary[i]] = true;
            if (ary[i]==0) startZero = false;
        }
        hasAnswer = check[ac];
        Arrays.fill(check, false);

        if (startZero) { //예외처리.
            int[] ary2 = new int[ary.length+1];
            for (int i=0;i<ary.length;i++) {
                ary2[i+1] = ary[i];
            }

            ary = ary2;
            b++;
            dp[0] = 1;
            check[0] = true;
        }

        memori = new int[b];


        if (hasAnswer) { //예외처리.
            limitDepth = 2;
            function(0, 1);
            //2개만 골라서 만들어봐.

            if (check[ac]) {
                System.out.println(1);
            } else {
                System.out.println(2);
            }

            printAnswer(ac);
            return;
        }


        //계산하는곳.

        for (int i=1;i<b;i++) {
            limitDepth = i+1;
            //limitdepth 는 숫자를 고르는 갯수를 나타냄.
            //숫자를 고르고나서, 계산횟수는 -1이 들어가개됨.
            function(0, 0);

        }

        //출력하는곳.

        if (check[ac]&&dp[ac]!=0) {
            System.out.println(dp[ac]);
            printAnswer(ac);
            return;
        }

        int nowVariable = Integer.MAX_VALUE;
        int out = 0;

        for (int i=0;i<(1<<a);i++) {
            if (i==ac) continue;
            if (check[i]) {
                if (nowVariable > Integer.bitCount(ac^i)) {
                    nowVariable = Integer.bitCount(ac^i);
                    out = i;
                } else if (nowVariable == Integer.bitCount(i)) {
                    if (dp[out]>dp[i]) {
                        out = i;
                    }
                }
            }
        }

        System.out.println(dp[out]);
        printAnswer(out);

    }
    public static int limitDepth,a,b, ac;
    public static int[] memori, ary, dp;
    public static boolean[] check;

    public static void function (int depth, int start) {
        if (depth==limitDepth) {
            int standard = 0;
            for (int i=0;i<depth;i++) {
                standard ^= ary[memori[i]];
            }

            if (!check[standard]) {
                dp[standard] = depth - 1;
                check[standard] = true;
            }

            return;
        }



        for (int i=start;i<b;i++) {
            memori[depth] = i;
            function(depth+1, i+1);
            if (check[ac]) return;
        }
    }

    public static int calculate (String a) {
        int length = a.length();

        int result = 0;

        for (int i=0;i<length;i++) {
            result += (a.charAt(length-1-i)-'0') * Math.pow(2, i);
        }
        return result;
    }

    public static void printAnswer (int ac) {
        String outa = Integer.toBinaryString(ac);
        String put = "";

        if (outa.length()<a) {
            int tryTime = a-outa.length();

            for (int i=0;i<tryTime;i++) {
                put += "0";
            }
            put += outa;
            System.out.println(put);
            return;
        }

        System.out.println(Integer.toBinaryString(ac));

        return;
    }
}
