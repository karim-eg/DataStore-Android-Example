package co.encept.datastore

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.edit
import co.encept.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            // Get User Data If It Exists
            CoroutineScope(Dispatchers.IO).launch {
                getUserData()
            }



            // Save User Data
            btnSave.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    saveUserData(
                        edName.text.toString(),
                        edEmail.text.toString()
                    )
                }
            }


            // Delete Saved User Data
            btnDelete.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    deleteUserData()

                    getUserData()
                }
            }
        }
    }


    private suspend fun saveUserData(name: String, email: String) {
        user.edit { usrData ->
            usrData[DataStoreKeys.USER_NAME] = name
            usrData[DataStoreKeys.EMAIL] = email
        }

        // Display Data after save:
        getUserData()
    }

    @SuppressLint("SetTextI18n")
    private suspend fun getUserData() {
        user.data.collect { usrData ->
            val name = usrData[DataStoreKeys.USER_NAME] ?: "none"
            val email = usrData[DataStoreKeys.EMAIL] ?: "none"

            // Display Data On UI
            runOnUiThread {
                binding.apply {
                    userName.text = "Username: $name"
                    txtEmail.text = "Email: $email"
                }
            }
        }
    }

    private suspend fun deleteUserData() {
        user.edit { usrData ->
            usrData.clear()
        }

        // Display Data after delete:
        getUserData()
    }
}