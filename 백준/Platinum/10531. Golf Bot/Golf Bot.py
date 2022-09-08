import math

def FFT(vector, w):
    if len(vector) == 1:
        return

    even = []
    odd = []

    for i in range(len(vector)):
        if i & 1:
            odd.append(vector[i])
        else:
            even.append(vector[i])

    FFT(even, w * w)
    FFT(odd, w * w)

    mul = complex(1, 0)
    n = len(vector) // 2

    for i in range(n):
        vector[i] = even[i] + mul * odd[i]
        vector[i + n] = even[i] - mul * odd[i]

        mul *= w


def start(a, b):
    size = 1
    while size <= len(a) or size <= len(b):
        size <<= 1
    size <<= 1

    while len(a) < size:
        a.append(0)
    while len(b) < size:
        b.append(0)

    init = complex(math.cos(2*math.pi / size), math.sin(2*math.pi/size))

    FFT(a, init)
    FFT(b, init)

    c = []
    for i in range(len(a)):
        c.append(a[i] * b[i])

    FFT(c, 1/init)

    for i in range(len(c)):
        c[i] /= size
        c[i] = round(c[i].real)

    return c

import sys

def main():
    input = sys.stdin.readline
    n = int(input().rstrip())
    a = [0 for _ in range(200002)]
    for _ in range(n):
        a[int(input().rstrip())] = 1

    k = a.copy()
    c = start(a, a.copy())

    for i in range(len(k)):
        if k[i]:
            c[i] = 1

    m = int(input().rstrip())
    count = 0
    for _ in range(m):
        if c[int(input().rstrip())]:
            count += 1
    print(count)
main()