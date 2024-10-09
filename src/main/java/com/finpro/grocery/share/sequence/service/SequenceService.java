package com.finpro.grocery.share.sequence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.share.sequence.entity.Sequence;
import com.finpro.grocery.share.sequence.repository.SequenceRepository;

@Service
public class SequenceService {
  @Autowired
  private SequenceRepository sequenceRepository;

  public String generateUniqueCode(String name, String format) {
    Sequence sequence = sequenceRepository.findById(name)
      .orElseGet(() -> initializeSequence(name));
    int nextValue = sequence.getCurrentValue() + 1;
    sequence.setCurrentValue(nextValue);
    sequenceRepository.save(sequence);

    return String.format(format, nextValue);
  }

  private Sequence initializeSequence(String name) {
    Sequence sequence = new Sequence();
    sequence.setSequenceName(name);
    sequence.setCurrentValue(0);
    return sequenceRepository.save(sequence);
  }
}