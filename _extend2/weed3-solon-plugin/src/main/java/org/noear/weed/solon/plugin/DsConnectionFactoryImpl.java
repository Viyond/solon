package org.noear.weed.solon.plugin;

import org.noear.solon.transaction.XTranUtils;
import org.noear.weed.DbConnectionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

 class DsConnectionFactoryImpl extends DbConnectionFactory {
    @Override
    public Connection getConnection(DataSource ds) throws SQLException {
        return XTranUtils.getConnection(ds);
    }
}
