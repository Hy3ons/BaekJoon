import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		StringBuilder st = new StringBuilder();
		
		int[] a=new int[sc.nextInt()];
		for (int i=0;i<a.length;i++) {
			a[i]=sc.nextInt();
		}
		Arrays.sort(a);
		for (int i=0;i<a.length;i++) {
			st.append(a[i]+"\n");
		}
		
		System.out.print(st.toString());
	}
}
