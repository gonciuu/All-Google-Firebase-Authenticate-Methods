package com.example.authloginmethods.screens.screens


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.android.synthetic.main.login_methods_fragment.*


class LoginMethodsFragment : Fragment() {
    private lateinit var authenticate: Authenticate

    companion object {
        fun newInstance() = LoginMethodsFragment()
        const val RC_SIGN_IN = 123
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_methods_fragment, container, false)
    }


    //------click on login methods buttons------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authenticate = Authenticate(this)
        authenticate.getCurrentUser()//check if user is already exist 

        //--------anomyus login---------
        anomyusLogin.setOnClickListener {
            makeLoadingText()
            authenticate.signInAnomyus()
        }


        //-------------email and password login--------------
        loginAndPasswordLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginMethodsFragment_to_loginFragment)
        }
        //phone number login
        phoneLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginMethodsFragment_to_phoneNumberFragment)
        }


        googleLogin.setOnClickListener {
            makeLoadingText()
            loginWithGoogleAccount()
        }
        facebookLogin.setOnClickListener {
            val intent = Intent(context, FacebookLoginActivity::class.java)
            startActivity(intent)

        }
        githubLogin.setOnClickListener {
            val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")
            authenticate.loginWithGithub(provider)
        }


        //login with yahoo
        yahooLogin.setOnClickListener {
            val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("yahoo.com")
            authenticate.loginWithYahoo(provider)
        }

        //login with twitter
        twitterLogin.setOnClickListener {
            val provider = OAuthProvider.newBuilder("twitter.com")
            authenticate.loginWithTwitter(provider)

        }

        //login with microsoft
        microsoftLogin.setOnClickListener {
            val provider = OAuthProvider.newBuilder("microsoft.com")
            authenticate.loginWithMicrosoft(provider)
        }
        //login_with_apple
        appleLogin.setOnClickListener {
            val provider = OAuthProvider.newBuilder("apple.com")
            authenticate.loginWithApple(provider)
        }
    }


        private fun makeLoadingText() =
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()


        // ------------------------- configure google sign in options--------------------------------
        private fun loginWithGoogleAccount() {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(requireActivity().getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(
                signInIntent,
                RC_SIGN_IN
            )        //start google accounts login intent
        }
        //============================================================================================


        //---------------------on google choose account activity resoult login with google account------------------------
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    authenticate.firebaseAuthWithGoogle(account.idToken!!)                      //login with account id token
                } catch (e: ApiException) {
                    Toast.makeText(context, "Api token error $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //=================================================================================================================
    }