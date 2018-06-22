package de.amr.demos.maze.swing;

import java.util.stream.IntStream;

import de.amr.demos.grid.SwingGridSampleApp;
import de.amr.easy.grid.ui.swing.animation.BreadthFirstTraversalAnimation;
import de.amr.easy.maze.alg.mst.ReverseDeleteDFSMST;

public class ReverseDeleteDFSApp extends SwingGridSampleApp {

	public static void main(String[] args) {
		launch(new ReverseDeleteDFSApp());
	}

	public ReverseDeleteDFSApp() {
		super(128);
		setAppName("Reverse-Delete-MST Maze (DFS)");
	}

	@Override
	public void run() {
		IntStream.of(128, 64, 32).forEach(cellSize -> {
			resizeGrid(cellSize);
			new ReverseDeleteDFSMST(grid).run(-1);
			BreadthFirstTraversalAnimation.floodFill(canvas, grid, 0);
			sleep(1000);
		});
		System.exit(0);
	}
}