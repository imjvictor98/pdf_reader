package com.raywenderlich.android.majesticreader.framework

import android.content.Context
import com.raywenderlich.android.majesticreader.data.BookmarkDataSource
import com.raywenderlich.android.majesticreader.domain.Bookmark
import com.raywenderlich.android.majesticreader.domain.Document
import com.raywenderlich.android.majesticreader.framework.db.BookmarkEntity
import com.raywenderlich.android.majesticreader.framework.db.MajesticReaderDatabase

class RoomBookmarkDataSource(context: Context) : BookmarkDataSource{
    private val bookMarkDao = MajesticReaderDatabase.getInstance(context).bookmarkDao()

    override suspend fun add(document: Document, bookmark: Bookmark) {
        bookMarkDao.addBookmark(BookmarkEntity(
            documentUri = document.url,
            page = bookmark.page
        ))
    }

    override suspend fun read(document: Document): List<Bookmark> =
        bookMarkDao.getBookmarks(document.url).map {
            Bookmark(it.id, it.page)
        }

    override suspend fun remove(document: Document, bookmark: Bookmark) =
        bookMarkDao.removeBookmark(
            BookmarkEntity(
                id = bookmark.id,
                documentUri = document.url,
                page = bookmark.page)
        )
}