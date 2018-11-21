import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class TestDijkstra {
	public static void main(String[] args) {

		String parsedVertices[] = new String[9]; // 정점의 수, 정점들의 배열을 저장하는 배열 선언.
		String parsedWeight[] = new String[36]; // 정점 사이의 가중치를 포함하는 배열을 선언.
		String inputArr[] = new String[8]; // WeightedGraph를 만들기 위해 입력할 배열 선언.

		FileReader fr = null;
		try {
			fr = new FileReader("c:\\users\\KimDoYeon\\Desktop\\input.txt"); // FileReader사용, input.txt 파일 오픈. 
			BufferedReader br = new BufferedReader(fr); // 버퍼 리더 선언.

			String line = null; // line은 input.txt의 첫줄부터 읽어들인 문자열
			int indexA = 0; // parsedVertices 배열에 value를 넣기 위한 인덱스.
			int indexB = 0; // parsedWieght 배열에 value를 넣기 위한 인덱스.
			while ((line = br.readLine()) != null) { // 버퍼 리더의 다음 라인이 없을 때 까지 반복, 즉 처음부터 끝까지 읽는다.
				StringTokenizer st = new StringTokenizer(line); // 읽어드린 문자열을 자르기 위해 StringTokenizer 선선.
				if (st.countTokens() == 1) { // 토큰의 개수가 1일 경우
					while (st.hasMoreTokens()) {
						parsedVertices[indexA] = st.nextToken(); // parsedVertices 배열에 넣는다.
						indexA = indexA + 1; // 인덱스 증가.
					}
				} else if (st.countTokens() == 3) { // 토큰의 개수가 3일경우, 3개씩 짝일 경우.
					while (st.hasMoreTokens()) {
						parsedWeight[indexB] = st.nextToken(); // parsedWieght 배열에 넣는다.
						indexB = indexB + 1; // 인덱스 증가.
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e); // 예외가 발생했을 경우 Exception 출력.
		} finally {
			try {
				fr.close(); // 파일을 모두 읽어서 배열에 원하는 값을 저장하였으니 파일을 닫는다.
			} catch (Exception e) {
			}
		}

		for (int i = 1; i < parsedVertices.length; i++) {
			inputArr[i - 1] = parsedVertices[i]; // Graph를 만들 때에는 정점들의 배열만 필요하기 때문에 정점의 수를 제외한 나머지를 inputArr에 넣는다.
		}

		WeightedGraph g = new WeightedGraph(inputArr); // 정점들의 배열인 inputArr를 바탕으로 가중치 그래프인 WeightedGraph를 만든다.

		for (int i = 0; i < parsedWeight.length; i += 3) { // parsedWieght에는 3개의 value씩 짝을 이루기 때문에 이를 바탕으로 add 해준다.
			g.add(parsedWeight[i], parsedWeight[i + 1], parsedWeight[i + 2]);
		}

		g.printGraph(); // 2x2배열인 가중치 그래프를 출력해본다.
		g.fillInfinite(); // 행과 열의 index가 같은 경루를 제외하고 0인 경우를 모두 infinite로 채운다.
		g.printGraph(); // infinite를 채워넣은 그래프를 출력해본다.

		System.out.println("---<Dijkstra>---");
		
		g.Dijkstra(g.a, "A", "B"); // Dijkstra 알고리즘을 실행한다.
		g.Dijkstra(g.a, "A", "C");
		g.Dijkstra(g.a, "A", "D");
		g.Dijkstra(g.a, "A", "E");
		g.Dijkstra(g.a, "A", "F");
		g.Dijkstra(g.a, "A", "G");
		g.Dijkstra(g.a, "A", "H");
	}
}
