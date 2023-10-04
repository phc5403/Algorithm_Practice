""" SWEA_5643_키 순서 """
from pprint import pprint


# edgeBig, edgeSmall을 순회한 후의 각 lst를 받아서,
# 최종적으로 학생 당 키의 대소를 알고 있는 사람의 수를 넘버링
def counting(arr):
    for subArr in arr:
        for num in subArr:
            cnt[num] += 1


def dfs(edge, cur, visited):
    global curStu
    visited[cur] = True

    # 연결된 간선이 있다면,
    for nxt in edge[cur]:
        if not visited[nxt]:
            # 자신보다 큰(작은) 학생의 번호를 lst에 기록
            lst[curStu].append(nxt)
            dfs(edge, nxt, visited)


TC = int(input())

for tc in range(1, TC + 1):
    N = int(input())
    M = int(input())

    edgeBig = [[] * (N + 1) for _ in range(N + 1)]  # 자신보다 큰 사람을 저장
    edgeSmall = [[] * (N + 1) for _ in range(N + 1)]  # 자신보다 작은 사람을 저장

    # 받은 입력값을 각각 edgeX에 맞게 채움
    for _ in range(M):
        lower, higher = map(int, input().split())
        edgeBig[lower].append(higher)
        edgeSmall[higher].append(lower)

    # Index번 학생 입장에서 자신이 키의 대소를 알고 있는 사람의 수를 저장
    cnt = [0] * (N + 1)

    # 해당 시점의 lst = 자신보다 큰 사람의 수를 넘버링 할 리스트
    lst = [[] * (N + 1) for _ in range(N + 1)]
    for start in range(1, N + 1):
        curStu = start  # 현재 학생입장에서 저장해야 하므로 따로 변수로 기록.
        visited = [False] * (N + 1)  # 방문 배열
        dfs(edgeBig, start, visited)

    counting(lst)

    # 해당 시점의 lst = 자신보다 작은 사람의 수를 넘버링 할 리스트
    lst = [[] * (N + 1) for _ in range(N + 1)]
    for start in range(1, N + 1):
        curStu = start
        visited = [False] * (N + 1)
        dfs(edgeSmall, start, visited)

    counting(lst)

    res = 0  # 최종 출력 값

    # (총 N명 - 자신을 제외하고 키의 대소를 알고 있는 사람)
    # = 자신의 키가 몇 번째인지 알 수 있는 사람.
    for idx in range(1, N + 1):
        if cnt[idx] == (N - 1):
            res += 1

    print(f'#{tc} {res}')
