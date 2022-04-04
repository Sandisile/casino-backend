package com.casino;

import com.casino.model.Account;
import com.casino.model.Player;
import com.casino.repo.CasinoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class CasinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasinoApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(CasinoRepository repository) {
		return (args) -> {
			// save a couple of players
			Account account = new Account();
			account.setBalance(0D);
			Player player = new Player();
			player.setPlayerId("1234");
			player.setAccount(account);
			player.setPassword("pass");
			player.setUsername("test");
			player.setId(1L);
			repository.save(player);
		};
	}

}
