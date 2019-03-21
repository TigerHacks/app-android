package tigerhacks.android.tigerhacksapp.sponsors.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.Glide
import tigerhacks.android.tigerhacksapp.R
import tigerhacks.android.tigerhacksapp.sponsors.Sponsor

/*
 * @author pauldg7@gmail.com (Paul Gillis)
 */

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SponsorImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val sponsorImageView: ImageView
    lateinit var sponsorData: Sponsor

    var listener: View.OnClickListener? = null
        @CallbackProp set

    init {
        LayoutInflater.from(context).inflate(R.layout.fragment_sponsors, this, true)
        sponsorImageView = findViewById(R.id.sponsorImageView)
    }

    @ModelProp
    fun setSponsor(sponsor: Sponsor) {
        sponsorData = sponsor
        Glide.with(sponsorImageView).load(sponsor.image).into(sponsorImageView)
    }

    @AfterPropsSet
    fun useProps() {
        setOnClickListener(listener)
    }
}