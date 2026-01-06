package com.example.examenfinal

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import org.osmdroid.util.GeoPoint
import com.example.examenfinal.theme.GeoTrackerTheme
import com.example.examenfinal.tracking.TrackingInterval
import com.example.examenfinal.tracking.TrackingViewModel
import com.example.examenfinal.ui.OsmMap
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GeoTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val nav = rememberNavController()
                    NavHost(navController = nav, startDestination = "main") {
                        composable("main") { MainScreen(onGoHistory = { nav.navigate("history") }) }
                        composable("history") { HistoryScreen(onBack = { nav.popBackStack() }) }
                    }
                }
            }
        }
    }
}

@Composable
private fun MainScreen(
    onGoHistory: () -> Unit,
    vm: TrackingViewModel = viewModel()
) {
    val ui by vm.ui.collectAsState()
    val ctx = LocalContext.current

    val permLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { res: Map<String, Boolean> ->
        val granted = (res[Manifest.permission.ACCESS_FINE_LOCATION] == true) ||
                (res[Manifest.permission.ACCESS_COARSE_LOCATION] == true)
        vm.setPermission(granted)
    }

    LaunchedEffect(Unit) {
        val fine = ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)
        vm.setPermission(fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED)
    }

    fun requestNeededPermissions() {
        val perms = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= 33) {
            perms.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        permLauncher.launch(perms.toTypedArray())
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onGoHistory,
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text("Log", modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

            val current = remember(ui.lat, ui.lon) {
                if (ui.lat != null && ui.lon != null) GeoPoint(ui.lat!!, ui.lon!!) else null
            }
            OsmMap(
                modifier = Modifier.fillMaxSize(),
                routePoints = ui.route,
                currentPoint = current
            )

            StatusCapsule(
                isTracking = ui.isTracking,
                points = ui.route.size,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp)
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                shadowElevation = 10.dp,
                tonalElevation = 2.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
                    )
                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(label = "Latitud", value = ui.lat?.let { String.format("%.5f", it) } ?: "--")
                        StatItem(label = "Longitud", value = ui.lon?.let { String.format("%.5f", it) } ?: "--")
                        StatItem(label = "Precisión", value = ui.accuracyMeters?.let { "${it.toInt()} m" } ?: "--")
                    }

                    Spacer(Modifier.height(16.dp))
                    Divider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IntervalSelectorCompact(selected = ui.interval, onSelect = vm::setInterval)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Notif.", style = MaterialTheme.typography.labelLarge)
                            Spacer(Modifier.width(8.dp))
                            Switch(
                                checked = ui.notifEnabled,
                                onCheckedChange = vm::setNotifEnabled,
                                modifier = Modifier.scale(0.8f)
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                if (!ui.hasLocationPermission) {
                                    requestNeededPermissions()
                                } else {
                                    vm.startBackgroundService()
                                }
                            },
                            enabled = !ui.isTracking,
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("INICIAR RASTREO")
                        }

                        Button(
                            onClick = vm::stopBackgroundService,
                            enabled = ui.isTracking,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("DETENER")
                        }
                    }

                    if (ui.route.isNotEmpty()) {
                        TextButton(onClick = vm::clearHistory) {
                            Text("Limpiar Mapa actual", color = MaterialTheme.colorScheme.outline)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCapsule(isTracking: Boolean, points: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50), // Forma de cápsula
        color = if (isTracking) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        if (isTracking) Color(0xFF4CAF50) else Color.Gray,
                        shape = RoundedCornerShape(50)
                    )
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = if (isTracking) "GRABANDO RUTA" else "EN ESPERA",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.width(12.dp))
            Box(Modifier.width(1.dp).height(12.dp).background(MaterialTheme.colorScheme.onSurface.copy(alpha=0.3f)))
            Spacer(Modifier.width(12.dp))
            Text(
                text = "$points ptos",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
        Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IntervalSelectorCompact(
    selected: TrackingInterval,
    onSelect: (TrackingInterval) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        FilterChip(
            selected = true,
            onClick = { expanded = true },
            label = { Text("Frecuencia: ${selected.label}") },
            trailingIcon = { Text("▼", fontSize = 10.sp) } // Flechita simple
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TrackingInterval.values().forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt.label) },
                    onClick = {
                        onSelect(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IntervalSelector(
    selected: TrackingInterval,
    onSelect: (TrackingInterval) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Frecuencia:")
        Spacer(Modifier.width(10.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selected.label,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor(),
                label = { Text("Segundos") }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                TrackingInterval.values().forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt.label) },
                        onClick = {
                            onSelect(opt)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryScreen(
    onBack: () -> Unit,
    vm: TrackingViewModel = viewModel()
) {
    val ui by vm.ui.collectAsState()
    val df = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onBack) { Text("Regresar") }
            Spacer(Modifier.width(10.dp))
            Text("Historial de Ruta", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(10.dp))

        // AQUI ESTABA EL ERROR: Ahora funcionará gracias al import de arriba
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(ui.historyDesc) { item ->
                val dt = df.format(Date(item.timestampMillis))
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(dt, style = MaterialTheme.typography.titleMedium)
                        Text("Lat: ${item.lat}")
                        Text("Lon: ${item.lon}")
                        Text("Error estimado: ${item.accuracyMeters} m")
                    }
                }
            }
        }
    }
}