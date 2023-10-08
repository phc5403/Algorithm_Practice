""" SWEA_5656 벽돌 깨기 """
from pprint import pprint
import copy
from collections import deque


# 중복 순열을 통해 벽돌을 떨어뜨릴 위치 선정
def perm(cnt):
    if cnt == N:
        # 각 라운드마다 독립적인 상황이므로, 맵을 깊은 복사로 전달
        lst = copy.deepcopy(arr)
        bfs(lst, order)
        return

    for i in range(COL):
        order[cnt] = number[i]
        perm(cnt + 1)


def bfs(lst, order):
    global res
    qu = deque()

    for c in range(N):
        # Pruning : 최대한 많은 벽돌을 깨야하기 때문에,
        # 낙하할 곳에 벽돌이 없다면 해당 라운드는 진행 할 필요 X
        if not isBrick(lst, order[c]):
            return
        else:  # 낙하할 좌표를 큐에 넣음
            qu.append([0, order[c]])

    while qu:
        x, y = qu.popleft()

        nx = x
        while nx < ROW:
            if lst[nx][y]:
                # 낙하하면서 처음 닿는 벽돌 찾음.
                artIsExplosion(lst, nx, y)
                break
            nx += 1

    # END : 모든 구슬을 던진 후, 남아있는 벽돌 계산
    brick = 0
    for r in range(ROW):
        for c in range(COL):
            if lst[r][c]:
                brick += 1

    # 남은 벽돌의 최솟값 갱신
    res = min(res, brick)


# "예술은 폭발이다."
def artIsExplosion(lst, x, y):
    bu = deque()
    bu.append([x, y])

    # 낙하 후 폭발 범위 계산
    while bu:
        x, y = bu.popleft()
        chain = lst[x][y]  # 폭발 범위

        # 상하좌우로 (벽돌의 번호 - 1)만큼 연쇄 폭발
        for idx in range(-chain + 1, chain):
            # +0은 자신이므로 제외
            if idx == 0:
                continue
            if 0 <= x + idx < ROW:  # 행간 연쇄 폭발
                bu.append([x + idx, y])
            if 0 <= y + idx < COL:  # 열간 연쇄 폭발
                bu.append([x, y + idx])
        lst[x][y] = 0  # 폭발 된 벽돌 번호 = 0

    # 폭발이 끝난 후 남은 벽돌 중력 낙하
    for col in range(COL):
        gu = deque()  # gravity_Queue
        for row in range(ROW):
            if lst[row][col]:  # 남은 벽돌의 번호를 큐에 넣음
                gu.append(lst[row][col])
                lst[row][col] = 0

        # 행의 역순으로 남은 벽돌을 꺼내서 배치
        for newRow in range(ROW - 1, -1, -1):
            if gu:
                lst[newRow][col] = gu.pop()
            else:
                break
    return


# 낙하 할 곳에 벽돌이 있는지 체크
def isBrick(lst, c):
    check = False
    for r in range(ROW):
        if lst[r][c]:
            check = True
    return check


""" Main """
TC = int(input())
for tc in range(1, TC + 1):
    N, COL, ROW = map(int, input().split())

    arr = [list(map(int, input().split())) for _ in range(ROW)]
    dx = [-1, 1, 0, 0]
    dy = [0, 0, -1, 1]

    res = float('inf')  # 결괏값

    # 중복 순열을 찾기 위한 리스트
    number = [num for num in range(COL)]
    order = [0] * N

    perm(0)

    print(f'#{tc} {res}')
