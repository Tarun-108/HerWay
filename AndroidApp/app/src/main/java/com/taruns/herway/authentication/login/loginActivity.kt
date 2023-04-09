package com.taruns.herway.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.authentication.register.CreateAccountActivity
import com.taruns.herway.authentication.register.CreatePinActivity
import com.taruns.herway.databinding.ActivityCommunityBinding
import com.taruns.herway.databinding.ActivityCreateAccountBinding
import com.taruns.herway.databinding.ActivityLoginBinding
import com.taruns.herway.databinding.FragmentOTPBinding
import com.taruns.herway.models.ContactModel
import com.taruns.herway.models.UserModel
import com.taruns.herway.models.post_list_model
import java.util.concurrent.TimeUnit

class loginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var userModel : UserModel

    private lateinit var otpFragmentBinding: FragmentOTPBinding


    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        otpFragmentBinding = binding.otpFragment

        binding.toRegister.setOnClickListener {
            var intent= Intent(this,CreateAccountActivity::class.java)
            startActivity(intent)
        }
        go_for_login_otp()

    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        userModel =  UserModel()
        updateUI(currentUser, userModel)

        binding.loginGetOtpBtn.setOnClickListener{
            binding.progressbar.visibility=View.VISIBLE
            check_phone_in_database()

        }
    }
    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun go_for_login_otp() {



        auth = Firebase.auth
        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true);
//        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true);

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("loginActivity.TAG", "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //Log.w(CreateAccountActivity.TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.i("CreateAccountActivity", "onCodeSent:$verificationId")

                binding.loginAccount.visibility = View.GONE

                binding.otpScreen.visibility = View.VISIBLE

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token


            }
        }

        otpFragmentBinding.nextButton.setOnClickListener{
            otpFragmentBinding.progressbar1.visibility=View.VISIBLE
            val code: String = otpFragmentBinding.editTextPasswordCnf.text.toString().trim()
            if(code.length!=6) return@setOnClickListener
            binding.otpScreen.visibility = View.GONE
            verifyPhoneNumberWithCode(storedVerificationId, code)
        }

        otpFragmentBinding.resend.setOnClickListener{
            otpFragmentBinding.progressbar1.visibility=View.GONE
            userModel.phone?.let { it1 -> resendVerificationCode(it1, resendToken) }
        }
    }
//7441147546
    private fun check_phone_in_database(): Unit {
    var phone="+91"+binding.editTextPhoneNumber.text.toString().trim()
        val reference = FirebaseDatabase.getInstance().getReference("Users").child(phone)

        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    //create new user
                    startPhoneNumberVerification(snapshot.getValue(UserModel::class.java)?.phone!!)
                    userModel= snapshot.getValue(UserModel::class.java)!!
                    Log.i("tags",snapshot.key.toString()+phone)

                }
                else{
                    Toast.makeText(getApplicationContext(), "Check Your Phone Number", Toast.LENGTH_LONG).show();
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("check","cancelled")
            }

        })
    }
    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
        // [END verify_with_code]
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // (optional) Activity for callback binding
            // If no activity is passed, reCAPTCHA verification can not be used.
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    val intent =  Intent(this@loginActivity, MainActivity::class.java)
                    intent.putExtra("userModel", userModel)
                    Toast.makeText(this@loginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                    otpFragmentBinding.editTextPasswordCnf.error = "Invalid OTP"
                }
            }
    }

    private fun updateUI(firebaseUser: FirebaseUser? = auth.currentUser, userModel: UserModel) {
        if(firebaseUser == null) return
        else{

        }
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }


}