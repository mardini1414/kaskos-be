package mardini.project.kaskos.service;

import mardini.project.kaskos.dto.CashOutRequest;
import mardini.project.kaskos.dto.HistoryResponse;
import mardini.project.kaskos.dto.TopUpRequest;
import mardini.project.kaskos.dto.WalletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

public interface WalletService {

    public WalletResponse getBalance();

    public void topUp(TopUpRequest topUpRequest, String email);

    public void cashOut(CashOutRequest cashOutRequest, String email);

    public Page<HistoryResponse> findAllHistories(String email, Date createdAt, Pageable pageable);

}
