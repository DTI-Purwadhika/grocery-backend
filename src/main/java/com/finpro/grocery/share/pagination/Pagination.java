package com.finpro.grocery.share.pagination;

import java.util.List;

import lombok.Data;

@Data
public class Pagination<T> {
  private int totalPages;
  private long totalElements;
  private boolean first;
  private boolean last;
  private List<T> content;

  public Pagination(int totalPages, long totalElements, boolean first, boolean last, List<T> content) {
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.first = first;
    this.last = last;
    this.content = content;
  }
}
