package org.noear.solon.logging.event;

import java.util.Map;

/**
 * 日志事件
 *
 * @author noear
 * @since 1.0
 */
public class LogEvent {
    /**
     * 日志器名称
     */
    private String loggerName;
    /**
     * 初始化类
     */
    private Class<?> initClass;
    /**
     * 级别
     */
    private Level level;
    /**
     * 原信息
     */
    private Map<String, String> metainfo;
    /**
     * 内容
     */
    private Object content;
    /**
     * 时间戳
     */
    private long timeStamp;
    /**
     * 线程名
     */
    private String threadName;

    public LogEvent(String loggerName, Class<?> initClass, Level level, Map<String, String> metainfo, Object content, long timeStamp, String threadName) {
        this.loggerName = loggerName;
        this.initClass = initClass;
        this.level = level;
        this.metainfo = metainfo;
        this.content = content;
        this.timeStamp = timeStamp;
        this.threadName = threadName;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public Class<?> getInitClass() {
        return initClass;
    }

    public Level getLevel() {
        return level;
    }

    public Map<String, String> getMetainfo() {
        return metainfo;
    }


    public Object getContent() {
        return content;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getThreadName() {
        return threadName;
    }
}
