package com.HighSight.HighSightBackend.controller;

import com.HighSight.HighSightBackend.controller.model.GetHighlightsForPageRequest;
import com.HighSight.HighSightBackend.repo.model.Page;
import com.HighSight.HighSightBackend.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class PageController {

    @Autowired
    PageService pageService;

    @RequestMapping(path = "/pages/get", produces = "application/json", method = RequestMethod.POST)
    public Page getPageByURL(@RequestBody GetHighlightsForPageRequest getHighLightsForPageRequest) {
        return pageService.getByUrl(getHighLightsForPageRequest.getUrl());
    }
}