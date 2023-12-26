package com.iknowwhatyoudidlastnightintheusastocks.api.members.domain;

import com.iknowwhatyoudidlastnightintheusastocks.api.commons.domain.BaseEntity;
import com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.domain.Stock;
import com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.domain.Subscription;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.sql.Time;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "subscription_id")
  private Subscription subscription;

  public void registerSubscription(String webhookUrl, Time pushAt) {
    subscription = Subscription.createForInit(webhookUrl, pushAt);
  }

  public void subscribeStock(Stock stock) {

    subscription.subscribe(stock);
  }

  public List<Stock> getSubscribingStocks() {
    return subscription.getSubscribingStocks();
  }
}
