package com.HighSight.HighSightBackend.repo;

import com.HighSight.HighSightBackend.repo.model.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface HighlightsRepository extends JpaRepository<Highlight, Long> {
    public List<Highlight> getHighlightsByPageId(Integer pageId) ;
}
