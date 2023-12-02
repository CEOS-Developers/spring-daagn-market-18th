package com.ceos.daangnmarket.domain.file.entity;

import com.ceos.daangnmarket.common.BaseEntity;
import com.ceos.daangnmarket.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file")
public class File extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "uploader_id")
  private Account account;

  private String type;

  private String name;

}
