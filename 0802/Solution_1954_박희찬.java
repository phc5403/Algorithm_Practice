import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	static int TC;
	static int[][] lst;
	static int N;
	// 우하좌상(문제 패턴)
	static int[] dx = {0, -1, 0, 1};
	static int[] dy = {1, 0, -1, 0};
	static int cnt;  // 넘버링
	static int MAX;  // 마지막 숫자
	static int direction;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		TC = Integer.parseInt(st.nextToken());
		for (int tc = 1; tc < TC + 1; tc++) {  // TC
			StringBuilder sb = new StringBuilder();
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			
			MAX = N * N;
			cnt = 1;
			lst = new int[N][N];
			direction = 0;
		
			int x = 0;
			int y = 0;
			lst[x][y] = cnt++;
			int nx, ny;
			
			while (cnt <= MAX) {  // 마지막 번호를 넘버링 할 때 까지
				nx = x + dx[direction];
				ny = y + dy[direction];
				
                // 정해진 방향으로, 넘버링 안 한 곳까지 밀기
				if (0 <= nx && nx < N && 0 <= ny && ny < N && lst[nx][ny] == 0) {
					lst[nx][ny] = cnt++;
					x = nx;
					y = ny;
				} else {  // 끝까지 밀었다면 방향 전환
					direction = (direction + 1) % 4;
					continue;
				}
			}
			
            // 결과 출력
			sb.append("#").append(tc).append("\n");
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					sb.append(lst[i][j]).append(" ");
				}
				sb.append("\n");
			}
			System.out.print(sb.toString());
		
		}  // TC
	
	}

}
