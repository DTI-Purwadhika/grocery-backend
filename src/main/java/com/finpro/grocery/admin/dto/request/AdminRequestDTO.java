package com.finpro.grocery.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {

  private String name;
  private String email;
  private String profilePicture;
  private Long storeId;

}
