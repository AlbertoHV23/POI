package com.poi.camppus.ui.groups

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.CreateAssigmentActivity
import com.poi.camppus.R
import com.poi.camppus.adapters.ListaTareasAdapter
import com.poi.camppus.adapters.ListaUsuariosAdapter
import com.poi.camppus.adapters.SubGroupsAdapter
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Usuarios
import com.poi.camppus.models.tbl_asssigments
import com.poi.camppus.models.tbl_subgroups


class FragmentsGroupAssigments(var id:String) : Fragment() {

    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();

    var id_grupo = id
    private var context2: Context? = null
    private var adapter: ListaTareasAdapter? = null
    lateinit var misAssigments: List<tbl_asssigments>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root =  inflater.inflate(R.layout.fragment_fragments_group_assigments, container, false)
        auth = FirebaseAuth.getInstance()

        val rvChat: RecyclerView =root.findViewById<RecyclerView>(R.id.rv_lista_asssigmnets_)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)
        val userRef = firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(id_grupo)
        userRef.collection(ReferenciasFirebase.ASSIGMENTS.toString())
            .get()
            .addOnSuccessListener { document ->
                misAssigments= document.toObjects(tbl_asssigments::class.java)
                this.adapter = ListaTareasAdapter(this.context2!!, misAssigments)
                rvChat.adapter = this.adapter
            }


        userRef.collection(ReferenciasFirebase.ASSIGMENTS.toString()).addSnapshotListener(){
                messages,error ->
            if (error == null){
                messages?.let {
                    misAssigments= it.toObjects(tbl_asssigments::class.java)
                    this.adapter = ListaTareasAdapter(this.context2!!, misAssigments)
                    rvChat.adapter = this.adapter
                }
            }
        }





        val fab: View = root.findViewById(R.id.fb4)
        fab.setOnClickListener { view ->
            val intent = Intent (getActivity(), CreateAssigmentActivity::class.java)
            intent.putExtra("ID", id_grupo)

            startActivity(intent)
        }

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }
}