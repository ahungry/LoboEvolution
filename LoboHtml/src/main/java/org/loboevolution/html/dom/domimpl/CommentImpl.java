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
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class CommentImpl extends CharacterDataImpl implements Comment {
	public CommentImpl(String text) {
		super(text);
	}

	@Override
	protected Node createSimilarNode() {
		return new CommentImpl(this.text);
	}

	@Override
	public String getLocalName() {
		return null;
	}

	@Override
	public String getNodeName() {
		return "#comment";
	}

	@Override
	public short getNodeType() {
		return Node.COMMENT_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return getTextContent();
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		setTextContent(nodeValue);
	}
}
