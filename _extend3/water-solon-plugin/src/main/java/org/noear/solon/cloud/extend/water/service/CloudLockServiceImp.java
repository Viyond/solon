package org.noear.solon.cloud.extend.water.service;

import org.noear.solon.cloud.service.CloudLockService;
import org.noear.water.utils.LockUtils;

/**
 * @author noear 2021/2/3 created
 */
public class CloudLockServiceImp implements CloudLockService {


    @Override
    public boolean lock(String group, String key, int seconds, String holder) {
        if (holder == null) {
            holder = "-";
        }

        return LockUtils.tryLock(group, key, seconds, holder);
    }

    @Override
    public void unlock(String group, String key, String holder) {
        LockUtils.unLock(group, key, holder);
    }

    @Override
    public boolean isLocked(String group, String key) {
        return LockUtils.isLocked(group, key);
    }

    @Override
    public String getLockHolder(String group, String key) {
        return LockUtils.getLockValue(group, key);
    }
}
