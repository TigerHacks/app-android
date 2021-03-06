package tigerhacks.android.tigerhacksapp.prizes

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_prize.view.categoryTitleView
import kotlinx.android.synthetic.main.view_prize.view.descriptionTextView
import kotlinx.android.synthetic.main.view_prize.view.divider
import kotlinx.android.synthetic.main.view_prize.view.favoriteButton
import kotlinx.android.synthetic.main.view_prize.view.prizeListTextView
import kotlinx.android.synthetic.main.view_prize.view.titleTextView
import tigerhacks.android.tigerhacksapp.R
import tigerhacks.android.tigerhacksapp.models.FavoritablePrize
import tigerhacks.android.tigerhacksapp.models.Prize

/**
 * @author pauldg7@gmail.com (Paul Gillis)
 * Created by Conno on 9/8/2018.
 */

//PrizeView is an extension of the CardView that adds onClick functionality for the prize cards
class PrizeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.view_prize, this, true)
        val dimen = resources.getDimension(R.dimen.margin_top_large).toInt()
        setPadding(0, 0, 0, dimen)
    }

    @SuppressLint("SetTextI18n")
    fun setup(favoritablePrize: FavoritablePrize) {
        val prize = favoritablePrize.prize
        favoriteButton.isChecked = favoritablePrize.favoritable.isFavorited
        titleTextView.text = prize.title
        descriptionTextView.text = prize.description
        prizeListTextView.text = prize.reward

//        if (hasHeader) categoryTitleView.text = "${prize.prizeType} Prizes"
//
//        val vis = if (hasHeader) View.VISIBLE else View.GONE
//        categoryTitleView.visibility = vis
//        divider.visibility = vis
    }
}
