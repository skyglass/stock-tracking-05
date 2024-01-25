package net.greeta.stock.catalogquery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Base class to derive entity classes from.
 *
 * @author Oliver Gierke
 */
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class DbEntity {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
      return false;
    }

    DbEntity that = (DbEntity) obj;

    return this.id.equals(that.getId());
  }

  @Override
  public int hashCode() {
    return id == null ? 0 : id.hashCode();
  }
}
