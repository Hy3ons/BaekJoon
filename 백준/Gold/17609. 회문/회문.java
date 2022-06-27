import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());

        while(testcase-->0) {
            char[] arr = br.readLine().toCharArray();
            boolean penlin = true;
            for (int i=0;i<arr.length;i++) {
                if (arr[i]!=arr[arr.length-1-i]) {
                    penlin = false;
                    break;
                }
            }

            if (penlin) {
                bw.write("0\n");
                continue;
            }
            if (arr.length==3) {
                if (arr[0]==arr[1]||arr[1]==arr[2]||arr[2]==arr[0]) {
                    bw.write("1\n");
                } else {
                    bw.write("2\n");
                }
                continue;
            }

            if (arr.length%2==0) {
                int mid = arr.length / 2;
                boolean use = false;
                boolean check = true;
                for (int i=mid+1,j=mid-1;i<arr.length&&j>=0;j--) {
                    if (arr[i]!=arr[j]) {
                        if (!use) {
                            use = true;
                        } else {
                            check = false;
                            break;
                        }
                    } else {
                        i++;
                    }
                }
                mid--;
                if (check) {
                    bw.write("1\n");
                    continue;
                }
                check = true;
                use = false;
                for (int i=mid+1,j=mid-1;i<arr.length&&j>=0;i++) {
                    if (arr[i]!=arr[j]) {
                        if (!use) {
                            use = true;
                        } else {
                            check = false;
                            break;
                        }
                    } else {
                        j--;
                    }
                }
                if (check) {
                    bw.write("1\n");
                    continue;
                }
            } else {
                boolean usa = true;
                boolean use = false;
                int j = arr.length / 2 + 1;
                for (int i=j-1;i>=0&&j<arr.length;i--) {
                    if (arr[i]!=arr[j]) {
                        if (!use) {
                            use = true;
                        } else {
                            usa = false;
                            break;
                        }
                    } else {
                        j++;
                    }
                }
                if (usa) {
                    bw.write("1\n");
                    continue;
                }
                usa = true;
                use = false;
                j = arr.length / 2 -1;
                for (int i=j+1;i<arr.length&&j>=0;i++) {
                    if (arr[i]!=arr[j]) {
                        if (!use) {
                            use = true;
                        } else {
                            usa = false;
                            break;
                        }
                    } else {
                        j--;
                    }
                }

                if (usa) {
                    bw.write("1\n");
                    continue;
                }
            }

            bw.write("2\n");
        }
        bw.flush();
    }
}
