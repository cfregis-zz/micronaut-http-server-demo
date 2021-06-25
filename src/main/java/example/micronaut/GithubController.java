package example.micronaut;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/github") // <1>
public class GithubController {

    private final GithubLowLevelClient githubLowLevelClient;
    private final GithubApiClient githubApiClient;
    
    private static final Logger LOG = LoggerFactory.getLogger(GithubFilter.class);

    public GithubController(GithubLowLevelClient githubLowLevelClient,
                            GithubApiClient githubApiClient) { // <2>
        this.githubLowLevelClient = githubLowLevelClient;
        this.githubApiClient = githubApiClient;
    }

    @Get("/releases-lowlevel") // <3>
    Maybe<List<GithubRelease>> releasesWithLowLevelClient() { // <4>
    	LOG.info("passou no GithubController: " + GithubConfiguration.GITHUB_API_URL + "/releases-lowlevel");
        return githubLowLevelClient.fetchReleases();
    }

    @Get(uri = "/releases", produces = MediaType.APPLICATION_JSON_STREAM) // <5>
    Flowable<GithubRelease> fetchReleases() { // <6>
    	LOG.info("passou no GithubController: " + GithubConfiguration.GITHUB_API_URL + "/releases");
        return githubApiClient.fetchReleases();
    }
}
