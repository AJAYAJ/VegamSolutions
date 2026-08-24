package `in`.vegamdigital.app.data.remote

import retrofit2.http.GET

/** Retrofit contract ready to point at the production backend later. */
interface VegamApi {
    @GET("student/dashboard") suspend fun dashboard(): Map<String, Any>
}

/** Firebase-shaped ports. Swap these bindings for Firebase SDK implementations later. */
interface FirebaseAuthGateway { suspend fun signIn(studentCode: String, password: String): Boolean; suspend fun signOut() }
interface FirestoreGateway { suspend fun add(collection: String, values: Map<String, Any?>) }
interface FirebaseStorageGateway { suspend fun upload(path: String, bytes: ByteArray): String }
interface FcmGateway { suspend fun registerToken(token: String) }

class DummyFirebaseAuthGateway : FirebaseAuthGateway {
    override suspend fun signIn(studentCode: String, password: String) = studentCode.isNotBlank() && password.length >= 4
    override suspend fun signOut() = Unit
}

class DummyFirestoreGateway : FirestoreGateway { override suspend fun add(collection: String, values: Map<String, Any?>) = Unit }
class DummyFirebaseStorageGateway : FirebaseStorageGateway { override suspend fun upload(path: String, bytes: ByteArray) = "dummy://storage/$path" }
class DummyFcmGateway : FcmGateway { override suspend fun registerToken(token: String) = Unit }
