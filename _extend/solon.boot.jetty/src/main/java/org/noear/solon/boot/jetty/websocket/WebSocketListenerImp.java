package org.noear.solon.boot.jetty.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.noear.solon.core.*;
import org.noear.solon.extend.xsocket.XListenerProxy;
import org.noear.solon.extend.xsocket.XSocketContextHandler;
import org.noear.solon.xsocket.*;

import java.nio.ByteBuffer;

public class WebSocketListenerImp extends WebSocketAdapter {
    XSocketContextHandler handler;
    XListener listener;

    public WebSocketListenerImp() {
        handler = new XSocketContextHandler(XMethod.WEBSOCKET);
        listener = XListenerProxy.getGlobal();
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);

        if (listener != null) {
            listener.onOpen(_SocketSession.get(getSession()));
        }
    }

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        try {
            ByteBuffer buf = ByteBuffer.wrap(payload, offset, len);
            XSession session = _SocketSession.get(getSession());
            XMessage message = XMessage.wrap(getSession().getUpgradeRequest().getOrigin(),
                    buf.array());

            if (listener != null) {
                listener.onMessage(session, message);
            }
            if (message.getHandled() == false) {
                handler.handle(session, message, false);
            }

        } catch (Throwable ex) {
            XEventBus.push(ex);
        }
    }

    @Override
    public void onWebSocketText(String text) {
        try {
            XSession session = _SocketSession.get(getSession());
            XMessage message = XMessage.wrap(getSession().getUpgradeRequest().getOrigin(),
                    text.getBytes("UTF-8"));

            if (listener != null) {
                listener.onMessage(session, message);
            }

            if (message.getHandled() == false) {
                handler.handle(session, message, true);
            }

        } catch (Throwable ex) {
            XEventBus.push(ex);
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        if (listener != null) {
            listener.onClose(_SocketSession.get(getSession()));
        }

        _SocketSession.remove(getSession());
        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        if (listener != null) {
            listener.onError(_SocketSession.get(getSession()), cause);
        }
    }
}
