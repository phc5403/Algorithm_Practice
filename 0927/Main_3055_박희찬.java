import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// DFS로 다시 풀어 봄.
public class BOJ_3055 { // BOJ_3055_탈출
	static StringBuilder sb;
	static StringTokenizer st;
	static int R, C;
	static char[][] forest;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	static class Pos {
		int x, y;

		public Pos(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		forest = new char[R][C];

		for (int i = 0; i < R; i++) {
			String str = br.readLine();
			for (int j = 0; j < C; j++) {
				forest[i][j] = str.charAt(j);
			}
		}

		sb = new StringBuilder();
		dfs(forest, 1);
		System.out.println(sb);

	} // Main

	/**
	 * DFS로 탐색
	 * @param lst : 숲의 지도
	 * @param time : 시간
	 */
	static void dfs(char[][] lst, int time) {
//		for (char[] cs : lst) {
//			System.out.println(Arrays.toString(cs));
//		}
//		System.out.println();
		
		Deque<Pos> qu = new ArrayDeque<>();  // 도치
		Deque<Pos> water = new ArrayDeque<>();  // 물

		// 고슴도치의 위치를 담음
		for (int r = 0; r < R; r++) {
			for (int c = 0; c < C; c++) {
				if (lst[r][c] == 'S') {
					qu.offer(new Pos(r, c));
				}
			}
		} // for
		
		// Pruning -> 완전 탐색 후, 고슴도치의 위치가 없다면 = 탈출 불가
		if (qu.isEmpty()) {
			sb.append("KAKTUS");
			return;
		}

		// 고슴도치가 이동 할 수 있는 곳 탐색
		while (!qu.isEmpty()) {
			Pos p = qu.poll();

			for (int k = 0; k < 4; k++) {
				int nx = p.x + dx[k];
				int ny = p.y + dy[k];

				if (isIn(nx, ny)) {
					// 다음 이동으로 탈출 할 수 있다면,
					if (lst[nx][ny] == 'D') {
						// 시간을 출력하고 Return
						sb.append(time);
						return;
					} else if (lst[nx][ny] == '.') {  // 다음 위치로 이동
						lst[nx][ny] = 'S';
					}
				}
			}
		} // while

		// 물의 위치를 담음
		for (int r = 0; r < R; r++) {
			for (int c = 0; c < C; c++) {
				if (lst[r][c] == '*') {
					water.offer(new Pos(r, c));
				}
			}
		} // for

		// 물이 이동 할 수 있는 곳 탐색
		while (!water.isEmpty()) {
			Pos p = water.poll();

			for (int k = 0; k < 4; k++) {
				int nwx = p.x + dx[k];
				int nwy = p.y + dy[k];

				// 물은 비어있거나, 고슴도치를 만나면 덮어씀
				if (isIn(nwx, nwy)) {
					if (lst[nwx][nwy] == '.' || lst[nwx][nwy] == 'S') {
						lst[nwx][nwy] = '*';
					}
				}
			}
		} // while
		
		// 재귀 호출(시간 + 1)
		dfs(lst, ++time);

	}

	static boolean isIn(int nx, int ny) {
		if (0 <= nx && nx < R && 0 <= ny && ny < C) {
			return true;
		}
		return false;
	}

}
