dic = {'kor' :1, 'eng' : 2, 'math' : 3, 'apple' : 4, 'pear':5, 'orange':6, 'red':7,'blue':8,'green':9}
n, m = map(int, input().split())

ss = [0 for _ in range(2048)];

for _ in range(n):
    f = list(map(str, input().split()))
    j = 0
    for i in f:
        j |= 1 << dic[i]
    ss[j] += 1

for _ in range(m):
    f = list(map(str, input().split()))
    j = 0
    for i in f:
        if i == '-':
            continue
        j |= 1<< dic[i]

    count = 0
    for i in range(1500):
        if i & j == j:
            count += ss[i]
    print(count)