package com.HighSight.HighSightBackend.repo;

import com.HighSight.HighSightBackend.repo.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagesRepository extends JpaRepository<Page, Long> {
    public List<Page> findByUrl (String url);
}
