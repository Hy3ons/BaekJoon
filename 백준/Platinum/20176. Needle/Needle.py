import math
import sys
sys.setrecursionlimit(30000)

load = [[] for _ in range(100001)]

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

def main():
    input = sys.stdin.readline
    arrays = []

    for i in range(3):
        n = int(input().rstrip())
        k = list(map(int, input().rstrip().split()))
        for j in range(len(k)):
            k[j] += 30001

        res = [0 for _ in range(63000)]
        for j in k:
            res[j] = 1
        arrays.append(res)

    arrays.append(start(arrays[0], arrays[2]))
    result = 0

    for i in range(2, len(arrays[3]), 2):
        if (i >> 1) >= len(arrays[1]):
            break
        if arrays[1][i >> 1]:
            result += arrays[3][i]

    print(result)
main()