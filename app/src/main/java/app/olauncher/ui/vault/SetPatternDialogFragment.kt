package app.olauncher.ui.vault

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import app.olauncher.R
import app.olauncher.helper.VaultManager

class SetPatternDialogFragment : DialogFragment() {

    private lateinit var vaultManager: VaultManager
    private var firstPattern: String? = null
    private lateinit var tvInstruction: TextView
    private lateinit var patternView: PatternLockView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_set_pattern, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vaultManager = VaultManager(requireContext())

        tvInstruction = view.findViewById(R.id.tvPatternInstruction)
        patternView = view.findViewById(R.id.setPatternLockView)
        val btnReset = view.findViewById<Button>(R.id.btnResetPattern)
        val btnCancel = view.findViewById<Button>(R.id.btnCancelPattern)

        tvInstruction.text = "Draw a pattern connecting at least 4 dots"

        patternView.setOnPatternListener(object : PatternLockView.OnPatternListener {
            override fun onPatternEntered(pattern: String) {
                if (pattern.length < 4) {
                    patternView.setError(true)
                    tvInstruction.text = "Pattern too short (minimum 4 dots). Try again."
                    patternView.postDelayed({ patternView.clearPattern() }, 1000)
                    return
                }

                if (firstPattern == null) {
                    firstPattern = pattern
                    tvInstruction.text = "Pattern recorded! Draw it again to confirm."
                    patternView.clearPattern()
                } else {
                    if (firstPattern == pattern) {
                        vaultManager.masterPattern = pattern
                        Toast.makeText(requireContext(), "Master Pattern saved successfully", Toast.LENGTH_SHORT).show()
                        dismiss()
                    } else {
                        patternView.setError(true)
                        tvInstruction.text = "Patterns do not match. Try again."
                        patternView.postDelayed({
                            firstPattern = null
                            tvInstruction.text = "Draw a pattern connecting at least 4 dots"
                            patternView.clearPattern()
                        }, 1200)
                    }
                }
            }
        })

        btnReset.setOnClickListener {
            firstPattern = null
            tvInstruction.text = "Draw a pattern connecting at least 4 dots"
            patternView.clearPattern()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        fun newInstance(): SetPatternDialogFragment = SetPatternDialogFragment()
    }
}
