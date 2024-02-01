package com.brandon.imagesearch_app.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.brandon.imagesearch_app.databinding.MainActivityBinding
import com.brandon.imagesearch_app.ui.search.SearchFragment
import com.brandon.imagesearch_app.ui.viewmodel.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private val viewPagerAdapter: MainViewPagerAdapter by lazy { MainViewPagerAdapter(this) }

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        initView()
        initListener()
        initSharedViewModel()
    }

    private fun initSharedViewModel() = with(sharedViewModel){
        sharedData.observe(this@MainActivity){

        }
    }

    private fun initListener() = with(binding){
        fab.setOnClickListener {
            viewPagerAdapter.getSearchFragment()?.upToTop()
        }
    }

    private fun initView() = with(binding) {
        vpMain.adapter = viewPagerAdapter
        vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (viewPagerAdapter.getFragment(position) is SearchFragment) {
                    fab.show()
                } else {
                    fab.hide()
                }
            }
        })
        TabLayoutMediator(tlMain, vpMain) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()
    }


}
