package com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.domain;

import com.iknowwhatyoudidlastnightintheusastocks.api.commons.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  LocalDate date;
  String title;
  String summary;
  String linkUrl;
  @ManyToOne
  @JoinColumn(name = "subscription_stock_id")
  Stock stock;
}
