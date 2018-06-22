package de.amr.easy.maze.alg.mst;

import static de.amr.easy.graph.api.traversal.TraversalState.COMPLETED;
import static de.amr.easy.util.StreamUtils.permute;

import de.amr.easy.graph.api.Edge;
import de.amr.easy.maze.alg.core.MazeGenerator;
import de.amr.easy.maze.alg.core.OrthogonalGrid;

/**
 * Maze generator derived from the Reverse-Delete-MST algorithm.
 * 
 * @author Armin Reichert
 *
 * @see <a href="https://en.wikipedia.org/wiki/Reverse-delete_algorithm">Wikipedia</a>
 */
public abstract class ReverseDeleteMST implements MazeGenerator {

	protected final OrthogonalGrid grid;

	public ReverseDeleteMST(OrthogonalGrid grid) {
		this.grid = grid;
	}

	@Override
	public void run(int start) {
		grid.setDefaultVertex(COMPLETED);
		grid.fill();
		Iterable<Edge<Void>> edges = permute(grid.edges())::iterator;
		for (Edge<Void> edge : edges) {
			if (grid.numEdges() == grid.numVertices() - 1) {
				break;
			}
			int u = edge.either(), v = edge.other();
			grid.removeEdge(edge);
			if (!connected(u, v)) {
				grid.addEdge(u, v);
			}
		}
	}

	/**
	 * 
	 * @param u
	 *          a cell
	 * @param v
	 *          a cell
	 * @return {@code true} if given cells are connected by some path
	 */
	protected abstract boolean connected(int u, int v);
}