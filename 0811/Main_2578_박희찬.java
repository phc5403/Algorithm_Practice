import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {  // 2578
	static int[][] lst; // 빙고판
	static int res = -1;  // 최종 Answer
	static final int size = 5;  // 판 사이즈
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 빙고판 초기화
		lst = new int[5][5];
		for (int i = 0; i < size; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < size; j++) {
				lst[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int[] number = new int[25];
		int speak = 0; // 5개씩 5번 부름
		int idx = 0; // 부른 숫자를 저장할 number[]의 Index

		// 사회자가 부를 숫자
		while (speak < size) {
			st = new StringTokenizer(br.readLine());
			while (st.hasMoreTokens()) {
				number[idx++] = Integer.parseInt(st.nextToken());
			}
			speak++;
		}

		for (int i = 1; i <= 25; i++) {
			search(number[i - 1], i);
			if (res != -1) {
				sb.append(res);
				System.out.println(sb);
				break;
			}

		}


	} // Main

	// 사회자가 부른 숫자를 빙고판에 표시하는 함수
	private static void search(int value, int cnt) {  // 사회자가 부른 숫자, 부른 개수
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (lst[i][j] == value) {
					lst[i][j] = 0;  // 0으로 마킹(숫자 범위= 1 ~25)
					if (cnt >= 10) { // 3빙고 되려면 최소 13개 이상의 칸이 필요함.
						bingo(cnt);
					}
					return;
				}
			}
		}
	}

	// 빙고 3줄을 판단하는 함수
	private static void bingo(int cnt) {  // 몇 번째 수를 부르는지에 대한 변수
		int bin = 0;  // 빙고 개수
		int temp = 0;  // 1줄 빙고(5칸) 체크용
		
		// 1. 행 검사
		for (int i = 0; i < size; i++) {
			temp = 0;
			for (int j = 0; j < size; j++) {
				if (lst[i][j] == 0) {
					temp++;
				}
			}
			if (temp == size) {
				bin++;
			}
		}
		
		// 2. 열 검사
		for (int i = 0; i < size; i++) {
			temp = 0;
			for (int j = 0; j < size; j++) {
				if (lst[j][i] == 0) {
					temp ++;
				}
			}
			if (temp == size) {
				bin++;
			}
		}
		
		// 3. 좌대각선 검사
		temp = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// (0, 0) (1, 1) (2, 2) (3, 3) (4, 4)이 좌대각선 좌표
				if (i == j && lst[i][j] == 0) {
					temp ++;
				}
			}
			if (temp == size) {
				bin++;
			}
		}
		
		// 4. 우대각선 검사
		temp = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// (0, 4) (1, 3) (2, 2) (3, 1) (4, 0)이 우대각선 좌표
				if ((i + j) == (size-1) && lst[i][j] == 0) {
					temp++;
				}
			}  // for: j
			if (temp == size) {
				bin++;
			}
		}  // for: i
		
		if (bin >= 3) {
			res = cnt;
			return;
		} else {
			return;
		}
		
	}  // bingo()

}
