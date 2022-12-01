package ca.unb.mobiledev.gittogetherui.ui.home
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ca.unb.mobiledev.gittogetherui.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class CreateProjectActivity : Activity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var titleEntry: TextInputEditText
    private lateinit var descEntry: TextInputEditText
    private lateinit var locationEntry: TextInputEditText
    private lateinit var tagDropdown: Spinner
    private lateinit var tagLayout: LinearLayout

    var context = this

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var back = findViewById<Button>(R.id.create_back_button)
        back.setOnClickListener {
            finish()
        }
        var cancel = findViewById<Button>(R.id.cancel_project_button)
        cancel.setOnClickListener {
            finish()
        }

        titleEntry = findViewById(R.id.title_field)
        descEntry = findViewById(R.id.desc_field)
        locationEntry = findViewById(R.id.location_field)
        tagDropdown = findViewById(R.id.tag_dropdown)
        tagLayout = findViewById(R.id.tag_layout)


        locationEntry.hint = "city, province/state, country"

        getCurrentLocation()
    }

    @SuppressLint("SetTextI18n")
    fun getCurrentLocation() {
        if (androidx.core.app.ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && androidx.core.app.ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@CreateProjectActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@CreateProjectActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@CreateProjectActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location == null)
                    Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    GlobalScope.launch(Dispatchers.IO) {
                        val lat = location.latitude
                        val lon = location.longitude

                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
                        val cityName: String = addresses[0].locality
                        val stateName: String = addresses[0].adminArea
                        val countryName: String = addresses[0].countryName

                        withContext(Dispatchers.Main) {
                            locationEntry.setText("$cityName, $stateName, $countryName")
                        }
                    }
                }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@CreateProjectActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}
