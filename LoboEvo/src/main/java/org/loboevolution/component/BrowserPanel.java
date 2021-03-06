package org.loboevolution.component;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.loboevolution.store.GeneralStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedHtml;
import org.loboevolution.tab.TabbedPanePopupMenu;
import org.loboevolution.welcome.WelcomePanel;

public class BrowserPanel extends JPanel implements IBrowserPanel {
	private static final long serialVersionUID = 1L;

	private final JScrollPane scroll;

	private final DnDTabbedPane tabbedPane;

	private IWelcomePanel welcome;
	
	private BrowserFrame browserFrame;

	public BrowserPanel(BrowserFrame browserFrame) {
		super(new BorderLayout());
		this.browserFrame = browserFrame;
		this.scroll = new JScrollPane();
		this.tabbedPane = new DnDTabbedPane(this);
		this.tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this));
		final List<String> startupURLs = GeneralStore.getStartupURLs();

		if (startupURLs.size() == 0) {
			this.welcome = new WelcomePanel(this);
			this.tabbedPane.addTab("Welcome", this.welcome.getWelocome());
		} else if (startupURLs.size() == 1) {
			final TabbedHtml html = new TabbedHtml();
			final String url = startupURLs.get(0);
			html.tab(this, url, 1);
		} else if (startupURLs.size() > 0) {
			final TabbedHtml html = new TabbedHtml();
			for (int i = 0; i < startupURLs.size(); i++) {
				final String url = startupURLs.get(i);
				html.tab(this, url, i);
			}
		}
		this.scroll.getViewport().add(this.tabbedPane);
		add(this.scroll);
	}

	/**
	 * @return the scroll
	 */
	public JScrollPane getScroll() {
		return this.scroll;
	}

	/**
	 * @return the tabbedPane
	 */
	public DnDTabbedPane getTabbedPane() {
		return this.tabbedPane;
	}

	/**
	 * @return the welcome
	 */
	public IWelcomePanel getWelcome() {
		return this.welcome;
	}
	
	/**
	 * @return the browserFrame
	 */
	public BrowserFrame getBrowserFrame() {
		return browserFrame;
	}
}
