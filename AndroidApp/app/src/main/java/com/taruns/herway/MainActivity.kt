package com.taruns.herway

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.taruns.herway.bottomNav.ContactFragment
import com.taruns.herway.bottomNav.NavigationFragment
import com.taruns.herway.bottomNav.SOSFragment
import com.taruns.herway.databinding.ActivityMainBinding
import com.taruns.herway.navigationdrawer.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                        startActivity(intent)
                    }
                    R.id.change_pin -> {
                        val intent: Intent = Intent(this@MainActivity, ChangePinActivity::class.java)
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
//                        val intent: Intent = Intent(this@MainActivity, EmergencyActivity::class.java)
//                        startActivity(intent)
                    }
                }
                true
            }
        }


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