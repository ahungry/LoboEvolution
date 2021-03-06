package org.loboevolution.html.js.geolocation;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import org.loboevolution.html.dom.domimpl.NodeImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.Window;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.mozilla.javascript.Function;

/**
 * <p>
 * The <code>Geolocation</code> class provides Java implementation of the
 * "Geolocation Interface" as detailed out in the W3C Specifications (
 * <a href="http://www.w3.org/TR/geolocation-API/#geolocation_interface">
 * http://www.w3.org/TR/geolocation-API/#geolocation_interface</a>).
 * </p>
 * 
 * <p>
 * <b>Note: This class must not have any sub-classes to ensure W3C
 * Specifications are being strictly followed by the system or application that
 * uses this geolocation package.</b>
 * </p>
 */
public class Geolocation extends AbstractScriptableDelegate {

	private Window window;

	public Geolocation(Window window) {
		this.window = window;
	}

	public void getCurrentPosition(final Function success) throws Exception {
		final IPAddressBasedGeoAcquirer ip = new IPAddressBasedGeoAcquirer();
		final Position acquireLocation = ip.acquireLocation();
		final NodeImpl node = (NodeImpl) window.getDocumentNode();
		Executor.executeFunction(node, success, null, new Object[] { acquireLocation });
	}

	public void getCurrentPosition(final Function success, final Function error) {
		try {
			getCurrentPosition(success);
		} catch (Exception e) {
			geoError(error, e);
		}
	}

	public long watchPosition(final Function success) {
		final long watchId = System.currentTimeMillis();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						getCurrentPosition(success);
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		return watchId;
	}

	public long watchPosition(final Function success, final Function error) {
		final long watchId = System.currentTimeMillis();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						getCurrentPosition(success, error);
						Thread.sleep(500);
					} catch (Exception e) {
						geoError(error, e);
						break;
					}
				}
			}
		});
		t.start();
		return watchId;
	}

	private void geoError(final Function error, Exception e) {
		final NodeImpl node = (NodeImpl) window.getDocumentNode();
		PositionError pError = null;
		if (e instanceof UnknownHostException) {
			pError = new PositionError(PositionError.POSITION_UNAVAILABLE);
		} else if (e instanceof TimeoutException) {
			pError = new PositionError(PositionError.TIMEOUT);
		}
		Executor.executeFunction(node, error, null, new Object[] { pError });
	}
}