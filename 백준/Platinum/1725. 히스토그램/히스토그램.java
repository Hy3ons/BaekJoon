import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static int[] arr;
    public static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(br.readLine());

        arr = new int[length];
        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(br.readLine());

        conquer(0, length-1);
        System.out.println(result);
    }

    public static void conquer (int start, int end) {
        if (start == end) {
            result = Math.max(result, arr[start]);
            return;
        }

        int mid = start + end >> 1;
        int nowMin = arr[mid];

        for (int i=mid, j = mid;;) {
            if (i <= start) {
                for (j++;j<=end;j++) {
                    nowMin = Math.min(nowMin, arr[j]);
                    result = Math.max(result, nowMin * (j - i + 1));
                }
                break;
            }
            if (j >= end) {
                for (i--;i>=start;i--) {
                    nowMin = Math.min(nowMin, arr[i]);
                    result = Math.max(result, nowMin * (j - i + 1));
                }
                break;
            }

            if (arr[i-1] > arr[j+1]) {
                i--;
                nowMin = Math.min(nowMin, arr[i]);
                result = Math.max(result, nowMin * (j - i + 1));
            } else if (arr[i-1] == arr[j+1]) {
                i--;
                j++;
                nowMin = Math.min(nowMin, arr[i]);
                result = Math.max(result, nowMin * (j - i + 1));
            } else {
                j++;
                nowMin = Math.min(nowMin, arr[j]);
                result = Math.max(result, nowMin * (j - i + 1));
            }

        }


        conquer(start, mid);
        conquer(mid+1, end);
    }

}
