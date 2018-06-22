package de.amr.easy.maze.alg.ust;

import static de.amr.easy.grid.api.GridPosition.TOP_LEFT;

import java.util.stream.IntStream;

import de.amr.easy.graph.api.traversal.TraversalState;
import de.amr.easy.grid.api.GridGraph2D;
import de.amr.easy.grid.iterators.traversals.LeftToRightSweep;

/**
 * Wilson's algorithm where the vertices are selected column-wise left-to-right.
 * 
 * @author Armin Reichert
 */
public class WilsonUSTLeftToRightSweep extends WilsonUST {

	public WilsonUSTLeftToRightSweep(GridGraph2D<TraversalState, Void> grid) {
		super(grid);
	}

	@Override
	protected IntStream cellStream() {
		return new LeftToRightSweep(grid).stream();
	}

	@Override
	protected int customizedStartCell(int start) {
		return grid.cell(TOP_LEFT);
	}
}