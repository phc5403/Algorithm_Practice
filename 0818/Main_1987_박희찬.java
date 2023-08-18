import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main { // 1987
	static StringTokenizer st;
	static StringBuilder sb;
	static int N, M; // 행, 열
	static int[][] lst; // 맵
	static boolean[][] visited; // 방문 처리
	static int[] alphabet; // 알파벳 사용 체크

	// 상하좌우
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int res = Integer.MIN_VALUE; // 최종 출력값

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		alphabet = new int[26]; // 알파벳 26글자를 0으로 초기화
		visited = new boolean[N][M]; // 방문 배열 초기화
		lst = new int[N][M];

		for (int row = 0; row < N; row++) {
			String str = br.readLine();
			for (int col = 0; col < M; col++) {
				// 입력받은 알파벳을 0 ~ 26의 정수로 치환
				lst[row][col] = str.charAt(col) - 65;
			}
		}

		// 출발은 (0, 0) 고정, 각각 방문, 사용 처리
		alphabet[lst[0][0]] = 1;
		visited[0][0] = true;

		dfs(0, 0, 1); // DFS

		sb = new StringBuilder();
		sb.append(res);
		System.out.println(res);
	} // Main

	/**
	 * DFS로 순회하면서 경로를 탐색하는 함수
	 * @param x   : 현재 위치
	 * @param y   : 현재 위치
	 * @param cnt : 현재까지 지났던 칸 수
	 */
	static void dfs(int x, int y, int cnt) {
		int nx, ny;

		// 상하좌우 4방 탐색
		for (int k = 0; k < 4; k++) {
			nx = x + dx[k];
			ny = y + dy[k];
			
			// 범위 안이면서, 방문하지 않았고, 알파벳이 사용되지 않은 곳 -> 지나갈 수 있음.
			if (isIn(nx, ny) && !visited[nx][ny] && alphabet[lst[nx][ny]] == 0) {
				// 1. 지났던 곳은 방문 처리, 알파벳 사용 처리
				visited[nx][ny] = true;
				alphabet[lst[nx][ny]] = 1;
				
				// 2. 다음 좌표로 재귀 호출
				dfs(nx, ny, cnt + 1);
				
				// 3. 이전의 4방 탐색 진행에 영향이 없도록 방문, 사용 처리를 롤백함.
				visited[nx][ny] = false;
				alphabet[lst[nx][ny]] = 0;
			}
		}

		// 최종 출력값 갱신
		res = Math.max(res, cnt);

	}

	/**
	 * 다음 좌표를 받고, 맵의 범위를 벗어나는지 체크하는 함수
	 * @param nx : 다음 갈 좌표
	 * @param ny : 다음 갈 좌표
	 * @return
	 */
	static boolean isIn(int nx, int ny) {
		// 지나 갈 수 있다면 true 반환
		if (0 <= nx && nx < N && 0 <= ny && ny < M) {
			return true;
		}
		return false;
	}
}
