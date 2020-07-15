package com.lukma.core.data.common

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.channelFlow

@Suppress("EXPERIMENTAL_API_USAGE")
fun <T : Any> Query.snapshotAsFlow(transform: (DocumentSnapshot) -> T?) =
    channelFlow<List<T>> {
        val task = addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, error ->
            if (error != null) {
                close(error)
            }

            snapshot
                ?.mapNotNull { runCatching { transform(it) }.getOrNull() }
                ?.run(::sendBlocking)
        }
        awaitClose { task.remove() }
    }
