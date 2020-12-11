package com.kongup.common.server

import android.content.Context
import android.os.AsyncTask
import android.view.WindowManager
import com.kongup.main.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK


class RetrofitAsyncTask(val context:Context, val nId: Int ): AsyncTask<Call<*>, Void, Response<*>>()
{
    private val TAG = RetrofitAsyncTask::class.java.name

    lateinit var mRemoteCallbackList: RetrofitCallback
    lateinit var mProgressDialog:MyProgressDialog



    override fun onPreExecute() {
        super.onPreExecute()

        mProgressDialog = MyProgressDialog(context, R.drawable.progressbar_xml)
        mProgressDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        mProgressDialog.show()
    }

    override fun doInBackground(vararg p0: Call<*>): Response<*>
    {
        val call = p0[0]

        return call.execute()

//        val execute = call.execute()
//
//        return execute
    }

    override fun onPostExecute(result: Response<*>)
    {
        super.onPostExecute(result)

        mProgressDialog.dismiss()

        try {
            if( result.code() == HTTP_OK )
            {
//                Log.e( TAG, result.body().toString() )
                mRemoteCallbackList.onResponse( nId, result )
            }
            else
            {
//                Log.e(TAG, "Code : ${result.code()}")
                mRemoteCallbackList.onFailed( result.code(), nId, result.errorBody() )
            }
        }
        catch (e:Exception)
        {
            mRemoteCallbackList.onExceptionCallback( e, nId )
        }
    }

    public fun setRetrofitCallback( retrofitCallback: RetrofitCallback)
    {
        mRemoteCallbackList = retrofitCallback
    }

    interface RetrofitCallback
    {
        fun onResponse( nId: Int, response: Response<*>)

        fun onFailed(nResponseCode:Int, nId:Int , response: ResponseBody?)

        fun onExceptionCallback(e: Exception, nId: Int)

    }
}