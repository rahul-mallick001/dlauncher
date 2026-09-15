package app.olauncher.helper

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import app.olauncher.R

object FrictionHelper {

    private val mindfulQuotes = listOf(
        "Take a mindful breath before opening.",
        "Is this intentional or just a habit?",
        "Pause and notice how you feel right now.",
        "Your focus is your most valuable asset.",
        "Spend your time on what truly matters."
    )

    fun showMindfulPauseDialog(
        context: Context,
        appName: String,
        durationSeconds: Int = 5,
        onProceed: () -> Unit,
        onCancel: () -> Unit = {}
    ) {
        val quote = mindfulQuotes.random()
        var countDownTimer: CountDownTimer? = null

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_friction_pause, null)
        val tvTitle = dialogView.findViewById<TextView>(R.id.tvFrictionTitle)
        val tvQuote = dialogView.findViewById<TextView>(R.id.tvFrictionQuote)
        val tvCountdown = dialogView.findViewById<TextView>(R.id.tvFrictionCountdown)
        val progressBar = dialogView.findViewById<ProgressBar>(R.id.progressBarFriction)
        val btnOpen = dialogView.findViewById<Button>(R.id.btnProceed)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        tvTitle.text = "Opening $appName"
        tvQuote.text = quote
        btnOpen.isEnabled = false
        btnOpen.alpha = 0.5f

        val totalMillis = durationSeconds * 1000L
        progressBar.max = durationSeconds * 100

        val dialog = MaterialAlertDialogBuilder(context)
            .setView(dialogView)
            .setCancelable(true)
            .setOnCancelListener {
                countDownTimer?.cancel()
                onCancel()
            }
            .create()

        countDownTimer = object : CountDownTimer(totalMillis, 50) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000) + 1
                val progress = ((totalMillis - millisUntilFinished).toFloat() / totalMillis * 100 * durationSeconds).toInt()
                progressBar.progress = progress
                tvCountdown.text = "${secondsRemaining}s"
                btnOpen.text = "Open (${secondsRemaining}s)"
            }

            override fun onFinish() {
                progressBar.progress = progressBar.max
                tvCountdown.text = "Ready"
                btnOpen.text = "Open Anyway"
                btnOpen.isEnabled = true
                btnOpen.alpha = 1.0f
            }
        }.start()

        btnOpen.setOnClickListener {
            dialog.dismiss()
            onProceed()
        }

        btnCancel.setOnClickListener {
            countDownTimer.cancel()
            dialog.dismiss()
            onCancel()
        }

        dialog.show()
    }
}
