from sys import stdin
from pprint import pprint

## BOJ_2239_스도쿠
# 167672KB, 6136ms ... 너무 비효율적이라 추후 재도전...


def dfs(depth):
    global flag
    # END : 깊이가 리스트의 길이와 같다면, 모든 빈 칸을 체크했다는 것.
    if depth == len(solve):
        if not flag:
            # pprint(lst, width=50)
            for row in range(9):
                for col in range(9):
                    print(lst[row][col], end='')
                print()

            # 답이 여러 개 있다면 제일 처음 찾은 정답이
            # 사전순이므로(낮은 숫자부터 대입해봤으므로)
            # 그 뒤의 정답은 출력할 필요 없음.
            flag = True
            return
        return
    if not flag:
        # 1 ~ 9까지 차례로 대입해봄
        for number in range(1, 10):
            # print(depth)
            x, y = solve[depth]

            # 1. 숫자를 넣었을 때 행 - 열 검사가 유효하다면
            if check(x, y, number):
                lst[x][y] = number  # 2. 숫자를 대입해보고
                if square(x, y):  # 3. 3 * 3 사각형에 대해 유효성 판단
                    dfs(depth + 1)  # 4. 유효하다면 그 숫자를 넣은 채로 다음 재귀 호출
                lst[x][y] = 0  # 백트래킹 -> 이전 상태로 복귀
    return

# 3 * 3 사각형의 유효성 검사
def square(row, col):
    # 받은 좌표를 계산하여 3 * 3 구역을 잡음
    row = (row // 3) * 3
    col = (col // 3) * 3
    cnt = [0] * 10  # 1 ~ 9 숫자 개수 체크

    for r in range(row, row + 3):
        for c in range(col, col + 3):
            # 1. 빈 칸이 아니면 카운팅
            if lst[r][c] > 0:
                cnt[lst[r][c]] += 1
            # 2. 특정 숫자가 2개 이상 들어가면 Fail
            if cnt[lst[r][c]] > 1:
                return False

    # 모든 유효성 검사가 완료되면 True 반환
    return True


# 행과 열에 대한 유효성 검사
def check(row, col, num):  # (row, col), 테스트할 숫자
    # 테스트할 숫자가 이미 행(열)에 적혀있다면 Fail
    for r in range(9):
        if lst[r][col] == num:
            return False

    for c in range(9):
        if lst[row][c] == num:
            return False

    # 모든 유효성 검사가 완료되면 True 반환
    return True


lst = [list(stdin.readline().strip()) for _ in range(9)]
solve = []  # 빈 칸의 좌표를 저장할 리스트
flag = False  # 정답을 찾았는지 판단 여부

# 입력받은 스도쿠를 숫자로 변환하여 리스트에 넣고,
# 빈 칸이라면 따로 solve 리스트에 넣어둠.
for i in range(9):
    for j in range(9):
        lst[i][j] = int(lst[i][j])
        if not lst[i][j]:
            solve.append([i, j])

# print(solve)
# print(len(solve))
dfs(0)
