package example.micronaut;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;

@Filter("/repos/**") // <1>
public class GithubFilter implements HttpClientFilter {

    private final GithubConfiguration configuration;
    
    private static final Logger LOG = LoggerFactory.getLogger(GithubFilter.class);

    public GithubFilter(GithubConfiguration configuration) { // <3>
    	System.out.println("passou no filter: " + GithubConfiguration.GITHUB_API_URL);
    	LOG.info("passou no filter: " + GithubConfiguration.GITHUB_API_URL);
        this.configuration = configuration;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
    	LOG.info("passou no filter: " + request.getPath());
    	System.out.println("passou no filter: " + request.getPath());
        return chain.proceed(request.basicAuth(configuration.getUsername(), configuration.getToken())); // <4>
    }
}
