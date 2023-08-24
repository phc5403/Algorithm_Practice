import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main { // 15961_회전 초밥
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	static int N, D, K, CHANCE;
	static int res = Integer.MIN_VALUE; // 먹을 수 있는 초밥의 가짓수의 최댓값
	static int[] lst;
	static int len;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());  // 초밥은 2부터 시작 ~ 3천까지
		K = Integer.parseInt(st.nextToken());  // 범위도 2부터 시작 ~ 3천까지
		CHANCE = Integer.parseInt(st.nextToken());
		
		
		len = N + K;  // 기존 길이 + 0부터 K번째까지 길이
		lst = new int[len];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			lst[i] = Integer.parseInt(st.nextToken());
		}
		
		/*
		 * 앞쪽부터 K번째까지 뒤에 붙임
		 * 회전 초밥이므로 시작 ~ 끝인 1 Cycle과,
		 * (끝 - K) ~ 끝 부터 K번째를 더 봐야함.
		 */
		System.arraycopy(lst, 0, lst, N, K);
		
		slidingWindow();
		
		sb = new StringBuilder();
		sb.append(res);
		System.out.println(sb);

	} // Main

	static void slidingWindow() {
		Deque<Integer> qu = new ArrayDeque<>();
		
		// 초밥의 가짓수 만큼 배열 생성
		int[] visited = new int[D + 1];
		int cnt = 0;  // 먹을 수 있는 초밥 가짓수
		
		// 1. 처음부터 K번째 까지의 초밥을 QU에 넣음
		for (int i = 0; i < K; i++) {
			qu.offer(lst[i]);
		}
	
		// 1-2. 현재 QU를 돌면서 방문 체크 + 개수 추가
		for (int cur : qu) {
			if (visited[cur] == 0) {
				cnt++;  // 처음 먹을 때만 개수 증가
			}
			visited[cur]++;
		}
		
		// 1-3. 최초 경우의 수에서 최댓값 갱신
		res = Math.max(res, cnt);

		// 2. K번째부터 초밥 벨트의 모든 조합까지 순회
		for (int idx = K; idx < len; idx++) {
			// 2-1. 가장 앞 쪽의 초밥을 뺌
			int prev = qu.poll();
			
			// 없어진 초밥의 먹은 횟수를 차감하고
			if (--visited[prev] == 0) {
				cnt--;  // 0이 되었다면 총 가짓수 차감
			}
			
			// 2-2. 다음 뒤의 초밥을 추가
			int nxt = lst[idx];
			qu.offer(nxt);
			
			
			// 처음 먹을 때만 cnt 증가
			if (visited[nxt] == 0) {  
				visited[nxt]++;
				cnt++;
			} else if (visited[lst[idx]] > 0) {  // 이미 먹은 초밥이면 개수 체크만.
				visited[nxt]++;
			}
			
			// 초밥 접시 한 사이클을 돌 때마다 최댓값 확인
			// 3. 쿠폰의 초밥을 먹지 않았다면
			if (visited[CHANCE] == 0) {
				res = Math.max(res, cnt + 1);  // 먹은 후의 최댓값 갱신
			} else {
				res = Math.max(res, cnt);  // 기존 횟수로 최댓값 갱신
			}
			
		} // for
	}
	
	
}
