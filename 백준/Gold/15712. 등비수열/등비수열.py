a, r, n, m = map(int, input().split())

dp = [0 for _ in range(35)]
dp[0] = 1
for i in range(1, 35):
    dp[i] = dp[i-1] + dp[i-1] * pow(r, 1 << (i-1), m)
    dp[i] %= m

res = 0
c = 0

for i in range(33):
    if n & (1<<i):
        res += dp[i] * pow(r, c, m);
        res %= m
        c += 1 << i
res *= a
res %= m
print(res)


