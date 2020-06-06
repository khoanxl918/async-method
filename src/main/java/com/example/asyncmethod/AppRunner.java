package com.example.asyncmethod;

import com.example.asyncmethod.model.User;
import com.example.asyncmethod.service.GithubLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AppRunner implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GithubLookupService service;

    public AppRunner(GithubLookupService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        //Starts the clock
        long start = System.currentTimeMillis();

        //Kicks of multiple, asynchronous lookups
        CompletableFuture<User> page1 = service.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = service.findUser("CloudFoundry");
        CompletableFuture<User> page3 = service.findUser("Spring-Projects");
        CompletableFuture<User> page4 = service.findUser("khoanxl918");

        //Waits until they are all done
        CompletableFuture.allOf(page1, page2, page3, page4).join();

        //Prints results, including elapsed time
        logger.info("Elapsed time :" + (System.currentTimeMillis() - start));
        logger.info("-->" + page1.get());
        logger.info("-->" + page2.get());
        logger.info("-->" + page3.get());
        logger.info("-->" + page4.get());
    }
}
