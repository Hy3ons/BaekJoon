#include <iostream>
#include <bitset>

using namespace std;

int main () {

    int stage;

    while(true) {
        cin >> stage;

        if (stage==0) break;

        long testcase = stage;

        long ary[stage][stage+1];

        for (int i=0;i<testcase;i++) {
            for (int j=0;j<testcase+1;j++) {
                cin >> ary[i][j];
            }
        }

        long dp[stage][1<<testcase];
        bool check[stage][1<<testcase];

        int p_test = 1<<stage;
        for (int i=0;i<stage;i++) {
            for (int j=0;j<p_test;j++) {
                check[i][j] = false;
                dp[i][j] = 0;
            }
        }
        // 현재 있는 스테이지를1차원에. 깬스테이지 기록과 현재 가지고 있는 무기들을 2차원에
        for (int i=0;i<stage;i++) {
            dp[i][1<<i] = ary[i][0];
            check[i][1<<i] = true;
        }

        for (int T=1;T<testcase;T++) {

            for (int i=0;i<p_test;i++) {
                if (bitset<32>(i).count()!=T) continue;

                for (int j=0;j<stage;j++) {
                    if (!check[j][i]) continue;

                    for (int k=0;k<testcase;k++) {
                        if ((i&(1<<k))==0) { //k번째 스테이지가 안깨져 잇다.

                            for (int g=0;g<stage;g++) {
                                if ((i&(1<<g))!=0) { //g장비 존재.

                                    if (check[k][i|(1<<k)]) {
                                        dp[k][i|(1<<k)] = min(dp[k][i|(1<<k)], dp[j][i] + ary[k][g+1]);
                                    } else {
                                        check[k][i|(1<<k)] = true;
                                        dp[k][i|(1<<k)] = dp[j][i] + ary[k][g+1];
                                    }
                                }
                            }
                            dp[k][i|(1<<k)] = min(dp[j][i] + ary[k][0], dp[k][i|(1<<k)]);
                        }
                    }
                }
            }
        }

        long result = 1000000000;

        for (int i=0;i<stage;i++) {
            if (check[i][p_test-1]) result = min(dp[i][p_test-1], result);
        }

        cout << result << endl;

    }





    return 0;
}
