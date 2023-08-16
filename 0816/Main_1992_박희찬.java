import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main { // 1992
	static StringTokenizer st;
	static StringBuilder sb;
	static int N;  // 영상의 크기
	static int[][] lst;  // 영상
	static int size; // 한 변의 길이

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		// N * N 배열에 영상 정보를 받음
		lst = new int[N][N];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < N; j++) {
				lst[i][j] = str.charAt(j) - '0';
			}
		}
		
		divide(0, 0, N);
		System.out.println(sb);
	} // Main

	/**
	 * 영상이 0 or 1로 압축 되었는지 확인하고, 
	 * 압축이 덜 되었다면 4등분하여 압축을 확인하는 함수
	 * @param x : 현재 x 좌표
	 * @param y : 현재 y 좌표
	 * @param size : 현재 한 변의 길이
	 */
	private static void divide(int x, int y, int size) {
		// 1. 현재 가진 영상 크기의 압축 결과 확인
		int sum = 0;
		for (int r = x; r < x + size; r++) {
			for (int c = y; c < y + size; c++) {
				if (lst[r][c] == 1) {
					sum++;
				}
			}
		}
		
		// 1-1. 모두 1로만 되어 있으면
		if (sum == size * size) {
			sb.append(1);  // 압축 결과는 "1"
			return;
		}
		// 1-2. 모두 0으로만 되어 있으면
		else if (sum == 0) {
			sb.append(0);  // 압축 결과는 "0"
			return;
		}
		
		/*
		 * 문제에서 주어진 영역을 나누는 순서를 정확히 맞춰야함.
		 * 0과 1이 섞여 있고 + 최소 크기인 1칸 이상일 경우,
		 * 4개의 영상으로 나누어 재귀 -> 각 재귀 결과로 압축한 결과를 차례대로 괄호 안에 묶어서 표현함.
		 */
		if (size > 0) {
			sb.append("(");
			int mid = size / 2;  // 한 변의 길이에서 절반으로 압축 시도
			divide(x, y, mid);  // 1. 왼쪽 위
			divide(x, y + mid, mid);  // 2. 오른쪽 위
			divide(x + mid, y, mid);  // 3. 왼쪽 아래
			divide(x + mid, y + mid, mid); // 4. 오른쪽 아래
			sb.append(")");
		}
		return;
	}
	
}
