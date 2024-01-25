package net.greeta.stock.catalogquery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Entity
@Table(name = "brand")
public class Brand extends DbEntity {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
