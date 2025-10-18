package com.noname.wagwag.repository;

import com.noname.wagwag.repository.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    
    Optional<Video> findByPostId(Long postId);
    
    @Query("SELECT v FROM Video v WHERE v.resolution = :resolution")
    List<Video> findByResolution(@Param("resolution") String resolution);
    
    @Query("SELECT v FROM Video v WHERE v.encodingType = :encodingType")
    List<Video> findByEncodingType(@Param("encodingType") String encodingType);
    
    @Query("SELECT SUM(v.fileSize) FROM Video v")
    Long getTotalFileSize();
    
    boolean existsByPostId(Long postId);
}
