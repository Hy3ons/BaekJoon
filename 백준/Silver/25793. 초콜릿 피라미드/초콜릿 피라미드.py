import sys

T = int(sys.stdin.readline().rstrip())

prefix = [0] * 1000001

for i in range(len(prefix)):
    if i == 0:
        pass
    prefix[i] = prefix[i-1] + i ** 2 + i


def solve ():
    R, C = map(int, sys.stdin.readline().rstrip().split())
    R, C = max(R, C), min(R, C)

    alpha, y = R - C + 1, 1
    black = alpha * C * (C+1) + 2 * prefix[C-1]
    black -= R * C

    gamma = alpha -1
    white = gamma * C * (C+1) // 2 + prefix[C-1]
    gamma = alpha + 1
    if C > 1:
        white += gamma * (C-1) * C // 2 + prefix[C-2]

    print(black, white)

for i in range(T):
    solve()