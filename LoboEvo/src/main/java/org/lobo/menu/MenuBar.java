package org.lobo.menu;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.lobo.component.BrowserFrame;
import org.lobo.menu.bookmarks.AddBookmarkAction;
import org.lobo.menu.bookmarks.ShowBookmarksAction;
import org.lobo.menu.crono.ShowRecentHostsAction;
import org.lobo.menu.file.OpenFileAction;
import org.lobo.menu.file.SaveFileAction;
import org.lobo.menu.tools.clear.ClearHistoryAction;
import org.lobo.menu.tools.pref.PreferencesAction;
import org.lobo.menu.tools.screen.ScreenShotAction;
import org.lobo.menu.view.CookiePageAction;
import org.lobo.menu.view.FullScreenAction;
import org.lobo.menu.view.InfoPageAction;
import org.lobo.menu.view.SourceAction;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MenuBar(BrowserFrame frame) {
		add(getFileMenu(frame));
		add(getEditMenu());
		add(getViewMenu(frame));
		add(getBookmarksMenu(frame));
		add(getNavigationMenu(frame));
		add(getChronologyMenu(frame));
		add(getToolsMenu(frame));
		add(getHelpMenu(frame));
	}

	/**
	 * Gets the bookmarks menu.
	 *
	 * @return the bookmarks menu
	 */
	public JMenu getBookmarksMenu(BrowserFrame frame) {
		final JMenu menu = new JMenu("Bookmarks");
		menu.setMnemonic('B');
		menu.add(menuItem("Add Bookmark", 'A', "ctrl shift a", new AddBookmarkAction(frame)));
		menu.add(menuItem("Show All Bookmarks", 'S', new ShowBookmarksAction(frame, null)));
		return menu;
	}

	/**
	 * Gets the chronology menu.
	 *
	 * @return the chronology menu
	 */
	public JMenu getChronologyMenu(BrowserFrame frame) {
		final JMenu menu = new JMenu("Recent");
		menu.add(menuItem("Hosts", new ShowRecentHostsAction(frame)));
		menu.add(menuItem("Bookmarks", new ShowBookmarksAction(frame, 20)));
		return menu;
	}

	/**
	 * Gets the edits the menu.
	 *
	 * @return the edits the menu
	 */
	public JMenu getEditMenu() {
		final JMenu menu = new JMenu("Edit");
		// menu.setMnemonic('E');
		// menu.add(menuItem("Copy", 'C', "ctrl C", new CopyAction(window,
		// actionPool)));
		return menu;
	}

	/**
	 * Gets the file menu.
	 *
	 * @return the file menu
	 */
	private JMenu getFileMenu(BrowserFrame frame) {
		final JMenu menu = new JMenu("File");
		menu.setMnemonic('F');
		menu.add(menuItem("Open File...", 'F', "ctrl O", new OpenFileAction(frame)));
		menu.addSeparator();
		menu.add(menuItem("Save As", 'S', "ctrl S", new SaveFileAction(frame)));
		menu.addSeparator();
		menu.add(menuItem("Close", 'C', new ExitAction(frame)));
		menu.addSeparator();
		menu.add(menuItemBlank("Blank Window", 'B', new OpenInTabAction(frame, null)));
		return menu;
	}

	/**
	 * Gets the help menu.
	 *
	 * @return the help menu
	 */
	public JMenu getHelpMenu(BrowserFrame frame) {
		final String homePage = "http://sourceforge.net/projects/loboevolution/";
		final String development = "https://github.com/oswetto/Loboevolution/";
		final String bug = "https://github.com/oswetto/Loboevolution/issues/";
		final String wiki = "http://sourceforge.net/p/loboevolution/wiki/Home/";
		final String forum = "http://sourceforge.net/p/loboevolution/discussion/";

		final JMenu menu = new JMenu("Help");
		menu.setMnemonic('H');
		menu.add(menuItem("About Lobo", 'A', new AboutAction()));
		menu.addSeparator();
		menu.add(menuItem("Project Home Page", new OpenInTabAction(frame, homePage)));
		menu.addSeparator();
		menu.add(menuItem("Development Home Page", new OpenInTabAction(frame, development)));
		menu.addSeparator();
		menu.add(menuItem("Bug tracking", new OpenInTabAction(frame, bug)));
		menu.addSeparator();
		menu.add(menuItem("Wiki", new OpenInTabAction(frame, wiki)));
		menu.addSeparator();
		menu.add(menuItem("Discussion Forum", new OpenInTabAction(frame, forum)));
		return menu;
	}

	/**
	 * Gets the navigation menu.
	 *
	 * @return the navigation menu
	 */
	public JMenu getNavigationMenu(BrowserFrame frame) {
		final JMenu menu = new JMenu("Navigation");
		menu.setMnemonic('N');

		// menu.add(menuItem("Back", 'B', "ctrl B", new BackAction(window,
		// actionPool)));
		// menu.add(menuItem("Forward", 'F', new ForwardAction(window, actionPool)));
		// menu.add(menuItem("Stop", 'S', KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
		// new StopAction(this, window)));
		// menu.add(menuItem("Reload", 'R', KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
		// new ReloadAction(window, actionPool)));

		return menu;
	}

	/**
	 * Gets the tools menu.
	 *
	 * @return the tools menu
	 */
	public JMenu getToolsMenu(BrowserFrame frame) {
		final JMenu menu = new JMenu("Tools");
		menu.setMnemonic('T');
		menu.add(menuItem("Preferences...", 'P', new PreferencesAction(frame)));
		menu.add(menuItem("Screenshot", ' ', "", new ScreenShotAction(frame)));
		menu.add(menuItem("Clear History", 'M', "ctrl M", new ClearHistoryAction(frame)));
		return menu;
	}

	/**
	 * Gets the view menu.
	 *
	 * @return the view menu
	 */
	public JMenu getViewMenu(BrowserFrame frame) {
		final JMenu menu = new JMenu("View");
		menu.setMnemonic('V');
		menu.add(menuItem("Page Source", 'S', new SourceAction(frame)));
		menu.add(menuItem("Info Page", ' ', new InfoPageAction(frame)));
		menu.add(menuItem("Cookie Page", ' ', new CookiePageAction(frame)));
		menu.add(menuItem("Full Screen", ' ', KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), new FullScreenAction(frame)));
		return menu;
	}

	/**
	 * Menu item.
	 *
	 * @param title  the title
	 * @param action the action
	 * @return the j menu item
	 */
	private JMenuItem menuItem(String title, Action action) {
		return menuItem(title, (char) 0, (KeyStroke) null, action);
	}

	/**
	 * Menu item.
	 *
	 * @param title    the title
	 * @param mnemonic the mnemonic
	 * @param action   the action
	 * @return the j menu item
	 */
	private JMenuItem menuItem(String title, char mnemonic, Action action) {
		return menuItem(title, mnemonic, (KeyStroke) null, action);
	}

	/**
	 * Menu item.
	 *
	 * @param title       the title
	 * @param mnemonic    the mnemonic
	 * @param accelerator the accelerator
	 * @param action      the action
	 * @return the j menu item
	 */
	private JMenuItem menuItem(String title, char mnemonic, KeyStroke accelerator, Action action) {
		final JMenuItem item = new JMenuItem();
		item.setAction(action);
		item.setText(title);
		if (mnemonic != 0) {
			item.setMnemonic(mnemonic);
		}
		if (accelerator != null) {
			item.setAccelerator(accelerator);
		}
		return item;
	}

	/**
	 * Menu item.
	 *
	 * @param title       the title
	 * @param mnemonic    the mnemonic
	 * @param accelerator the accelerator
	 * @param action      the action
	 * @return the j menu item
	 */
	private JMenuItem menuItem(String title, char mnemonic, String accelerator, Action action) {
		final KeyStroke keyStroke = accelerator == null ? null : KeyStroke.getKeyStroke(accelerator);
		return menuItem(title, mnemonic, keyStroke, action);
	}

	/**
	 * Menu item blank.
	 *
	 * @param title       the title
	 * @param mnemonic    the mnemonic
	 * @param accelerator the accelerator
	 * @param action      the action
	 * @return the j menu item
	 */
	private JMenuItem menuItemBlank(String title, char mnemonic, Action action) {
		final KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK);
		return menuItem(title, mnemonic, keyStroke, action);
	}
}