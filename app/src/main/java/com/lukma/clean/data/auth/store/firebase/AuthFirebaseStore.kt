package com.lukma.clean.data.auth.store.firebase

import com.google.firebase.auth.*
import com.lukma.clean.domain.auth.Auth
import com.lukma.clean.domain.auth.AuthRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class AuthFirebaseStore(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    override fun signInWithEmail(email: String, password: String) = Flowable
            .create<AuthResult>({ emitter ->
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { emitter.onNext(it) }
                        .addOnFailureListener { emitter.onError(it) }
            }, BackpressureStrategy.LATEST)

    override fun signInWithFacebook(token: String) = Flowable
            .create<AuthResult>({ emitter ->
                firebaseAuth.signInWithCredential(FacebookAuthProvider.getCredential(token))
                        .addOnSuccessListener { emitter.onNext(it) }
                        .addOnFailureListener { emitter.onError(it) }
            }, BackpressureStrategy.LATEST)

    override fun signInWithGoogle(token: String) = Flowable
            .create<AuthResult>({ emitter ->
                firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(token, null))
                        .addOnSuccessListener { emitter.onNext(it) }
                        .addOnFailureListener { emitter.onError(it) }
            }, BackpressureStrategy.LATEST)

    override fun createUserWithEmailAndPassword(
            email: String,
            password: String
    ) = Flowable.create<AuthResult>({ emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { emitter.onNext(it) }
                .addOnFailureListener { emitter.onError(it) }
    }, BackpressureStrategy.LATEST)

    override fun updateProfile(fullName: String): Flowable<Void> {
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build()

        return Flowable.create<Void>({ emitter ->
            firebaseAuth.currentUser?.updateProfile(profileUpdates)
                    ?.addOnSuccessListener { emitter.onNext(it) }
                    ?.addOnFailureListener { emitter.onError(it) }
        }, BackpressureStrategy.LATEST)
    }

    override fun authorize(faId: String, fcmId: String) = throw IllegalAccessException()

    override fun register(
            faId: String,
            fcmId: String,
            facebookToken: String,
            googleToken: String
    ) = throw IllegalAccessException()

    override fun refreshToken(token: String) = throw IllegalAccessException()

    override fun isAuthenticated() = throw IllegalAccessException()

    override fun gets() = throw IllegalAccessException()

    override fun getIsActive() = throw IllegalAccessException()

    override fun insert(data: Auth) = throw IllegalAccessException()

    override fun update(data: Auth) = throw IllegalAccessException()

    override fun delete(username: String) = throw IllegalAccessException()
}