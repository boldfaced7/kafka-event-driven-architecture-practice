package com.fastcampus.kafkahandson.ugc;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {}
