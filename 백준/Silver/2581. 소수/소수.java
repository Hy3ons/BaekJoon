import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner v1 = new Scanner(System.in);
		
		int m = v1.nextInt();
		int n = v1.nextInt();
		
		int[] pn = new int[n+100];
		
		for (int i=0; i<=n; i++) {
			pn[i] = 0;
		}
		
		
		L :for (int i = m ; i<=n; i++) {
			if (i%2==0&&i!=2) {
				pn[i] = 0;
				continue L;
			}
			
			for (int j = 3; j<i;j++) {
				double check = i/(double)j;
				if (check == (int)(i/j)) {
					pn[i] = 0;
					continue L;
				}
			}
			pn[i] = 1;
		}
		pn[1] = 0;
		pn[2] = 1;
		int sum =0;
		
		for (int i = m ; i<=n; i++) {
			if (pn[i]==1) {
				sum += i;
			}
		}
		
		int min = 0;
		G: for (int i = m ; i<=n; i++) {
			if (pn[i]==1) {
				min = i;
				break G;
			}
		}
		if (sum==0) {
			System.out.println(-1);
		} else {
			System.out.println(sum);
			System.out.println(min);
			
		}
		
	}
}