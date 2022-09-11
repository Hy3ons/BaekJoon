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


def start(a):
    size = 1
    while size <= len(a):
        size <<= 1
    size <<= 1

    while len(a) < size:
        a.append(0)

    init = complex(math.cos(2*math.pi / size), math.sin(2*math.pi/size))

    FFT(a, init)

    c = []
    for i in range(len(a)):
        c.append(a[i] * a[i])

    FFT(c, 1/init)

    for i in range(len(c)):
        c[i] /= size
        c[i] = round(c[i].real)

    return c

def main():
    N = int(input())
    base = [0 for _ in range(N+100)]
    ans = [0 for _ in range(N+100)]

    same = [0 for _ in range(N + 100)]


    for i in range(1, N):
        base[(i ** 2) % N] += 1
        same[(2 * (i ** 2)) % N] += 1

    c = start(base)
    for i in range(len(c)):
        ans[i % N] += c[i]
    result = 0
    for i in range(1, N):
        idx = (i**2)%N
        result += ((ans[idx]-same[idx]) >> 1) + same[idx]

    print(result)


main()