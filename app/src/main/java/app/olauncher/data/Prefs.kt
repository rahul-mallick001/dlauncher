package app.olauncher.data

import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class Prefs(context: Context) {
    private val PREFS_FILENAME = "app.olauncher"

    private val FIRST_OPEN = "FIRST_OPEN"
    private val FIRST_OPEN_TIME = "FIRST_OPEN_TIME"
    private val FIRST_SETTINGS_OPEN = "FIRST_SETTINGS_OPEN"
    private val FIRST_HIDE = "FIRST_HIDE"
    private val USER_STATE = "USER_STATE"
    private val LOCK_MODE = "LOCK_MODE"
    private val HOME_APPS_NUM = "HOME_APPS_NUM"
    private val HOME_COLUMNS = "HOME_COLUMNS"
    private val AUTO_SHOW_KEYBOARD = "AUTO_SHOW_KEYBOARD"
    private val KEYBOARD_MESSAGE = "KEYBOARD_MESSAGE"
    private val DAILY_WALLPAPER = "DAILY_WALLPAPER"
    private val DAILY_WALLPAPER_URL = "DAILY_WALLPAPER_URL"
    private val HOME_ALIGNMENT = "HOME_ALIGNMENT"
    private val HOME_BOTTOM_ALIGNMENT = "HOME_BOTTOM_ALIGNMENT"
    private val APP_LABEL_ALIGNMENT = "APP_LABEL_ALIGNMENT"
    private val STATUS_BAR = "STATUS_BAR"
    private val DATE_TIME_VISIBILITY = "DATE_TIME_VISIBILITY"
    private val SWIPE_LEFT_ENABLED = "SWIPE_LEFT_ENABLED"
    private val SWIPE_RIGHT_ENABLED = "SWIPE_RIGHT_ENABLED"
    private val HIDDEN_APPS = "HIDDEN_APPS"
    private val HIDDEN_APPS_UPDATED = "HIDDEN_APPS_UPDATED"
    private val SHOW_HINT_COUNTER = "SHOW_HINT_COUNTER"
    private val APP_THEME = "APP_THEME"
    private val ACCENT_THEME = "ACCENT_THEME"
    private val FONT_FAMILY = "FONT_FAMILY"
    private val ABOUT_CLICKED = "ABOUT_CLICKED"
    private val RATE_CLICKED = "RATE_CLICKED"
    private val WALLPAPER_MSG_SHOWN = "WALLPAPER_MSG_SHOWN"
    private val SHARE_SHOWN_TIME = "SHARE_SHOWN_TIME"
    private val SWIPE_DOWN_ACTION = "SWIPE_DOWN_ACTION"
    private val TEXT_SIZE_SCALE = "TEXT_SIZE_SCALE"
    private val BOLD_FONT = "BOLD_FONT"
    private val PRO_MESSAGE_SHOWN = "PRO_MESSAGE_SHOWN"
    private val HIDE_SET_DEFAULT_LAUNCHER = "HIDE_SET_DEFAULT_LAUNCHER"
    private val SCREEN_TIME_LAST_UPDATED = "SCREEN_TIME_LAST_UPDATED"
    private val LAUNCHER_RESTART_TIMESTAMP = "LAUNCHER_RECREATE_TIMESTAMP"
    private val SHOWN_ON_DAY_OF_YEAR = "SHOWN_ON_DAY_OF_YEAR"
    private val AMOLED_LOCK_SCREEN_APPLIED = "AMOLED_LOCK_SCREEN_APPLIED"

    // Redesigned & New Feature Preferences
    private val FUZZY_SEARCH = "FUZZY_SEARCH"
    private val CALC_IN_SEARCH = "CALC_IN_SEARCH"
    private val SEARCH_SHORTCUTS = "SEARCH_SHORTCUTS"
    private val BIOMETRIC_LOCK_ENABLED = "BIOMETRIC_LOCK_ENABLED"
    private val BIOMETRIC_LOCKED_APPS = "BIOMETRIC_LOCKED_APPS"
    private val FRICTION_MODE_ENABLED = "FRICTION_MODE_ENABLED"
    private val FRICTION_DURATION = "FRICTION_DURATION"
    private val FRICTION_APPS = "FRICTION_APPS"
    private val CUSTOM_NOTE = "CUSTOM_NOTE"
    private val SHOW_CUSTOM_NOTE = "SHOW_CUSTOM_NOTE"

    private val APP_NAME_SWIPE_LEFT = "APP_NAME_SWIPE_LEFT"
    private val APP_NAME_SWIPE_RIGHT = "APP_NAME_SWIPE_RIGHT"
    private val APP_PACKAGE_SWIPE_LEFT = "APP_PACKAGE_SWIPE_LEFT"
    private val APP_PACKAGE_SWIPE_RIGHT = "APP_PACKAGE_SWIPE_RIGHT"
    private val APP_ACTIVITY_CLASS_NAME_SWIPE_LEFT = "APP_ACTIVITY_CLASS_NAME_SWIPE_LEFT"
    private val APP_ACTIVITY_CLASS_NAME_SWIPE_RIGHT = "APP_ACTIVITY_CLASS_NAME_SWIPE_RIGHT"
    private val APP_USER_SWIPE_LEFT = "APP_USER_SWIPE_LEFT"
    private val APP_USER_SWIPE_RIGHT = "APP_USER_SWIPE_RIGHT"
    private val CLOCK_APP_PACKAGE = "CLOCK_APP_PACKAGE"
    private val CLOCK_APP_USER = "CLOCK_APP_USER"
    private val CLOCK_APP_CLASS_NAME = "CLOCK_APP_CLASS_NAME"
    private val CALENDAR_APP_PACKAGE = "CALENDAR_APP_PACKAGE"
    private val CALENDAR_APP_USER = "CALENDAR_APP_USER"
    private val CALENDAR_APP_CLASS_NAME = "CALENDAR_APP_CLASS_NAME"
    private val SCREEN_TIME_APP_PACKAGE = "SCREEN_TIME_APP_PACKAGE"
    private val SCREEN_TIME_APP_USER = "SCREEN_TIME_APP_USER"
    private val SCREEN_TIME_APP_CLASS_NAME = "SCREEN_TIME_APP_CLASS_NAME"

    private val SHORTCUT_ID_SWIPE_LEFT = "SHORTCUT_ID_SWIPE_LEFT"
    private val IS_SHORTCUT_SWIPE_LEFT = "IS_SHORTCUT_SWIPE_LEFT"
    private val SHORTCUT_ID_SWIPE_RIGHT = "SHORTCUT_ID_SWIPE_RIGHT"
    private val IS_SHORTCUT_SWIPE_RIGHT = "IS_SHORTCUT_SWIPE_RIGHT"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var firstOpen: Boolean
        get() = prefs.getBoolean(FIRST_OPEN, true)
        set(value) = prefs.edit { putBoolean(FIRST_OPEN, value).apply() }

    var firstOpenTime: Long
        get() = prefs.getLong(FIRST_OPEN_TIME, 0L)
        set(value) = prefs.edit { putLong(FIRST_OPEN_TIME, value).apply() }

    var firstSettingsOpen: Boolean
        get() = prefs.getBoolean(FIRST_SETTINGS_OPEN, true)
        set(value) = prefs.edit { putBoolean(FIRST_SETTINGS_OPEN, value).apply() }

    var firstHide: Boolean
        get() = prefs.getBoolean(FIRST_HIDE, true)
        set(value) = prefs.edit { putBoolean(FIRST_HIDE, value).apply() }

    var userState: String
        get() = prefs.getString(USER_STATE, Constants.UserState.START).toString()
        set(value) = prefs.edit { putString(USER_STATE, value).apply() }

    var lockModeOn: Boolean
        get() = prefs.getBoolean(LOCK_MODE, false)
        set(value) = prefs.edit { putBoolean(LOCK_MODE, value).apply() }

    var autoShowKeyboard: Boolean
        get() = prefs.getBoolean(AUTO_SHOW_KEYBOARD, true)
        set(value) = prefs.edit { putBoolean(AUTO_SHOW_KEYBOARD, value).apply() }

    var keyboardMessageShown: Boolean
        get() = prefs.getBoolean(KEYBOARD_MESSAGE, false)
        set(value) = prefs.edit { putBoolean(KEYBOARD_MESSAGE, value).apply() }

    var dailyWallpaper: Boolean
        get() = prefs.getBoolean(DAILY_WALLPAPER, false)
        set(value) = prefs.edit { putBoolean(DAILY_WALLPAPER, value).apply() }

    var dailyWallpaperUrl: String
        get() = prefs.getString(DAILY_WALLPAPER_URL, "").toString()
        set(value) = prefs.edit { putString(DAILY_WALLPAPER_URL, value).apply() }

    var homeAppsNum: Int
        get() = prefs.getInt(HOME_APPS_NUM, 4)
        set(value) = prefs.edit { putInt(HOME_APPS_NUM, value).apply() }

    var homeColumns: Int
        get() = prefs.getInt(HOME_COLUMNS, 1)
        set(value) = prefs.edit { putInt(HOME_COLUMNS, value).apply() }

    var homeAlignment: Int
        get() = prefs.getInt(HOME_ALIGNMENT, Gravity.START)
        set(value) = prefs.edit { putInt(HOME_ALIGNMENT, value).apply() }

    var homeBottomAlignment: Boolean
        get() = prefs.getBoolean(HOME_BOTTOM_ALIGNMENT, false)
        set(value) = prefs.edit { putBoolean(HOME_BOTTOM_ALIGNMENT, value).apply() }

    var appLabelAlignment: Int
        get() = prefs.getInt(APP_LABEL_ALIGNMENT, Gravity.START)
        set(value) = prefs.edit { putInt(APP_LABEL_ALIGNMENT, value).apply() }

    var showStatusBar: Boolean
        get() = prefs.getBoolean(STATUS_BAR, false)
        set(value) = prefs.edit { putBoolean(STATUS_BAR, value).apply() }

    var dateTimeVisibility: Int
        get() = prefs.getInt(DATE_TIME_VISIBILITY, Constants.DateTime.ON)
        set(value) = prefs.edit { putInt(DATE_TIME_VISIBILITY, value).apply() }

    var swipeLeftEnabled: Boolean
        get() = prefs.getBoolean(SWIPE_LEFT_ENABLED, true)
        set(value) = prefs.edit { putBoolean(SWIPE_LEFT_ENABLED, value).apply() }

    var swipeRightEnabled: Boolean
        get() = prefs.getBoolean(SWIPE_RIGHT_ENABLED, true)
        set(value) = prefs.edit { putBoolean(SWIPE_RIGHT_ENABLED, value).apply() }

    var appTheme: Int
        get() = prefs.getInt(APP_THEME, AppCompatDelegate.MODE_NIGHT_YES)
        set(value) = prefs.edit { putInt(APP_THEME, value).apply() }

    var accentTheme: Int
        get() = prefs.getInt(ACCENT_THEME, Constants.AccentTheme.MONOCHROME)
        set(value) = prefs.edit { putInt(ACCENT_THEME, value).apply() }

    var fontFamily: Int
        get() = prefs.getInt(FONT_FAMILY, Constants.Font.SYSTEM)
        set(value) = prefs.edit { putInt(FONT_FAMILY, value).apply() }

    var textSizeScale: Float
        get() = prefs.getFloat(TEXT_SIZE_SCALE, 1.0f)
        set(value) = prefs.edit { putFloat(TEXT_SIZE_SCALE, value).apply() }

    var boldFont: Boolean
        get() = prefs.getBoolean(BOLD_FONT, false)
        set(value) = prefs.edit { putBoolean(BOLD_FONT, value).apply() }

    var proMessageShown: Boolean
        get() = prefs.getBoolean(PRO_MESSAGE_SHOWN, false)
        set(value) = prefs.edit { putBoolean(PRO_MESSAGE_SHOWN, value).apply() }

    var hideSetDefaultLauncher: Boolean
        get() = prefs.getBoolean(HIDE_SET_DEFAULT_LAUNCHER, false)
        set(value) = prefs.edit { putBoolean(HIDE_SET_DEFAULT_LAUNCHER, value).apply() }

    var screenTimeLastUpdated: Long
        get() = prefs.getLong(SCREEN_TIME_LAST_UPDATED, 0L)
        set(value) = prefs.edit { putLong(SCREEN_TIME_LAST_UPDATED, value).apply() }

    var launcherRestartTimestamp: Long
        get() = prefs.getLong(LAUNCHER_RESTART_TIMESTAMP, 0L)
        set(value) = prefs.edit { putLong(LAUNCHER_RESTART_TIMESTAMP, value).apply() }

    var shownOnDayOfYear: Int
        get() = prefs.getInt(SHOWN_ON_DAY_OF_YEAR, 0)
        set(value) = prefs.edit { putInt(SHOWN_ON_DAY_OF_YEAR, value).apply() }

    var hiddenApps: MutableSet<String>
        get() = prefs.getStringSet(HIDDEN_APPS, mutableSetOf()) as MutableSet<String>
        set(value) = prefs.edit { putStringSet(HIDDEN_APPS, value).apply() }

    var hiddenAppsUpdated: Boolean
        get() = prefs.getBoolean(HIDDEN_APPS_UPDATED, false)
        set(value) = prefs.edit { putBoolean(HIDDEN_APPS_UPDATED, value).apply() }

    var toShowHintCounter: Int
        get() = prefs.getInt(SHOW_HINT_COUNTER, 1)
        set(value) = prefs.edit { putInt(SHOW_HINT_COUNTER, value).apply() }

    var aboutClicked: Boolean
        get() = prefs.getBoolean(ABOUT_CLICKED, false)
        set(value) = prefs.edit { putBoolean(ABOUT_CLICKED, value).apply() }

    var rateClicked: Boolean
        get() = prefs.getBoolean(RATE_CLICKED, false)
        set(value) = prefs.edit { putBoolean(RATE_CLICKED, value).apply() }

    var wallpaperMsgShown: Boolean
        get() = prefs.getBoolean(WALLPAPER_MSG_SHOWN, false)
        set(value) = prefs.edit { putBoolean(WALLPAPER_MSG_SHOWN, value).apply() }

    var shareShownTime: Long
        get() = prefs.getLong(SHARE_SHOWN_TIME, 0L)
        set(value) = prefs.edit { putLong(SHARE_SHOWN_TIME, value).apply() }

    var swipeDownAction: Int
        get() = prefs.getInt(SWIPE_DOWN_ACTION, Constants.SwipeDownAction.NOTIFICATIONS)
        set(value) = prefs.edit { putInt(SWIPE_DOWN_ACTION, value).apply() }

    // Redesigned & New Features
    var fuzzySearch: Boolean
        get() = prefs.getBoolean(FUZZY_SEARCH, true)
        set(value) = prefs.edit { putBoolean(FUZZY_SEARCH, value).apply() }

    var calcInSearch: Boolean
        get() = prefs.getBoolean(CALC_IN_SEARCH, true)
        set(value) = prefs.edit { putBoolean(CALC_IN_SEARCH, value).apply() }

    var searchShortcuts: Boolean
        get() = prefs.getBoolean(SEARCH_SHORTCUTS, true)
        set(value) = prefs.edit { putBoolean(SEARCH_SHORTCUTS, value).apply() }

    var biometricLockEnabled: Boolean
        get() = prefs.getBoolean(BIOMETRIC_LOCK_ENABLED, false)
        set(value) = prefs.edit { putBoolean(BIOMETRIC_LOCK_ENABLED, value).apply() }

    var biometricLockedApps: MutableSet<String>
        get() = prefs.getStringSet(BIOMETRIC_LOCKED_APPS, mutableSetOf()) as MutableSet<String>
        set(value) = prefs.edit { putStringSet(BIOMETRIC_LOCKED_APPS, value).apply() }

    var frictionModeEnabled: Boolean
        get() = prefs.getBoolean(FRICTION_MODE_ENABLED, false)
        set(value) = prefs.edit { putBoolean(FRICTION_MODE_ENABLED, value).apply() }

    var frictionDuration: Int
        get() = prefs.getInt(FRICTION_DURATION, 5)
        set(value) = prefs.edit { putInt(FRICTION_DURATION, value).apply() }

    var frictionApps: MutableSet<String>
        get() = prefs.getStringSet(FRICTION_APPS, mutableSetOf()) as MutableSet<String>
        set(value) = prefs.edit { putStringSet(FRICTION_APPS, value).apply() }

    var customNote: String
        get() = prefs.getString(CUSTOM_NOTE, "").toString()
        set(value) = prefs.edit { putString(CUSTOM_NOTE, value).apply() }

    var showCustomNote: Boolean
        get() = prefs.getBoolean(SHOW_CUSTOM_NOTE, false)
        set(value) = prefs.edit { putBoolean(SHOW_CUSTOM_NOTE, value).apply() }

    // Dynamic slot access for up to 15 Home Apps
    fun getAppName(location: Int): String = prefs.getString("APP_NAME_$location", "").toString()
    fun setAppName(location: Int, name: String) = prefs.edit { putString("APP_NAME_$location", name).apply() }

    fun getAppPackage(location: Int): String = prefs.getString("APP_PACKAGE_$location", "").toString()
    fun setAppPackage(location: Int, pkg: String) = prefs.edit { putString("APP_PACKAGE_$location", pkg).apply() }

    fun getAppActivityClassName(location: Int): String? = prefs.getString("APP_ACTIVITY_CLASS_NAME_$location", "").toString()
    fun setAppActivityClassName(location: Int, cls: String?) = prefs.edit { putString("APP_ACTIVITY_CLASS_NAME_$location", cls ?: "").apply() }

    fun getAppUser(location: Int): String = prefs.getString("APP_USER_$location", "").toString()
    fun setAppUser(location: Int, user: String) = prefs.edit { putString("APP_USER_$location", user).apply() }

    fun getShortcutId(location: Int): String = prefs.getString("SHORTCUT_ID_$location", "").toString()
    fun setShortcutId(location: Int, id: String) = prefs.edit { putString("SHORTCUT_ID_$location", id).apply() }

    fun getIsShortcut(location: Int): Boolean = prefs.getBoolean("IS_SHORTCUT_$location", false)
    fun setIsShortcut(location: Int, isSc: Boolean) = prefs.edit { putBoolean("IS_SHORTCUT_$location", isSc).apply() }

    // Legacy compatibility bridge
    var appName1: String get() = getAppName(1); set(v) = setAppName(1, v)
    var appName2: String get() = getAppName(2); set(v) = setAppName(2, v)
    var appName3: String get() = getAppName(3); set(v) = setAppName(3, v)
    var appName4: String get() = getAppName(4); set(v) = setAppName(4, v)
    var appName5: String get() = getAppName(5); set(v) = setAppName(5, v)
    var appName6: String get() = getAppName(6); set(v) = setAppName(6, v)
    var appName7: String get() = getAppName(7); set(v) = setAppName(7, v)
    var appName8: String get() = getAppName(8); set(v) = setAppName(8, v)

    var appPackage1: String get() = getAppPackage(1); set(v) = setAppPackage(1, v)
    var appPackage2: String get() = getAppPackage(2); set(v) = setAppPackage(2, v)
    var appPackage3: String get() = getAppPackage(3); set(v) = setAppPackage(3, v)
    var appPackage4: String get() = getAppPackage(4); set(v) = setAppPackage(4, v)
    var appPackage5: String get() = getAppPackage(5); set(v) = setAppPackage(5, v)
    var appPackage6: String get() = getAppPackage(6); set(v) = setAppPackage(6, v)
    var appPackage7: String get() = getAppPackage(7); set(v) = setAppPackage(7, v)
    var appPackage8: String get() = getAppPackage(8); set(v) = setAppPackage(8, v)

    var appUser1: String get() = getAppUser(1); set(v) = setAppUser(1, v)
    var appUser2: String get() = getAppUser(2); set(v) = setAppUser(2, v)
    var appUser3: String get() = getAppUser(3); set(v) = setAppUser(3, v)
    var appUser4: String get() = getAppUser(4); set(v) = setAppUser(4, v)
    var appUser5: String get() = getAppUser(5); set(v) = setAppUser(5, v)
    var appUser6: String get() = getAppUser(6); set(v) = setAppUser(6, v)
    var appUser7: String get() = getAppUser(7); set(v) = setAppUser(7, v)
    var appUser8: String get() = getAppUser(8); set(v) = setAppUser(8, v)

    var isShortcut1: Boolean get() = getIsShortcut(1); set(v) = setIsShortcut(1, v)
    var isShortcut2: Boolean get() = getIsShortcut(2); set(v) = setIsShortcut(2, v)
    var isShortcut3: Boolean get() = getIsShortcut(3); set(v) = setIsShortcut(3, v)
    var isShortcut4: Boolean get() = getIsShortcut(4); set(v) = setIsShortcut(4, v)
    var isShortcut5: Boolean get() = getIsShortcut(5); set(v) = setIsShortcut(5, v)
    var isShortcut6: Boolean get() = getIsShortcut(6); set(v) = setIsShortcut(6, v)
    var isShortcut7: Boolean get() = getIsShortcut(7); set(v) = setIsShortcut(7, v)
    var isShortcut8: Boolean get() = getIsShortcut(8); set(v) = setIsShortcut(8, v)

    var shortcutId1: String get() = getShortcutId(1); set(v) = setShortcutId(1, v)
    var shortcutId2: String get() = getShortcutId(2); set(v) = setShortcutId(2, v)
    var shortcutId3: String get() = getShortcutId(3); set(v) = setShortcutId(3, v)
    var shortcutId4: String get() = getShortcutId(4); set(v) = setShortcutId(4, v)
    var shortcutId5: String get() = getShortcutId(5); set(v) = setShortcutId(5, v)
    var shortcutId6: String get() = getShortcutId(6); set(v) = setShortcutId(6, v)
    var shortcutId7: String get() = getShortcutId(7); set(v) = setShortcutId(7, v)
    var shortcutId8: String get() = getShortcutId(8); set(v) = setShortcutId(8, v)

    var appActivityClassName1: String? get() = getAppActivityClassName(1); set(v) = setAppActivityClassName(1, v)
    var appActivityClassName2: String? get() = getAppActivityClassName(2); set(v) = setAppActivityClassName(2, v)
    var appActivityClassName3: String? get() = getAppActivityClassName(3); set(v) = setAppActivityClassName(3, v)
    var appActivityClassName4: String? get() = getAppActivityClassName(4); set(v) = setAppActivityClassName(4, v)
    var appActivityClassName5: String? get() = getAppActivityClassName(5); set(v) = setAppActivityClassName(5, v)
    var appActivityClassName6: String? get() = getAppActivityClassName(6); set(v) = setAppActivityClassName(6, v)
    var appActivityClassName7: String? get() = getAppActivityClassName(7); set(v) = setAppActivityClassName(7, v)
    var appActivityClassName8: String? get() = getAppActivityClassName(8); set(v) = setAppActivityClassName(8, v)

    var appNameSwipeLeft: String
        get() = prefs.getString(APP_NAME_SWIPE_LEFT, "Camera").toString()
        set(value) = prefs.edit { putString(APP_NAME_SWIPE_LEFT, value).apply() }

    var appNameSwipeRight: String
        get() = prefs.getString(APP_NAME_SWIPE_RIGHT, "Phone").toString()
        set(value) = prefs.edit { putString(APP_NAME_SWIPE_RIGHT, value).apply() }

    var appPackageSwipeLeft: String
        get() = prefs.getString(APP_PACKAGE_SWIPE_LEFT, "").toString()
        set(value) = prefs.edit { putString(APP_PACKAGE_SWIPE_LEFT, value).apply() }

    var appActivityClassNameSwipeLeft: String?
        get() = prefs.getString(APP_ACTIVITY_CLASS_NAME_SWIPE_LEFT, "").toString()
        set(value) = prefs.edit { putString(APP_ACTIVITY_CLASS_NAME_SWIPE_LEFT, value).apply() }

    var appPackageSwipeRight: String
        get() = prefs.getString(APP_PACKAGE_SWIPE_RIGHT, "").toString()
        set(value) = prefs.edit { putString(APP_PACKAGE_SWIPE_RIGHT, value).apply() }

    var appActivityClassNameRight: String?
        get() = prefs.getString(APP_ACTIVITY_CLASS_NAME_SWIPE_RIGHT, "").toString()
        set(value) = prefs.edit { putString(APP_ACTIVITY_CLASS_NAME_SWIPE_RIGHT, value).apply() }

    var appUserSwipeLeft: String
        get() = prefs.getString(APP_USER_SWIPE_LEFT, "").toString()
        set(value) = prefs.edit { putString(APP_USER_SWIPE_LEFT, value).apply() }

    var appUserSwipeRight: String
        get() = prefs.getString(APP_USER_SWIPE_RIGHT, "").toString()
        set(value) = prefs.edit { putString(APP_USER_SWIPE_RIGHT, value).apply() }

    var clockAppPackage: String
        get() = prefs.getString(CLOCK_APP_PACKAGE, "").toString()
        set(value) = prefs.edit { putString(CLOCK_APP_PACKAGE, value).apply() }

    var clockAppUser: String
        get() = prefs.getString(CLOCK_APP_USER, "").toString()
        set(value) = prefs.edit { putString(CLOCK_APP_USER, value).apply() }

    var clockAppClassName: String?
        get() = prefs.getString(CLOCK_APP_CLASS_NAME, "").toString()
        set(value) = prefs.edit { putString(CLOCK_APP_CLASS_NAME, value).apply() }

    var calendarAppPackage: String
        get() = prefs.getString(CALENDAR_APP_PACKAGE, "").toString()
        set(value) = prefs.edit { putString(CALENDAR_APP_PACKAGE, value).apply() }

    var calendarAppUser: String
        get() = prefs.getString(CALENDAR_APP_USER, "").toString()
        set(value) = prefs.edit { putString(CALENDAR_APP_USER, value).apply() }

    var calendarAppClassName: String?
        get() = prefs.getString(CALENDAR_APP_CLASS_NAME, "").toString()
        set(value) = prefs.edit { putString(CALENDAR_APP_CLASS_NAME, value).apply() }

    var screenTimeAppPackage: String
        get() = prefs.getString(SCREEN_TIME_APP_PACKAGE, "").toString()
        set(value) = prefs.edit { putString(SCREEN_TIME_APP_PACKAGE, value).apply() }

    var screenTimeAppUser: String
        get() = prefs.getString(SCREEN_TIME_APP_USER, "").toString()
        set(value) = prefs.edit { putString(SCREEN_TIME_APP_USER, value).apply() }

    var screenTimeAppClassName: String?
        get() = prefs.getString(SCREEN_TIME_APP_CLASS_NAME, "").toString()
        set(value) = prefs.edit { putString(SCREEN_TIME_APP_CLASS_NAME, value).apply() }

    var shortcutIdSwipeLeft: String
        get() = prefs.getString(SHORTCUT_ID_SWIPE_LEFT, "").toString()
        set(value) = prefs.edit { putString(SHORTCUT_ID_SWIPE_LEFT, value).apply() }

    var isShortcutSwipeLeft: Boolean
        get() = prefs.getBoolean(IS_SHORTCUT_SWIPE_LEFT, false)
        set(value) = prefs.edit { putBoolean(IS_SHORTCUT_SWIPE_LEFT, value).apply() }

    var shortcutIdSwipeRight: String
        get() = prefs.getString(SHORTCUT_ID_SWIPE_RIGHT, "").toString()
        set(value) = prefs.edit { putString(SHORTCUT_ID_SWIPE_RIGHT, value).apply() }

    var isShortcutSwipeRight: Boolean
        get() = prefs.getBoolean(IS_SHORTCUT_SWIPE_RIGHT, false)
        set(value) = prefs.edit { putBoolean(IS_SHORTCUT_SWIPE_RIGHT, value).apply() }

    fun updateAppActivityClassName(packageName: String, activityClassName: String) {
        for (i in 1..15) {
            if (getAppPackage(i) == packageName) setAppActivityClassName(i, activityClassName)
        }
        if (clockAppPackage == packageName) clockAppClassName = activityClassName
        if (calendarAppPackage == packageName) calendarAppClassName = activityClassName
        if (screenTimeAppPackage == packageName) screenTimeAppClassName = activityClassName
        if (appPackageSwipeLeft == packageName) appActivityClassNameSwipeLeft = activityClassName
        if (appPackageSwipeRight == packageName) appActivityClassNameRight = activityClassName
    }

    fun getAppRenameLabel(appPackage: String): String = prefs.getString("RENAME_$appPackage", "").toString()
    fun setAppRenameLabel(appPackage: String, renameLabel: String) = prefs.edit { putString("RENAME_$appPackage", renameLabel).apply() }

    fun getAppCategory(appPackage: String): String = prefs.getString("CATEGORY_$appPackage", Constants.Category.ALL).toString()
    fun setAppCategory(appPackage: String, category: String) = prefs.edit { putString("CATEGORY_$appPackage", category).apply() }

    fun isAppBiometricLocked(appPackage: String): Boolean = biometricLockedApps.contains(appPackage)
    fun setAppBiometricLocked(appPackage: String, locked: Boolean) {
        val set = biometricLockedApps.toMutableSet()
        if (locked) set.add(appPackage) else set.remove(appPackage)
        biometricLockedApps = set
    }

    fun isAppFrictionEnabled(appPackage: String): Boolean = frictionApps.contains(appPackage)
    fun setAppFrictionEnabled(appPackage: String, enabled: Boolean) {
        val set = frictionApps.toMutableSet()
        if (enabled) set.add(appPackage) else set.remove(appPackage)
        frictionApps = set
    }

    var amoledLockScreenApplied: Boolean
        get() = prefs.getBoolean(AMOLED_LOCK_SCREEN_APPLIED, false)
        set(value) = prefs.edit { putBoolean(AMOLED_LOCK_SCREEN_APPLIED, value).apply() }
}
