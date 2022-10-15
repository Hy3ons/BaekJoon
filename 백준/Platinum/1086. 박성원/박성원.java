import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int aryLength = Integer.parseInt(br.readLine());
        num = new String[aryLength];
        int[] ary = new int[aryLength];

        for (int i=0;i<aryLength;i++) {
            num[i] = br.readLine();
        }
        K = Integer.parseInt(br.readLine());

        int[][] rest = new int[aryLength][K];

        for (int i=0;i<aryLength;i++) {
            for (int j=0;j<K;j++) {
                rest[i][j] = getRest(i,j);
            }
        }



        String ak = String.valueOf(K);
        BigInteger bi;

        for (int i=0;i<aryLength;i++) {
            bi = new BigInteger(num[i]);
            ary[i] = Integer.parseInt(String.valueOf(bi.mod(new BigInteger(ak))));
        }



        //집합의 수는 중첩되지 않는다.
        //모든 정답을 찾아서 기약분수의 형태로나타내면 끝난다... 그걸 어케하누?ㅋㅋ

        long result = 0;
        long maxCase= factorial(aryLength);


        long[][] dp = new long[1<<aryLength][101];

        //45분째...
        //1시간 54분째....
        //aryLength 개의 수가 있다.

        boolean[][] check = new boolean[1<<aryLength][101];
        //ary 를 사용하라.
        for (int i=0;i<aryLength;i++) {
            check[1<<i][ary[i]] = true;
            dp[1<<i][ary[i]] = 1;
        }



        for (int T=1;T<aryLength;T++) {

            for (int i=0;i<dp.length;i++) {
                if (Integer.bitCount(i)!=T) {
                    continue;
                }

                for (int j=0;j<K;j++) { //현재 숫자.
                    if (check[i][j]) {


                        for (int k=0;k<aryLength;k++) {
                            if ((i&(1<<k))==0) { //k 그 비트가 꺼져있을때
                                int temp = rest[k][j];

                                dp[i|(1<<k)][temp] += dp[i][j];
                                check[i|(1<<k)][temp]=true;
                            }
                        }
                    }
                }
            }

        }

        result = dp[dp.length-1][0];

        while(true) {
            long a = gcd(result, maxCase);
            if (a==1) {
               System.out.println(result+"/"+maxCase);
                return;
            }

            result/=a;
            maxCase/=a;
        }




    }
    public static String[] num;
    public static int K;

    public static long factorial (long a) {
        if (a==1) {
            return 1;
        }
        return factorial(a-1) * a;
    }

    public static long gcd (long a, long b) {
        if (b==0) {
            return a;
        }
        return gcd(b, a%b);
    }

    static int getRest(int idx, int prev){
        int rest=prev;
        for(int i=0; i<num[idx].length(); i++){
            int curr = num[idx].charAt(i)-'0';
            curr = rest*10+curr;
            rest = curr%K;
        }
        return rest;
    }
}
