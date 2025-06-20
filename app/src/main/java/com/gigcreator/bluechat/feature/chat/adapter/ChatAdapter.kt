package com.gigcreator.bluechat.feature.chat.adapter

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.gigcreator.bluechat.R
import com.gigcreator.bluechat.databinding.ChatCardBinding
import com.gigcreator.bluechat.databinding.ScanCardBinding

class ChatAdapter(
    private val device: BluetoothDevice,
    private val deviceName: String?,
    private val onAdd: (ChatMessage, ChatAdapter) -> Unit
): RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    private val arrayList = ArrayList<ChatMessage>()

    inner class ChatHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding: ChatCardBinding = ChatCardBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(message: ChatMessage) = with(binding) {
            //val params = cardContainer.layoutParams as LinearLayout.LayoutParams
            if (message.isCurrentDevice) {
                cardContainer.gravity = Gravity.END
                name.text = "$deviceName:"

            } else {
                cardContainer.gravity = Gravity.START
                name.text = (device.name?: device.address) + ":"
            }
            //cardContainer.layoutParams = params
            messageData.text = message.message
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_card, parent, false)
        return ChatHolder(view)
    }

    override fun onBindViewHolder(
        holder: ChatHolder,
        position: Int
    ) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun add(message: ChatMessage) {
        arrayList.add(message)
        notifyItemInserted(arrayList.size - 1)
        onAdd.invoke(message, this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        arrayList.clear()
        notifyDataSetChanged()
    }
}