result = []

testcase = int(input())
for i in range(testcase):
    str = input()
    temp = 0
    stack = 1
    for j in range(len(str)) :
        if str[j]=='O' :
            temp += stack
            stack += 1
        else :
            stack = 1
    result.append(temp)
for i in result :
    print(i)