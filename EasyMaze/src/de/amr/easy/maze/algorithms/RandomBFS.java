package de.amr.easy.maze.algorithms;

import static de.amr.easy.graph.api.TraversalState.COMPLETED;
import static de.amr.easy.graph.api.TraversalState.UNVISITED;
import static de.amr.easy.graph.api.TraversalState.VISITED;

import java.util.ArrayList;
import java.util.List;

import de.amr.easy.graph.api.TraversalState;
import de.amr.easy.grid.api.ObservableDataGrid2D;

/**
 * Maze generator using a randomized breadth-first-traversal.
 * 
 * @author Armin Reichert
 */
public class RandomBFS extends MazeAlgorithm {

	private final List<Integer> frontier = new ArrayList<>();

	public RandomBFS(ObservableDataGrid2D<TraversalState> grid) {
		super(grid);
	}

	@Override
	public void accept(Integer start) {
		extendsMaze(start);
		while (!frontier.isEmpty()) {
			Integer cell = frontier.remove(rnd.nextInt(frontier.size()));
			grid.neighbors(cell).filter(this::outsideMaze).forEach(neighbor -> {
				extendsMaze(neighbor);
				grid.addEdge(cell, neighbor);
			});
			grid.set(cell, COMPLETED);
		}
	}

	private void extendsMaze(Integer cell) {
		grid.set(cell, VISITED);
		frontier.add(cell);
	}

	private boolean outsideMaze(Integer cell) {
		return grid.get(cell) == UNVISITED;
	}
}