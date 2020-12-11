//package com.kongup.main
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.kongup.common.server.data.GetLocation
//
//class ListViewModel : ViewModel()
//{
//    private val mArrayList: MutableList<GetLocation> = mutableListOf()
//
//    /**
//     * 라이브 데이터 생성
//     */
//    val mLiveData: MutableLiveData<MutableList<GetLocation>> by lazy {
//        MutableLiveData<MutableList<GetLocation>>().apply {
//            value = mArrayList
//        }
//    }
//
//    /**
//     * 값추가
//     */
//    fun addLocation(data: GetLocation)
//    {
//        val tempList = mLiveData.value
//        tempList?.add(data)
//        mLiveData.value = tempList
//    }
//
//    /**
//     * 전체 삭제
//     */
//    fun remove()
//    {
//        val tempList = mLiveData.value
//        tempList?.clear()
//        mLiveData.value = tempList
//    }
//}