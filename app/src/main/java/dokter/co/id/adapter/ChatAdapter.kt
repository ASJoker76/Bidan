package dokter.co.id.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dokter.co.id.R
import dokter.co.id.model.Message


class ChatAdapter(
    private val chatList: MutableList<Message> = mutableListOf()
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SENDER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_sender, parent, false)
            SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_receiver, parent, false)
            ReceiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chatList[position]
        if (holder.itemViewType == TYPE_SENDER) {
            val senderViewHolder = holder as SenderViewHolder
            senderViewHolder.textViewMessage.text = chat.isi_pesan
        } else {
            val receiverViewHolder = holder as ReceiverViewHolder
            receiverViewHolder.textViewMessage.text = chat.isi_pesan
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].type.equals("0")) {
            TYPE_SENDER
        } else {
            TYPE_RECEIVER
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    inner class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)
    }

    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)
    }

    companion object {
        private const val TYPE_SENDER = 0
        private const val TYPE_RECEIVER = 1
    }

    fun setView(responNew: List<Message>) {
        chatList.clear() // clear list
        chatList.addAll(responNew)
        notifyDataSetChanged() // let your adapter know about the changes and reload view.
    }
}
