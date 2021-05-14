package com.poi.camppus

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.adapters.PostAdapter
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_posts
import java.time.LocalDateTime
import java.util.*

class SubGroupPageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();
    private var context2: Context? = null
    private var adapter: PostAdapter? = null
    lateinit var MisMensajes: List<tbl_posts>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Camppus_login)
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_sub_group_page)
        var NOMBRE=  intent.getStringExtra("NAMETEAM")
        var uid=  intent.getStringExtra("ID")


        var txt_name: TextView = findViewById(R.id.txt_SubGroupName3)
        var txt_mensaje: TextView = findViewById(R.id.txt_mensaje_main4)
        var btn_enviar: Button = findViewById(R.id.btn_enviarmensajemain4)


        val userRef = firebase.collection(ReferenciasFirebase.SUBGROUPS.toString()).document(uid.toString())
        val rvChat: RecyclerView =findViewById<RecyclerView>(R.id.rv_lista_subgrupos)
        rvChat.layoutManager = LinearLayoutManager(this)

        userRef.collection(ReferenciasFirebase.POSTS.toString()).orderBy("date", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { document ->
                    MisMensajes= document.toObjects(tbl_posts::class.java)
                    this.adapter = PostAdapter(this, MisMensajes)
                    rvChat.adapter = this.adapter
                }

        userRef.collection(ReferenciasFirebase.POSTS.toString()).orderBy("date", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .addSnapshotListener(){ messages, error ->
                    if (error == null){
                        messages?.let {
                            MisMensajes= it.toObjects(tbl_posts::class.java)
                            this.adapter = PostAdapter(this, MisMensajes)
                            rvChat.adapter = this.adapter
                        }
                    }
                }

        txt_name.text = NOMBRE
        btn_enviar.setOnClickListener(){
            var POST:tbl_posts = tbl_posts(UUID.randomUUID().toString(), txt_mensaje.text.toString(), auth.currentUser.email, LocalDateTime.now().toString(), uid.toString())
            postear(POST)

            txt_mensaje.setText("")



        }



    }

    private fun postear(post: tbl_posts){


        firebase.collection(ReferenciasFirebase.SUBGROUPS.toString()).document(post.Team).collection(ReferenciasFirebase.POSTS.toString()).document().set(post)

    }


}