package com.kakaopay.kakaopaypretest.view

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.kakaopay.kakaopaypretest.R
import com.kakaopay.kakaopaypretest.constant.*
import com.kakaopay.kakaopaypretest.custom.EndlessRecyclerViewScrollListener
import com.kakaopay.kakaopaypretest.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    lateinit var mainBinding: ActivityMainBinding
    var toast: Toast? = null

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
        mainBinding.lifecycleOwner = this
    }


    private fun initView() {
        setSupportActionBar(mainToolbar)
        supportActionBar!!.title = resources.getString(R.string.name)
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        val widthSize = size.x

        val adapter = MainRecyclerViewAdapter(widthSize)
        adapter.setHasStableIds(true)
        mainRecyclerView.adapter = adapter

        mainRecyclerView.addItemDecoration(ItemSpaceDecoration())
        val manager = GridLayoutManager(baseContext, MAIN_GRID_COLUMN)

        mainRecyclerView.layoutManager = manager
        mainRecyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener() {
            override fun loadMore() {
                if (mainViewModel.state.value != LoadingState.LOADING) {
                    mainViewModel.addSearchImage(KakaoImageSearchSortEnum.ACCURACY, DEFAULT_SEARCH_IMAGE_SIZE)
                }
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menu add
        menuInflater.inflate(R.menu.menu_main_search, menu)

        //searchView
        val searchView = menu!!.findItem(R.id.menu_main_search).actionView as SearchView

        searchView.queryHint = resources.getString(R.string.query_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty() && mainViewModel.state.value != LoadingState.LOADING ) {
                    mainViewModel.searchImage(
                        query,
                        KakaoImageSearchSortEnum.ACCURACY,
                        DEFAULT_SEARCH_IMAGE_PAGE,
                        DEFAULT_SEARCH_IMAGE_SIZE
                    )
                } else if (query.isEmpty()) {
                    showToast(getString(R.string.toast_please_input))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_main_init) {
            mainViewModel.clearItem()
            showToast(getString(R.string.toast_search_init))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(msg: String) {
        if (toast == null) {
            toast = Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(msg)
        }
        toast?.show()
    }

}
