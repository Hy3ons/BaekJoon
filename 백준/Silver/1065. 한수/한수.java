import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		
		int input = scn.nextInt();
		int result = 0;
		
		for (int i = 1 ; i<=input ; i++) {	
			if (i<=99) {
				result++;
				continue;
			}
			
			if (i>=100 && i<=999) {
				String[] array = String.valueOf(i).split("");
				if ((Integer.parseInt(array[0])+Integer.parseInt(array[2]))/2.0 == Integer.parseInt(array[1])) {
					result++;
					continue;
				}
			}
		}
			
		System.out.println(result);
	}

}
