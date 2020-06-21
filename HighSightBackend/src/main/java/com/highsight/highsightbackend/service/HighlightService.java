package com.HighSight.HighSightBackend.service;

import com.HighSight.HighSightBackend.controller.model.Tag;
import com.HighSight.HighSightBackend.repo.HighlightsRepository;
import com.HighSight.HighSightBackend.repo.PagesRepository;
import com.HighSight.HighSightBackend.repo.model.Highlight;
import com.HighSight.HighSightBackend.repo.model.OverallScore;
import com.HighSight.HighSightBackend.repo.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class HighlightService {
    @Autowired
    private HighlightsRepository highlightsRepository;

    @Autowired
    private PagesRepository pagesRepository;

    public List<Highlight> getHighlightsForUrl(String url){
        List<Page> pages = pagesRepository.findByUrl(url);
        Page page = pages.isEmpty() ? null : pages.get(0);
        if (page == null){
            return Collections.emptyList();
        } else {
            return highlightsRepository.getHighlightsByPageId(page.getId());
        }
    }

    public Highlight saveHighlight(String url, String content, Tag tag) {
        List<Page> pages = pagesRepository.findByUrl(url);
        Page matchingPage = null;
        if (pages.isEmpty()) {
            OverallScore score = tag == Tag.Useful ? OverallScore.POSITIVE : OverallScore.NEGATIVE;
            Page page = new Page(url, score.name());
            matchingPage = pagesRepository.save(page);
        }
        else {
            matchingPage = pages.get(0);
        }
        Highlight highlight = new Highlight(matchingPage.getId(), content, tag.name());
        Highlight savedHighlight = highlightsRepository.save(highlight);
        // update page overallscore
        List<Highlight> allHighlights = highlightsRepository.getHighlightsByPageId(matchingPage.getId());
        Long usefulTags = allHighlights.stream().filter(h -> h.getTag().equals(Tag.Useful.name())).count();
        Long inaccurateTags = allHighlights.stream().filter(h -> h.getTag().equals(Tag.Inaccurate.name())).count();
        OverallScore newOverallScore = usefulTags.equals(inaccurateTags) ? OverallScore.NEUTRAL :
                (usefulTags > inaccurateTags ? OverallScore.POSITIVE : OverallScore.NEGATIVE);
        pagesRepository.save(new Page(matchingPage.getId(), matchingPage.getUrl(), newOverallScore.name()));
        return savedHighlight;
    }
}
