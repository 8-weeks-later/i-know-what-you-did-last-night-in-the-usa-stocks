package com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.domain;

import com.iknowwhatyoudidlastnightintheusastocks.api.commons.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String webhookUrl;
  Time pushAt;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscription", cascade = CascadeType.ALL)
  private List<SubscriptionStock> subscriptionStocks;

  @Builder
  public Subscription(String webhookUrl, Time pushAt, List<SubscriptionStock> subscriptionStocks) {
    this.webhookUrl = webhookUrl;
    this.pushAt = pushAt;
    this.subscriptionStocks = subscriptionStocks;
  }

  public static Subscription createForInit(String webhookUrl, Time pushAt) {
    return Subscription.builder().webhookUrl(webhookUrl).pushAt(pushAt).subscriptionStocks(new ArrayList<>()).build();
  }

  public void subscribe(Stock stock) {
    this.subscriptionStocks.add(SubscriptionStock.builder().stock(stock).subscription(this).build());
  }

  public List<Stock> getSubscribingStocks() {
    return subscriptionStocks.stream().map(SubscriptionStock::getStock).toList();
  }
}
