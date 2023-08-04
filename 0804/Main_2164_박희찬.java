import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		Deque<Integer> qu = new ArrayDeque<>();  // Deque 생성

		// 1부터 N까지의 카드 입력
		for (int i = 1; i < N + 1; i++) {
			qu.add(i);
		}
		
		// 처음 입력이 1개 == 마지막에 남게 되는 카드
		if (qu.size() == 1) {
			System.out.println(1);
		} else {  // 1. 입력이 2개 이상이면
			while (true) {
				if (qu.size() >= 3) {  // 2. 최소 3장이 있어야 주어진 동작을 수행할 수 있음
					qu.pop();
					qu.addLast(qu.pop());
				} else {  // 3. 2장 이하라면, 제일 위에 있는 카드를 버리면 끝.
					qu.pop();
					break;
				}
			}

			System.out.println(qu.pop());
		}

	} // Main

}
