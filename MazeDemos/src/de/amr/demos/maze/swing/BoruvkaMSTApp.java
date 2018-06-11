package de.amr.demos.maze.swing;

import java.util.stream.IntStream;

import de.amr.demos.grid.SwingGridSampleApp;
import de.amr.easy.grid.impl.Top4;
import de.amr.easy.grid.ui.swing.animation.BreadthFirstTraversalAnimation;
import de.amr.easy.maze.alg.mst.BoruvkaMST;

public class BoruvkaMSTApp extends SwingGridSampleApp {

	public static void main(String[] args) {
		launch(new BoruvkaMSTApp());
	}

	public BoruvkaMSTApp() {
		super(64, Top4.get());
		setAppName("Boruvka-MST Maze");
		fullscreen = false;
	}

	@Override
	public void run() {
		IntStream.of(64, 32, 16, 8, 4, 2).forEach(cellSize -> {
			resizeGrid(cellSize);
			new BoruvkaMST(grid).run(-1);
			BreadthFirstTraversalAnimation.floodFill(canvas, grid, 0);
			sleep(1000);
		});
	}
}