import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();
        int multi = sc.nextInt();
        int MOD = sc.nextInt();

        long[] ary = new long[33];
        ary[0] = value;

        for (int i=1;i<33;i++) ary[i] = (ary[i-1]*ary[i-1]) % MOD;
        
        long result = 1;
        for (int i=0;i<32;i++) {
            if ((multi&(1<<i))==0) continue;
            
            result *= ary[i];
            result %= MOD;    
        }
        
        System.out.println(result);
    }
}
