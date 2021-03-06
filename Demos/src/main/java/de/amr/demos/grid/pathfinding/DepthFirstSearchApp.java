package de.amr.demos.grid.pathfinding;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import de.amr.easy.graph.impl.traversal.DepthFirstTraversal2;
import de.amr.easy.grid.api.GridPosition;
import de.amr.easy.grid.impl.OrthogonalGrid;
import de.amr.easy.grid.ui.swing.rendering.ConfigurableGridRenderer;
import de.amr.easy.grid.ui.swing.rendering.WallPassageGridRenderer;
import de.amr.easy.maze.alg.mst.KruskalMST;

/**
 * A simple test application.
 * 
 * @author Armin Reichert
 */
public class DepthFirstSearchApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(DepthFirstSearchApp::new);
	}

	private OrthogonalGrid grid;
	private Iterable<Integer> solution;
	private DrawingArea canvas;
	private ConfigurableGridRenderer renderer;

	private class DrawingArea extends JComponent {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawGrid((Graphics2D) g);
		}

		private void drawGrid(Graphics2D g) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(renderer.getGridBgColor());
			g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			renderer.drawGrid(g, grid);
			if (solution != null) {
				drawSolution(g);
			}
		}

		private void drawSolution(Graphics2D g) {
			int prev = -1;
			int cs = renderer.getCellSize();
			for (int cell : solution) {
				int x = cs * grid.col(cell) + cs / 2, y = cs * grid.row(cell) + cs / 2;
				if (prev != -1) {
					int px = cs * grid.col(prev) + cs / 2, py = cs * grid.row(prev) + cs / 2;
					g.setColor(Color.BLUE);
					g.setStroke(new BasicStroke(3));
					g.drawLine(px, py, x, y);
				}
				prev = cell;
			}
		}
	};

	public DepthFirstSearchApp() {
		newMaze(8);

		canvas = new DrawingArea();
		canvas.setPreferredSize(new Dimension(800, 800));

		renderer = new WallPassageGridRenderer();
		renderer.fnCellBgColor = cell -> Color.WHITE;
		renderer.fnCellSize = () -> Math.min(canvas.getHeight(), canvas.getWidth()) / grid.numRows();
		renderer.fnGridBgColor = () -> Color.BLACK;
		renderer.fnPassageColor = (cell, dir) -> renderer.getCellBgColor(cell);
		renderer.fnPassageWidth = () -> renderer.getCellSize() * 95 / 100;
		renderer.fnText = cell -> String.format("%d", cell);
		renderer.fnTextColor = cell -> Color.LIGHT_GRAY;
		renderer.fnTextFont = () -> new Font(Font.SANS_SERIF, Font.PLAIN, renderer.getCellSize() / 3);

		addKeyboardAction('s', this::dfs);
		addKeyboardAction(' ', this::newMaze);
		addKeyboardAction('+', this::largerMaze);
		addKeyboardAction('-', this::smallerMaze);

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("DFS Test Application");
		window.getContentPane().add(canvas);
		window.pack();
		window.setVisible(true);
	}

	private void addKeyboardAction(char key, Runnable code) {
		canvas.getInputMap().put(KeyStroke.getKeyStroke(key), "action_" + key);
		canvas.getActionMap().put("action_" + key, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				code.run();
				if (canvas != null) {
					canvas.repaint();
				}
			}
		});
	}

	private void largerMaze() {
		if (grid.numRows() < canvas.getHeight() / 4) {
			newMaze(2 * grid.numRows());
		}
	}

	private void smallerMaze() {
		if (grid.numRows() >= 2) {
			newMaze(grid.numRows() / 2);
		}
	}

	private void newMaze(int gridSize) {
		grid = new KruskalMST(gridSize, gridSize).createMaze(0, 0);
		solution = null;
	}

	private void newMaze() {
		newMaze(grid.numRows());
	}

	private void dfs() {
		DepthFirstTraversal2 dfs = new DepthFirstTraversal2(grid);
		dfs.traverseGraph(grid.cell(GridPosition.TOP_LEFT), grid.cell(GridPosition.BOTTOM_RIGHT));
		solution = dfs.path(grid.cell(GridPosition.BOTTOM_RIGHT));
	}
}