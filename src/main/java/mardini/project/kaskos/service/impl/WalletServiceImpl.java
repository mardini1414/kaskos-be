package mardini.project.kaskos.service.impl;

import lombok.RequiredArgsConstructor;
import mardini.project.kaskos.dto.CashOutRequest;
import mardini.project.kaskos.dto.HistoryResponse;
import mardini.project.kaskos.dto.TopUpRequest;
import mardini.project.kaskos.dto.WalletResponse;
import mardini.project.kaskos.entity.History;
import mardini.project.kaskos.entity.Wallet;
import mardini.project.kaskos.enums.TransactionType;
import mardini.project.kaskos.repository.HistoryRepository;
import mardini.project.kaskos.repository.WalletRepository;
import mardini.project.kaskos.service.WalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    @Value("${wallet.id}")
    private UUID walletId;
    private final WalletRepository walletRepository;
    private final HistoryRepository historyRepository;

    @Override
    public WalletResponse getBalance() {
        return walletRepository.findProjectedBy();
    }

    @Override
    @Transactional
    public void topUp(TopUpRequest topUpRequest, String email) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        int newBalance = wallet.get().getBalance() + topUpRequest.getNominal();
        wallet.get().setBalance(newBalance);

        History history = new History();
        history.setNominal(topUpRequest.getNominal());
        history.setUserEmail(email);
        history.setTransactionType(TransactionType.TOP_UP);

        walletRepository.save(wallet.get());
        historyRepository.save(history);
    }

    @Override
    @Transactional
    public void cashOut(CashOutRequest cashOutRequest, String email) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.get().getBalance() < cashOutRequest.getNominal()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "nominal can't less than balance");
        }
        int newBalance = wallet.get().getBalance() - cashOutRequest.getNominal();
        wallet.get().setBalance(newBalance);

        History history = new History();
        history.setNominal(cashOutRequest.getNominal());
        history.setUserEmail(email);
        history.setTransactionType(TransactionType.CASH_OUT);

        walletRepository.save(wallet.get());
        historyRepository.save(history);
    }

    public Page<HistoryResponse> findAllHistories(String email, Date createdAt, Pageable pageable) {
        if(createdAt != null){
            return historyRepository.findAllHistoryByCreatedAt(email, createdAt, pageable);
        }else {
            return historyRepository.findAllHistory(email, pageable);
        }
    }
}
