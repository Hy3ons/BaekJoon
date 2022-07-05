import java.io.*;
import java.util.StringTokenizer;

class Node {
    Node father;
    int value;
    Node (int value) {
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());

        while (testcase-->0) {
            int amount = Integer.parseInt(br.readLine());

            Node[] nodes = new Node[amount+1];
            for (int i=1;i<=amount;i++)
                nodes[i] = new Node(i);
            StringTokenizer st;
            for (int i=1;i<amount;i++) {
                st = new StringTokenizer(br.readLine());
                int goal1 = Integer.parseInt(st.nextToken());
                int goal2 = Integer.parseInt(st.nextToken());

                nodes[goal2].father = nodes[goal1];
            }
            st = new StringTokenizer(br.readLine());
            int goal1 = Integer.parseInt(st.nextToken());
            int goal2 = Integer.parseInt(st.nextToken());

            boolean[] visited = new boolean[amount+1];
            visited[goal1] = true;

            Node temp = nodes[goal1].father;
            while(temp!=null) {
                visited[temp.value] = true;
                temp = temp.father;
            }

            temp = nodes[goal2];
            while (temp!=null) {
                if (visited[temp.value]) {
                    bw.write(temp.value+"\n");
                    break;
                }
                temp = temp.father;
            }

        }
        bw.flush();
    }
}