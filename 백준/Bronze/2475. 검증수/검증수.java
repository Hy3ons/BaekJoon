import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int a = 0;
        for (String s : new Scanner(System.in).nextLine().split(" ")) 
            a += Integer.parseInt(s) * Integer.parseInt(s);
        System.out.println(a%10);
    }
}
