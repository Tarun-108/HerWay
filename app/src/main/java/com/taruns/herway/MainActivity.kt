package com.taruns.herway

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
                        Toast.makeText(this@MainActivity, "First Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.change_pin -> {
                        Toast.makeText(this@MainActivity, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.community -> {
                        Toast.makeText(this@MainActivity, "third Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.feedback -> {
                        Toast.makeText(this@MainActivity, "fourth Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.help -> {
                        Toast.makeText(this@MainActivity, "fifth Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.logout -> {
                        Toast.makeText(this@MainActivity, "sixth Item Clicked", Toast.LENGTH_SHORT).show()
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
