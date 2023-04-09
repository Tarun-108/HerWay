package com.taruns.herway.authentication.register

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
import com.google.firebase.ktx.Firebase
import com.taruns.herway.authentication.login.loginActivity
import com.taruns.herway.databinding.ActivityCreateAccountBinding
import com.taruns.herway.databinding.FragmentOTPBinding
import com.taruns.herway.models.ContactModel
import com.taruns.herway.models.UserModel
import com.taruns.herway.navigationdrawer.EmergencyActivity
import java.util.concurrent.TimeUnit

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var userModel : UserModel

    private lateinit var otpFragmentBinding: FragmentOTPBinding


    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        otpFragmentBinding = binding.otpFragment


        auth = Firebase.auth
        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true);
//        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true);

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)

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
                Log.d(TAG, "onCodeSent:$verificationId")

                binding.createAccount.visibility = View.GONE
                binding.otpScreen.visibility = View.VISIBLE

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token


            }
        }

        otpFragmentBinding.nextButton.setOnClickListener{
            val code: String = otpFragmentBinding.editTextPasswordCnf.text.toString().trim()
            if(code.length!=6) return@setOnClickListener
            binding.otpScreen.visibility = View.GONE
            verifyPhoneNumberWithCode(storedVerificationId, code)
        }

        otpFragmentBinding.resend.setOnClickListener{
            userModel.phone?.let { it1 -> resendVerificationCode(it1, resendToken) }
        }

        binding.toLogin.setOnClickListener {
            val intent: Intent = Intent(this@CreateAccountActivity, loginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        userModel =  UserModel()
        updateUI(currentUser, userModel)

        binding.sendOtp.setOnClickListener{

            userModel = createUser()
            userModel.phone?.let { it1 -> startPhoneNumberVerification(it1) }
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
                    val intent =  Intent(this@CreateAccountActivity, CreatePinActivity::class.java)
                    intent.putExtra("userModel", userModel)
                    Toast.makeText(this@CreateAccountActivity, "Registeration Success", Toast.LENGTH_SHORT).show()
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

    fun createUser(): UserModel {

        val user = UserModel()
        if(binding.editTextName.text.toString().trim().isEmpty()){
            binding.editTextName.requestFocus()
            binding.editTextName.error = "Enter Name"
        }
        else if(binding.editTextNumber.text.toString().trim().isEmpty()){
            binding.editTextNumber.requestFocus()
            binding.editTextNumber.error = "Enter Name"
        }
        else if(binding.editTextEmail.text.toString().trim().isEmpty()){
            binding.editTextEmail.requestFocus()
            binding.editTextEmail.error = "Enter Name"
        }
        else if(binding.editTextAge.text.toString().trim().isEmpty()){
            binding.editTextAge.requestFocus()
            binding.editTextAge.error = "Enter Name"
        }else{
            user.age = binding.editTextAge.text.toString().toInt()
            user.name = binding.editTextName.text.toString()
            user.email = binding.editTextEmail.text.toString()
            user.phone = "+91"+binding.editTextNumber.text.toString()
            user.pin = "0000"
            user.eContacts = ArrayList<ContactModel>()
        }


        return user

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