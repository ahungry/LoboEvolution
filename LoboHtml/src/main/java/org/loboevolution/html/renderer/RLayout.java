package org.loboevolution.html.renderer;

import java.util.HashMap;
import java.util.Map;

import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.control.BaseInputControl;
import org.loboevolution.html.control.CanvasControl;
import org.loboevolution.html.control.ImgControl;
import org.loboevolution.html.control.InputSelectControl;
import org.loboevolution.html.control.InputTextAreaControl;
import org.loboevolution.html.control.RImgControl;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.control.SVGControl;
import org.loboevolution.html.control.UIControl;
import org.loboevolution.html.control.UIControlWrapper;
import org.loboevolution.html.dom.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.dom.domimpl.HTMLCanvasElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.dom.domimpl.UINode;
import org.loboevolution.html.dom.svgimpl.SVGSVGElementImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;

public class RLayout {
	
	protected static final Map<HTMLTag, MarkupLayout> elementLayout = new HashMap<HTMLTag, MarkupLayout>();
	
	static {
		final Map<HTMLTag, MarkupLayout> el = elementLayout;
		final EmLayout em = new EmLayout();
		el.put(HTMLTag.I,em);
		el.put(HTMLTag.EM,em);
		el.put(HTMLTag.CITE,em);
		el.put(HTMLTag.H1,new HLayout(24));
		el.put(HTMLTag.H2,new HLayout(18));
		el.put(HTMLTag.H3,new HLayout(15));
		el.put(HTMLTag.H4,new HLayout(12));
		el.put(HTMLTag.H5,new HLayout(10));
		el.put(HTMLTag.H6,new HLayout(8));
		final StrongLayout strong = new StrongLayout();
		el.put(HTMLTag.B,strong);
		el.put(HTMLTag.STRONG,strong);
		el.put(HTMLTag.TH,strong);
		el.put(HTMLTag.U,new ULayout());
		el.put(HTMLTag.STRIKE,new StrikeLayout());
		el.put(HTMLTag.BR,new BrLayout());
		el.put(HTMLTag.P,new PLayout());
		el.put(HTMLTag.NOSCRIPT,new NoScriptLayout());
		final NopLayout nop = new NopLayout();
		el.put(HTMLTag.SCRIPT,nop);
		el.put(HTMLTag.HEAD,nop);
		el.put(HTMLTag.TITLE,nop);
		el.put(HTMLTag.META,nop);
		el.put(HTMLTag.STYLE,nop);
		el.put(HTMLTag.LINK,nop);
		el.put(HTMLTag.IMG,new ImgLayout());
		el.put(HTMLTag.TABLE,new TableLayout());
		el.put(HTMLTag.TR, new TableRowLayout());
		el.put(HTMLTag.TD, new TableCellLayout());
		final AnchorLayout anchor = new AnchorLayout();
		el.put(HTMLTag.A,anchor);
		el.put(HTMLTag.ANCHOR,anchor);
		el.put(HTMLTag.INPUT,new InputLayout());
		el.put(HTMLTag.TEXTAREA,new TextAreaLayout());
		el.put(HTMLTag.SELECT,new SelectLayout());
		final ListItemLayout list = new ListItemLayout();
		el.put(HTMLTag.UL,list);
		el.put(HTMLTag.OL,list);
		el.put(HTMLTag.LI,list);
		final CommonBlockLayout cbl = new CommonBlockLayout();
		el.put(HTMLTag.PRE,cbl);
		el.put(HTMLTag.CENTER,cbl);
		el.put(HTMLTag.CAPTION,cbl);
		final DivLayout div = new DivLayout();
		el.put(HTMLTag.DIV,div);
		el.put(HTMLTag.BODY,div);
		el.put(HTMLTag.DL,div);
		el.put(HTMLTag.DT,div);
		el.put(HTMLTag.HTML,div);
		final BlockQuoteLayout bq = new BlockQuoteLayout();
		el.put(HTMLTag.BLOCKQUOTE,bq);
		el.put(HTMLTag.DD,bq);
		el.put(HTMLTag.HR,new HrLayout());
		el.put(HTMLTag.SPAN,new SpanLayout());
		final ObjectLayout ol = new ObjectLayout(false);
		el.put(HTMLTag.OBJECT,new ObjectLayout(true));
		el.put(HTMLTag.APPLET,ol);
		el.put(HTMLTag.EMBED,ol);
		el.put(HTMLTag.CANVAS,new CanvasLayout());
		el.put(HTMLTag.SVG,new SVGLayout());
		//el.put(HTMLTag.IFRAME,new IFrameLayout());
	}
	
