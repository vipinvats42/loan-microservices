package com.pranshi.loan.loan.controller;

import org.springframework.beans.factory.annotation.Value;
import com.pranshi.loan.loan.constants.LoansConstants;
import com.pranshi.loan.loan.dto.ErrorResponseDto;
import com.pranshi.loan.loan.dto.LoanContactInfoDto;
import com.pranshi.loan.loan.service.ILoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.pranshi.loan.loan.dto.ErrorResponseDto;
import com.pranshi.loan.loan.dto.LoanDto;
import com.pranshi.loan.loan.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.beans.factory.annotation.Autowired;

@Tag(name = "CRUD REST APIs for Loans in EazyBank", description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details")

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
// @AllArgsConstructor
@Validated
@EnableConfigurationProperties(value = LoanContactInfoDto.class)
public class LoansController {

        private ILoanService iLoansService;

        public LoansController(ILoanService iLoansService) {
                this.iLoansService = iLoansService;
        }

        @Value("${build.version}")
        private String buildVersion;

        @Autowired
        private LoanContactInfoDto loanContactInfoDto;

        @Autowired
        private Environment environment;

        @PostMapping("/create")
        public ResponseEntity<ResponseDto> createLoan(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                iLoansService.createLoan(mobileNumber);
                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
        }

        @Operation(summary = "Fetch Loan Details REST API", description = "REST API to fetch loan details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/fetch")
        public ResponseEntity<LoanDto> fetchLoanDetails(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                LoanDto loansDto = iLoansService.fetchLoan(mobileNumber);
                return ResponseEntity.status(HttpStatus.OK).body(loansDto);
        }

        @Operation(summary = "Update Loan Details REST API", description = "REST API to update loan details based on a loan number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PutMapping("/update")
        public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto loansDto) {
                boolean isUpdated = iLoansService.updateLoan(loansDto);
                if (isUpdated) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(new ResponseDto(LoansConstants.STATUS_417,
                                                        LoansConstants.MESSAGE_417_UPDATE));
                }
        }

        @Operation(summary = "Delete Loan Details REST API", description = "REST API to delete Loan details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @DeleteMapping("/delete")
        public ResponseEntity<ResponseDto> deleteLoanDetails(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                boolean isDeleted = iLoansService.deleteLoan(mobileNumber);
                if (isDeleted) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(new ResponseDto(LoansConstants.STATUS_417,
                                                        LoansConstants.MESSAGE_417_DELETE));
                }
        }

        @Operation(summary = "Get Build information", description = "Get build information that is deployed into account microservice")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })

        @GetMapping("/build-info")
        ResponseEntity<ResponseDto> getBuildVersion() {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(new ResponseDto(LoansConstants.STATUS_200, buildVersion));
        }

        @Operation(summary = "Get Java version", description = "Get Java version installed into acccount microservice")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })

        @GetMapping("/java-version")
        ResponseEntity<String> getJavaVersion() {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(environment.getProperty("JAVA_HOME"));
        }

        @Operation(summary = "Get Contact Info", description = "Contact info details that can be reached out in case of any issue")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })

        @GetMapping("/contact-info")
        ResponseEntity<LoanContactInfoDto> getContactInfo() {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(loanContactInfoDto);
        }

}
