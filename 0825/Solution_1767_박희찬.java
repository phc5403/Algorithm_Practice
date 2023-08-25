import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution { // 1767_프로세서 연결하기
	static StringBuilder sb;
	static StringTokenizer st;
	static int TC;
	static int N;
	static int[][] lst;
	static List<Core> core;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int coreLen;
	static int res;

	static class Core {
		int x, y;
		boolean flag;

		public Core(int x, int y, boolean flag) {
			super();
			this.x = x;
			this.y = y;
			this.flag = flag;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		st = new StringTokenizer(br.readLine());
//		TC = Integer.parseInt(st.nextToken());

		for (int tc = 1; tc < 2; tc++) {
//		for (int tc = 1; tc < TC + 1; tc++) {
			res = Integer.MAX_VALUE;

			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());

			lst = new int[N][N];  // static
			core = new ArrayList<>();

			int cnt = 0;
			for (int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for (int col = 0; col < N; col++) {
					int temp = Integer.parseInt(st.nextToken());
					if (temp == 1) {
						if (row == 0 || col == 0) { // 가장자리에 위치한 Core = 전원 연결
//							System.out.println(row + ", " + col);
							core.add(new Core(row, col, true));
							cnt++;
						} else { // 아닌 경우, 전원 OFF
							core.add(new Core(row, col, false));
						}
					}
					lst[row][col] = temp;
				}
			}

			coreLen = core.size();


			// 테스트한 코어 개수, 코어 번호, 초기 켜져있는 코어 개수
			dfs(lst, 0, 0, cnt);
			
			/////////////
//			for (int[] temp: lst) {
//				System.out.println(Arrays.toString(temp));
//			}

			//
			sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(res);
			System.out.println(sb);
		}
		// TC

	} // Main

	static void dfs(int[][] lst, int index, int cNum, int cnt) {
//		Deque<Pos> qu = new ArrayDeque<>();
//		visited = new boolean[N][N];

		if (index == coreLen && cNum == coreLen) {
			electric();
			
//			for (int[] temp: lst) {
//				System.out.println(Arrays.toString(temp));
//			}
//			System.out.println("------------------------");
			
			return;
		}

		// 코어 좌표쌍 처음부터 꺼내기
		int x = core.get(cNum).x;
		int y = core.get(cNum).y;

		
		int [][] copyLst = deepCopy(lst);
		// 0, 1, 2, 3 -> 상하좌우
		for (int angle = 0; angle < 4; angle++) {
//			System.out.println(cnt);
			cnt = contact(x, y, core.get(cNum).flag, angle + 1, cnt, lst);

			dfs(lst, index + 1, cNum + 1, cnt);
			lst = deepCopy(copyLst);
		}
	}

	static int contact(int x, int y, boolean flag, int dir, int cnt, int[][] lst) {
		// 전선이 코어에 닿으면 멈춤
		// 경계 끝까지 가야 코어 On
//		int temp = 0; // 코어 On / Off 매번 계산해서 return
		dir %= 4;
		
		// 코어가 OFF인것만 전선 연결, ON이면 패스
		if (flag) {
			return cnt;
		}
		int nx = x;
		int ny = y;
		
		while (true) {
			nx = x + dx[dir];
			ny = y + dy[dir];
			
			if (isIn(nx, ny) && lst[nx][ny] == 0) {
				lst[nx][ny] = 4;  // 전선을 3으로 임의 설정
				
				if (nx == 0 || ny == 0) {  // 연결된거
					return cnt + 1;
				} 
				
				x = nx;
				y = ny;
			} else {
				return cnt;
			}
		} // while
				
	}

	static int[][] deepCopy(int[][] origin) {
		int[][] temp = new int[N][N];
		for (int i = 0; i < N; i++) {
			temp[i] = Arrays.copyOf(origin[i], N);
		}

		return temp;
	}

	static void electric() {
		int sum = 0;

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (lst[r][c] == 4) {
					sum++;
				}
			}
		}

		res = Math.min(res, sum);
	}
	
	static boolean isIn(int nx, int ny) {
		if (0 <= nx && nx < N && 0 <= ny && ny < N) {
			return true;
		}
		return false;
	}

}
