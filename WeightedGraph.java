import java.util.Stack;

public class WeightedGraph {
	static int infinite = 999; // 무한대는 999로 설정.
	int size;
	String[] vertices; // 정점 인덱스의 String형 (A, B, C, D ...)을 가지고 있는 String 배열.
	int[][] a; // 가중치를 저장 할 int형의 2x2 배열 선언.

	public WeightedGraph(String[] args) { // WeightedGraph 객체 생성.
		size = args.length; // 배열의 사이즈는 입력한 String 배열의 개수, 메인에서의 inputArr 배열.
		vertices = new String[size]; // String 배열 vertices 초기화.
		System.arraycopy(args, 0, vertices, 0, size); // vertices 배열에 초기에 입력한 정점들을 삽입.
		a = new int[size][size]; // 2차원 배열 초기화.

	}

	public void add(String v, String w, String wei) { // WeightGraph 배열에 가중치 삽입.
		int weight = Integer.parseInt(wei); // 입력받은 가중치가 String이기 때문에 Int형으로 Casting.
		int i = index(v), j = index(w); // 입력받은 String과 일치하는 index를 받는다.
		a[i][j] = weight; // 해당 index에 weight를 삽입한다.
		a[j][i] = weight; // 반대의 경우에도 동일하게 삽입힌다.
	}

	public void fillInfinite() { // 행과 열의 index가 같을 경우를 제외하고, 가중치가 0인 경우는 모두 무한으로 채운다.
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (i != j && a[i][j] == 0)
					a[i][j] = infinite;
			}
		}
	}

	public int index(String v) { // 정점 String과 일치하는 index를 리턴받기 위한 메소드.
		for (int i = 0; i < size; i++)
			if (vertices[i].equals(v))
				return i;
		return a.length;
	}

	public void printGraph() { // 2x2의 가중치 그래프를 출력하기 위한 메소드.
		System.out.println("---<PrintGraph>---");
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + " "); // 열 출력
			}
			System.out.println(); // 행 출력
		}
	}

	public int popDistance(String start, String end) { // 입력받은 출발점과 도착점 사이 간선의 가중치를 return
		int s = index(start);
		int e = index(end);
		return a[s][e]; // 두 정점 사이의 거리가 나온다.
	}

	public void Dijkstra(int[][] graph, String start, String end) { // Dijkstra 알고리즘
		int verticesCount = graph.length; // 정점의 수
		boolean[] isVisits = new boolean[verticesCount]; // 방문점 배열
		int[] distance = new int[verticesCount]; // Distance 배열
		int[] PrevPath = new int[verticesCount]; // Prev 배열

		int nextVertex = index(start); // distance 배열의 최소값의 정점
		int min = 0; // distance 배열의 최소값
		
		for (int i = 0; i < verticesCount; i++) {
			isVisits[i] = false; // 방문 한 곳 없다고 초기화
			distance[i] = infinite; // 전부 다 무한대로 초기화
			PrevPath[i] = infinite; // 전부 다 무한대로 초기화
		}
		distance[index(start)] = 0; // 시작점 0 으로 초기화

		// 다익스트라 실행
		while (true) {
			min = infinite; // 최소값을 infinity로 초기화
			for (int j = 0; j < verticesCount; j++) {
				if (isVisits[j] == false && distance[j] < min) { // 가장먼저 방문한 정점은 제외하고 최소값을 조사한다.
					nextVertex = j; // 다음 이동할 정점 선택.
					min = distance[j]; // 다음으로 이동한 최소값
				}
			}
			if (min == infinite)
				break; // 최소값이 infinity이면 모든 정점을 지났다는 것, 최소값이 모든 정점을 지났으면
						// infinity
			isVisits[nextVertex] = true; // 다음으로 이동할 정점 방문

			for (int j = 0; j < verticesCount; j++) {
				int distanceVertex = distance[nextVertex] + graph[nextVertex][j]; // 정점에서 방문한 다른 정점의 거리
				if (distance[j] > distanceVertex) // 정점에서 다른 정점에서의 거리가 distance
													// 배열보다 적다면 교체해 준다.
				{
					distance[j] = distanceVertex; // 교체해 준다.
					PrevPath[j] = nextVertex; // 교체 된다면 그 지점의 정점을 기록을 남긴다.
				}
			}
		}
		Printpath(PrevPath, start, end);
	}

	public void Printpath(int[] historyPath, String start, String end) { // 경로계산 메소드
		Stack<Integer> path = new Stack<Integer>();
		int vertex = index(end); // 거꾸로 탐색한다.
		while (true) {
			path.push(vertex);
			if (vertex == index(start))
				break; // 시작점이면 리턴한다.
			vertex = historyPath[vertex]; // 탐색
		}
		int popedVertices[] = new int[path.size()]; // Path의 사이즈 만큼을 가진 popedVertices 배열을 만든다.

		int index = 0;
		while (!path.isEmpty()) {
			popedVertices[index] = path.pop(); // 스택에서 pop하는 동시에 popedVertices 배열에 저장한다.
			index = index + 1;
		}
		System.out.println(start); // 출발점인 start를 출력한다.

		int dis = 0; // 하나씩 더해지면서 늘어나는 거리를 나타내는 변수.
		int disIndex = 0;

		for (int i = 0; i <= popedVertices.length; i++) {
			if (disIndex < popedVertices.length - 1) { // -1을 해주는 이유는 반복해야하는 횟수가 배열의 length보다 1이 작기 때문.
				dis = dis + popDistance(vertices[popedVertices[disIndex]], vertices[popedVertices[disIndex + 1]]);
				// 경로에 따른 현재 정점->다음 정점까지의 가중치를 dis에 차곡차곡 더한다.
				disIndex = disIndex + 1;
				System.out.println("->" + vertices[popedVertices[disIndex]] + "(" + dis + ")");
			}
		}
	}

}
