package com.example.tugas_pertemuan10

import android.app.Application
import com.example.tugas_pertemuan10.depedenciesinjection.ContainerApp

class KrsApp: Application() {
    lateinit var containerApp: ContainerApp //Fungsinya untuk menyimoan instance

    override fun onCreate(){
        super.onCreate()
        containerApp = ContainerApp(this) //Membuat instance ContainerAPp
        //instance adalah object yang dibuat dari class
    }
}