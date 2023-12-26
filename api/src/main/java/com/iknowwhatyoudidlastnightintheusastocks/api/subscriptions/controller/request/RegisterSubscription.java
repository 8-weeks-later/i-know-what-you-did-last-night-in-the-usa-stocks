package com.iknowwhatyoudidlastnightintheusastocks.api.subscriptions.controller.request;

import jakarta.validation.constraints.NotBlank;
import java.sql.Time;

public record RegisterSubscription(@NotBlank(message = "webhook url이 필요합니다.") String webhookUrl,
                                   Time pushAt) {

}