	protected static class AnchorLayout extends CommonLayout {
		public AnchorLayout() {
			super(RenderState.DISPLAY_INLINE);
		}
	}

	protected static class BlockQuoteLayout extends CommonLayout {
		public BlockQuoteLayout() {
			super(RenderState.DISPLAY_BLOCK);
		}
	}

	protected static class BrLayout implements MarkupLayout {

		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final String clear = markupElement.getAttribute("clear");
			bodyLayout.addLineBreak(markupElement, LineBreak.getBreakType(clear));
		}
	}

	protected static class CommonBlockLayout extends CommonLayout {
		public CommonBlockLayout() {
			super(RenderState.DISPLAY_BLOCK);
		}
	}

	protected static abstract class CommonLayout implements MarkupLayout {
	
	    private final int display;

		public CommonLayout(int defaultDisplay) {
			this.display = defaultDisplay;
		}

		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final RenderState rs = markupElement.getRenderState();
			int display = markupElement.getHidden() ? RenderState.DISPLAY_NONE : rs == null ? this.display : rs.getDisplay();

	        if (display == RenderState.DISPLAY_INLINE || display == RenderState.DISPLAY_INLINE_BLOCK) {
	            final int position = rs == null ? RenderState.POSITION_STATIC : rs.getPosition();
	            if (position == RenderState.POSITION_ABSOLUTE || position == RenderState.POSITION_FIXED) {
	                display = RenderState.DISPLAY_BLOCK;
	            } else {
	                final int boxFloat = rs == null ? RenderState.FLOAT_NONE : rs.getFloat();
	                if (boxFloat != RenderState.FLOAT_NONE) {
	                    display = RenderState.DISPLAY_BLOCK;
	                }
	            }
	        }
			
	        switch (display) {
	        case RenderState.DISPLAY_TABLE_COLUMN:
	        case RenderState.DISPLAY_TABLE_COLUMN_GROUP:
	        case RenderState.DISPLAY_NONE:
	            final UINode node = markupElement.getUINode();
	            if (node instanceof BaseBoundableRenderable) {
	                ((BaseBoundableRenderable) node).markLayoutValid();
	            }
	            break;
	        case RenderState.DISPLAY_BLOCK:
	        case RenderState.DISPLAY_TABLE_ROW:
	            bodyLayout.layoutRBlock(markupElement);
	            break;
	        case RenderState.DISPLAY_LIST_ITEM:
	            final String tagName = markupElement.getTagName();
	            if ("UL".equalsIgnoreCase(tagName) || "OL".equalsIgnoreCase(tagName)) {
	                bodyLayout.layoutList(markupElement);
	            } else {
	                bodyLayout.layoutListItem(markupElement);
	            }
	            break;
			case RenderState.DISPLAY_TABLE:
                bodyLayout.layoutRTable(markupElement);
                break;
	        case RenderState.DISPLAY_INLINE_TABLE:
	        case RenderState.DISPLAY_INLINE_BLOCK:
	            bodyLayout.layoutRInlineBlock(markupElement);
	            break;
	        case RenderState.DISPLAY_TABLE_CELL:
	        default:
	            bodyLayout.layoutMarkup(markupElement);
	            break;
	        }
		}
	}

	/**
	 * This is layout common to elements that render themselves, except RBlock,
	 * RTable and RList.
	 */
	protected static abstract class CommonWidgetLayout implements MarkupLayout {
		protected static final int ADD_INLINE = 0;
	    protected static final int ADD_AS_BLOCK = 1;
	    protected static final int ADD_INLINE_BLOCK = 2;
	    private final int method;

	    public CommonWidgetLayout(int method) {
	    	this.method = method;
	    }

	    protected abstract RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement);

	    @Override
	    public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
	        final UINode node = markupElement.getUINode();
	        RElement renderable = null;
	        if (node == null) {
	            renderable = createRenderable(bodyLayout, markupElement);
	            if (renderable == null) {
	                return;
	            }
	            markupElement.setUINode(renderable);
	        } else {
	            renderable = (RElement) node;
	        }
	        renderable.setOriginalParent(bodyLayout);
	        switch (method) {
	        case ADD_INLINE:
	            bodyLayout.addRenderableToLineCheckStyle(renderable, markupElement, true);
	            break;
	        case ADD_AS_BLOCK:
	        case ADD_INLINE_BLOCK:
	            bodyLayout.positionRElement(markupElement, renderable, true, true, false);
	            break;
	        default:
	            return;
	        }
	    }
	}
	
	protected static class CanvasLayout extends CommonWidgetLayout {
		public CanvasLayout() {
			super(ADD_AS_BLOCK);
		}

		@Override
		protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			UIControl control = new CanvasControl((HTMLCanvasElementImpl) markupElement);
			return new RUIControl(markupElement,control, bodyLayout.container,
					bodyLayout.frameContext, bodyLayout.userAgentContext);
		}
	}
	
	
	protected static class SVGLayout extends CommonWidgetLayout {
		public SVGLayout() {
			super(ADD_AS_BLOCK);
		}

		@Override
		protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			UIControl control = new SVGControl((SVGSVGElementImpl) markupElement);
			return new RUIControl(markupElement,control, bodyLayout.container,
					bodyLayout.frameContext, bodyLayout.userAgentContext);
		}
	}
	
	
	protected static class DivLayout extends CommonLayout {
		public DivLayout() {
			super(RenderState.DISPLAY_BLOCK);
		}
	}

	protected static class EmLayout extends CommonLayout {
		public EmLayout() {
			super(RenderState.DISPLAY_INLINE);
		}


		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			super.layoutMarkup(bodyLayout, markupElement);
		}
	}

	protected static class HLayout extends CommonLayout {
		public HLayout(int fontSize) {
			super(RenderState.DISPLAY_BLOCK);
		}

		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			super.layoutMarkup(bodyLayout, markupElement);
		}
	}

	protected static class HrLayout implements MarkupLayout {

		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			bodyLayout.layoutHr(markupElement);
		}
	}

	protected static class ImgLayout extends CommonWidgetLayout {
		public ImgLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final UIControl control = new ImgControl((HTMLImageElementImpl) markupElement);
			return new RImgControl(markupElement, control, bodyLayout.container, bodyLayout.frameContext,
					bodyLayout.userAgentContext);
		}
	}

	protected static class InputLayout extends CommonWidgetLayout {
		public InputLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final HTMLBaseInputElement bie = (HTMLBaseInputElement) markupElement;
			final BaseInputControl uiControl = bodyLayout.createInputControl(bie);
			if (uiControl == null) {
				return null;
			}
			bie.setInputContext(uiControl);
			return new RUIControl(markupElement, uiControl, bodyLayout.container, bodyLayout.frameContext,
					bodyLayout.userAgentContext);
		}
	}

	protected static class ListItemLayout extends CommonLayout {
		public ListItemLayout() {
			super(RenderState.DISPLAY_LIST_ITEM);
		}
	}

	protected static class MiscLayout extends CommonLayout {
		public MiscLayout() {
			super(RenderState.DISPLAY_INLINE);
		}
	}

	protected static class NopLayout implements MarkupLayout {
		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		}
	}

	protected static class NoScriptLayout implements MarkupLayout {
		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final UserAgentContext ucontext = bodyLayout.userAgentContext;
			if (!ucontext.isScriptingEnabled()) {
				bodyLayout.layoutMarkup(markupElement);
			}
		}
	}

	protected static class ObjectLayout extends CommonWidgetLayout {
		/**
		 * Must use this ThreadLocal because an ObjectLayout instance is shared across
		 * renderers.
		 */
		private final ThreadLocal<HtmlObject> htmlObject = new ThreadLocal<HtmlObject>();

		private final boolean tryToRenderContent;

		/**
		 * @param tryToRenderContent If the object is unknown, content is rendered as
		 *                           HTML.
		 * @param usesAlignAttribute
		 */
		public ObjectLayout(boolean tryToRenderContent) {
			super(ADD_INLINE);
			this.tryToRenderContent = tryToRenderContent;
		}

		@Override
		protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final HtmlObject ho = (HtmlObject) this.htmlObject.get();
			final UIControl uiControl = new UIControlWrapper(ho);
			final RUIControl ruiControl = new RUIControl(markupElement, uiControl, bodyLayout.container,
					bodyLayout.frameContext, bodyLayout.userAgentContext);
			return ruiControl;
		}

		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final HtmlObject ho = bodyLayout.rendererContext.getHtmlObject(markupElement);
			if (ho == null && this.tryToRenderContent) {
				// Don't know what to do with it - render contents.
				bodyLayout.layoutMarkup(markupElement);
			} else if (ho != null) {
				this.htmlObject.set(ho);
				super.layoutMarkup(bodyLayout, markupElement);
			}
		}
	}

	protected static class PLayout extends CommonLayout {
		public PLayout() {
			super(RenderState.DISPLAY_BLOCK);
		}

		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			super.layoutMarkup(bodyLayout, markupElement);
		}
	}

	protected static class SelectLayout extends CommonWidgetLayout {
		public SelectLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final HTMLBaseInputElement bie = (HTMLBaseInputElement) markupElement;
			final BaseInputControl uiControl = new InputSelectControl(bie);
			bie.setInputContext(uiControl);
			return new RUIControl(markupElement, uiControl, bodyLayout.container, bodyLayout.frameContext,
					bodyLayout.userAgentContext);
		}
	}

	protected static class SpanLayout extends CommonLayout {
		public SpanLayout() {
			super(RenderState.DISPLAY_INLINE);
		}
	}

	protected static class StrikeLayout extends CommonLayout {
		public StrikeLayout() {
			super(RenderState.DISPLAY_INLINE);
		}
	}

	protected static class StrongLayout extends CommonLayout {
		public StrongLayout() {
			super(RenderState.DISPLAY_INLINE);
		}
	}

	protected static class TableLayout extends CommonLayout {
		public TableLayout() {
			super(RenderState.DISPLAY_TABLE);
		}
	}
	
	protected static class TableRowLayout extends CommonLayout {
		public TableRowLayout() {
			super(RenderState.DISPLAY_TABLE_ROW);
		}
	}
	
	protected static class TableCellLayout extends CommonLayout {
		public TableCellLayout() {
			super(RenderState.DISPLAY_TABLE_CELL);
		}
	}

	protected static class TextAreaLayout extends CommonWidgetLayout {
		public TextAreaLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			final HTMLBaseInputElement bie = (HTMLBaseInputElement) markupElement;
			final BaseInputControl control = new InputTextAreaControl(bie);
			bie.setInputContext(control);
			return new RUIControl(markupElement, control, bodyLayout.container, bodyLayout.frameContext,
					bodyLayout.userAgentContext);
		}
	}

	protected static class ULayout extends CommonLayout {
		public ULayout() {
			super(RenderState.DISPLAY_INLINE);
		}

		@Override
		public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
			super.layoutMarkup(bodyLayout, markupElement);
		}
	}
}