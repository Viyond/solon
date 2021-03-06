package org.noear.solon.extend.data.tranImp;

import org.noear.solon.extend.data.annotation.Tran;
import org.noear.solon.ext.RunnableEx;
import org.noear.solon.extend.data.TranNode;

public class TranDbImp extends DbTran implements TranNode {
    public TranDbImp(Tran meta) {
        super(meta);
    }

    @Override
    public void apply(RunnableEx runnable) throws Throwable {
        super.execute(() -> {
            runnable.run();
        });
    }
}
