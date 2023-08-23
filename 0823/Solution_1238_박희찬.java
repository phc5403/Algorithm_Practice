import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution { // 1238_Contact
	static StringBuilder sb;
	static StringTokenizer st;
	static int TC;
	static int N, START; // 사람 수, 시작 노드
	static List<ArrayList<Integer>> lst;  // 연락망 관리 List
	static List<Integer> depth;  // 깊이 관리 List
	static boolean[] visited;  // 방문 배열
	static int res;  // 최종 출력값

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int tc = 1; tc < 11; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); // 0 < N <= 100
			START = Integer.parseInt(st.nextToken());

			// 사용할 Collections 초기화
			lst = new ArrayList<>();
			depth = new ArrayList<>();

			// 1 <= 부여될 수 있는 번호 <= 100
			for (int i = 0; i < 101; i++) {
				lst.add(new ArrayList<>());
			}

			// 연락망 정보 할당
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N / 2; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
//				System.out.println(from + ", " + to);

				lst.get(from).add(to);
			}
			
			///////
//			for (ArrayList<Integer> temp : lst) {
//				System.out.print(temp.toString() + ", ");
//			}
//			System.out.println();

			///////////

			res = Integer.MIN_VALUE;
			visited = new boolean[101];
			bfs();

//			System.out.println(Arrays.toString(visited));

			sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(res);
			System.out.println(sb);

		} // TC

	} // Main

	static void bfs() {
		Deque<Integer> qu = new ArrayDeque<>();
		
		// 첫 시작 번호 큐잉 & 방문 처리
		qu.offer(START);
		visited[START] = true;
		
		int temp = 0;  // 깊이별 최댓값을 저장할 임시 변수

		// while - for에서 깊이별로 구분하기 위해 Queue의 길이로 반복을 나눔
		while (!qu.isEmpty()) {
			int qulen = qu.size();
			temp = 0;

			for (int i = 0; i < qulen; i++) {
				int cur = qu.poll();

				// 다음 연락할 사람이 있다면
				for (int idx = 0; idx < lst.get(cur).size(); idx++) {
					int nxt = lst.get(cur).get(idx);

					// 방문 했다면 다음 번호 검사
					if (visited[nxt]) {
						continue;
					}
					
					// 방문하지 않았다면 방문처리
					visited[nxt] = true;
					
					// 다음 번호 중 최댓값 갱신
					temp = Math.max(temp, nxt);
					qu.offer(nxt);
				}

			}  // for
//			System.out.println(temp);
			depth.add(temp);  // 해당 깊이에서의 최댓값 갱신
		}  // while
		
		res = depth.get(depth.size() - 2);
	}

}
