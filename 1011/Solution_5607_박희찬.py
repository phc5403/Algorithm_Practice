''' SWEA_5607_조합 '''
import math

''' WA -> 페르마의 소정리를 다시 공부하여 재풀이 할 예정. '''

# 구해야할 조합을 분할정복으로 계산
def divideConquer(n, r):
    global M
    if r == 1:
        return n

    temp = divideConquer(n, r // 2) % M

    if r % 2 == 0:
        return math.pow(temp, 2) % M
    else:
        return (math.pow(temp, 2) % M * n) % M


for tc in range(1, int(input()) + 1):
    N, R = map(int, input().split())

    M = 1234567891  # 나머지를 구해야할 수

    # 페르마의 소정리를 이용하기위해 필요한 정보 계산
    A = math.factorial(N)
    B = (math.factorial(N - R) * math.factorial(R)) % M

    C = divideConquer(B, M - 2)

    print(f'#{tc} {int(A * C % M)}')
