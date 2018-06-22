package de.amr.easy.maze.alg.ust;

import static de.amr.easy.grid.api.GridPosition.TOP_LEFT;

import java.util.stream.IntStream;

import de.amr.easy.grid.iterators.shapes.Rectangle;
import de.amr.easy.grid.iterators.traversals.ExpandingRectangle;
import de.amr.easy.maze.alg.core.OrthogonalGrid;

/**
 * Wilson's algorithm where the vertices are selected from an expanding rectangle.
 * 
 * @author Armin Reichert
 */
public class WilsonUSTExpandingRectangle extends WilsonUST {

	public WilsonUSTExpandingRectangle(OrthogonalGrid grid) {
		super(grid);
	}

	@Override
	public void run(int start) {
		super.run(grid.cell(TOP_LEFT));
	}

	@Override
	protected IntStream randomWalkStartCells() {
		Rectangle startRect = new Rectangle(grid, grid.cell(TOP_LEFT), 1, 1);
		ExpandingRectangle expRect = new ExpandingRectangle(startRect);
		expRect.setExpandHorizontally(true);
		expRect.setExpandVertically(true);
		expRect.setMaxExpansion(grid.numCols() - 1);
		return expRect.stream();
	}
}