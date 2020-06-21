package com.HighSight.HighSightBackend.service;

import com.HighSight.HighSightBackend.repo.PagesRepository;
import com.HighSight.HighSightBackend.repo.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class PageService {
    @Autowired
    private PagesRepository repo;

    public Page getByUrl(String url) {
        //strip off params and get the original url
        String shortenedUrl = UriComponentsBuilder.fromUriString(url)
                .replaceQuery(null)
                .build(Collections.emptyMap())
                .toString();
        List<Page> pages = repo.findByUrl(shortenedUrl);
        if (pages.isEmpty()) {
            return null;
        } else {
            return pages.get(0);
        }
    }
}
