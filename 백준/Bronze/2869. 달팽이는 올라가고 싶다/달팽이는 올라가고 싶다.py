import math
A, B, V = map(int, input().split())
V = max(0, V-A)
print(math.ceil(V / (A-B)) + 1)