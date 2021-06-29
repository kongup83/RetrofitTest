package com.kongup.common.server.coroutines

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.WindowManager
import android.widget.PopupMenu
import com.kongup.common.server.MyProgressDialog
import com.kongup.main.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection

class RetrofitCoroutineTask(private val context: Context, private val nId: Int ): AsyncTaskCoroutine<Call<*>, Response<*>?>(){

    private val TAG = RetrofitCoroutineTask::class.java.name

    private var mRemoteCallbackList: RetrofitCallback? = null
    private lateinit var mProgressDialog: MyProgressDialog


    override fun onPreExecute() {
        super.onPreExecute()

        mProgressDialog = MyProgressDialog(context, R.drawable.progressbar_xml)
//        mProgressDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//        mProgressDialog.setCancelable( true )
        mProgressDialog.setOnDismissListener {
            Log.e(TAG, "Dismiss ID : $nId")
        }

        mProgressDialog.setOnCancelListener {
            Log.e(TAG, "Cancel ID : $nId")
        }

        mProgressDialog.show()
    }

    override fun doInBackground(vararg params: Call<*>): Response<*>? {

        val call = params[0]

        return call.execute()
    }



    override fun onPostExecute(result: Response<*>?) {
        super.onPostExecute(result)

        mProgressDialog.dismiss()

        result?.let {
            try {
                if( result.code() == HttpURLConnection.HTTP_OK)
                {
//                Log.e( TAG, result.body().toString() )
                    mRemoteCallbackList?.onResponse( nId, result )
                }
                else
                {
//                Log.e(TAG, "Code : ${result.code()}")
                    mRemoteCallbackList?.onFailed( result.code(), nId, result.errorBody() )
                }
            }
            catch (e:Exception)
            {
                mRemoteCallbackList?.onExceptionCallback( e, nId )
            }
        }
    }


    public fun setRetrofitCallback( retrofitCallback: RetrofitCallback)
    {
        mRemoteCallbackList = retrofitCallback
    }


    interface RetrofitCallback
    {
        fun onResponse( nId: Int, response: Response<*>?)

        fun onFailed(nResponseCode:Int, nId:Int , response: ResponseBody?)

        fun onExceptionCallback(e: Exception, nId: Int)
    }

}