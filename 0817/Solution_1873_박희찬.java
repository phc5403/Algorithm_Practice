import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution { // 1873
	static StringBuilder sb;
	static StringTokenizer st;
	static int TC;
	static int N, ROW, COL; // 명령어 개수, 행, 열
	static char[] operation; // 전차 명령어
	static char[][] lst; // 맵

	// 상하좌우
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		st = new StringTokenizer(br.readLine());
		TC = Integer.parseInt(st.nextToken());

		for (int tc = 1; tc < TC + 1; tc++) {
			st = new StringTokenizer(br.readLine());

			ROW = Integer.parseInt(st.nextToken());
			COL = Integer.parseInt(st.nextToken());

			// 입력받은 문자열로 맵을 채움
			lst = new char[ROW][COL];
			for (int i = 0; i < ROW; i++) {
				String str = br.readLine();
				for (int j = 0; j < COL; j++) {
					lst[i][j] = str.charAt(j);
				}
			}

			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());

			// 명령어를 초기화
			operation = new char[N];
			String str = br.readLine();
			for (int i = 0; i < N; i++) {
				operation[i] = str.charAt(i);
			}

			// Find Tank
			int startX=0, startY=0;
					 
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					if (lst[i][j] == '<' || lst[i][j] == 'v' || lst[i][j] == '^' || lst[i][j] == '>') {
						startX = i;
						startY = j;
						break;
					}
				}
			}			
			bfs(startX, startY); // BFS() start
						
			sb = new StringBuilder();
			sb.append("#").append(tc).append(" ");
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					sb.append(lst[i][j]);
				}
				sb.append("\n");
			}
			System.out.print(sb);

		} // TC
	} // Main

	/**
	 * 주어진 명령어에 따라 전차의 행동을 조작하는 함수
	 * 
	 * @param x : 전차의 시작 x 좌표
	 * @param y : 전차의 시작 y 좌표
	 */
	private static void bfs(int x, int y) {
		int dir = directing(lst[x][y]); // 초기 방향

		for (int idx = 0; idx < N; idx++) {
			char oper = operation[idx]; // 명령어 1개
			int nx, ny; // 다음 좌표

			switch (oper) {
			case 'U': { // 1. Up
				nx = x + dx[0];
				ny = y + dy[0];

				// 범위 안이라면,
				if (isIn(nx, ny) && lst[nx][ny] == '.') {
					// 이동 후 포신 방향 변경, 현재 좌표 갱신
					lst[x][y] = '.';
					x = nx;
					y = ny;
				}
				lst[x][y] = '^'; // 움직일 수 없더라도 포신 방향 변경.
				break;
			}
			case 'D': {
				nx = x + dx[1];
				ny = y + dy[1];
				if (isIn(nx, ny) && lst[nx][ny] == '.') {
					lst[x][y] = '.';
					x = nx;
					y = ny;
				}
				lst[x][y] = 'v';
				break;
			}

			case 'L': {
				nx = x + dx[2];
				ny = y + dy[2];
				if (isIn(nx, ny) && lst[nx][ny] == '.') {
					lst[x][y] = '.';
					x = nx;
					y = ny;
				}
				lst[x][y] = '<';
				break;
			}

			case 'R': {
				nx = x + dx[3];
				ny = y + dy[3];
				if (isIn(nx, ny) && lst[nx][ny] == '.') {
					lst[x][y] = '.';
					x = nx;
					y = ny;
				}
				lst[x][y] = '>';
				break;
			}

			case 'S': { // 포탄을 발사
				dir = directing(lst[x][y]); // 현재 방향 체크
				shoot(x, y, dir); // 포탄 발사 함수 호출
				break;
			}
			} // switch

		} // for

	}

	/**
	 * 현재 위치의 포신 방향을 찾는 함수
	 * @param ch : 현재 위치의 포신 방향 표시
	 * @return
	 */
	private static int directing(char ch) { // 현재 방향
		if (ch == '^') {
			return 0;
		} else if (ch == 'v') {
			return 1;
		} else if (ch == '<') {
			return 2;
		} else { // ch == '>'
			return 3;
		}
	}

	/**
	 * 이동 & 동작을 위한 다음 위치가 맵 밖인지 판단하는 함수
	 * @param nx : 다음 행동 위치의 x 좌표
	 * @param ny : 다음 행동 위치의 y 좌표
	 * @return
	 */
	private static boolean isIn(int nx, int ny) {
		if (0 <= nx && nx < ROW && 0 <= ny && ny < COL) {
			return true;
		}
		return false;
	}

	/**
	 * 포탄을 발사하는 함수
	 * @param x   : 현재 위치 x 좌표
	 * @param y   : 현재 위치 y 좌표
	 * @param dir : 현재 방향
	 */
	private static void shoot(int x, int y, int dir) {
		int nx, ny;

		while (true) {
			nx = x + dx[dir];
			ny = y + dy[dir];

			// 1. 이동 & 동작을 위한 다음 좌표가 범위 안 일때.
			if (isIn(nx, ny)) {

				// 1-1. 벽돌 벽은 부수고 평지로 만듦
				if (lst[nx][ny] == '*') {
					lst[nx][ny] = '.';
					return;
				
				// 1-2. 강철 벽을 만나면 포탄만 소멸됨.
				} else if (lst[nx][ny] == '#') {
					return;
				
				// 1-3. 길 또는 강이라면 맵 끝까지 날아감.
				} else {
					x = nx;
					y = ny;
				}
			} else { // 2. 범위 밖이라면 아무런 일도 일어나지않음.
				return;
			} // if-else
		} // while

	}

}
