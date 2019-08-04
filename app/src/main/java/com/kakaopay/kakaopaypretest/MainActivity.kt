package com.kakaopay.kakaopaypretest

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.kakaopay.kakaopaypretest.constant.DEFAULT_SEARCH_IMAGE_PAGE
import com.kakaopay.kakaopaypretest.constant.DEFAULT_SEARCH_IMAGE_SIZE
import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import com.kakaopay.kakaopaypretest.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var mainBinding: ActivityMainBinding
    val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindInit()
        initView()

    }

    private fun bindInit() {
        mainBinding.vm = mainViewModel
    }


    private fun initView() {
        setSupportActionBar(mainToolbar)
        supportActionBar!!.title = resources.getString(R.string.name)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //menu add
        menuInflater.inflate(R.menu.menu_main_search, menu)

        //searchView
        val searchView = menu!!.findItem(R.id.menu_main_search).actionView as SearchView
        searchView.queryHint = resources.getString(R.string.query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                query.let {
                    if (it!!.isNotBlank())
                        mainViewModel.searchImage(
                                it, KakaoImageSearchSortEnum.ACCURACY, DEFAULT_SEARCH_IMAGE_PAGE,
                                DEFAULT_SEARCH_IMAGE_SIZE
                        )
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
