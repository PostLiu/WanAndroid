package com.postliu.wanandroid.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.repeatOnLifecycle
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Observable
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 为[LiveEventBus]接收值提供Jetpack Compose支持
 *
 * @param T  [class type]
 * @param initialValue  [A default value]
 * @param lifecycle  [A class that has an Android lifecycle. These events can be used by custom components to handle lifecycle changes without implementing any code inside the Activity or the Fragment.]
 * @param minActiveState  [Lifecycle states. You can consider the states as the nodes in a graph and Lifecycle.Events as the edges between these nodes.]
 * @param context [ Persistent context for the coroutine. It is an indexed set of Element instances. An indexed set is a mix between a set and a map. Every element in this set has a unique Key.]
 * @return [State]
 */
@ExperimentalLifecycleComposeApi
@Composable
fun <T> Observable<T>.collectAsStateWithLifecycle(
    initialValue: T,
    lifecycle: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T> {
    return produceState(initialValue, this, lifecycle, minActiveState, context) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            if (context == EmptyCoroutineContext) {
                this@collectAsStateWithLifecycle.observe(lifecycle) { this@produceState.value = it }
            } else withContext(context) {
                this@collectAsStateWithLifecycle.observe(lifecycle) { this@produceState.value = it }
            }
        }
    }
}