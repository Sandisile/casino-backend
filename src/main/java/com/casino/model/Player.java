/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casino.model;

import lombok.*;

import javax.persistence.*;

/**
 *
 * @author bruce
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true)
    private String playerId;
    private String username;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
    
}
