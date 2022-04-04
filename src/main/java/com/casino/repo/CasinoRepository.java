package com.casino.repo;

import com.casino.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasinoRepository extends JpaRepository<Player, Long> {

    Player findPlayerByPlayerIdIgnoreCase(String playerId);
    Player findPlayerByUsernameIgnoreCase(String username);
}
