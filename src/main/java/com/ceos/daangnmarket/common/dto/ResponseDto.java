package com.ceos.daangnmarket.common.dto;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto<T> implements Serializable {

  private Integer code;
  private String code_desc;
  private T data;

  public static <T> ResponseEntity<ResponseDto<T>> ok(T data) {
    return ResponseEntity.ok(new ResponseDto<T>(200, "OK", data));
  }

  public static <T> ResponseEntity<ResponseDto<T>> created(T data) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(new ResponseDto<T>(201,"CREATED", data));
  }

  public static ResponseEntity<Void> noContent() {
    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .build();
  }
}