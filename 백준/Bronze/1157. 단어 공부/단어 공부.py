def main (str) :
    list = [0 for i in range(26)]
    ms = -2100000000

    for i in range(len(str)):
        index = ord(str[i])-ord('A')
        list[index] += 1
        ms = max(ms, list[index])

    answer = True
    index = -1

    for i in range(len(list)) :
        if (ms==list[i]) :
            index = i
            if answer :
                answer = False
            else :
                print('?')
                return

    print(chr(ord('A')+index))

str = input().upper()
main(str)