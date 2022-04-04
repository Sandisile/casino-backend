
package com.casino.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bruce
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString(exclude = "player")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "account")
public class Account {

   @Id
   @GeneratedValue
   @EqualsAndHashCode.Include
   private Long id;
   private Double balance;

   @OneToOne(mappedBy = "account")
   private Player player;
   @OneToMany( cascade = CascadeType.ALL)
   private Set<Transaction> transactions;
}
