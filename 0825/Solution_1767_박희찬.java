import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * DFS()에서 가지치기 여부에 따른 TestCase 3개의 시간과 반복 횟수
 * Pruning X : 0.57s / 1024, 4096, 262144
 * Pruning O : 0.13s / 35, 536, 812
 * 
 */
public class Solution { // 1767_프로세서 연결하기
	static StringBuilder sb;
	static StringTokenizer st;
	static int TC;
	static int N; // 맵 크기
	static int[][] lst;  // 원본 맵
	static List<Core> cores;  // 각 코어들 좌표를 저장
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int coreLen;  // 코어 개수
	static int res; // 최종 출력값(전선 개수)
	static int maxCore;  // 최대 코어 연결 개수
	static int test; // DFS 몇 번 도는지 체크(문제와 상관없음)

	static class Core {
		int x, y;

		public Core(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		TC = Integer.parseInt(st.nextToken());

//		for (int tc = 1; tc < 2; tc++) {
		for (int tc = 1; tc < TC + 1; tc++) {
			res = Integer.MAX_VALUE;
			maxCore = Integer.MIN_VALUE;

			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());

			lst = new int[N][N]; // static
			cores = new ArrayList<>();

			int cnt = 0; // 처음에 켜져있는 코어의 개수

			for (int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for (int col = 0; col < N; col++) {
					int temp = Integer.parseInt(st.nextToken());

					// 1. 코어를 발견
					if (temp == 1) {
						// 1-1. 가장자리에 위치한 Core = 전원 연결
						if (row == 0 || col == 0 || row == N - 1 || col == N - 1) {
//							System.out.println(row + ", " + col);
							cnt++;
						}
						cores.add(new Core(row, col));
					}
					lst[row][col] = temp;
				}
			}

			// 총 코어 개수
			coreLen = cores.size();

//			test = 0;/////////////

			// 원본 맵, 코어 번호, 초기에 켜져있는 코어 개수
//			System.out.println("StartCnt : " + cnt);
			dfs(lst, 0, cnt);

			//
			sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(res);
//			System.out.println("maxCORE: " + maxCore);
			System.out.println(sb);
//			System.out.println(test);
		}
		// TC

	} // Main

	/**
	 * 코어를 하나씩 꺼내어, 각 코어당 4방향으로 전선을 깔아보는 함수
	 * 
	 * @param lst     : 맵
	 * @param cNum    : 코어 번호(작업할 코어 선택)
	 * @param coreCnt : 현재까지 연결되어있는 코어 개수
	 */
	static void dfs(int[][] lst, int cNum, int coreCnt) {
		/* Pruning condition
		 * 총 코어 개수 - (현재 체크한 코어 개수 + 현재 연결된 코어 개수)
		 * 가 현재까지 최대 연결된 코어 개수보다 적다면,
		 * 남은 코어가 모두 연결되더라도 현재까지 찾은 최대 연결 개수를 못 넘음.
		 */
		if (coreLen - cNum + coreCnt < maxCore) {
			return;
		}
		
		// 모든 코어를 확인했다면, 전선 개수 계산
		if (cNum == coreLen) {
//			test++;///////
			electric(lst, coreCnt);

			return;
		}

		// 코어 좌표쌍 처음부터 꺼내기
		int x = cores.get(cNum).x;
		int y = cores.get(cNum).y;

		// 1. 외곽이라 이미 코어 전원이 켜져있다면.
		if (x == 0 || y == 0 || x == N - 1 || y == N - 1) {
			dfs(lst, cNum + 1, coreCnt);
		}
		
		// 2. 외곽이 아니라 전선을 연결해야 한다면.
		else {
			// 현재 맵을 임시 copyLst에 임시 저장
			int[][] copyLst = deepCopy(lst);
			
			// 0, 1, 2, 3 -> 상하좌우
			for (int angle = 0; angle < 4; angle++) {
				
				// contact()에서 1 이상을 반환 받으면 코어가 연결된 것임.
				if (contact(lst, x, y, angle + 1) != 0) {
					dfs(lst, cNum + 1, coreCnt + 1);
				} 
				// contact()에서 0을 반환받으면, 연결을 못함
				// 전선이 겹치거나, 코어를 만나서 배치 불가한 경우.
				else {
					dfs(lst, cNum + 1, coreCnt);
				}
				
				// 현재 맵을 원상 복구
				lst = deepCopy(copyLst);
			} // for

		}
	}

	/**
	 * 주어진 방향대로 전선을 배치해보는 함수
	 * @param lst : 작업할 맵
	 * @param x : 시작 위치(코어)
	 * @param y : 시작 위치(코어)
	 * @param dir : 전선을 배치할 방향
	 * @return : 연결된 전선 개수
	 */
	static int contact(int[][] lst, int x, int y, int dir) {
		// 전선이 코어에 닿거나, 전선이 겹치면 배치 불가
		// 방해 없이 경계 끝까지 가야 코어 On
		
		int line = 0; // 전선 개수
		dir %= 4;  // 방향

		int nx = x;
		int ny = y;

		while (true) {
			nx += dx[dir];
			ny += dy[dir];

			// 1. 범위를 벗어나지 않고,
			if (isIn(nx, ny)) {
				// 2. 배치 할 수 있는곳이면 전선 수 증가
				if (lst[nx][ny] == 0) {
					line++;
				}

				// 3. 코어에 막히거나, 전선끼리 겹쳐버리면,
				// 배치할 수 없으므로 전선 수를 0개로 돌림.
				if (lst[nx][ny] == 1 || lst[nx][ny] == 4) {
					line = 0;
					break;
				}
			} else {
				break;
			}

		} // while

		int lineX = x;
		int lineY = y;

		// 위에서 계산한 전선 수 만큼 전선을 배치
		for (int idx = 0; idx < line; idx++) {
			lineX += dx[dir];
			lineY += dy[dir];
			lst[lineX][lineY] = 4;  // 편의상 전선을 4로 배치함
		}

		// 전선 수 0: 배치 할 수 없음.
		// 전선 수 > 1 : 끝까지 배치하여 코어가 연결됨.
		return line; 
	}

	/**
	 * DFS()에서 작업될 맵을 복사하는 함수
	 * @param origin : 깊은 복사할 원본 맵
	 * @return : 깊은 복사된 새로운 맵
	 */
	static int[][] deepCopy(int[][] origin) {
		int[][] temp = new int[N][N];
		for (int i = 0; i < N; i++) {
			temp[i] = Arrays.copyOf(origin[i], N);
		}

		return temp;
	}

	/**
	 * 현재 작업된 맵에 전선 수를 계산하고,
	 * 최대 코어 연결 개수와 최종 출력할 전선수를 계산하는 함수
	 * @param lst : 전선 수를 조사할 맵
	 * @param coreCnt : 현재 조사할 맵에 연결되어 있는 코어 개수
	 */
	static void electric(int[][] lst, int coreCnt) {
		int sum = 0;

		// 전선 수를 셈
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (lst[r][c] == 4) {
					sum++;
				}
			}
		}

		// 최대 코어 연결 개수 갱신, res는 그 때의 전선 수
		if (maxCore < coreCnt) {
			maxCore = coreCnt;
			res = sum;
	
		} 
		// 최대 코어 연결 개수가 같다면 전선 수가 최소인것으로 갱신.
		else if (maxCore == coreCnt) {
			res = Math.min(res, sum);
		}

	}

	static boolean isIn(int nx, int ny) {
		if (0 <= nx && nx < N && 0 <= ny && ny < N) {
			return true;
		}
		return false;
	}

}
