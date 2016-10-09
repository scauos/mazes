package de.amr.easy.maze.algorithms;

import static de.amr.easy.graph.api.TraversalState.COMPLETED;
import static de.amr.easy.grid.api.Direction.E;
import static de.amr.easy.grid.api.Direction.S;

import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import de.amr.easy.graph.api.TraversalState;
import de.amr.easy.graph.impl.DefaultEdge;
import de.amr.easy.grid.api.Direction;
import de.amr.easy.grid.api.ObservableDataGrid2D;

/**
 * Creates a random binary spanning tree.
 * 
 * @author Armin Reichert
 */
public class BinaryTree implements Consumer<Integer> {

	protected final ObservableDataGrid2D<Integer, DefaultEdge<Integer>, TraversalState> grid;

	private final Random rnd = new Random();

	public BinaryTree(ObservableDataGrid2D<Integer, DefaultEdge<Integer>, TraversalState> grid) {
		this.grid = grid;
	}

	@Override
	public void accept(Integer start) {
		cellStream().forEach(cell -> {
			randomNeighbor(cell, S, E).ifPresent(neighbor -> {
				grid.addEdge(new DefaultEdge<>(cell, neighbor));
				grid.set(cell, COMPLETED);
				grid.set(neighbor, COMPLETED);
			});
		});
	}

	/**
	 * 
	 * @param cell
	 *          a grid cell
	 * @param firstDir
	 *          first direction of tree
	 * @param secondDir
	 *          second direction of tree
	 * @return a random neighbor towards one of the given directions or nothing
	 */
	private Optional<Integer> randomNeighbor(Integer cell, Direction firstDir, Direction secondDir) {
		boolean b = rnd.nextBoolean();
		Integer neighbor = grid.neighbor(cell, b ? firstDir : secondDir);
		return neighbor != null ? Optional.of(neighbor)
				: Optional.ofNullable(grid.neighbor(cell, b ? secondDir : firstDir));
	}

	/*
	 * @return stream of all grid cells in the order used for maze creation
	 */
	protected Stream<Integer> cellStream() {
		return grid.vertexStream();
	}
}