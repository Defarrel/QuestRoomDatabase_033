package com.example.tugas_pertemuan10.ui.viewmodel

import android.app.appsearch.StorageInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas_pertemuan10.data.entity.Mahasiswa
import com.example.tugas_pertemuan10.repository.LocalRepositoryMhs
import com.example.tugas_pertemuan10.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel(savedStateHandle: SavedStateHandle,
    private val repositoryMhs: LocalRepositoryMhs): ViewModel() {
        var updateUIState by mutableStateOf(MhsUIState())
            private set
    private val _nim: String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinationUpdate.NIM])

    init{
        viewModelScope.launch {
            updateUIState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }
    fun updateState(MahasiswaEvent: MahasiswaEvent) {
        updateUIState = updateUIState.copy(
            mahasiswaEvent = MahasiswaEvent,
        )
    }
    fun validateFields(): Boolean {
        val event = updateUIState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong",
        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    suspend fun updateData(){
        val currentEvent = updateUIState.mahasiswaEvent

        if (validateFields()){
            try {
                repositoryMhs.updateMhs(currentEvent.toMahasiswaEntity())
                updateUIState = updateUIState.copy(
                    snackBarMessage = "Data berhasil diupdate",
                    mahasiswaEvent = MahasiswaEvent(), //Reset input form
                    isEntryValid = FormErrorState() //Reset validasi form
                )
                println("snackbarMassage diatur: ${updateUIState.snackBarMessage}")
            } catch (e: Exception){
                updateUIState = updateUIState.copy(
                    snackBarMessage = "Data gagal diupdate"
                )
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }

        fun resetSnackBarMessage(){
            updateUIState = updateUIState.copy(snackBarMessage = null)
        }
    }
}

fun Mahasiswa.toUIStateMhs(): MhsUIState = MhsUIState(
    mahasiswaEvent = this.toDetailUiEvent(),
)