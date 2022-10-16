r = [1, 1, 2, 2, 2, 8]
i = list(map(int, input().split()))

for k in range(6):
    print(r[k] - i[k], end=' ')