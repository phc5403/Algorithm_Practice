''' SWEA_1767_프로세서 연결하기 '''
from pprint import pprint
import copy


def dfs(lst, idx, conn):  # 검사할 코어 번호, 연결된 코어 개수
    global cores, res_core
    # Pruning : 총 코어 개수 + 연결된 개수 - 현재 코어 번호 < 현재까지 최대로 연결된 개수
    # 전자가 후자보다 작다면 앞으로 남은 코어를 모두 연결해도 최대 개수를 넘을 수 없음
    if len(cores) + conn - idx < res_core:
        return

    # 모든 코어 확인 했다면 전선 개수 세기
    if idx == len(cores):
        find_line(lst, conn)
        return

    # 코어 하나씩 꺼내봄
    x, y = cores[idx][0], cores[idx][1]

    # 이미 연결되어있다면.
    if x == 0 or x == N - 1 or y == 0 or y == N - 1:
        dfs(lst, idx + 1, conn)

    else:
        # temp = copy.deepcopy(lst)
        temp = [tmp[:] for tmp in lst]

        for k in range(4):

            if bfs(lst, x, y, k) != 0:  # bfs()는 전선 개수를 반환하므로 1이상이면 연결이 가능했다는 것.
                dfs(lst, idx + 1, conn + 1)  # 연결된 개수 증가
            else:  # 전선 배치가 불가능하다면 다음 코어 번호로 재귀
                dfs(lst, idx + 1, conn)

            # lst = copy.deepcopy(temp)
            lst = [t[:] for t in temp]


def bfs(lst, x, y, d):
    line = 0
    nx, ny = x, y

    while True:
        nx += dx[d]
        ny += dy[d]

        # 1. 유효 범위
        if 0 <= nx < N and 0 <= ny < N:
            # 1-1. 전선 배치 가능 -> 개수 증가
            if lst[nx][ny] == 0:
                line += 1

            # 1-2. 코어 만남 or 전선 겹침 -> 배치 결과 = 불가능
            if lst[nx][ny] == 1 or lst[nx][ny] == 4:
                line = 0
                break

        else:  # 2. 유효 범위 벗어남
            break

    nx, ny = x, y

    # 전선 배치 가능하면 맵에 '4'로 표시
    for _ in range(line):
        nx += dx[d]
        ny += dy[d]
        lst[nx][ny] = 4

    return line  # 연결한 전선 개수 반환


def find_line(lst, conn):
    global res_core, res_line

    line_sum = 0

    for r in range(N):
        for c in range(N):
            if lst[r][c] == 4:
                line_sum += 1

    # 최대 코어 연결 개수 비교
    if res_core < conn:
        res_core = conn
        res_line = line_sum  # 그 때의 전선 개수 기록

    # 최대 코어 연결 개수가 같다면,
    elif res_core == conn:
        # 그 중 전선 개수 최솟값 갱신
        res_line = min(res_line, line_sum)


''' Main '''
for tc in range(1, int(input()) + 1):
    N = int(input())

    arr = [list(map(int, input().split())) for _ in range(N)]

    cores = []  # 맵에 있는 모든 코어 좌표
    before = 0  # 사전에 연결되어있는 코어 개수
    dx = [-1, 1, 0, 0]
    dy = [0, 0, -1, 1]

    for r in range(N):
        for c in range(N):
            if arr[r][c]:  # 코어 발견
                # 초기 위치가 외곽이라면 사전 연결 ++
                if r == 0 or r == N - 1 or c == 0 or c == N - 1:
                    before += 1

                cores.append([r, c])  # 사전에 연결된 코어

    # 최대 코어 연결 개수, 최소 전선 연결 개수
    res_core, res_line = float('-inf'), float('inf')

    # 검사 시작할 코어 번호, 사전 연결된 코어의 개수
    dfs(arr, 0, before)

    print(f"#{tc} {res_line}")
