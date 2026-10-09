package com.example.new_lto_app


import android.app.Activity
import android.app.AlertDialog
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

// Shows the English / Filipino picker dialog and actually switches the app's
// language using AndroidX's per-app language support. The chosen language is
// remembered automatically — you don't need to save it yourself.
object LanguageHelper {

    fun showPicker(activity: Activity) {
        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_language, null)
        val radioEnglish = dialogView.findViewById<RadioButton>(R.id.radioEnglish)
        val radioFilipino = dialogView.findViewById<RadioButton>(R.id.radioFilipino)

        // Pre-select whichever language is currently active
        val currentLocales = AppCompatDelegate.getApplicationLocales()
        if (!currentLocales.isEmpty && currentLocales.toLanguageTags().startsWith("fil")) {
            radioFilipino.isChecked = true
        } else {
            radioEnglish.isChecked = true
        }

        val dialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .create()

        radioEnglish.setOnClickListener {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            dialog.dismiss()
            // The Activity automatically recreates itself when the locale changes,
            // so any strings pulled from res/values/strings.xml will update.
        }

        radioFilipino.setOnClickListener {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("fil"))
            dialog.dismiss()
        }

        dialog.show()
    }
}
