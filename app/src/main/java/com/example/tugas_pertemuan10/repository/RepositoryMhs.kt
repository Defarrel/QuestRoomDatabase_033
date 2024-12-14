package com.example.tugas_pertemuan10.repository

import com.example.tugas_pertemuan10.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)

}