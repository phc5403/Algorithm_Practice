import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

//https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AW8Wj7cqbY0DFAXN#
public class Solution { // SWEA_9229
	static int TC;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		TC = Integer.parseInt(st.nextToken());
		for (int tc = 1; tc < TC + 1; tc++) { // TC
			StringBuilder sb = new StringBuilder();
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // 과자 개수
			int K = Integer.parseInt(st.nextToken()); // 무게 합 제한

			int[] snack = new int[N];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				snack[i] = Integer.parseInt(st.nextToken());
			}

			Arrays.sort(snack);
			int left = 0; // 투포인터_left
			int right = N - 1; // 투포인터_right
			int sum = 0; // 선택한 과자의 합
			int res = -1; // 무게 합 최대

			while (left < right) {
				sum = snack[left] + snack[right];
				if (sum > K) {  // sum > K일 때, 범위 갱신
					right--;
				}

				else { // sum <= K일 때, res 갱신
					// END_1 : 현재 최대 합으로 res 갱신
					// END_2 : sum이 K보단 부족하지만, 최대 합일 경우 존재.
					if (res < sum) res = snack[left] + snack[right];
					left++; // sum < K일 때, 범위 갱신
				}
			}

			sb.append("#").append(tc).append(" ").append(res);
			System.out.println(sb.toString());
		} // TC
	}

}
