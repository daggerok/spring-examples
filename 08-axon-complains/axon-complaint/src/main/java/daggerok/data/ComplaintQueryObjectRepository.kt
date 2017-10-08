package daggerok.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ComplaintQueryObjectRepository : JpaRepository<ComplaintQueryObject, UUID> //{
/*
  override fun <S : ComplaintQueryObject?> saveAndFlush(entity: S): S {
    throw RuntimeException("Query read only.")
  }

  override fun deleteAllInBatch() {
    throw RuntimeException("Query read only.")
  }

  override fun delete(entity: ComplaintQueryObject?) {
    throw RuntimeException("Query read only.")
  }

  override fun delete(entities: MutableIterable<ComplaintQueryObject>?) {
    throw RuntimeException("Query read only.")
  }

  override fun delete(id: String?) {
    throw RuntimeException("Query read only.")
  }

  override fun deleteAll() {
    throw RuntimeException("Query read only.")
  }

  override fun deleteInBatch(entities: MutableIterable<ComplaintQueryObject>?) {
    throw RuntimeException("Query read only.")
  }

  override fun <S : ComplaintQueryObject?> save(entities: MutableIterable<S>?): MutableList<S> {
    throw RuntimeException("Query read only.")
  }

  override fun <S : ComplaintQueryObject?> save(entity: S): S {
    throw RuntimeException("Query read only.")
  }

  override fun flush() {
    throw RuntimeException("Query read only.")
  }
*/
//}
