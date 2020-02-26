package com.example.notifications_messagingstyle_remoteinput

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var username: String? = null
    var message: String? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nm = NotificationManagerCompat.from(this)
        list = mutableListOf()
        list!!.add(ChatData("user1", "message1", System.currentTimeMillis()))
        list!!.add(ChatData("user2", "message2", System.currentTimeMillis()))
        list!!.add(ChatData("user3", "message3", System.currentTimeMillis()))



        bt1.setOnClickListener {
            username = et1.text.toString()
            message = et2.text.toString()
            list!!.add(ChatData(username, message!!, System.currentTimeMillis()))
            n(this)
        }


    }


    companion object {
        var nm: NotificationManagerCompat? = null
        var list: MutableList<ChatData>? = null


        fun n(c: Context) {

            //--------------------------------------------------------------------------------------------------------------------------
            var messagingStyle =
                NotificationCompat.MessagingStyle("me").setConversationTitle("GroupChat")
            for (i in list!!) {
                messagingStyle.addMessage(
                    NotificationCompat.MessagingStyle.Message(
                        i.message,
                        i.t,
                        i.username
                    )
                )
            }
            //--------------------------------------------------------------------------------------------------------------

            var i = Intent(c, BroadcastReceiverEx::class.java)
            var pi = PendingIntent.getBroadcast(c, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

            var action =
                NotificationCompat.Action.Builder(R.drawable.ic_android_black_24dp, "Reply", pi)
                    .addRemoteInput(androidx.core.app.RemoteInput.Builder("key").setLabel("Answer here....").build())
                    .build()
            //------------------------------------------------------------------------------------------------------------------------------


            var n = NotificationCompat.Builder(c, App.id)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("User1")
                .setContentText("hi...san")
                .setSubText("ChatApp")
                .setStyle(messagingStyle)
                .addAction(action)
                .setOnlyAlertOnce(true)
                .setContentIntent(
                    PendingIntent.getActivity(
                        c,
                        0,
                        Intent(c, MainActivity::class.java),
                        0
                    )
                )
                .setOnlyAlertOnce(true)
                .setColor(Color.GREEN)
                .build()
            nm!!.notify(1, n)
        }
    }
}
