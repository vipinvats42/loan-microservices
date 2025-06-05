package com.pranshi.loan.loan.dto;

import java.util.HashMap;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "cards")
@Getter
@Setter
public class LoanContactInfoDto {
    private String message;
    private HashMap<String, String> contactDetails;
    private List<String> onCallSupport;

}