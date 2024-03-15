package xyz.dnieln7.media.common

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

fun ComponentActivity.launchWithLifecycle(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend () -> Unit,
) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            block()
        }
    }
}
