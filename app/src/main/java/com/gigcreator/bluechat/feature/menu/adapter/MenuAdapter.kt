package com.gigcreator.bluechat.feature.menu.adapter

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigcreator.bluechat.R
import com.gigcreator.bluechat.databinding.ScanCardBinding

class MenuAdapter(
    private val onClickListener: (BluetoothDevice) -> Unit
): RecyclerView.Adapter<MenuAdapter.MenuHolder>() {

    private val arrayList = ArrayList<BluetoothDevice>()

    inner class MenuHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding: ScanCardBinding = ScanCardBinding.bind(view)

        fun bind(device: BluetoothDevice) = with(binding) {
            name.text = device.name?: device.address
            root.setOnClickListener {
                onClickListener.invoke(device)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scan_card, parent, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(
        holder: MenuHolder,
        position: Int
    ) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun set(bluetoothDevices: Collection<BluetoothDevice>) {
        arrayList.addAll(bluetoothDevices)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        arrayList.clear()
        notifyDataSetChanged()
    }
}