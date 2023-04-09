package com.taruns.herway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.taruns.herway.bottomNav.ContactFragment
import com.taruns.herway.bottomNav.NavigationFragment
import com.taruns.herway.bottomNav.SOSFragment
import com.taruns.herway.databinding.ActivityMainBinding
import com.taruns.herway.databinding.NavDrawerHeaderBinding
import com.taruns.herway.models.UserModel
import com.taruns.herway.navigationdrawer.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var binding_header: NavDrawerHeaderBinding
    lateinit var toggle: ActionBarDrawerToggle
    val phone_user="+917441147546"
     var userDataModel:UserModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding_header = NavDrawerHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    userDataModel= intent.getSerializableExtra("userModel") as UserModel?
       // Log.i("tagfff",userDataModel.toString())
        //getUserdata()

        val bundle = Bundle()
        bundle.putSerializable("userDataModel", userDataModel)
// set Fragmentclass Arguments
// set Fragmentclass Arguments
        val fragobj = SOSFragment()
        fragobj.setArguments(bundle)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.call->setCurrentFragment(ContactFragment())
                R.id.navigation->setCurrentFragment(NavigationFragment())
                R.id.sos->setCurrentFragment(SOSFragment())

            }
            true
        }



        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toggle.drawerArrowDrawable.color = resources.getColor(R.color.color_primary)
            toggle.syncState()

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {

                    R.id.emergency_contacts -> {
                        val intent: Intent = Intent(this@MainActivity, EmergencyActivity::class.java)
                        intent.putExtra("userModel", userDataModel)
                        startActivity(intent)
                    }
                    R.id.change_pin -> {
                        val intent: Intent = Intent(this@MainActivity, ChangePinActivity::class.java)
                        intent.putExtra("userModel", userDataModel)
                        startActivity(intent)
                    }
                    R.id.community -> {
                        val intent: Intent = Intent(this@MainActivity, CommunityActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.feedback -> {
                        val intent: Intent = Intent(this@MainActivity, FeedbackActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.help -> {
                        val intent: Intent = Intent(this@MainActivity, HelpActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.logout -> {
                       val intent: Intent = Intent(this@MainActivity, ProfileActivity::class.java)
                        intent.putExtra("userModel", userDataModel)
                        startActivity(intent)
                    }
                }
                true
            }
        }


    }

    private fun getUserdata() {
        var reference =
            FirebaseDatabase.getInstance().getReference("Users").child(phone_user)
        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("checkk",snapshot.toString())
                userDataModel=snapshot.getValue(UserModel::class.java)!!

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("check","cancelled")
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragment.id,fragment)
            commit()
        }

}
