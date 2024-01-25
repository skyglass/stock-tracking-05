package net.greeta.stock.catalogquery.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CatalogItemRepository
    extends JpaRepository<CatalogItem, UUID>, JpaSpecificationExecutor<CatalogItem>, PagingAndSortingRepository<CatalogItem, UUID> {
  Page<CatalogItem> findAllByName(String name, Pageable pageable);
}
