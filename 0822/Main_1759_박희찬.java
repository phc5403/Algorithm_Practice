import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main { // 1759
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	static int L, C; // 조합할 암호 개수, 주어질 알파벳 갯수
	static String[] password;  // 만들어진 비밀번호를 저장할 배열
	static boolean[] visited; // 조합 : 방문 배열
	static char[] gather = {'a', 'e', 'i', 'o', 'u'};  // 모음 배열
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());  // 조합 해야 할 암호 개수
		C = Integer.parseInt(st.nextToken());  // 주어질 알파벳 갯수
		
		// 결과를 저장할 배열 초기화 및 할당
		password = new String[C];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < C; i++) {
			password[i] = st.nextToken();
		}
		
		// 오름차순으로 정렬
		Arrays.sort(password);
	
		visited = new boolean[C];
		comb(0, 0);
		System.out.println(sb);

	} // Main

	/**
	 * 조합을 구해 주어진 알파벳에서 사용할 L개를 뽑는 함수
	 * @param start
	 * @param cnt
	 */
	static void comb(int start, int cnt) {
		// 필요한 L개를 뽑았다면,
		if (cnt == L) {
			
			// 뽑은 알파벳을 문자열로 만듦
			String str = "";
			for (int i = 0; i < C; i++) {
				if (visited[i]) {
					str += password[i];
				}
			}
			
			// 모음과 자음 개수 체크 -> 성립한다면 StringBuilder()에 추가
			if (check(str)) {
				for (int i = 0; i < str.length(); i++) {
					sb.append(str.charAt(i));
				}
				sb.append("\n");
			}
			return;
		}
		
		// 조합 구하기
		for (int i = start; i < C; i++) {
			if (!visited[i]) {
				visited[i] = true;
				comb(i, cnt + 1);
				visited[i] = false;
			}
		}
	}
	
	/**
	 * 후보 암호의 모음, 자음 개수를 체크하는 함수
	 * @param str : 검사할 문자열
	 * @return
	 */
	static boolean check(String str) {
		int gaNum = 0;  // 모음
		
		for (int i = 0; i < gather.length; i++) {
			for (int j = 0; j < L; j++) {
				if (gather[i] == str.charAt(j)) {
					gaNum++;
				}
			}
		}
		
		// 자음 = 뽑은 개수 - 모음 개수
		int otherNum = L - gaNum; 
		
		// 최소 1개의 모음 and 최소 2개의 자음
		if (0 < gaNum && 2 <= otherNum) {
			return true;
		}
		
		return false;
	}

}
