package com.example.notifications_messagingstyle_remoteinput

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastReceiverEx : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        var remoteinput = androidx.core.app.RemoteInput.getResultsFromIntent(intent)

        if (remoteinput != null) {

            var msg = remoteinput.getString("key")


            MainActivity.list!!.add(ChatData("me",msg!!,System.currentTimeMillis()))
            MainActivity.n(context!!)
        }
    }
}