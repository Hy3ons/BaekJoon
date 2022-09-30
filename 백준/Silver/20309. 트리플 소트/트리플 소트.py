N = int(input())
arr = list(map(int, input().split()))

def solve ():
    for i in range(len(arr)):
        if (i + 1) % 2  != arr[i] % 2:
            print("NO")
            return

    print("YES")

solve()