import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class stone {
    int value;
    char color;
    stone (char color, int value) {
        this.color = color;
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());
        char[] color = br.readLine().toCharArray();

        StringTokenizer st = new StringTokenizer(br.readLine());
        stone[] arr = new stone[testcase];

        for (int i=0;i<testcase;i++) {
            arr[i] = new stone(color[i], Integer.parseInt(st.nextToken()));
        }

        //귀찮으니 스택으로 구현
        Stack<stone> stack = new Stack<>();
        stack.push(arr[0]);
        for (int i=1;i<arr.length;i++) {
            if (stack.peek().color!=arr[i].color) {
                stack.push(arr[i]);
            } else {
                if (stack.peek().value<arr[i].value) {
                    stack.pop();
                    stack.push(arr[i]);
                }
            }
        }

        arr = new stone[stack.size()];
        for (int i=arr.length-1;i>=0;i--) arr[i] = stack.pop();
        //예외처리들.
        if (arr.length==2) {
            System.out.println(0);
            return;
        } else if (arr.length==3) {
            System.out.println(arr[1].value);
            return;
        }
        
        arr[0].value = arr[arr.length-1].value = 0;
        PriorityQueue<Integer> qu = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i=0;i<arr.length;i++) {
            qu.offer(arr[i].value);
        }

        int takeAmount = arr.length%2==0 ? arr.length/2 - 1 : arr.length/2;

        long count = 0;
        for (int i=0;i<takeAmount;i++) {
            count += qu.poll();
        }
        System.out.println(count);



    }

}
