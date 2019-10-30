package tigerhacks.android.tigerhacksapp.tigerpass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.android.synthetic.main.activity_tiger_login.loginButton
import kotlinx.android.synthetic.main.activity_tiger_login.loginContainer
import kotlinx.android.synthetic.main.activity_tiger_login.loginGithubButton
import kotlinx.android.synthetic.main.activity_tiger_login.loginGoogleButton
import kotlinx.android.synthetic.main.activity_tiger_login.passwordEditText
import kotlinx.android.synthetic.main.activity_tiger_login.toolbar
import kotlinx.android.synthetic.main.activity_tiger_login.usernameEditText
import tigerhacks.android.tigerhacksapp.R
import tigerhacks.android.tigerhacksapp.service.extensions.dpToPx



class LoginActivity : AppCompatActivity() {
    companion object {
        fun newInstance(context: Context) = Intent(context, LoginActivity::class.java)

        const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val provider = OAuthProvider.newBuilder("github.com")
        .addCustomParameter("login", "your-email@gmail.com")
        .setScopes(listOf("user:email"))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiger_login)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Sign in"

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            swapToPass()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_host))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val twentyFourDp = dpToPx(30)
        val img = resources.getDrawable(R.mipmap.ic_launcher_round, theme)
        img.setBounds(0, 0, twentyFourDp, twentyFourDp)
        loginButton.setCompoundDrawables(img, null, null, null)

        loginButton.setOnClickListener {
            if (usernameEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                login(usernameEditText.text.toString(), passwordEditText.text.toString())
            } else {
                loginFailure("Invalid Username or Password")
            }
        }

        loginGoogleButton.setOnClickListener {
            loginWithGoogle()
        }

        loginGithubButton.setOnClickListener {
            loginWithGithub()
        }
    }

    private var isActive = true

    public override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            swapToPass()
        } else isActive = true
    }

    override fun onStop() {
        super.onStop()
        isActive = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java) ?: return
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                loginFailure()
            }
        }
    }

    private fun login(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    swapToPass()
                } else {
                    val message = it.exception?.message
                    if (message == null) loginFailure() else loginFailure(message)
                }
            }
    }

    private fun loginWithGithub() {
        auth.startActivityForSignInWithProvider(this, provider)
            .addOnSuccessListener { swapToPass() }
            .addOnFailureListener {
                loginFailure()
            }
    }

    private fun loginWithGoogle() {
        val logInIntent = googleSignInClient.signInIntent
        startActivityForResult(logInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { //Success
                    swapToPass()
                } else {
                    loginFailure()
                }
            }
    }

    private fun swapToPass() {
        startActivity(TigerPassActivity.newInstance(this))
        finish()
    }

    private fun loginFailure(message: String = "Authentication Failed.") {
        if (!isActive) return
        Snackbar.make(loginContainer, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
