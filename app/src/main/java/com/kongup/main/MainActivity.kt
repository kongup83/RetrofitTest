package com.kongup.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.kongup.common.MyApplication
import com.kongup.common.server.RetrofitAsyncTask
import com.kongup.common.server.WeatherAPIs
import com.kongup.common.server.data.GetLocation
import com.kongup.common.server.data.GetLocationSearch
import com.kongup.main.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Response

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, RetrofitAsyncTask.RetrofitCallback
{
    val TAG = MainActivity::class.java.name

    private val mSearch = "se"

    private lateinit var mRecyclerAdapter: RecyclerAdapter
    private val mWeatherAPIs by lazy {
        ( application as MyApplication).mWeatherService
    }

    private val mArrayList = ArrayList<GetLocation>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mRecyclerAdapter = RecyclerAdapter(this@MainActivity, mArrayList)
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.adapter = mRecyclerAdapter

        refresh.setOnRefreshListener(this)

        getLocationSearch(mSearch)

    }


    override fun onRefresh() {
        getLocationSearch(mSearch)
    }


    /**
     *  지역 목록 조회
     */
    private fun getLocationSearch(strSearch: String) {

        val retrofitAsyncTask = RetrofitAsyncTask(this@MainActivity, WeatherAPIs.GET_LOCATION_SEARCH)

        val call = mWeatherAPIs.getLocationSearch( strSearch )

        retrofitAsyncTask.execute(call)
        retrofitAsyncTask.setRetrofitCallback(this@MainActivity)
    }


    private fun getLocation( nWoeid: Int)
    {
        val retrofitAsyncTask = RetrofitAsyncTask(this@MainActivity,  WeatherAPIs.GET_LOCATION )

        val call = mWeatherAPIs.getLocation( nWoeid )

        retrofitAsyncTask.execute(call)
        retrofitAsyncTask.setRetrofitCallback(this@MainActivity)
    }

    override fun onResponse(nId: Int, response: Response<*>)
    {

        when( val strBody = response.body() )
        {
            is GetLocationSearch -> {
                for ( locationInfo in strBody )
                    getLocation( locationInfo.woeid )
            }

            is GetLocation->{
                mArrayList.add(strBody)
                mRecyclerAdapter.notifyDataSetChanged()

                refresh.isRefreshing = false
            }
        }
    }

    override fun onFailed(nResponseCode: Int, nId: Int, response: ResponseBody?) {
//        Log.e(TAG, response.toString() )
        Snackbar.make( findViewById(android.R.id.content), "${response?.string()}", Snackbar.LENGTH_LONG).show();
    }

    override fun onExceptionCallback(e: Exception, nId: Int) {
//        Log.e(TAG, e?.message )
        Snackbar.make( findViewById(android.R.id.content), "${e?.message}", Snackbar.LENGTH_LONG).show();
    }

}


