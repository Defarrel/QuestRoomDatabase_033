package com.example.tugas_pertemuan10.depedenciesinjection

import android.content.Context
import com.example.tugas_pertemuan10.data.database.KrsDatabase
import com.example.tugas_pertemuan10.repository.LocalRepositoryMhs
import com.example.tugas_pertemuan10.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}


class ContainerApp(private val context: Context): InterfaceContainerApp {
    override val repositoryMhs: RepositoryMhs by lazy{
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}