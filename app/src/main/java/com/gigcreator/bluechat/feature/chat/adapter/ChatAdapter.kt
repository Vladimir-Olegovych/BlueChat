package com.gigcreator.bluechat.feature.chat.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.gigcreator.bluechat.R
import com.gigcreator.bluechat.core.textToPixels
import com.gigcreator.bluechat.databinding.ChatCardBinding

class ChatAdapter(
    private val onAdd: (ChatMessage, ChatAdapter) -> Unit
): RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    private val arrayList = ArrayList<ChatMessage>()
    private val resources = Resources.getSystem()

    inner class ChatHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding: ChatCardBinding = ChatCardBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(message: ChatMessage) = with(binding) {
            if (message.isCurrentDevice) {
                cardContainer.gravity = Gravity.END
            } else {
                cardContainer.gravity = Gravity.START
            }

            messageData.text = message.message

            val halfScreenWidth = resources.displayMetrics.widthPixels / 2
            val textWidth = textToPixels(message.message)
            val params = messageContainer.layoutParams as LinearLayout.LayoutParams

            if (textWidth > halfScreenWidth) {
                params.width = halfScreenWidth
            } else {
                params.width = LinearLayout.LayoutParams.WRAP_CONTENT
            }

            messageContainer.layoutParams = params
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