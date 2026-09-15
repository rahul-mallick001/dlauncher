package app.olauncher.data

import android.os.UserHandle
import java.text.CollationKey

sealed class AppModel : Comparable<AppModel> {
    abstract val appLabel: String
    abstract val key: CollationKey?
    abstract val appPackage: String
    abstract val user: UserHandle
    abstract val isNew: Boolean
    open val isBiometricLocked: Boolean = false
    open val isFrictionEnabled: Boolean = false
    open val categoryTag: String? = null

    data class App(
        override val appLabel: String,
        override val key: CollationKey?,
        override val appPackage: String,
        val activityClassName: String?,
        override val isNew: Boolean = false,
        override val user: UserHandle,
        override val isBiometricLocked: Boolean = false,
        override val isFrictionEnabled: Boolean = false,
        override val categoryTag: String? = null
    ) : AppModel()

    data class PinnedShortcut(
        override val appLabel: String,
        override val key: CollationKey?,
        override val appPackage: String,
        val shortcutId: String,
        override val isNew: Boolean = false,
        override val user: UserHandle,
        override val isBiometricLocked: Boolean = false,
        override val isFrictionEnabled: Boolean = false,
        override val categoryTag: String? = null
    ) : AppModel() {
        val identity: String
            get() = shortcutIdentity(appPackage, shortcutId, user.toString())
    }

    data class CalculationResult(
        val expression: String,
        val result: String,
        override val user: UserHandle = android.os.Process.myUserHandle()
    ) : AppModel() {
        override val appLabel: String = "$expression $result"
        override val key: CollationKey? = null
        override val appPackage: String = ""
        override val isNew: Boolean = false
    }

    data class PrivateSpaceHeader(
        val isLocked: Boolean = true,
        override val user: UserHandle = android.os.Process.myUserHandle(),
    ) : AppModel() {
        override val appLabel: String = ""
        override val key: CollationKey? = null
        override val appPackage: String = ""
        override val isNew: Boolean = false
    }

    override fun compareTo(other: AppModel): Int = when {
        this is CalculationResult -> -1
        other is CalculationResult -> 1
        key != null && other.key != null -> key!!.compareTo(other.key)
        else -> appLabel.compareTo(other.appLabel, true)
    }
}
