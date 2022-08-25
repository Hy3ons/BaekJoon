temp, b, c = map(int, input().split())

a = 1
while b > 0:
    if (b & 1) == 1:
        a *= temp
        a %= c

    temp *= temp
    temp %= c

    b >>= 1

print(a % c)