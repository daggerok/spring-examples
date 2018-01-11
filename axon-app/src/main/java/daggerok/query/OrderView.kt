package daggerok.query

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class OrderView(@Id var id: String? = null,
                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var createdAt: LocalDateTime? = null)
