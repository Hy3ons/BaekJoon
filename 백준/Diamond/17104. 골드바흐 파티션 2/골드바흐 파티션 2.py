import math

def FFT(vector, w):
    if (len(vector) == 1):
        return

    temp = len(vector) // 2
    even = []
    odd = []
    for i in range(len(vector)):
        if i & 1:
            odd.append(vector[i])
        else :
            even.append(vector[i])

    FFT(even, w * w)
    FFT(odd, w * w)

    mul = complex(1, 0)

    for i in range(len(vector)//2):
        vector[i] = even[i] + mul * odd[i]
        vector[i + len(vector) // 2] = even[i] - mul *odd[i]

        mul *= w

def start(a):
    n = 1
    while n <= len(a):
        n <<= 1
    n <<= 1

    while len(a) < n:
        a.append(0)

    w = complex(math.cos(2*math.pi/n), math.sin(2*math.pi/n))
    FFT(a, w)

    c = []
    for i in range(len(a)):
        c.append(a[i] * a[i])

    FFT(c, 1/w)
    for i in range(len(c)):
        c[i] = int(round(c[i].real / n))
    return c

MAX = 1000050
import sys
sys.setrecursionlimit(10000)

def main():
    eratos = [1 for _ in range(MAX)]
    ready = [0 for _ in range(500050)]
    eratos[0] = eratos[1] = 0
    same = [0 for _ in range(MAX)]
    for i in range(3, MAX, 2):
        if eratos[i]:
            ready[(i - 1) >> 1] = 1
            if (i-1) < MAX:
                same[i - 1] += 1
            for j in range(i*i, MAX, i):
                eratos[j] = 0

    c = start(ready)
    input = sys.stdin.readline
    for _ in range(int(input().rstrip())):
        value = int(input().rstrip())
        if value == 4:
            sys.stdout.write('1\n')
            continue
        idx = (value >> 1) - 1
        sys.stdout.write(str(((c[idx] - same[idx]) >> 1 ) + same[idx])+'\n')
    sys.stdout.flush()
main()