a = list(input())
for i in range(len(a)):
    if ord('a') <= ord(a[i]) <= ord('z'):
        a[i] = chr(-ord('a') + ord(a[i]) + ord('A'))
    else:
        a[i] = chr(ord(a[i]) - ord('A') + ord('a'))
print(*a, sep='')
