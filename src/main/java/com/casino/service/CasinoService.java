/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casino.service;

import com.casino.dto.Request;
import com.casino.exception.InsufficientFundsException;
import com.casino.exception.ResourceNotFoundException;
import com.casino.model.Account;
import com.casino.model.Player;
import com.casino.model.Transaction;
import com.casino.model.TransactionType;
import com.casino.repo.AccountRepository;
import com.casino.repo.CasinoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author bruce
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CasinoService {

    private final CasinoRepository casinoRepository;
    private final AccountRepository accountRepository;
    @Value("${top-secret.password}")
    private String topSecretPassword;

    public Double getPlayerBalance(String playerId) {
        Player player = casinoRepository.findPlayerByPlayerIdIgnoreCase(playerId);

        return player.getAccount().getBalance();
    }
    
    public Object deposit(@NonNull String playerId, @NonNull Request request) throws ResourceNotFoundException {
        Player player = casinoRepository.findPlayerByPlayerIdIgnoreCase(playerId);
        if(Objects.nonNull(player)) {
            Transaction transaction = new Transaction();
            transaction.setTransactionId(request.getTransactionId());
            transaction.setTransactionAmount(request.getAmount());
            transaction.setTransactionType(TransactionType.DEPOSIT.toString());
            Set<Transaction> transactions = player.getAccount().getTransactions();
            if(Objects.isNull(transactions)) {
                transactions = new HashSet<>();
            }

            if(transactions.isEmpty() || !transactions.contains(transaction)) {
                Account account = player.getAccount();
//                transaction.setAccount(account);
                transactions.add(transaction);
                account.setTransactions(transactions);
                Double balance = account.getBalance();
                balance += (Objects.nonNull(request.getAmount())) ? request.getAmount() : 0.0;
                account.setBalance(balance);
                player.setAccount(account);
                return casinoRepository.save(player).getAccount().getBalance();
            }
            return player.getAccount().getBalance();
        }
        throw new ResourceNotFoundException("Player not found ");
    }
    
    public Object deduct(@NonNull String playerId, @NonNull Request request) throws InsufficientFundsException, ResourceNotFoundException {
        Player player = casinoRepository.findPlayerByPlayerIdIgnoreCase(playerId);
        if(Objects.nonNull(player)) {
            Double balance = player.getAccount().getBalance();
            if (Objects.nonNull(balance)) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(request.getTransactionId());
                transaction.setTransactionAmount(request.getAmount());
                transaction.setTransactionType(TransactionType.WAGER.toString());
                Set<Transaction> transactions = player.getAccount().getTransactions();
                if(Objects.isNull(transactions)) {
                    transactions = new HashSet<>();
                }

                if(transactions.isEmpty() || !transactions.contains(transaction)) {
                    Account account = player.getAccount();
//                    transaction.setAccount(account);
                    transactions.add(transaction);
                    balance -= (Objects.nonNull(request.getAmount())) ? request.getAmount() : 0.0;
                    if(balance < 0) {
                        throw new InsufficientFundsException("Insufficient funds");
                    }
                    account.setBalance(balance);
                    player.setAccount(account);
                    return casinoRepository.save(player).getAccount().getBalance();
                }
                return player.getAccount().getBalance();
            }
            throw new InsufficientFundsException("Insufficient funds");
        }
        throw new ResourceNotFoundException("Player not found ");
    }

    public List<Transaction> findLastTenTransactions(@NonNull Player player) {
        if(topSecretPassword.equals(player.getPassword())) {
            Account account = casinoRepository.findPlayerByUsernameIgnoreCase(player.getUsername()).getAccount();
            Set<Transaction> transactions = account.getTransactions();
            Comparator<Transaction> transactionComparator = Comparator.comparing(Transaction::getCreated, (t1 , t2) -> {
                return t2.compareTo(t1);
            });
            if(Objects.nonNull(transactions) && !transactions.isEmpty()) {
                List<Transaction> transactionList = new ArrayList<>(transactions);
                Collections.sort(transactionList, transactionComparator);
                return transactionList.subList(0, (transactionList.size()> 10)? 10: transactionList.size());
            }
        }
        return Collections.emptyList();
    }
    
    
}
