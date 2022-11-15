T = int(input())

def solve ():
    N = int(input())
    a = []
    for i in range(N):
        a.append(list(map(int, input().split())))

    K, D, A = map(int, input().split())

    ans = 0
    for m in a:
        temp = m[0] * K - m[1] * D + m[2] * A
        if temp > 0:
            ans += temp
    print(ans)
for i in range(T):
    solve()