package org.noear.solon.cloud.service;

/**
 * 云端跟踪服务（链路）
 *
 * @author noear
 * @since 1.2
 */
public interface CloudTraceService {
    /**
     * TraceId 头名称
     */
    String HEADER_TRACE_ID_NAME();

    /**
     * 获取跟踪标识
     */
    String getTraceId();
}
