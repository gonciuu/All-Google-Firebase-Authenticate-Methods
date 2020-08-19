# All firebase google auth login methods in one app

A new Kotlin application.
#### Login Methods App using Firebase Authenticate 

## Getting Started


A few resources to get you started if this is your first kotlin project:
- [getting started with kotlin](https://kotlinlang.org/docs/tutorials/getting-started.html)
- [kotlin codelabs](https://codelabs.developers.google.com/android-kotlin-fundamentals/)


## **Anonymous Login**

**documentation** - [Anonymous login documentation](https://firebase.google.com/docs/auth/android/anonymous-auth?hl=en)

#### Example login code from documentation


```kotlin
auth.signInAnonymously()
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
            } else {
                Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
```

## **Login with email and password**

**documentation** - [Email and password login documentation](https://firebase.google.com/docs/auth/android/password-auth?hl=en)

#### Example register code from documentation


```kotlin
auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
            } else {
                Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                updateUI(null)
            }

        }
```

#### Login method from documentation
```kotlin
auth.signInWithEmailAndPassword()
```


## **Login with phone number**

**documentation** - [Phone number login documentation](https://firebase.google.com/docs/auth/android/phone-auth?hl=en)

#### Create credential method from documentation
```kotlin

var storedVerificationId: String? = null
var resendToken : PhoneAuthProvider? = null

callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        signInWithPhoneAuthCredential(credential)
    }

    override fun onVerificationFailed(e: FirebaseException) { }

    override fun onCodeSent(verificationId: String,token: PhoneAuthProvider.ForceResendingToken  ) {
        storedVerificationId = verificationId
        resendToken = token
    }
}


PhoneAuthProvider.getInstance().verifyPhoneNumber(
        phoneNumber, // Phone number to verify
        60, // Timeout duration
        TimeUnit.SECONDS, // Unit of timeout
        this, // Activity (for callback binding)
        callbacks) // OnVerificationStateChangedCallbacks

val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

```


#### Example register code from documentation


```kotlin
private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
    auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
}

```

## **Login with google account**

**documentation** - [Google login documentation](https://firebase.google.com/docs/auth/android/google-signin?hl=en)


## **Login with Facebook**

**documentation** - [Facebook login documentation](https://firebase.google.com/docs/auth/android/facebook-login?hl=en)
**register your app in Facebook** - [Facebook for developers](https://developers.facebook.com/docs/facebook-login/android)

## **Login with Twitter**

**documentation** - [Twitter login documentation](https://firebase.google.com/docs/auth/android/twitter-login?hl=en)
**register your app in Twitter** - [Twitter for developers](https://developer.twitter.com/en)

## **Login with Github**

**documentation** - [Github login documentation](https://firebase.google.com/docs/auth/android/github-auth?hl=en)
**register your app in Github** - [Github for developers](https://github.com/settings/applications/new)

## **Login with Yahoo**

**documentation** - [Yahoo login documentation](https://firebase.google.com/docs/auth/android/yahoo-oauth?hl=en)
**register your app in Yahoo** - [Yahoo for developers](https://developer.yahoo.com/oauth2/guide/openid_connect/getting_started.html)

## **Login with Microsoft**

**documentation** - [Microsoft login documentation](https://firebase.google.com/docs/auth/android/microsoft-oauth?hl=en)
**register your app in Microsoft** - [Microsoft for developers](https://docs.microsoft.com/en-us/azure/active-directory/develop/quickstart-register-app)

## **Login with Apple**

**documentation** - [Apple login documentation](https://firebase.google.com/docs/auth/android/apple?hl=en)
**register your app in Apple** - [Apple for developers](https://developer.apple.com/)


## Screenshots :

<img src="screenshots/loading.png" width="40%">
<img src="screenshots/values_1.png" width="40%">
<img src="screenshots/values_2.png" width="40%">
<img src="screenshots/calculate_1.png" width="40%">
<img src="screenshots/calculate_2.png" width="40%">
<img src="screenshots/calculate_3.png" width="40%">
<img src="screenshots/calculate_4.png" width="40%">

## Thanks for reading. Don't forget to star my project 