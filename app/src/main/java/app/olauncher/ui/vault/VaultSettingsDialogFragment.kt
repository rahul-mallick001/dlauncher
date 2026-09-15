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
import androidx.fragment.app.DialogFragment
import app.olauncher.R
import app.olauncher.helper.VaultManager
import com.google.android.material.switchmaterial.SwitchMaterial

class VaultSettingsDialogFragment : DialogFragment() {

    private lateinit var vaultManager: VaultManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_vault_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vaultManager = VaultManager(requireContext())

        val switchAutoSocial = view.findViewById<SwitchMaterial>(R.id.switchAutoSocialBlock)
        val switch3FA = view.findViewById<SwitchMaterial>(R.id.switch3FA)
        val switchQA = view.findViewById<SwitchMaterial>(R.id.switchQA)

        val btnSetPin = view.findViewById<Button>(R.id.btnSetPin)
        val btnSetPattern = view.findViewById<Button>(R.id.btnSetPattern)
        val btnManageTodos = view.findViewById<Button>(R.id.btnManageTodos)
        val btnClose = view.findViewById<Button>(R.id.btnCloseVaultSettings)

        switchAutoSocial.isChecked = vaultManager.autoBlockSocialMedia
        switch3FA.isChecked = vaultManager.is3FAEnabled
        switchQA.isChecked = vaultManager.isQAEnabled

        switchAutoSocial.setOnCheckedChangeListener { _, isChecked ->
            vaultManager.autoBlockSocialMedia = isChecked
        }

        switch3FA.setOnCheckedChangeListener { _, isChecked ->
            vaultManager.is3FAEnabled = isChecked
        }

        switchQA.setOnCheckedChangeListener { _, isChecked ->
            vaultManager.isQAEnabled = isChecked
        }

        btnSetPin.setOnClickListener {
            SetPinDialogFragment.newInstance().show(parentFragmentManager, "SET_PIN")
        }

        btnSetPattern.setOnClickListener {
            SetPatternDialogFragment.newInstance().show(parentFragmentManager, "SET_PATTERN")
        }

        btnManageTodos.setOnClickListener {
            TodoListDialogFragment.newInstance().show(parentFragmentManager, "TODO_LIST")
        }

        btnClose.setOnClickListener {
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
        fun newInstance(): VaultSettingsDialogFragment = VaultSettingsDialogFragment()
    }
}
