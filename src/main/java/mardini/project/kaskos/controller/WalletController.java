package mardini.project.kaskos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mardini.project.kaskos.dto.*;
import mardini.project.kaskos.service.WalletService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping(path = "wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<ApiResponse> getBalance() {
        WalletResponse wallet = walletService.getBalance();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder().data(wallet).build());
    }

    @PostMapping(path = "top-up")
    public ResponseEntity<MessageResponse> topUp(@RequestBody @Valid TopUpRequest topUpRequest, Authentication authentication) {
        walletService.topUp(topUpRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder().message("top up success").build());
    }

    @PostMapping(path = "cash-out")
    public ResponseEntity<MessageResponse> cashOut(@RequestBody @Valid CashOutRequest cashOutRequest, Authentication authentication) {
        walletService.cashOut(cashOutRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder().message("cash out success").build());
    }

    @GetMapping(path = "histories")
    public ResponseEntity<ApiResponse> getHistories(@RequestParam(value = "email", required = false) String email,
                                                    @RequestParam(value = "created_at", required = false) Date createdAt,
                                                    Pageable pageable
    ) {
        Page<HistoryResponse> histories = walletService.findAllHistories(email, createdAt, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder().data(histories).build());
    }
}
