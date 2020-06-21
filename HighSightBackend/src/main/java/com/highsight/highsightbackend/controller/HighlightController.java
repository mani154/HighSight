package com.HighSight.HighSightBackend.controller;

import com.HighSight.HighSightBackend.controller.model.AddHighlightRequest;
import com.HighSight.HighSightBackend.controller.model.GetHighlightsForPageRequest;
import com.HighSight.HighSightBackend.controller.model.Tag;
import com.HighSight.HighSightBackend.repo.model.Highlight;
import com.HighSight.HighSightBackend.service.HighlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HighlightController {

    @Autowired
    HighlightService highlightService;

    @RequestMapping(path = "/highlights/get", produces = "application/json", method = RequestMethod.POST)
    public List<Highlight> getHighlightsForUrl(@RequestBody GetHighlightsForPageRequest getHighLightsForPageRequest) {
        System.out.println("Request received for " + getHighLightsForPageRequest.getUrl());
        List<Highlight> response = highlightService.getHighlightsForUrl(getHighLightsForPageRequest.getUrl());
        System.out.println("sending response " + response);
        return response;
    }

    @RequestMapping(path = "/highlights/add", produces = "application/json", method = RequestMethod.POST)
    public Highlight getHighlightsForUrl(@RequestBody AddHighlightRequest addHighlightRequest) {
        System.out.println("Request received for " + addHighlightRequest.getUrl());
        Tag tag = addHighlightRequest.isUseful() ? Tag.Useful : Tag.Inaccurate;
        Highlight response = highlightService.saveHighlight(addHighlightRequest.getUrl(),
                addHighlightRequest.getContent(), tag);
        System.out.println("sending response " + response);
        return response;
    }

}
