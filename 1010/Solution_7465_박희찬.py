""" SWEA_7465 창용 마을 무리의 개수"""


def dfs(cur):  # 현재 탐색중인 사람의 번호
    visited[cur] = 1

    # 연결된 사람이 있다면 방문 후, 재귀 호출
    for nxt in edge[cur]:
        if not visited[nxt]:
            dfs(nxt)


for tc in range(1, int(input()) + 1):
    N, M = map(int, input().split())

    edge = [[] for _ in range(N + 1)]

    # 양방향 간선
    # 단방향으로 구성할 경우, 예를 들어
    # 6명 중 2 -> 5, 2 -> 6이라는 관계로 2만 일방적으로 알고 있는 상황을 보면
    # dfs를 1번부터 시작한다면 무리의 수를 정확히 알 수 있지만
    # 입력으로 주는 간선의 정보를 5 -> 2, 6 -> 2 처럼 역순으로만 주어도 찾아 낼 수 없다.
    # 내가 1번부터 시작해서 상관없을 줄 알았지만 오히려 입력을 줄 때 시작번호와 관계없이 조작된다는 점.
    # 그림으로 그려서 이해하는게 편함.
    for _ in range(M):
        a, b = map(int, input().split())
        edge[a].append(b)
        edge[b].append(a)

    res = 0  # 무리의 개수

    # 방문 배열
    visited = [0] * (N + 1)
    for i in range(1, N + 1):
        if not visited[i]:
            dfs(i)
            res += 1

    print(f'#{tc} {res}')
