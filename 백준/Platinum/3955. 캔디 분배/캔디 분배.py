import sys

times = []


def gcd(a, b):
    mul = a // b
    remain = a % b

    if (remain == 0):
        return b
    times.append(mul)

    return gcd(b, remain)


testcase = int(sys.stdin.readline())
MAX = 1000000000
imf = 'IMPOSSIBLE'

for _ in range(testcase):
    K, C = map(int, sys.stdin.readline().split())

    if C == 1:
        if K + 1 > MAX:
            print(imf)
        else:
            print(K + 1)
        continue
    times.clear()
    if K > C:
        if gcd(K, C) != 1:
            print(imf)
            continue

        prevX, prevY = 1, 0
        x, y = 0, 1

        for mul in times :
            tempX = x * -mul + prevX
            tempY = y * -mul + prevY
            prevX, prevY = x, y
            x, y = tempX , tempY
        while y > MAX :
            y -= K

        while y < 0 or y * C < K :
            y += K

        if (y > MAX) :
            print(imf)
        else :
            print(y)
    else :
        if gcd(C, K) != 1 :
            print(imf)
            continue

        prevX, prevY = 1, 0
        x, y = 0, 1

        for mul in times:
            tempX = x * -mul + prevX
            tempY = y * -mul + prevY
            prevX, prevY = x, y
            x, y = tempX, tempY

        while x > MAX:
            x -= K
            
        while x < 0 or x * C < K:
            x += K

        if (x > MAX):
            print(imf)
        else:
            print(x)