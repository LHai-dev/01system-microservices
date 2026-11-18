package com.mptc.training.ecommerce.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class SentinelBlockHandler implements BlockRequestHandler {
    @PostConstruct
    public void register() {
        GatewayCallbackManager.setBlockHandler(this);
    }

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
        if (t instanceof DegradeException) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 502);
            result.put("message", "Limhai service report : service is not found");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.APPLICATION_JSON).bodyValue(result);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 429);
        result.put("message", "limhai service report: Request count exceeds the maximum limit");

        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.APPLICATION_JSON).bodyValue(result);
    }
}
