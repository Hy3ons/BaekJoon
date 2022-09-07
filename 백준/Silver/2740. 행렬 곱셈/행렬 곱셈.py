def makeMatrix ():
    A, B = map(int, input().split())
    matrix = [list(map(int, input().split())) for _ in range(A)]

    return matrix

def func(matrix1, matrix2):
    A = len(matrix1)
    B = len(matrix2[0])

    res = [[0 for i in range(B)] for _ in range(A)]

    for i in range(A):
        for j in range(B):
            for k in range(len(matrix1[0])):
                res[i][j] += matrix1[i][k] * matrix2[k][j]
    return res
for a in func(makeMatrix(), makeMatrix()):
    print(*a)