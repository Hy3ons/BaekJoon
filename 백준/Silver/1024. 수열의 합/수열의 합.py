S , m = map(int, input().split())
def main(K):
    temp = (S << 1) + K - (K * K)
    if temp < 0:
        return False

    temp >>= 1
    if temp % K != 0:
        return False
    temp //= K
    for i in range(temp, temp+K):
        print(i, end=' ')
    return True

def start() :
    for i in range(m, 101):
        if main(i) :
            return

    print(-1)
start()