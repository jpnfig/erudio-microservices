package br.com.erudio.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import reactor.core.publisher.Mono;

@Component
public class RequestMonitorWebFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return chain.filter(exchange)
				.contextWrite(context -> {
					ContextSnapshotFactory.builder().build().setThreadLocalsFrom(
							context, ObservationThreadLocalAccessor.KEY);
							return context;
				});
	}

}
