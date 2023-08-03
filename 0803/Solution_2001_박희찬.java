import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
	static int N, M;
	static StringBuilder sb;
	static int[][] lst;
	static int TC;
	static int MAX;
	static int sum;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		TC = Integer.parseInt(st.nextToken());

		for (int tc = 1; tc < TC + 1; tc++) {

			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			lst = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					lst[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			MAX = Integer.MIN_VALUE;
			// M의 크기에 맞게 순회
			for (int a = 0; a < N - M + 1; a++) {
				for (int b = 0; b < N - M + 1; b++) {
					sum = 0;
					for (int c = 0; c < M; c++) {
						for (int d = 0; d < M; d++) {
							sum += lst[a + c][b + d];
						}
						MAX = Math.max(MAX, sum);
					}
				}
			}
			
			// 출력
			sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(MAX);
			System.out.println(sb.toString());
//			System.out.println("#" + tc + " " + MAX);

		} // TC

	} // Main

}
