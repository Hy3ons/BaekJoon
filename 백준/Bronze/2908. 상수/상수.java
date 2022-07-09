import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringTokenizer st = new StringTokenizer(sc.nextLine());

        char[] a = st.nextToken().toCharArray()
                , b = st.nextToken().toCharArray();

        int temp1 = 0, temp2 = 0;

        for (int i=0;i<a.length;i++) {
            temp1*= 10;
            temp1 +=a[a.length-1-i] -'0';
        }
        for (int i=0;i<b.length;i++) {
            temp2*=10;
            temp2 += b[b.length-1-i] - '0';
        }
        System.out.println(Math.max(temp1, temp2));
    }
}