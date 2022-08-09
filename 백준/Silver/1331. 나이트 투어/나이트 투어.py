import sys

def changer (loc):
    x = ord(loc[0]) - ord('A')
    y = int(loc[1]) - 1

    return x, y

def encode(x, y):
    return x*6 + y

road = [input() for _ in range(36)]

dx = [-2,-1,1,2,2,1,-1,-2]
dy = [1,2,2,1,-1,-2,-2,-1]

visited = [False for _ in range(36)]
nowX, nowY = changer(road[35])

def main():
    global nowX, nowY
    for i in range(0, 36):
        goalX, goalY = changer(road[i])
        canPass = True

        for j in range(8):
            tempX = nowX + dx[j]
            tempY = nowY + dy[j]

            if tempX < 0 or tempY < 0 or tempX > 5 or tempY > 5:
                continue

            if tempX == goalX and tempY == goalY:
                nowX = goalX
                nowY = goalY
                if visited[encode(nowX, nowY)]:
                    print("Invalid")
                    return
                else :
                    visited[encode(nowX, nowY)] = True
                    canPass = False
                    break
        if canPass:
            print("Invalid")
            return
    print("Valid")

main()