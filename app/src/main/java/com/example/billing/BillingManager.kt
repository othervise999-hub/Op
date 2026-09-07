package com.example.billing

import android.content.Context
import com.example.model.FilterType
import com.example.model.QualityOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SubscriptionPlan(
    val id: String,
    val title: String,
    val price: String,
    val period: String,
    val badge: String? = null,
    val description: String
)

class BillingManager private constructor(context: Context) {
    private val prefs = context.getSharedPreferences("ultravideo_billing_prefs", Context.MODE_PRIVATE)

    private val _isPremium = MutableStateFlow(prefs.getBoolean("is_premium", false))
    val isPremium: StateFlow<Boolean> = _isPremium.asStateFlow()

    val availablePlans = listOf(
        SubscriptionPlan(
            id = "ultravideo_monthly",
            title = "Monthly Pro",
            price = "$4.99",
            period = "/ month",
            badge = "7 Days Free",
            description = "Billed monthly. Cancel anytime in Google Play."
        ),
        SubscriptionPlan(
            id = "ultravideo_yearly",
            title = "Annual VIP",
            price = "$29.99",
            period = "/ year",
            badge = "SAVE 50%",
            description = "Best value! $2.50/mo billed annually."
        ),
        SubscriptionPlan(
            id = "ultravideo_lifetime",
            title = "Lifetime Founder",
            price = "$49.99",
            period = "one-time",
            badge = "POPULAR",
            description = "One-time payment. Forever 9K & neural cloud access."
        )
    )

    fun isQualityAvailable(quality: QualityOption): Boolean {
        if (!quality.isPro) return true
        return _isPremium.value
    }

    fun isFilterAvailable(filter: FilterType): Boolean {
        if (!filter.isPro) return true
        return _isPremium.value
    }

    fun purchaseSubscription(planId: String, onSuccess: () -> Unit = {}) {
        // Simulates Google Play Billing Purchase Flow and unlocks state
        prefs.edit().putBoolean("is_premium", true).apply()
        _isPremium.value = true
        onSuccess()
    }

    fun restorePurchases(onResult: (Boolean) -> Unit) {
        val active = prefs.getBoolean("is_premium", false)
        _isPremium.value = active
        onResult(active)
    }

    fun setPremiumStatus(enabled: Boolean) {
        prefs.edit().putBoolean("is_premium", enabled).apply()
        _isPremium.value = enabled
    }

    companion object {
        @Volatile
        private var INSTANCE: BillingManager? = null

        fun getInstance(context: Context): BillingManager {
            return INSTANCE ?: synchronized(this) {
                val instance = BillingManager(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
