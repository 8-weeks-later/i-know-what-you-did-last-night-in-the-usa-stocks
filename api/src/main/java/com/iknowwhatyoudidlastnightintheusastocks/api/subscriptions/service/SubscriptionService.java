package com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.service;

import com.iknowwhatyoudidlastnightintheusastocks.api.members.domain.MemberRepository;
import com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.controller.response.SubscribingStock;
import com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.domain.Stock;
import com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.domain.StockRepository;
import java.sql.Time;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SubscriptionService {

  private final MemberRepository memberRepository;
  private final StockRepository stockRepository;

  @Transactional
  public void register(String email, String webhookUrl, Time pushAt) {
    var member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("찾을 수 없는 멤버입니다."));
    member.registerSubscription(webhookUrl, pushAt);
  }

  @Transactional
  public void subscribeStock(String email, String ticker) {
    var member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("찾을 수 없는 멤버입니다."));
    var stock = stockRepository.findByTicker(ticker).orElseThrow(() -> new RuntimeException("존재하지 않는 주식입니다."));

    member.subscribeStock(stock);
  }

  public List<SubscribingStock> getSubscribingStocks(String email) {
    var member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("찾을 수 없는 멤버입니다."));
    return member.getSubscribingStocks().stream().map(Stock::toSubscribingStock).toList();
  }
}
