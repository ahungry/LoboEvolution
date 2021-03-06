package org.loboevolution.tab;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import org.loboevolution.common.Strings;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.store.TabStore;

public class DnDTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	private final DragGestureListener dragGestureListener = new DragGestureListenerImpl(this);

	protected int dragTabIdx = -1;

	private final DropTargetListener dropTargetListener = new DropTargetListenerImpl(this);

	private GlassPane glass;
	
	private IBrowserPanel browserPanel;

	private int index;
	
	public DnDTabbedPane(IBrowserPanel browserPanel) {
		init(browserPanel);
	}

	protected int getDropIndex(Point p) {
		int idx = indexAtLocation(p.x, p.y);
		if (idx == -1 && getTabCount() > 0) {
			final Rectangle tabBounds = getBoundsAt(getTabCount() - 1);
			if (tabBounds.x + tabBounds.width <= p.x && tabBounds.y <= p.y + 1
					&& p.y <= tabBounds.y + tabBounds.height) {
				idx = getTabCount() - 1;
			}
		}
		return idx;
	}

	/**
	 * @return the glass
	 */
	protected GlassPane getGlass() {
		return this.glass == null ? new GlassPane(this) : this.glass;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	private void init(IBrowserPanel browserPanel) {
		setUI(new TabbedPaneUI(this));
		this.browserPanel = browserPanel;
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this.dropTargetListener, true);
		new DragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this.dragGestureListener);
		final ChangeListener changeListener = changeEvent -> {
			final JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
			IBrowserFrame browserFrame = browserPanel.getBrowserFrame();
			String uri = TabStore.getTab(sourceTabbedPane.getSelectedIndex());
			if (browserFrame.getToolbar() != null && Strings.isNotBlank(uri)) {
				browserFrame.getToolbar().getAddressBar().setText(uri);
			}
			setIndex(sourceTabbedPane.getSelectedIndex());
		};
		addChangeListener(changeListener);
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the browserPanel
	 */
	public IBrowserPanel getBrowserPanel() {
		return browserPanel;
	}
}