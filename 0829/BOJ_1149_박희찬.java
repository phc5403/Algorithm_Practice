import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {  // BOJ_1149_RGB거리
	static StringBuilder sb;
	static StringTokenizer st;
	static int N;  // 집의 개수
	static int[][] RGB, DP;  // RGB 비용, DP 계산용
	static int res = Integer.MAX_VALUE; // 모든 집을 칠하는 비용의 최솟값
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		RGB = new int[N][4];
		
		// 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 3; j++) {
				RGB[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		DP = new int[N][4];
		
		// DP를 위해 1번집의 RGB 비용만 입력
		for (int i = 0; i < 3; i++) {
			DP[0][i] = RGB[0][i];
		}
		
		rgbStreet();
		
		sb = new StringBuilder();
		sb.append(res);
		System.out.println(sb);

	} // Main

	
	static void rgbStreet() {
		
		// 2번집부터 끝까지 검사
		for (int home = 1; home < N; home++) {
			
			// RGB 3가지 경우를 확인
			for (int rgb = 0; rgb < 3; rgb++) {
				
				int min = Integer.MAX_VALUE;
				for (int idx = 0; idx < 3; idx++) {
					
					// 집이 같지 않아야하는 조건들을 위해 같은 열은 넘어감
					if (rgb != idx) {
						// 이전의 값을 선택했을 때 최솟값이었다면
						if (DP[home - 1][idx] < min) {
							min = DP[home - 1][idx];
						}
						// 현재의 값을 이전의 최솟값 + 현재 RGB 비용
						DP[home][rgb] = min + RGB[home][rgb];
					} 
//					DP[rgb][idx] = min + RGB[rgb][idx - 1];
				}
			}
		}
		
		
		// 모든 집 탐색 끝
		for (int col = 0; col < 3; col++) {
			res = Math.min(res, DP[N - 1][col]);
		}
		
	}

}
