""" SWEA_5656_벽돌깨기"""
from collections import deque
import time
import copy

## 현재 TestCase 47 / 50으로 WA, 반례 찾는 중...


# 벽돌을 N번 떨어뜨릴 위치의 경우의 수를 중복 순열로 계산
def perm(cnt):
    if cnt == N:
        # 떨어뜨릴 위치와 순서를 담은 리스트를 BFS()로 보냄.
        lst = copy.deepcopy(arr)
        bfs(lst, order)
        return

    for i in range(COL):
        order[cnt] = num[i]
        perm(cnt + 1)


# BFS : 구슬을 쏘아 처음 만나는 벽돌을 찾음
def bfs(lst, orderList):
    global res
    qu = deque()

    # 쏘는 위치 & 순서를 큐에 넣음
    for y in range(N):
        qu.append([0, orderList[y]])

    while qu:
        x, y = qu.popleft()

        nx = x
        while nx + 1 < ROW:
            nx += 1

            # 가장 처음 만나는 벽돌의 위치를 찾음
            if lst[nx][y] > 0:
                bomb(lst, nx, y)
                break

    # 남은 벽돌의 개수를 계산
    brick = 0
    for r in range(ROW):
        for c in range(COL):
            if lst[r][c]:
                brick += 1

    # 최소 개수 갱신
    res = min(res, brick)


# 벽돌의 폭발
def bomb(lst, x1, y1):
    bu = deque()
    bu.append([x1, y1])

    while bu:
        x, y = bu.popleft()

        if lst[x][y] >= 2:
            # 벽돌이 폭발할 범위
            chain = lst[x][y]

            # 상하좌우로 퍼질 범위 계산
            for idx in range(-chain + 1, chain):
                # 터지는 위치는 다음 큐에 넣을 필요 없음.
                if idx == 0:
                    continue

                # 수직, 수평 = 상하좌우로 터질 벽돌을 다음 큐에 넣음.
                if 0 <= x + idx < ROW:
                    bu.append([x + idx, y])
                if 0 <= y + idx < COL:
                    bu.append([x, y + idx])

        # 1. 위의 if를 거치지 않는 벽돌의 숫자 1은 0으로 소멸하고,
        # 2. if를 거친 벽돌 본인도 소멸하게 됨.
        lst[x][y] = 0

    # 벽돌의 낙하 계산
    for col in range(COL):
        gravity = deque()
        for row in range(ROW):
            # [0][0]부터 세로로 계산하기 위해 큐에 넣음
            if lst[row][col]:
                gravity.append([row, col])

        while gravity:
            r, c = gravity.pop()

            # 자신의 아래 위치의 값이 0인 곳 까지 떨어짐
            nr = r
            while nr + 1 < ROW:
                nr += 1
                if lst[nr][c] == 0:
                    lst[nr][c] = lst[r][c]
                    lst[r][c] = 0
                    r = nr


""" Main """
TC = int(input().strip())

for tc in range(1, TC + 1):
    N, COL, ROW = map(int, input().strip().split())

    arr = [list(map(int, input().split())) for _ in range(ROW)]

    num = [number for number in range(COL)]
    order = [-1] * (N + 1)

    dx, dy = [-1, 1, 0, 0], [0, 0, -1, 1]

    # 남은 벽돌 개수의 최댓값
    res = float('inf')

    perm(0)

    print(f'#{tc} {res}')
    # print(eee - sss)
