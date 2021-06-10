package org.gotson.komga.infrastructure.jooq

import org.gotson.komga.domain.model.Sidecar
import org.gotson.komga.domain.model.SidecarStored
import org.gotson.komga.domain.persistence.SidecarRepository
import org.gotson.komga.jooq.Tables
import org.gotson.komga.jooq.tables.records.SidecarRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.net.URL

@Component
class SidecarDao(
  private val dsl: DSLContext
) : SidecarRepository {

  private val sc = Tables.SIDECAR

  override fun findAll(): Collection<SidecarStored> =
    dsl.selectFrom(sc).fetch().map { it.toDomain() }

  override fun save(libraryId: String, sidecar: Sidecar) {
    dsl.insertInto(sc)
      .values(
        sidecar.url.toString(),
        sidecar.parentUrl.toString(),
        sidecar.lastModifiedTime,
        libraryId,
      )
      .onDuplicateKeyUpdate()
      .set(sc.LAST_MODIFIED_TIME, sidecar.lastModifiedTime)
      .set(sc.PARENT_URL, sidecar.parentUrl.toString())
      .set(sc.LIBRARY_ID, libraryId)
      .execute()
  }

  override fun deleteByLibraryIdAndUrls(libraryId: String, urls: Collection<URL>) {
    dsl.deleteFrom(sc)
      .where(sc.LIBRARY_ID.eq(libraryId))
      .and(sc.URL.`in`(urls.map { it.toString() }))
      .execute()
  }

  private fun SidecarRecord.toDomain() =
    SidecarStored(
      url = URL(url),
      parentUrl = URL(parentUrl),
      lastModifiedTime = lastModifiedTime,
      libraryId = libraryId,
    )
}
