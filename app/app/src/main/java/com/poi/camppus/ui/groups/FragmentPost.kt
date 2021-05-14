package com.poi.camppus.ui.groups

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.R
import com.poi.camppus.adapters.ListaChatAdapter
import com.poi.camppus.adapters.MensajesAdapter
import com.poi.camppus.adapters.PostAdapter
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Mensajes
import com.poi.camppus.models.tbl_posts
import io.grpc.Server
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class FragmentPost(id:String) : Fragment() {
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();

    var id_grupo = id

    private var context2: Context? = null
    private var adapter: PostAdapter? = null
    lateinit var MisMensajes: List<tbl_posts>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        var root =  inflater.inflate(R.layout.fragment_post, container, false)
        var btn_postear:Button = root.findViewById(R.id.btn_enviarmensajemain2)
        var txt_post:EditText = root.findViewById(R.id.txt_mensaje_main2)

        val rvChat:RecyclerView =root.findViewById<RecyclerView>(R.id.rv_listaPost)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)

        val userRef = firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(id_grupo)

        userRef.collection(ReferenciasFirebase.POSTS.toString()).orderBy("date",com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { document ->
                    MisMensajes= document.toObjects(tbl_posts::class.java)
                    this.adapter = PostAdapter(this.context2!!, MisMensajes)
                    rvChat.adapter = this.adapter
                }

        userRef.collection(ReferenciasFirebase.POSTS.toString()).orderBy("date",com.google.firebase.firestore.Query.Direction.ASCENDING)
                .addSnapshotListener(){
                    messages,error ->
                    if (error == null){
                        messages?.let {
                            MisMensajes= it.toObjects(tbl_posts::class.java)
                            this.adapter = PostAdapter(this.context2!!, MisMensajes)
                            rvChat.adapter = this.adapter
                        }
                    }
                }




        btn_postear.setOnClickListener(){
            val localDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss")
            val formatted = localDateTime.format(formatter)

                var POST:tbl_posts = tbl_posts(UUID.randomUUID().toString(),txt_post.text.toString(),auth.currentUser.email, formatted,id_grupo)
               postear(POST)
                txt_post.setText("")


        }

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }

    private fun postear(post:tbl_posts){


        firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(post.Team).collection(ReferenciasFirebase.POSTS.toString()).document().set(post)

    }

}

