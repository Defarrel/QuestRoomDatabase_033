package com.example.tugas_pertemuan10.ui.costumwidget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugas_pertemuan10.ui.navigation.AlamatNavigasi
import com.example.tugas_pertemuan10.ui.view.mahasiswa.DestinationInsert
import com.example.tugas_pertemuan10.ui.view.mahasiswa.DetailMhsView
import com.example.tugas_pertemuan10.ui.view.mahasiswa.HomeMhsView
import com.example.tugas_pertemuan10.ui.view.mahasiswa.InsertMhsView
import com.example.tugas_pertemuan10.ui.view.mahasiswa.UpdateMhsView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(navController = navController, startDestination = DestinationInsert.route) {
        composable(
            route = DestinationInsert.route
        ){
            HomeMhsView(
                onDetailClick = {nim ->
                    navController.navigate("${ AlamatNavigasi.DestinationDetail.route}/$nim")
                    println("Pengelola Halaman: nim = $nim")
                },
                onAddMhs = {
                    navController.navigate(AlamatNavigasi.DestinationInsert.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinationInsert.route
        ){
            InsertMhsView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            AlamatNavigasi.DestinationDetail.routeWithArgs,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinationDetail.NIM) {
                    type = NavType.StringType
                }
            )
        ){
            val nim  = it.arguments?.getString(AlamatNavigasi.DestinationDetail.NIM)
            nim.let { nim ->
                DetailMhsView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${AlamatNavigasi.DestinationEdit.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            AlamatNavigasi.DestinationEdit.routeWithArgs,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinationEdit.NIM) {
                    type = NavType.StringType
                }
            )
        ){
            UpdateMhsView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}
