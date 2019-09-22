package com.mss.imagesearcher.robolectric

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow
import java.lang.reflect.Modifier
import java.util.*

@Implements(Snackbar::class)
class ShadowSnackbar {

    @RealObject
    internal var snackbar: Snackbar? = null
    internal var text: String = ""
    private val duration: Int = 0
    private val gravity: Int = 0
    private val xOffset: Int = 0
    private val yOffset: Int = 0
    private val view: View? = null

    companion object {

        internal val shadowSnackbars: MutableList<ShadowSnackbar> = ArrayList()

        @Implementation
        fun make(view: View, text: CharSequence, duration: Int): Snackbar? {
            var snackbar: Snackbar? = null

            try {
                val constructor = Snackbar::class.java.getDeclaredConstructor(ViewGroup::class.java, View::class.java, ContentViewCallback::class.java)
                        ?: throw IllegalArgumentException("Seems like the constructor was not found!")


                if (Modifier.isPrivate(constructor.modifiers)) {
                    constructor.isAccessible = true
                }

                val parent = findSuitableParent(view)
                val content = LayoutInflater.from(parent!!.context).inflate(com.google.android.material.R.layout.design_layout_snackbar_include, parent, false) as SnackbarContentLayout

                snackbar = constructor.newInstance(parent, content, content)
                snackbar.setText(text)
                snackbar.duration = duration
            } catch (e: Exception) {
                e.printStackTrace()
            }

            shadowOf(snackbar).text = text.toString()

            shadowSnackbars.add(shadowOf(snackbar))

            return snackbar
        }

        private fun findSuitableParent(view: View?): ViewGroup? {
            var view = view
            var fallback: ViewGroup? = null
            do {
                if (view is CoordinatorLayout) {
                    return view
                } else if (view is FrameLayout) {
                    if (view.id == android.R.id.content) {
                        return view
                    } else {
                        fallback = view
                    }
                }

                if (view != null) {
                    val parent = view.parent

                    if (parent == null) {
                        fallback = FrameLayout(view.context)
                    }

                    view = if (parent is View) parent else null
                }
            } while (view != null)

            return fallback
        }

        @Implementation
        fun make(view: View, @StringRes resId: Int, duration: Int): Snackbar? {
            return make(view, view.resources.getText(resId), duration)
        }


        internal fun shadowOf(bar: Snackbar?): ShadowSnackbar {
            return Shadow.extract<Any>(bar) as ShadowSnackbar
        }

        fun reset() {
            shadowSnackbars.clear()
        }

        fun shownSnackbarCount(): Int {
            return if (shadowSnackbars.isEmpty()) 0 else shadowSnackbars.size

        }

        val textOfLatestSnackbar: String?
            get() = if (!shadowSnackbars.isEmpty()) shadowSnackbars[shadowSnackbars.size - 1].text else null

        val latestSnackbar: Snackbar?
            get() = if (!shadowSnackbars.isEmpty()) shadowSnackbars[shadowSnackbars.size - 1].snackbar else null
    }
}