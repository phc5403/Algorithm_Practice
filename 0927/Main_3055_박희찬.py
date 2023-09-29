from sys import stdin
from collections import deque
from pprint import pprint


def bfs(que):
    global forest, dist

    while que:
        x, y = que.popleft()

        # 현재 위치가 도착점이라면, 해당 거리를 Return
        if [x, y] == [endX, endY]:
            return dist[x][y]

        for k in range(4):
            nx = x + dx[k]
            ny = y + dy[k]

            # 다음 좌표가 갈 수 있는 위치일 때.
            if 0 <= nx < R and 0 <= ny < C:
                # 1. 최초에, 비버 위치를 먼저 넣었으므로 비버가 선 이동.
                # 1-1. 비버는 다음 위치가 길 or 도착점인 경우 이동 가능
                if forest[x][y] == 'S' and (forest[nx][ny] == '.' or forest[nx][ny] == 'D'):
                    forest[nx][ny] = 'S'  # 이동한 곳을 비버 위치로 갱신
                    dist[nx][ny] = dist[x][y] + 1  # 이동한 거리 갱신
                    que.append([nx, ny])

                # 2. 물은 다음 위치가 길 or 비버(일 경우 덮어씀)인 경우 이동 가능
                elif forest[x][y] == '*' and (forest[nx][ny] == '.' or forest[nx][ny] == 'S'):
                    forest[nx][ny] = '*'  # 이동한 곳을 물로 덮음
                    que.append([nx, ny])

    return "KAKTUS"


R, C = map(int, stdin.readline().split())

# 숲
forest = [list(stdin.readline().strip()) for _ in range(R)]

# 이동 거리를 표시할 맵
dist = [[0] * C for _ in range(R)]

dx = [-1, 1, 0, 0]
dy = [0, 0, -1, 1]
qu = deque()
endX, endY = 0, 0  # 도착점

for i in range(R):
    for j in range(C):
        # 비버 위치를 큐에 먼저 넣음
        if forest[i][j] == 'S':
            qu.append([i, j])
        # 도착점 좌표를 할당
        if forest[i][j] == 'D':
            endX, endY = i, j

# 물의 위치를 큐에 넣음
for i in range(R):
    for j in range(C):
        if forest[i][j] == '*':
            qu.append([i, j])

print(bfs(qu))
