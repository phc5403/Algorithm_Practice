import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Main {
	static int size;
	static boolean[] lst;
	static int N;  // 학생 수
	static int gender;  // 성별
	static int num;  // 학생들이 받은 수
	
	public static void main(String[] args) throws IOException {	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		size = Integer.parseInt(st.nextToken());
		lst = new boolean[size];
		
		// 스위치 상태 초기화
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < size; i++) {
			if (Integer.parseInt(st.nextToken()) == 1) {
				lst[i] = true;
			} else {
				lst[i] = false;
			}
		}
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		// 학생들 마다 스위치 변경
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			gender = Integer.parseInt(st.nextToken());
			num = Integer.parseInt(st.nextToken());
			
			 func(gender, num);
		}
		
		// 최종 스위치 변경 상태를 출력
		for (int i = 0; i < size; i++) {
			if (i != 0 && i % 20 == 0) {  // 한 줄에 20개씩 출력
				System.out.println();
			}
			
			if (lst[i] == true) {
				System.out.print("1");
				if (i != size - 1) System.out.print(" ");
			} else {
				System.out.print("0");
				if (i != size - 1) System.out.print(" ");
			}
		}
	}
	
	static boolean[] func(int gender, int num) {
		if (gender == 1) { // 남
			for (int i = 0; i < size; i++) {
				if ((i + 1) % num == 0) {  // 스위치 번호가 자기가 받은 수의 배수
					lst[i] = !lst[i];  // 스위치 상태 변경
				}
			}
			return lst;
			
		} else {  // 여
			// 좌우 대칭 확인용
			int left = num - 2;  
			int right = num; 
			
			lst[num - 1] = !lst[num - 1];  // 본인 번호는 무조건 바뀜
			while (true) {
				// 1. 좌우가 대칭 일 경우만 모두 상태를 변경
				if ((0 <= left && right < size) && (lst[left] == lst[right])) {
					lst[left] = !lst[left];
					lst[right] = !lst[right];
					left--;
					right++;
				} else {  // 2. 대칭이 아니거나, 모든 범위를 확인 했다면 break
					break;
				}
			}
			return lst;
		}
	}
}

