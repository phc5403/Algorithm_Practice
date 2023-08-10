import java.util.StringTokenizer;

public class Temp {  /////////////
	static StringBuilder sb;
	static StringTokenizer st;
	static boolean[][] visited;  // bfs() 방문 처리
	static boolean[][] isSelected; // 순열 방문 처리
	static boolean[][] operation; // 회전 연산의 정보를 저장
	static int[][] lst;  // 원본 배열
	static int N, M, K;  // 행, 열, 회전 연산의 개수
	static int res;  // Answer : 배열 A의 값 중 최솟값
	
	// 좌하우상
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {-1, 0, 1, 0};

	public static void main(String[] args) {
		/*
		 * 1. 입력 받기
		 * 2. 회전 연산을 2차원 배열에 저장
		 * 3. 원본 배열을 복사함
		 * 4. 복사본을 중복 순열의 경우의수 -> 그에 맞게 회전 -> 최솟값 구하기
		 * 5. 모든 경우의 수를 끝낼때 까지 4번 반복.
		 */
	}
	
	/**
	 * 각 계산을 마친 배열의 최솟값을 구하는 함수
	 * @param array : 계산을 위한 원본 배열의 복사본
	 */
	private static void arrayCalcul(int[][] array) {
		int sum = 0;
		
		/*
		 * 1. sum += array의 각 행에 있는 모든 수의 합.
		 * 2. Math.min(res, sum) -> sum과 비교하여 최솟값으로 res를 갱신
		 */
		res = Math.min(res, sum);
	}
	
	/**
	 * 원본 배열의 복사본을 입력으로 받은 회전 연산의 범위대로
	 * 시계 방향으로 회전하는 함수 
	 * @param r : 회전 연산을 위한 회전 범위가 조정 될 행 좌표
	 * @param s : 회전 연산을 위한 회전 범위가 조정 될 열 좌표
	 * @param x : 출발점의 행 좌표
	 * @param y : 출발점의 열 좌표
	 * @param array : 원본 배열 복사본
	 */
	private static void rotate(int r, int s, int x, int y, int[][] array) {
		// 배열 돌리기 1(반시계 회전)을 수정해 시계 방향 회전을 구현(현재 실패)
	}
	
	/**
	 * 회전 연산의 순서의 종류를 정하기 위한 중복 순열을 구하는 함수
	 * @param cnt : 현재 선택된 숫자의 수
	 * @param isSelected : 방문 처리용
	 */
	private static void perm(int cnt, int[][] isSelected) {
		// 회전 연산의 경우의 수가 나올 때 마다,
		// 그 순서대로 회전 시키기 위해 rotate() 호출.
		if (cnt == K) {   
//			rotate(x, y, array);
		}
		
		// 중복 순열을 구하는 로직
		// ...
		// ...
		
	}
}
