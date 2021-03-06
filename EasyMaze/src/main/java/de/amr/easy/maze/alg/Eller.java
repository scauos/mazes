package de.amr.easy.maze.alg;

import static de.amr.easy.graph.api.traversal.TraversalState.COMPLETED;
import static de.amr.easy.graph.api.traversal.TraversalState.UNVISITED;
import static de.amr.easy.grid.impl.OrthogonalGrid.emptyGrid;
import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.amr.easy.data.Partition;
import de.amr.easy.grid.impl.OrthogonalGrid;
import de.amr.easy.maze.alg.core.MazeGenerator;

/**
 * Maze generator using Eller's algorithm.
 * 
 * @author Armin Reichert
 * 
 * @see <a href= "http://weblog.jamisbuck.org/2010/12/29/maze-generation-eller-s-algorithm">Maze
 *      Generation: Eller's Algorithm</a>.
 * 
 */
public class Eller implements MazeGenerator<OrthogonalGrid> {

	private OrthogonalGrid grid;
	private Random rnd = new Random();
	private Partition<Integer> parts = new Partition<>();

	public Eller(int numCols, int numRows) {
		grid = emptyGrid(numCols, numRows, UNVISITED);
	}

	@Override
	public OrthogonalGrid getGrid() {
		return grid;
	}

	@Override
	public OrthogonalGrid createMaze(int x, int y) {
		range(0, grid.numRows() - 1).forEach(row -> {
			connectCellsInsideRow(row, false);
			connectCellsWithNextRow(row);
		});
		connectCellsInsideRow(grid.numRows() - 1, true);
		return grid;
	}

	private void connectCells(int u, int v) {
		grid.addEdge(u, v);
		grid.set(u, COMPLETED);
		grid.set(v, COMPLETED);
		parts.union(u, v);
	}

	private void connectCellsInsideRow(int row, boolean all) {
		range(0, grid.numCols() - 1).filter(col -> all || rnd.nextBoolean()).forEach(col -> {
			int left = grid.cell(col, row), right = grid.cell(col + 1, row);
			if (parts.find(left) != parts.find(right)) {
				connectCells(left, right);
			}
		});
	}

	private void connectCellsWithNextRow(int row) {
		// connect randomly selected cells of this row with next row
		Set<Partition<Integer>.Set> connectedParts = new HashSet<>();
		range(0, grid.numCols()).filter(col -> rnd.nextBoolean()).forEach(col -> {
			int top = grid.cell(col, row), bottom = grid.cell(col, row + 1);
			connectCells(top, bottom);
			connectedParts.add(parts.find(top));
		});
		// collect cells of still unconnected parts in this row
		List<Integer> unconnectedCells = new ArrayList<>();
		range(0, grid.numCols()).forEach(col -> {
			int cell = grid.cell(col, row);
			Partition<Integer>.Set part = parts.find(cell);
			if (!connectedParts.contains(part)) {
				unconnectedCells.add(cell);
			}
		});
		// shuffle unconnected cells to avoid biased maze
		Collections.shuffle(unconnectedCells);
		// connect cells and mark component as connected
		unconnectedCells.forEach(top -> {
			Partition<Integer>.Set part = parts.find(top);
			if (!connectedParts.contains(part)) {
				int bottom = grid.cell(grid.col(top), row + 1);
				connectCells(top, bottom);
				connectedParts.add(part);
			}
		});
	}
}