package org.noear.solon.boot.smarthttp;

import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.boot.smarthttp.http.SmartHttpContextHandler;
import org.noear.solon.boot.smarthttp.http.FormContentFilter;
import org.noear.solon.boot.smarthttp.websocket.WebSocketHandleImp;
import org.noear.solon.boot.smarthttp.websocket._SessionManagerImpl;
import org.noear.solon.core.Signal;
import org.noear.solon.core.SignalSim;
import org.noear.solon.core.SignalType;
import org.noear.solon.core.handle.MethodType;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.util.PrintUtil;
import org.noear.solon.socketd.SessionManager;
import org.smartboot.http.HttpBootstrap;

public final class XPluginImp implements Plugin {

    HttpBootstrap _server = null;

    public static String solon_boot_ver() {
        return "smart http 1.0.21/" + Solon.cfg().version();
    }

    @Override
    public void start(SolonApp app) {
        if (app.enableHttp() == false) {
            return;
        }

        XServerProp.init();

        String _name = app.cfg().get("server.http.name");
        int _port = app.cfg().getInt("server.http.port", 0);
        if (_port < 1) {
            _port = app.port();
        }

        long time_start = System.currentTimeMillis();

        SmartHttpContextHandler _handler = new SmartHttpContextHandler();

        _server = new HttpBootstrap();
        _server.setBannerEnabled(false);
        _server.pipeline().next(_handler);

        if (app.enableWebSocket()) {
            _server.wsPipeline().next(new WebSocketHandleImp());

            SessionManager.register(new _SessionManagerImpl());
        }

        PrintUtil.info("solon.server:main: SmartHttpServer 1.0.21(smarthttp)");

        try {

            _server.setThreadNum(Runtime.getRuntime().availableProcessors() + 2)
                    .setPort(_port)
                    .start();


            app.signalAdd(new SignalSim(_name, _port, "http", SignalType.HTTP));

            app.before("**", MethodType.ALL, -9, new FormContentFilter());

            long time_end = System.currentTimeMillis();

            String connectorInfo = "solon.connector:main: smarthttp: Started ServerConnector@{HTTP/1.1,[http/1.1]";
            if (app.enableWebSocket()) {
                PrintUtil.info(connectorInfo + "[WebSocket]}{0.0.0.0:" + _port + "}");
            } else {
                PrintUtil.info(connectorInfo + "}{0.0.0.0:" + _port + "}");
            }

            PrintUtil.info("solon.server:main: smarthttp: Started @" + (time_end - time_start) + "ms");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() throws Throwable {
        if (_server != null) {
            _server.shutdown();
            _server = null;

            PrintUtil.info("solon.server:main: smarthttp: Has Stopped " + solon_boot_ver());
        }
    }
}

