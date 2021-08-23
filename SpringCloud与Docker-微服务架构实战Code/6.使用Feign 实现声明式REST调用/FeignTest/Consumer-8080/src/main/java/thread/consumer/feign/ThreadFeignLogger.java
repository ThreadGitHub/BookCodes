package thread.consumer.feign;

import feign.Logger;
import feign.Request;
import feign.Response;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * 重写feign.Logger实现 用SpringBoot的自定义配置 大于 原本自动注入的原则 这里重写了实现 优先用这里
 * 修改: 把下面原本判断Debug级别的位置 换成INFO
 */
public class ThreadFeignLogger extends feign.Logger{
    private final org.slf4j.Logger logger;

    public ThreadFeignLogger() {
        this(Logger.class);
    }

    public ThreadFeignLogger(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz));
    }

    public ThreadFeignLogger(String name) {
        this(LoggerFactory.getLogger(name));
    }

    ThreadFeignLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (this.logger.isInfoEnabled()) {
            super.logRequest(configKey, logLevel, request);
        }

    }
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        return this.logger.isInfoEnabled() ? super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime) : response;
    }
    @Override
    protected void log(String configKey, String format, Object... args) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info(String.format(methodTag(configKey) + format, args));
        }
    }
}
