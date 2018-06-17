package de.amr.easy.graph.impl.traversal;

import static de.amr.easy.graph.api.traversal.TraversalState.COMPLETED;
import static de.amr.easy.graph.api.traversal.TraversalState.UNVISITED;
import static de.amr.easy.graph.api.traversal.TraversalState.VISITED;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import de.amr.easy.data.Stack;
import de.amr.easy.graph.api.Graph;

/**
 * Alternative implementation of Depth-first-traversal of an undirected graph.
 * 
 * @author Armin Reichert
 */
public class DepthFirstTraversal2 extends AbstractGraphTraversal {

	private final Stack<Integer> stack = new Stack<>();

	public DepthFirstTraversal2(Graph<?> graph) {
		super(graph);
	}

	protected void clear() {
		parentMap.clear();
		stateMap.clear();
		stack.clear();
	}

	@Override
	public boolean inQ(int v) {
		return stack.contains(v);
	}

	@Override
	public void traverseGraph(int source, int target) {
		clear();
		int current = source;
		stack.push(current);
		visit(current, -1);
		while (!stack.isEmpty()) {
			if (current == target) {
				break;
			}
			OptionalInt neighbor = unvisitedChildren(current).findAny();
			if (neighbor.isPresent()) {
				visit(neighbor.getAsInt(), current);
				if (unvisitedChildren(neighbor.getAsInt()).findAny().isPresent()) {
					stack.push(neighbor.getAsInt());
				}
				current = neighbor.getAsInt();
			} else {
				setState(current, COMPLETED);
				if (!stack.isEmpty()) {
					current = stack.pop();
				}
				if (getState(current) == VISITED) {
					stack.push(current);
				}
			}
		}
		while (!stack.isEmpty()) {
			setState(stack.pop(), COMPLETED);
		}
	}

	private void visit(int child, int parent) {
		setState(child, VISITED);
		parentMap.put(child, parent);
		if (parent != -1) {
			edgeTraversed(parent, child);
		}
	}

	private IntStream unvisitedChildren(int v) {
		return graph.adj(v).filter(child -> getState(child) == UNVISITED);
	}
}