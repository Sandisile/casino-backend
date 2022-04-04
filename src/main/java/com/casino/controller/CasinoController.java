package com.casino.controller;

import com.casino.dto.Request;
import com.casino.exception.InsufficientFundsException;
import com.casino.exception.ResourceNotFoundException;
import com.casino.model.Player;
import com.casino.service.CasinoService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author bruce
 */

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/casino")
public class CasinoController {

    private final CasinoService casinoService;
    
    @GetMapping("/{player-id}")
    public ResponseEntity getPlayerBalance(@PathVariable("player-id") String playerId) {
        return ResponseEntity.ok(casinoService.getPlayerBalance(playerId));
    }

    @PostMapping("/transactions")
    public ResponseEntity getLastTenTransactions(@RequestBody Player player) {
        return ResponseEntity.ok(casinoService.findLastTenTransactions(player));
    }

    @PostMapping("/deposit/{player-id}")
    public ResponseEntity deposit(@PathVariable("player-id") @NonNull String playerId, @RequestBody Request request) throws ResourceNotFoundException {
        return ResponseEntity.ok(casinoService.deposit(playerId, request));
    }

    @PostMapping("/deduct/{player-id}")
    public  ResponseEntity deduct(@PathVariable("player-id") @NonNull String playerId, @RequestBody Request request) throws InsufficientFundsException, ResourceNotFoundException {
        return ResponseEntity.ok(casinoService.deduct(playerId, request));
    }

    
}
