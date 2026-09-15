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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.olauncher.R
import app.olauncher.helper.VaultManager

class VaultChallengeDialogFragment : DialogFragment() {

    interface OnUnlockListener {
        fun onUnlocked()
    }

    private var unlockListener: OnUnlockListener? = null
    private var appName: String = "Restricted App"
    private var appPackage: String = ""

    private lateinit var vaultManager: VaultManager

    // UI containers
    private lateinit var tvTargetApp: TextView
    private lateinit var tvStageHeader: TextView
    private lateinit var containerStage1: LinearLayout
    private lateinit var containerStage2: LinearLayout
    private lateinit var containerStage3: LinearLayout
    private lateinit var containerStage4: LinearLayout
    private lateinit var containerStage5: LinearLayout

    // Stage 1
    private lateinit var etScreenTimeInput: EditText
    private lateinit var btnSubmitScreenTime: Button

    // Stage 2
    private lateinit var rvChallengeTodos: RecyclerView
    private lateinit var btnSubmitTodos: Button
    private lateinit var todoAdapter: TodoAdapter

    // Stage 3
    private lateinit var btnScanBiometrics: Button
    private lateinit var tvBiometricDesc: TextView

    // Stage 4
    private lateinit var vaultPatternLockView: PatternLockView
    private lateinit var tvPatternPrompt: TextView

    // Stage 5
    private lateinit var tvPinDisplay: TextView
    private lateinit var tvPinStatus: TextView
    private var enteredPin: StringBuilder = StringBuilder()

    // Step sequence
    private val activeStages = mutableListOf<Int>()
    private var currentStageIndex = 0

    fun setOnUnlockListener(listener: OnUnlockListener) {
        this.unlockListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            appName = it.getString(ARG_APP_NAME, "Restricted App")
            appPackage = it.getString(ARG_APP_PACKAGE, "")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_vault_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vaultManager = VaultManager(requireContext())

        initViews(view)
        setupStages()
        showStage(0)
    }

