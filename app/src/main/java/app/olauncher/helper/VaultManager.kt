package app.olauncher.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import app.olauncher.helper.usageStats.EventLogWrapper
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.abs

data class TodoItem(
    val id: String,
    val title: String,
    var isCompleted: Boolean
)

class VaultManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app.olauncher.vault", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_MASTER_PIN = "KEY_MASTER_PIN"
        private const val KEY_MASTER_PATTERN = "KEY_MASTER_PATTERN"
        private const val KEY_3FA_ENABLED = "KEY_3FA_ENABLED"
        private const val KEY_QA_ENABLED = "KEY_QA_ENABLED"
        private const val KEY_AUTO_BLOCK_SOCIAL = "KEY_AUTO_BLOCK_SOCIAL"
        private const val KEY_VAULT_APPS = "KEY_VAULT_APPS"
        private const val KEY_TODO_LIST_JSON = "KEY_TODO_LIST_JSON"
        private const val KEY_VAULT_INITIALIZED = "KEY_VAULT_INITIALIZED"

        val SOCIAL_MEDIA_PACKAGES = setOf(
            "com.instagram.android",
            "com.instagram.lite",
            "com.instagram.barcelona",
            "com.zhiliaoapp.musically",
            "com.ss.android.ugc.trill",
            "com.bytedance.tiktok",
            "com.facebook.katana",
            "com.facebook.orca",
            "com.facebook.lite",
            "com.twitter.android",
            "com.twitter.android.lite",
            "com.snapchat.android",
            "com.google.android.youtube",
            "com.google.android.apps.youtube.music",
            "com.reddit.frontpage",
            "com.pinterest",
            "com.netflix.mediaclient",
            "tv.twitch.android.app",
            "com.discord",
            "com.tinder",
            "com.bumble.app",
            "com.hinge.app"
        )
    }

    var isVaultInitialized: Boolean
        get() = prefs.getBoolean(KEY_VAULT_INITIALIZED, false)
        set(value) = prefs.edit { putBoolean(KEY_VAULT_INITIALIZED, value).apply() }

    var masterPin: String
        get() = prefs.getString(KEY_MASTER_PIN, "0000").toString()
        set(value) = prefs.edit {
            putString(KEY_MASTER_PIN, value).apply()
            putBoolean(KEY_VAULT_INITIALIZED, true).apply()
        }

    var masterPattern: String
        get() = prefs.getString(KEY_MASTER_PATTERN, "01258").toString()
        set(value) = prefs.edit {
            putString(KEY_MASTER_PATTERN, value).apply()
            putBoolean(KEY_VAULT_INITIALIZED, true).apply()
        }

    var is3FAEnabled: Boolean
        get() = prefs.getBoolean(KEY_3FA_ENABLED, true)
        set(value) = prefs.edit { putBoolean(KEY_3FA_ENABLED, value).apply() }

    var isQAEnabled: Boolean
        get() = prefs.getBoolean(KEY_QA_ENABLED, true)
        set(value) = prefs.edit { putBoolean(KEY_QA_ENABLED, value).apply() }

    var autoBlockSocialMedia: Boolean
        get() = prefs.getBoolean(KEY_AUTO_BLOCK_SOCIAL, true)
        set(value) = prefs.edit { putBoolean(KEY_AUTO_BLOCK_SOCIAL, value).apply() }

    var vaultLockedApps: MutableSet<String>
        get() = prefs.getStringSet(KEY_VAULT_APPS, mutableSetOf()) as MutableSet<String>
        set(value) = prefs.edit { putStringSet(KEY_VAULT_APPS, value).apply() }

    fun isAppProtected(packageName: String): Boolean {
        if (vaultLockedApps.contains(packageName)) return true
        if (autoBlockSocialMedia && SOCIAL_MEDIA_PACKAGES.contains(packageName)) return true
        return false
    }

    fun setAppVaultLocked(packageName: String, locked: Boolean) {
        val set = vaultLockedApps.toMutableSet()
        if (locked) set.add(packageName) else set.remove(packageName)
        vaultLockedApps = set
    }

    fun verifyPin(pin: String): Boolean = pin == masterPin

    fun verifyPattern(pattern: String): Boolean = pattern == masterPattern

    fun getTodayScreenTimeMinutes(): Int {
        return try {
            val eventLog = EventLogWrapper(context)
            val stats = eventLog.getForegroundStatsByRelativeDay(0)
            val usage = eventLog.aggregateForegroundStats(stats)
            (eventLog.aggregateSimpleUsageStats(usage) / 60000L).toInt()
        } catch (_: Exception) {
            0
        }
    }

    fun getTodayScreenTimeHours(): Int = getTodayScreenTimeMinutes() / 60

    fun validateScreenTimeAnswer(enteredHours: Int): Pair<Boolean, Int> {
        val actualMinutes = getTodayScreenTimeMinutes()
        val actualHours = actualMinutes / 60
        val remainingMinutes = actualMinutes % 60

        // Exact hour match or acceptable boundary tolerance if close to hour change (e.g. 1h 55m allows 1 or 2)
        val isMatch = if (enteredHours == actualHours) {
            true
        } else if (remainingMinutes >= 45 && enteredHours == actualHours + 1) {
            true
        } else {
            false
        }
        return Pair(isMatch, actualHours)
    }

    fun getTodoList(): List<TodoItem> {
        val jsonString = prefs.getString(KEY_TODO_LIST_JSON, null)
        if (jsonString.isNullOrEmpty()) {
            // Default starter todos
            return listOf(
                TodoItem("1", "Read for 30 minutes", false),
                TodoItem("2", "Physical workout / walk", false),
                TodoItem("3", "Focus on top daily priority", false)
            )
        }
        return try {
            val list = mutableListOf<TodoItem>()
            val array = JSONArray(jsonString)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    TodoItem(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        isCompleted = obj.getBoolean("isCompleted")
                    )
                )
            }
            list
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun saveTodoList(todos: List<TodoItem>) {
        val array = JSONArray()
        for (todo in todos) {
            val obj = JSONObject().apply {
                put("id", todo.id)
                put("title", todo.title)
                put("isCompleted", todo.isCompleted)
            }
            array.put(obj)
        }
        prefs.edit { putString(KEY_TODO_LIST_JSON, array.toString()).apply() }
    }

    fun addTodo(title: String) {
        if (title.isBlank()) return
        val current = getTodoList().toMutableList()
        current.add(TodoItem(System.currentTimeMillis().toString(), title.trim(), false))
        saveTodoList(current)
    }

    fun toggleTodo(id: String) {
        val current = getTodoList().toMutableList()
        val item = current.find { it.id == id }
        if (item != null) {
            item.isCompleted = !item.isCompleted
            saveTodoList(current)
        }
    }

    fun removeTodo(id: String) {
        val current = getTodoList().toMutableList()
        current.removeAll { it.id == id }
        saveTodoList(current)
    }

    fun areAllTodosCompleted(): Boolean {
        val todos = getTodoList()
        if (todos.isEmpty()) return true
        return todos.all { it.isCompleted }
    }
}
