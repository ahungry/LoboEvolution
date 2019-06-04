package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dom.HTMLParagraphElement;
import org.lobobrowser.html.style.ParagraphRenderState;
import org.lobobrowser.html.style.RenderState;

public class HTMLPElementImpl extends HTMLAbstractUIElement implements HTMLParagraphElement {
	public HTMLPElementImpl(String name) {
		super(name);
	}

	@Override
	protected void appendInnerTextImpl(StringBuffer buffer) {
		final int length = buffer.length();
		int lineBreaks;
		if (length == 0) {
			lineBreaks = 2;
		} else {
			int start = length - 4;
			if (start < 0) {
				start = 0;
			}
			lineBreaks = 0;
			for (int i = start; i < length; i++) {
				final char ch = buffer.charAt(i);
				if (ch == '\n') {
					lineBreaks++;
				}
			}
		}
		for (int i = 0; i < 2 - lineBreaks; i++) {
			buffer.append("\r\n");
		}
		super.appendInnerTextImpl(buffer);
		buffer.append("\r\n\r\n");
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ParagraphRenderState(prevRenderState, this);
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}
}