    private fun initViews(view: View) {
        tvTargetApp = view.findViewById(R.id.tvVaultTargetApp)
        tvStageHeader = view.findViewById(R.id.tvVaultStageHeader)
        containerStage1 = view.findViewById(R.id.containerStage1)
        containerStage2 = view.findViewById(R.id.containerStage2)
        containerStage3 = view.findViewById(R.id.containerStage3)
        containerStage4 = view.findViewById(R.id.containerStage4)
        containerStage5 = view.findViewById(R.id.containerStage5)

        tvTargetApp.text = "RESTRICTED: $appName"

        // Stage 1
        etScreenTimeInput = view.findViewById(R.id.etScreenTimeInput)
        btnSubmitScreenTime = view.findViewById(R.id.btnSubmitScreenTime)
        btnSubmitScreenTime.setOnClickListener { handleScreenTimeSubmit() }

        // Stage 2
        rvChallengeTodos = view.findViewById(R.id.rvChallengeTodos)
        btnSubmitTodos = view.findViewById(R.id.btnSubmitTodos)
        rvChallengeTodos.layoutManager = LinearLayoutManager(requireContext())
        todoAdapter = TodoAdapter(
            items = vaultManager.getTodoList(),
            onItemToggled = { item -> vaultManager.toggleTodo(item.id) }
        )
        rvChallengeTodos.adapter = todoAdapter
        btnSubmitTodos.setOnClickListener { handleTodosSubmit() }

        // Stage 3
        btnScanBiometrics = view.findViewById(R.id.btnScanBiometrics)
        tvBiometricDesc = view.findViewById(R.id.tvBiometricDesc)
        btnScanBiometrics.setOnClickListener { triggerBiometricPrompt() }

        // Stage 4
        vaultPatternLockView = view.findViewById(R.id.vaultPatternLockView)
        tvPatternPrompt = view.findViewById(R.id.tvPatternPrompt)
        vaultPatternLockView.setOnPatternListener(object : PatternLockView.OnPatternListener {
            override fun onPatternEntered(pattern: String) {
                handlePatternSubmit(pattern)
            }
        })

        // Stage 5
        tvPinDisplay = view.findViewById(R.id.tvPinDisplay)
        tvPinStatus = view.findViewById(R.id.tvPinStatus)
        setupPinPad(view)

        // Cancel button
        view.findViewById<Button>(R.id.btnVaultCancel).setOnClickListener {
            Toast.makeText(requireContext(), "Access cancelled. Stay focused!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun setupStages() {
        activeStages.clear()
        if (vaultManager.isQAEnabled) {
            activeStages.add(1) // Q1 Screen Time
            activeStages.add(2) // Q2 Todo check
        }
        if (vaultManager.is3FAEnabled) {
            activeStages.add(3) // Biometric
            activeStages.add(4) // Pattern
        }
        activeStages.add(5) // Master PIN always required
    }

    private fun showStage(index: Int) {
        if (index >= activeStages.size) {
            // All stages completed!
            grantAccess()
            return
        }

        currentStageIndex = index
        val stageNumber = activeStages[index]
        val totalStages = activeStages.size
        val currentDisplayNumber = index + 1

        containerStage1.visibility = View.GONE
        containerStage2.visibility = View.GONE
        containerStage3.visibility = View.GONE
        containerStage4.visibility = View.GONE
        containerStage5.visibility = View.GONE

        when (stageNumber) {
            1 -> {
                tvStageHeader.text = "STAGE $currentDisplayNumber OF $totalStages: SCREEN TIME AWARENESS"
                containerStage1.visibility = View.VISIBLE
                etScreenTimeInput.text.clear()
                etScreenTimeInput.requestFocus()
            }
            2 -> {
                tvStageHeader.text = "STAGE $currentDisplayNumber OF $totalStages: DAILY PRIORITIES"
                containerStage2.visibility = View.VISIBLE
                todoAdapter.updateList(vaultManager.getTodoList())
            }
            3 -> {
                tvStageHeader.text = "STAGE $currentDisplayNumber OF $totalStages: BIOMETRIC AUTH"
                containerStage3.visibility = View.VISIBLE
                triggerBiometricPrompt()
            }
            4 -> {
                tvStageHeader.text = "STAGE $currentDisplayNumber OF $totalStages: SECURITY PATTERN"
                containerStage4.visibility = View.VISIBLE
                vaultPatternLockView.clearPattern()
            }
            5 -> {
                tvStageHeader.text = "STAGE $currentDisplayNumber OF $totalStages: MASTER PIN"
                containerStage5.visibility = View.VISIBLE
                enteredPin.clear()
                updatePinDisplay()
            }
        }
    }

    private fun handleScreenTimeSubmit() {
        val input = etScreenTimeInput.text.toString().trim()
        val hours = input.toIntOrNull()
        if (hours == null) {
            Toast.makeText(requireContext(), "Please enter a valid number in hours", Toast.LENGTH_SHORT).show()
            return
        }

        val (isMatch, actualHours) = vaultManager.validateScreenTimeAnswer(hours)
        if (isMatch) {
            Toast.makeText(requireContext(), "Awareness confirmed. Proceeding...", Toast.LENGTH_SHORT).show()
            showStage(currentStageIndex + 1)
        } else {
            val totalMins = vaultManager.getTodayScreenTimeMinutes()
            val totalH = totalMins / 60
            val totalM = totalMins % 60
            Toast.makeText(
                requireContext(),
                "Access Denied: You entered ${hours}h, but today's usage is ${totalH}h ${totalM}m. Confront reality.",
                Toast.LENGTH_LONG
            ).show()
            dismiss()
        }
    }

    private fun handleTodosSubmit() {
        if (vaultManager.areAllTodosCompleted()) {
            Toast.makeText(requireContext(), "Daily priorities completed! Proceeding...", Toast.LENGTH_SHORT).show()
            showStage(currentStageIndex + 1)
        } else {
            Toast.makeText(
                requireContext(),
                "Access Denied: You have pending daily tasks. Finish them first.",
                Toast.LENGTH_LONG
            ).show()
            dismiss()
        }
    }

    private fun triggerBiometricPrompt() {
        val biometricManager = BiometricManager.from(requireContext())
        val canAuth = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)

        if (canAuth != BiometricManager.BIOMETRIC_SUCCESS) {
            // Device does not have enrolled biometrics, skip to next stage
            tvBiometricDesc.text = "Biometric not enrolled on device. Moving to next stage."
            btnScanBiometrics.text = "Proceed"
            btnScanBiometrics.setOnClickListener {
                showStage(currentStageIndex + 1)
            }
            return
        }

        val executor = ContextCompat.getMainExecutor(requireContext())
        val prompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(requireContext(), "Biometrics verified!", Toast.LENGTH_SHORT).show()
                showStage(currentStageIndex + 1)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_USER_CANCELED || errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    Toast.makeText(requireContext(), "Authentication cancelled", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(), "Biometric unrecognized. Try again.", Toast.LENGTH_SHORT).show()
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Vault Biometric Check")
            .setSubtitle("Confirm identity to access $appName")
            .setNegativeButtonText("Cancel")
            .build()

        prompt.authenticate(promptInfo)
    }

    private fun handlePatternSubmit(pattern: String) {
        if (vaultManager.verifyPattern(pattern)) {
            Toast.makeText(requireContext(), "Pattern verified!", Toast.LENGTH_SHORT).show()
            showStage(currentStageIndex + 1)
        } else {
            vaultPatternLockView.setError(true)
            Toast.makeText(requireContext(), "Incorrect pattern. Access denied.", Toast.LENGTH_SHORT).show()
            vaultPatternLockView.postDelayed({
                dismiss()
            }, 1000)
        }
    }

    private fun setupPinPad(view: View) {
        val keys = listOf(
            R.id.btnKey0 to "0", R.id.btnKey1 to "1", R.id.btnKey2 to "2",
            R.id.btnKey3 to "3", R.id.btnKey4 to "4", R.id.btnKey5 to "5",
            R.id.btnKey6 to "6", R.id.btnKey7 to "7", R.id.btnKey8 to "8",
            R.id.btnKey9 to "9"
        )

        for ((id, digit) in keys) {
            view.findViewById<Button>(id).setOnClickListener {
                if (enteredPin.length < 8) {
                    enteredPin.append(digit)
                    updatePinDisplay()
                    checkPin()
                }
            }
        }

        view.findViewById<Button>(R.id.btnKeyClear).setOnClickListener {
            enteredPin.clear()
            updatePinDisplay()
        }

        view.findViewById<Button>(R.id.btnKeyDel).setOnClickListener {
            if (enteredPin.isNotEmpty()) {
                enteredPin.deleteCharAt(enteredPin.length - 1)
                updatePinDisplay()
            }
        }
    }

    private fun updatePinDisplay() {
        val len = enteredPin.length
        if (len == 0) {
            tvPinDisplay.text = "• • • •"
        } else {
            val masked = StringBuilder()
            for (i in 0 until len) {
                masked.append("● ")
            }
            tvPinDisplay.text = masked.toString().trim()
        }
    }

    private fun checkPin() {
        val pin = enteredPin.toString()
        if (pin.length >= vaultManager.masterPin.length) {
            if (vaultManager.verifyPin(pin)) {
                Toast.makeText(requireContext(), "Master PIN accepted!", Toast.LENGTH_SHORT).show()
                grantAccess()
            } else {
                tvPinStatus.text = "Incorrect PIN. Access Denied."
                tvPinStatus.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_light))
                Toast.makeText(requireContext(), "Incorrect Master PIN.", Toast.LENGTH_SHORT).show()
                tvPinDisplay.postDelayed({
                    dismiss()
                }, 800)
            }
        }
    }

    private fun grantAccess() {
        Toast.makeText(requireContext(), "Vault Unlocked: Proceeding...", Toast.LENGTH_SHORT).show()
        unlockListener?.onUnlocked()
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.92).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        private const val ARG_APP_NAME = "ARG_APP_NAME"
        private const val ARG_APP_PACKAGE = "ARG_APP_PACKAGE"

        fun newInstance(appName: String, appPackage: String): VaultChallengeDialogFragment {
            return VaultChallengeDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_APP_NAME, appName)
                    putString(ARG_APP_PACKAGE, appPackage)
                }
            }
        }
    }
}
