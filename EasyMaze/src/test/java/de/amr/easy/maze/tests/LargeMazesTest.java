package de.amr.easy.maze.tests;

import org.junit.Test;

import de.amr.easy.maze.alg.RecursiveDivision;
import de.amr.easy.maze.alg.mst.KruskalMST;

public class LargeMazesTest {

	private void test_Kruskal(int numCols, int numRows) {
		new KruskalMST(numCols, numRows).createMaze(0, 0);
	}

	private void test_RecursiveDivision(int numCols, int numRows) {
		new RecursiveDivision(numCols, numRows).createMaze(0, 0);
	}

	@Test
	public void test_Kruskal_100_000() {
		test_Kruskal(100, 1000);
	}

	@Test
	public void test_Kruskal_500_000() {
		test_Kruskal(500, 1000);
	}

	@Test
	public void test_Kruskal_1_000_000() {
		test_Kruskal(1000, 1000);
	}

	@Test
	public void test_RecursiveDivision_100_000() {
		test_RecursiveDivision(100, 1000);
	}

	@Test
	public void test_RecursiveDivision_500_000() {
		test_RecursiveDivision(500, 1000);
	}

	@Test
	public void test_RecursiveDivision_1_000_000() {
		test_RecursiveDivision(1000, 1000);
	}
}