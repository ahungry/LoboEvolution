/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.renderer;

import javax.swing.JCheckBox;

import org.lobo.common.WrapperLayout;
import org.lobobrowser.html.dom.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.renderer.HtmlController;

public class InputCheckboxControl extends BaseInputControl {
	
	private static final long serialVersionUID = 1L;
	private final JCheckBox widget;

	public InputCheckboxControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		final JCheckBox checkBox = new JCheckBox();
		checkBox.setOpaque(false);
		this.widget = checkBox;
		if(modelNode.getTitle() != null) checkBox.setToolTipText(modelNode.getTitle());
		checkBox.setVisible(!modelNode.getHidden());
		checkBox.applyComponentOrientation(direction(modelNode.getDir()));
		checkBox.setSelected(this.controlElement.getAttributeAsBoolean("checked"));
		checkBox.setEnabled(!modelNode.getDisabled());
		checkBox.setSelected(modelNode.getChecked());
		checkBox.addActionListener(event -> HtmlController.getInstance().onPressed(InputCheckboxControl.this.controlElement, null, 0, 0));
	}

	@Override
	public void click() {
		this.widget.doClick();
	}


	@Override
	public boolean getChecked() {
		return this.widget.isSelected();
	}

	@Override
	public String getValue() {
		return this.controlElement.getAttribute("value");
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
	}

	@Override
	public void resetInput() {
		this.widget.setSelected(this.controlElement.getAttributeAsBoolean("checked"));
	}


	@Override
	public void setChecked(boolean checked) {
		this.widget.setSelected(checked);
	}


	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}
}
