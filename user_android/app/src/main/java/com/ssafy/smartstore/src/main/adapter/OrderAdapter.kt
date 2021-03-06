package com.ssafy.smartstore.src.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.smartstore.R
import com.ssafy.smartstore.config.ApplicationClass
import com.ssafy.smartstore.src.main.response.LatestOrderResponse
import com.ssafy.smartstore.util.CommonUtils

private const val TAG = "OrderAdapter_싸피"
class OrderAdapter(val context: Context, var userLastOrderList: List<LatestOrderResponse>) :RecyclerView.Adapter<OrderAdapter.OrderHolder>(){

    inner class OrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val menuImage = itemView.findViewById<ImageView>(R.id.menuImage)
        val textMenuNames = itemView.findViewById<TextView>(R.id.odlistTvName)
        val textMenuPrice = itemView.findViewById<TextView>(R.id.odlistTvTotalPrice)
        val textMenuDate = itemView.findViewById<TextView>(R.id.odlistTvDate)
        val textCompleted = itemView.findViewById<TextView>(R.id.textCompleted)

        fun bindInfo(data:LatestOrderResponse){
            Log.d(TAG, "bindInfo: ${data}")

            Glide.with(itemView)
                .load("${ApplicationClass.MENU_IMGS_URL}${data.img}")
                .into(menuImage)

            if(data.orderCnt > 1){
                textMenuNames.text = "${data.koName} 외 ${data.orderCnt -1}건"  //외 x건
            }else{
                textMenuNames.text = data.koName
            }

            textMenuPrice.text = CommonUtils.makeComma(data.totalPrice)
            textMenuDate.text = CommonUtils.getFormattedString(data.orderDate)
            var com = data.orderCompleted.toString()
            if(com == "N") {
                textCompleted.background.setTint(ContextCompat.getColor(context, R.color.coffee_orange))
                textCompleted.setTextColor(ContextCompat.getColor(context, R.color.black))
                textCompleted.text = "접수 중"
            }
            else if(com == "M") {
                textCompleted.background.setTint(ContextCompat.getColor(context, R.color.coffee_yellow))
                textCompleted.setTextColor(ContextCompat.getColor(context, R.color.black))
                textCompleted.text = "진행 중"
            } else if(com == "P") {
                textCompleted.background.setTint(ContextCompat.getColor(context, R.color.brewed_green))
                textCompleted.setTextColor(ContextCompat.getColor(context, R.color.white))
                textCompleted.text = "제조완료"

            } else if(com == "Y") {
                textCompleted.background.setTint(ContextCompat.getColor(context, R.color.brewed_green))
                textCompleted.setTextColor(ContextCompat.getColor(context, R.color.white))
                textCompleted.text = "픽업완료"
            } else {

            }
            //클릭연결
            itemView.setOnClickListener{
                Log.d(TAG, "bindInfo: ${data.orderId}")
                itemClickListner.onClick(it, layoutPosition, data.orderId)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_order, parent, false)
        return OrderHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bindInfo(userLastOrderList!![position])
    }

    override fun getItemCount(): Int {
        return userLastOrderList!!.size
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
    interface ItemClickListener {
        fun onClick(view: View,  position: Int, orderid:Int)
    }
    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener
    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}

