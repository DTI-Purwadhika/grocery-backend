package com.finpro.grocery.share.sequence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.share.sequence.entity.Sequence;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, String> {}