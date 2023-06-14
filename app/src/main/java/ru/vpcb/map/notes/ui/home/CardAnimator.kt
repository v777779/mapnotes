package ru.vpcb.map.notes.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.view.isVisible
import ru.vpcb.map.notes.model.Note
import timber.log.Timber


object CardAnimator {
    const val SCREEN_MARGIN = 16
    const val SCREEN_TOP = 48
    const val CARD_WIDTH = 0.9
    const val CARD_HEIGHT = 200
    const val MARKER_OFFSET = 56

    fun hideCard(view: View?): Boolean {
        val card = view ?: return false
        card.isVisible = false
        return true
    }

    private fun animator(view: View, op: String, start: Float, end: Float, delay: Long) =
        ObjectAnimator.ofFloat(view, op, start, end)
            .apply {
                duration = delay
            }

    private fun animators(vararg animators: ObjectAnimator) =
        AnimatorSet().apply {
            animators.forEach {
                playTogether(it)
            }

        }


    fun showCard(view: View?, note: Note): Boolean {
        return try {
            val card = view ?: return false
            val context = card.context ?: return false
            val dp = context.resources?.displayMetrics ?: return false
            var x = note.screen?.x?.toFloat() ?: return false
            var y = note.screen?.y?.toFloat() ?: return false
            val w = (CARD_WIDTH * dp.widthPixels).toFloat()
            val h = (CARD_HEIGHT * dp.density).toFloat()
            val margin = SCREEN_MARGIN * dp.density
            val top = SCREEN_TOP * dp.density
            val offset = MARKER_OFFSET * dp.density

            card.isVisible = true
            val limitX = dp.widthPixels - w - margin
            val offsetX = if (x < dp.widthPixels / 2) -100 else 100
            val limitY = top + h + offset
            val offsetY = if (y < limitY) -100 else 100

            x -= w / 2
            if (x < margin) x = margin
            else if (x > limitX) x = limitX

            y = if (y < limitY) y + offset else y - h

            card.x = x
            card.y = y

            animators(
                animator(card, "translationX", x - offsetX, x, 150),
                animator(card, "translationY", y - offsetY, y, 150),
                animator(card, "alpha", 0f, 1f, 250)
            ).start()

            true
        } catch (e: Exception) {
            Timber.d(e)
            false
        }
    }


}