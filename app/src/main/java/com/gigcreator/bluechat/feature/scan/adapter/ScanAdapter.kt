package com.gigcreator.bluechat.feature.scan.adapter

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigcreator.bluechat.R
import com.gigcreator.bluechat.databinding.ScanCardBinding

class ScanAdapter(
    private val onClickListener: (BluetoothDevice) -> Unit
): RecyclerView.Adapter<ScanAdapter.ScanHolder>() {

    private val arrayList = ArrayList<BluetoothDevice>()

    inner class ScanHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding: ScanCardBinding = ScanCardBinding.bind(view)

        fun bind(device: BluetoothDevice) = with(binding) {
            if (device.bondState == BluetoothDevice.BOND_BONDED) {
                root.setBackgroundResource(R.drawable.shape_button_theme_1)
                name.setTextColor(Color.WHITE)
            }
            else {
                name.setTextColor(Color.BLACK)
                root.setBackgroundResource(R.drawable.shape_scan_card)
            }
            name.text = device.name?: device.address
            root.setOnClickListener {
                onClickListener.invoke(device)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScanHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scan_card, parent, false)
        return ScanHolder(view)
    }

    override fun onBindViewHolder(
        holder: ScanHolder,
        position: Int
    ) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun add(bluetoothDevice: BluetoothDevice) {
        val index = arrayList.indexOfFirst { it.address == bluetoothDevice.address }

        if (index != -1) {
            arrayList.removeAt(index)
            notifyItemRemoved(index)
        }

        arrayList.add(bluetoothDevice)
        notifyItemInserted(arrayList.size - 1)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        arrayList.clear()
        notifyDataSetChanged()
    }
}