package de.syntax_institut.androidabschlussprojekt.scanner.presentation.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.scannerscreen.CameraPreview
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.scannerscreen.NoteInputBottomSheet
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.scannerscreen.PermissionExplanationDialog
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.scannerscreen.ScannerBottomInfo
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.scannerscreen.ScannerOverlay
import de.syntax_institut.androidabschlussprojekt.scanner.viewmodel.ScannerViewModel
import de.syntax_institut.androidabschlussprojekt.util.handleActionWithPermission
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerScreen(
    onNavigateToISBNScannerManually: () -> Unit,
    scannerViewModel: ScannerViewModel = koinViewModel(),
) {
    val scannedIsbn by scannerViewModel.scannedIsbn
    val book by scannerViewModel.bookData
    val context = LocalContext.current

    var hasCameraPermission by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    var showNoteSheet by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage by scannerViewModel.errorMessage.collectAsState()


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val requestSinglePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        handleActionWithPermission(
            context = context,
            requestSinglePermissionLauncher = requestSinglePermissionLauncher,
            permission = Manifest.permission.CAMERA,
            action = { hasCameraPermission = true },
            showRationale = { showPermissionDialog = true }
        )
    }

    if (errorMessage != null) {
        LaunchedEffect(errorMessage) {
            snackbarHostState.showSnackbar(
                message = errorMessage ?: "Unbekannter Fehler",
                withDismissAction = true
            )
            scannerViewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (hasCameraPermission) {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                analyzer = scannerViewModel.getAnalyzer()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
        }

        ScannerOverlay()

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .height(100.dp)
                .width(300.dp)
                .border(2.dp, Color.Green, shape = RoundedCornerShape(4.dp))
                .background(Color.Transparent)
        )

        ScannerBottomInfo(
            scannedIsbn = scannedIsbn,
            book = book,
            onSaveClick = { scannerViewModel.addBookToLibrary() },
            onAddNoteClick = {
                scannerViewModel.addBookToLibrary()
                showNoteSheet = true
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp)
        )

        FloatingActionButton(
            onClick = { onNavigateToISBNScannerManually() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 70.dp, end = 20.dp),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "ISBN Nummer eintippen"
            )
        }

        if (showPermissionDialog) {
            PermissionExplanationDialog(
                onDismiss = { showPermissionDialog = false },
                onConfirm = {
                    showPermissionDialog = false
                    requestSinglePermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            )
        }

        if (showNoteSheet) {
            NoteInputBottomSheet(
                noteText = noteText,
                onNoteChange = { noteText = it },
                onSave = {
                    book?.let {
                        scannerViewModel.saveNote(it.id, noteText)
                    }
                    noteText = ""
                    showNoteSheet = false
                },
                onDismiss = {
                    noteText = ""
                    showNoteSheet = false
                },
                sheetState = sheetState
            )
        }
    }
}



