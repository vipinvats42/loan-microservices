package com.pranshi.loan.loan.dto;

import java.util.HashMap;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cards")
public record LoanContactInfoDto(String message, HashMap<String, String> contactDetails, List<String> onCallSupport) {

}