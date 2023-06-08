package dokter.co.id.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dokter.co.id.MainActivity
import dokter.co.id.model.Dokter
import dokter.co.id.model.Message
import dokter.co.id.R


class ViewModelMain : ViewModel() {

//    val sharedData: MutableLiveData<String> = MutableLiveData()
    var listChat: MutableLiveData<Message> = MutableLiveData()

    private var database: DatabaseReference? = null
    private lateinit var fragmentManager: FragmentManager

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun setFragmentManager(manager: FragmentManager) {
        fragmentManager = manager
    }

    fun setFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.flView, fragment)
        transaction.commit()
    }

    fun checkEmailAndPassword(context: Context,email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login berhasil
                    val dokter = firebaseAuth.currentUser
                    Log.d("TAG", "Login berhasil. User ID: ${dokter?.uid}")

                    database = FirebaseDatabase.getInstance().getReference("User")

                    val query: Query = database!!
                        .orderByChild("email")
                        .equalTo(email)
                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // dataSnapshot is the "issue" node with all children with id 0
                                for (dokter in dataSnapshot.children) {
                                    // do something with the individual "issues"
                                    val dokter: Dokter? = dokter.getValue(Dokter::class.java)
//                                if (customer!!.password.equals(binding!!.etPassword.getText().toString().trim())) {
                                    if (dokter!!.role.equals("Dokter")) {
                                        val editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit()
                                        editor.putString("id_user", dokter.dokterId)
                                        editor.putString("nama", dokter.nama)
                                        editor.apply()

                                        val intent =
                                            Intent(context, MainActivity::class.java)
                                        context.startActivity(intent)
                                    }
                                }
                            } else {
                                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("User not found!")
                                    .setConfirmClickListener { sDialog ->
                                        sDialog.dismissWithAnimation()
                                    }
                                    .show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("TAG", "tidak valid.", databaseError.toException())
                        }
                    })

                } else {
                    // Email atau kata sandi tidak valid
                    Log.e("TAG", "Email atau kata sandi tidak valid.", task.exception)
                    SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("User not found!")
                        .setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                        }
                        .show()
                }
            }
    }

    fun loadfirebase(context: Context) {
        FirebaseApp.initializeApp(context)
        database = FirebaseDatabase.getInstance().getReference("Chat")
        database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (dataSnapshot in snapshot.children){
                        val user = dataSnapshot.getValue(Message::class.java)
                        listChat.value = user!!
                    }
//                    inventoryAdapter.setView(listDataBarang)
//                    inventoryAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun sendChat(context: Context, isi_pesan: String){
//        val nama = bindingDialog!!.tvNamaBarang.text.toString()
//        val qty = bindingDialog!!.tvJumlahBarang.text.toString()
//        val harga = bindingDialog!!.tvHargaBarang.getDoubleValue().toString()
        val prefs = context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        val id_user = prefs.getString("id_user", "")
        val nama = prefs.getString("nama", "")

//        val customerId = database!!.push().key.toString()
//        val dokterId = database!!.push().key.toString()

        val barang = Message(id_user!!, "Doker1", nama!!, isi_pesan, "Doker1", "20230605", "0")
        database = FirebaseDatabase.getInstance().getReference("Chat")
        database!!.child(id_user).setValue(barang).addOnCompleteListener {
            loadfirebase(context)
        }
    }
}