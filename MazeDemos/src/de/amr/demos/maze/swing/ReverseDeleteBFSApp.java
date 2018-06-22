package de.amr.demos.maze.swing;

import java.util.stream.IntStream;

import de.amr.demos.grid.SwingGridSampleApp;
import de.amr.easy.grid.ui.swing.animation.BreadthFirstTraversalAnimation;
import de.amr.easy.maze.alg.mst.ReverseDeleteBFSMST;

public class ReverseDeleteBFSApp extends SwingGridSampleApp {

	public static void main(String[] args) {
		launch(new ReverseDeleteBFSApp());
	}

	public ReverseDeleteBFSApp() {
		super(128);
		setAppName("Reverse-Delete-MST Maze (BFS)");
	}

	@Override
	public void run() {
		IntStream.of(128, 64, 32).forEach(cellSize -> {
			resizeGrid(cellSize);
			new ReverseDeleteBFSMST(grid).run(-1);
			BreadthFirstTraversalAnimation.floodFill(canvas, grid, 0);
			sleep(1000);
		});
		System.exit(0);
	}
}