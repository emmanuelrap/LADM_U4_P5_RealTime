package com.example.ladm_u4_p5_realtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.ladm_u4_p5_realtime.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var arregloIDs = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //---------- consulta en tiempo real (snapshot)
        val consulta = FirebaseDatabase.getInstance().getReference().child("usuarios")
        val posListener = object : ValueEventListener{ // clase embevida
            override fun onDataChange(snapshot: DataSnapshot) {
                var datos = ArrayList<String>()
                arregloIDs.clear()

                for(data in snapshot.children!!){
                    val id = data.key
                    arregloIDs.add(id!!)
                    val usuario =data.getValue<User>()!!.userName
                    val correo =data.getValue<User>()!!.email
                    datos.add("nombre: ${usuario} correo: ${correo}")
                }
                mostrarLista(datos)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        consulta.addValueEventListener(posListener)
        //---------------------------------------------

        binding.btnInsertar.setOnClickListener {
            var baseDatos =Firebase.database.reference //reference es el link de acceso
            val usuario=User(binding.etNombre.text.toString(),binding.etCorreo.text.toString()) //hash mapOf (equivalencia)
            baseDatos.child("usuarios").push().setValue(usuario)
                .addOnSuccessListener {
                    AlertDialog.Builder(this)
                        .setTitle("NO insertó Correctamente")
                        .setPositiveButton("OK"){d,i->}
                        .show()
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this)
                        .setTitle("NO insertó Correctamente")
                        .setMessage(it.message)
                        .setPositiveButton("OK"){d,i->}
                        .show()
                }
        }
    }
     fun mostrarLista(datos:ArrayList<String>){
        binding.lv.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1)
     }
}