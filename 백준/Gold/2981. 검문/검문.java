import java.util.*;
import java.io.*;

public class Main {
	
	 public static int gcd(int p, int q)
	 {
		if (q == 0) return p;
		return gcd(q, p%q);
	 }
	 
	 public static String[] divisor(int a) {
			int[] divisors = new int[2000000];
		
			StringBuilder ssss = new StringBuilder();
			int result2ff =0;
			for (int i=1;i<=Math.sqrt(a);i++) {
				if (a%i==0) {
					divisors[result2ff] = i;
					result2ff++;
				}
			}
			
			for (int i=result2ff-1;i!=-1;i--) {
				divisors[result2ff] = a/divisors[i]; 
				result2ff++;
			}
			
			for (int i=1; ;i++) {
				if (divisors[i]==0) {
					break;
				}
				
				if (divisors[i]==divisors[i-1]) {
					continue;
				}
				ssss.append(divisors[i]+" ");
			}
			
			return ssss.toString().split(" ");
	 }
	 
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int[] n = new int[sc.nextInt()];
		for (int i=0;i<n.length;i++) {
			n[i] = sc.nextInt();
		}
		Arrays.sort(n);
		
		String[] out = divisor(n[1]-n[0]);
		
		G:for (int i=0; i<out.length;i++) {
			int hh = n[0]%Integer.parseInt(out[i]);
			for (int z=0; z<n.length;z++) {
				if ((n[z]%Integer.parseInt(out[i]))!=hh) {
					continue G;
				}
			}
			sb.append(out[i]+" ");
		}
		
		System.out.println(sb.toString());
	}

}
