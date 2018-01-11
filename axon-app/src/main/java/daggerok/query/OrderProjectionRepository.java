package daggerok.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "order-query", path = "order-query")
public interface OrderProjectionRepository extends JpaRepository<OrderView, String> {}
