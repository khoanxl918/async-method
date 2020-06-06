package com.example.asyncmethod.service;

import com.example.asyncmethod.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GithubLookupService {
    public static final Logger logger = LoggerFactory.getLogger(GithubLookupService.class);

    private final RestTemplate restTemplate;

    public GithubLookupService(RestTemplateBuilder templateBuilder) {
        this.restTemplate = templateBuilder.build();
    }

    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        logger.info("Finding user... " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User result = restTemplate.getForObject(url, User.class);

        //Delay 1 sec for demo purpose
        Thread.sleep(1000);
        return CompletableFuture.completedFuture(result);
    }
}
