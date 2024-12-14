package com.example.tugas_pertemuan10.ui.view.mahasiswa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.KeyEventDispatcher.Component
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas_pertemuan10.data.entity.Mahasiswa
import com.example.tugas_pertemuan10.ui.costumwidget.TopAppBar
import com.example.tugas_pertemuan10.ui.viewmodel.DetailMhsViewModel
import com.example.tugas_pertemuan10.ui.viewmodel.DetailUiState
import com.example.tugas_pertemuan10.ui.viewmodel.PenyediaViewModel
import com.example.tugas_pertemuan10.ui.viewmodel.toMahasiswaEntity


@Composable
fun DetailMhsView(
    modifier: Modifier = Modifier,
    viewModel: DetailMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {},
){
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Mahasiswa",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiState.value.detailUiEvent.nim) },
                shape = MaterialTheme.shapes.medium,
                modifier = modifier.padding(16.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa",
                )
            }
        }
    ){innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailMhs(
            modifier = modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteMhs()
                onDeleteClick
            }
        )
    }
}

@Composable
fun BodyDetailMhs(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {}
){
    var deleteConfirmationRequared by rememberSaveable { mutableStateOf(false) }
    when{
        detailUiState.isLoading ->{
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator() //tampilkan loading
            }
        }
        detailUiState.isUiEventNotEmpty ->{
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                ItemDetailMhs(
                    mahasiswa = detailUiState.detailUiEvent.toMahasiswaEntity(),
                    modifier = modifier
                )
                Spacer(modifier = modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequared = true
                    },
                    modifier = modifier.fillMaxWidth()
                ){
                    Text("Delete")
                }
                if(deleteConfirmationRequared){
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequared = false
                            onDeleteClick()
                        },
                        onDeleteCancel = {
                            deleteConfirmationRequared = false
                        },
                        modifier = modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
