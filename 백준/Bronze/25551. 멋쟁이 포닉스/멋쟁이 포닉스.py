a = [list(map(int, input().split())) for _ in range(3)]
c1 = min(a[1][0], a[0][1], a[2][1])
c2 = min(a[1][1], a[0][0], a[2][0])

if c1 == c2:
    print(c1 * 2)
else:
    print(min(c1, c2) * 2 + 1)