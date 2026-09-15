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
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import app.olauncher.R
import app.olauncher.helper.VaultManager

class SetPinDialogFragment : DialogFragment() {

    private lateinit var vaultManager: VaultManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_set_pin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vaultManager = VaultManager(requireContext())

        val etNewPin = view.findViewById<EditText>(R.id.etNewPin)
        val etConfirmPin = view.findViewById<EditText>(R.id.etConfirmPin)
        val btnSavePin = view.findViewById<Button>(R.id.btnSavePin)
        val btnCancelPin = view.findViewById<Button>(R.id.btnCancelPin)

        btnSavePin.setOnClickListener {
            val pin1 = etNewPin.text.toString().trim()
            val pin2 = etConfirmPin.text.toString().trim()

            if (pin1.length < 4) {
                Toast.makeText(requireContext(), "PIN must be at least 4 digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pin1 != pin2) {
                Toast.makeText(requireContext(), "PINs do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            vaultManager.masterPin = pin1
            Toast.makeText(requireContext(), "Master PIN saved successfully", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        btnCancelPin.setOnClickListener {
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
        fun newInstance(): SetPinDialogFragment = SetPinDialogFragment()
    }
}
