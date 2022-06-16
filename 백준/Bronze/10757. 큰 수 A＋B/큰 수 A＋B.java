import java.math.BigInteger;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringTokenizer st = new StringTokenizer(sc.nextLine());
        System.out.println(new BigInteger(st.nextToken()).add(new BigInteger(st.nextToken())));
    }
}
