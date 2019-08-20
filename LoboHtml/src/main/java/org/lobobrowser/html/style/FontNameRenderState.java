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

package org.lobobrowser.html.style;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;

public class FontNameRenderState extends StyleSheetRenderState {
	
	private final String fontName;
	
	private RenderState delegate;

	public FontNameRenderState(RenderState prevRenderState, HTMLElementImpl element, String fontName) {
		super(prevRenderState, element);
		this.fontName = fontName;
		this.delegate = prevRenderState;
	}

	@Override
	public Font getFont() {
		final Font parentFont = this.delegate.getFont();
		Font f = new Font(this.fontName, parentFont.getStyle(), parentFont.getSize());
		return f;
	}
}