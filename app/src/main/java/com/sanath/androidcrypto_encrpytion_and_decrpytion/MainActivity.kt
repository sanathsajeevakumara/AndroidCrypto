package com.sanath.androidcrypto_encrpytion_and_decrpytion

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanath.androidcrypto_encrpytion_and_decrpytion.ui.theme.AndroidCrypto_Encrpytion_and_DecrpytionTheme
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cryptoManager = CryptoManager()
        setContent {
            AndroidCrypto_Encrpytion_and_DecrpytionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    var messageToEncrypt by remember {
                        mutableStateOf("")
                    }

                    var messageToDecrypt by remember {
                        mutableStateOf("")
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    ) {
                        TextField(
                            value = messageToEncrypt,
                            onValueChange = { messageToEncrypt = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Encryption Text") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Button(onClick = {
                                val bytes = messageToEncrypt.encodeToByteArray()
                                var file = File(filesDir, "Secret.txt")
                                if (!file.exists()) {
                                    file.createNewFile()
                                }
                                val fos = FileOutputStream(file)

                                messageToDecrypt = cryptoManager.encrypt(
                                    bytes = bytes,
                                    outputStream = fos
                                ).decodeToString()
                            }) {
                                Text(text = "Encrypt")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                var file = File(filesDir, "Secret.txt")
                                messageToEncrypt = cryptoManager.decrypt(
                                    inputStream = FileInputStream(file)
                                ).decodeToString()
                            }) {
                                Text(text = "Decrypt")
                            }
                        }
                        Text(text = messageToDecrypt)
                    }

                }
            }
        }
    }
}