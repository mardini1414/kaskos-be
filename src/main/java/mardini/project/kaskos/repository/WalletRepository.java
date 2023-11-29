package mardini.project.kaskos.repository;

import mardini.project.kaskos.dto.WalletResponse;
import mardini.project.kaskos.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    WalletResponse findProjectedBy();

}
