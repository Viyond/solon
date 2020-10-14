package org.noear.solon.serialization.snack3;

import org.noear.snack.ONode;
import org.noear.solon.web.XContext;
import org.noear.solon.web.XRender;

//不要要入参，方便后面多视图混用
//
public class SnackRender implements XRender {

    private boolean _typedJson;
    public SnackRender(boolean typedJson) {
        _typedJson = typedJson;
    }

    @Override
    public void render(Object obj, XContext ctx) throws Throwable {
        String txt = null;

        if (_typedJson) {
            //序列化处理
            //
            txt = ONode.serialize(obj);
        } else {
            //非序列化处理
            //
            if (obj == null) {
                return;
            }

            if (obj instanceof Throwable) {
                throw (Throwable) obj;
            }

            if (obj instanceof String) {
                ctx.output((String) obj); //不能做为json输出
                return;
            }

            txt = ONode.stringify(obj);
        }

        if(XPluginImp.output_meta) {
            ctx.headerSet("solon.serialization", "SnackRender");
        }

        ctx.attrSet("output", txt);
        ctx.outputAsJson(txt);
    }
}
