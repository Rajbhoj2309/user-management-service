package com.usermanagemet.domain;



import com.usermanagemet.enums.RoleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles")
public class Role {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  @Enumerated(EnumType.STRING)
	  private RoleEnum name;

}
