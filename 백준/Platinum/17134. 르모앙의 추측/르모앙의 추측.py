import math
import sys
sys.setrecursionlimit(30000)

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

    eratos = [1 for _ in range(1000002)]
    eratos[0] = 0
    eratos[1] = 0

    prime = []

    for i in range(2, 1000002):
        if eratos[i]:
            prime.append(i)
            for j in range(i * i, 1000002, i):
                eratos[j] = 0

    eratos[2] = 0
    b = [0 for _ in range(1000002)]

    for i in prime:
        if (i << 1) >= len(b):
            break
        b[i << 1] = 1

    c = start(eratos, b)


    N = int(input().rstrip())
    for n in [int(input().rstrip()) for _ in range(N)]:
        print(c[n])

main()