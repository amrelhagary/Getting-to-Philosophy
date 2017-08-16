package com.bento.GettingToPhilosophy.Controller;

import com.bento.GettingToPhilosophy.Entity.Wiki;
import com.bento.GettingToPhilosophy.Error.ErrorInfo;
import com.bento.GettingToPhilosophy.Service.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private WikiService wikiService;

    @Autowired
    public RestApiController(WikiService wikiService){
        this.wikiService = wikiService;
    }

    @RequestMapping(value="/find-philosophy/{topic-name}")
    public Wiki trackDown(@PathVariable("topic-name") String topicName) throws Exception{

        Wiki topic = new Wiki();
        try{
            topic = wikiService.findPhilosophy("https://en.wikipedia.org/wiki/" + topicName);
        }catch (Exception e){
            throw e;
        }

        return topic;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exceptionHandling(final Exception exception){
        return exception.getMessage();
    }
}
