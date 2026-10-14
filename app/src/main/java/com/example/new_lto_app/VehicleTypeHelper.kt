package com.example.new_lto_app


import android.app.Activity
import android.app.AlertDialog
import android.widget.RadioGroup

object VehicleTypeHelper {

    const val MOTORCYCLE = "MOTORCYCLE"
    const val LIGHT = "LIGHT"

    fun showPicker(activity: Activity, onChosen: (vehicleType: String) -> Unit) {
        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_vehicle_type, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroupVehicleType)

        val dialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .create()

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val chosen = when (checkedId) {
                R.id.radioMotorcycle -> MOTORCYCLE
                else -> LIGHT
            }
            dialog.dismiss()
            onChosen(chosen)
        }

        dialog.show()
    }
}