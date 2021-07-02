package com.example.mydream

import com.google.firebase.firestore.DocumentReference
import kotlin.coroutines.suspendCoroutine

suspend fun DocumentReference.setAndWait(data: Any) = suspendCoroutine<String?> { cont ->
    set(data)
            .addOnSuccessListener {
                cont.resumeWith(Result.success(id))
            }.addOnFailureListener {
                cont.resumeWith(Result.success(null))
            }
}
