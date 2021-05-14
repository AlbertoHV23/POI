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
import com.poi.camppus.R
import com.poi.camppus.activities.AddGroupActivity
import com.poi.camppus.activities.AddSubGroupActivity
import com.poi.camppus.activities.SendmessagesActivity
import com.poi.camppus.adapters.GroupsAdapter
import com.poi.camppus.adapters.PostAdapter
import com.poi.camppus.adapters.SubGroupsAdapter
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_groups
import com.poi.camppus.models.tbl_posts
import com.poi.camppus.models.tbl_subgroups


class FragmentFiles(var id:String) : Fragment() {
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();

    var id_grupo = id
    private var context2: Context? = null
    private var adapter: SubGroupsAdapter? = null
    lateinit var misGrupos: List<tbl_subgroups>




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root =  inflater.inflate(R.layout.fragment_files, container, false)

        val fab: View = root.findViewById(R.id.fb2)
        auth = FirebaseAuth.getInstance()


        val rvChat: RecyclerView =root.findViewById<RecyclerView>(R.id.rv_lista_subgrupos) //busca
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)
        val userRef = firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(id_grupo)

        userRef.collection(ReferenciasFirebase.SUBGROUPS.toString())
                .get()
                .addOnSuccessListener { document ->
                    misGrupos= document.toObjects(tbl_subgroups::class.java)
                    this.adapter = SubGroupsAdapter(this.context2!!, misGrupos)
                    rvChat.adapter = this.adapter
                }

        userRef.collection(ReferenciasFirebase.SUBGROUPS.toString())
            .addSnapshotListener(){
                    messages,error ->
                if (error == null){
                    messages?.let {
                        misGrupos= it.toObjects(tbl_subgroups::class.java)
                        this.adapter = SubGroupsAdapter(this.context2!!, misGrupos)
                        rvChat.adapter = this.adapter
                    }
                }
            }







        fab.setOnClickListener { view ->
            val intent = Intent (getActivity(), AddSubGroupActivity::class.java)
            intent.putExtra("ID", id)
            startActivity(intent)
            startActivity(intent)
        }
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }
}