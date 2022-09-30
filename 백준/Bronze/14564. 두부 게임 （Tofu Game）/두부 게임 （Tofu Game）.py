N, M, A = map(int, input().split())
end = M // 2 + 1

now = A

while 1:
    num = int(input())
    if num == end:
        break

    bet = abs(end - num)
    if num < end:
        now -= bet
        if now <= 0:
            now += N
    else:
        now += bet
        if now > N:
            now -= N
    print(now)
print(0)