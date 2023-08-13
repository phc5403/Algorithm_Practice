import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Main { // 17406
	static StringTokenizer st;
	static StringBuilder sb;
	static int N, M, K;  // 원본배열 행, 열 / 회전 연산의 개수
	static int r, c, s;  // 회전 연산의 세 정수
	static int nN, mM;  // 연산에 의해 돌려야할 행, 열
	static int[][] lst;  // 원본 배열
	static int[][] copyLst;  // 연산을 위해 원본을 복사한 배열
	static int[][] operation; // 회전 연산을 저장할 배열
	static boolean[][] visited;  // 배열 bfs()회전시 방문 체크용
	static int[] isSelected;  // 조합 : 회전 연산 방문 체크용
	static int range;  // 한 번 회전시 내부 사각형까지 돌려야 하는 횟수
	static int startX, startY;  // 회전 연산에 의해 돌려야할 출발점 좌표
	static int res; // 최종 Answer

	// 하우상좌(시계 방향)
	static int[] dx = { 1, 0, -1, 0 };
	static int[] dy = { 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		res = Integer.MAX_VALUE;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		
		// 원본 배열 할당
		lst = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				lst[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 회전 연산 정보 할당
		operation = new int[K][K];
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			r = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			s = Integer.parseInt(st.nextToken());
			operation[i] = new int[] { r, c, s };
		}

		/**
		 * comb() -> deepCopy() -> rotate() -> arrayMin() 순으로 동작
		 * 회전 연산 순서 정하기 -> 원본 배열을 복사 ->
		 * 복사본으로 배열 회전 -> 회전이 끝난 후 배열의 최솟값 찾기 & 갱신.
		 */
		isSelected = new int[K];
		comb(1, isSelected);
		
		// 최종 출력
		sb.append(res);
		System.out.println(sb);

	} // Main

	/**
	 * 조합 : 회전 연산을 어떤 순서로 돌릴지 정하는 함수
	 * @param cnt : 현재 선택된 숫자 개수
	 * @param isSelected : 방문 체크용
	 */
	private static void comb(int cnt, int[] isSelected) {
		if (cnt == K) {
			// 1. 조합으로 순서가 정해지면, 작업을 위한 원본 배열 복사
			copyLst = new int[N][M];
			deepCopy();
			
			// 2. 정해진 순서에 의해 수행할 회전 연산 정보 설정
			for (int num : isSelected) {  // [0, 1], [1, 0]
				r = operation[num][0];  
				c = operation[num][1];
				s = operation[num][2];
				
				// 새로 돌려야할 배열의 행, 열
				nN = Math.abs((r - s - 1) - (r + s - 1)) + 1; 
				mM = Math.abs((c - s - 1) - (c + s - 1)) + 1; 

				// 한 번 회전시 내부 사각형까지 돌려야 하는 횟수
				range = Math.min(nN, mM) / 2; // 2
				visited = new boolean[N][M];
				
				// 3. 주어진 회전 연산 정보로 배열 돌리기
				for (int i = 0; i < range; i++) {
					startX = r - s - 1 + i;
					startY = c - s - 1 + i;
					rotate(startX, startY);
				}  // for : 1번의 회전연산을 마침.
			}  // foreach
			
			// 4. 1개의 순서로 회전 연산이 모두 끝난 후, 배열의 최솟값 구하기.
			arrayMin(copyLst); 
			return;
		}

		for (int i = 0; i < K; i++) {
			if (isSelected[i] == 1) {
				continue;
			}
			isSelected[i] = 1;
			comb(cnt + 1, isSelected);
			isSelected[i] = 0;
		}
	}

	/**
	 * 원본 배열을 복사하는 함수
	 */
	private static void deepCopy() {
		if (lst == null) {
			return;
		}

		for (int i = 0; i < N; i++) {
			copyLst[i] = Arrays.copyOf(lst[i], M); // 2
		}
	}

	/**
	 * 배열을 돌리는 함수
	 * @param x : 새로 돌려야 할 배열의 시작 X 좌표
	 * @param y : 새로 돌려야 할 배열의 시작 Y 좌표
	 */
	private static void rotate(int x, int y) {
		int start = copyLst[x][y];  // 최초 출발점 기억
		int nx, ny;
		int direc = 0; // 방향 변수

		while (true) {
			nx = x + dx[direc];
			ny = y + dy[direc];

			// 다음 위치가 범위를 벗어나지 않았다면,
			if (r - s - 1 <= nx && nx < r + s && c - s - 1 <= ny && ny < c + s && !visited[nx][ny]) {
				// 다음 위치의 값을 현재 위치로 땡겨옴
				copyLst[x][y] = copyLst[nx][ny];
				visited[nx][ny] = true;
				// 현재 위치 -> 다음 위치로 이동
				x = nx;
				y = ny;

				// 한 바퀴를 돌고나면, 최초 출발점의 값을 기억한 start변수로
				// 마지막 위치의 값을 갱신.
				if (x == startX && y == startY) {
					copyLst[startX][startY + 1] = start;
					break;
				}

			} else {  // 각 방향 끝까지 간 이후 다음 방향으로 전환.
				direc = (direc + 1) % 4;
			}
		} // while
	} // rotate()

	/**
	 * 배열의 최솟값을 구하는 함수
	 * @param arr : 배열의 값을 구할 복사된 배열
	 */
	private static void arrayMin(int[][] arr) {
		int sum = Integer.MAX_VALUE;
		int temp;
		for (int[] subArr : arr) {
			temp = 0;
			for (int element : subArr) {
				temp += element;
			}
			sum = Math.min(sum, temp);
		}
		res = Math.min(res, sum);
	}
}
