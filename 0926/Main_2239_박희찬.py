from sys import stdin
from pprint import pprint


def dfs(index):
    # solve 배열의 길이와 같아진다 = 모든 빈 칸 채움
    if index == len(solve):
        return

    # 1부터 9까지 숫자를 임의로 대입한 후
    # 유효한 숫자인지 검사 -> 성공 시 다음 인덱스로 재귀 호출
    for number in range(1, 10):
        x, y = solve[index][0], solve[index][1]
        lst[x][y] = number
        if check(x, y, number):
            dfs(index + 1)



# 부분 : 3x3 사각형에 1 ~ 9까지의 숫자가 유효한지 검사
# def square(row, col):
#     cnt = [z for z in range(10)]
#     for r in range(int(row / 3) + 1):
#         for c in range(int(col / 3) + 1):
#             cnt[lst[r][c]] += 1
#             if cnt[lst[r][c]] > 1:
#                 return False
#     return True

# 전체 : 행과 열 중복 검사
def check(row, col, num):
    # 행 중복 검사 = 중복시 False 반환
    for r in range(9):
        if solve[r][col] == num:
            return False

    # 열 중복 검사 = 중복시 False 반환
    for c in range(9):
        print(row, c)
        if solve[row][c] == num:
            return False

    return True


lst = [list(stdin.readline().strip()) for _ in range(9)]
solve = []

for i in range(9):
    for j in range(9):
        lst[i][j] = int(lst[i][j])
        if not lst[i][j]:
            solve.append([i, j])


# pprint(lst, width=50)
dfs(0)

# print(solve)
# pprint(lst, width=50)
