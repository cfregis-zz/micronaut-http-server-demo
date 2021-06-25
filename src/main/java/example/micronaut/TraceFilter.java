package example.micronaut;

import org.reactivestreams.Publisher;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;

@Filter("/github/**") 
public class TraceFilter implements HttpServerFilter { 
	private final TraceService traceService;

	public TraceFilter(TraceService traceService) { 
		this.traceService = traceService;
	}

	@Override
	public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
		return traceService.trace(request) 
				.switchMap(aBoolean -> {
					return aBoolean?chain.proceed(request): Publishers.just(HttpResponse.status(HttpStatus.UNAUTHORIZED));
				})
				.doOnNext(res -> res.getHeaders().add("X-Trace-Enabled", "true"));
	}

}