def func (c) :
    return int(c - ord('A'))

def main (str) :
    list = [0 for i in range(26)]
    ms = -2100000000

    for i in range(len(str)):
        list[func(ord(str[i]))] += 1
        ms = max(ms, list[func(ord(str[i]))])

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

str = input()
str = str.upper()
main(str)