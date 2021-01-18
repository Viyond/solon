package org.noear.solon.cloud.extend.water.service;

import org.noear.solon.cloud.CloudDiscoveryHandler;
import org.noear.solon.cloud.model.Discovery;
import org.noear.water.dso.DiscoverHandler;
import org.noear.water.model.DiscoverM;

/**
 * @author noear
 * @since 1.2
 */
public class CloudDiscoveryObserverEntity implements DiscoverHandler {
    public String service;
    public CloudDiscoveryHandler handler;

    public CloudDiscoveryObserverEntity(String service, CloudDiscoveryHandler handler) {
        this.service = service;
        this.handler = handler;
    }

    @Override
    public void handler(DiscoverM d1) {
        Discovery d2 = ConvertUtil.from(service, d1);
        handler.handler(d2);
    }
}