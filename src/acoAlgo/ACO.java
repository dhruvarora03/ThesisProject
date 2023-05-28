package acoAlgo;

import java.util.ArrayList;

import javax.lang.model.element.Element;

public class ACO {

	private static final String String = null;
	private CityGraph weight_distance;
	private int citynum = 24;// triangle:24 (38 generate 0~255 three number)
								// bubbleSort:18 (56 generate 0~63 five number)
								// binarySearch:36(56+16 generate 0~63 six number)
	private int p = 1000;// interation times
	private double bestLength;
	private String bestTour;
	private int antNum = 100;
	private int bestAnt = Integer.MAX_VALUE;
	private ANT[] ants;
	private double alpha = 1.0;
	private double beta = 5.0;
	private double Q = 1000;
	private long startTime;
	private long endTime;
	int iterations = 0;

	// initial
	private void Init_Distance() {
		weight_distance = new CityGraph(citynum);
	}

	// initial pares
	private void Init_paras() {
		bestLength = Double.MAX_VALUE;
		bestTour = "";
	}

	// P< the max interations

	private void Init_Ants() { // each interation is a new start, no last interation's info
		ants = null;
		ants = new ANT[antNum];
		for (int i = 0; i < antNum; i++) {

			ants[i] = new ANT(citynum, alpha, beta);
		}
	}

	private void MovetoNextCity() {
		for (int i = 0; i < antNum; i++) {
			ants[i].chooseNextCity();
			// ants[i].updatePheromone();
			// ants[i].calRoad();
		}
	}

	// record the best result right now
	private void findBestRoad() {

		for (int i = 0; i < antNum; i++) {

			if (bestLength > ants[i].getRoadLength()) {
				bestLength = ants[i].getRoadLength();
				bestTour = ants[i].getRoad();
				bestAnt = i;
			}
		}
	}
	// based on update score to update

	private String updatePheromone(String weWantRoute) {

		for (int i = 0; i < antNum; i++) {
			String flag = ants[i].updatePheromone(Q, (endTime - startTime) / 1000, weWantRoute);
			if (flag == "!") {
				return "!";
			}
		}
		return "ao";

	}

	public int iterations() {
		return iterations;
	}

	private String iterator(String weWantRoute) {
		Init_Distance();
		Init_paras();
		startTime = System.currentTimeMillis();
		for (int i = 0; i < p; i++) {// after each iteration,the results will update
			Init_Ants();
			MovetoNextCity();
			findBestRoad();
			endTime = System.currentTimeMillis();
			String flag2 = updatePheromone(weWantRoute);
			if (flag2 == "!") {
				// System.out.println("Found at " + i + "th iteration!");
				iterations = i;
				return "!";
			}
		}
		return "no";
	}

	public static void main(String[] args) {
		ArrayList<String> aList = new ArrayList<>();

		// // bubbleSort
		// int routeLength = 0;
		// for (int j = 1; j < 3; j++) {
		// 	for (int m = 0; m < 3 - j; m++) {
		// 		routeLength++;
		// 	}
		// }
		// Tools tools = new Tools();
		// aList = tools.genWeWantRoute(routeLength);
		// // bubbleSort

		// // binarySearch
		// double arrInt = 5.0;
		// double d = Math.ceil(arrInt / 2);
		// int totalCompareNum = (int) d;
		// Tools tools = new Tools();

		// for (int j = totalCompareNum; j > 1; j--) {
		// ArrayList<String> aListTemp = new ArrayList<>();
		// aListTemp = tools.genWeWantRoute(j - 1);
		// for (int i = 0; i < aListTemp.size(); i++) {
		// String aString = aListTemp.get(i);
		// aList.add(aString + "$");
		// }
		// }
		// aList.remove(2);
		// aList.remove(2);
		// aList.add("$");
		// // binarySearch

		// triangle
		aList.add("000");
		aList.add("001");
		aList.add("11");
		aList.add("22");
		aList.add("33");
		// triangle

		int success = 0;
		int failure = 0;
		long start = System.currentTimeMillis();
		int totalIterations = 0;
		int loop = 1;
		for (int j = loop; j > 0; j--) {
			ACO aco = new ACO();
			for (int i = 0; i < aList.size(); i++) {
				String iString = aco.iterator(aList.get(i));
				if (iString != "!") {
					failure++;
					// i--;
				} else {
					success++;
					System.out.println("Branch: " + aList.get(i));
					System.out.println("***************************");
					totalIterations += aco.iterations();
				}
			}
		}
		long end = System.currentTimeMillis();
		float executionTime = (float) (end - start) / 1000;
		float averageTime = executionTime / loop;
		float codeCoverage = (float) success / (success + failure);
		float averageIterations = (float) totalIterations / success;
		System.out.println("Tested Program: TriangleType.java");
		System.out.println("Code Coverage: " + codeCoverage);
		System.out.println("Success Rate: " + codeCoverage);
		System.out.println("Avearge Execution Time: " + averageTime + "s");
		System.out.println("Average Iterations: " + averageIterations);

	}
}
