package de.amr.easy.maze.alg.ust;

import java.util.stream.IntStream;

import de.amr.easy.grid.iterators.traversals.CollapsingWalls;
import de.amr.easy.maze.alg.core.OrthogonalGrid;

/**
 * Wilson's algorithm where the vertices are selected alternating left-to-right and right-to-left
 * column-wise.
 * 
 * @author Armin Reichert
 */
public class WilsonUSTCollapsingWalls extends WilsonUST {

	public WilsonUSTCollapsingWalls(OrthogonalGrid grid) {
		super(grid);
	}

	@Override
	protected IntStream randomWalkStartCells() {
		return new CollapsingWalls(grid).stream();
	}
}