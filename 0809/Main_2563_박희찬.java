import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main  {  // BOJ_2563_색종이

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		boolean[][] lst = new boolean[100][100];  // 전체 종이 Map
		
		int x, y;  // 색종이를 붙일 좌표
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());  // 색종이 X 좌표
			y = Integer.parseInt(st.nextToken());  // 색종이 Y 좌표
			
			// 색종이를 붙인 곳을 표시
			for (int row = x; row < x + 10; row++) {
				for (int col = y; col < y + 10; col++) {
					lst[row][col] = true;
				}
			}
		}
		
		// 색종이를 붙인 곳의 개수 = 색종이 넓이
		int cnt = 0;
		for (boolean[] b1 : lst) {
			for (boolean b2 : b1) {
				if(b2 == true) cnt++;
			}
		}
		
		sb.append(cnt);
		System.out.println(sb.toString());

	} // Main

}
