package com.finpro.grocery.share.sequence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "code_sequences")
public class Sequence {
  @Id
  @Column(name = "sequence_name", nullable = false, unique = true)
  private String sequenceName;

  @Column(name = "current_value", nullable = false)
  private Integer currentValue = 0;
}

