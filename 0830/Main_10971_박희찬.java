
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


// 혼자 힘으로 풀 수 없어서 구글링 코드로 공부함.
// https://velog.io/@jxlhe46/%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%EC%99%B8%ED%8C%90%EC%9B%90-%EC%88%9C%ED%9A%8C-%EB%AC%B8%EC%A0%9C
public class BOJ_10971 {
	static StringBuilder sb;
	static StringTokenizer st;
	static int N;
	static int[][] edge, DP;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		edge = new int[N][N];
		
		/*
		 * N = 4라고 가정하면,
		 * DP 배열의 열 크기를 비트마스킹으로 1 << N을 해줌으로써,
		 * 각 노드 번호에 해당하는 4 bit 만큼을 할당하여,
		 *     4321
		 * EX) 0100 : 3번 노드를 방문했다는 표시를 할 수 있음. 
		 */
		DP = new int[N][1 << N];  // 현재 노드 번호와(row), 이제까지의 방문 상태를 저장.
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				edge[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
//		for (int[] temp : edge) {
//			System.out.println(Arrays.toString(temp));
//		}
//		System.out.println("----------------");
		
		sb = new StringBuilder();
		dfs(0, 1);
		
//		for (int[] temp : DP) {
//			System.out.println(Arrays.toString(temp));
//		}
		
		// 0번 노드부터 시작
		sb.append(dfs(0, 1));
		System.out.println(sb);
	}

	
	/**
	 * 
	 * @param cur : 현재 보고 있는 노드 번호
	 * @param visited : 방문한 노드 수 누적
	 * @return : 최소 비용
	 */
	static int dfs(int cur, int visited) {
		// 모든 노드를 방문 했다면,
		if (visited == (1 << N) - 1) {
			// [cur] : 현재노드에서 [0] : 출발점 0으로 돌아오는 경로가 있다면
			if (edge[cur][0] > 0) {
//				System.out.println(edge[cur][0]);
				return edge[cur][0];  // 최소 비용 반환
			}
			
			return 1000000 * N;  // Integer.MAX_VALUE로 설정하면 오버플로우 발생
		}
		
		
		// 1. 현재 상태를 이미 계산한 값이 이미 DP에 저장되있다면 그대로 사용
		// Memoization
		if (DP[cur][visited] != 0) {
			 return DP[cur][visited];
		}
		
		// 2. 없다면, 현재 노드에 대한 탐색 진행
		DP[cur][visited] = 1000000 * N;
		
		// 다음 노드에 대해 탐색
		for (int nxt = 0; nxt < N; nxt++) {
			// 현재 노드에서 다음 노드로 가는 경로가 없다면 넘김
			if (edge[cur][nxt] == 0) {
				continue;
			}
			
			//visited가 1이면 방문했다는표시, 
			// (1 << nxt)로 visited의 bit 위치를 맞추고
			// 둘다 1이 나온다는것은 방문이 됐다는 의미임. -> 넘김
			if ((visited & (1 << nxt)) == 1) {
				continue;
			}
			
			// 다음번 노드에 방문 처리 후 최소 비용 반환
			int temp = dfs(nxt, visited | 1 << nxt);
//			System.out.println(edge[cur][nxt] + ", " + temp);
			DP[cur][visited] = Math.min(DP[cur][visited], edge[cur][nxt] + temp);
		}  // for
		
//		System.out.println(DP[cur][visited]);
		return DP[cur][visited];  // 최종 출력을 위한 최소 비용 반환
	}
	
	
}
