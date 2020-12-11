package com.kongup.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kongup.common.server.data.GetLocation
import com.kongup.main.R
import kotlinx.android.synthetic.main.data_db.view.*
import java.text.SimpleDateFormat
import java.util.*


class RecyclerAdapter(val context: Context, val list: MutableList<GetLocation>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder
    {
        val view = LayoutInflater.from(context).inflate(R.layout.data_db, parent, false)

        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(viewHolder: ItemViewHolder, nPosition: Int) {

        if (nPosition == 0)
            viewHolder.containerView.linear_header.visibility = View.VISIBLE
        else
            viewHolder.containerView.linear_header.visibility = View.GONE

        val getLocation = list.get(nPosition)

        val strLocal                = getLocation.title
        val consolidatedWeathers    = getLocation.consolidated_weather

        val format      = SimpleDateFormat("yyyy-MM-dd")
        val time        = Date()
        val strToday    = format.format(time)

        val nLength = consolidatedWeathers.size

        for (i in 0 until nLength) {

            val consolidated_weathers = consolidatedWeathers[i]
            val strApplicableDate = consolidated_weathers.applicable_date

            if (strToday == strApplicableDate) {

                var strTodayIcon        = consolidated_weathers.weather_state_abbr
                val strTodayTitle       = consolidated_weathers.weather_state_name
                val strTodayTemp        = consolidated_weathers.the_temp
                val strTodayHumidity    = consolidated_weathers.humidity

                viewHolder.containerView.text_local.text            = strLocal
                viewHolder.containerView.text_today_title.text      = strTodayTitle
                viewHolder.containerView.text_today_temp.text       = strTodayTemp.toInt().toString() + "℃"
                viewHolder.containerView.text_today_humidity.text   = strTodayHumidity.toString() + "％"

                strTodayIcon = String.run {

                    format(context.getString(R.string.img_url), strTodayIcon)
                }

                imageLoder( strTodayIcon, viewHolder.containerView.img_today_icon )

                if (i + 1 < nLength) {
                    val consolidatedWeatherTomorrow     = getLocation.consolidated_weather[i + 1]
                    var strTomorrowIcon                 = consolidatedWeatherTomorrow.weather_state_abbr
                    val strTomorrowTitle                = consolidatedWeatherTomorrow.weather_state_name
                    val strTomorrowTemp                 = consolidatedWeatherTomorrow.the_temp
                    val strTomorrowHumidity             = consolidatedWeatherTomorrow.humidity

                    viewHolder.containerView.text_tomorrow_title.text       = strTomorrowTitle
                    viewHolder.containerView.text_tomorrow_temp.text        = strTomorrowTemp.toInt().toString() + "℃"
                    viewHolder.containerView.text_tomorrow_humidity.text    = strTomorrowHumidity.toString() + "％"

                    strTomorrowIcon = String.run {
                        format(context.getString(R.string.img_url), strTomorrowIcon)
                    }

                    imageLoder( strTomorrowIcon, viewHolder.containerView.img_tomorrow_icon)
                }

                break
            }
        }
    }



    private fun imageLoder(strProfileImg: String, target: ImageView) {

        val requestOptions = RequestOptions().apply {
            placeholder(R.mipmap.ic_launcher)
            error(R.mipmap.ic_launcher)
            override(100)
        }

        Glide.with(context).apply {
            load(strProfileImg).apply {
                thumbnail(0.1f)
                apply(requestOptions)
                into(target)
            }
        }
    }

}