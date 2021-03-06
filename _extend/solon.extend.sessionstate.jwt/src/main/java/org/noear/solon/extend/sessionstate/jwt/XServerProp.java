package org.noear.solon.extend.sessionstate.jwt;

import org.noear.solon.Solon;

class XServerProp {
    public static int request_maxRequestSize = 1024 * 1024 * 2;//默认2mb
    public static int session_timeout = 0;
    public static String session_state_domain;
    public static boolean session_state_domain_auto;

    public static String session_jwt_name;
    public static String session_jwt_signKey;
    public static boolean session_jwt_allowExpire;
    public static boolean session_jwt_requestUseHeader;
    public static boolean session_jwt_responseUseHeader;

    public static void init() {
        String tmp = Solon.cfg().get("server.request.maxRequestSize", "").trim().toLowerCase();//k数
        if (tmp.endsWith("mb")) {
            int val = Integer.parseInt(tmp.substring(0, tmp.length() - 2));
            request_maxRequestSize = val * 1204 * 1204;
        }

        if (tmp.endsWith("kb")) {
            int val = Integer.parseInt(tmp.substring(0, tmp.length() - 2));
            request_maxRequestSize = val * 1204;
        }

        session_timeout = Solon.cfg().getInt("server.session.timeout", 0);
        session_state_domain = Solon.cfg().get("server.session.state.domain");
        session_state_domain_auto = Solon.cfg().getBool("server.session.state.domain.auto", true);

        session_jwt_name = Solon.cfg().get("server.session.state.jwt.name", "TOKEN");
        session_jwt_signKey = Solon.cfg().get("server.session.state.jwt.signKey");
        session_jwt_allowExpire = Solon.cfg().getBool("server.session.state.jwt.allowExpire", true);
        session_jwt_requestUseHeader = Solon.cfg().getBool("server.session.state.jwt.requestUseHeader", false);
        session_jwt_responseUseHeader = Solon.cfg().getBool("server.session.state.jwt.responseUseHeader", false);
    }
}
