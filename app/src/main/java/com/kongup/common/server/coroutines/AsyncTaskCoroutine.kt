package com.kongup.common.server.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AsyncTaskCoroutine<I, O> {

    open fun onPreExecute() {}

    open fun onPostExecute(result: O?) {}

    abstract fun doInBackground(vararg params: I): O?

    fun <T> execute(vararg input: I) {
        GlobalScope.launch(Dispatchers.Main) {
            onPreExecute()
            callAsync(*input)
        }
    }

    private suspend fun callAsync(vararg input: I) {


        var result = withContext( Dispatchers.IO ) {
            doInBackground( *input )
        }

        GlobalScope.launch( Dispatchers.Main ) {
            onPostExecute( result )
        }
    }
}